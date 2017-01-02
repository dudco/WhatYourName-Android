package com.example.dudco.whatsyourname;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import java.io.FileNotFoundException;

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

    public void createAddTextDialog(){
        View view = LayoutInflater.from(CreateNameCardActivity.this).inflate(R.layout.custom_dialog, null);

        TextView text = (TextView) view.findViewById(R.id.text_custom_add);
        LinearLayout color = (LinearLayout) view.findViewById(R.id.color_picker);

        AlertDialog.Builder ad = new AlertDialog.Builder(CreateNameCardActivity.this);
        ad.setTitle("텍스트");
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.setView(R.layout.custom_dialog);
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
            case R.id.done : break;
            case android.R.id.home :finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
