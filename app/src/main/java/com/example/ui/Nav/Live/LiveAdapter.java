package com.example.ui.Nav.Live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ui.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LiveAdapter extends BaseAdapter {
    public ArrayList<LiveData> list = new ArrayList<LiveData>();//게시글 저장
    private String line;//현재 라인

    public LiveAdapter(String line){
        this.line = line;
    }

    public String getLine() {
        return line;
    }


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.live_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textView1 = (TextView) convertView.findViewById(R.id.title);
        TextView textView2 = (TextView) convertView.findViewById(R.id.time);
        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        LiveData result = list.get(position);

        textView1.setText(result.getTitle());
        textView2.setText(result.getTime());
        return convertView;
    }
    public void addItem(LiveData result) {
        //리스트 맨 앞에 데이터 추가
        list.add(0, result);
    }

    public void clearItem(){
        list.clear();
    }

    public void reverse(){
        Collections.reverse(list);
    }
}
