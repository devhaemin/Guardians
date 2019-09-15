package kr.guardians.falldetection.Adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import kr.guardians.falldetection.OnItemClickListener;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    ArrayList<Patient> patients;
    ViewGroup parent;
    OnItemClickListener<Patient> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Patient> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeRecyclerAdapter(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.recycler_home,viewGroup,false);
        ViewHolder myViewHolder = new ViewHolder(v);
        parent = viewGroup;

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,int i) {

        final Patient patient = patients.get(i);
        String infoText = patient.getRoomCode()+"호실 "+patient.getName()+"님";
        viewHolder.recyclerText.setText(infoText);
        viewHolder.warningText.setText(patient.getWarningRate()+"%");
        Glide.with(parent.getContext())
                .load(patient.getImageUrl())
                .thumbnail(0.1f)
                .placeholder(R.drawable.thumbnail)
                .into(viewHolder.homeImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null)
                onItemClickListener.onItemClick(v,patient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView homeImageView;
        TextView recyclerText;
        TextView warningText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeImageView = itemView.findViewById(R.id.home_image_view);
            GradientDrawable drawable =
                    (GradientDrawable) itemView.getContext().getDrawable(R.drawable.a_bg_rounded_imageview);
            homeImageView.setBackground(drawable);
            homeImageView.setClipToOutline(true);

            recyclerText = itemView.findViewById(R.id.recycler_info_text);
            warningText = itemView.findViewById(R.id.warning_rate);


        }
    }


}
