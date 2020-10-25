package com.example.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class Map extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //노선도 줌인 줌아웃
        PhotoView photoView = findViewById(R.id.map_view);
        photoView.setImageResource(R.drawable.map);
        //노선도 초기 풀 스크린
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }

}
