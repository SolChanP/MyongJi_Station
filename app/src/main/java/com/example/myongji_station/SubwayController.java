package com.example.myongji_station;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

//역과 (지하철)경로의 데이터를 이용하여
//(선택 기준)최단 경로
//소요되는 정보
//역과 (지하철)의 정보
//컨트롤.
public class SubwayController {
    private int INF = Integer.MAX_VALUE;// 최대 정수를 표현하기 위한 변수.
    // 데이터.
    private ArrayList<Station> station;// 역의 이름과 호선 정보를 이용하기 위한 변수, 역시 얕은복사 이용.
    private ArrayList<Subway>[] subway;// 역의 경로 정보를 이용하기 위한 변수, 데이터 변경이 없기 때문에 얕은 복사 이용.
    private int value;// 기능의 종류 판단 변수, 우선 순위 설정(거리 = 1, 시간 = 2, 비용 = 3)
    // 제공할 데이터
    private int dist[];// 최단 경로 거리를 저장하기 위한 배열변수, 메서드에 따라 경로가 시간 또는 비용으로 사용됨.
    private int start, end, subCnt = 0;// 출발역과 도착역의 인덱스 번호를 저장, 경로의 수를 저장하기 위한 변수들.
    private int allTime = 0, allMoney = 0, allMeter = 0;// 총 소요 비용과 돈, 거리를 저장하기 위한 변수, 최단이 아님!.
    // 경로 추적
    private ArrayList<Route> route;//// 경로지 정보 저장할 어레이 리스트.
    private int pre[];// 경로를 추적하기 위한 배열변수, 해당역의 이전 역을 추적(저장).
    private int routeCnt;// 경로를 추적하기 위한 배열변수2, 경로하는 역의 수를 저장.
    private int curLine;// 환승역을 구분하기 위한 현재 타고있는 호선의 번호.
    // 생성자.
    public SubwayController(SubwayBuild data) {
        this.subway = data.getSubway();// 경로 데이터 얕은 복사.
        this.station = data.getStation();// 역 데이터 얕은 복사.
        this.subCnt = data.getSubCnt();// 역의 수 저장.
        this.dist = new int[subCnt + 1];
        pre = new int[subCnt + 1];
        Arrays.fill(dist, INF);// dist의 모든 배열값을 INF로 설정, INF -> 정수 배열의 최대값
    }
    //초기화 메서드
    public void SubwayController() {//프로그램이 종료되는 것이 아니기 때문에
        //새로운 길찾기시 서브 제공 데이터 값 초기화 필요
        this.allTime = 0;//누적 시간 초기화
        this.allMoney = 0;//누적 비용 초기화
        this.allMeter = 0;//누적 거리 초기화
        this.routeCnt = 0;//누적 경로하는 역 초기화
    }



