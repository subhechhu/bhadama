package com.subhechhu.bhadama.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.turf.TurfMeta;
import com.mapbox.turf.TurfTransformation;
import com.subhechhu.bhadama.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class MapActivity extends AppCompatActivity {

    private static final String TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID
            = "TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID";
    private static final String TURF_CALCULATION_FILL_LAYER_ID = "TURF_CALCULATION_FILL_LAYER_ID";
    private static final String CIRCLE_CENTER_SOURCE_ID = "CIRCLE_CENTER_SOURCE_ID";
    private static final String CIRCLE_CENTER_ICON_ID = "CIRCLE_CENTER_ICON_ID";
    private static final String CIRCLE_CENTER_LAYER_ID = "CIRCLE_CENTER_LAYER_ID";

    private static Point markerPoint;

    double latitude = 27.7014884022, longitude = 85.323283875;
    String location;

    private MapView mapView;
    private MapboxMap mapboxMap;

    FloatingActionButton floating_map_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);

        latitude = Double.parseDouble(getIntent().getStringExtra("lat"));
        longitude = Double.parseDouble(getIntent().getStringExtra("lon"));
        location = getIntent().getStringExtra("location");

        markerPoint = Point.fromLngLat(longitude, latitude);


        floating_map_center = findViewById(R.id.floating_map_center);

        ImageView button_map_close = findViewById(R.id.button_map_close);
        TextView textview_map_textview = findViewById(R.id.textview_map_textview);

        button_map_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textview_map_textview.setText(location);

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(13)
                .tilt(20)
                .build();


        floating_map_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mapboxMap!=null){
                    mapboxMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(position), 3000);
//                    mapboxMap.setCameraPosition(position);
                }
            }
        });

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(new Style.Builder().fromUri(Style.MAPBOX_STREETS)
                .withImage(CIRCLE_CENTER_ICON_ID,
                        Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(
                                ContextCompat.getDrawable(this, R.drawable.red_marker))))
                .withSource(new GeoJsonSource(CIRCLE_CENTER_SOURCE_ID,
                        Feature.fromGeometry(markerPoint)))
                .withSource(new GeoJsonSource(TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID))
                .withLayer(new SymbolLayer(CIRCLE_CENTER_LAYER_ID,
                        CIRCLE_CENTER_SOURCE_ID).withProperties(
                        iconImage(CIRCLE_CENTER_ICON_ID),
                        iconIgnorePlacement(true),
                        iconAllowOverlap(true),
                        iconOffset(new Float[]{0f, -4f})
                )), style -> {
            MapActivity.this.mapboxMap = mapboxMap;
            initPolygonCircleFillLayer();
            drawPolygonCircle();
            mapboxMap.setCameraPosition(position);
            mapboxMap.getUiSettings().setQuickZoomGesturesEnabled(false);
            mapboxMap.getUiSettings().setZoomGesturesEnabled(false);
        }));
    }

    private void drawPolygonCircle() {
        mapboxMap.getStyle(style -> {
            Polygon polygonArea = getTurfPolygon();
            GeoJsonSource polygonCircleSource = style.getSourceAs(TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID);
            if (polygonCircleSource != null) {
                polygonCircleSource.setGeoJson(Polygon.fromOuterInner(
                        LineString.fromLngLats(TurfMeta.coordAll(polygonArea, false))));
            }
        });
    }

    private Polygon getTurfPolygon() {
        return TurfTransformation.circle(MapActivity.markerPoint, 1, 360, com.mapbox.turf.TurfConstants.UNIT_KILOMETERS);
    }

    private void initPolygonCircleFillLayer() {
        mapboxMap.getStyle(style -> {
            FillLayer fillLayer = new FillLayer(TURF_CALCULATION_FILL_LAYER_ID,
                    TURF_CALCULATION_FILL_LAYER_GEOJSON_SOURCE_ID);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fillLayer.setProperties(
                        fillColor(getColor(R.color.primary_solid)),
                        fillOpacity(.5f));
            }
            style.addLayerBelow(fillLayer, CIRCLE_CENTER_LAYER_ID);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}