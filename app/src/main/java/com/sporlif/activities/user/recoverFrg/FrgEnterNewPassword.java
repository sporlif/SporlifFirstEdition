package com.sporlif.activities.user.recoverFrg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sporlif.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrgEnterNewPassword extends Fragment {


    public FrgEnterNewPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_enter_new_password, container, false);
    }

}
