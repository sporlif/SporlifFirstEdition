package com.sporlif.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sporlif.R;

public class ActRecoverPass extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_recover_pass);

        launchWidgets();
        launchEvents();

    }

    public void launchWidgets(){

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recuperar contraseña");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

    }

    public void launchEvents(){



    }

}
