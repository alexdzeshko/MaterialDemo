package com.epam.dziashko.aliaksei.materialdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends ActionBarActivity {


    public static final String EXTRA_DATA = "data";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        ImageView imageView = (ImageView) findViewById(R.id.detail_image);
        
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_DATA)).into(imageView);
    }
}
