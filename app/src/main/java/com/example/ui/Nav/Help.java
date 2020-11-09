package com.example.ui.Nav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ui.R;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
    //고객센터 메뉴 리스너-------------------------------------------------------
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.help_notice :
                //intent = new Intent(getBaseContext(), Map.class);
                //startActivity(intent);
                break;
            case R.id.nav_location :
                //intent = new Intent(getBaseContext(), Map.class);
                //startActivity(intent);
                break;
            case R.id.nav_fa :
                //intent = new Intent(getBaseContext(), Map.class);
                //startActivity(intent);
                break;
            case R.id.nav_help :
                //intent = new Intent(getBaseContext(), Map.class);
                //startActivity(intent);
                break;
        }
    }
    //---------------------------------------------------------------------------
}
