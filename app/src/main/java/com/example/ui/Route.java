package com.example.ui;

public class Route {
    private int index;// 역의 인덱스 번호를 저장.
    private boolean isTransfer = false;// 경로지 간 해당 역에서 환승 여부 저장.
    //생성자(index).
    public Route(int index) {
        this.index = index;
    }
    //생성자(isTransfer)
    public Route(boolean isTransfer) {
        this.isTransfer = isTransfer;
    }
    public void setTransfer() {//해당 역에서 환승여부 반환.
        this.isTransfer = true;
    }
    public int getIndex() {//인덱스 번호 반환.
        return index;
    }
    public boolean isTransfer() {//해당 역에서 환승여부 반환.
        return isTransfer;
    }
}
