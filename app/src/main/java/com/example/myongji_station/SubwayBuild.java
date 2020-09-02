package com.example.myongji_station;
import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
//역과 경로 데이터를 Read Write하기 위한 클래스
public class SubwayBuild extends Activity {
    private ArrayList<Station> station;// 역의 정보를 저장하기 위한 Station타입의 어레이리스트 선언.
    private ArrayList<Subway>[] subway;// (지하철)경로의 정보를 저장하기 위한 Subway타입의 어레이리스트 선언.
    private String temp_s;// 문자열을 읽을 변수.
    private int staCnt, subCnt;//역과 경로의 수량을 저장하는 변수.
    ///
    private Context mContext;
    ///
    // 생성자 메서드.
    public SubwayBuild(Context context) throws IOException {
        // 임시변수 초기화
        staCnt = 0;//역
        subCnt = 0;//경로
        temp_s = null;
        ///
        mContext = context;
        ///
        this.StationB();//역의 정보 데이터 생성.
        this.SubwayB();//경로의 정보 데이터 생성.


    }

    // 역 데이터 생성 메서드.
    public void StationB() throws IOException {
        // 데이터 읽어 오기.
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.stationdata);
        InputStreamReader inputreader = new InputStreamReader(inputStream);

        // 역의 데이터 파일 읽기.
        BufferedReader stationData = new BufferedReader(inputreader);
        // 역 정보를 저장하기 위한 공간 확보 단계 .
        while ((temp_s = stationData.readLine()) != null)
            staCnt++;// Station.txt의 라인 수 만큼 temp_n증가.
        station = new ArrayList<Station>(staCnt + 1);// 역의 수 만큼 생성, 0번째 빈역이 있기 때문에 역의 수는 + 1
        // 데이터 다시 읽어 오기.
        InputStream inputStream2 = mContext.getResources().openRawResource(R.raw.stationdata);
        InputStreamReader inputreader2 = new InputStreamReader(inputStream2);
        BufferedReader stationData2 = new BufferedReader(inputreader2);
        // 데이터 입력 단계
        while ((temp_s = stationData2.readLine()) != null) {
            String[] split = temp_s.split("	");// tap으로 구분.
            String name_t = split[0];// 역의 이름 저장.
            String line_t = split[1];// 호선 정보 저장. ex(1 or 1, 2, 3)
            station.add(new Station(name_t, line_t));// 역 어레이리스트에 넣기.
        }
    }
    // (지하철)경로 데이터 생성 메서드.
    public void SubwayB() throws IOException {
        // 데이터 읽어 오기.
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.subwaydata);
        InputStreamReader inputreader = new InputStreamReader(inputStream);
        // 경로의 데이러 파일 읽기.
        BufferedReader subwayData = new BufferedReader(inputreader);
        // 경로 데이터 저장하기 위한 공간 확보 단계.
        while ((temp_s = subwayData.readLine()) != null) // Subway.txt의 라인 수 만큼 temp_n증가.
            subCnt++;
        subway = new ArrayList[subCnt + 1];
        for (int i = 1; i <= subCnt; i++) {
            subway[i] = new ArrayList<>();//경로의 수 만큼 생성, [0]번은 빈역
        }
        // 데이터 다시 읽어 오기.
        InputStream inputStream2 = mContext.getResources().openRawResource(R.raw.subwaydata);
        InputStreamReader inputreader2 = new InputStreamReader(inputStream2);
        BufferedReader subwayData2 = new BufferedReader(inputreader2);
        // 데이터 입력 단계.
        while ((temp_s = subwayData2.readLine()) != null) {
            String[] split = temp_s.split("	");// tap으로 구분.
            int start_t = Integer.parseInt(split[0]);
            int dest_t = Integer.parseInt(split[1]);
            int weight_t = Integer.parseInt(split[2]);
            int time_t = Integer.parseInt(split[3]);
            int money_t = Integer.parseInt(split[4]);
            subway[start_t].add(new Subway(dest_t, weight_t, time_t, money_t));// 경로(라인) 양방향이기 때문에,
            subway[dest_t].add(new Subway(start_t, weight_t, time_t, money_t));// 양방향으로 데이터 값 설정.
        }
    }
    //역 데이터 반환 메서드.
    public ArrayList<Station> getStation(){
        return station;
    }
    //경로 데이터 반환 메서드.
    public ArrayList<Subway>[] getSubway(){
        return subway;
    }
    //역의 수 반환 메서드.
    public int getStaCnt() {
        return staCnt;
    }
    //경로의 수 반환 메서드.
    public int getSubCnt() {
        return subCnt;
    }
}
