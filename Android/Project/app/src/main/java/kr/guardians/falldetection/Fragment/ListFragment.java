package kr.guardians.falldetection.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import kr.guardians.falldetection.Activity.PatientInfoActivity;
import kr.guardians.falldetection.Activity.SearchActivity;
import kr.guardians.falldetection.Adapter.AllListRecyclerAdapter;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

/*
 TO-DO : 서버 연결 관리하기 -> Query 클래스 설계해서 각 Fragment에서 분할적으로 작동할 수 있도록 진행하자!!
 */
public class ListFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {


    private View currentView;
    private RecyclerView recyclerView;
    private ArrayList<Patient> patients;
    private RelativeLayout editSearch;
    private ImageButton btnMenu;
    private TextView textOrder;
    private SwipeRefreshLayout refreshLayout;

    private final String ORDER_PATIENT_NAME = "patientName";
    private final String ORDER_ROOM_CODE = "roomCode";
    private final String ORDER_WARNING_RATE = "warningRate";

    private String currentOrder;

    AllListRecyclerAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_list, container, false);
        bindViewID();
        patients = new ArrayList<>();
       // addDummyData();
        currentOrder = ORDER_PATIENT_NAME;
        refresh(currentOrder);
        btnMenu.setOnClickListener(this);
        editSearch.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(currentOrder);
                refreshLayout.setRefreshing(false);
            }
        });

        adapter = new AllListRecyclerAdapter(patients);
        RecyclerView.LayoutManager li = new GridLayoutManager(getContext(), 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(li);


        adapter.setOnItemClickListener(new OnItemClickListener<Patient>() {
            @Override
            public void onItemClick(View v, Patient patient) {
                PatientInfoActivity.start(getContext(),patient.getPatientSeq()+"");
            }
        });
        return currentView;
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
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {

                    }
                });
    }

    private void bindViewID() {
        editSearch = currentView.findViewById(R.id.search_button);
        btnMenu = currentView.findViewById(R.id.mode_menu_btn);
        textOrder = currentView.findViewById(R.id.mode_text);
        recyclerView = currentView.findViewById(R.id.list_recycler);
        refreshLayout = currentView.findViewById(R.id.swipe_refresh);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mode_menu_btn: {
                assert getContext() != null;
                Context wrapper = new ContextThemeWrapper(getContext(), R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, btnMenu);
                popupMenu.getMenuInflater().inflate(R.menu.list_order_menu, popupMenu.getMenu());

                //registering popup with OnMenuItemClickListener
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();//showing popup menu
                break;
            }
            case R.id.search_button: {
                Intent toSearch = new Intent(getContext(), SearchActivity.class);
                startActivity(toSearch);
                break;
            }
        }
    }

    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.itemTitle) return false;
        textOrder.setText(item.getTitle());
        switch (item.getItemId()) {
            case R.id.hangeul_order:
                currentOrder = ORDER_PATIENT_NAME;
                refresh(currentOrder);
                break;
            case R.id.warning_order:
                currentOrder = ORDER_WARNING_RATE;
                refresh(currentOrder);
                break;
            case R.id.room_order:
                currentOrder = ORDER_ROOM_CODE;
                refresh(currentOrder);
                break;
        }
        return true;
    }

    private void addDummyData() {
        patients.add(new Patient("정해민", "01098810664", "12"
                , "20000",
                "https://en.pimg.jp/008/051/595/1/8051595.jpg"
                , 0, 20, 36, 0+""
        ));
        patients.add(new Patient("유서린", "01098810664", "12"
                , "20000",
                "https://kr.pixtastock.com/illustration/8051691"
                , 0, 20, 20, 1+""
        ));
        patients.add(new Patient("엄태욱", "01098810664", "12"
                , "20000",
                "https://kr.pixtastock.com/illustration/8051691"
                , 0, 20, 30, 2+""
        ));
        patients.add(new Patient("박하나", "01098810664", "12"
                , "20000",
                "https://kr.pixtastock.com/illustration/8051671"
                , 0, 20, 90, 3+""
        ));
        patients.add(new Patient("고민혁", "01098810664", "12"
                , "20000",
                "https://kr.pixtastock.com/illustration/8051671"
                , 0, 20, 70, 4+""
        ));
        patients.add(new Patient("이유진", "01098810664", "12"
                , "20000",
                "https://kr.pixtastock.com/illustration/8051666"
                , 0, 20, 50, 5+""
        ));
    }
}
