package kr.guardians.falldetection.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import kr.guardians.falldetection.Activity.PatientInfoActivity;
import kr.guardians.falldetection.Adapter.AllListRecyclerAdapter;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    Intent toInfoActivity;
    RecyclerView recyclerView;
    ArrayList<Patient> patients;

    EditText editSearch;
    ImageButton btnMenu;
    TextView textOrder;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        editSearch = view.findViewById(R.id.search_button);
        btnMenu = view.findViewById(R.id.mode_menu_btn);
        textOrder = view.findViewById(R.id.mode_text);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),btnMenu);
                popupMenu.getMenuInflater().inflate(R.menu.list_order_menu, popupMenu.getMenu());

                //registering popup with OnMenuItemClickListener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.itemTitle) return false;
                        textOrder.setText(item.getTitle());
                        switch (item.getItemId()){
                            case R.id.hangeul_order:
                                break;
                            case R.id.warning_order:
                                break;
                            case R.id.room_order:
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();//showing popup menu
            }
        });

        recyclerView = view.findViewById(R.id.list_recycler);
        patients = new ArrayList<>();
        addDummyData();

        AllListRecyclerAdapter adapter = new AllListRecyclerAdapter(patients);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager li = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(li);

        toInfoActivity = new Intent(container.getContext(), PatientInfoActivity.class);

        adapter.setOnItemClickListener(new OnItemClickListener<Patient>() {
            @Override
            public void onItemClick(View v, Patient patient) {
                toInfoActivity.putExtra("patientCode",patient.getPatientCode());
                startActivity(toInfoActivity);

            }
        });
        return view;
    }

    private void addDummyData() {
        patients.add(new Patient("정해민", "01098810664", "12"
                , "20000",
                "http://blogfiles.naver.net/data44/2009/2/24/146/c2fa_color_05_skyloveshee.gif"
                , 0, 20, 36, 0
        ));
        patients.add(new Patient("유서린", "01098810664", "12"
                , "20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                , 0, 20, 20, 1
        ));
        patients.add(new Patient("엄태욱", "01098810664", "12"
                , "20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                , 0, 20, 30, 2
        ));
        patients.add(new Patient("박하나", "01098810664", "12"
                , "20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                , 0, 20, 90, 3
        ));
        patients.add(new Patient("고민혁", "01098810664", "12"
                , "20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                , 0, 20, 70, 4
        ));
        patients.add(new Patient("이유진", "01098810664", "12"
                , "20000",
                "http://blogfiles.naver.net/20120420_290/kimminasss_13349010348975WBa9_JPEG/%BC%D2%B3%E0%B0%A8%BC%BA%C0%CC%B9%CC%C1%F616.jpg"
                , 0, 20, 50, 5
        ));
    }
}
