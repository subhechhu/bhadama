package com.subhechhu.bhadama.activity.propertyDetailsSeller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.subhechhu.bhadama.AppController;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;
import com.subhechhu.bhadama.util.Network;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator3;

import static com.subhechhu.bhadama.util.GetConstants.*;
import static com.subhechhu.bhadama.util.GetUrl.*;

public class PropertyDetailsSeller extends AppCompatActivity {
    private static final String TAG = PropertyDetailsSeller.class.getSimpleName();
    public static final int ADD_ACTIVITY = 12129;

    ViewPager2 vpPager;
    CircleIndicator3 indicator;

    TextView textView_rent, textView_date, textView_tenant, textView_furnishing, textView_parking,
            textView_watersupply, textview_postedDate, textview_approvalStatus, textView_room,
            textview_roomtype, textview_place;

    FloatingActionButton floating_deleteButton, floating_editButton, floating_disableButton;
    FloatingActionMenu floating_icon;

    ProgressBar progressBar_action;

    BottomSheetDialog actionDialog;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Calendar cal;
    int posessionDay, posessionMonth, posessionYear, postedDay, postedMonth, postedYear;

    Gson gson;

    PropertyDetailsViewModel propertyDetailsViewModel;
    ModelPersonalProperty personalProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_property_details);

        propertyDetailsViewModel = ViewModelProviders.of(this).get(PropertyDetailsViewModel.class);
        propertyDetailsViewModel.getDeleteResponse().observe(this, response -> {
            Log.e(TAG, "delete response: " + response);

            try {
                JSONObject responseObj = new JSONObject(response);
                if (responseObj.getInt("statusCode") == 200) {
                    actionDialog.dismiss();
                    Toast.makeText(this, "Property has been deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Property not deleted. Please try later.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gson = new Gson();
        personalProperty = gson.fromJson(getIntent().getStringExtra("data"), ModelPersonalProperty.class);

        ImageView imageView_property_image = findViewById(R.id.imageView_property_image);
        imageView_property_image.setImageResource(getIntent().getIntExtra("img", 0));

        cal = Calendar.getInstance();

        try {
            Date date = inputFormat.parse(personalProperty.getAvailableFrom());
            cal.setTime(date);

            posessionDay = cal.get(Calendar.DAY_OF_MONTH);
            posessionMonth = cal.get(Calendar.MONTH);
            posessionYear = cal.get(Calendar.YEAR);

            date = inputFormat.parse(personalProperty.getCreatedAt());
            cal.setTime(date);

            postedDay = cal.get(Calendar.DAY_OF_MONTH);
            postedMonth = cal.get(Calendar.MONTH);
            postedYear = cal.get(Calendar.YEAR);


        } catch (Exception e) {
            e.printStackTrace();
        }

        textView_rent = findViewById(R.id.textView_rent);
        textView_date = findViewById(R.id.textView_date);
        textView_tenant = findViewById(R.id.textView_tenant);
        textView_furnishing = findViewById(R.id.textView_furnishing);
        textView_parking = findViewById(R.id.textView_parking);
        textView_watersupply = findViewById(R.id.textView_watersupply);
        textview_postedDate = findViewById(R.id.textview_postedDate);
        textview_approvalStatus = findViewById(R.id.textview_approvalStatus);
        textView_room = findViewById(R.id.textView_room);
        textview_roomtype = findViewById(R.id.textView_roomtype);
        textview_place = findViewById(R.id.textView_place);

        floating_icon = findViewById(R.id.floating_icon_property);
        floating_icon.setIconAnimated(false);

        floating_deleteButton = findViewById(R.id.floating_icon_delete);
        floating_disableButton = findViewById(R.id.floating_icon_disable);
        floating_editButton = findViewById(R.id.floating_icon_edit);

        floating_deleteButton.setOnClickListener(view -> {
            floating_icon.close(true);
            renderActionDialog("delete");
        });

        floating_disableButton.setOnClickListener(view -> {
            floating_icon.close(true);
            renderActionDialog("disable");
        });

        floating_editButton.setOnClickListener(view -> {
            floating_icon.close(true);

            Gson gson = new Gson();
            String propertyJson = gson.toJson(personalProperty);
            Intent intent = new Intent(PropertyDetailsSeller.this, AddPropertyActivity.class);
            intent.putExtra("data", propertyJson);
            intent.putExtra("from", "PropertyDetailsSeller");
            startActivityForResult(intent, ADD_ACTIVITY);

        });


        textView_rent.setText(getString(R.string.rent_amount_int, personalProperty.getRent()));
        textView_date.setText(getString(R.string.date_possession, "Possession", posessionYear, posessionMonth + 1, posessionDay));
        textView_tenant.setText(personalProperty.getTenants());


        if (personalProperty.getFurnishing().equalsIgnoreCase("true"))
            textView_furnishing.setText(R.string.furnished);
        else
            textView_furnishing.setText(R.string.unfurnished);

        if (personalProperty.isTwoWheeler() && personalProperty.isFourWheeler())
            textView_parking.setText(getString(R.string.bothParking));
        else if (personalProperty.isTwoWheeler())
            textView_parking.setText(getString(R.string.parking_2));
        else
            textView_parking.setText(getString(R.string.parking_4));

        if (personalProperty.isWaterSupplyNwscc() && personalProperty.isWaterSupplyUnderground() ||
                personalProperty.isWaterSupplyNwscc() && personalProperty.isWaterSupplyOther() ||
                personalProperty.isWaterSupplyOther() && personalProperty.isWaterSupplyUnderground())
            textView_watersupply.setText(getString(R.string.differentSources));
        else if (personalProperty.isWaterSupplyNwscc()) {
            textView_watersupply.setText(getString(R.string.nwsc_plain));
        } else if (personalProperty.isWaterSupplyUnderground()) {
            textView_watersupply.setText(getString(R.string.underground));
        } else
            textView_watersupply.setText(getString(R.string.nowater));

        textview_postedDate.setText(getString(R.string.date_possession, "Created On", postedYear, postedMonth + 1, postedDay));
        if (personalProperty.getRoomType().isEmpty())
            textview_roomtype.setText(R.string.flatorhouse);
        else
            textview_roomtype.setText(personalProperty.getRoomType());

        if (personalProperty.getRoomSize().equalsIgnoreCase("1"))
            textView_room.setText(getString(R.string.total_room, personalProperty.getRoomSize(), "Room"));
        else
            textView_room.setText(getString(R.string.total_room, personalProperty.getRoomSize(), "Rooms"));

        textview_place.setText(personalProperty.getPlace());

        if (personalProperty.isApproved()) {
            textview_approvalStatus.setText(getString(R.string.approved));
            textview_approvalStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_circle_24, 0, 0, 0);
            textview_approvalStatus.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this, R.color.green));
        } else {
            textview_approvalStatus.setText(getString(R.string.pending));
            textview_approvalStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_report_24, 0, 0, 0);
            textview_approvalStatus.getCompoundDrawables()[0].setTint(ContextCompat.getColor(this, R.color.yellow));
        }

        textview_approvalStatus.setCompoundDrawablePadding(25);

//        vpPager = findViewById(R.id.vpPager);

//        adapterViewPager = new PoiAdapter(this);
//        vpPager.setAdapter(adapterViewPager);
//
//        indicator = findViewById(R.id.indicator);
//        indicator.setViewPager(vpPager);
//
//        adapterViewPager.registerAdapterDataObserver(indicator.getAdapterDataObserver());

    }

    public void renderActionDialog(String action) {
        View view = getLayoutInflater().inflate(R.layout.dialog_action, null);
        actionDialog = new BottomSheetDialog(PropertyDetailsSeller.this, R.style.dialogStyle);
        actionDialog.setContentView(view);
        actionDialog.setCancelable(false);
        actionDialog.show();

        progressBar_action = view.findViewById(R.id.progressBar_action);
        progressBar_action.setVisibility(View.INVISIBLE);
        AppCompatButton button_proceed = view.findViewById(R.id.button_proceed);
        TextView textView_message = view.findViewById(R.id.textView_message);

        if (action.equalsIgnoreCase("delete")) {
            textView_message.setText(R.string.delete_warning);
            button_proceed.setText(R.string.delete);
        } else {
            textView_message.setText(R.string.disable_warning);
            button_proceed.setText(R.string.disable);
        }

        view.findViewById(R.id.button_otp_close).setOnClickListener(view1 -> actionDialog.dismiss());

        button_proceed.setOnClickListener(view12 -> {
            if (isConnected()) {
                propertyDetailsViewModel.makeDeleteRequest(MODIFY_PROPERTY + personalProperty.getId(), DELETE_PROPERTY_REQUESTCODE);
                progressBar_action.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_ACTIVITY)
            finish();
    }

    private boolean isConnected() {
        return Network.getConnection(AppController.getContext());
    }

    @Override
    public void onBackPressed() {
        if (floating_icon.isOpened())
            floating_icon.close(true);
        else
            finish();
    }
}