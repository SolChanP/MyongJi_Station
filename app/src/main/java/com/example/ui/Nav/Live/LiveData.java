package com.example.ui.Nav.Live;

import java.io.Serializable;

public class LiveData implements Serializable {
    private String title;//게시글 제목
    private String line;//게시글 호선
    private String dir;//게시글 방향
    private String data;//게시글 내용
    private String num;//게시글 번호
    private String time;//게시글 시간

    public LiveData(String line, String dir, String data, String title, String num, String time) {
        this.line = line;
        this.dir = dir;
        this.data = data;
        this.title = title;
        this.num = num;
        this.time = time;
    }
    public LiveData(String dir, String data, String title, String time) {
        this.line = line;
        this.dir = dir;
        this.data = data;
        this.title = title;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNum(String num){this.num = num;}
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNum(){return num;}
    public String getTitle() {
        return title;
    }

    public String getLine() {
        return line;
    }

    public String getDir() {
        return dir;
    }

    public String getData() {
        return data;
    }

}