    // 최단 거리 경로 구하는 메서드. 핵심!!!
    public void findMeter(String start, String end) {// 시간 우선 탐색
        SubwayController();//제공할 데이터 값 초기화.
        this.setStart(start);// 출발역 세팅
        this.setEnd(end);// 도착역 세팅
        System.out.println("최단 거리 경로  탐색합니다.");// 경로 탐색 시작 알림 메세지.
        this.value = 1;// 탐색 정보 출력시 거리 우선 탐색임을 구분하기 위한 데이터.
        PriorityQueue<Subway> pq = new PriorityQueue<>();// 우선순위 큐 Subway타입 선언 및 생성.
        boolean check[] = new boolean[subCnt + 1];// 방문 여부 체크 위한 boolean배열 변수 선언.
        pq.add(new Subway(this.start, 0));// 우선순위 큐에 시작지점과 거리 추가, 시작지점 -> 시작지점 거리는 0이기 때문에 0이다.
        dist[this.start] = 0;// 거리 배열에도 출발 거리는 0으로 지정.
        while (!pq.isEmpty()) {// 우선순위 큐가 빈 상태가 아니면 반복.
            Subway curSub = pq.poll();// Subway타입의 curSub변수에 우선순위 큐 맨 앞 요소 받아서 저장.
            int cur = curSub.getDest();// top해서 가져온 경로의 목적지를 cur변수에 저장.
            if (check[cur] == true)// 목적지(도착지X)가 방문하였던 경우,
                continue;// 아래 구문 스킵, 원래는 if else구현해야 하나 큰 차이 없어 가독성 좋게 표현.
            check[cur] = true;// 목적지 방문 여부 true 변경.
            for (Subway sub : subway[cur]) {// 목적지가 갈 수 있는 경로를 모두 방문, subway[cur].get(0), subway[cur].get(1) ... 이렇게 모든
                // 경로를 방문하며.
                if (dist[sub.getDest()] > dist[cur] + sub.getWeight()) {// ex 1(출발지) -> 2 -> 3, 3까지의 거리가 > 2까지의 거리 + 2에서
                    // 3까지의 거리인 경우,
                    dist[sub.getDest()] = dist[cur] + sub.getWeight();// 1에서 3까지의 더 짧은 거리를 찾은거임! 따라서 3까지의 거리 dist[3] 갱신!
                    pq.add(new Subway(sub.getDest(), dist[sub.getDest()]));// 우선순위 큐에 새로 찾은 정보들 추가.
                    pre[sub.getDest()] = cur;// 경로를 추적하기 위해 저장.
                    // ex 경로가 1 -> 3 -> 2 -> 5일 경우
                    // pre[5]는 2, pre[2]는 3, pre[3]은 1을 저장.
                    // 나중에 역추적 할 거임.
                }
            }
        }
        // 경로 역추적 메서드 호출.
        this.Backtracer();// 경로 역추적하여 서브 데이터 구하는 메서드.
    }

    public void findTime(String start, String end) {// 거리 우선 탐색
        SubwayController();//제공할 데이터 값 초기화.
        this.setStart(start);// 출발역 세팅
        this.setEnd(end);// 도착역 세팅
        System.out.println("최단 시간 경로  탐색합니다.");// 경로 탐색 시작 알림 메세지.
        this.value = 2;// 탐색 정보 출력시 거리 우선 탐색임을 구분하기 위한 데이터.
        PriorityQueue<Subway> pq = new PriorityQueue<>();// 우선순위 큐 Subway타입 선언 및 생성.
        boolean check[] = new boolean[subCnt + 1];// 방문 여부 체크 위한 boolean배열 변수 선언.
        pq.add(new Subway(this.start, 0));// 우선순위 큐에 시작지점과 거리 추가, 시작지점 -> 시작지점 거리는 0이기 때문에 0이다.
        dist[this.start] = 0;// 거리 배열에도 출발 거리는 0으로 지정.
        while (!pq.isEmpty()) {// 우선순위 큐가 빈 상태가 아니면 반복.
            Subway curSub = pq.poll();// Subway타입의 curSub변수에 우선순위 큐 맨 앞 요소 받아서 저장.
            int cur = curSub.getDest();// top해서 가져온 경로의 목적지를 cur변수에 저장.
            if (check[cur] == true)// 목적지(도착지X)가 방문하였던 경우,
                continue;// 아래 구문 스킵, 원래는 if else구현해야 하나 큰 차이 없어 가독성 좋게 표현.
            check[cur] = true;// 목적지 방문 여부 true 변경.
            for (Subway sub : subway[cur]) {// 목적지가 갈 수 있는 경로를 모두 방문, subway[cur].get(0), subway[cur].get(1) ... 이렇게 모든
                // 경로를 방문하며.
                if (dist[sub.getDest()] > dist[cur] + sub.getTime()) {// ex 1(출발지) -> 2 -> 3, 3까지의 거리가 > 2까지의 거리 + 2에서
                    // 3까지의 거리인 경우,
                    dist[sub.getDest()] = dist[cur] + sub.getTime();// 1에서 3까지의 더 짧은 거리를 찾은거임! 따라서 3까지의 거리 dist[3] 갱신!
                    pq.add(new Subway(sub.getDest(), dist[sub.getDest()]));// 우선순위 큐에 새로 찾은 정보들 추가.
                    pre[sub.getDest()] = cur;// 경로를 추적하기 위해 저장.
                    // ex 경로가 1 -> 3 -> 2 -> 5일 경우
                    // pre[5]는 2, pre[2]는 3, pre[3]은 1을 저장.
                    // 나중에 역추적 할 거임.
                }
            }
        }
        // 경로 역추적 메서드 호출.
        this.Backtracer();// 경로 역추적하여 서브 데이터 구하는 메서드.
    }

