package com.example.ui;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.Nav.Favorites.Favorites;
import com.example.ui.Nav.Help;
import com.example.ui.Nav.Live.Live;
import com.example.ui.Nav.Location.Location;
import com.example.ui.Nav.Map;
import com.example.ui.Nav.Setting;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class MainActivity extends Activity {
    //컨트롤러 선언
    private SubwayController controller;// 컨트롤러 선언.
    //XML데이터 변수 선언
    private Button find;//길찾기
    private EditText start_s;//출발역
    private EditText end_s;//도착역
    //역 입력 검색 리스트 구분
    int REQUEST_START = 1;
    int REQUEST_END = 2;
    private TextView test;

    //네비게이션 드로어
    private DrawerLayout drawerLayout;
    private View drawerView;

    //라디오 그룹
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--------------노선도 레이아웃 완성 시 삭제 예정-----------------------------------------------------------
        //노선도 줌인 줌아웃
        PhotoView photoView = findViewById(R.id.main_map);
        photoView.setImageResource(R.drawable.map);
        //노선도 초기 풀 스크린
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //----------------------------------------------------------------------------------------------------------------

        //XML데이터 생성
        rg = (RadioGroup)findViewById(R.id.radio_group);
        find = findViewById(R.id.find_btn);//길찾기
        start_s = findViewById(R.id.start_s);//출발역
        end_s = findViewById(R.id.end_s);//도착역

        //네비게이션 드로어 부분
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listener);
        Button nav = (Button)findViewById(R.id.nav);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });
        //-------------------------------------------------------------------------------------------------
        //출발역 입력창이 눌렸을 때
        start_s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (startTouch(event)) return true;
                return false;
            }
        });
        //도착역 입력창이 눌렸을 때
        end_s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (endTouch(event)) return true;
                return false;
            }
        });
    }
    //출발역 입력창이 눌렸을 때 액션 메서드
    public boolean startTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //터치했을 때의 이벤트
                Intent intent = new Intent(MainActivity.this, SearchStation.class);
                startActivityForResult(intent, REQUEST_START);//요청 후 결과 돌려받기
                return true;
            }
        }
        return false;
    }
    //도착역 입력창이 눌렸을 때 액션 메서드
    public boolean endTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //터치했을 때의 이벤트
                Intent intent = new Intent(MainActivity.this, SearchStation.class);
                startActivityForResult(intent, REQUEST_END);//요청 후 결과 돌려받기
                return true;
            }
        }
        return false;
    }
    //돌려받은 결과 저장하기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_START)//출발역일 경우,
            if (resultCode == RESULT_OK) {
                start_s.setText(data.getStringExtra("Station"));
            }
        if (requestCode == REQUEST_END)//도착역일 경우,
            if (resultCode == RESULT_OK) {
                end_s.setText(data.getStringExtra("Station"));
            }
    }
    //네비게이션 드로어 부분
    //드로어 리스너 메서드 오버라이드
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };
    //버튼 및 드로어 메뉴 클릭시
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.nav_map :
            case R.id.nav_map_layout :
                intent = new Intent(getBaseContext(), Map.class);
                startActivity(intent);
                break;
            case R.id.nav_location :
            case R.id.nav_location_layout :
                intent = new Intent(getBaseContext(), Location.class);
                startActivity(intent);
                break;
            case R.id.nav_fa :
            case R.id.nav_fa_layout :
                intent = new Intent(getBaseContext(), Favorites.class);
                startActivity(intent);
                break;
            case R.id.nav_live :
            case R.id.nav_live_layout :
                intent = new Intent(getBaseContext(), Live.class);
                startActivity(intent);
                break;
            case R.id.nav_help :
            case R.id.nav_help_layout :
                intent = new Intent(getBaseContext(), Help.class);
                startActivity(intent);
                break;
            case R.id.nav_setting :
            case R.id.nav_setting_layout :
                intent = new Intent(getBaseContext(), Setting.class);
                startActivity(intent);
                break;
            case R.id.main_change :
                String temp = start_s.getText().toString();
                start_s.setText(end_s.getText().toString());
                end_s.setText(temp);
                break;
                //길찾기 버튼이 눌린 경우
            case R.id.find_btn:
                if(start_s.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "출발역을 입력하세요", Toast.LENGTH_LONG).show();
                else if(end_s.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "도착역을 입력하세요", Toast.LENGTH_LONG).show();
                else
                    this.find_btn_clicked();
                break;
        }
    }
    //길찾기 버튼이 눌린 경우
    public void find_btn_clicked(){
        Intent intent = new Intent(MainActivity.this, Result.class);
        //컨트롤러 생성
        //클릭시 컨트롤러를 생성하는 이유는
        //클릭 외부에서 생성시 컨트롤러 내에서 메모리 누수현상이
        // 나타나는 데 그것을 해결하는 가장 간단한 방법이기 때문
        try {
            controller = new SubwayController(this);// 입력된 정보를 바탕으로 컨트롤러 생성.
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (rg.getCheckedRadioButtonId()){
            case R.id.radio_time:// 시간 우선 탐색 시작.
                controller.findTime(start_s.getText().toString(), end_s.getText().toString());
                intent.putExtra("result", controller.getResultData());
                startActivity(intent);
                controller = null;//컨트롤러 객체 값 null로 초기화
                break;
            case R.id.radio_money:// 비용 우선 탐색 시작.
                controller.findMoney(start_s.getText().toString(), end_s.getText().toString());
                intent.putExtra("result", controller.getResultData());
                startActivity(intent);
                controller = null;//컨트롤러 객체 값 null로 초기화
                break;
            case R.id.radio_meter:// 거리 우선 탐색 시작.
                controller.findMeter(start_s.getText().toString(), end_s.getText().toString());
                intent.putExtra("result", controller.getResultData());
                startActivity(intent);
                controller = null;//컨트롤러 객체 값 null로 초기화
                break;
            default:
                Toast.makeText(getApplicationContext(), "탐색 옵션을 선택하세요", Toast.LENGTH_LONG).show();
        }
    }
    //-------------------------------------------------------------------------------------------------
    //뒤로가기 버튼
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(drawerView)){
            drawerLayout.closeDrawers();
        }
        else{
            super.onBackPressed();
        }
    }
    //-------------------------------------------------------------------------------------------------
}