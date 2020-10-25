package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchStation extends AppCompatActivity {
    private ArrayList<Station> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<Station> arraylist;

    private String temp_s;// 문자열을 읽을 변수.
    private int staCnt;//역과 경로의 수량을 저장하는 변수.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_station);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

        // 검색에 사용할 데이터을 미리 저장한다.
        try {
            settingList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<Station>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        MainActivity.class); // 다음넘어갈 화면
                // intent 객체에 데이터를 실어서 보내기
                // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 position 값을 이용하여 인텐트로 넘길값들을 넘긴다
                intent.putExtra("Station", list.get(position).getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });
    }
    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).getName().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
    // 검색에 사용될 데이터를 리스트에 추가한다.
    public void settingList() throws IOException {
        // 데이터 읽어 오기.
        InputStream inputStream = getResources().openRawResource(R.raw.stationdata);
        InputStreamReader inputreader = new InputStreamReader(inputStream);

        // 역의 데이터 파일 읽기.
        BufferedReader stationData = new BufferedReader(inputreader);
        // 역 정보를 저장하기 위한 공간 확보 단계 .
        while ((temp_s = stationData.readLine()) != null)
            staCnt++;// Station.txt의 라인 수 만큼 temp_n증가.
        list = new ArrayList<Station>(staCnt + 1);// 역의 수 만큼 생성, 0번째 빈역이 있기 때문에 역의 수는 + 1
        // 데이터 다시 읽어 오기.
        InputStream inputStream2 = getResources().openRawResource(R.raw.stationdata);
        InputStreamReader inputreader2 = new InputStreamReader(inputStream2);
        BufferedReader stationData2 = new BufferedReader(inputreader2);
        // 데이터 입력 단계
        temp_s = stationData2.readLine();//0번째는 '빈역'이기 때문에 제외코드
        while ((temp_s = stationData2.readLine()) != null) {
            String[] split = temp_s.split("	");// tap으로 구분.
            String name_t = split[0];// 역의 이름 저장.
            String line_t = split[1];// 호선 정보 저장. ex(1 or 1, 2, 3)
            list.add(new Station(name_t, line_t));// 역 어레이리스트에 넣기.
        }
    }
}
