package com.subhechhu.bhadama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SignupActivity extends AppCompatActivity {

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText edittext_signup_name, edittext_signup_phone, edittext_signup_pin, edittext_signup_reenterpin, edittext_signup_email;
        AppCompatButton button_signup_phone_verify, button_signup_pin_verify, button_signup_reenterpin_verify, button_signup_email_verify, button_signup_proceed;

        edittext_signup_email = findViewById(R.id.edittext_signup_email);
        edittext_signup_reenterpin = findViewById(R.id.edittext_signup_reenterpin);
        edittext_signup_pin = findViewById(R.id.edittext_signup_pin);
        edittext_signup_phone = findViewById(R.id.edittext_signup_phone);
        edittext_signup_name = findViewById(R.id.edittext_signup_name);

        button_signup_email_verify = findViewById(R.id.button_signup_email_verify);
        button_signup_reenterpin_verify = findViewById(R.id.button_signup_reenterpin_verify);
        button_signup_pin_verify = findViewById(R.id.button_signup_pin_verify);
        button_signup_phone_verify = findViewById(R.id.button_signup_phone_verify);
        button_signup_proceed = findViewById(R.id.button_signup_proceed);

        edittext_signup_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (verifyEmail(edittext_signup_email)) {
                    button_signup_email_verify.setVisibility(View.INVISIBLE);
                } else {
                    button_signup_email_verify.setVisibility(View.VISIBLE);
                }
            }
        });

        edittext_signup_reenterpin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (verifyReenterPin(edittext_signup_reenterpin, edittext_signup_pin)) {
                    button_signup_reenterpin_verify.setVisibility(View.INVISIBLE);
                } else {
                    button_signup_reenterpin_verify.setVisibility(View.VISIBLE);
                }
            }
        });

        edittext_signup_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (verifyPin(edittext_signup_pin, edittext_signup_reenterpin, button_signup_reenterpin_verify)) {
                    button_signup_pin_verify.setVisibility(View.INVISIBLE);
                } else {
                    button_signup_pin_verify.setVisibility(View.VISIBLE);
                }
            }
        });

        edittext_signup_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (verifyphone(edittext_signup_phone)) {
                    button_signup_phone_verify.setVisibility(View.INVISIBLE);
                } else {
                    button_signup_phone_verify.setVisibility(View.VISIBLE);
                }
            }
        });

        button_signup_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edittext_signup_name.getText().toString().isEmpty() &&
                        verifyEmail(edittext_signup_email) &&
                        verifyReenterPin(edittext_signup_reenterpin, edittext_signup_pin) &&
                        verifyPin(edittext_signup_pin, edittext_signup_reenterpin, button_signup_reenterpin_verify) &&
                        verifyphone(edittext_signup_phone)) {
                    proceedSignup();
                } else {
                    Toast.makeText(SignupActivity.this, "All fields must be entered", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean verifyEmail(EditText edittext_signup_email) {
        return edittext_signup_email.getText().toString().trim().matches(emailPattern);
    }

    private boolean verifyReenterPin(EditText edittextSignupReenterpin, EditText edittext_signup_pin) {
        if (edittextSignupReenterpin.getText().toString().length() == 4 && !edittextSignupReenterpin.getText().toString().equals(edittext_signup_pin.getText().toString())) {
            Toast.makeText(SignupActivity.this, "Pin did not match", Toast.LENGTH_LONG).show();
        }
        return edittextSignupReenterpin.getText().toString().equals(edittext_signup_pin.getText().toString());
    }

    private boolean verifyPin(EditText edittext_signup_pin, EditText edittextSignupReenterpin, AppCompatButton button_signup_reenterpin_verify) {
        if (edittext_signup_pin.getText().toString().length() == 4 && edittext_signup_pin.getText().toString().equals(edittextSignupReenterpin.getText().toString())) {
            button_signup_reenterpin_verify.setVisibility(View.INVISIBLE);
        } else {
            if (edittext_signup_pin.getText().toString().length() == 4)
                button_signup_reenterpin_verify.setVisibility(View.VISIBLE);
        }
        return edittext_signup_pin.getText().toString().length() == 4;
    }

    private boolean verifyphone(EditText edittext_signup_phone) {
        return edittext_signup_phone.getText().toString().length() == 10;
    }


    public void proceedSignup() {
        View view = getLayoutInflater().inflate(R.layout.otp_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(SignupActivity.this, R.style.dialogStyle);
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
                Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });
    }
}