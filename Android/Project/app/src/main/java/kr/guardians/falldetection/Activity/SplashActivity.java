package kr.guardians.falldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.User;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    Intent toMain;
    Intent toLogin;

    RetrofitInterface retrofitInterface;
    GlobalApplication application;

    String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        application = (GlobalApplication) getApplicationContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Change Activity into FullScreen

        toMain = new Intent(this, MainActivity.class);
        toLogin = new Intent(this, LoginActivity.class);

        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        //Code for Network Connection

        doAutoLoginProcess();

    }
    void doAutoLoginProcess(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Call<User> callUser = retrofitInterface.doLoginWithToken(application.getAccessToken().getTokenString());
                callUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200) {

                            User user = response.body();
                            application.setUser(user);
                            startActivity(toMain);

                        } else {
                            startActivity(toLogin);
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e(TAG, t.getMessage() + "");
                        startActivity(toLogin);
                        Toast.makeText(application, "서버 접속이 원활하지 않습니다.\n네트워크 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }, 1500);
    }
}
