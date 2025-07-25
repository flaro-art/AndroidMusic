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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.fragment.MusicFragment;
import com.example.pojo.ItemViewModel;
import com.example.pojo.MediaPlayerManager;
import com.example.pojo.PlayableMusicFile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.MusicAdapter;
import adapter.MusicFragmentVPAdapter;
import utils.FileUtils;


public class MainActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private MusicFragmentVPAdapter musicFragmentVPAdapter;
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private List<String> musicNames;
    private List<PlayableMusicFile> playableMusicFiles;

    public MediaPlayer mediaPlayer= MediaPlayerManager.getInstance().getMediaPlayer();
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

        mViewPager=findViewById(R.id.vp);
        mBottomNavigationView=findViewById(R.id.bottom_menu);

        initData();

        musicFragmentVPAdapter = new MusicFragmentVPAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(musicFragmentVPAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this, "当前页面：" + position, Toast.LENGTH_SHORT).show();
                onPagerSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {
                    mViewPager.setCurrentItem(0);
                    return true;
                } else if (itemId == R.id.menu_player) {
                    mViewPager.setCurrentItem(1);
                    return true;
                } else if (itemId == R.id.menu_music_list) {
                    mViewPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });
    }

    private void onPagerSelected(int position) {
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.menu_player);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.menu_music_list);
                break;
        }
    }
    private void initData() {
        fragments=new ArrayList<>();
        fragments.add(MusicFragment.newInstance("",""));
        fragments.add(new MusicPlayerFragment());
        fragments.add(new MusicListFragment());
    }

    public void playMusic(PlayableMusicFile playableMusicFile) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(playableMusicFile.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}