package com.example.ui;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultData implements Serializable {
    private ArrayList<Route> route;//경로 데이터
    private ArrayList<Station> station;//역 데이터
    private int time;//시간
    private int money;//비용
    private int meter;//거리
    private String value;//탐색옵션
    public ResultData(){
        this.route = new ArrayList<Route>();
        this.station = new ArrayList<Station>();
        time = 0;
        meter = 0;
        money = 0;
        value = "";
    }
    public ArrayList<Route> getRoute() {
        return route;
    }
    public ArrayList<Station> getStation() {
        return station;
    }

    public void setRoute(ArrayList<Route> route) {
        this.route.addAll(route);
    }
    public void setStation(ArrayList<Station> station) {
        this.station.addAll(station);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setMeter(int meter) {
        this.meter = meter;
    }

    public int getTime() {
        return time;
    }

    public int getMoney() {
        return money;
    }

    public int getMeter() {
        return meter;
    }

}
