package com.sporlif.activities.user.regist_frg;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sporlif.R;

public class FrgUserData extends Fragment {

    private TextView actRegistEtEmail;
    private TextView actRegistEtPass;
    private TextView actRegistEtConfirmPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_user_data, container, false);
        launchWidgets(view);

        return view;
    }

    public void launchWidgets(View view){
        actRegistEtEmail = (TextView) view.findViewById(R.id.actRegistEtEmail);
        actRegistEtPass = (TextView) view.findViewById(R.id.actRegistEtPass);
        actRegistEtConfirmPass = (TextView) view.findViewById(R.id.actRegistEtConfirmPass);
    }

}
