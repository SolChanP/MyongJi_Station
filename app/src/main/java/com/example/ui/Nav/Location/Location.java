package com.example.ui.Nav.Location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ui.Nav.Location.TrainMap.Line1;
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
import java.util.Timer;
import java.util.TimerTask;

public class Location extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static String IP_ADDRESS = "www.solac.shop";
    private static String TAG = "phptest";
    private String mJsonString;
    private TrainAdapter mAdapter;
    private ListView listView;


    private Timer m_timer;
    private TimerTask m_task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        listView = (ListView) findViewById(R.id.loc_list);

        mAdapter = new TrainAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        Toast.makeText(getApplicationContext(), "데이터 불러오는 중...", Toast.LENGTH_SHORT).show();
        m_timer = new Timer();
        m_task = new TimerTask() {
            @Override
            public void run() {
                doUpdate();
            }
        };
        m_timer.schedule(m_task, 1000, 1000);


    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TrainData data = (TrainData) mAdapter.getItem(position);
        Intent intent;
        switch (data.getLine()){
            case "1호선 상행":
            case "1호선 하행":
                intent = new Intent(Location.this, Line1.class);
                intent.putExtra("result", data);
                startActivity(intent);
                break;
        }
    }

    public void doUpdate(){
        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/getjson.php", "");
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
                mAdapter.clearItem();
                mJsonString = result;
                showResult();
                mAdapter.notifyDataSetChanged();
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

        String TAG_JSON = "열차 데이터";
        String TAG_line = "line";
        String TAG_cur_station = "cur_station";
        String TAG_rem_next_time = "rem_next_time";
        String TAG_next_station = "next_station";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String line = item.getString(TAG_line);
                String cur_station = item.getString(TAG_cur_station);
                String next_station = item.getString(TAG_next_station);
                String rem_next_time = item.getString(TAG_rem_next_time);

                TrainData personalData = new TrainData();

                personalData.setLine(line);
                personalData.setCur_station(cur_station);
                personalData.setNext_station(next_station);
                personalData.setRem_next_time(rem_next_time);

                mAdapter.addItem(personalData);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
