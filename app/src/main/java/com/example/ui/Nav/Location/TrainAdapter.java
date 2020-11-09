package com.example.ui.Nav.Location;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ui.Nav.Favorites.FavoritesData;
import com.example.ui.R;

import java.util.ArrayList;


public class TrainAdapter extends BaseAdapter {
    public ArrayList<TrainData> list = new ArrayList<TrainData>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textView1 = (TextView) convertView.findViewById(R.id.list_line);
        TextView textView2 = (TextView) convertView.findViewById(R.id.list_cur_station);
        TextView textView3 = (TextView) convertView.findViewById(R.id.list_rem_next_time);
        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        TrainData result = list.get(position);

        textView1.setText(result.getLine());
        textView2.setText(result.getCur_station());
        textView3.setText(result.getRem_next_time());
        result = null;

        return convertView;
    }
    public void addItem(TrainData result) {
        list.add(result);
    }

    public void clearItem(){
        list.clear();
    }
}