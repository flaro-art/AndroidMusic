package com.example.mymusic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fragment.MusicFragment;
import com.example.pojo.PlayableMusicFile;

import java.util.List;
import java.util.Objects;

import adapter.MusicAdapter;
import utils.FileUtils;


public class MusicListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<PlayableMusicFile> playableMusicFiles;
    private MusicAdapter musicArrayAdapter;
    private ListView listView;
    private MainActivity mainActivity;
    private String mParam1;
    private String mParam2;

    public MusicListFragment() {
        // Required empty public constructor
    }
    public static MusicListFragment newInstance(String param1, String param2) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playableMusicFiles = FileUtils.loadMusicWithMediaStore(requireActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_music_list, container, false);
        musicArrayAdapter = new MusicAdapter(requireActivity(), playableMusicFiles);
        mainActivity = new MainActivity();
        listView = view.findViewById(R.id.music_list_view);
        listView.setAdapter(musicArrayAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            mainActivity.playMusic(playableMusicFiles.get(position));
        });

        return view;
    }
}