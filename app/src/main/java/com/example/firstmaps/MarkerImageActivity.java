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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MarkerImageActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 111;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 666;
    String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_image);

        EditText et = findViewById(R.id.edtCoordinates);
        et.setText("[Широта; Долгота]: " +
                getIntent().getDoubleExtra("latitude",0.00 ) + "; " +
                getIntent().getDoubleExtra("longitude",0.00 ));

        String photoPath = getIntent().getStringExtra("photoPath");
        if (!photoPath.isEmpty())
        {
            ImageView iv = findViewById(R.id.iwContent);
            iv.setImageURI(Uri.parse(photoPath));
            currentPhotoPath = photoPath;
        }

        // Создаем обработчик события нажатия кнопки добавления фото
        FloatingActionButton myFab = findViewById(R.id.fabAddPhoto);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePhoto(v);
            }
        });

        // Создаем обработчик события нажатия кнопки Save
        Button btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveAndExit(v);
            }
        });

        // Дизайн
        btSave.setEnabled(false);

    }

    public void takePhoto(View view) {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, TAKE_PICTURE);
        startAcivity();
    }

    public void saveAndExit(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("photoPath", currentPhotoPath);
        setResult(RESULT_OK, intent);
        this.finish(); // closing activity
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            //savePhoto(photo);
            ImageView iv = findViewById(R.id.iwContent);
            iv.setImageURI(Uri.parse(currentPhotoPath));
            findViewById(R.id.btSave).setEnabled(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void startAcivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            }
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println(ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.firstmaps.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }
    }




//    private void savePhoto(Bitmap photo) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
//            }
//        }
//
//        File myDir = new File(root);
//        myDir.mkdirs();
//
//        Date currentTime = Calendar.getInstance().getTime();
//        String currentTimeToString = currentTime.toString().replace(' ', '_');
//        String fname = "Image-" + currentTimeToString + ".jpg";
//
//        File file = new File(myDir, fname);
//
//        if (file.exists())
//            file.delete();
//
//        try {
//        //    file.createNewFile();
//            FileOutputStream out = new FileOutputStream(file);
//            photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//            currentPhotoPath = file.getAbsolutePath();
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
