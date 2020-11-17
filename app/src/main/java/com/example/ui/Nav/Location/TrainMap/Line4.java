package com.example.ui.Nav.Location.TrainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.Nav.Location.TrainData;
import com.example.ui.R;

public class Line4 extends AppCompatActivity {
    private TextView title, cur, next, rem;
    private Button line4[] = new Button[21];
    private Integer[] Rid_button = {
            R.id.station104_4, R.id.station401, R.id.station307_4, R.id.station402, R.id.station403_4, R.id.station404, R.id.station405, R.id.station406_4,
            R.id.station407, R.id.station115_4, R.id.station408, R.id.station409_4, R.id.station410, R.id.station411, R.id.station412_4, R.id.station413
            ,R.id.station414, R.id.station415, R.id.station416_4, R.id.station417_4, R.id.station216_4
    };
    private TrainData result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line4);
        title = findViewById(R.id.nav_location_title);
        cur = findViewById(R.id.line4_cur);
        next = findViewById(R.id.line4_next);
        rem = findViewById(R.id.line4_rem);

        //버튼 생성...
        this.makeBtn();
        Intent intent = getIntent();
        result = (TrainData) intent.getSerializableExtra("result");
        title.setText(result.getLine() + " 열차 위치");
        for(int i = 0; i < line4.length; i++){
            if(line4[i].getText().toString().equals(result.getCur_station())){
                line4[i].setBackgroundResource(R.drawable.stationbutton_location);
            }
        }

        cur.setText(result.getCur_station() + "역");
        next.setText(result.getNext_station() + "역");
        rem.setText(result.getRem_next_time());
    }
    public void makeBtn(){
        for(int i = 0 ; i < line4.length; i++){
            line4[i] = (Button) findViewById(Rid_button[i]);
        }
    }
}
