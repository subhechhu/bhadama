package com.subhechhu.bhadama.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.subhechhu.bhadama.Adapter.LocationAdapter;
import com.subhechhu.bhadama.Adapter.PersonalPropertyListAdapter;
import com.subhechhu.bhadama.Adapter.SavedPropertyListAdapter;
import com.subhechhu.bhadama.Model.LocationModel;
import com.subhechhu.bhadama.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PersonalPropertyActivity extends AppCompatActivity{

    FloatingActionButton floating_personalprop_add;
    ImageView imageView_arrow;
    RecyclerView recyclerview_property;


    PersonalPropertyListAdapter personalPropertyListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_property);

        TextView textView_personalprop_empty = findViewById(R.id.textView_personalprop_empty);
//        textView_personalprop_empty.setText(getIntent().getStringExtra("Message"));

        floating_personalprop_add = findViewById(R.id.floating_personalprop_add);
        imageView_arrow = findViewById(R.id.imageView_arrow);
        imageView_arrow.setVisibility(View.INVISIBLE);

        recyclerview_property = findViewById(R.id.recyclerview_property);

        Integer house[] = {
                R.drawable.aa_1,
                R.drawable.aa_2,
                R.drawable.aa_3,
                R.drawable.aa_4,
                R.drawable.aa_5};


        personalPropertyListAdapter = new PersonalPropertyListAdapter(house);

        recyclerview_property.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_property.setHasFixedSize(true);
        recyclerview_property.setAdapter(personalPropertyListAdapter);

        personalPropertyListAdapter.showList(getList());

        floating_personalprop_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PersonalPropertyActivity.this, AddPropertyActivity.class), 654);
            }
        });

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
    }

    private List<LocationModel> getList() {
        List<LocationModel> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LocationModel model = new LocationModel();
            model.setPlaceId("320876946998");
            model.setOsmId("5074785424");
            model.setDisplayName("ॐ shiv mandir, Dantakali Road, Dhruba Jyori Chok, Koseli Chok, Dharan Sub-Metropolitan, Province #1, 56100, Nepa");
            model.setDisplayPlace("ॐ shiv mandir");
            list.add(model);
        }
        return list;
    }
}