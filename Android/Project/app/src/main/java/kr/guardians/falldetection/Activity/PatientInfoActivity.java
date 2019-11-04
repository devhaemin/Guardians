package kr.guardians.falldetection.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import kr.guardians.falldetection.CustomWidget.CircleProgressBar;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientInfoActivity extends AppCompatActivity {

    private Intent fromOutside;
    private String TAG = "PatientInfoActivity";
    private String patientCode;

    private ImageButton btnBack;
    private ImageView patientImage, profileImage;
    private TextView nameText, infoText, painText, phoneText, patientInfoText;
    private CircleProgressBar progressBar;

    private GlobalApplication application;

    private View loadView;


    public static void start(Context context, String patientCode) {
        Intent starter = new Intent(context, PatientInfoActivity.class);
        starter.putExtra("patientCode", patientCode);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        application = (GlobalApplication) getApplicationContext();
        checkVaildAccess();
        bindView();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refresh();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void refresh() {
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.getPatientInfo(application.getAccessToken().getTokenString(), patientCode).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.body() != null) {
                    Log.e(TAG, "Code: " + response.code() + " Message: " + response.message());
                    Patient patient = response.body();
                    nameText.setText(patient.getPatientName());
                    String gender = patient.getGender() == 0 ? "남성" : "여성";
                    infoText.setText(patient.getAge() + "세 | " + gender);
                    painText.setText(patient.getPainInfo());
                    patientInfoText.setText(patient.getRoomCode() + "호실 " + patient.getPatientName() + "님");
                    phoneText.setText(patient.getPhone());
                    progressBar.setProgress((int)patient.getWarningRate());

                    Glide.with(getBaseContext())
                            //.load(patient.getProfileImageUrl())
                            .load("https://drive.google.com/uc?authuser=0&id=1ZVyFrRXNO-ooK7HKljtFxQ8CHqofd559&export=download")
                            .placeholder(R.drawable.status0)
                            .circleCrop()
                            .into(profileImage);
                    Glide.with(getBaseContext())
                            .load(patient.getImageUrl())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .thumbnail(0.1f)
                            .placeholder(R.drawable.status0)
                            .into(patientImage);
                    loadView.setVisibility(View.GONE);
                     } else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void bindView() {
        btnBack = findViewById(R.id.btn_back);
        patientImage = findViewById(R.id.patient_image);
        nameText = findViewById(R.id.text_name);
        infoText = findViewById(R.id.text_info);
        painText = findViewById(R.id.text_pain);
        phoneText = findViewById(R.id.text_phone);
        patientInfoText = findViewById(R.id.text_patient_info);
        profileImage = findViewById(R.id.profile_image);
        progressBar = findViewById(R.id.progress);
        progressBar.setProgress(40);
        loadView = findViewById(R.id.load_view);
    }

    private void checkVaildAccess() {
        fromOutside = getIntent();
        if (fromOutside == null) {
            Toast.makeText(this, "비정상적인 접근입니다. 지속된다면 고객센터에 문의 주세요.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Intent NULL Error");
            finish();
        }
        patientCode = fromOutside.getStringExtra("patientCode");
    }
}
