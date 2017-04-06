package com.example.sportsgo.sportsgo.adapter;

/**
 * Created by Long on 4/6/2017.
 */
import com.example.sportsgo.sportsgo.Fragment.InfoFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new InfoFragment();
            case 1:
                // Games fragment activity
                return new InfoFragment();
            case 2:
                // Movies fragment activity
                return new InfoFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}