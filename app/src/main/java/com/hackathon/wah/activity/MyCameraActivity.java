package com.hackathon.wah.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hackathon.wah.R;
import com.hackathon.wah.widget.CapturePreview;

public class MyCameraActivity extends Activity
{

    private CapturePreview capturePreview;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MyCameraActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    123);
        } else {
            // Permission has already been granted
        }
        if (ContextCompat.checkSelfPermission(MyCameraActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        } else {
            // Permission has already been granted
        }
        if (ContextCompat.checkSelfPermission(MyCameraActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    123);
        } else {
            // Permission has already been granted
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        capturePreview = findViewById(R.id.capture);

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ImageButton cameraButton = findViewById(R.id.camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePreview.takeAPicture(MyCameraActivity.this);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                onBackPressed();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}