    public void findMoney(String start, String end) {// 비용 우선 탐색
        SubwayController();//제공할 데이터 값 초기화.
        this.setStart(start);// 출발역 세팅
        this.setEnd(end);// 도착역 세팅
        System.out.println("최단 비용 경로  탐색합니다.");// 경로 탐색 시작 알림 메세지.
        this.value = 3;// 탐색 정보 출력시 거리 우선 탐색임을 구분하기 위한 데이터.
        PriorityQueue<Subway> pq = new PriorityQueue<>();// 우선순위 큐 Subway타입 선언 및 생성.
        boolean check[] = new boolean[subCnt + 1];// 방문 여부 체크 위한 boolean배열 변수 선언.
        pq.add(new Subway(this.start, 0));// 우선순위 큐에 시작지점과 거리 추가, 시작지점 -> 시작지점 거리는 0이기 때문에 0이다.
        dist[this.start] = 0;// 거리 배열에도 출발 거리는 0으로 지정.
        while (!pq.isEmpty()) {// 우선순위 큐가 빈 상태가 아니면 반복.
            Subway curSub = pq.poll();// Subway타입의 curSub변수에 우선순위 큐 맨 앞 요소 받아서 저장.
            int cur = curSub.getDest();// top해서 가져온 경로의 목적지를 cur변수에 저장.
            if (check[cur] == true)// 목적지(도착지X)가 방문하였던 경우,
                continue;// 아래 구문 스킵, 원래는 if else구현해야 하나 큰 차이 없어 가독성 좋게 표현.
            check[cur] = true;// 목적지 방문 여부 true 변경.
            for (Subway sub : subway[cur]) {// 목적지가 갈 수 있는 경로를 모두 방문, subway[cur].get(0), subway[cur].get(1) ... 이렇게 모든
                // 경로를 방문하며.
                if (dist[sub.getDest()] > dist[cur] + sub.getMoney()) {// ex 1(출발지) -> 2 -> 3, 3까지의 거리가 > 2까지의 거리 + 2에서
                    // 3까지의 거리인 경우,
                    dist[sub.getDest()] = dist[cur] + sub.getMoney();// 1에서 3까지의 더 짧은 거리를 찾은거임! 따라서 3까지의 거리 dist[3] 갱신!
                    pq.add(new Subway(sub.getDest(), dist[sub.getDest()]));// 우선순위 큐에 새로 찾은 정보들 추가.
                    pre[sub.getDest()] = cur;// 경로를 추적하기 위해 저장.
                    // ex 경로가 1 -> 3 -> 2 -> 5일 경우
                    // pre[5]는 2, pre[2]는 3, pre[3]은 1을 저장.
                    // 나중에 역추적 할 거임.
                }
            }
        }
        // 경로 역추적 메서드 호출.
        this.Backtracer();// 경로 역추적하여 서브 데이터 구하는 메서드.
    }

