package com.example.dudco.whatsyourname;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dudco.whatsyourname.Data.UserData;
import com.example.dudco.whatsyourname.Data.UserService;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edit_id, edit_pass;
    private Button btn_submit, btn_reg;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_id = (EditText) findViewById(R.id.edit_login_id);
        edit_pass = (EditText) findViewById(R.id.edit_login_pass);

        btn_submit = (Button) findViewById(R.id.btn_login_submit);
        btn_reg = (Button) findViewById(R.id.btn_login_reg);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsynTask().execute(edit_id.getText().toString(), edit_pass.getText().toString());
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    class LoginAsynTask extends AsyncTask<String, Void, UserData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog.Builder(LoginActivity.this)
                    .setTitle("로그인")
                    .setMessage("로그인 중 입니다...")
                    .show();
        }

        @Override
        protected UserData doInBackground(String... params) {
            try {
                Response<UserData> res = Util.retrofit.create(UserService.class).login(params[0], params[1]).execute();
                if(res.code() == 200){
                    return res.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(UserData userData) {
            super.onPostExecute(userData);
            dialog.dismiss();
            if(userData != null){
                Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String json_userdata = new Gson().toJson(userData);
                intent.putExtra("user", json_userdata);
                intent.putExtra("bActivity", "LoginActivity");
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
