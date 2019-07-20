package com.hackathon.wah.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hackathon.wah.R;
import com.hackathon.wah.helper.AppController;
import com.hackathon.wah.helper.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BukaRekeningActivity extends AppCompatActivity {

    private Calendar selectedCalendar = Calendar.getInstance();
    private TextInputLayout dateLayout;
    private AppCompatEditText dateText;
    private ImageView kkView, wefieView;
    private EditText nameView, emailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka_rekening);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dateLayout = findViewById(R.id.date_layout);
        dateText = findViewById(R.id.date);

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                selectedCalendar.set(Calendar.YEAR, year);
                selectedCalendar.set(Calendar.MONTH, monthOfYear);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateLayout.setErrorEnabled(false);
                updateLabel();
            }
        };

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BukaRekeningActivity.this, dateListener, selectedCalendar
                        .get(Calendar.YEAR), selectedCalendar.get(Calendar.MONTH),
                        selectedCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    new DatePickerDialog(BukaRekeningActivity.this, dateListener, selectedCalendar
                            .get(Calendar.YEAR), selectedCalendar.get(Calendar.MONTH),
                            selectedCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        kkView = findViewById(R.id.camerakk);
        kkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BukaRekeningActivity.this, MyCameraActivity.class);
                startActivityForResult(intent, 123);
            }
        });
        wefieView = findViewById(R.id.camerapelajar);
        wefieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BukaRekeningActivity.this, WefieActivity.class);
                startActivityForResult(intent, 125);
            }
        });

        nameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);

        Button button = findViewById(R.id.buat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BukaRekeningActivity.this, BerhasilActivity.class);
                startActivityForResult(intent, 125);
                if(nameView.getText().toString().length()>0 && emailView.getText().toString().length()>0){
                    send();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void send(){
        final String TAG = "TAG";
        final String tag_string_req = "STR_REQ";

        strReq = new StringRequest(Request.Method.GET,
                "http://mamilesapp-wth-50.apps.openshift.mandiriwhatthehack.com/send.php?admincode=ABC123ajij&subject=Proses Pembukaan Rekening Mamiles telah Diterima!&name="+ nameView.getText().toString()+"&to="+ emailView.getText().toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, String.format("[%s][%s] %s", tag_string_req, Utility.TAG_LOG_RESPONSE, response));

                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, String.format("[%s][%s] %s", tag_string_req, Utility.TAG_LOG_ERROR, error.getMessage()));
            }
        });
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode == RESULT_OK){
            File imgFile = new File(Environment.getExternalStorageDirectory()+"/dir","kartukeluarga.jpg");
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                kkView.setImageBitmap(myBitmap);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 200);
                kkView.setLayoutParams(layoutParams);
                kkView.setPadding(0,0,0,0);
                kkView.setBackground(null);
            }
        } else if(requestCode==125 && resultCode == RESULT_OK){
            File imgFile = new File(Environment.getExternalStorageDirectory()+"/dir","wefie.jpg");
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                wefieView.setImageBitmap(myBitmap);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 200);
                wefieView.setLayoutParams(layoutParams);
                wefieView.setPadding(0,0,0,0);
                wefieView.setBackground(null);
            }
        }
    }

    private StringRequest strReq;
    @Override
    public void onStop() {
        if(strReq!=null){
            strReq.cancel();
        }
        super.onStop();
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(selectedCalendar.getTime()));
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
