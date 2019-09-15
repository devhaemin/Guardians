package kr.guardians.falldetection.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import kr.guardians.falldetection.Adapter.MainPagerAdapter;
import kr.guardians.falldetection.CustomWidget.CustomAppbar;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ToggleButton toggleHome,toggleList,toggleAlarm,togglePsearch;
    ViewPager vp;
    MainPagerAdapter pa;

    CustomAppbar appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViewByID();

        pa = new MainPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(pa);
        setCurrentPage(0);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setCurrentPage(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    private void setCurrentPage(int i){
        toggleAlarm.setChecked(false);
        toggleList.setChecked(false);
        toggleHome.setChecked(false);
        togglePsearch.setChecked(false);
        vp.setCurrentItem(i);
        switch (i){
            case 0:
                toggleHome.setChecked(true);
                break;
            case 1:
                toggleList.setChecked(true);
                break;
            case 2:
                toggleAlarm.setChecked(true);
                break;
            case 3:
                togglePsearch.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.toggle_home:
                    setCurrentPage(0);
                    break;
                case R.id.toggle_list:
                    setCurrentPage(1);
                    break;
                case R.id.toggle_alarm:
                    setCurrentPage(2);
                    break;
                case R.id.toggle_psearch:
                    setCurrentPage(3);
                    break;
            }
    }

    private void bindViewByID(){
        vp = findViewById(R.id.viewpager);
        toggleHome = findViewById(R.id.toggle_home);
        toggleAlarm = findViewById(R.id.toggle_alarm);
        toggleList = findViewById(R.id.toggle_list);
        togglePsearch = findViewById(R.id.toggle_psearch);
        appbar = findViewById(R.id.appbar);
        addListenerToView();
    }

    private void addListenerToView(){
        togglePsearch.setOnClickListener(this);
        toggleHome.setOnClickListener(this);
        toggleList.setOnClickListener(this);
        toggleAlarm.setOnClickListener(this);

    }
}
