package com.sporlif.activities.user.recoverFrg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sporlif.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrgEnterCode extends Fragment {

    private Toolbar toolbar;
    private View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private TextView frgEnterCodeTxtEmail;
    private FrgEnterMail frgEnterMail;

    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.frg_enter_code, container, false);

        launchWidgets();
        launchEvents();

        return view;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void launchWidgets(){

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.frg_enter_mail_tag);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frgEnterCodeTxtEmail = (TextView) view.findViewById(R.id.frgEnterCodeTxtEmail);
        frgEnterCodeTxtEmail.setText(frgEnterCodeTxtEmail.getText() + " " + getEmail());

    }

    private void launchEvents(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frgEnterMail = new FrgEnterMail();
                fragmentManager = getFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frlRecoverPassContainer, frgEnterMail);
                transaction.commit();

            }
        });

    }

    private void showToolbar(String string, boolean b, View view) {

    }

}
