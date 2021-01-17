package com.subhechhu.bhadama.activity.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.subhechhu.bhadama.activity.HomeActivity;
import com.subhechhu.bhadama.activity.signup.SignupActivity;
import com.subhechhu.bhadama.AppController;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.util.GetConstants;
import com.subhechhu.bhadama.util.GetUrl;
import com.subhechhu.bhadama.util.Network;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    private static final String TAG = SigninActivity.class.getSimpleName();

    EditText editText_phone, editText_pin;
    ProgressBar progressBar_login, progressBar_otp, progressBar_newPin;
    AppCompatButton button_login_phone_verify, button_login_pin_verify, button_login_signin,
            button_otp_verify, button_newpin_proceed;
    TextView textView_forgot_pin;

    BottomSheetDialog newPinDialog, otpDialog;

    Snackbar snackbar;
    LinearLayout parentlayout;

    SigninViewModel signinViewModel;
    ForgotPasswordViewModel forgotPasswordViewModel;

    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        AppController.storePreferenceString(AppController.getContext().getString(R.string.at), "");

        parentlayout = findViewById(R.id.parentlayout);

        progressBar_login = findViewById(R.id.progressBar_login);
        editText_phone = findViewById(R.id.edittext_home_location);
        editText_pin = findViewById(R.id.edittext_login_pin);

        button_login_phone_verify = findViewById(R.id.button_login_phone_verify);
        button_login_pin_verify = findViewById(R.id.button_login_pin_verify);
        button_login_signin = findViewById(R.id.button_login_signin);

        textView_forgot_pin = findViewById(R.id.textView_forgot_pin);

        /*
         *
         * Response
         * of
         * Signin
         *
         * */
        signinViewModel = ViewModelProviders.of(this).get(SigninViewModel.class);
        signinViewModel.signinResponse().observe(this, response -> {
            Log.d(TAG, "user" + response);
            button_login_signin.setEnabled(true);
            progressBar_login.setVisibility(View.INVISIBLE);
            try {
                JSONObject responseObject = new JSONObject(response);
                JSONObject responseBody = responseObject.getJSONObject("body");
                if (responseObject.getInt("statusCode") == 200 || responseObject.getInt("statusCode") == 201) {
                    Log.e(TAG, "accesstoken: " + responseBody.getString("accessToken"));
                    AppController.storePreferenceBoolean(AppController.getContext().getString(R.string.login_pref), true);
                    AppController.storePreferenceString(AppController.getContext().getString(R.string.at), responseBody.getString("accessToken"));
                    startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(SigninActivity.this, responseBody.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);

        /*
         *
         * Response
         * of
         * Forget
         * Pin
         *
         * */

        forgotPasswordViewModel.forgetPasswordResponse().observe(this, response ->
        {
            textView_forgot_pin.setEnabled(true);
            progressBar_login.setVisibility(View.INVISIBLE);
            try {
                JSONObject responseObject = new JSONObject(response);
                JSONObject responseBody = responseObject.getJSONObject("body");
                if (responseObject.getInt("statusCode") == 200 || responseObject.getInt("statusCode") == 201) {
                    snackbar = Snackbar.make(parentlayout, responseBody.getString("message"), Snackbar.LENGTH_INDEFINITE)
                            .setAction("OKAY", view -> {
                                snackbar.dismiss();
                                renderOTPDialog();
                            })
                            .setActionTextColor(getResources().getColor(R.color.secondary));
                    snackbar.show();
                } else {
                    Toast.makeText(SigninActivity.this, responseBody.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        /*
         *
         * Response
         * of
         * Verify
         * OTP
         *
         * */
        forgotPasswordViewModel.verifyOTPResponse().observe(this, response ->
        {
            try {
                JSONObject responseObject = new JSONObject(response);
                JSONObject responseBody = responseObject.getJSONObject("body");
                if (responseObject.getInt("statusCode") == 200 || responseObject.getInt("statusCode") == 201) {
                    otpDialog.dismiss();
                    renderNewPin();
                } else {
                    button_otp_verify.setEnabled(true);
                    progressBar_otp.setVisibility(View.INVISIBLE);
                    Toast.makeText(SigninActivity.this, responseBody.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        /*
         *
         * Response
         * of
         * New
         * Pin
         *
         * */
        forgotPasswordViewModel.newPinResponse().observe(this, response ->
        {
            try {
                JSONObject responseObject = new JSONObject(response);
                JSONObject responseBody = responseObject.getJSONObject("body");
                if (responseObject.getInt("statusCode") == 200 || responseObject.getInt("statusCode") == 201) {
                    Toast.makeText(SigninActivity.this, "New pin has been generated.\nProceed to login", Toast.LENGTH_LONG).show();
                    newPinDialog.dismiss();
                } else {
                    button_newpin_proceed.setEnabled(true);
                    progressBar_newPin.setVisibility(View.INVISIBLE);
                    Toast.makeText(SigninActivity.this, responseBody.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        editText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.toString().length() == 10) {
                    button_login_phone_verify.setVisibility(View.INVISIBLE);
                } else
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

        button_login_signin.setOnClickListener(view -> {
            if (Network.getConnection(getApplicationContext())) {
                if (!verifyPhone(editText_phone)) {
                    Toast.makeText(SigninActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                } else if (editText_pin.getText().toString().length() != 4) {
                    Toast.makeText(SigninActivity.this, "Invalid Pin", Toast.LENGTH_LONG).show();
                } else {
                    progressBar_login.setVisibility(View.VISIBLE);

                    Map<String, Object> users = new HashMap<>();
                    users.put("phone_number", editText_phone.getText().toString());
                    users.put("pin", editText_pin.getText().toString());

                    signinViewModel.makePostRequest(GetUrl.LOGIN, users, GetConstants.LOGIN_REQUESTCODE);
                }
            }
        });

    }

    public void forgotPin(View v) {
        if (isConnected()) {
            if (editText_phone.getText().toString().length() != 10) {
                Toast.makeText(SigninActivity.this, "Please Enter Valid Phone Number To Proceed", Toast.LENGTH_LONG).show();
            } else {
                progressBar_login.setVisibility(View.VISIBLE);
                textView_forgot_pin.setEnabled(false);

                phoneNumber = editText_phone.getText().toString();

                Map<String, Object> phoneMap = new HashMap<>();
                phoneMap.put("phone_number", editText_phone.getText().toString());

                forgotPasswordViewModel.makePostRequest(GetUrl.FORGET_PASSWORD, phoneMap, GetConstants.FORGETPIN_REQUESTCODE);
            }
        }
    }

    private void renderOTPDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_otp, null);
        otpDialog = new BottomSheetDialog(SigninActivity.this, R.style.dialogStyle);
        otpDialog.setContentView(view);
        otpDialog.setCancelable(false);
        otpDialog.show();

        EditText editText_otp = view.findViewById(R.id.edittext_otp_otp);
        progressBar_otp = view.findViewById(R.id.progressBar_otp);

        button_otp_verify = view.findViewById(R.id.button_otp_verify);

        view.findViewById(R.id.button_otp_close).setOnClickListener(view1 -> otpDialog.dismiss());

        button_otp_verify.setOnClickListener(view12 -> {
            if (isConnected()) {
                button_otp_verify.setEnabled(false);
                progressBar_otp.setVisibility(View.VISIBLE);

                if (editText_otp.getText().toString().length() != 4) {
                    Toast.makeText(this, "Enter Valid OTP to Proceed", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, Object> otpmap = new HashMap<>();
                    otpmap.put("phone_number", phoneNumber);
                    otpmap.put("otp", editText_otp.getText().toString());
                    forgotPasswordViewModel.makePostRequest(GetUrl.VERIFY_OTP, otpmap, GetConstants.VERIFYOTP_REQUESTCODE);
                }
            }
        });
    }

    private void renderNewPin() {
        View view = getLayoutInflater().inflate(R.layout.dialog_newpin, null);
        newPinDialog = new BottomSheetDialog(SigninActivity.this, R.style.dialogStyle);
        newPinDialog.setContentView(view);
        newPinDialog.setCancelable(false);
        newPinDialog.show();

        EditText editText_pin = view.findViewById(R.id.edittext_newpin_pin);
        EditText edit_confirmpin = view.findViewById(R.id.edittext_newpin_reenterpin);

        AppCompatButton button_newpin_pin_verify = view.findViewById(R.id.button_newpin_pin_verify);
        progressBar_newPin = view.findViewById(R.id.progressBar_newpin);
        AppCompatButton button_newpin_reenterpin_verify = view.findViewById(R.id.button_newpin_reenterpin_verify);
        button_newpin_proceed = view.findViewById(R.id.button_newpin_proceed);

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

        view.findViewById(R.id.button_newpin_close).setOnClickListener(view12 -> newPinDialog.dismiss());

        button_newpin_proceed.setOnClickListener(view1 -> {
            if (isConnected()) {
                if (verifyReenterPin(edit_confirmpin, editText_pin)) {
                    button_newpin_proceed.setEnabled(false);
                    progressBar_newPin.setVisibility(View.VISIBLE);

                    Map<String, String> newPinMap = new HashMap<>();
                    newPinMap.put("phone_number", phoneNumber);
                    newPinMap.put("pin", editText_pin.getText().toString());
                    forgotPasswordViewModel.makePutRequest(GetUrl.UPDATE_PIN, newPinMap, GetConstants.NEWPIN_REQUESTCODE);
                } else {
                    Toast.makeText(this, "Pin Did Not Match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void signUp(View v) {
        startActivity(new Intent(SigninActivity.this, SignupActivity.class));
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

    private boolean verifyReenterPin(EditText edittext_login_reenterpin, EditText edittext_login_pin) {
        if (edittext_login_reenterpin.getText().toString().length() == 4 && !edittext_login_reenterpin.getText().toString().equals(edittext_login_pin.getText().toString())) {
            Toast.makeText(SigninActivity.this, "Pin did not match", Toast.LENGTH_LONG).show();
        }
        return edittext_login_reenterpin.getText().toString().equals(edittext_login_pin.getText().toString());
    }

    private boolean verifyPhone(EditText editText_phone) {
        return editText_phone.getText().toString().length() == 10;
    }

    private boolean isConnected() {
        return Network.getConnection(AppController.getContext());
    }
}