package com.example.ui.Nav.Live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ui.R;

import java.util.ArrayList;
import java.util.Collections;

public class LiveCmtAdapter extends BaseAdapter {
    public ArrayList<String> list = new ArrayList<String>();//댓글 저장
    private String num;//해당 게시글 번호

    public LiveCmtAdapter(String num){
        this.num = num;
    }

    public String getNum() {
        return  num;
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
            convertView = inflater.inflate(R.layout.live_cmt_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textView1 = (TextView) convertView.findViewById(R.id.live_comment);
        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        String result = list.get(position);
        textView1.setText(result);
        return convertView;
    }
    public void addItem(String result) {
        list.add(result);
        System.out.println(result);
    }

    public void clearItem(){
        list.clear();
    }

    public void reverse(){
        Collections.reverse(list);
    }
}
