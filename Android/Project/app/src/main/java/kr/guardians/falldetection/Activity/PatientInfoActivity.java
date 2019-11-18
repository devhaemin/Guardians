package kr.guardians.falldetection.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import kr.guardians.falldetection.CustomWidget.CircleProgressBar;
import kr.guardians.falldetection.CustomWidget.GuardiansBedView;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Random;

public class PatientInfoActivity extends AppCompatActivity {

    private Intent fromOutside;
    private String TAG = "PatientInfoActivity";
    private String patientCode;

    private ImageButton btnBack, btnEdit;
    private WebView patientImage;
    private ImageView profileImage;
    private TextView nameText, infoText, painText, phoneText, patientInfoText, progressText;
    private CircleProgressBar progressBar;
    private GuardiansBedView bedView;
    private Activity activity;

    private GlobalApplication application;


    private View loadView;
    private boolean started = false;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshWarning();
            if(started) {
                startLoop();
            }
        }
    };

    public void stopLoop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void startLoop() {
        started = true;
        handler.postDelayed(runnable, 2000);

    }


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
        activity = this;



    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
        startLoop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLoop();
    }
    private void refreshWarning(){
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.getPatientInfo(application.getAccessToken().getTokenString(),patientCode).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.body() != null) {
                    int progress = (int) response.body().getWarningRate();
                    progressBar.setProgress(progress);
                    progressText.setText(progress + "%");
                    if (progress <= 30) {
                        progressText.setTextColor(getResources().getColor(R.color.blueProgress, null));
                    } else if (progress <= 50) {
                        progressText.setTextColor(getResources().getColor(R.color.yellowProgress, null));
                    } else {
                        progressText.setTextColor(getResources().getColor(R.color.redProgress, null));
                    }
                }
            }
            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });
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
                    int progress = (int) patient.getWarningRate();
                    progressBar.setProgress(progress);
                    progressText.setText(progress + "%");
                    if (progress <= 30) {
                        progressText.setTextColor(getResources().getColor(R.color.blueProgress, null));
                    } else if (progress <= 50) {
                        progressText.setTextColor(getResources().getColor(R.color.yellowProgress, null));
                    } else {
                        progressText.setTextColor(getResources().getColor(R.color.redProgress, null));
                    }


                    Glide.with(getBaseContext())
                            .load(patient.getProfileImageUrl())
                            .placeholder(R.drawable.status0)
                            .circleCrop()
                            .into(profileImage);

                    patientImage.setPadding(0, 0, 0, 0);
                    //webView.setInitialScale(100);
                    patientImage.getSettings().setBuiltInZoomControls(false);
                    patientImage.getSettings().setJavaScriptEnabled(true);
                    patientImage.getSettings().setLoadWithOverviewMode(true);
                    patientImage.getSettings().setUseWideViewPort(true);
                    patientImage.setInitialScale(10);
                    patientImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

                    String url = "http://rkeldjswm.gonetis.com:8090/?action=stream";
                    patientImage.loadUrl(url);
                    DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
                    int deviceWidth = disp.widthPixels;
                    int deviceHeight = disp.heightPixels;
                    float density = disp.density;
                    String imgSrcHtml = "<html><img src='" + url + "'width='" + deviceWidth + "px' height='" + 280 * density + "px' /></html>";
                    // String imgSrcHtml = url;
                    patientImage.loadData(imgSrcHtml, "text/html", "UTF-8");

                    bedView.setBeds(patient);

                    btnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PatientModifyActivity.start(activity, patientCode);
                        }
                    });
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
        btnEdit = findViewById(R.id.btn_edit);
        patientImage = findViewById(R.id.patient_image);
        nameText = findViewById(R.id.text_name);
        infoText = findViewById(R.id.text_info);
        painText = findViewById(R.id.text_pain);
        phoneText = findViewById(R.id.text_phone);
        patientInfoText = findViewById(R.id.text_patient_info);
        progressText = findViewById(R.id.progress_text);
        profileImage = findViewById(R.id.profile_image);
        progressBar = findViewById(R.id.progress);
        progressBar.setProgress(40);
        loadView = findViewById(R.id.load_view);
        bedView = findViewById(R.id.bedview);
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
