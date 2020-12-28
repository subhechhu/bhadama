package com.subhechhu.bhadama.activity.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.subhechhu.bhadama.AppController;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageOne;
import com.subhechhu.bhadama.util.GetConstants;
import com.subhechhu.bhadama.util.GetUrl;
import com.subhechhu.bhadama.util.Network;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText edittext_signup_name, edittext_signup_phone, edittext_signup_pin, edittext_signup_reenterpin, edittext_signup_email;
        AppCompatButton button_signup_phone_verify, button_signup_pin_verify, button_signup_reenterpin_verify, button_signup_email_verify, button_signup_proceed;

        ProgressBar progressBar_signup = findViewById(R.id.progressBar_signup);

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

        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);
        signupViewModel.signupResponse().observe(this, userResponse -> {
            Log.d(TAG, "user" + userResponse);
            button_signup_proceed.setEnabled(true);
            progressBar_signup.setVisibility(View.INVISIBLE);
            try {
                JSONObject responseObject = new JSONObject(userResponse);
                JSONObject responseBody = responseObject.getJSONObject("body");
                if (responseObject.getInt("statusCode") == 200 || responseObject.getInt("statusCode") == 201) {
                    Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, responseBody.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


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

                    if (Network.getConnection(AppController.getInstance())) {
                        progressBar_signup.setVisibility(View.VISIBLE);
                        button_signup_proceed.setEnabled(false);

                        Map<String, String> users = new HashMap<>();
                        users.put("username", edittext_signup_name.getText().toString());
                        users.put("phone_number", edittext_signup_phone.getText().toString());
                        users.put("pin", edittext_signup_pin.getText().toString());
                        users.put("email", edittext_signup_email.getText().toString());

                        signupViewModel.getPostRequest(GetUrl.SIGNUP, users, GetConstants.SIGNUP_REQUESTCODE);
                    }
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