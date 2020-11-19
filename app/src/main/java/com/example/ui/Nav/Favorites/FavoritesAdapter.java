package com.example.ui.Nav.Favorites;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ui.R;

import java.util.ArrayList;

public class FavoritesAdapter extends BaseAdapter {
    public ArrayList<FavoritesData> list = new ArrayList<FavoritesData>();

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
            convertView = inflater.inflate(R.layout.favorites_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textView1 = (TextView) convertView.findViewById(R.id.nickname);
        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        FavoritesData result = list.get(position);

        textView1.setText(result.getNickname());
        return convertView;
    }
    public void addItem(FavoritesData result) {
        list.add(result);
    }

    public void clearItem(){
        list.clear();
    }
}
