package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line6 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line6[] = new Button[26];
    private Integer[] Rid_button = {
            R.id.station601_6, R.id.station602, R.id.station121_6, R.id.station603, R.id.station604, R.id.station605_6, R.id.station606, R.id.station116_6,
            R.id.station607, R.id.station608_6, R.id.station609, R.id.station412_6, R.id.station610, R.id.station611, R.id.station612, R.id.station613,
            R.id.station614_6, R.id.station615, R.id.station616, R.id.station417_6, R.id.station617, R.id.station618_6, R.id.station619, R.id.station620,
            R.id.station621_6, R.id.station622
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line6);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line6_cur);
        next = findViewById(R.id.line6_next);
        rem = findViewById(R.id.line6_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line6.length; i++){
            if(line6[i].getText().toString().equals(result.getCur_station())){
                line6[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line6.length; i++){
            line6[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}
