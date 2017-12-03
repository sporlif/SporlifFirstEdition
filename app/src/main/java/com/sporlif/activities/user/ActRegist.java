package com.sporlif.activities.user;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sporlif.R;
import com.sporlif.activities.user.regist_frg.FrgLivingPlace;
import com.sporlif.activities.user.regist_frg.FrgProfile;
import com.sporlif.activities.user.regist_frg.FrgRegistName;
import com.sporlif.activities.user.regist_frg.FrgUserData;
import com.sporlif.utils.ClientHttpRequest;
import com.sporlif.utils.DialogAsyncTask;

import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;

public class ActRegist extends Activity {

    private Button actRegistBtnNext;

    public JsonObjectBuilder registData;
    public JsonArray genres;
    public JsonArray places;
    public JsonArray positions;

    protected int frgCount = 1;
    protected int currentFrg = -1;

    public FrgRegistName frgRegistName;
    public FrgLivingPlace frgLivingPlace;
    public FrgProfile frgProfile;
    public FrgUserData frgUserData;

    public static final String[] FRG_TAGS = {"TAG_FRG_REGIST_NAME", "TAG_FRG_LIVING_PLACE", "TAG_FRG_PROFILE", "TAG_FRG_USER_DATA"};

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.act_regist);

        actRegistBtnNext = (Button) findViewById(R.id.actRegistBtnNext);
        registData = Json.createObjectBuilder();

        new getSpnData(ActRegist.this).execute();

        actRegistBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (currentFrg) {

                    case 0:

                        if (frgRegistName.getFirstName().trim().length() == 0) {
                            Toast.makeText(ActRegist.this, "Debes escribir tu nombre", Toast.LENGTH_LONG);
                            return;
                        }

                        if (frgRegistName.getLastName().trim().length() == 0) {
                            Toast.makeText(ActRegist.this, "Debes escribir tu apellido", Toast.LENGTH_LONG);
                            return;
                        }

                        if (frgRegistName.getGenreId() == -1) {
                            Toast.makeText(ActRegist.this, "Debes seleccionar tú género", Toast.LENGTH_LONG);
                            return;
                        }

                        break;

                    case 1:

                        if(frgLivingPlace.getSelectedPosId() == 0){
                            Toast.makeText(ActRegist.this, "Debes seleccionar tú lugar de residencia.", Toast.LENGTH_LONG);
                            return;
                        }

                        break;

                    default:
                        Toast.makeText(ActRegist.this, "currentFrg no reconocido", Toast.LENGTH_LONG);
                        break;

                }

                frgCount++;
                setFragment(frgCount);

            }
        });

    }

    private class getSpnData extends DialogAsyncTask<String> {

        public getSpnData(Context context) {
            super(context);
        }

        @Override
        protected String task() {

            int tries = 0;
            ClientHttpRequest request;
            do {
                try {

                    request = new ClientHttpRequest(new URL("https://wsporlif-project.herokuapp.com/?op=getC&filter=pasto").openConnection());//luigar
                    request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);
                    places = Json.createReader(request.post()).readArray();

                    request = new ClientHttpRequest(new URL("https://wsporlif-project.herokuapp.com/?op=getG").openConnection());//generos
                    request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);
                    genres = Json.createReader(request.post()).readArray();

                    request = new ClientHttpRequest(new URL("https://wsporlif-project.herokuapp.com/?op=getP").openConnection());//positions
                    request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);
                    positions = Json.createReader(request.post()).readArray();

                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                    tries++;
                    if (tries == 3) {
                        return "TIMEOUT";
                    }
                }

            } while (tries < 3);

            return "OK";
        }

        @Override
        protected void result(String res) {

            if (res == "TIMEOUT") {

                System.out.println("El servidor no responde");
                actRegistBtnNext.setEnabled(false);

            } else {
                setFragment(frgCount);
            }

        }
    }

    public void setFragment(int index) {

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (currentFrg != -1) {
            transaction.hide(manager.findFragmentByTag(FRG_TAGS[currentFrg]));
        }
        switch (index) {

            case 1:

                if (frgRegistName == null) {
                    frgRegistName = new FrgRegistName();
                    transaction.add(R.id.frgRegistData, frgRegistName, FRG_TAGS[index - 1]);
                } else {
                    transaction.show(frgRegistName);
                }

                break;

            case 2:

                if (frgLivingPlace == null) {
                    frgLivingPlace = new FrgLivingPlace();
                    transaction.add(R.id.frgRegistData, frgLivingPlace, FRG_TAGS[index - 1]);
                } else {
                    transaction.show(frgLivingPlace);
                }

                break;

            case 3:

                if (frgProfile == null) {
                    frgProfile = new FrgProfile();
                    transaction.add(R.id.frgRegistData, frgProfile, FRG_TAGS[index - 1]);
                } else {
                    transaction.show(frgProfile);
                }

                break;

            case 4:

                if (frgUserData == null) {
                    frgUserData = new FrgUserData();
                    transaction.add(R.id.frgRegistData, frgUserData, FRG_TAGS[index - 1]);
                } else {
                    transaction.show(frgUserData);
                }

                break;

            case 5:

                //validar y registrar datos

                break;

            default:
                Toast.makeText(ActRegist.this, "Index no reconocido", Toast.LENGTH_LONG);
                break;

        }

        currentFrg = index - 1;
        transaction.commit();

    }

    @Override
    public void onBackPressed() {

        frgCount--;

        if (frgCount == 0) {
            finish();
        } else {
            setFragment(frgCount);
        }

    }
}
