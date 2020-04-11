package com.funwithandroid.javaquiz.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.CountDownTimer;
import android.view.Window;
import android.widget.TextView;

import com.funwithandroid.javaquiz.R;

public class ViewDialog {

    public void showDialogcorrect(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogbox);
        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {         }
            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        }.start();
        dialog.show();

    }
    public void showDialogwrong(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogboxwrong);
        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {         }
            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        }.start();
        dialog.show();

    }
}
