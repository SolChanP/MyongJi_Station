package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line5 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line5[] = new Button[11];
    private Integer[] Rid_button = {
            R.id.station209_5, R.id.station501, R.id.station502, R.id.station503_5, R.id.station504, R.id.station122_5, R.id.station505, R.id.station506,
            R.id.station403_5, R.id.station507, R.id.station109_5
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line5);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line5_cur);
        next = findViewById(R.id.line5_next);
        rem = findViewById(R.id.line5_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line5.length; i++){
            if(line5[i].getText().toString().equals(result.getCur_station())){
                line5[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line5.length; i++){
            line5[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}
