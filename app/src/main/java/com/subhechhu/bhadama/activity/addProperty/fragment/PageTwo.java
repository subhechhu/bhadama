package com.subhechhu.bhadama.activity.addProperty.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;
import com.subhechhu.bhadama.activity.personalProperty.ModelPersonalProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class PageTwo extends Fragment {

    private static final String TAG = PageTwo.class.getSimpleName();
    RadioGroup radio_group_furnishing, radio_group_rent_tenant;
    CheckBox cbtwoWheeler, cbfourWheeler, cbnwsc, cbunderground, cbother;

    boolean twoWheeler = true, fourWheeler, nwsc = true, underground, other;

    String furnishing = "Unfurnished", tenants = "Any";
    boolean furnishingBool;

    FragmentViewModel fragmentViewModel;
    JSONObject fieldObject;

    ModelPersonalProperty personalProperty;

    public static PageTwo newInstance() {
        return new PageTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
        fieldObject = new JSONObject();

        if (getActivity() instanceof AddPropertyActivity) {
            personalProperty = ((AddPropertyActivity) Objects.requireNonNull(getActivity())).getDataToEdit();
        }
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

            if (getActivity() instanceof AddPropertyActivity) {
                if (personalProperty != null)
                    if (!personalProperty.isTwoWheeler() == twoWheeler)
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).putDataToEdit("twoWheeler", twoWheeler);
                    else {
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).removeDataToEdit("twoWheeler");
                    }
            }
        });

        cbfourWheeler.setOnClickListener(view12 -> {
            try {
                fieldObject.put("fourWheeler", cbfourWheeler.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            fourWheeler = cbfourWheeler.isChecked();

            if (getActivity() instanceof AddPropertyActivity) {
                if (personalProperty != null)
                    if (!personalProperty.isFourWheeler() == fourWheeler)
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).putDataToEdit("fourWheeler", fourWheeler);
                    else {
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).removeDataToEdit("fourWheeler");
                    }
            }
        });

        cbnwsc.setOnClickListener(view14 -> {
            try {
                fieldObject.put("watersupply_nwsc", cbnwsc.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            nwsc = cbnwsc.isChecked();
            if (getActivity() instanceof AddPropertyActivity) {
                if (personalProperty != null)
                    if (!personalProperty.isWaterSupplyNwscc() == nwsc)
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).putDataToEdit("waterSupplyNwscc", nwsc);
                    else {
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).removeDataToEdit("waterSupplyNwscc");
                    }
            }
        });

        cbunderground.setOnClickListener(view13 -> {
            try {
                fieldObject.put("watersupply_underground", cbunderground.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            underground = cbunderground.isChecked();
            if (getActivity() instanceof AddPropertyActivity) {
                if (personalProperty != null)
                    if (!personalProperty.isWaterSupplyUnderground() == underground)
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).putDataToEdit("waterSupplyUnderground", underground);
                    else {
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).removeDataToEdit("waterSupplyUnderground");
                    }
            }
        });

        cbother.setOnClickListener(view15 -> {
            try {
                fieldObject.put("watersupply_other", cbother.isChecked());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            other = cbother.isChecked();

            if (getActivity() instanceof AddPropertyActivity) {
                if (personalProperty != null)
                    if (!personalProperty.isWaterSupplyOther() == other)
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).putDataToEdit("waterSupplyOther", other);
                    else {
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).removeDataToEdit("waterSupplyOther");
                    }
            }
        });

        radio_group_furnishing.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.radio_furnished) {
                furnishingBool = true;
                furnishing = "Furnished";
            } else {
                furnishing = "Unfurnished";
                furnishingBool = false;
            }
            try {
                fieldObject.put("furnishing", furnishingBool);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (getActivity() instanceof AddPropertyActivity) {
                if (personalProperty != null) {
                    if (!personalProperty.getFurnishing().equalsIgnoreCase("" + furnishingBool))
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).putDataToEdit("furnishing", furnishingBool);
                    else {
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).removeDataToEdit("furnishing");
                    }
                }
            }

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

            if (getActivity() instanceof AddPropertyActivity) {
                if (personalProperty != null)
                    if (!personalProperty.getTenants().equalsIgnoreCase("" + tenants))
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).putDataToEdit("tenants", tenants);
                    else {
                        ((AddPropertyActivity) Objects.requireNonNull(getActivity())).removeDataToEdit("tenants");
                    }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (personalProperty != null) {

            //Adding furnishing
            if (personalProperty.getFurnishing().equalsIgnoreCase("true")) {
                radio_group_furnishing.check(R.id.radio_furnished);
                furnishingBool = true;
            } else {
                radio_group_furnishing.check(R.id.radio_unfurnished);
                furnishingBool = false;
            }


            //Parking
            cbtwoWheeler.setChecked(personalProperty.isTwoWheeler());
            cbfourWheeler.setChecked(personalProperty.isFourWheeler());

            //Tenants
            if (personalProperty.getTenants().equalsIgnoreCase("Any"))
                radio_group_rent_tenant.check(R.id.radio_anyone);
            else if (personalProperty.getTenants().equalsIgnoreCase("Family"))
                radio_group_rent_tenant.check(R.id.radio_family);
            else if (personalProperty.getTenants().equalsIgnoreCase("Bachelor"))
                radio_group_rent_tenant.check(R.id.radio_bachelor);

            //WaterSupply
            cbnwsc.setChecked(personalProperty.isWaterSupplyNwscc());
            cbunderground.setChecked(personalProperty.isWaterSupplyUnderground());
            cbother.setChecked(personalProperty.isWaterSupplyOther());

            tenants = personalProperty.getTenants();
            other = personalProperty.isWaterSupplyOther();
            underground = personalProperty.isWaterSupplyUnderground();
            nwsc = personalProperty.isWaterSupplyNwscc();

            twoWheeler = personalProperty.isTwoWheeler();
            fourWheeler = personalProperty.isFourWheeler();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            fieldObject.put("furnishing", furnishingBool);
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
        Log.d(TAG, "fragment two on pause data" + fieldObject);
        fragmentViewModel.postFragmentTwoData(fieldObject);
    }
}