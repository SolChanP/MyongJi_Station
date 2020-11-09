package com.example.ui.Nav.Location;

public class TrainData {
    private String line;
    private String cur_station;
    private String rem_next_time;

    public String getLine() {
        return line;
    }

    public String getCur_station() {
        return cur_station;
    }

    public String getRem_next_time() {
       // return "약 " + Integer.toString(Integer.parseInt(rem_next_time) / 60) + "분"; -> 분 으로 표시
        return "약" + rem_next_time + "초";
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setCur_station(String cur_station) {
        this.cur_station = cur_station;
    }

    public void setRem_next_time(String rem_next_time) {
        this.rem_next_time = rem_next_time;
    }
}