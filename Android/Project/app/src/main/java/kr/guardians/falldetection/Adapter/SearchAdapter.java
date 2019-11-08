package kr.guardians.falldetection.Adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.guardians.falldetection.Activity.PatientInfoActivity;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.POJO.SearchItem;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    ArrayList<Patient> patients;
    Activity activity;

    public SearchAdapter(ArrayList<Patient> patients, Activity activity) {
        this.patients = patients;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.recycler_search,viewGroup,false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder  viewHolder, int i) {
        final Patient patient = patients.get(i);
        viewHolder.nameText.setText(patient.getPatientName()+"님");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientInfoActivity.start(activity,patient.getPatientSeq());
                activity.finish();
                doPreferenceProcess(patient);
            }
        });
    }
    private void doPreferenceProcess(Patient patient) {

        SharedPreferences pref = activity.getSharedPreferences("search", MODE_PRIVATE);
        String json = pref.getString("searchItems", "");
        Gson gson = new Gson();
        ArrayList<SearchItem> searchItems = new ArrayList<>();
        ArrayList<SearchItem> prefs = gson.fromJson(json, new TypeToken<ArrayList<SearchItem>>() {}.getType());
        if(prefs != null)
        searchItems.addAll(prefs);
        searchItems.add(new SearchItem(patient.getPatientName(),System.currentTimeMillis(),patient.getPatientSeq()));
        String toPref = gson.toJson(searchItems);
        pref.edit().putString("searchItems",toPref).apply();
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.search_text);
        }
    }
}
