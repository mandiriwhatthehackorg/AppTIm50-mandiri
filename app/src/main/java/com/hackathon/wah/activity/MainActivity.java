package com.hackathon.wah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackathon.wah.R;
import com.hackathon.wah.helper.AppController;
import com.hackathon.wah.helper.PrefManager;
import com.hackathon.wah.helper.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private PrefManager prefManager;

    private TextView titleView;
    private Button buttonView;
    private LinearLayout rekening3, mamilesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefManager = new PrefManager(this);
        prefManager.opened();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();

        final ListView drawerList = findViewById(R.id.drawer_list);
        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("name","Profil Saya");
        arrayList.add(hashMap);
        hashMap=new HashMap<>();
        hashMap.put("name","Pengaturan");
        arrayList.add(hashMap);
        hashMap=new HashMap<>();
        hashMap.put("name","Status Transaksi");
        arrayList.add(hashMap);
        hashMap=new HashMap<>();
        hashMap.put("name","Suku Bunga");
        arrayList.add(hashMap);
        hashMap=new HashMap<>();
        hashMap.put("name","Bantuan");
        arrayList.add(hashMap);
        hashMap=new HashMap<>();
        hashMap.put("name","Tentang Mandiri Online");
        arrayList.add(hashMap);
        hashMap=new HashMap<>();
        hashMap.put("name","Hubungi Kami");
        arrayList.add(hashMap);
        hashMap=new HashMap<>();
        hashMap.put("name","Logout");
        arrayList.add(hashMap);

        String[] from={"name"};//string array
        int[] to={R.id.textView};//int array of views id's
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.list_view_items,from,to);
        drawerList.setAdapter(simpleAdapter);
        rekening3 = findViewById(R.id.rekening3);
        mamilesLayout = findViewById(R.id.mamilesLayout);
        ImageButton plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PIlihKartuActivity.class);
                startActivityForResult(intent, 123);
            }
        });

        rekening3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MamilesActivity.class);
                startActivityForResult(intent, 123);
            }
        });
        mamilesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MamilesActivity.class);
                startActivityForResult(intent, 123);
            }
        });
    }

    private void openNews(){
        final String TAG = "TAG";
        final String tag_string_req = "STR_REQ";

        strReq = new StringRequest(Request.Method.POST,
                Utility.URL_GET_NEWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, String.format("[%s][%s] %s", tag_string_req, Utility.TAG_LOG_RESPONSE, response));
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean(Utility.TAG_ERROR);

                    if(!error) {
                        JSONArray news = jObj.getJSONArray("news");

                        for(int i=0;i<news.length();i++){
                            titleView.setText(news.getJSONObject(i).getString("title"));
                        }

                    } else {
                        String errorMsg = jObj.getString(Utility.TAG_ERROR_MESSAGE);
                        Log.e(TAG, String.format("[%s][%s] %s", tag_string_req, Utility.TAG_LOG_ERROR, errorMsg));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, String.format("[%s][%s] %s", tag_string_req, Utility.TAG_LOG_ERROR, error.getMessage()));
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("home_version", "1");
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private StringRequest strReq;
    @Override
    public void onStop() {
        if(strReq!=null){
            strReq.cancel();
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123){
            rekening3.setVisibility(View.VISIBLE);
            mamilesLayout.setVisibility(View.VISIBLE);
        }
    }
}
