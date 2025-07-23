package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MusicAdapter extends FragmentPagerAdapter {
    public MusicAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    @Override
    public Fragment getItem(int position) {
        return null;
    }
    @Override
    public int getCount() {
        return 0;
    }
}
