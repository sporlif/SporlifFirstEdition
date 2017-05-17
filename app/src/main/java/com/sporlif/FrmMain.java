package com.sporlif;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class FrmMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_main);

        new ConnectExample().execute();
    }

    private class ConnectExample extends AsyncTask<Double, Double, String> {

        protected String doInBackground(Double... urls) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {

                ClientHttpRequest request = new ClientHttpRequest(new URL("http://192.168.5.197:8080/serverPhp/index.php").openConnection());
                request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);
                request.setParameter("poolName", "pruebads");
                request.setParameter("tz","-5");

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

                System.out.println("req: "+req);

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
            Toast.makeText(FrmMain.this, asd, Toast.LENGTH_LONG);
        }

        protected void onPreExecute() {
        }

    }

    public String getClientMessage(ClientHttpRequest request) throws IOException {
        Reader r = new InputStreamReader(request.post(), "UTF-8");
        char[] buffer = new char[4096];
        StringBuilder sb = new StringBuilder();
        for(int len; (len = r.read(buffer)) > 0;)
            sb.append(buffer, 0, len);
        return sb.toString();
    }


}
