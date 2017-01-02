package com.example.dudco.whatsyourname;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dudco.whatsyourname.Data.UserData;
import com.example.dudco.whatsyourname.Data.UserService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner_year, spinner_month, spinner_day;
    private EditText edit_id, edit_pass, edit_repass, edit_name, edit_phone;
    private Button btn_submit;
    private ArrayAdapter<String> adapter_year,adapter_month,adapter_day;
    private String year, month, day;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_id = (EditText) findViewById(R.id.edit_reg_id);
        edit_pass = (EditText) findViewById(R.id.edit_reg_pass);
        edit_repass = (EditText) findViewById(R.id.edit_reg_repass);
        edit_name = (EditText) findViewById(R.id.edit_reg_name);
        edit_phone = (EditText) findViewById(R.id.edit_reg_phone);

        btn_submit = (Button) findViewById(R.id.btn_reg_submit);

        spinner_year = (Spinner) findViewById(R.id.spinner_reg_year);
        spinner_month = (Spinner) findViewById(R.id.spinner_reg_month);
        spinner_day = (Spinner) findViewById(R.id.spinner_reg_day);

        adapter_year = new ArrayAdapter<>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter_month = new ArrayAdapter<>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter_day = new ArrayAdapter<>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Calendar cal = new GregorianCalendar(Locale.KOREA);

        for(int i = 1900; i <= cal.get(Calendar.YEAR); i++){
            adapter_year.add(i+"년");
        }

        spinner_year.setAdapter(adapter_year);
        spinner_year.setOnItemSelectedListener(this);
        for(int i = 1; i < 13; i++){
            adapter_month.add(i+"월");
        }
        spinner_month.setAdapter(adapter_month);
        spinner_month.setOnItemSelectedListener(this);

        for(int i = 1; i < 32; i++){
            adapter_day.add(i+"일");
        }
        spinner_day.setAdapter(adapter_day);
        spinner_day.setOnItemSelectedListener(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edit_id.getText().toString();
                String pass = edit_pass.getText().toString();
                String name = edit_name.getText().toString();
                String[] _year = year.split("년");
                String[] _month = month.split("월");
                String[] _day = day.split("일");
                String phone_string = edit_phone.getText().toString();
                String phone = phone_string.substring(0, 3) + "." + phone_string.substring(3, 7) + "." + phone_string.substring(7, 11);
                String birth = _year[0] + "." + _month[0] + "." + _day[0];

                new RegAsynTask().execute(id, pass, name, phone, birth);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_reg_year : year = adapter_year.getItem(position); break;
            case R.id.spinner_reg_month : month = adapter_month.getItem(position); break;
            case R.id.spinner_reg_day : day = adapter_day.getItem(position); break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class RegAsynTask extends AsyncTask<String, Void, UserData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog.Builder(RegisterActivity.this)
                    .setTitle("회원가입")
                    .setMessage("회원가입 중 입니다...")
                    .show();
        }

        @Override
        protected UserData doInBackground(String... params) {
            try {
                Response<UserData> res = Util.retrofit.create(UserService.class).register(params[0], params[1], params[2], params[3], params[4]).execute();
                if(res.code() == 200){
                    return res.body();
                }else{
                    return null;
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

            if(userData == null) {
                Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                edit_id.setText("");
            }else{
                Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                String json_userdata = new Gson().toJson(userData);
                intent.putExtra("user", json_userdata);
                intent.putExtra("bActivity", "RegisterActivity");
                startActivity(intent);
                finish();
            }
        }
    }
}
