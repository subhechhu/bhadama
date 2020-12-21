package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.subhechhu.bhadama.R;

public class SigninActivity extends AppCompatActivity {
    EditText editText_phone, editText_pin;
    AppCompatButton button_login_phone_verify, button_login_pin_verify, button_login_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);

        editText_phone = findViewById(R.id.edittext_home_location);
        editText_pin = findViewById(R.id.edittext_login_pin);

        button_login_phone_verify = findViewById(R.id.button_login_phone_verify);
        button_login_pin_verify = findViewById(R.id.button_login_pin_verify);
        button_login_signin = findViewById(R.id.button_login_signin);

        editText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.toString().length() == 10)
                    button_login_phone_verify.setVisibility(View.INVISIBLE);
                else
                    button_login_phone_verify.setVisibility(View.VISIBLE);
            }
        });

        editText_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.toString().length() == 4)
                    button_login_pin_verify.setVisibility(View.INVISIBLE);
                else
                    button_login_pin_verify.setVisibility(View.VISIBLE);
            }
        });

        button_login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!verifyPhone(editText_phone)) {
                    Toast.makeText(SigninActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else if (editText_pin.getText().toString().length() != 4) {
                    Toast.makeText(SigninActivity.this, "Invalid Pin", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                }
            }
        });
    }

    public void signUp(View v) {
        startActivity(new Intent(SigninActivity.this, SignupActivity.class));
    }

    public void forgotPin(View v) {

        if (editText_phone.getText().toString().length() != 10) {
            Toast.makeText(SigninActivity.this, "Please enter phone number to proceed", Toast.LENGTH_LONG).show();
        } else {
            View view = getLayoutInflater().inflate(R.layout.otp_dialog, null);
            BottomSheetDialog dialog = new BottomSheetDialog(SigninActivity.this, R.style.dialogStyle);
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
                    Toast.makeText(SigninActivity.this, "OTP entered is " + editText_otp.getText().toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    renderNewPin();
                }
            });
        }
    }

    private void renderNewPin() {
        View view = getLayoutInflater().inflate(R.layout.newpin_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(SigninActivity.this, R.style.dialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        EditText editText_pin = view.findViewById(R.id.edittext_newpin_pin);
        EditText edit_confirmpin = view.findViewById(R.id.edittext_newpin_reenterpin);

        AppCompatButton button_newpin_pin_verify = view.findViewById(R.id.button_newpin_pin_verify);
        AppCompatButton button_newpin_reenterpin_verify = view.findViewById(R.id.button_newpin_reenterpin_verify);


        editText_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (verifyPin(editText_pin, edit_confirmpin, button_newpin_reenterpin_verify)) {
                    button_newpin_pin_verify.setVisibility(View.INVISIBLE);
                } else {
                    button_newpin_pin_verify.setVisibility(View.VISIBLE);
                }
            }
        });

        edit_confirmpin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (verifyReenterPin(edit_confirmpin, editText_pin)) {
                    button_newpin_reenterpin_verify.setVisibility(View.INVISIBLE);
                } else {
                    button_newpin_reenterpin_verify.setVisibility(View.VISIBLE);
                }
            }
        });


        view.findViewById(R.id.button_newpin_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.button_newpin_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed(dialog, editText_pin, edit_confirmpin, button_newpin_reenterpin_verify);
            }
        });
    }

    private void proceed(BottomSheetDialog dialog, EditText edittext_Login_Reenterpin, EditText edittext_login_pin, AppCompatButton button_newpin_reenterpin_verify) {
        if (verifyPin(edittext_login_pin, edittext_Login_Reenterpin, button_newpin_reenterpin_verify) &&
                verifyReenterPin(edittext_Login_Reenterpin, edittext_login_pin)) {
            Toast.makeText(SigninActivity.this, "New pin has been generated.\nProceed to login", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    private boolean verifyReenterPin(EditText edittext_login_reenterpin, EditText edittext_login_pin) {
        if (edittext_login_reenterpin.getText().toString().length() == 4 && !edittext_login_reenterpin.getText().toString().equals(edittext_login_pin.getText().toString())) {
            Toast.makeText(SigninActivity.this, "Pin did not match", Toast.LENGTH_LONG).show();
        }
        return edittext_login_reenterpin.getText().toString().equals(edittext_login_pin.getText().toString());
    }

    private boolean verifyPin(EditText edittext_login_pin, EditText edittextSignupReenterpin, AppCompatButton button_signup_reenterpin_verify) {
        if (edittext_login_pin.getText().toString().length() == 4 && edittext_login_pin.getText().toString().equals(edittextSignupReenterpin.getText().toString())) {
            button_signup_reenterpin_verify.setVisibility(View.INVISIBLE);
        } else {
            if (edittext_login_pin.getText().toString().length() == 4)
                button_signup_reenterpin_verify.setVisibility(View.VISIBLE);
        }
        return edittext_login_pin.getText().toString().length() == 4;
    }

    private boolean verifyPhone(EditText editText_phone) {
        return editText_phone.getText().toString().length() == 10;
    }
}