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
import kr.guardians.falldetection.Adapter.HomeRecyclerAdapter;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Patient> patients;
    Intent toInfoActivity;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.home_recycler);
        patients = new ArrayList<>();
        addDummyData();

        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(patients);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) lm).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        toInfoActivity = new Intent(container.getContext(), PatientInfoActivity.class);

        adapter.setOnItemClickListener(new OnItemClickListener<Patient>() {
            @Override
            public void onItemClick(View v, Patient patient) {
                toInfoActivity.putExtra("patientCode",patient.getPatientCode());
                startActivity(toInfoActivity);

            }
        });
        return v;
    }
    private void addDummyData(){
        patients.add(new Patient("정해민","01098810664","12"
                ,"20000",
                "http://blogfiles.naver.net/data44/2009/2/24/146/c2fa_color_05_skyloveshee.gif"
                ,0,20,10,0
        ));
        patients.add(new Patient("유서린","01098810664","12"
                ,"20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                ,0,20,20,1
        ));
        patients.add(new Patient("엄태욱","01098810664","12"
                ,"20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                ,0,20,40,2
        ));
        patients.add(new Patient("박하나","01098810664","12"
                ,"20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                ,0,20,60,3
        ));
        patients.add(new Patient("고민혁","01098810664","12"
                ,"20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                ,0,20,80,4
        ));
        patients.add(new Patient("이유진","01098810664","12"
                ,"20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                ,0,20,100,5
        ));
    }


}
