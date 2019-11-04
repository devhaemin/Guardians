package kr.guardians.falldetection.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import kr.guardians.falldetection.Activity.PatientModifyActivity;
import kr.guardians.falldetection.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    Button modifyPatient, emailSubmit, showPrivateTerm, showGpsTerm;

    Switch myAlarmToggle, allAlarmToggle;

    public ModifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_modify, container, false);
        modifyPatient = v.findViewById(R.id.patient_modify);
        emailSubmit = v.findViewById(R.id.email_submit);
        showPrivateTerm = v.findViewById(R.id.show_private_term);
        showGpsTerm = v.findViewById(R.id.show_gps_term);

        myAlarmToggle = v.findViewById(R.id.my_alarm_toggle);
        allAlarmToggle = v.findViewById(R.id.all_alarm_toggle);

        modifyPatient.setOnClickListener(this);
        emailSubmit.setOnClickListener(this);
        showPrivateTerm.setOnClickListener(this);
        showGpsTerm.setOnClickListener(this);

        myAlarmToggle.setOnCheckedChangeListener(this);
        allAlarmToggle.setOnCheckedChangeListener(this);

        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Toast.makeText(getContext(),"현재 준비중입니다.",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.patient_modify:
                i = new Intent(getActivity(), PatientModifyActivity.class);
                startActivity(i);
                break;
            case R.id.email_submit:

                i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"haemin001212@naver.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,"[가디언즈 앱] 제목 :");
                i.putExtra(Intent.EXTRA_TEXT,"앱 버전:\n오류 내용:\n사용하시는 요양원:");
                i.setType("message/rfc822");
                startActivity(i);
                break;
            case R.id.show_private_term:
                Toast.makeText(getContext(),"현재 준비중입니다.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_gps_term:
                Toast.makeText(getContext(),"현재 준비중입니다.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_version:
                Toast.makeText(getContext(),"현재 최신 버전입니다.",Toast.LENGTH_SHORT).show();
        }
    }
}
