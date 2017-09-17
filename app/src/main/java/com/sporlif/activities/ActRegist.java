package com.sporlif.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sporlif.R;
import com.sporlif.activities.regist_frg.FrgRegistName;
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

    public FrgRegistName frgRegistName;

    public static final String TAG_FRG_REGIST_NAME = "TAG_FRG_REGIST_NAME";
    public static final String TAG_FRG_LIVING_PLACE = "TAG_FRG_LIVING_PLACE";
    public static final String TAG_FRG_PROFILE = "TAG_FRG_PROFILE";
    public static final String TAG_FRG_USER_DATA = "TAG_FRG_USER_DATA";

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

        switch (index) {

            case 1:

                if (frgRegistName == null) {
                    frgRegistName = new FrgRegistName();
                }

                transaction.add(R.id.frgRegistData, frgRegistName, TAG_FRG_REGIST_NAME);
                transaction.commit();

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            default:
                Toast.makeText(ActRegist.this, "Index no reconocido", Toast.LENGTH_LONG);
                break;

        }

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
