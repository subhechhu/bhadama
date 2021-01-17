package com.subhechhu.bhadama.activity.addProperty;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.subhechhu.bhadama.AppController;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;
import com.subhechhu.bhadama.util.GetConstants;
import com.subhechhu.bhadama.util.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.subhechhu.bhadama.util.GetConstants.*;
import static com.subhechhu.bhadama.util.GetUrl.*;
import static com.subhechhu.bhadama.util.GetUrl.UPLOAD_IMAGE;

public class AddPropertyActivity extends AppCompatActivity {

    private static final String TAG = AddPropertyActivity.class.getSimpleName();

    FloatingActionButton floating_personalprop_next, floating_personalprop_prev;
    addPropertyAdapter adapterViewPager;

    TextView textViewProgressDialogMessage;
    Dialog progressDialog;

    int currentPosition = 0;
    ViewPager2 vpPager;
    JSONArray imagesArray;
    int currentUploadCount = -1;

    String roomSize = "", roomType = "", rentAmount = "", roomAvailableFrom = "", location = "";
    String furnishing = "Unfurnished", parking = "2 Wheeler", tenants = "AnyOne", waterSupply = "Nepal Water Supply Corp.";
    String from, propertyId;

    Gson gson;

    ModelPersonalProperty personalProperty;
    AddPropertyViewModel addPropertyViewModel;

    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        floating_personalprop_next = findViewById(R.id.floating_personalprop_next);
        floating_personalprop_prev = findViewById(R.id.floating_personalprop_previous);

        vpPager = findViewById(R.id.vpPager);
        vpPager.setUserInputEnabled(false);

        adapterViewPager = new addPropertyAdapter(this);
        vpPager.setAdapter(adapterViewPager);

        if (getIntent().getStringExtra("from") != null)
            from = getIntent().getStringExtra("from");

        if (from != null && from.equalsIgnoreCase("PropertyDetailsSeller")) {
            gson = new Gson();
            personalProperty = gson.fromJson(getIntent().getStringExtra("data"), ModelPersonalProperty.class);
        }

        floating_personalprop_next.setOnClickListener(view -> {
            switch (currentPosition) {
                case 0:
                    //TODO uncomment below lines
//                    if (roomSize.isEmpty())
//                        makeToast("Select Number Of Rooms");
//                    else if (rentAmount.isEmpty())
//                        makeToast("Enter Room Rent");
//                    else if (roomAvailableFrom.isEmpty())
//                        makeToast("Enter Room Available Date");
//                    else if (location.isEmpty())
//                        makeToast("Enter Room Location");
//                    else
//                        vpPager.setCurrentItem(currentPosition + 1, true);
//                    break;
                case 1:
                case 2:
                case 3:
                    vpPager.setCurrentItem(currentPosition + 1, true);
                    break;
            }
        });

