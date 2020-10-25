package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Favorites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
    }

    public void OnClick(View v) {
        Toast.makeText(getApplicationContext(), "아직 구현 전이지~", Toast.LENGTH_LONG).show();
    }
}
