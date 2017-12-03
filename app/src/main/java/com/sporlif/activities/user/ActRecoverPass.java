package com.sporlif.activities.user;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sporlif.R;
import com.sporlif.activities.user.recover_frg.FrgEnterMail;

public class ActRecoverPass extends AppCompatActivity {

    private Toolbar toolbar;
    private FrgEnterMail frgEnterMail;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recover_pass);

        launchWidgets();
        launchEvents();

    }

    public void launchWidgets(){

        frgEnterMail = new FrgEnterMail();
        //getSupportFragmentManager().beginTransaction().add(R.id.frlRecoverPassContainer, frgEnterMail);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frlRecoverPassContainer, frgEnterMail);
        transaction.commit();

    }

    public void launchEvents(){



    }

}
