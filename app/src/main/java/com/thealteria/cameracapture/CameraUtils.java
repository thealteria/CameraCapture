package com.thealteria.cameracapture;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.thealteria.cameracapture.MainActivity.DIRECTORY_NAME;
import static com.thealteria.cameracapture.MainActivity.IMAGE_EXTENSION;

class CameraUtils {
    private static final String TAG = "CameraUtils";

    static void refreshGallery(Context context, String path) {
        MediaScannerConnection.scanFile(context,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    static File createPhoto() {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_NAME).toString());

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.e(TAG, "Failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File image = null;

        try {
            image = new File(storageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + IMAGE_EXTENSION);
        } catch (Exception e) {
            Log.e(TAG, "createPhoto: " + e.getMessage());
        }

        return image;
    }
}
