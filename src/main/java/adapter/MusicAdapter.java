package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mymusic.R;
import com.example.pojo.PlayableMusicFile;

import java.util.List;

public  class MusicAdapter extends BaseAdapter {
    private Context mContext;

    private List<PlayableMusicFile> musicFiles;

    public MusicAdapter(Context mContext, List<PlayableMusicFile> musicFiles) {
        this.mContext = mContext;
        this.musicFiles = musicFiles;
    }
    public List<PlayableMusicFile> getMusicFiles() {
        return musicFiles;
    }
    @Override
    public int getCount() {
        return musicFiles.size();
    }

    @Override
    public Object getItem(int position) {
        return musicFiles.get(position).getDisplayName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select, null);
        TextView item_select =view.findViewById(R.id.item_select);
        item_select.setText(musicFiles.get(position).getDisplayName());
        return view;
    }

}
