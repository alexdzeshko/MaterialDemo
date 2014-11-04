package com.epam.dziashko.aliaksei.materialdemo;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends ActionBarActivity {


    public static final String EXTRA_DATA = "data";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(DetailActivity.this);
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.detail_image);

        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_DATA)).into(imageView);
    }
}
