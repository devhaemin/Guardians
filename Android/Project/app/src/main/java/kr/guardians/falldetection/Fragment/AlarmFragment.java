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
import kr.guardians.falldetection.Adapter.AlarmRecyclerAdapter;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Alarm;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Random;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Alarm> alarms;
    Intent toInfoActivity;
    SwipeRefreshLayout refreshLayout;
    GlobalApplication application;
    AlarmRecyclerAdapter alarmRecyclerAdapter;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        application = (GlobalApplication) getActivity().getApplicationContext();
        recyclerView = v.findViewById(R.id.alarm_recycler);
        refreshLayout = v.findViewById(R.id.swipe_refresh);
        alarms = new ArrayList<>();
        refresh();

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) lm).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        alarmRecyclerAdapter = new AlarmRecyclerAdapter(alarms);

        recyclerView.setAdapter(alarmRecyclerAdapter);
        toInfoActivity = new Intent(container.getContext(), PatientInfoActivity.class);

        alarmRecyclerAdapter.setOnItemClickListener(new OnItemClickListener<Alarm>() {
            @Override
            public void onItemClick(View v, Alarm alarm) {
                toInfoActivity.putExtra("patientCode",alarm.getPatientSeq());
                startActivity(toInfoActivity);

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        return v;
    }
    private void refresh(){
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.getAlarmList(application.getAccessToken().getTokenString()).enqueue(new Callback<ArrayList<Alarm>>() {
            @Override
            public void onResponse(Call<ArrayList<Alarm>> call, Response<ArrayList<Alarm>> response) {
                if(response.body() != null){
                    alarms.clear();
                    alarms.addAll(response.body());
                    alarms.get(0).setProfileImageUrl("https://drive.google.com/uc?authuser=0&id=1XJvaY0EQ6LJOFp3aCcKQJ4G2TUGnMaKe&export=download");
                    alarms.get(1).setProfileImageUrl("https://drive.google.com/uc?authuser=0&id=1jjGExihHtrIrUwB8O3cVXGiu_rWQNcIv&export=download");
                    alarms.get(3).setProfileImageUrl("https://drive.google.com/uc?authuser=0&id=1jjGExihHtrIrUwB8O3cVXGiu_rWQNcIv&export=download");
                    alarms.get(2).setProfileImageUrl("https://drive.google.com/uc?authuser=0&id=1ZVyFrRXNO-ooK7HKljtFxQ8CHqofd559&export=download");
                    for(int i = 0; i < alarms.size(); i++){
                        alarms.get(i).setTime(System.currentTimeMillis()-i*4-3);
                    }
                    alarmRecyclerAdapter.notifyDataSetChanged();
                }
                refreshLayout.setRefreshing(false);
                Log.e(TAG,"Code : "+response.code()+" Message : "+response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Alarm>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.e(TAG,"Error",t);
            }
        });
    }

}
