package com.thealteria.cameracapture;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import static com.thealteria.cameracapture.MainActivity.TAG;

class PermissionsChecker {
    private Context mContext;
//    private PermissionListener permissionlistener;

    PermissionsChecker(Context context) {
        this.mContext = context;
//        this.permissionlistener = permissionlistener;
    }

    void setPermission() {
        TedPermission.with(mContext)
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .check();
    }

    private PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Log.i(TAG, "onPermissionGranted: granted");
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(mContext, "Please grant the permissions in order to use the app",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
