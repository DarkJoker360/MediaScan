package com.esposito.mediascan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

        Button button = (Button) findViewById(R.id.btn_scan);

        if (!isRooted()){
            TextView noroot_tv = findViewById(R.id.no_root);
            noroot_tv.setVisibility(View.VISIBLE);
        } else {
            TextView scanTxt = findViewById(R.id.textView);
            scanTxt.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            if (button != null) {
                button.setOnClickListener((View.OnClickListener) (it -> {
                    try {
                        Toast.makeText((Context) MainActivity.this, R.string.scan_msg_int, Toast.LENGTH_LONG).show();
                        Runtime.getRuntime().exec("su -c am broadcast -a " +
                                "android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file:///sdcard");
                    } catch (IOException e) {
                        Toast.makeText((Context) MainActivity.this, R.string.err_msg_int, Toast.LENGTH_LONG).show();
                    }
                    try {
                        Toast.makeText((Context) MainActivity.this, R.string.scan_msg_ext, Toast.LENGTH_LONG).show();
                        Runtime.getRuntime().exec("su -c am broadcast -a " +
                                "android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file:///sdcard1");
                    } catch (IOException e) {
                        Toast.makeText((Context) MainActivity.this, R.string.err_msg_ext, Toast.LENGTH_LONG).show();
                    }
                    try {
                        Toast.makeText((Context) MainActivity.this, R.string.scan_msg_otg, Toast.LENGTH_LONG).show();
                        Runtime.getRuntime().exec("su -c am broadcast -a " +
                                "android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file:///usb-otg");
                    } catch (IOException e) {
                        Toast.makeText((Context) MainActivity.this, R.string.err_msg_otg, Toast.LENGTH_LONG).show();
                    }
                }));
            }
        }
    }

    private boolean isRooted() {
        try {
            Runtime.getRuntime().exec("su");
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
}