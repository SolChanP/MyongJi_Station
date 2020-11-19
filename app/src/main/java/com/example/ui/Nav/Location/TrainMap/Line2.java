package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line2 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line2[] = new Button[18];
    private Integer[] Rid_button = {
            R.id.station101_2, R.id.station201, R.id.station202_2, R.id.station203, R.id.station204, R.id.station205, R.id.station206, R.id.station207_2,
            R.id.station208, R.id.station209_2, R.id.station210, R.id.station211_2, R.id.station212, R.id.station213, R.id.station214_2, R.id.station215
            ,R.id.station216_2, R.id.station217
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line2);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line2_cur);
        next = findViewById(R.id.line2_next);
        rem = findViewById(R.id.line2_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line2.length; i++){
            if(line2[i].getText().toString().equals(result.getCur_station())){
                line2[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line2.length; i++){
            line2[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}