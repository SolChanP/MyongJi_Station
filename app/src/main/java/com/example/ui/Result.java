package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


public class Result extends AppCompatActivity {
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = (TextView)findViewById(R.id.result);
        Intent intent = getIntent();
        String tresult = intent.getExtras().getString("result");
        result.setText(tresult);





    }
}
