package kr.guardians.falldetection.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kr.guardians.falldetection.Activity.PatientInfoActivity;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

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
            }
        });
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
