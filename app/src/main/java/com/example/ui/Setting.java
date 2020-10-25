package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    //설정 약관 및 정책 카테고리 클릭 리스너-----------------------------------------------
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){

            case R.id.setting_1 :
            case R.id.setting_1_btn :
            case R.id.setting_1_layout :
                //intent = new Intent(getBaseContext(), Map.class);
                //startActivity(intent);
                break;
            case R.id.setting_2 :
            case R.id.setting_2_btn :
            case R.id.setting_2_layout :
                //intent = new Intent(getBaseContext(), Map.class);
                //startActivity(intent);
                break;
            case R.id.setting_3 :
            case R.id.setting_3_btn :
            case R.id.setting_3_layout :
                //intent = new Intent(getBaseContext(), Map.class);
                //startActivity(intent);
                break;
        }
    }
    //---------------------------------------------------------------------------------
}
