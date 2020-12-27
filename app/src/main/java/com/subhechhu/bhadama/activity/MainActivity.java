package com.subhechhu.bhadama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.subhechhu.bhadama.activity.login.SigninActivity;
import com.subhechhu.bhadama.AppController;
import com.subhechhu.bhadama.R;

public class MainActivity extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppController.getBoolean(
                        AppController.getInstance().getString(R.string.login_pref))) {
                    i = new Intent(MainActivity.this, HomeActivity.class);
//                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                } else {
                    i = new Intent(MainActivity.this, SigninActivity.class);
//                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                }
                startActivity(i);
                finish();
            }
        }, 1500);
    }
}