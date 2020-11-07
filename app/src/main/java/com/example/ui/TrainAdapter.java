package com.example.ui;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.CustomViewHolder> {

    private ArrayList<TrainData> mList = null;
    private Activity context = null;


    public TrainAdapter(Activity context, ArrayList<TrainData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView line;
        protected TextView cur_station;
        protected TextView rem_next_time;


        public CustomViewHolder(View view) {
            super(view);
            this.line = (TextView) view.findViewById(R.id.textView_list_line);
            this.cur_station = (TextView) view.findViewById(R.id.textView_list_cur_station);
            this.rem_next_time = (TextView) view.findViewById(R.id.textView_list_rem_next_time);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.line.setText(mList.get(position).getLine());
        viewholder.cur_station.setText(mList.get(position).getCur_station());
        viewholder.rem_next_time.setText(mList.get(position).getRem_next_time());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}