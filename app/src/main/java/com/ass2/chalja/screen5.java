package com.ass2.chalja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;

public class screen5 extends AppCompatActivity {

    ImageView add, setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);
        add=findViewById(R.id.add);
        setting=findViewById(R.id.IDsetting);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(screen5.this, "Screen Shot Taken", Toast.LENGTH_SHORT).show();
                takeScreenShot(setting);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(screen5.this, SearchContacts.class));
            }
        });
    }

    public void takeScreenShot(View view){
        View view1 = view.getRootView();
        Bitmap bitmap = Bitmap.createBitmap(view1.getWidth(), view1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view1.draw(canvas);
        File fileScreenShot = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                Calendar.getInstance().getTime().toString() + ".jpg");
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(fileScreenShot);
            bitmap.compress(Bitmap.CompressFormat.JPEG
                    ,100
                    ,fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}