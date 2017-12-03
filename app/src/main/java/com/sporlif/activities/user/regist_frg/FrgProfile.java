package com.sporlif.activities.user.regist_frg;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sporlif.R;
import com.sporlif.activities.user.ActRegist;

import java.util.ArrayList;

import javax.json.JsonArray;

public class FrgProfile extends Fragment {

    private EditText frgProfileNickName;
    private TextView frgProfileLstPosition;
    private ImageView frgProfileImgProfile;

    private JsonArray postions;
    private boolean[] selectedPos;
    private ArrayList<String> mSelectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_profile, container, false);
        launchWidgets(view);

        postions = ((ActRegist) getActivity()).positions;
        selectedPos = new boolean[postions.size()];
        return view;
    }

    public void launchWidgets(View view) {

        frgProfileNickName = (EditText) view.findViewById(R.id.frgProfileNickName);
        frgProfileLstPosition = (TextView) view.findViewById(R.id.frgProfileLstPosition);
        frgProfileImgProfile = (ImageView) view.findViewById(R.id.frgProfileImgProfile);

    }

    private void launchEvents(){
        frgProfileLstPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(FrgProfile.this.getActivity());

                builder.setTitle(R.string.act_regist_lbl_position);
                builder.setMultiChoiceItems(R.array.position, selectedPos, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int index, boolean isChecked) {

                                String selectedValue = postions.getJsonObject(index).getString("d2");

                                if (isChecked) {
                                    mSelectedItems.add(selectedValue);
                                } else if (mSelectedItems.contains(selectedValue)) {
                                    mSelectedItems.remove(selectedValue);
                                }

                                selectedPos[index] = isChecked;//cuando se borra todas las pocisiones, se borran también este arreglo

                            }
                        });
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if (mSelectedItems.size() == 0) {
                            return;
                        }

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            sb.append(mSelectedItems.get(i) + (i == mSelectedItems.size() - 1 ? "" : ", "));
                        }
                        frgProfileLstPosition.setText(sb.toString());
                    }

                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.setCancelable(true);
                builder.show();
            }
        });
    }

    public String getNickName(){
        return frgProfileNickName.getText().toString();
    }

    public int getPosition(){
        return 1;
    }

    public Drawable getImgProfile(){
        return frgProfileImgProfile.getDrawable();
    }

}
