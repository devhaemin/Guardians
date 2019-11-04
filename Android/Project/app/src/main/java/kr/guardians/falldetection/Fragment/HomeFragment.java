package kr.guardians.falldetection.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.guardians.falldetection.Activity.PatientInfoActivity;
import kr.guardians.falldetection.Adapter.HomeRecyclerAdapter;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Patient> patients;
    Intent toInfoActivity;
    private SwipeRefreshLayout refreshLayout;
    String TAG = "HomeFragment";

    HomeRecyclerAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.home_recycler);
        refreshLayout = v.findViewById(R.id.swipe_refresh);

        patients = new ArrayList<>();
        //addDummyData();
        refresh("warningRate");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh("warningRate");
                refreshLayout.setRefreshing(false);
            }
        });

        adapter = new HomeRecyclerAdapter(patients,getContext());
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) lm).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        toInfoActivity = new Intent(container.getContext(), PatientInfoActivity.class);

        adapter.setOnItemClickListener(new OnItemClickListener<Patient>() {
            @Override
            public void onItemClick(View v, Patient patient) {
                toInfoActivity.putExtra("patientCode",patient.getPatientSeq());
                startActivity(toInfoActivity);

            }
        });
        return v;
    }

    private void refresh(String order){
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.getPatientList(((GlobalApplication)getActivity().getApplicationContext()).getAccessToken().getTokenString(),order)
                .enqueue(new Callback<ArrayList<Patient>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                        if(response.body() != null){
                            patients.clear();
                            patients.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                        Log.e(TAG,"Code: "+response.code()+" Message: "+response.message());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {

                        Log.e(TAG,"Error",t);
                    }
                });
    }


}
