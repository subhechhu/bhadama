package com.subhechhu.bhadama.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.subhechhu.bhadama.activity.AddPropertyActivity;
import com.subhechhu.bhadama.R;

public class PageTwo extends Fragment {

    RadioGroup radio_group_furnishing, radio_group_rent_tenant;
    CheckBox cbtwoWheeler, cbfourWheeler, cbnwsc, cbunderground, cbother;


    boolean twoWheeler, fourWheeler, nwsc, underground, other;

    String furnishing = "Unfurnished", tenants = "Anyone", waterSupply = "Nepal Water Supply Corp.";

    public static PageTwo newInstance() {
        return new PageTwo();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagetwo, container, false);
        radio_group_furnishing = view.findViewById(R.id.radio_group_furnishing);
        radio_group_rent_tenant = view.findViewById(R.id.radio_group_rent_tenant);

        cbtwoWheeler = view.findViewById(R.id.checkbox_twowheeler);
        cbfourWheeler = view.findViewById(R.id.checkbox_fourwheeler);

        cbnwsc = view.findViewById(R.id.checkbox_nwsc);
        cbunderground = view.findViewById(R.id.checkbox_underground);
        cbother = view.findViewById(R.id.checkbox_other);

        cbtwoWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddPropertyActivity) getActivity()).updateParking(cbtwoWheeler.isChecked(),cbfourWheeler.isChecked());
            }
        });

        cbfourWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddPropertyActivity) getActivity()).updateParking(cbtwoWheeler.isChecked(),cbfourWheeler.isChecked());
            }
        });

        cbnwsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddPropertyActivity) getActivity()).updateWater(cbnwsc.isChecked(),cbunderground.isChecked(), cbother.isChecked());
            }
        });

        cbunderground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddPropertyActivity) getActivity()).updateWater(cbnwsc.isChecked(),cbunderground.isChecked(), cbother.isChecked());
            }
        });

        cbother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddPropertyActivity) getActivity()).updateWater(cbnwsc.isChecked(),cbunderground.isChecked(), cbother.isChecked());
            }
        });

        radio_group_furnishing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_furnished)
                    furnishing = "Furnished";
                else
                    furnishing = "Unfurnished";
                ((AddPropertyActivity) getActivity()).updateFurnishing(furnishing);
            }
        });

        radio_group_rent_tenant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_bachelor)
                    tenants = "Bachelors";
                else if(i == R.id.radio_family)
                    tenants = "Family";
                else
                    tenants = "Anyone";
                ((AddPropertyActivity) getActivity()).updateTenant(tenants);
            }
        });



        return view;
    }
}