    // 저장된 경로를 역추적하여 스택으로 정리후 Route타입 어레이리스트에 인덱스 번호로 저장, 서브 데이터(누적 거리, 시간, 비용,
    // 환승여부)들도 계산해서 저장..
    public void Backtracer() {
        Stack<Integer> stack = new Stack<>();// 역추적한 경로를 저장할 스택.
        route = new ArrayList<Route>(stack.size());// 경로지 저장할 어레이 리스트..
        int cur = end;// 현재 위치를 도착지 인덱스로 설정, 역추적 해야하기 때문이다.

        while (cur != start) {// 출발지 인덱스가 아닌 경우 반복.
            stack.push(cur);// 도착지 인덱스를 스택에 푸쉬!.
            routeCnt++;// 경로하는 역의 수 증가.
            // ex) 경로가 1 -> 3 -> 2 -> 5일 경우,
            cur = pre[cur];// pre[5]는 2, 따라서 cur에 2 저장.
        }
        stack.push(cur);// 출발지 인덱스 번호 푸쉬!.
        routeCnt++;// 마지막으로 경로하는 역의 수 증가.

        while (!stack.isEmpty()) {// 스택이 비워질때까지 반복한다.
            int station_n = stack.pop();// 역의 인덱스 번호, 스택의 탑을 팝!.
            route.add(new Route(station_n));// Route어레이리스트에 저장.
        }
        stack = null;//메모리 누수 방지
        for (int i = 0; i < route.size() - 1; i++) {
            for (int j = 0; j < subway[route.get(i).getIndex()].size(); j++) {// 소요 시간과 소요 비용을 구하기 위한 방법!!!!!!!!
                if (subway[route.get(i).getIndex()].get(j).getDest() == route.get(i + 1).getIndex()) {
                    // ex busList[5].get(0 ~).end => 5의 모든 간선을 돌며 도착지가 저장된 경로와 같으면 그 거리를 저장.
                    // 저장된 다음 경로는 route.get(i + 1)/ route.get(i+1)은 저장된 경로
                    // i번째 다음 경로이다.
                    allTime += subway[route.get(i).getIndex()].get(j).getTime();// 최단 데이터가 아닌 누적 데이터 저장 하는거임.
                    allMoney += subway[route.get(i).getIndex()].get(j).getMoney();//
                    allMeter += subway[route.get(i).getIndex()].get(j).getWeight();//
                }
            }
        }
        /// 역 환승 여부 판단하기.
        // 출발 호선이 몇 호선인지 판단하기, 이중for문이지만 최대 비교 횟수는 2(최대 호선) x 2이다!!!.
        if (station.get(start).getNumberLine().size() >= 2) {// 현재역의 호선이 2개 이상일 경우,
            for (int i = 0; i < station.get(start).getNumberLine().size(); i++) {
                for (int j = 0; j < station.get(route.get(1).getIndex()).getNumberLine().size(); j++) {// 다음역의 라인 수 만큼
                    // 반복.
                    if (station.get(start).getNumberLine().get(i) == station.get(route.get(1).getIndex())
                            .getNumberLine().get(j)) {// 현재i번째 호선 == 다음역j번째 호선
                        curLine = station.get(start).getNumberLine().get(i);// 같은 호선을 현재 호선으로 저장.
                    }
                }
            }
        } else// 환승이 불가능한 역.
            curLine = station.get(start).getNumberLine().get(0);// 현재역은 환승불가능 역이기 때문에 현재 호선이 시작 호선이다.
        // 환승이 필요한 역 확인하기.
        boolean temp = false;
        for (int i = 0; i < route.size() - 1; i++) {
            for (int j = 0; j < station.get(route.get(i + 1).getIndex()).getNumberLine().size(); j++) {
                if (curLine == station.get(route.get(i + 1).getIndex()).getNumberLine().get(j)) {// 환승이 필요 없는 경우.
                    temp = true;
                    break;
                }
            }
            if (temp == false) {// 환승 해야 함
                route.get(i).setTransfer();// 환승여부 true설정
                if (station.get(route.get(i + 1).getIndex()).getNumberLine().size() == 1) {// 다음역이 환승이 없는 역인 경우.
                    curLine = station.get(route.get(i + 1).getIndex()).getNumberLine().get(0);// 경로 정보는 1개임
                } else {// 다음역도 환승 역인 경우, 이 코드는 최대 2개 호선이 연결 된 상태에서 구동 가능.
                    for (int j = 0; j < station.get(route.get(i).getIndex()).getNumberLine().size(); j++) {// 이번역의 경로만큼
                        if (curLine != station.get(route.get(i).getIndex()).getNumberLine().get(j)) {// 현재 경로가 아닌 경로를
                            // 찾으면
                            curLine = station.get(route.get(i).getIndex()).getNumberLine().get(j);// 현재 경로를 변경.
                            break;
                        }
                    }
                }
            }
            temp = false;
        }
    }

