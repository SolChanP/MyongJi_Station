package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line1 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line1[] = new Button[23];
    private Integer[] Rid_button = {
            R.id.station101, R.id.station102, R.id.station103, R.id.station104, R.id.station105, R.id.station106, R.id.station107, R.id.station108,
            R.id.station109, R.id.station110, R.id.station111, R.id.station112, R.id.station113, R.id.station114, R.id.station115, R.id.station116
            ,R.id.station117, R.id.station118, R.id.station119, R.id.station120, R.id.station121, R.id.station122, R.id.station123
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line1);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line1_cur);
        next = findViewById(R.id.line1_next);
        rem = findViewById(R.id.line1_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line1.length; i++){
            if(line1[i].getText().toString().equals(result.getCur_station())){
                line1[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line1.length; i++){
            line1[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}
