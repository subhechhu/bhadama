package com.subhechhu.bhadama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.subhechhu.bhadama.AppController;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.signin.SigninActivity;

public class MainActivity extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            if (AppController.getPreferenceBoolean(
                    AppController.getContext().getString(R.string.login_pref))) {
                i = new Intent(MainActivity.this, HomeActivity.class);
            } else {
                i = new Intent(MainActivity.this, SigninActivity.class);
            }
//            i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }, 1500);
    }
}