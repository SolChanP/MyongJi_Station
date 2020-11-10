package com.example.ui.Nav.Live;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ui.R;

public class LiveResult extends AppCompatActivity {
    private LiveData result;
    private TextView line, title, dir, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_result);

        line = (TextView) findViewById(R.id.live_line);
        title = (TextView) findViewById(R.id.live_title);
        dir = (TextView) findViewById(R.id.live_dir);
        data = (TextView) findViewById(R.id.live_data);


        Intent intent = getIntent();
        result = (LiveData) intent.getSerializableExtra("result");

        this.setData(result);
    }
    public void setData(LiveData result){

        line.setText(result.getLine().toString() + "호선 게시글");
        title.setText(result.getTitle().toString());
        dir.setText("열차정보 : " + result.getDir().toString());
        data.setText(result.getData().toString());

    }
}
