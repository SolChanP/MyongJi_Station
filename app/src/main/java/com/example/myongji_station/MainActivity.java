package com.example.myongji_station;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;


//데이터 DB구현시 빌드 예외 처리 제거예정...
public class MainActivity extends AppCompatActivity {
    //컨트롤러 및 빌더 선언
    private SubwayBuild subBuild;// 데이터 생성 객체.
    private SubwayController controller;// 컨트롤러 선언.
    //XML데이터 변수 선언
    private Button find;//길찾기
    private TextView test;//출력 데이터
    private EditText start_s;//출발역
    private EditText end_s;//도착역
    private EditText value;//탐색 옵션
    //역 입력 검색 리스트 구분
    int REQUEST_START = 1;
    int REQUEST_END = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //데이터 생성
        try {
            subBuild = new SubwayBuild(this);// 데이터 생성 요청.
        } catch (IOException e) {
            e.printStackTrace();
        }
        //XML데이터 생성
        find = findViewById(R.id.find);//길찾기
        test = findViewById(R.id.test);//출력
        start_s = findViewById(R.id.start_s);//출발역
        end_s = findViewById(R.id.end_s);//도착역
        value = findViewById(R.id.value);//탐색옵션
        //출발역 입력창이 눌렸을 때
        start_s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (startTouch(event)) return true;
                return false;
            }
        });
        //도착역 입력창이 눌렸을 때
        end_s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (endTouch(event)) return true;
                return false;
            }
        });
        //길찾기 버튼이 눌렸을 경우,
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //컨트롤러 생성
                //클릭시 컨트롤러를 생성하는 이유는
                //클릭 외부에서 생성시 컨트롤러 내에서 메모리 누수현상이
                // 나타나는 데 그것을 해결하는 가장 간단한 방법이기 때문
                controller = new SubwayController(subBuild);// 입력된 정보를 바탕으로 컨트롤러 생성.
                switch (value.getText().toString()) {
                    case "1":// 거리 우선 탐색 시작.
                        controller.findMeter(start_s.getText().toString(), end_s.getText().toString());
                        test.setText(controller.getAll());
                        controller = null;//컨트롤러 객체 값 null로 초기화
                        break;
                    case "2":// 시간 우선 탐색 시작.
                        controller.findTime(start_s.getText().toString(), end_s.getText().toString());
                        test.setText(controller.getAll());
                        controller = null;//컨트롤러 객체 값 null로 초기화
                        break;
                    case "3":// 비용 우선 탐색 시작.
                        controller.findMoney(start_s.getText().toString(), end_s.getText().toString());
                        test.setText(controller.getAll());
                        controller = null;//컨트롤러 객체 값 null로 초기화
                        break;
                    default:
                        System.out.println("탐색 옵션을 잘못 선택하셨습니다. \n 프로그램을 종료합니다.");
                        controller = null;//컨트롤러 객체 값 null로 초기화
                        return;// 프로그램 종료.
                }
            }
        });
    }
    //출발역 입력창이 눌렸을 때 액션 메서드
    public boolean startTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //터치했을 때의 이벤트
                Intent intent = new Intent(MainActivity.this, SearchStation.class);
                startActivityForResult(intent, REQUEST_START);//요청 후 결과 돌려받기
                return true;
            }
        }
        return false;
    }
    //도착역 입력창이 눌렸을 때 액션 메서드
    public boolean endTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //터치했을 때의 이벤트
                Intent intent = new Intent(MainActivity.this, SearchStation.class);
                startActivityForResult(intent, REQUEST_END);//요청 후 결과 돌려받기
                return true;
            }
        }
        return false;
    }
    //돌려받은 결과 저장하기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_START)//출발역일 경우,
            if (resultCode == RESULT_OK) {
                start_s.setText(data.getStringExtra("Station"));
            }
        if (requestCode == REQUEST_END)//도착역일 경우,
            if (resultCode == RESULT_OK) {
                end_s.setText(data.getStringExtra("Station"));
            }
    }

}
