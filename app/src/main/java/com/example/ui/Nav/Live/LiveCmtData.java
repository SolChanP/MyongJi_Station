package com.example.ui.Nav.Live;

import java.io.Serializable;

public class LiveCmtData implements Serializable {
    private String data;//댓글 내
    private String time;//댓글 시간

    public LiveCmtData(String data, String time) {
        this.data = data;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public void setData(String data) {
        this.data = data;
    }


    public String getData() {
        return data;
    }

}