package com.example.mymusic;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadMusicWithMediaStore(this); // 调用你的查询方法
        getSupportFragmentManager().beginTransaction().replace(R.id.mian_fragment, new MusicPlayerFragment()).commit();
        Button btnPlay =findViewById(R.id.btn_play);
        Button btnList =findViewById(R.id.btn_list);
        btnPlay.setOnClickListener(this);
        btnList.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
    }
    @Override
    public void onClick(View v) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // 获取栈顶的事务条目
            FragmentManager.BackStackEntry topEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);

            String tag = topEntry.getName();  // 获取事务的 tag（即 addToBackStack("add") 中的参数）

            if (String.valueOf(v.getId()).equals(tag)) {
                // 当前栈顶事务是 tag 为 "add" 的操作
                Log.d("BackStack", "栈顶事务 tag 为 "+v.getId());
                return;
            } else {
                changeFragment(v.getId());
                Log.d("BackStack", "栈顶事务 tag 为: " + tag);
            }
        } else {
            changeFragment(v.getId());
        }
    }
    private void changeFragment(int operationId) {
        if(operationId == R.id.btn_list) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.mian_fragment, new MusicListFragment()).
                    addToBackStack(String.valueOf(operationId)).commit();
        }
        else if(operationId == R.id.btn_play) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.mian_fragment, new MusicPlayerFragment()).
                    addToBackStack(String.valueOf(operationId)).commit();
        }
    }
    public void loadMusicWithMediaStore(Context context) {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        Log.d("MusicFile", String.valueOf(cursor.getCount()));
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                Log.d("MusicFile", "文件名: " + name + " | 路径: " + path);
            }
        } else {
            Log.d("MusicFile", "未找到音频文件");
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}