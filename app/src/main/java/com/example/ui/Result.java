package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;


public class Result extends AppCompatActivity {
    private TextView route;
    private TextView route_cnt;
    private TextView time;
    private TextView money;
    private TextView meter;
    private TextView count;

    private ResultData result;

    private ArrayList<Route> routeData;
    private ArrayList<Station> station;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        routeData = new ArrayList<Route>();
        station = new ArrayList<Station>();
        route = (TextView)findViewById(R.id.route);
        route_cnt = (TextView)findViewById(R.id.route_cnt);
        time = (TextView)findViewById(R.id.time);
        money = (TextView)findViewById(R.id.money);
        meter = (TextView)findViewById(R.id.meter);
        count = (TextView)findViewById(R.id.count);


        Intent intent = getIntent();
        result = (ResultData) intent.getSerializableExtra("result");

        routeData.addAll(result.getRoute());
        station.addAll(result.getStation());

        this.showData();


    }
    public void showData(){
        //경로 스트링 생성
        String temp = "";
        int tRouteCnt;//경로 역의 수
        int tcount = 0; // 환승 횟수
        for (tRouteCnt = 0; tRouteCnt < routeData.size() - 1; tRouteCnt++) {
            temp += findSubName(routeData.get(tRouteCnt).getIndex(), station) + "역";
            if (routeData.get(tRouteCnt).isTransfer() == true) {
                temp += " 환승 ";
                tcount++;
            }
            temp += " -> ";
        }
        temp += findSubName(routeData.get(tRouteCnt).getIndex(), station) + "역";

        route.setText(temp);
        route_cnt.setText("총 경로하는 역 : " + Integer.toString(tRouteCnt + 1) + "개 ");
        time.setText("총 소요시간 : 약 " + Integer.toString(result.getTime() / 60) + "분");
        money.setText("총 소요비용 : 약 " + Integer.toString(result.getMoney()) + "원");
        meter.setText("총 소요거리 : 약 " + Integer.toString(result.getMeter() / 1000) + "Km");
        count.setText("총 환승횟수 : " + Integer.toString(tcount) + "회");
    }

    // 역의 인덱스 번호로 역의 문자열 이름을 반환 해주는 메서드
    public String findSubName(int index, ArrayList<Station> data) {
        return data.get(index).getName();
    }
}
