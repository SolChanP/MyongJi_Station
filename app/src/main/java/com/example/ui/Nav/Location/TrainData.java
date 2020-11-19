package com.example.ui.Nav.Location;

import java.io.Serializable;

public class TrainData implements Serializable {
    private String line;
    private String cur_station;
    private String rem_next_time;
    private String next_station;

    public void setNext_station(String next_station) {
        this.next_station = next_station;
    }

    public String getNext_station() {
        return next_station;
    }

    public String getLine() {
        return line;
    }

    public String getCur_station() {
        return cur_station;
    }

    public String getRem_next_time() {
        return "약 " + Integer.toString(Integer.parseInt(rem_next_time) / 60) + "분" + " " + Integer.toString(Integer.parseInt(rem_next_time) % 60) + "초";
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