package com.example.firstmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.Toast;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MarkerImageActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 111;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 666;
    private static Uri imageUri;
    String currentPhotoPath;
    //Marker mapMarker;
    private String root = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_image);

        //кнопка добавления фото
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fabAddPhoto);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePhoto(v);
            }
        });

    }

    public void takePhoto(View view) {
        getPermissions();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            savePhoto(photo);
            ImageView iw = findViewById(R.id.iwContent);
        //    iw.setImageURI(Uri.parse(mapMarker.filePath));
            iw.setImageURI(Uri.parse(currentPhotoPath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void savePhoto(Bitmap photo) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            }
        }

        File myDir = new File(root);
        myDir.mkdirs();

        Date currentTime = Calendar.getInstance().getTime();
        String currentTimeToString = currentTime.toString().replace(' ', '_');
        String fname = "Image-" + currentTimeToString + ".jpg";

        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();

        try {
        //    file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            currentPhotoPath = file.getAbsolutePath();
        //    images.add(file.getAbsolutePath());
        //    Log.d(TAG,"Путь к изображению "+file.getAbsolutePath());


            /*
            MapsActivity.realm.beginTransaction();
            Paths path = new Paths();
            path.setPathToPhoto(file.getAbsolutePath());
            MapsActivity.realm.copyToRealm(path);
            Log.d(TAG,"Добавили путь в базу");
            Log.d(TAG,"Lat " + lat);
            Log.d(TAG,"Lng " + lng);
            Log.d(TAG,"Добавили путь в базу");
            Mark mark = MapsActivity.realm.where(Mark.class)
                    .equalTo("lat", Double.valueOf( lat))
                    .equalTo("lng", lng)
                    .findFirst();
            Log.d(TAG,"Прикрепляем изображение к выбранной метке lat"+mark.getLat()+" lng "+mark.getLng());
            mark.addPath(path);
            Log.d(TAG,"Изображение прикреплено");
            MapsActivity.realm.commitTransaction();
             */
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    /* Права на создание файла getPermissions */
    private void getPermissions(){
        int MY_WRITE_EXTERNAL_REQUEST  = 1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_WRITE_EXTERNAL_REQUEST);
            }
        }
    }

}
