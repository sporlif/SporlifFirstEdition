package com.sporlif.activities.user.regist_frg;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sporlif.R;
import com.sporlif.activities.user.ActRegist;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class FrgLivingPlace extends Fragment {

    private TextView actRegistSpnPlace;

    private JsonArray positions;
    private int selectedPosId = 0;

    public FrgLivingPlace() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_living_place, container, false);

        positions = ((ActRegist) getActivity()).places;
        actRegistSpnPlace = (TextView) view.findViewById(R.id.actRegistSpnPlace);

        actRegistSpnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] posArray = new String[positions.size()];

                for (int i=0;i<posArray.length;i++){
                    JsonObject posObj = positions.getJsonObject(i);
                    posArray[i] = posObj.getString("d2");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(FrgLivingPlace.this.getActivity());
                builder.setTitle(R.string.act_regist_lbl_living_place);
                builder.setItems(posArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int index) {

                        selectedPosId = positions.getJsonObject(index).getInt("d1");
                        actRegistSpnPlace.setText(posArray[index]);

                    }
                });
                builder.setCancelable(true);
                builder.show();

            }
        });

        return view;
    }

    public int getSelectedPosId(){
        return selectedPosId;
    }

}
