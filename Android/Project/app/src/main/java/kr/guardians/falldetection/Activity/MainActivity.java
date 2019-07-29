package kr.guardians.falldetection.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import kr.guardians.falldetection.Adapter.MainPagerAdapter;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPagerAdapter pa = new MainPagerAdapter(getSupportFragmentManager());
        ViewPager vp = findViewById(R.id.viewpager);
        vp.setAdapter(pa);
    }
}