        floating_personalprop_prev.setOnClickListener(view -> vpPager.setCurrentItem(currentPosition - 1, true));

        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                if (position == 0) {
                    floating_personalprop_prev.setVisibility(View.INVISIBLE);
                }
                if (position != 0 && position != 3) {
                    floating_personalprop_prev.setVisibility(View.VISIBLE);
                    floating_personalprop_next.setVisibility(View.VISIBLE);
                }
                if (position == 3) {
                    floating_personalprop_next.setVisibility(View.INVISIBLE);
                }
            }
        });

        addPropertyViewModel = ViewModelProviders.of(this).get(AddPropertyViewModel.class);
        addPropertyViewModel.addPropertyResponse().observe(this, response -> {
            Log.e(TAG, "============= ==== add property response: " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("statusCode") == 201) {
                    propertyId = jsonObject.getJSONObject("body").getString("id");
                    currentUploadCount++;
                    uploadImage();
                } else {
                    makeToast("Something went wrong. Please Try Again...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addPropertyViewModel.getImageUploadResponse().observe(this, response -> {
            Log.e(TAG, "============= ====  image upload response: " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("statusCode") == 201 || jsonObject.getInt("statusCode") == 200    ) {
                    images.add(jsonObject.getJSONObject("body").getString("data"));
                } else {
                    makeToast("Something went wrong. Please Try Again...");
                }
                currentUploadCount++;
                uploadImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addPropertyViewModel.getPropertyUpdateResponse().observe(this, response -> {
            Log.e(TAG, "============= ====  update property response: " + response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getInt("statusCode") == 200) {
                    progressDialog.dismiss();
                    if (from != null && from.equalsIgnoreCase("PropertyDetailsSeller")) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                    }
                    Toast.makeText(this, "Property Created Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    public ModelPersonalProperty getDataToEdit() {
        return personalProperty;
    }
    public void putDataToEdit(String key, Object value){

    }

    private void uploadImage() {
        try {
            if (currentUploadCount < imagesArray.length()) {
                Log.e(TAG, "============= ==== image " + (currentUploadCount + 1) + ": " + imagesArray.getString(currentUploadCount));
                textViewProgressDialogMessage.setText(getString(R.string.uploading_images, (currentUploadCount + 1), imagesArray.length()));
                addPropertyViewModel.makeImageUploadRequest(UPLOAD_IMAGE, getImageInByte(imagesArray.getString(currentUploadCount)), UPLOAD_IMAGE_REQUESTCODE);
            } else {
                if (imagesArray.length() > 0) {
                    textViewProgressDialogMessage.setText("Hang on. It's almost done.");
                    uploadImageToServer();
                } else {
                    progressDialog.dismiss();
                    if (from != null && from.equalsIgnoreCase("PropertyDetailsSeller")) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                    }
                    Toast.makeText(this, "Property Created Successfully!", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadImageToServer() {
        Map<String, Object> params = new HashMap<>();
        params.put("images", images);
        addPropertyViewModel.makePatchRequest(MODIFY_PROPERTY + propertyId, params, UPDATE_PROPERTY_REQUESTCODE);
    }

    private byte[] getImageInByte(String filePath) {
        try {
            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            return imageBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public void updateRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void updateRent(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public void updateAvailableDate(String roomAvailableFrom) {
        this.roomAvailableFrom = roomAvailableFrom;
    }

    public void updateLocation(String location, String city, String latitude, String longitude) {
        this.location = location;
    }

    public void uploadData(JSONObject propertyJson, JSONArray imagesArray) {
        if (Network.getConnection(AppController.getContext())) {
            this.imagesArray = imagesArray;
            if (imagesArray.length() > 0)
                images = new ArrayList<>();
            try {
                Map<String, Object> property = new HashMap<>();
                property.put("roomSize", propertyJson.getString("roomSize"));
                property.put("roomtype", propertyJson.getString("roomtype"));
                property.put("rent", propertyJson.getString("rent"));
                property.put("availableFrom", propertyJson.getString("availableFrom"));
                property.put("place", propertyJson.getString("place"));
                property.put("furnishing", propertyJson.getBoolean("furnishing"));
                property.put("tenants", propertyJson.getString("tenants"));
                property.put("waterSupplyOther", propertyJson.getBoolean("waterSupplyOther"));
                property.put("waterSupplyNwscc", propertyJson.getBoolean("waterSupplyNwscc"));
                property.put("waterSupplyUnderground", propertyJson.getBoolean("waterSupplyUnderground"));
                property.put("twoWheeler", propertyJson.getBoolean("twoWheeler"));
                property.put("fourWheeler", propertyJson.getBoolean("fourWheeler"));
                property.put("location", propertyJson.getJSONObject("location"));

                Log.e(TAG, "============= ==== Property data: " + property.toString());
                Log.e(TAG, "============= ==== Property image: " + imagesArray.toString());

                progressDialog = new Dialog(this);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.dialog_progress);

                textViewProgressDialogMessage = (TextView) progressDialog.findViewById(R.id.textView_message);
                textViewProgressDialogMessage.setText(R.string.property_data_upload);

                progressDialog.show();
                if (from != null && from.equalsIgnoreCase("PropertyDetailsSeller"))
                    makeToast("Edit Feature Yet To Implement");
//                    addPropertyViewModel.makePostRequest(VERIFY_PROPERTY, property, VERIFYPROPERTY_REQUESTCODE); //TODO change it to edit profile.
                else
                    addPropertyViewModel.makePostRequest(VERIFY_PROPERTY, property, VERIFYPROPERTY_REQUESTCODE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//
//    public void updateFurnishing(String furnishing) {
//        this.furnishing = furnishing;
//    }
//
//    public void updateTenant(String tenants) {
//        this.tenants = tenants;
//    }

//    public void updateParking(boolean twowheeler, boolean fourwheeler) { //Check for bug adding the logs
//        if (twowheeler)
//            parking = "2 Wheeler";
//        else if (fourwheeler)
//            parking = "4 Wheeler";
//        else if (twowheeler && fourwheeler)
//            parking = "Bike & Car";
//        else
//            parking = "Not Available";
//    }
//
//    public void updateWater(boolean checked, boolean checked1, boolean checked2) {
//        if (checked)
//            waterSupply = "Nepal Water Supply Corp.";
//        else if (checked1)
//            waterSupply = "Underground";
//        else if (checked2)
//            waterSupply = "Others";
//        else if (checked && checked1 || checked1 && checked2 || checked2 && checked)
//            waterSupply = "Multiple Source";
//        else
//            waterSupply = "None";
//    }

    @Override
    public void onBackPressed() {
        if (currentPosition != 0) {
            vpPager.setCurrentItem(currentPosition - 1, true);
        } else
            super.onBackPressed();
    }
}