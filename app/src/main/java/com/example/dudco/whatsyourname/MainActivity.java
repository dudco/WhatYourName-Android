package com.example.dudco.whatsyourname;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.example.dudco.whatsyourname.Data.UserData;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    UserData user;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image_main);

        String json_userdata = getIntent().getStringExtra("user");
        String bActivity = getIntent().getStringExtra("bActivity");

        user = new Gson().fromJson(json_userdata, UserData.class);

        Log.d("dudco", user.getName());

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", user.getId());
        editor.putString("pass", user.getPass());
        editor.commit();



        if(bActivity.equals("RegisterActivity")){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Notice!")
                    .setMessage("기본 설정을 위해 설정 화면으로 이동할까요?")
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNameCard();
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

    public void checkNameCard(){
        if(user.getName_card() == null){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CreateNameCardActivity.class));
                }
            });
        }else{
            AQuery aq = new AQuery(this);
            aq.id(imageView).image("http://iwin247.net:3000/download/"+user.getName_card());
        }
    }
}
