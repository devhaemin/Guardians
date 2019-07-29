package kr.guardians.falldetection.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kr.guardians.falldetection.Fragment.HomeFragment;
import kr.guardians.falldetection.Fragment.ListFragment;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;
    Context context;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        ListFragment listFragment = new ListFragment();

        fragments.add(homeFragment);
        fragments.add(listFragment);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}




























