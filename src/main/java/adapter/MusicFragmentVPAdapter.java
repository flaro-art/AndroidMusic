package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class MusicFragmentVPAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MusicFragmentVPAdapter(@NonNull FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments==null?null:fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
