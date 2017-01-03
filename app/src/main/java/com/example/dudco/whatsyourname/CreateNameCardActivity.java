package com.example.dudco.whatsyourname;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.JsonObject;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CreateNameCardActivity extends AppCompatActivity {
    StickerView stickerView;
    FloatingActionButton fab;

    private static final int REQ_CODE_ALBUM = 201;
    Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_name_card);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("명함 만들기");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        stickerView = (StickerView) findViewById(R.id.stiker);
        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerClicked(Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    stickerView.replace(sticker);
                    stickerView.invalidate();
                }
                Log.d("dudco", "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(Sticker sticker) {
                Log.d("dudco", "onStickerDeleted");

            }

            @Override
            public void onStickerDragFinished(Sticker sticker) {
                Log.d("dudco", "onStickerDragFinished");

            }

            @Override
            public void onStickerZoomFinished(Sticker sticker) {
                Log.d("dudco", "onStickerZoomFinished");

            }

            @Override
            public void onStickerFlipped(Sticker sticker) {
                Log.d("dudco", "onStickerFlipped");

            }

            @Override
            public void onStickerDoubleTapped(Sticker sticker) {
                Log.d("dudco", "onStickerDoubleTapped");

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"이미지", "텍스트"};
                AlertDialog.Builder ad = new AlertDialog.Builder(CreateNameCardActivity.this);
                ad.setTitle("추가하기");
                ad.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("dudco", which+"");
                                dialog.dismiss();
                                if(which == 0){
                                    addImage();
                                }else{
                                    addText();
                                }
                            }
                        });
                ad.show();
            }
        });
    }
    public void addImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQ_CODE_ALBUM);
    }

    public void addText(){
        createAddTextDialog();
    }

    GridView grid;
    Integer pos;
    public void createAddTextDialog(){
//        View view = LayoutInflater.from(CreateNameCardActivity.this).inflate(R.layout.custom_dialog, null);

//        text = (TextView) view.findViewById(R.id.text_custom_add);
//        color = (Button) view.findViewById(R.id.color_picker);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        final EditText edit = new EditText(this);
        edit.setInputType(InputType.TYPE_CLASS_TEXT);
        edit.setHint("글자");

        final Button btn = new Button(this);
        btn.setText("색깔");

        container.addView(edit);
        container.addView(btn);

        final List<Integer> colors = new ArrayList<>();

        colors.add(R.color.amber);
        colors.add(R.color.black);
        colors.add(R.color.blue);
        colors.add(R.color.blue_grey);
        colors.add(R.color.brown);
        colors.add(R.color.cyan);
        colors.add(R.color.deep_orange);
        colors.add(R.color.deep_purple);
        colors.add(R.color.gray);
        colors.add(R.color.yellow);
        colors.add(R.color.white);
        colors.add(R.color.teal);
        colors.add(R.color.red);
        colors.add(R.color.pink);
        colors.add(R.color.lime);
        colors.add(R.color.orange);
        colors.add(R.color.green);

        grid = new GridView(CreateNameCardActivity.this);
        grid.setClickable(true);

        grid.setAdapter(new CustomGridAdapter(colors));
        grid.setNumColumns(5);


        final AlertDialog.Builder ad_b = new AlertDialog.Builder(CreateNameCardActivity.this);
        ad_b.setView(grid).setTitle("색깔");
        final AlertDialog ad_2 = ad_b.create();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("dudco", "click!");
                ad_2.dismiss();
                btn.setBackgroundResource(colors.get(position));
                pos = position;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dudco", "color clicked");
                ad_2.show();
            }
        });
        AlertDialog.Builder ad = new AlertDialog.Builder(CreateNameCardActivity.this);
        ad.setTitle("텍스트");
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("dudco", edit.getText().toString() + "ㅁㄴㅇㄹ" + pos);
                Log.d("dudco", "확인");
                TextSticker sticker = new TextSticker(CreateNameCardActivity.this);
                sticker.setTextColor(ContextCompat.getColor(CreateNameCardActivity.this, colors.get(pos)));
                sticker.setText(edit.getText().toString());
                sticker.resizeText();

                stickerView.addSticker(sticker);
                dialog.dismiss();
            }
        });
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.setView(container);
        ad.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQ_CODE_ALBUM){
                mImageUri = data.getData();
                try {
                    DrawableSticker sticker = new DrawableSticker(Drawable.createFromStream(getContentResolver().openInputStream(mImageUri),mImageUri.toString()));
                    stickerView.addSticker(sticker);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done :
                reqPermission();

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                String id = pref.getString("id", "null");

                if(!id.equals("null")) {
                    AQuery aq = new AQuery(this);
                    File file = getNewFile(CreateNameCardActivity.this, "Sticker");
                    stickerView.save(file);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("image", file);
                    map.put("id", id);

                    aq.ajax("http://iwin247.net:3000/up", map, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String object, AjaxStatus status) {
//                        super.callback(url, object, status);
                            Log.d("dudco", url);
                            Log.d("dudco", status.getCode() + "    " + status.getMessage());
                            Log.d("dudco", object.toString());
                            finish();
                        }
                    });
                }
                break;
            case android.R.id.home :finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String getFolderName(String name) {
        File mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        name);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return "";
            }
        }
        return mediaStorageDir.getAbsolutePath();
    }
    public static File getNewFile(Context context, String folderName) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);

        String timeStamp = simpleDateFormat.format(new Date());

        String path;
        if (isSDAvailable()) {
            path = getFolderName(folderName) + File.separator + timeStamp + ".jpg";
        } else {
            path = context.getFilesDir().getPath() + File.separator + timeStamp + ".jpg";
        }

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        return new File(path);
    }

    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public void reqPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission_group.STORAGE}, 100);
            }
        }
    }
}
