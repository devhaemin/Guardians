package kr.guardians.falldetection.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.guardians.falldetection.Activity.PatientInfoActivity;
import kr.guardians.falldetection.Adapter.AlarmRecyclerAdapter;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Alarm;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Alarm> alarms;
    Intent toInfoActivity;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        recyclerView = v.findViewById(R.id.alarm_recycler);
        alarms = createDummyData();

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) lm).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        AlarmRecyclerAdapter alarmRecyclerAdapter = new AlarmRecyclerAdapter(alarms);

        recyclerView.setAdapter(alarmRecyclerAdapter);
        toInfoActivity = new Intent(container.getContext(), PatientInfoActivity.class);

        alarmRecyclerAdapter.setOnItemClickListener(new OnItemClickListener<Alarm>() {
            @Override
            public void onItemClick(View v, Alarm alarm) {
                toInfoActivity.putExtra("patientCode",alarm.getPatientCode());
                startActivity(toInfoActivity);

            }
        });

        return v;
    }
    ArrayList<Alarm> createDummyData(){
        ArrayList<Alarm> alarms = new ArrayList<>();

        alarms.add(new Alarm("정해민",System.currentTimeMillis() - 6000,30,false,""));
        alarms.add(new Alarm("박하나",1567360161376L,60,false,""));
        alarms.add(new Alarm("윤재웅",1567360161076L,80,false,""));
        alarms.add(new Alarm("고민혁",1567360161066L,100,false,""));
        alarms.add(new Alarm("저뜨또",1567360161006L,30,false,""));
        alarms.add(new Alarm("엄태욱",1567360161004L,50,false,""));
        alarms.add(new Alarm("이유진",1567360161000L,40,false,""));
        alarms.add(new Alarm("한지형",1567360920779L,30,false,""));
        alarms.add(new Alarm("신창화",1567360920779L,30,false,""));

        return alarms;
    }

}
