package com.thealteria.cameracapture;

import android.app.ActionBar;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static com.thealteria.cameracapture.MainActivity.DIRECTORY_NAME;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImageActivity";
    GridView gridView;
    private String pathImage;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pathImage = Environment.getExternalStorageDirectory().toString() + File.separator + DIRECTORY_NAME;

        gridView = findViewById(R.id.gridView);
        arrayList = new ArrayList<>();

        getFromSdcard();

        GridAdapter gridAdapter = new GridAdapter(this, arrayList);
        gridView.setAdapter(gridAdapter);
    }

    public void getFromSdcard() {
        File file = new File(pathImage);

        File[] listFile = file.listFiles();

        if (listFile != null) {
            for (File mFile : listFile) {
                if (file.isDirectory()) {
                    arrayList.add(mFile.getPath());
                }
            }
        } else {
            Log.i(TAG, "getFromSdcard: null array");
        }
    }
}
