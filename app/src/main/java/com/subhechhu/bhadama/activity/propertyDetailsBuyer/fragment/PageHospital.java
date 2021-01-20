package com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.propertyDetailsBuyer.PropertyDetailsBuyerActivity;
import com.subhechhu.bhadama.activity.propertyDetailsBuyer.map.POIMapActivity;
import com.subhechhu.bhadama.util.GetUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class PageHospital extends Fragment {

    private static final String TAG = PageHospital.class.getSimpleName();

    View parentView;
    ProgressBar progressBar;
    LinearLayout linearlist;
    TextView textView_viewInMap;

    POIViewModel poiViewModel;
    String position;
    LatLng latLng;

    JSONArray locationArray;

    public static PageHospital newInstance() {
        return new PageHospital();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        latLng = ((PropertyDetailsBuyerActivity) Objects.requireNonNull(getActivity())).getLatLong();
        position = "&lat=" + latLng.getLatitude() + "&lon=" + latLng.getLongitude();

        poiViewModel = ViewModelProviders.of(this).get(POIViewModel.class);
        poiViewModel.makeGetRequest(GetUrl.POI_HOSPITAL + position, 7485);

        locationArray = new JSONArray();

        try {
            JSONObject object = new JSONObject();
            object.put("lat", latLng.getLatitude());
            object.put("lon", latLng.getLongitude());
            object.put("name", "Interested Property");
            locationArray.put(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_poi, container, false);

        textView_viewInMap = parentView.findViewById(R.id.textView_viewinmap);

        parentView.findViewById(R.id.textView_viewinmap).setOnClickListener(view -> {
            if (locationArray.length() > 0) {
                Intent intent = new Intent(getActivity(), POIMapActivity.class);
                intent.putExtra("lat", latLng.getLatitude());
                intent.putExtra("lon", latLng.getLongitude());
                intent.putExtra("locationArray", locationArray.toString());
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "No Hospital Nearby", Toast.LENGTH_SHORT).show();
            }
        });

        progressBar = parentView.findViewById(R.id.progressBar_poi);
        linearlist = parentView.findViewById(R.id.linear_list);

        TextView textView_title = parentView.findViewById(R.id.textview_title);
        textView_title.setText("Hospital");
        textView_title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_local_hospital_24, 0, 0, 0);
        textView_title.setCompoundDrawablePadding(10);

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        poiViewModel.getPOIRepository().observe(this, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            if (response != null) {
                textView_viewInMap.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.size(); i++) {
                    if (i == 5)
                        break;
                    View row = getLayoutInflater().inflate(R.layout.row_item_poi, null);
                    TextView tvPoint = row.findViewById(R.id.textView_point);
                    TextView tvDistance = row.findViewById(R.id.textView_distance);

                    tvPoint.setText(response.get(i).getName());
                    tvDistance.setText(response.get(i).getDistance() + " m");

                    linearlist.addView(row);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("lat", response.get(i).getLat());
                        object.put("lon", response.get(i).getLon());
                        object.put("name", response.get(i).getName());

                        locationArray.put(object);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}