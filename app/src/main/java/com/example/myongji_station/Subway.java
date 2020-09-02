package com.example.myongji_station;

//(지하철)경로의 정보를 담고 있는 클래스.
//우선순위 큐를 이용하기 위한 객체 정렬을 위해 Comparable 인터페이스 구현.
public class Subway implements Comparable<Subway> {
    private int dest; // 경로의 목적지.
    private int weight;// 경로의 거리, 토탈경로X.
    private int time;// 경로의 시간.
    private int money;// 경로의 비용.

    // 생성자 메서드.
    public Subway(int dest, int weight) {
        this.weight = weight;
        this.dest = dest;
    }

    // 생성자 메서드2.
    public Subway(int dest, int weight, int time, int money) {
        this.weight = weight;
        this.dest = dest;
        this.time = time;
        this.money = money;
    }

    // 우선순위 큐의 기준을 정해주는 메서드.
    @Override
    public int compareTo(Subway o) {// 우선순위 큐를 사용할 때 우선순위 기준을 정한다.
        return this.weight - o.weight;
    }
    //경로의 목적지 반환 해주는 메서드.
    public int getDest() {
        return dest;
    }
    //경로의 거리 반환 해주는 메서드.

    public int getWeight() {
        return weight;
    }
    //경로의 시간 반환 해주는 메서드.

    public int getTime() {
        return time;
    }
    //경로의 비용 반환 해주는 메서드.

    public int getMoney() {
        return money;
    }
}