package com.example.ui.Nav;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.R;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Claim extends AppCompatActivity {
    //입력 데이터 TextView 생성
    private TextView train, data, phone;
    //서버 주소
    private static String IP_ADDRESS = "www.solac.shop";
    private static String TAG = "phptest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        train = (TextView) findViewById(R.id.claim_train);//열차정보
        data = (TextView) findViewById(R.id.claim_data);//민원 내
        phone = (TextView) findViewById((R.id.claim_phone));//휴대폰 번호용



    }
    public void onClick(View v){
        if(v.getId() == R.id.claim_add) {
            //디비 인서트
            String iTrain = train.getText().toString();
            String iData = data.getText().toString();
            String iPhone = phone.getText().toString();
            InsertData task = new InsertData();
            task.execute("http://" + IP_ADDRESS + "/claim.php", iTrain, iData, iPhone);
        }
    }

    //PHP 데이터 넣기
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Claim.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "민원이 접수되었습니다.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }
        @Override
        protected String doInBackground(String... params) {

            String train = (String)params[1];
            String data = (String)params[2];
            String phone = (String)params[3];


            String serverURL = (String)params[0];
            String postParameters = "train=" + train + "&data=" + data + "&phone=" + phone;
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
}