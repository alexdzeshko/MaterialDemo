package com.epam.dziashko.aliaksei.materialdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.dziashko.aliaksei.materialdemo.R;

public class ToolbarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((ActionBarActivity)getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.standalone_toolbar, null);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_standalone_toolbar);
        ((ActionBarActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Standalone toolbar");
    }

    @Override public void onPause() {
        ((ActionBarActivity)getActivity()).getSupportActionBar().show();
        super.onPause();
    }
}
