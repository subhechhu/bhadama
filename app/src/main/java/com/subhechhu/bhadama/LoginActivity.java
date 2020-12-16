package com.subhechhu.bhadama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoginActivity extends AppCompatActivity {
    EditText editText_phone, editText_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_phone = findViewById(R.id.edittext_login_phone);
        editText_pin = findViewById(R.id.edittext_login_pin);
    }

    public void signUp(View v) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    public void forgotPin(View v){

        View view = getLayoutInflater().inflate(R.layout.otp_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(LoginActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        EditText editText_otp = view.findViewById(R.id.edittext_otp_otp);

        view.findViewById(R.id.button_otp_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.button_otp_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LoginActivity.this,"OTP entered is "+editText_otp.getText().toString(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                renderNewPin();
            }
        });
    }

    private void renderNewPin() {
        View view = getLayoutInflater().inflate(R.layout.newpin_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(LoginActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        EditText editText_pin = view.findViewById(R.id.edittext_newpin_pin);
        EditText edit_confirmpin = view.findViewById(R.id.edittext_newpin_confirmpin);

        view.findViewById(R.id.button_newpin_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.button_newpin_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"New pin has been generated.\nProceed to login",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}