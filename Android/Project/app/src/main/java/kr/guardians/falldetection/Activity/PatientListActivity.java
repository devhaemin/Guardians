package kr.guardians.falldetection.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    ArrayList<Patient> patients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        patients = new ArrayList<>();

        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.getPatientList(((GlobalApplication)getApplicationContext()).getAccessToken().getTokenString(),"patientName")
                .enqueue(new Callback<ArrayList<Patient>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                        if(response.body() != null){
                            patients = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {

                    }
                });
    }
}
