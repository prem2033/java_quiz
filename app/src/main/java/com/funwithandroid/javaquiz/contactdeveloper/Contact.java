package com.funwithandroid.javaquiz.contactdeveloper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class Contact {
    public  static void callDeveloper(Context context){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+919973950290"));
        try {
            context.startActivity(callIntent);
        } catch (Exception e) {
            Toast.makeText(context, "No Call Client found", Toast.LENGTH_SHORT).show();
        }
    }
    public static void messageDeveloper(Context context){
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String("+919973950290"));
        smsIntent.putExtra("sms_body", "write your message..");
        try {
            context.startActivity(smsIntent);
        } catch (Exception e) {
            Toast.makeText(context, "No SMS client found", Toast.LENGTH_SHORT).show();
        }
    }
    public static void mailDeveloper(Context context){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"premprakash2033@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"prakashprem2033@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_BCC,"");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject here...");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here....");
        try {
            context.startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "No email clients found.",Toast.LENGTH_SHORT).show();
        }

    }

}
