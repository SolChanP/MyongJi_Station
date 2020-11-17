package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line7 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line7[] = new Button[13];
    private Integer[] Rid_button = {
            R.id.station202, R.id.station303, R.id.station503, R.id.station601, R.id.station701, R.id.station702, R.id.station703, R.id.station704,
            R.id.station705, R.id.station706, R.id.station416, R.id.station707, R.id.station614
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line7);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line7_cur);
        next = findViewById(R.id.line7_next);
        rem = findViewById(R.id.line7_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line7.length; i++){
            if(line7[i].getText().toString().equals(result.getCur_station())){
                line7[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line7.length; i++){
            line7[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}