    // 정보 출력 메서드.
    public String getAll() {
        int i = 0;// 경로 출력을 위한 증감변수.
        String test;
        // 설정된 기능값에 따라 출력 결과가 다름.
        switch (value) {
            case 1:// 거리 우선 탐색.
                test = findSubName(route.get(0).getIndex(), station) + " -> ";
                for (i = 1; i < route.size() - 1; i++) {
                    test += findSubName(route.get(i).getIndex(), station);
                    if (route.get(i).isTransfer() == true) {
                        test += " 환승 ";
                    }
                    test += " -> ";
                }
                test += findSubName(route.get(i).getIndex(), station);
                test += "\n" + routeCnt + "개 역을 방문합니다. \n";
                test += "최단 거리   :  약" + (double) dist[end] / 1000 + "Km\n";
                test += "총 소요시간 :  약" + allTime / 60 + "분 \n";
                test += "총 소요비용 : " + allMoney + "원 \n";
                return test;
            case 2:// 시간 우선 탐색.
                test = findSubName(route.get(0).getIndex(), station) + " -> ";
                for (i = 1; i < route.size() - 1; i++) {
                    test += findSubName(route.get(i).getIndex(), station);
                    if (route.get(i).isTransfer() == true) {
                        test += " 환승 ";
                    }
                    test += " -> ";
                }
                test += findSubName(route.get(i).getIndex(), station);
                test += "\n" + routeCnt + "개 역을 방문합니다. \n";
                test += "최단 소요시간  : 약 " + dist[end] / 60 + "분\n";
                test += "총 거리	  : 약 " + (double) allMeter / 1000 + "Km\n";
                test += "총 소요비용	  : " + allMoney + "원\n";
                return test;
            case 3:// 비용 우선 탐색.
                test = findSubName(route.get(0).getIndex(), station) + " -> ";
                for (i = 1; i < route.size() - 1; i++) {
                    test += findSubName(route.get(i).getIndex(), station);
                    if (route.get(i).isTransfer() == true) {
                        test += " 환승 ";
                    }
                    test += " -> ";
                }
                test += findSubName(route.get(i).getIndex(), station);
                test += "\n" + routeCnt + "개 역을 방문합니다. \n";
                test += "최단 비용    : " + dist[end] + "원  \n";
                test += "총 소요시간 : 약" + allTime / 60 + "분 \n";
                test += "총 소요거리 : 약" + (double) allMeter / 1000 + "Km \n";
                return test;
            default:// 오류 메세지 출력.
                test = "길찾기 결과 데이터가 없습니다. \n프로그램을 종료합니다.";
                return test;
        }
    }

    // 입력받은 문자열 역의 이름을 역의 인덱스 값으로 반환 해주는 메서드
    public static int findIndex(String text, ArrayList<Station> data) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {// 출발지 도착지 인덱스 번호 검색기.
            if (data.get(i).getName().equals(text))
                index = i;
        }
        System.out.println(index);
        return index;
    }

    // 역의 인덱스 번호로 역의 문자열 이름을 반환 해주는 메서드
    public String findSubName(int index, ArrayList<Station> data) {
        return data.get(index).getName();
    }

    public void setStart(String start) {// 시작역 저장 메서드.
        this.start = findIndex(start, this.station);// 출발역 인덱스 저장.
    }

    public void setEnd(String end) {// 도착역 저장 메서드.
        this.end = findIndex(end, this.station);// 도착역 인덱스 저장.
    }
}

