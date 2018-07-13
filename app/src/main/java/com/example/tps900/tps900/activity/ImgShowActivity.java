package com.example.tps900.tps900.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.image.ScaleView;




public class ImgShowActivity extends AppCompatActivity {
    Bitmap bitmap;

    ScaleView showImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_show);
        showImage = (ScaleView) findViewById(R.id.show_image);

        initView();
    }

    public void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            byte[] buff = intent.getByteArrayExtra("bitmap");
            bitmap = BitmapFactory.decodeByteArray(buff, 0, buff.length);
            BitmapDrawable mBitmapDrawable = new BitmapDrawable(bitmap);
            showImage.setBackgroundDrawable(mBitmapDrawable);
        }
        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
