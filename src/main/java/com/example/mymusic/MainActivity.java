package com.example.mymusic;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.pojo.ItemViewModel;
import com.example.pojo.PlayableMusicFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.MusicAdapter;
import utils.FileUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private List<String> musicNames;
    private List<PlayableMusicFile> playableMusicFiles;

    private MediaPlayer mediaPlayer;
    private ItemViewModel viewModel;

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
        mediaPlayer = new MediaPlayer();
        playableMusicFiles = FileUtils.loadMusicWithMediaStore(this);
        musicNames = new ArrayList<>();
        for (PlayableMusicFile musicFile : playableMusicFiles) {
            musicNames.add(musicFile.getDisplayName());
            Log.d("MusicFile", "时间："+musicFile.getDuration()+"文件名: " + musicFile.getDisplayName());
        }
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
            getSupportFragmentManager().executePendingTransactions();
            MusicAdapter musicArrayAdapter = new MusicAdapter(this, playableMusicFiles);
            ListView listView = findViewById(R.id.music_list_view);
            listView.setAdapter(musicArrayAdapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                try {
                    String path = playableMusicFiles.get(position).getPath();
                    Log.d("MusicFile", "开始播放文件");
                    MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("musicName", musicNames.get(position));
                    bundle.putString("musicPath", path);
                    bundle.putInt("position", position);
                    bundle.putLong("duration", playableMusicFiles.get(position).getDuration());
                    bundle.putBoolean("isPlaying", true);
                    musicPlayerFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.mian_fragment, musicPlayerFragment).
                            addToBackStack(String.valueOf(R.id.btn_play)).
                            commit();
//                    getSupportFragmentManager().beginTransaction().
//                            hide().
//                            addToBackStack(String.valueOf(R.id.btn_play)).
//                            commit();

                } catch (Exception e) {
                    Log.d("MusicFile", "文件读取失败");
                    throw new RuntimeException(e);
                }
                TextView textView = findViewById(R.id.item_select);
                if(textView != null) {
                    textView.setText(musicNames.get(position));
                }
                Log.d("MusicFile", "点击了第"+position+"个文件: " + musicNames.get(position));
            });
        }
        else if(operationId == R.id.btn_play) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.mian_fragment, new MusicPlayerFragment()).
                    addToBackStack(String.valueOf(operationId)).commit();
        }
    }

}