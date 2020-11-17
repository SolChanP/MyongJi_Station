package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line3 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line3[] = new Button[11];
    private Integer[] Rid_button = {
            R.id.station207_3, R.id.station301, R.id.station302, R.id.station303_3, R.id.station304, R.id.station123_3, R.id.station305, R.id.station306,
            R.id.station307_3, R.id.station308, R.id.station107_3
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line3);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line3_cur);
        next = findViewById(R.id.line3_next);
        rem = findViewById(R.id.line3_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line3.length; i++){
            if(line3[i].getText().toString().equals(result.getCur_station())){
                line3[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line3.length; i++){
            line3[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}