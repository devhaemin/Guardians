package kr.guardians.falldetection.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kr.guardians.falldetection.CustomWidget.CircleProgressBar;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class AllListRecyclerAdapter extends RecyclerView.Adapter<AllListRecyclerAdapter.ListViewHolder> {

    ArrayList<Patient> patients;
    OnItemClickListener<Patient> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Patient> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AllListRecyclerAdapter(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.warning_progress,viewGroup,false);
        ListViewHolder viewHolder = new ListViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        final Patient patient = patients.get(i);

        String infoText = patient.getRoomCode()+"호실 "+patient.getPatientName()+"님";
        listViewHolder.circleProgressBar.setProgress((float)patient.getWarningRate());
        listViewHolder.progressInfoText.setText(infoText);
        listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,patient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        CircleProgressBar circleProgressBar;
        TextView progressInfoText;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            circleProgressBar = itemView.findViewById(R.id.progress);
            progressInfoText = itemView.findViewById(R.id.progress_info_text);
        }
    }
}
