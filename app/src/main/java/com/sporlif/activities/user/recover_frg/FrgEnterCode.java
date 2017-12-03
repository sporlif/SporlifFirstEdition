package com.sporlif.activities.user.recover_frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sporlif.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrgEnterCode extends Fragment {


    public FrgEnterCode() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.frg_enter_code, container, false);
        showToolbar(getResources().getString(R.string.frg_enter_mail_tag), false, view);

        return inflater.inflate(R.layout.frg_enter_mail, container, false);

    }

    private void showToolbar(String string, boolean b, View view) {

    }

}
