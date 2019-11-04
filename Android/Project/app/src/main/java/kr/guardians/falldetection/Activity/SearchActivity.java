package kr.guardians.falldetection.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.guardians.falldetection.Fragment.RecentSearchFragment;
import kr.guardians.falldetection.Fragment.SearchFragment;
import kr.guardians.falldetection.POJO.SearchItem;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private EditText editSearch;
    private TextView btnCancel;
    private ArrayList<SearchItem> searchItems;
    private RecentSearchFragment recentFragment;
    private SearchFragment searchFragment;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        doPreferenceProcess();

        recentFragment = new RecentSearchFragment();
        recentFragment.setSearchItems(searchItems);

        searchFragment = new SearchFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.search_fragment_container, recentFragment).commit();

        bindViewId();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();

        if (isFirst) {

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().remove(recentFragment).commit();
            fm.beginTransaction().add(R.id.search_fragment_container, searchFragment).commit();
            isFirst = false;
        }
        searchFragment.refresh(str);


    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void bindViewId() {
        editSearch = findViewById(R.id.search_edit);
        btnCancel = findViewById(R.id.btn_cancel);
        editSearch.requestFocus();
        editSearch.addTextChangedListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void doPreferenceProcess() {

        SharedPreferences pref = getSharedPreferences("search", MODE_PRIVATE);
        String json = pref.getString("searchItems", "");
        Gson gson = new Gson();
        searchItems = gson.fromJson(json, new TypeToken<ArrayList<SearchItem>>() {
        }.getType());

    }
}
