package com.example.dudco.whatsyourname;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dudco.whatsyourname.Data.UserData;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json_userdata = getIntent().getStringExtra("user");
        String bActivity = getIntent().getStringExtra("bActivity");

        if(bActivity.equals("RegisterActivity")){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Notice!")
                    .setMessage("기본 설정을 위해 설정 화면으로 이동합니다")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(MainActivity.this, ConfigActivity.class));
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        UserData user = new Gson().fromJson(json_userdata, UserData.class);

        Log.d("dudco", user.getName());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.config :  startActivity(new Intent(MainActivity.this, ConfigActivity.class)); break;
            case R.id.create :
                startActivity(new Intent(MainActivity.this, CreateNameCardActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
