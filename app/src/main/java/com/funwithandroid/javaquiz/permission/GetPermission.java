package com.funwithandroid.javaquiz.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class GetPermission {
    public static boolean CheckPermissions(Context context) {
        int result = ContextCompat.checkSelfPermission(context, CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED ;
    }
    public static void RequestPermissions(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{CALL_PHONE}, 1);
    }
    public static boolean CheckPermissionsSMS(Context context) {
        int result = ContextCompat.checkSelfPermission(context, SEND_SMS);
        return result == PackageManager.PERMISSION_GRANTED ;
    }
    public static void RequestPermissionsSMS(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{SEND_SMS}, 1);
    }
}
