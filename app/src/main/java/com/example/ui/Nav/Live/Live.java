package com.example.ui.Nav.Live;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Live extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Button line1, line2, line3, line4, line5, line6, line7, line8, line9;
    // 리스트뷰
    private ListView listView;
    // 어댑터
    private LiveAdapter adapter, adapter1, adapter2 ,adapter3, adapter4,adapter5, adapter6, adapter7,adapter8, adapter9, adapterT;
    private  int REQUEST_DATA = 1;
    //현재 라인 텍스트뷰
    private TextView curLine;
    //서버 주소
    private static String IP_ADDRESS = "www.solac.shop";
    private static String TAG = "phptest";
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        //라인별 버튼 생성
        this.makeButton();

        listView = (ListView) findViewById(R.id.live_list);
        listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        //어댑터 생성
        this.makeAdapter();
        // 어뎁터를 리스트뷰에 세팅한다.
        listView.setAdapter(adapter);
        // 리스트뷰에 아이템클릭리스너를 등록한다.
        listView.setOnItemClickListener(this);

        curLine = (TextView)findViewById(R.id.live_cur);

        Toast.makeText(getApplicationContext(), "원하는 게시판 항목을 터치하세요!", Toast.LENGTH_SHORT).show();
    }

    //클릭 리스너
    public void OnClick(View v){
        switch(v.getId()){
            // 1 ~ 9 호선별 게시판 클릭
            case R.id.live_line1:
                adapterT = adapter1;
                setListView();
                doUpdate(1);
                break;
            case R.id.live_line2:
                adapterT = adapter2;
                setListView();
                doUpdate(2);
                break;
            case R.id.live_line3:
                adapterT = adapter3;
                setListView();
                doUpdate(3);
                break;
            case R.id.live_line4:
                adapterT = adapter4;
                setListView();
                doUpdate(4);
                break;
            case R.id.live_line5:
                adapterT = adapter5;
                setListView();
                doUpdate(5);
                break;
            case R.id.live_line6:
                adapterT = adapter6;
                setListView();
                doUpdate(6);
                break;
            case R.id.live_line7:
                adapterT = adapter7;
                setListView();
                doUpdate(7);
                break;
            case R.id.live_line8:
                adapterT = adapter8;
                setListView();
                doUpdate(8);
                break;
            case R.id.live_line9:
                adapterT = adapter9;
                setListView();
                doUpdate(9);
                break;
            //게시글 추가
            case R.id.live_add:
                if(adapterT.getLine().equals("0")){
                    Toast.makeText(getApplicationContext(), "게시판을 선택하세요!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(Live.this, LiveInput.class);
                    startActivityForResult(intent, REQUEST_DATA);//요청 후 결과 돌려받기
                    this.onResume();
                    break;
                }
        }
    }
    //listView 세팅.
    private void setListView(){
        listView.setAdapter(adapterT);
        listView.setOnItemClickListener(this);
        curLine.setText("현재 게시판 : " + adapterT.getLine() + "호선");
    };
    //등록 화면에서 데이터 받은 상황
    protected void onActivityResult(int requestCode, int resultCode, Intent dAta) {
        super.onActivityResult(requestCode, resultCode, dAta);
        if (requestCode == REQUEST_DATA)
            if (resultCode == RESULT_OK) {
                LiveData temp = (LiveData) dAta.getSerializableExtra("data");
                System.out.println(adapterT.getLine()+ " " + temp.getDir() + " " + temp.getData() + " " + temp.getTitle() + " "+ temp.getTime());
                String line = adapterT.getLine();
                String train = temp.getDir();
                String data = temp.getData();
                String title = temp.getTitle();
                String time = temp.getTime();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", line, train, data, title, time);

            }
    }
    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(adapterT);
        // ArrayList에 더미 데이터 입력
        defaultData();

    }
    private void defaultData() {
    }

    //게시판 리스트 클릭 이벤트.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 리스트에서 데이터를 받아온다.
        LiveData data = (LiveData) adapterT.getItem(position);
        Intent intent = new Intent(Live.this, LiveResult.class);
        intent.putExtra("result", data);
        startActivity(intent);
        this.onResume();
    }
    //어댑터 생성 메서드
    public void makeAdapter(){
        adapter = new LiveAdapter("0");
        adapter1 = new LiveAdapter("1");
        adapter2 = new LiveAdapter("2");
        adapter3 = new LiveAdapter("3");
        adapter4 = new LiveAdapter("4");
        adapter5 = new LiveAdapter("5");
        adapter6 = new LiveAdapter("6");
        adapter7 = new LiveAdapter("7");
        adapter8 = new LiveAdapter("8");
        adapter9 = new LiveAdapter("9");
        adapterT = new LiveAdapter("0");
    }
    //버튼 생성 메서드
    public void makeButton(){
        line1 = (Button) findViewById(R.id.live_line1);
        line2 = (Button) findViewById(R.id.live_line2);
        line3 = (Button) findViewById(R.id.live_line3);
        line4 = (Button) findViewById(R.id.live_line4);
        line5 = (Button) findViewById(R.id.live_line5);
        line6 = (Button) findViewById(R.id.live_line6);
        line7 = (Button) findViewById(R.id.live_line7);
        line8 = (Button) findViewById(R.id.live_line8);
        line9 = (Button) findViewById(R.id.live_line9);
    }
    //PHP 데이터 받기
    public void doUpdate(int line){
        GetData task = new GetData();
        switch (line){
            case 1:
                task.execute("http://" + IP_ADDRESS + "/getLive1.php", "");
                break;
            case 2:
                task.execute("http://" + IP_ADDRESS + "/getLive2.php", "");
                break;
            case 3:
                task.execute("http://" + IP_ADDRESS + "/getLive3.php", "");
                break;
            case 4:
                task.execute("http://" + IP_ADDRESS + "/getLive4.php", "");
                break;
            case 5:
                task.execute("http://" + IP_ADDRESS + "/getLive5.php", "");
                break;
            case 6:
                task.execute("http://" + IP_ADDRESS + "/getLive6.php", "");
                break;
            case 7:
                task.execute("http://" + IP_ADDRESS + "/getLive7.php", "");
                break;
            case 8:
                task.execute("http://" + IP_ADDRESS + "/getLive8.php", "");
                break;
            case 9:
                task.execute("http://" + IP_ADDRESS + "/getLive9.php", "");
                break;
        }
    }
    //PHP 데이터 넣기
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Live.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "성공^^", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }
        @Override
        protected String doInBackground(String... params) {

            String line = (String)params[1];
            String train = (String)params[2];
            String data = (String)params[3];
            String title = (String)params[4];
            String time = (String)params[5];


            String serverURL = (String)params[0];
            String postParameters = "line=" + line + "&train=" + train + "&data=" + data + "&title=" + title + "&time=" + time;
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String lines = null;

                while((lines = bufferedReader.readLine()) != null){
                    sb.append(lines);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
    private class GetData extends AsyncTask<String, Void, String> {
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //데이터 다 가져오면 작동하는 메서드.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
            } else {
                adapterT.clearItem();
                mJsonString = result;
                showResult();
                onResume();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String postParameters = params[1];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();
                return null;
            }

        }
    }

    private void showResult() {

        String TAG_JSON = "실시간데이터";
        String TAG_line = "line";
        String TAG_train = "train";
        String TAG_data = "data";
        String TAG_title = "title";
        String TAG_num = "num";
        String TAG_time = "time";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String line = item.getString(TAG_line);
                String train = item.getString(TAG_train);
                String data = item.getString(TAG_data);
                String title = item.getString(TAG_title);
                String num = item.getString(TAG_num);
                String time = item.getString(TAG_time);

                LiveData personalData = new LiveData(line, train, data, title, num, time);

                adapterT.addItem(personalData);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}