package com.sporlif.activities.regist_frg;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sporlif.R;
import com.sporlif.activities.ActRegist;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class FrgRegistName extends Fragment {

    private EditText frgPersonalDataEtFirstName;
    private EditText frgPersonalDataEtLastName;
    private DatePicker frgPersonalDataDpBirth;
    private RadioButton frgPersonalDataRbGenre1;
    private RadioButton frgPersonalDataRbGenre2;

    public FrgRegistName() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_personal_data, container, false);
        launchWidgets(view);

        JsonArray gns = ((ActRegist) getActivity()).genres;
        frgPersonalDataRbGenre1.setText(gns.getJsonObject(0).getString("d3"));
        frgPersonalDataRbGenre2.setText(gns.getJsonObject(1).getString("d3"));

        return view;
    }


    public void launchWidgets(View view) {

        frgPersonalDataEtFirstName = (EditText) view.findViewById(R.id.frgPersonalDataEtFirstName);
        frgPersonalDataEtLastName = (EditText) view.findViewById(R.id.frgPersonalDataEtLastName);
        frgPersonalDataDpBirth = (DatePicker) view.findViewById(R.id.frgPersonalDataDpBirth);
        frgPersonalDataRbGenre1 = (RadioButton) view.findViewById(R.id.frgPersonalDataRbGenre1);
        frgPersonalDataRbGenre2 = (RadioButton) view.findViewById(R.id.frgPersonalDataRbGenre2);

    }

}
