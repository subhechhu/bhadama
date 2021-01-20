package com.subhechhu.bhadama.activity.addProperty;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.subhechhu.bhadama.activity.addProperty.fragment.PageFour;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageOne;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageThree;
import com.subhechhu.bhadama.activity.addProperty.fragment.PageTwo;
import com.subhechhu.bhadama.activity.personalProperty.ModelLocation;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;
import com.subhechhu.bhadama.activity.propertyDetailsSeller.PropertyDetailsSeller;
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
    AddPropertyAdapter adapterViewPager;

    PageOne pageOne;
    PageTwo pageTwo;
    PageThree pageThree;
    PageFour pageFour;

    TextView textViewProgressDialogMessage;
    Dialog progressDialog;

    int currentPosition = 0;
    ViewPager2 vpPager;
    JSONArray imagesArray;
    int currentUploadCount = 0;
    int imageArrayLength = 0;

    String from, propertyId;
    boolean toEdit = false;
    Gson gson;

    ModelPersonalProperty personalProperty;
    AddPropertyViewModel addPropertyViewModel;

    Map<String, Object> updateProperty;
    Map<String, Object> propertyMap;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        floating_personalprop_next = findViewById(R.id.floating_personalprop_next);
        floating_personalprop_prev = findViewById(R.id.floating_personalprop_previous);

        vpPager = findViewById(R.id.vpPager);
        vpPager.setUserInputEnabled(false);

        adapterViewPager = new AddPropertyAdapter(this);
        vpPager.setAdapter(adapterViewPager);

        if (getIntent().getStringExtra("from") != null)
            from = getIntent().getStringExtra("from");

        updateProperty = new HashMap<>();
        propertyMap = new HashMap<>();

        if (from != null && from.equalsIgnoreCase("PropertyDetailsSeller")) {
            gson = new Gson();
            personalProperty = gson.fromJson(getIntent().getStringExtra("data"), ModelPersonalProperty.class);
            propertyId = personalProperty.getId();
            toEdit = true;
        }

        floating_personalprop_next.setOnClickListener(view -> {
            switch (currentPosition) {
                case 0:
                    if (pageOne == null) {
                        pageOne = (PageOne) adapterViewPager.getFragment(0);
                    }
                    if (pageOne.nextClickListener())
                        vpPager.setCurrentItem(currentPosition + 1, true);
                    break;
                case 1:
                    if (pageTwo == null) {
                        pageTwo = (PageTwo) adapterViewPager.getFragment(1);
                    }
                    pageTwo.nextClickListener();
                    vpPager.setCurrentItem(currentPosition + 1, true);
                    break;
                case 2:
                    if (pageThree == null) {
                        pageThree = (PageThree) adapterViewPager.getFragment(2);
                    }
                    pageThree.nextClickListener();
                    vpPager.setCurrentItem(currentPosition + 1, true);
                    break;
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
            Log.e(TAG, "add property response: " + response);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("statusCode") == 201) {
                    makeToast("Property Created");
                    finish();
                } else {
                    makeToast("Something went wrong. Please Try Again...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addPropertyViewModel.getImageUploadResponse().observe(this, response -> {
            Log.e(TAG, "upload image response: " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("statusCode") == 201 || jsonObject.getInt("statusCode") == 200) {
                    images.add(jsonObject.getJSONObject("body").getString("data"));
                } else {
                    makeToast("Something went wrong");
                }
                currentUploadCount++;
                uploadImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addPropertyViewModel.getPropertyUpdateResponse().observe(this, response -> {
            Log.e(TAG, "update property response: " + response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getInt("statusCode") == 200) {
                    progressDialog.dismiss();

                    Gson gson = new Gson();
                    String propertyJson = gson.toJson(personalProperty);
                    Intent intent = new Intent(this, PropertyDetailsSeller.class);
                    intent.putExtra("data_new", propertyJson);
                    setResult(RESULT_OK, intent);
                    makeToast("Property Updated");
                    Log.e(TAG, "final add prop activity personal property: " + propertyJson);
                    finish();
                } else {
                    makeToast("Something Went Wrong");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public ModelPersonalProperty getDataToEdit() {
        return personalProperty;
    }

    public void putDataToEdit(String key, Object value) {
        updateProperty.put(key, value);
    }

    public void removeDataToEdit(String key) {
        updateProperty.remove(key);
    }


    private void uploadImage() {
        try {
            if (currentUploadCount < imageArrayLength) {
                if (imagesArray.getString(currentUploadCount).startsWith("https://")) {
                    images.add(imagesArray.getString(currentUploadCount));
                    currentUploadCount++;
                    uploadImage();
                } else {
                    textViewProgressDialogMessage.setText(getString(R.string.uploading_images, (currentUploadCount + 1), imageArrayLength));
                    addPropertyViewModel.makeImageUploadRequest(UPLOAD_IMAGE, getImageInByte(imagesArray.getString(currentUploadCount)), UPLOAD_IMAGE_REQUESTCODE);
                }
            } else {
                propertyMap.put("images", images);
                personalProperty.setImages(images);

                textViewProgressDialogMessage.setText(R.string.hangon);
                if (toEdit) {
                    addPropertyViewModel.makePatchRequest(MODIFY_PROPERTY + propertyId, propertyMap, UPDATE_PROPERTY_REQUESTCODE);
                } else {
                    addPropertyViewModel.makePostRequest(VERIFY_PROPERTY, propertyMap, VERIFYPROPERTY_REQUESTCODE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public void uploadData(JSONObject propertyJson) {
        if (Network.getConnection(AppController.getContext())) {
            try {
                ModelPersonalProperty modelPersonalProperty = new ModelPersonalProperty();
                if (propertyJson.has("roomSize")) {
                    personalProperty.setRoomSize(propertyJson.getString("roomSize"));
                    propertyMap.put("roomSize", propertyJson.getString("roomSize"));
                }
                if (propertyJson.has("roomtype")) {
                    personalProperty.setRoomType(propertyJson.getString("roomtype"));
                    propertyMap.put("roomtype", propertyJson.getString("roomtype"));
                }

                if (propertyJson.has("rent")) {
                    personalProperty.setRent(Integer.parseInt(propertyJson.getString("rent")));
                    propertyMap.put("rent", propertyJson.getString("rent"));
                }

                if (propertyJson.has("availableFrom")) {
                    personalProperty.setAvailableFrom(propertyJson.getString("availableFrom"));
                    propertyMap.put("availableFrom", propertyJson.getString("availableFrom"));
                }

                if (propertyJson.has("place")) {
                    personalProperty.setPlace(propertyJson.getString("place"));
                    propertyMap.put("place", propertyJson.getString("place"));
                }
                if (propertyJson.has("furnishing")) {
                    personalProperty.setFurnishing(String.valueOf(propertyJson.getBoolean("furnishing")));
                    propertyMap.put("furnishing", propertyJson.getBoolean("furnishing"));
                }

                if (propertyJson.has("tenants")) {
                    personalProperty.setFurnishing(propertyJson.getString("tenants"));
                    propertyMap.put("tenants", propertyJson.getString("tenants"));
                }

                if (propertyJson.has("waterSupplyOther")) {
                    personalProperty.setWaterSupplyOther(propertyJson.getBoolean("waterSupplyOther"));
                    propertyMap.put("waterSupplyOther", propertyJson.getBoolean("waterSupplyOther"));
                }
                if (propertyJson.has("waterSupplyNwscc")) {
                    personalProperty.setWaterSupplyNwscc(propertyJson.getBoolean("waterSupplyNwscc"));
                    propertyMap.put("waterSupplyNwscc", propertyJson.getBoolean("waterSupplyNwscc"));
                }
                if (propertyJson.has("waterSupplyUnderground")) {
                    personalProperty.setWaterSupplyUnderground(propertyJson.getBoolean("waterSupplyUnderground"));
                    propertyMap.put("waterSupplyUnderground", propertyJson.getBoolean("waterSupplyUnderground"));
                }
                if (propertyJson.has("twoWheeler")) {
                    personalProperty.setTwoWheeler(propertyJson.getBoolean("twoWheeler"));
                    propertyMap.put("twoWheeler", propertyJson.getBoolean("twoWheeler"));
                }
                if (propertyJson.has("fourWheeler")) {
                    personalProperty.setFourWheeler(propertyJson.getBoolean("fourWheeler"));
                    propertyMap.put("fourWheeler", propertyJson.getBoolean("fourWheeler"));
                }
                if (propertyJson.has("location")) {
                    ModelLocation modelLocation = new ModelLocation();
                    ArrayList<String> arrayList = new ArrayList<>();
                    modelLocation.setType("Point");
                    arrayList.add(propertyJson.getJSONObject("location").getJSONArray("coordinates").getString(0));
                    arrayList.add(propertyJson.getJSONObject("location").getJSONArray("coordinates").getString(1));
                    modelLocation.setCoordinates(arrayList);
                    personalProperty.setLocation(modelLocation);

                    propertyMap.put("location", propertyJson.getJSONObject("location"));
                }
                if (propertyJson.has("city")) {
                    personalProperty.setCity(propertyJson.getString("city"));
                    propertyMap.put("city", propertyJson.getString("city"));
                }

                renderProgressDialog(getString(R.string.property_data_upload));

                if (propertyJson.has("images") && propertyJson.getJSONArray("images").length() > 0) {
                    images = new ArrayList<>();
                    currentUploadCount = 0;
                    imageArrayLength = propertyJson.getJSONArray("images").length();
                    imagesArray = propertyJson.getJSONArray("images");
                    uploadImage();
                } else {
                    if (toEdit) {
                        if (propertyJson.has("images") && propertyJson.getJSONArray("images").length() == 0) {
                            propertyMap.put("images", new JSONArray());
                            personalProperty.setImages(new ArrayList<>());
                        }

                        if (propertyMap.isEmpty()) {
                            makeToast("Nothing to edit");
                            finish();
                        } else {
                            Log.e(TAG, "Data to update: " + propertyMap.toString());
                            Log.e(TAG, "Prop to previous activity: " + personalProperty.toString());
                            addPropertyViewModel.makePatchRequest(MODIFY_PROPERTY + propertyId, propertyMap, UPDATE_PROPERTY_REQUESTCODE);
                        }
                    } else {
                        Log.e(TAG, "Data to create: " + propertyMap.toString());
                        addPropertyViewModel.makePostRequest(VERIFY_PROPERTY, propertyMap, VERIFYPROPERTY_REQUESTCODE);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void renderProgressDialog(String message) {
        progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_progress);

        textViewProgressDialogMessage = progressDialog.findViewById(R.id.textView_message);
        textViewProgressDialogMessage.setText(message);

        progressDialog.show();
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (currentPosition != 0) {
            vpPager.setCurrentItem(currentPosition - 1, true);
        } else
            super.onBackPressed();
    }

}