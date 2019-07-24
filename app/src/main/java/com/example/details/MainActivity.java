package com.example.details;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView UserName ;
    String myURL = "https://cyberdukaan.com/mobi/dukaan/agri/details/";
    EditText address,district,state;
    ImageView userpic;
    String[] urls = {"https://cyberdukaan.com/mobi/dukaan/img/IptvVnwJmNUCDdEAOZ6Jv0IuVy5DnM9CTFvjNTvCk/7A5D8BC1207C4434E0537E8B12B65F2C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = findViewById(R.id.button);
        UserName = findViewById(R.id.tvusername);
        address = findViewById(R.id.etaddress);
        district = findViewById(R.id.etdistrict);
        state = findViewById(R.id.etstate);
        userpic =findViewById(R.id.userpic);
        Glide.with(this).load(urls[0]).into(userpic);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.i("PAGE","WEB "+s);
                                try {
                                    JSONObject obj = new JSONObject(s);
                                    JSONObject jsonObject = obj.getJSONObject("RESPONSE");
                                    JSONObject Name = jsonObject.getJSONObject("NAME");
                                    UserName.setText(Name.getString("NAME"));
                                    address.setText(Name.getString("ADDRESS"));
                                    district.setText(Name.getString("SHOPNAME"));
                                    state.setText(Name.getString("STATE"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("ERROR","ERROR is "+volleyError);
                            }
                        }
                ){
                    @Override
                    protected Map<String,String> getParams()throws AuthFailureError {
                        Map<String,String> parmams = new HashMap<>();
                        parmams.put("a", "{\"items\":{\"API\":\"EAP_AGR_STORAGE_G\",\"CURD_OPERATION\":\"G\",\"USERNAME\":\"7777777710\",\"LANGUAGE\":\"ENG\"}}");
                        return parmams;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

    }
}
