package kr.guardians.falldetection.Activity;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import kr.guardians.falldetection.Adapter.MainPagerAdapter;
import kr.guardians.falldetection.CustomWidget.CustomAppbar;
import kr.guardians.falldetection.FirebaseCloudMessage.FirebaseMessagingService;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.User;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private final long FINISH_INTERVAL_TIME = 2000;
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
    private ToggleButton toggleHome, toggleList, toggleAlarm, togglePsearch;
    private ViewPager vp;
    private MainPagerAdapter pa;
    private CustomAppbar appbar;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token : ",token);
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.setFirebaseToken(((GlobalApplication) getApplicationContext()).getAccessToken().getTokenString(), token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e(TAG, "Token is sent : " + token);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        bindViewByID();

        pa = new MainPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(pa);
        setCurrentPage(0);
        vp.addOnPageChangeListener(this);
        vp.setOffscreenPageLimit(4);

        builder = new NotificationCompat.Builder(getBaseContext(), "ha")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("가디언즈")
                .setContentText("정해민님의 낙상 위험도가 80%로 감지되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(345, builder.build());

    }

    private void setCurrentPage(int i) {
        toggleAlarm.setChecked(false);
        toggleList.setChecked(false);
        toggleHome.setChecked(false);
        togglePsearch.setChecked(false);
        vp.setCurrentItem(i);
        switch (i) {
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
        switch (v.getId()) {
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

    private void bindViewByID() {
        vp = findViewById(R.id.viewpager);
        toggleHome = findViewById(R.id.toggle_home);
        toggleAlarm = findViewById(R.id.toggle_alarm);
        toggleList = findViewById(R.id.toggle_list);
        togglePsearch = findViewById(R.id.toggle_psearch);
        appbar = findViewById(R.id.appbar);
        addListenerToView();
    }

    private void addListenerToView() {
        togglePsearch.setOnClickListener(this);
        toggleHome.setOnClickListener(this);
        toggleList.setOnClickListener(this);
        toggleAlarm.setOnClickListener(this);

    }


    @Override
    public void onPageSelected(int i) {
        setCurrentPage(i);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
