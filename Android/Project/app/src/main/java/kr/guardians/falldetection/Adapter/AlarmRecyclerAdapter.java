package kr.guardians.falldetection.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import kr.guardians.falldetection.CustomWidget.CircleProgressBar;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Alarm;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class AlarmRecyclerAdapter extends RecyclerView.Adapter<AlarmRecyclerAdapter.AlarmViewHolder> {

    ArrayList<Alarm> alarms;
    ViewGroup parent;
    OnItemClickListener<Alarm> onItemClickListener;

    public AlarmRecyclerAdapter(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }

    public void setOnItemClickListener(OnItemClickListener<Alarm> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        parent = viewGroup;
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_alarm, viewGroup, false);
        AlarmViewHolder alarmViewHolder = new AlarmViewHolder(itemView);

        return alarmViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder alarmViewHolder, int i) {
        final Alarm alarm = alarms.get(i);

        if(alarm.isChecked())
        alarmViewHolder.checkedToggle.setVisibility(View.VISIBLE);
        else alarmViewHolder.checkedToggle.setVisibility(View.GONE);

        alarmViewHolder.progressBar.setProgress((float)alarm.getWarningRate());
        alarmViewHolder.dateTime.setText(alarm.getTime());
        alarmViewHolder.name.setText(alarm.getPatientName()+"님");

        alarmViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,alarm);
            }
        });

        Glide.with(parent.getContext())
                .load(alarm.getProfileImageUrl())
                .thumbnail(0.1f)
                .placeholder(R.drawable.thumbnail)
                .apply(RequestOptions.circleCropTransform())
                .into(alarmViewHolder.profileImage);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView name;
        TextView dateTime;
        CircleProgressBar progressBar;
        ImageView checkedToggle;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profile_image_view);
            name = itemView.findViewById(R.id.name_text);
            dateTime = itemView.findViewById(R.id.time_text);
            progressBar = itemView.findViewById(R.id.progress);
            checkedToggle = itemView.findViewById(R.id.toggle_check);
        }
    }
}
