package com.sporlif.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sporlif.FrmMain;
import com.sporlif.R;

public class LoginActivity extends AppCompatActivity {

    private TextView actLoginTxtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        launchWidgets();
        launchEvents();

    }

    public void launchWidgets(){

        actLoginTxtSignIn = (TextView) findViewById(R.id.actLoginTxtSignIn);

    }

    public void launchEvents(){

        actLoginTxtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FrmMain.class);
                startActivity(intent);
            }
        });

    }

}

