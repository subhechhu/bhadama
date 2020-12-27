package com.subhechhu.bhadama.activity.addProperty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.subhechhu.bhadama.R;

public class AddPropertyActivity extends AppCompatActivity {

    FloatingActionButton floating_personalprop_next, floating_personalprop_prev;
    addPropertyAdapter adapterViewPager;

    int currentPosition = 0;

    FragmentManager fm;

    ViewPager2 vpPager;

    String roomSize = "", roomType = "", rentAmount = "", roomAvailableFrom = "", location = "";
    String furnishing = "Unfurnished", parking = "2 Wheeler", tenants = "AnyOne", waterSupply = "Nepal Water Supply Corp.";

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

        floating_personalprop_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentPosition) {
                    case 0:
                        //TODO uncomment below lines
//                        if (roomSize.isEmpty())
//                            makeToast("Select Number Of Rooms");
//                        else if (rentAmount.isEmpty())
//                            makeToast("Enter Room Rent");
//                        else if (roomAvailableFrom.isEmpty())
//                            makeToast("Enter Room Available Date");
//                        else if (location.isEmpty())
//                            makeToast("Enter Room Location");
//                        else
//                            vpPager.setCurrentItem(currentPosition + 1, true);
//                        break;
                    case 1:
                    case 2:
                    case 3:
                        vpPager.setCurrentItem(currentPosition + 1, true);
                        break;
                }
            }
        });

        floating_personalprop_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpPager.setCurrentItem(currentPosition - 1, true);
            }
        });

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
        Log.e("TAG", "rentamount: " + rentAmount);
        this.rentAmount = rentAmount;
    }

    public void updateAvailableDate(String roomAvailableFrom) {
        this.roomAvailableFrom = roomAvailableFrom;
    }

    public void updateLocation(String location, String city, String latitude, String longitude) {
        this.location = location;
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