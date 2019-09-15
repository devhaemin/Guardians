package kr.guardians.falldetection.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import kr.guardians.falldetection.Fragment.AlarmFragment;
import kr.guardians.falldetection.Fragment.HomeFragment;
import kr.guardians.falldetection.Fragment.ListFragment;
import kr.guardians.falldetection.Fragment.ModifyFragment;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;
    Context context;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        ListFragment listFragment = new ListFragment();
        AlarmFragment alarmFragment = new AlarmFragment();
        ModifyFragment modifyFragment = new ModifyFragment();

        fragments.add(homeFragment);
        fragments.add(listFragment);
        fragments.add(alarmFragment);
        fragments.add(modifyFragment);
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




























