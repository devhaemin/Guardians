package kr.guardians.falldetection.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kr.guardians.falldetection.Activity.PatientInfoActivity;
import kr.guardians.falldetection.POJO.SearchItem;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

public class RecentRecyclerAdapter extends RecyclerView.Adapter<RecentRecyclerAdapter.RecentViewHolder>{

    Context context;
    ArrayList<SearchItem> searchItems;
    Activity activity;


    public RecentRecyclerAdapter(Context context, ArrayList<SearchItem> searchItems, Activity callingActivity) {
        this.context = context;
        this.searchItems = searchItems;
        this.activity = callingActivity;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_recent,viewGroup,false);
        RecentViewHolder viewHolder = new RecentViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder recentViewHolder, int i) {
        final SearchItem item = searchItems.get(i);
        recentViewHolder.textView.setText(item.getText());
        recentViewHolder.dateView.setText(DateUtils.getRelativeTimeSpanString(context,item.getTimeSec()));
        recentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientInfoActivity.start(context,item.getPatientCode());
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    class RecentViewHolder extends RecyclerView.ViewHolder{

        TextView textView,dateView;
        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.search_text);
            dateView = itemView.findViewById(R.id.search_date);

        }
    }
}
