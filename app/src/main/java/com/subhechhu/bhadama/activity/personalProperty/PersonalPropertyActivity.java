package com.subhechhu.bhadama.activity.personalProperty;

/*
 *
 *LIST OF THE PROPERTY
 * THAT OWNER HAVE POSTED
 *
 *
 * IF OWNER WANTS TO POST
 * A NEW PORPERTY, HE WILL
 * HAVE TO PASS VIA THIS
 * ACTIVITY.
 *
 */


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.subhechhu.bhadama.activity.addProperty.AddPropertyActivity;
import com.subhechhu.bhadama.R;
import com.subhechhu.bhadama.activity.propertyDetailsSeller.PropertyDetailsSeller;
import com.subhechhu.bhadama.util.GetConstants;
import com.subhechhu.bhadama.util.GetUrl;
import com.subhechhu.bhadama.util.Network;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PersonalPropertyActivity extends AppCompatActivity {
//    private static final String TAG = PersonalPropertyActivity.class.getSimpleName();

    FloatingActionButton floating_personalprop_add;
    ImageView imageView_arrow;

    TextView textView_personalprop_message;
    AppCompatButton button_refresh;
    ProgressBar progressBar_fetchPropList;

    RecyclerView recyclerview_property;

    PersonalPropertyViewModel personalPropertyViewModel;
    PersonalPropertyListAdapter personalPropertyListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_property);

        progressBar_fetchPropList = findViewById(R.id.progressBar_fetchPropList);
        textView_personalprop_message = findViewById(R.id.textView_personalprop_message);
        button_refresh = findViewById(R.id.button_refresh);

        floating_personalprop_add = findViewById(R.id.floating_personalprop_add);
        floating_personalprop_add.setVisibility(View.VISIBLE); //todo remove this
        imageView_arrow = findViewById(R.id.imageView_arrow);

        recyclerview_property = findViewById(R.id.recyclerview_property);
        personalPropertyViewModel = ViewModelProviders.of(this).get(PersonalPropertyViewModel.class);

        personalPropertyListAdapter = new PersonalPropertyListAdapter(this);

        recyclerview_property.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_property.setHasFixedSize(true);
        recyclerview_property.setAdapter(personalPropertyListAdapter);

        button_refresh.setOnClickListener(view -> {
            textView_personalprop_message.setVisibility(View.INVISIBLE);
            button_refresh.setVisibility(View.INVISIBLE);
            progressBar_fetchPropList.setVisibility(View.VISIBLE);

            personalPropertyViewModel.makeGetRequest(GetUrl.GET_PROPERTIES, GetConstants.GET_PROPERTIES_REQUESTCODE);
        });
        floating_personalprop_add.setOnClickListener(view -> startActivityForResult(new Intent(PersonalPropertyActivity.this, AddPropertyActivity.class), 654));

        recyclerview_property.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floating_personalprop_add.getVisibility() == View.VISIBLE) {
                    floating_personalprop_add.hide();
                } else if (dy < 0 && floating_personalprop_add.getVisibility() != View.VISIBLE) {
                    floating_personalprop_add.show();
                }
            }
        });

        personalPropertyViewModel.getPropertyListResponse().observe(this, response -> {
            progressBar_fetchPropList.setVisibility(View.INVISIBLE);
            try {
                JSONObject responeObject = new JSONObject(response);
                if (responeObject.getInt("statusCode") == 200) {
                    JSONObject bodyObject = responeObject.getJSONObject("body");
                    JSONArray data = bodyObject.getJSONArray("data");

                    if (data.length() == 0) {
                        textView_personalprop_message.setVisibility(View.VISIBLE);
                        textView_personalprop_message.setText(R.string.empty_property);
                        imageView_arrow.setVisibility(View.VISIBLE);
                        recyclerview_property.setVisibility(View.INVISIBLE);
                    } else {
                        recyclerview_property.setVisibility(View.VISIBLE);
                        textView_personalprop_message.setVisibility(View.INVISIBLE);
                        imageView_arrow.setVisibility(View.INVISIBLE);
                        button_refresh.setVisibility(View.INVISIBLE);

                        Gson gson = new Gson();
                        Type listOfMyClassObject = new TypeToken<List<ModelPersonalProperty>>() {
                        }.getType();
                        List<ModelPersonalProperty> personalPropertyList = gson.fromJson(String.valueOf(bodyObject.getJSONArray("data")), listOfMyClassObject);
                        Collections.reverse(personalPropertyList);
                        personalPropertyListAdapter.showList(personalPropertyList);
                    }
                    floating_personalprop_add.setVisibility(View.VISIBLE);
                } else {
                    textView_personalprop_message.setVisibility(View.VISIBLE);
                    textView_personalprop_message.setText(R.string.unable_to_fetch);
                    button_refresh.setVisibility(View.VISIBLE);
                    imageView_arrow.setVisibility(View.VISIBLE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void propertyDetailView(ModelPersonalProperty modelPersonalProperty, ImageView imageView) {
        Gson gson = new Gson();
        String propertyJson = gson.toJson(modelPersonalProperty);
        Intent intent = new Intent(this, PropertyDetailsSeller.class);
        intent.putExtra("img", R.drawable.icon_main);
        intent.putExtra("data", propertyJson);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageView, "backgroundImage");
        startActivity(intent, options.toBundle());
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Network.getConnection(this))
            personalPropertyViewModel.makeGetRequest(GetUrl.GET_PROPERTIES, GetConstants.GET_PROPERTIES_REQUESTCODE);
    }
}