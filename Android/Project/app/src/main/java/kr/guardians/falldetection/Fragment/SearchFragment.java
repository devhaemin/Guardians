package kr.guardians.falldetection.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kr.guardians.falldetection.Adapter.SearchAdapter;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    RecyclerView recyclerView;
    TextView textBlank;
    SearchAdapter adapter;
    ArrayList<Patient> searchList;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = v.findViewById(R.id.search_recycler);
        textBlank = v.findViewById(R.id.text_blank);
        searchList = new ArrayList<>();
        adapter = new SearchAdapter(searchList, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void refresh(String s) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        retrofitInterface.getSearchList(s).enqueue(new Callback<ArrayList<Patient>>() {
            @Override
            public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    textBlank.setVisibility(View.INVISIBLE);
                    searchList.clear();
                    searchList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    textBlank.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {
                textBlank.setVisibility(View.VISIBLE);
                Log.e(TAG, "Error", t);

            }
        });
    }

}
