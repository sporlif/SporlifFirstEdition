package com.sporlif.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sporlif.R;
import com.sporlif.utils.ClientHttpRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class ActRegist extends Activity{

    private ImageView actRegistImgProfile;
    private EditText actRegistEtFirstName, actRegistEtLastName, actRegistEtPlace, actRegistEtNickName;
    private TextView actRegistSpnGenre, actRegistSpnPosition, actRegistSpnBirth;
    private Button actRegistBtnRegist;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.act_regist);

        launchWidgets();
        launchEvents();

        //El AsyncTask lo pongo hasta que logremos hacer bien la conexión y el envío de datos al servidor
        new ConnectExample().execute();

    }

    public void launchWidgets() {
        actRegistImgProfile = (ImageView) findViewById(R.id.actRegistImgProfile);
        actRegistEtFirstName = (EditText) findViewById(R.id.actRegistEtFirstName);
        actRegistEtLastName = (EditText) findViewById(R.id.actRegistEtLastName);
        actRegistEtPlace = (EditText) findViewById(R.id.actRegistEtPlace);
        actRegistEtNickName = (EditText) findViewById(R.id.actRegistEtNickName);
        actRegistSpnGenre = (TextView) findViewById(R.id.actRegistSpnGenre);
        actRegistSpnPosition = (TextView) findViewById(R.id.actRegistSpnPosition);
        actRegistSpnBirth = (TextView) findViewById(R.id.actRegistSpnBirth);
        actRegistBtnRegist = (Button) findViewById(R.id.actRegistBtnRegist);
    }

    public void launchEvents() {


    }

    private class ConnectExample extends AsyncTask<Double, Double, String> {

        protected String doInBackground(Double... urls) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {

                ClientHttpRequest request = new ClientHttpRequest(new URL("http://wsporlif-project.herokuapp.com").openConnection());
                request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);
                request.setParameter("poolName", "pruebads");
                request.setParameter("tz", "-5");

                OutputStreamWriter w = new OutputStreamWriter(baos, "UTF-8");

                JsonObjectBuilder js = Json.createObjectBuilder();
                js.add("name", "Daniel");
                js.add("lastName", "Daza");
                js.add("age", 24);

                w.write(js.build().toString());
                w.close();
                baos.close();

                System.out.println("contenido del baos:" + baos.toString());

                request.setParameter("data", "", new ByteArrayInputStream(baos.toByteArray()));
                String req = getClientMessage(request);

                System.out.println("req: " + req);

                return "Conexión Exitosa";

            } catch (Exception e) {
                e.printStackTrace();
                return "Conexión Paila";
            }

        }

        protected void onProgressUpdate(Double... progress) {
            System.out.println(progress[0] + "%");
        }

        protected void onPostExecute(String asd) {
            Toast.makeText(ActRegist.this, asd, Toast.LENGTH_LONG);
        }

        protected void onPreExecute() {
        }

    }

    public String getClientMessage(ClientHttpRequest request) throws IOException {
        Reader r = new InputStreamReader(request.post(), "UTF-8");
        char[] buffer = new char[4096];
        StringBuilder sb = new StringBuilder();
        for (int len; (len = r.read(buffer)) > 0; )
            sb.append(buffer, 0, len);
        return sb.toString();
    }

}
