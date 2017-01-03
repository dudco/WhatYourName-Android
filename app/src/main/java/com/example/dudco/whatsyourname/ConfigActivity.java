package com.example.dudco.whatsyourname;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.dudco.whatsyourname.Data.UserData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class ConfigActivity extends AppCompatActivity {
    private EditText edit_email, edit_career, edit_belong, edit_pax, edit_des;
    private AQuery aq = new AQuery(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("설정");

        edit_email = (EditText) findViewById(R.id.edit_config_email);
        edit_career = (EditText) findViewById(R.id.edit_config_career);
        edit_belong = (EditText) findViewById(R.id.edit_config_belong);
        edit_pax = (EditText) findViewById(R.id.edit_config_pax);
        edit_des = (EditText) findViewById(R.id.edit_config_des);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : finish(); break;
            case R.id.done :
                SharedPreferences sp = getSharedPreferences("pref", MODE_PRIVATE);
                String id = sp.getString("id", "null");

                if(!id.equals("null")) {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("id", id);

                    if (!edit_email.getText().toString().isEmpty()) {
                        params.put("email", edit_email.getText().toString());
                    }
                    if (!edit_career.getText().toString().isEmpty()) {
                        params.put("career", edit_career.getText().toString());
                    }
                    if (!edit_belong.getText().toString().isEmpty()) {
                        params.put("belong", edit_belong.getText().toString());
                    }
                    if (!edit_pax.getText().toString().isEmpty()) {
                        params.put("pax", edit_pax.getText().toString());
                    }
                    if (!edit_des.getText().toString().isEmpty()) {
                        params.put("des", edit_des.getText().toString());
                    }

                    aq.ajax("http://iwin247.net:3000/update", params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String object, AjaxStatus status) {
//                        Log.d("dudco", status.getCode() + "  " + status.getMessage());
//                        Log.d("dudco", url);
//                        Log.d("dudco", status.getCode() +"  " + status.getMessage());
//                        UserData user = new Gson().fromJson(object, UserData.class);
//                        Log.d("dudco", user.toString());
                            if (status.getCode() == 200) {
                                Toast.makeText(ConfigActivity.this, "업데이트 성공!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
