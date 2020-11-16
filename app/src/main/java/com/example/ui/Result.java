package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;


import java.util.ArrayList;


public class Result extends AppCompatActivity {
    private TextView route;
    private TextView route_cnt;
    private TextView time;
    private TextView money;
    private TextView meter;
    private TextView count;

    //스크롤 뷰
    private HorizontalScrollView scroll;

    private ResultData result;

    private ArrayList<Route> routeData;
    private ArrayList<Station> station;

    private Button btn[] = new Button[110];
    private Integer[] Rid_button = {
            //1호선, 23
            R.id.station101, R.id.station102, R.id.station103, R.id.station104, R.id.station105, R.id.station106, R.id.station107, R.id.station108,
            R.id.station109, R.id.station110, R.id.station111, R.id.station112, R.id.station113, R.id.station114, R.id.station115, R.id.station116
            , R.id.station117, R.id.station118, R.id.station119, R.id.station120, R.id.station121, R.id.station122, R.id.station123,
            //2호선, 17
            R.id.station201, R.id.station202, R.id.station203, R.id.station204, R.id.station205, R.id.station206, R.id.station207, R.id.station208,
            R.id.station209, R.id.station210, R.id.station211, R.id.station212, R.id.station213, R.id.station214, R.id.station215, R.id.station216, R.id.station217,
            //3호선, 8
            R.id.station301, R.id.station302, R.id.station303, R.id.station304, R.id.station305, R.id.station306, R.id.station307, R.id.station308,
            //4호선, 17
            R.id.station401, R.id.station402, R.id.station403, R.id.station404, R.id.station405, R.id.station406, R.id.station407, R.id.station408, R.id.station409, R.id.station410,
            R.id.station411, R.id.station412, R.id.station413, R.id.station414, R.id.station415, R.id.station416, R.id.station417,
            //5호선, 7
            R.id.station501, R.id.station502, R.id.station503, R.id.station504, R.id.station505, R.id.station506, R.id.station507,
            //6호선, 22
            R.id.station601, R.id.station602, R.id.station603, R.id.station604, R.id.station605, R.id.station606, R.id.station607, R.id.station608, R.id.station609,
            R.id.station610, R.id.station611, R.id.station612, R.id.station613, R.id.station614, R.id.station615, R.id.station616, R.id.station617, R.id.station618,
            R.id.station619,R.id.station620, R.id.station621, R.id.station622,
            //7호선, 7
            R.id.station701, R.id.station702, R.id.station703, R.id.station704, R.id.station705, R.id.station706, R.id.station707,
            //8호선, 5
            R.id.station801, R.id.station802, R.id.station803, R.id.station804, R.id.station805, R.id.station806,
            //9호선, 3
            R.id.station901, R.id.station902, R.id.station903, R.id.station904
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        routeData = new ArrayList<Route>();
        station = new ArrayList<Station>();
        route = (TextView) findViewById(R.id.route);
        route_cnt = (TextView) findViewById(R.id.route_cnt);
        time = (TextView) findViewById(R.id.time);
        money = (TextView) findViewById(R.id.money);
        meter = (TextView) findViewById(R.id.meter);
        count = (TextView) findViewById(R.id.count);
        //버튼 생성
        this.makeBtn();
        Intent intent = getIntent();
        result = (ResultData) intent.getSerializableExtra("result");

        routeData.addAll(result.getRoute());
        station.addAll(result.getStation());

        //스크롤 뷰, 초기위치 설정
        scroll = findViewById(R.id.result_scroll);
        scroll.post(new Runnable() {
            public void run() {
                scroll.smoothScrollTo(300, 0);
            }
        });

        //텍스트 데이터 제공
        this.showData();
        //노선도 경로 제공공
       this.showRoute();

    }

    public void showData() {
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
        if (Integer.toString(result.getMeter() / 1000).equals("0")) {
            meter.setText("총 소요거리 : 약 " + Integer.toString(result.getMeter() % 1000) + "m");
        } else
            meter.setText("총 소요거리 : 약 " + Integer.toString(result.getMeter() / 1000) + "Km");
        count.setText("총 환승횟수 : " + Integer.toString(tcount) + "회");
    }


    // 역의 인덱스 번호로 역의 문자열 이름을 반환 해주는 메서드
    public String findSubName(int index, ArrayList<Station> data) {
        return data.get(index).getName();
    }

    public void makeBtn() {
        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(Rid_button[i]);
        }
    }
    public void showRoute(){
        for(int i = 0; i < routeData.size(); i++){
            for(int j = 0; j < btn.length; j++) {
                if (btn[j].getText().toString().equals(findSubName(routeData.get(i).getIndex(), station))) {
                    if(routeData.get(i).isTransfer()){
                        btn[j].setBackgroundResource(R.drawable.stationbutton_location_trans);
                    }
                    else{
                        btn[j].setBackgroundResource(R.drawable.stationbutton_location);
                    }
                }
            }
            if(i == 0){

            }
        }
    }
    public void OnStationClick(View v){
        //어떤 기능을 넣을지 고민중~
    }
}
