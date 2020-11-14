package com.example.ui.Nav.Favorites;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.text.Html;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ui.R;
import com.example.ui.Result;
import com.example.ui.SearchStation;
import com.example.ui.SubwayController;

import java.io.IOException;

public class Favorites extends AppCompatActivity implements AdapterView.OnItemClickListener {


    // 추가될 아이템 내용을 입력받는 EditText
    private EditText nickName;//즐겨찾기 명칭
    private EditText start;//출발역
    private EditText des;//도착지
    private EditText value;//탐색 옵션

    //역 입력 검색 리스트 구분
    int REQUEST_START = 1;
    int REQUEST_END = 2;

    //컨트롤러 선언
    private SubwayController controller;// 컨트롤러 선언.

    // 리스트뷰
    private ListView listView;
    // 어댑터
    private FavoritesAdapter adapter;

    private Button btnInsert, btnSelect, btnDeleted;
    //DB부분
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    //삭제 입력 폼
    //다이얼로그
    AlertDialog.Builder alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //////////////////////////////////////////////////////////////
        // 위젯 레퍼런스 시작
        nickName = (EditText) findViewById(R.id.fa_nickname);
        start = (EditText) findViewById(R.id.fa_start);
        des = (EditText) findViewById(R.id.fa_des);
        value = (EditText) findViewById(R.id.fa_value);


        btnInsert = (Button) findViewById(R.id.btnInsert);
        alert = new AlertDialog.Builder(Favorites.this);


        listView = (ListView) findViewById(R.id.fa_list);
        listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        // 위젯 레퍼런스 끝
        ///////////////////////////////////////////////////////////
        //어댑터 생성
        adapter = new FavoritesAdapter();
        // 어뎁터를 리스트뷰에 세팅한다.
        listView.setAdapter(adapter);
        // 리스트뷰에 아이템클릭리스너를 등록한다.
        listView.setOnItemClickListener(this);

        myDBHelper = new myDBHelper(this);

        //출발역 입력창이 눌렸을 때
        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (startTouch(event)) return true;
                return false;
            }
        });
        //도착역 입력창이 눌렸을 때
        des.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (endTouch(event)) return true;
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // ArrayList 초기화
        adapter.clearItem();
        // ArrayList에 더미 데이터 입력
        updateData();
        adapter.notifyDataSetChanged();
    }

    private void defaultData() {
        //없어영~~~
    }

    public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
        // 리스트에서 데이터를 받아온다.
        FavoritesData data = (FavoritesData) adapter.getItem(position);
        String message = "길찾기 클릭시 해당 경로로 탐색을 시작합니다.<br />" +
                "별명? : " + data.getNickname() + "<br />" +
                "출발역 : " + data.getStart() + "<br />" +
                "도착역 : " + data.getEnd() + "<br />" +
                "탐색옵션 : " + data.getValue() + "<br />";
        // 삭제
        alert.setTitle("경로 데이터");
        alert.setMessage(Html.fromHtml(message));
        alert.setPositiveButton("삭제",
                new DialogInterface.OnClickListener() {
                    FavoritesData data = (FavoritesData) adapter.getItem(position);

                    public void onClick(DialogInterface dialog, int which) {
                        sqlDB = myDBHelper.getWritableDatabase();
                        sqlDB.execSQL("DELETE FROM groupTBL WHERE  nickName = '" + data.getNickname() + "';");
                        sqlDB.close();
                        updateData();
                        Toast.makeText(getApplicationContext(), "삭제됨", Toast.LENGTH_LONG).show();
                    }
                });
        //길찾기
        alert.setNegativeButton("길찾기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FavoritesData data = (FavoritesData) adapter.getItem(position);
                        findRoute(data);
                    }
                });
        alert.show();
    }

    public void findRoute(FavoritesData data) {
        Intent intent = new Intent(Favorites.this, Result.class);
        //컨트롤러 생성
        //클릭시 컨트롤러를 생성하는 이유는
        //클릭 외부에서 생성시 컨트롤러 내에서 메모리 누수현상이
        // 나타나는 데 그것을 해결하는 가장 간단한 방법이기 때문
        try {
            controller = new SubwayController(this);// 입력된 정보를 바탕으로 컨트롤러 생성.
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (data.getValue()) {
            case "시간":// 시간 우선 탐색 시작.
                controller.findTime(data.getStart().toString(), data.getEnd().toString());
                intent.putExtra("result", controller.getResultData());
                startActivity(intent);
              //  finish();
                controller = null;//컨트롤러 객체 값 null로 초기화
                break;
            case "비용":// 비용 우선 탐색 시작.
                controller.findMoney(data.getStart().toString(), data.getEnd().toString());
                intent.putExtra("result", controller.getResultData());
                startActivity(intent);
               // finish();
                controller = null;//컨트롤러 객체 값 null로 초기화
                break;
            case "거리":// 거리 우선 탐색 시작.
                controller.findMeter(data.getStart().toString(), data.getEnd().toString());
                intent.putExtra("result", controller.getResultData());
                startActivity(intent);
               // finish();
                controller = null;//컨트롤러 객체 값 null로 초기화
                break;
            default:
                Toast.makeText(getApplicationContext(), "데이터에 문제가 있어 보입니다", Toast.LENGTH_LONG).show();
        }
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            // 리스트에 추가 버튼이 클릭되었을때의 처리
            case R.id.btnInsert:
                if (nickName.getText().toString().equals("") || start.getText().toString().equals("")
                        || des.getText().toString().equals("") || value.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요^^", Toast.LENGTH_LONG).show();
                } else {
                    if (value.getText().toString().equals("시간") || value.getText().toString().equals("거리")
                            || value.getText().toString().equals("비용")) {
                        this.insertData();
                        this.updateData();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "탐색옵션 예시 : '시간' or' 비용' or' '거리'", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    //데이터 입력
    public boolean insertData() {
        sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '" + nickName.getText().toString() + "' , " + start.getText().toString() + ", " +
                des.getText().toString() + ", '" + value.getText().toString() + "');");
        sqlDB.close();
        // EditText의 내용을 지운다.
        nickName.setText("");
        start.setText("");
        des.setText("");
        value.setText("");
        // 데이터가 추가된 위치(리스트뷰의 마지막)으로 포커스를 이동시킨다.
        listView.setSelection(adapter.getCount() - 1);
        return true;
    }

    //데이터 조회
    public void updateData() {
        adapter.clearItem();
        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);
        while (cursor.moveToNext()) {
            FavoritesData result = new FavoritesData(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
            adapter.addItem(result);
            System.out.println(cursor.getString(0) + " " + cursor.getString(1) + " " +
                    cursor.getString(2) + " " + cursor.getString(3));
        }
        adapter.notifyDataSetChanged();
    }

    //출발역 입력창이 눌렸을 때 액션 메서드
    public boolean startTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //터치했을 때의 이벤트
                Intent intent = new Intent(Favorites.this, SearchStation.class);
                startActivityForResult(intent, REQUEST_START);//요청 후 결과 돌려받기
                adapter.notifyDataSetChanged();
                updateData();
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
                Intent intent = new Intent(Favorites.this, SearchStation.class);
                startActivityForResult(intent, REQUEST_END);//요청 후 결과 돌려받기
                updateData();
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
                start.setText(data.getStringExtra("Station"));
            }
        if (requestCode == REQUEST_END)//도착역일 경우,
            if (resultCode == RESULT_OK) {
                des.setText(data.getStringExtra("Station"));
            }

    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL ( nickName CHAR(20) PRIMARY KEY, start CHAR(20)," +
                    "ends CHAR(20), value CHAR(20));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
