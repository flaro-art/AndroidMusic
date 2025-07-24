package com.example.mymusic;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pojo.PlayableMusicFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import utils.FileUtils;


public class  MusicPlayerFragment extends Fragment implements View.OnClickListener {
    private TextView playView;
    private Button playButton;
    private String musicName;
    private String musicPath;
    private long duration;
    private int position;
    private  MediaPlayer mediaPlayer ;
    private List<PlayableMusicFile> playableMusicFiles;


    private  static boolean isPlaying;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playableMusicFiles = FileUtils.loadMusicWithMediaStore(requireContext());
        if(getArguments()!=null){
            musicName = getArguments().getString("musicName");
            musicPath = getArguments().getString("musicPath");
            duration = getArguments().getLong("duration");
            position = getArguments().getInt("position");
            isPlaying = getArguments().getBoolean("isPlaying");
            isPlaying = getArguments().getBoolean("isPlaying");
            Log.d("MusicPlayerFragment",musicName+" "+musicPath+" "+duration+" "+position);
        }else {
            musicName = playableMusicFiles.get(position).getDisplayName();
            musicPath = playableMusicFiles.get(position).getPath();
            duration  =  playableMusicFiles.get(position).getDuration();
            isPlaying = false;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);
        playView = view.findViewById(R.id.music_name);
        playView.setText(musicName);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(musicPath);
            mediaPlayer.prepare();
            if (isPlaying){
                mediaPlayer.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        playButton = view.findViewById(R.id.play_pause_button);
        Button nextButton = view.findViewById(R.id.next_button);
        Button lastButton = view.findViewById(R.id.last_button);
        playButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        lastButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.play_pause_button){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                playButton.setText("播放");
            }else{
                mediaPlayer.start();
                playButton.setText("暂停");
            }
        }
        if(v.getId()==R.id.next_button){
            mediaPlayer.stop();
            mediaPlayer.reset();
            if(position+1>=playableMusicFiles.size()){
                position = 0;
            }else position++;
            String path = playableMusicFiles.get(position).getPath();
            playView.setText(playableMusicFiles.get(position).getDisplayName());
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(v.getId()==R.id.last_button){
            mediaPlayer.stop();
            mediaPlayer.reset();
            if(position-1<0){
                position = playableMusicFiles.size()-1;
            }else position--;
            String path = playableMusicFiles.get(position).getPath();
            playView.setText(playableMusicFiles.get(position).getDisplayName());
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
}