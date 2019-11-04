package kr.guardians.falldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.User;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.ResponseCheckProcess;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private Intent toMain;
    private Intent toLogin;

    private RetrofitInterface retrofitInterface;

    private String TAG = "SplashActivity";
    private ResponseCheckProcess<User> checkProcess;

    private GlobalApplication application;

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
        Log.d(TAG, "Generate Retrofit Client");

        doAutoLoginProcess();

    }

    private void doAutoLoginProcess() {
        Handler handler = new Handler();
        checkProcess = new ResponseCheckProcess<>(this, TAG);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Call<User> callUser = retrofitInterface.doLoginWithToken(application.getAccessToken().getTokenString());
                callUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if (checkProcess.isVaild(call, response)) {
                            User user = response.body();
                            assert user != null;
                            application.setUser(user);
                            startActivity(toMain);
                            Toast.makeText(getApplicationContext(), user.getUserName() + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "가디언즈 낙상도우미 앱에 오신 것을 환영합니다.\n로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
                            startActivity(toLogin);
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        //checkProcess.onFailure(call, t);
                        Toast.makeText(getApplicationContext(), "가디언즈 낙상도우미 앱에 오신 것을 환영합니다.\n로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
                        startActivity(toLogin);
                        finish();
                    }
                });
            }
        }, 1500);
    }
}
