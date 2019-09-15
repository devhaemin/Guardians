package kr.guardians.falldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.RegisterProgress.InsertEmailActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button loginButton;
    Button registerButton;
    EditText editEmail;
    EditText editPw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindView();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent ik = new Intent(this,MainActivity.class);
                startActivity(ik);
                break;
            case R.id.btn_register:
                Intent i = new Intent(this, InsertEmailActivity.class);
                startActivity(i);
                break;
        }
    }

    private void bindView(){
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);
        editEmail = findViewById(R.id.edit_login_email);
        editPw = findViewById(R.id.edit_login_pw);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }
}
