package com.example.ui.Nav.Live;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ui.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LiveInput extends AppCompatActivity implements Serializable {
    private LiveData result;
    private TextView line;
    private EditText dir, data, title;
    private String formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_input);

        line = (TextView) findViewById(R.id.live_line);
        title = (EditText) findViewById(R.id.live_title);
        dir = (EditText) findViewById(R.id.live_dir);
        data = (EditText) findViewById(R.id.live_data);

        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm");
        // nowDate 변수에 값을 저장한다.
        formatDate = sdfNow.format(date);

        Intent intent = getIntent();
//        result = (LiveData) intent.getSerializableExtra("result");

        this.setData(result);
    }
    public void OnClick(View v){
        if(v.getId() == R.id.live_line_add){
           /* if(data.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
            }else{*/
            LiveData temp = new LiveData(dir.getText().toString(), data.getText().toString(), title.getText().toString(), formatDate);
            Intent intent = new Intent(
                    getApplicationContext(), // 현재화면의 제어권자
                    Live.class); // 다음넘어갈 화면
            // intent 객체에 데이터를 실어서 보내기
            // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 인텐트로 넘길값들을 넘긴다
            intent.putExtra("data", temp);
            setResult(RESULT_OK, intent);
            finish();
            //  }
        }
    }
    public void setData(LiveData result){

        // line.setText(result.getLine().toString() + "호선 게시글");
    }
}