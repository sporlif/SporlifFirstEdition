package com.sporlif.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sporlif.R;

/**
 * Created by MAURICIO on 24/05/2017.
 */

public class UtilsForViews {

    private Toolbar toolbar;

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void createToolbar(View view, AppCompatActivity activity, String title, boolean upEnabled){

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upEnabled);

    }

}
