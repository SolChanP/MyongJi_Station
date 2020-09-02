package com.example.myongji_station;
import java.util.ArrayList;

//역의 정보를 저장하는 클래스.
public class Station {// 역의 이름과 호선정보,,,, 환승 구별을 위해 호선 정보가 필요.
    private String name;// 역 이름
    private ArrayList<Integer> numberLine;// 호선 정보
    // 환승 로직
    // 현재역과 다음역의 호선 정보가 다르면 환승.
    // 환승 가능 역에는 여러개의 호선 정보를 저장.

    // 생성자 메서드.
    public Station(String name, String numberLine) {// 생성자.
        String[] line_t = numberLine.split(",");// 호선 정보를 담기위한 임시 변수.

        this.numberLine = new ArrayList<Integer>(line_t.length);// 연결된 호선 수 만큼 생성.
        for (int i = 0; i < line_t.length; i++) {
            this.numberLine.add(Integer.parseInt(line_t[i]));// 호선 정보 등록.
        }
        this.name = name;
    }

    // 역의 이름 반환 메서드.
    public String getName() {// 역의 이름을 반환 해준다.
        return this.name;
    }

    // 역의 호선정보 유무 판단 메서드.
    public boolean isLine(Integer line) {// 역의 호선정보 유무를 판별해준다.
        for (int i = 0; i < numberLine.size(); i++) {
            if (numberLine.get(i) == line)// 요청이 온 호선이 존재하는지 판단한다.
                return true;
        }
        return false;
    }
    //호선 전체 정보를 반환하는 메서드.
    public ArrayList<Integer> getNumberLine(){
        return numberLine;
    }

}