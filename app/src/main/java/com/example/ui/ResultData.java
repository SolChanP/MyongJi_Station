package com.example.ui;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultData implements Serializable {
    private ArrayList<Route> route;
    private ArrayList<Station> station;
    private int time;
    private int money;
    private int meter;
    public ResultData(){
        this.route = new ArrayList<Route>();
        this.station = new ArrayList<Station>();
        time = 0;
        meter = 0;
        money = 0;
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
