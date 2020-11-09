package com.example.ui.Nav.Favorites;

public class FavoritesData {
    private String nickname;
    private String start;
    private String end;
    private String value;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNickname() {
        return nickname;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getValue() {
        return value;
    }

    public FavoritesData(String nickname, String start, String end, String value) {
        this.nickname = nickname;
        this.start = start;
        this.end = end;
        this.value = value;
    }
}
