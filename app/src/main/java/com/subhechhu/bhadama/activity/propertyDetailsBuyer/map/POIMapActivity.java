package com.subhechhu.bhadama.activity.propertyDetailsBuyer.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.subhechhu.bhadama.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

public class POIMapActivity extends AppCompatActivity {

    private static final String TAG = POIMapActivity.class.getSimpleName();
    ViewPager2 viewPager2;
    MapView mapView;

    private static final String HOME_LAYER_ID = "hole_layer_id";
    private static final String GEOJSON_SRC_ID = "extremes_source_id";
    private static final String BLUE_PIN_IMAGE_ID = "blue_pin_id";
    private static final String SOURCE_ID_SECONDARY = "SOURCE_ID_SECONDARY";
    private static final String ICON_ID_SECONDARY = "ICON_ID_SECONDARY";
    private static final String LAYER_ID = "LAYER_ID";

    private MarkerView markerView;
    private MarkerViewManager markerViewManager;

    MapboxMap mapboxMap;
    String locationList;
    List<MapModel> list;

    Double lat, lon;

    CameraPosition camPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_poi);

        locationList = getIntent().getStringExtra("locationArray");
        lat = getIntent().getDoubleExtra("lat", 0D);
        lon = getIntent().getDoubleExtra("lon", 0D);

        Log.e(TAG, "location: " + locationList);

        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LatLng latLng = new LatLng(Double.parseDouble(list.get(position).getLon()),
                        Double.parseDouble(list.get(position).getLat()));
                updateCamera(latLng);
            }
        });

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(locationList);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                list.add(new MapModel(jsonObject.getString("name"),
                        jsonObject.getString("lat"),
                        jsonObject.getString("lon")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewPager2.setAdapter(new ViewPagerAdapter(this, list, viewPager2));

        mapView.getMapAsync(mapboxMap -> {
            POIMapActivity.this.mapboxMap = mapboxMap;
            mapboxMap.setStyle(new Style.Builder()
                    .fromUri(Style.MAPBOX_STREETS)
                    .withImage(ICON_ID_SECONDARY, BitmapFactory.decodeResource(
                            POIMapActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))
                    .withSource(new GeoJsonSource(SOURCE_ID_SECONDARY,
                            FeatureCollection.fromFeatures(getPointsList())))
                    .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID_SECONDARY)
                            .withProperties(
                                    iconImage(ICON_ID_SECONDARY),
                                    iconAllowOverlap(true),
                                    iconIgnorePlacement(true)
                            )
                    ), style -> {
                setUpMapImagePins(style);
                addHomeToMap(FeatureCollection.fromFeature(getHome()));
                updateCamera(new LatLng(lat, lon));
            });
        });
    }

    private void addHomeToMap(@NonNull FeatureCollection featureCollection) {
        GeoJsonSource geoJsonSource = new GeoJsonSource(GEOJSON_SRC_ID, featureCollection);
        if (mapboxMap != null) {
            mapboxMap.getStyle(style -> {
                style.addSource(geoJsonSource);
                initHome(style);
            });
        }
    }

    private void initHome(@NonNull Style loadedMapStyle) {
        if (mapboxMap != null) {
            SymbolLayer minTempLayer = new SymbolLayer(HOME_LAYER_ID, GEOJSON_SRC_ID);
            minTempLayer.withProperties(
                    iconImage(BLUE_PIN_IMAGE_ID),
                    iconAllowOverlap(true),
                    iconIgnorePlacement(true));
            loadedMapStyle.addLayer(minTempLayer);
        }
    }

    private void setUpMapImagePins(@NonNull Style loadedMapStyle) {
        Bitmap icon = BitmapFactory.decodeResource(
                this.getResources(), R.mipmap.location);
        loadedMapStyle.addImage(BLUE_PIN_IMAGE_ID, icon);
    }

    private void updateCamera(LatLng latLng) {
        camPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(18)
                .tilt(20)
                .build();

        if (mapboxMap != null)
            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(camPosition), 1000);
    }

    private Feature getHome() {
        return Feature.fromGeometry(
                Point.fromLngLat(lon, lat));
    }

    private List<Feature> getPointsList() {
        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        Log.e(TAG, "location in getPoint List: " + locationList);
        try {
            JSONArray jsonArray = new JSONArray(locationList);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                symbolLayerIconFeatureList.add(Feature.fromGeometry(
                        Point.fromLngLat(Double.parseDouble(jsonObject.getString("lon")),
                                Double.parseDouble(jsonObject.getString("lat")))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return symbolLayerIconFeatureList;
    }
}