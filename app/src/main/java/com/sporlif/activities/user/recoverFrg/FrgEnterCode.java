package com.sporlif.activities.user.recoverFrg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sporlif.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrgEnterCode extends Fragment {

    private Toolbar toolbar;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.frg_enter_code, container, false);

        launchWidgets();
        launchEvents();

        return view;

    }

    private void launchWidgets(){

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.frg_enter_mail_tag);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void launchEvents(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }

    private void showToolbar(String string, boolean b, View view) {

    }

}
