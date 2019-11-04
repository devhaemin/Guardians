package kr.guardians.falldetection.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kr.guardians.falldetection.Adapter.RecentRecyclerAdapter;
import kr.guardians.falldetection.POJO.SearchItem;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentSearchFragment extends Fragment {

    View currentView;
    TextView textBlank;
    RecyclerView recyclerView;
    ArrayList<SearchItem> searchItems;

    public RecentSearchFragment() {
        // Required empty public constructor
    }

    public void setSearchItems(ArrayList<SearchItem> searchItems) {
        this.searchItems = searchItems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_recent_search,container,false);
        recyclerView = currentView.findViewById(R.id.recent_recycler);
        textBlank = currentView.findViewById(R.id.text_blank);

        if(searchItems == null){
            textBlank.setVisibility(View.VISIBLE);
        }else{
            textBlank.setVisibility(View.VISIBLE);
            RecentRecyclerAdapter recentAdapter = new RecentRecyclerAdapter(getContext(),searchItems,getActivity());
            recyclerView.setAdapter(recentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        }

        return currentView;
    }


}
