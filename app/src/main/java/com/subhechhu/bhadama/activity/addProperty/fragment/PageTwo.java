package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.subhechhu.bhadama.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PageTwo extends Fragment {

    private static final String TAG = PageTwo.class.getSimpleName();
    RadioGroup radio_group_furnishing, radio_group_rent_tenant;
    CheckBox cbtwoWheeler, cbfourWheeler, cbnwsc, cbunderground, cbother;

    boolean twoWheeler = true, fourWheeler, nwsc = true, underground, other;

    String furnishing = "Unfurnished", tenants = "Any";

    FragmentViewModel fragmentViewModel;
    JSONObject fieldObject;

    public static PageTwo newInstance() {
        return new PageTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
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

        cbtwoWheeler.setOnClickListener(view1 -> {
            try {
                fieldObject.put("twoWheeler", cbtwoWheeler.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            twoWheeler = cbtwoWheeler.isChecked();
//            ((AddPropertyActivity) Objects.requireNonNull(getActivity())).updateParking(cbtwoWheeler.isChecked(), cbfourWheeler.isChecked());
        });

        cbfourWheeler.setOnClickListener(view12 -> {
            try {
                fieldObject.put("fourWheeler", cbfourWheeler.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            fourWheeler = cbfourWheeler.isChecked();
//            ((AddPropertyActivity) Objects.requireNonNull(getActivity())).updateParking(cbtwoWheeler.isChecked(), cbfourWheeler.isChecked());
        });

        cbnwsc.setOnClickListener(view14 -> {
            try {
                fieldObject.put("watersupply_nwsc", cbnwsc.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            nwsc = cbnwsc.isChecked();
//            ((AddPropertyActivity) Objects.requireNonNull(getActivity())).updateWater(cbnwsc.isChecked(), cbnwsc.isChecked(), cbother.isChecked());
        });

        cbunderground.setOnClickListener(view13 -> {
            try {
                fieldObject.put("watersupply_underground", cbunderground.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            underground = cbunderground.isChecked();
//            ((AddPropertyActivity) Objects.requireNonNull(getActivity())).updateWater(cbnwsc.isChecked(), cbunderground.isChecked(), cbother.isChecked());
        });

        cbother.setOnClickListener(view15 -> {
            try {
                fieldObject.put("watersupply_other", cbother.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            other = cbother.isChecked();
//            ((AddPropertyActivity) Objects.requireNonNull(getActivity())).updateWater(cbnwsc.isChecked(), cbother.isChecked(), cbother.isChecked());
        });

        radio_group_furnishing.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.radio_furnished)
                furnishing = "Furnished";
            else
                furnishing = "Unfurnished";
            try {
                fieldObject.put("furnishing", furnishing);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
//            ((AddPropertyActivity) Objects.requireNonNull(getActivity())).updateFurnishing(furnishing);
        });

        radio_group_rent_tenant.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.radio_bachelor)
                tenants = "Bachelors";
            else if (i == R.id.radio_family)
                tenants = "Family";
            else
                tenants = "Any";
            try {
                fieldObject.put("tenants", tenants);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
//            ((AddPropertyActivity) Objects.requireNonNull(getActivity())).updateTenant(tenants);
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fieldObject == null) {
            fieldObject = new JSONObject();
            try {
                fieldObject.put("furnishing", furnishing);
                fieldObject.put("tenants", tenants);
                fieldObject.put("watersupply_other", other);
                fieldObject.put("watersupply_underground", underground);
                fieldObject.put("watersupply_nwsc", nwsc);
                fieldObject.put("twoWheeler", twoWheeler);
                fieldObject.put("fourWheeler", fourWheeler);
            } catch (JSONException e) {
                Log.e(TAG, "exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        Log.d(TAG, "fragment two on pause data" + fieldObject);
        fragmentViewModel.postFragmentTwoData(fieldObject);
    }
}