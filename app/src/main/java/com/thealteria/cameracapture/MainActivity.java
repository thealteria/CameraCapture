package com.thealteria.cameracapture;

import android.Manifest;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private ImageView imageView;

    private static final int REQUEST_TAKE_PHOTO = 100;
    public static final String DIRECTORY_NAME = "CameraCapture";
    public static final String IMAGE_EXTENSION = ".jpg";
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.mImageView);

        PermissionsChecker permissionsChecker = new PermissionsChecker(getApplicationContext());
        //permissionlistener
        permissionsChecker.setPermission();
    }


    public void bGallery(View view) {
        Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
        startActivity(intent);
    }

    public void bCapture(View view) {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile;

            photoFile = CameraUtils.createPhoto();
            if (photoFile != null) {
                path = photoFile.getAbsolutePath();
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getApplicationContext(),
                        getApplicationContext().getPackageName() + ".provider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePic, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {

                CameraUtils.refreshGallery(getApplicationContext(), path);

                Glide.with(this).load(path).apply(
                        new RequestOptions().override(400, 400)).into(imageView);
                Toast.makeText(getApplicationContext(), "Photo saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}
