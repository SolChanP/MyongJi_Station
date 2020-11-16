package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;



public class Line8 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line8[] = new Button[6];
    private Integer[] Rid_button = {
            //8호선, 6
            R.id.station801, R.id.station802, R.id.station803, R.id.station804, R.id.station805, R.id.station806
    };
    private  TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line8);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line8_cur);
        next = findViewById(R.id.line8_next);
        rem = findViewById(R.id.line8_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line8.length; i++){
            if(line8[i].getText().toString().equals(result.getCur_station())){
                line8[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line8.length; i++){
            line8[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}
