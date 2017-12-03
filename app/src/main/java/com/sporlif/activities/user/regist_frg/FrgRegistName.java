package com.sporlif.activities.user.regist_frg;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sporlif.R;
import com.sporlif.activities.user.ActRegist;

import javax.json.JsonArray;

public class FrgRegistName extends Fragment {

    private EditText frgPersonalDataEtFirstName;
    private EditText frgPersonalDataEtLastName;
    private DatePicker frgPersonalDataDpBirth;
    private RadioButton frgPersonalDataRbGenre1;
    private RadioButton frgPersonalDataRbGenre2;

    private int[] gnsId = new int[2];

    public FrgRegistName() {

    }
;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_personal_data, container, false);
        launchWidgets(view);

        JsonArray gns = ((ActRegist) getActivity()).genres;
        gnsId[0] = gns.getJsonObject(0).getInt("d1");
        frgPersonalDataRbGenre1.setText(gns.getJsonObject(0).getString("d3"));
        gnsId[1] = gns.getJsonObject(1).getInt("d1");
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

    public String getFirstName(){
        return frgPersonalDataEtFirstName.getText().toString();
    }

    public String getLastName(){
        return frgPersonalDataEtLastName.getText().toString();
    }

    public String getBirthDay(){
        return frgPersonalDataDpBirth.getYear() + "-" + frgPersonalDataDpBirth.getMonth() + "-" + frgPersonalDataDpBirth.getDayOfMonth();
    }

    public int getGenreId(){
        if(frgPersonalDataRbGenre1.isChecked()){
            return gnsId[0];
        } else if (frgPersonalDataRbGenre2.isChecked()) {
            return gnsId[1];
        } else {
            return -1;
        }
    }

}
