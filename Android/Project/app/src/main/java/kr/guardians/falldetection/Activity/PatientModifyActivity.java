package kr.guardians.falldetection.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import kr.guardians.falldetection.CustomWidget.CustomAppbar;
import kr.guardians.falldetection.CustomWidget.GuardiansBedView;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.Bed;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class PatientModifyActivity extends AppCompatActivity {

    String patientSeq;
    GuardiansBedView bedView;
    GlobalApplication application;
    Patient patient;

    CustomAppbar appbar;
    ImageView profileImage;
    Button completeBtn;
    EditText editName,editPhone,editDetail,editRoom;
    boolean isSuccess;

    public static void start(Context context, String patientSeq) {
        Intent starter = new Intent(context, PatientModifyActivity.class);
        starter.putExtra("patientSeq",patientSeq);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_modify);
        application = (GlobalApplication)getApplicationContext();

        Intent fromOutside = getIntent();
        patientSeq = fromOutside.getStringExtra("patientSeq");



        bindViewById();
        changeUIForPatient();
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
                isSuccess = true;
                ArrayList<Bed> beds = bedView.getBeds();
                for(Bed bed : beds){
                    retrofitInterface.editBedInfo(bed.getPatientSeq(),bed.getBedX()+"",bed.getBedY()+"").enqueue(new Callback<Bed>() {
                        @Override
                        public void onResponse(Call<Bed> call, Response<Bed> response) {

                        }

                        @Override
                        public void onFailure(Call<Bed> call, Throwable t) {
                            isSuccess = false;
                        }
                    });
                }
                if(isSuccess){
                    Toast.makeText(getBaseContext(),"정보수정이 성공적으로 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getBaseContext(),"서버연결에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindViewById() {
        completeBtn = findViewById(R.id.complete_btn);
        profileImage = findViewById(R.id.profile_image);
        appbar = findViewById(R.id.appbar);
        bedView = findViewById(R.id.bedview);
        editName = findViewById(R.id.edit_name);
        editDetail = findViewById(R.id.edit_detail);
        editPhone = findViewById(R.id.edit_phone);
        editRoom = findViewById(R.id.edit_room);

        appbar.setBackDefault(this);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
    }

    private void changeUIForPatient(){
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.getPatientInfo(application.getAccessToken().getTokenString(),patientSeq).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if(response.body() != null){
                    patient = response.body();
                    bedView.setBeds(patient);
                    editDetail.setText(patient.getPainInfo());
                    editName.setText(patient.getPatientName());
                    editPhone.setText(patient.getPhone());
                    editRoom.setText(patient.getRoomCode());
                    Glide.with(getBaseContext())
                            .load(patient.getProfileImageUrl())
                            .circleCrop()
                            .into(profileImage);

                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });
    }
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,400);

    }
}
