package com.subhechhu.bhadama.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.subhechhu.bhadama.Adapter.SavedPropertyListAdapter;
import com.subhechhu.bhadama.Model.LocationModel;
import com.subhechhu.bhadama.R;

import java.util.ArrayList;
import java.util.List;

public class SavedPropertyActivity extends AppCompatActivity implements SavedPropertyListAdapter.ItemClick {

    RecyclerView recyclerview_property;
    SavedPropertyListAdapter savedPropertyListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_property);

        TextView textView_personalprop_empty = findViewById(R.id.textView_personalprop_empty);
        textView_personalprop_empty.setText(getIntent().getStringExtra("Message"));

        if (getIntent().getStringExtra("from").equalsIgnoreCase("Saved")) {
            ImageView imageView_arrow = findViewById(R.id.imageView_arrow);
            imageView_arrow.setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.floating_personalprop_add).setVisibility(View.INVISIBLE);

        recyclerview_property = findViewById(R.id.recyclerview_property);

        Integer house[] = {
                R.drawable.aa_1,
                R.drawable.aa_2,
                R.drawable.aa_3,
                R.drawable.aa_4,
                R.drawable.aa_5};


        savedPropertyListAdapter = new SavedPropertyListAdapter(this, this, house);

        recyclerview_property.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_property.setHasFixedSize(true);
        recyclerview_property.setAdapter(savedPropertyListAdapter);
        savedPropertyListAdapter.showList(getList());

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

    @Override
    public void onClick(LocationModel modelWord, ImageView imageView, int house) {
        Intent intent = new Intent(SavedPropertyActivity.this, PropertyDetails.class);
        intent.putExtra("img", house);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(SavedPropertyActivity.this, (View) imageView, "backgroundImage");
        startActivity(intent, options.toBundle());
    }
}