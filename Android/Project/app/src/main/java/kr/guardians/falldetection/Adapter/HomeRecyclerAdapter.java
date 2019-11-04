package kr.guardians.falldetection.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    ArrayList<Patient> patients;
    ViewGroup parent;
    OnItemClickListener<Patient> onItemClickListener;
    Context context;

    public HomeRecyclerAdapter(ArrayList<Patient> patients,Context context) {
        this.patients = patients;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener<Patient> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.recycler_home, viewGroup, false);
        ViewHolder myViewHolder = new ViewHolder(v);
        parent = viewGroup;

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Patient patient = patients.get(i);
        String infoText = patient.getRoomCode() + "호실 " + patient.getPatientName() + "님";
        viewHolder.recyclerText.setText(infoText);
        viewHolder.warningText.setText((int)patient.getWarningRate() + "%");
        Glide.with(parent.getContext())
                .load(getDrawableId(patient.getBodyStatus()))
                .thumbnail(0.1f)
                .placeholder(R.drawable.status0)
                .into(viewHolder.homeImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(v, patient);
            }
        });
        viewHolder.progressBar.setProgress((int)patient.getWarningRate());
        viewHolder.progressBar.setProgressTintList(ColorStateList.valueOf(context.getColor(getColor((int)patient.getWarningRate()))));
    }
    public int getColor(int progress) {
        if(progress <= 30){
            return R.color.blueProgress;
        }else if(progress <= 50){
            return R.color.yellowProgress;
        }else{
            return R.color.redProgress;
        }
    }

    int getDrawableId(int status) {
        switch (status) {
            case 0:
                return R.drawable.status0;
            case 1:
                return R.drawable.status1;
            case 2:
                return R.drawable.status2;
            case 3:
                return R.drawable.status3;
            case 4:
                return R.drawable.status4;
            case 5:
                return R.drawable.status5;
            case 6:
                return R.drawable.status6;
            case 7:
                return R.drawable.status7;
            case 8:
                return R.drawable.status8;
            case 9:
                return R.drawable.status9;
            case 10:
                return R.drawable.status10;
            case 11:
                return R.drawable.status11;
            default:
                return R.drawable.status0;
        }
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeImageView;
        TextView recyclerText;
        TextView warningText;
        ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeImageView = itemView.findViewById(R.id.home_image_view);
            GradientDrawable drawable =
                    (GradientDrawable) itemView.getContext().getDrawable(R.drawable.a_bg_rounded_imageview);
            homeImageView.setBackground(drawable);
            homeImageView.setClipToOutline(true);

            recyclerText = itemView.findViewById(R.id.recycler_info_text);
            warningText = itemView.findViewById(R.id.warning_rate);

            progressBar = itemView.findViewById(R.id.progressBar);


        }
    }


}
