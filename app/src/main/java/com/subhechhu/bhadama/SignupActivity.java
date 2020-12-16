package com.subhechhu.bhadama;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void proceedSignup(View v){
        View view = getLayoutInflater().inflate(R.layout.otp_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(SignupActivity.this);
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
//                Toast.makeText(SignupActivity.this,"OTP entered is "+editText_otp.getText().toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(SignupActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });
    }
}