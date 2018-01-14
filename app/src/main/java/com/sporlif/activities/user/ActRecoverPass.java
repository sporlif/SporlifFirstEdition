package com.sporlif.activities.user;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sporlif.R;
import com.sporlif.activities.user.recoverFrg.FrgEnterMail;

public class ActRecoverPass extends AppCompatActivity {

    private FrgEnterMail frgEnterMail;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recover_pass);

        launchWidgets();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.act_recover_pass);
    }

    public void launchWidgets(){

        frgEnterMail = new FrgEnterMail();
        //getSupportFragmentManager().beginTransaction().add(R.id.frlRecoverPassContainer, frgEnterMail);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frlRecoverPassContainer, frgEnterMail);
        transaction.commit();

    }

}
