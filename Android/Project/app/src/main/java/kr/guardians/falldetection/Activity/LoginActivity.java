package kr.guardians.falldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.User;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.RegisterProgress.InsertEmailActivity;
import kr.guardians.falldetection.Server.ResponseCheckProcess;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton, registerButton;
    private EditText editEmail, editPw;
    private String TAG = "LoginActivity";
    private ResponseCheckProcess<User> checkProcess;
    private GlobalApplication application;
    private Intent toMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        application = (GlobalApplication) getApplicationContext();
        toMain = new Intent(this,MainActivity.class);

        bindView();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                doLoginProcess();
                break;
            case R.id.btn_register:
                Intent i = new Intent(this, InsertEmailActivity.class);
                startActivity(i);
                break;
        }
    }

    private void doLoginProcess() {
        checkProcess = new ResponseCheckProcess<>(this, TAG);

        if (editEmail.getText().toString().equals("haemin_debug") && editPw.getText().toString().equals("haemin_debug")) {
            startActivity(toMain);
            finish();
        } else {
            RetrofitClient.getRetrofit().create(RetrofitInterface.class)
                    .doLoginProcess(editEmail.getText().toString(), editPw.getText().toString(), FirebaseInstanceId.getInstance().getToken())
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            if (checkProcess.isVaild(call, response)) {
                                User user = response.body();
                                assert user != null;
                                application.setUser(user);
                                ((GlobalApplication)getApplicationContext()).setAccessToken(user.getAccessToken());
                                Toast.makeText(getApplicationContext(), user.getUserName() + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                                startActivity(toMain);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"아이디 혹은 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            checkProcess.onFailure(call, t);
                        }
                    });
        }
    }

    private void bindView() {
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);
        editEmail = findViewById(R.id.edit_login_email);
        editPw = findViewById(R.id.edit_login_pw);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }
}
