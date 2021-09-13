package com.cricket.ipl2021.iplteamclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricket.ipl2021.iplteamclone.R;
import com.cricket.ipl2021.iplteamclone.Utils.Constants;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private static final String TAG = "data-->";
    Context context;

    // RVOnClickListener rvOnClickListener;

    public TeamAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_team, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_name.setText(Constants.teamNameArray[position]);
        Glide.with(context).load(Constants.teamLogoArray[position]).placeholder(context.getResources().getDrawable(R.drawable.loading_icon)).into(holder.iv_logo);
        holder.rl_back.setBackgroundColor(context.getResources().getColor(Constants.teamColorArray[position]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  rvOnClickListener.onClick(position);
            }
        });

    }

//    public void SetOnItemClickListener(RVOnClickListener rvOnClickListener) {
//        this.rvOnClickListener = rvOnClickListener;
//    }

    @Override
    public int getItemCount() {
        return Constants.teamNameArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_back;
        ImageView iv_logo;
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_back = (RelativeLayout) itemView.findViewById(R.id.rl_back);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }
}
