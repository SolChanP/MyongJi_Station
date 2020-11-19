package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line9 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line9[] = new Button[11];
    private Integer[] Rid_button = {
            R.id.station112_9, R.id.station901, R.id.station406_9, R.id.station605_9, R.id.station902, R.id.station119_9, R.id.station903, R.id.station702_9,
            R.id.station904, R.id.station621_9, R.id.station211_9
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line9);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line9_cur);
        next = findViewById(R.id.line9_next);
        rem = findViewById(R.id.line9_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line9.length; i++){
            if(line9[i].getText().toString().equals(result.getCur_station())){
                line9[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line9.length; i++){
            line9[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}