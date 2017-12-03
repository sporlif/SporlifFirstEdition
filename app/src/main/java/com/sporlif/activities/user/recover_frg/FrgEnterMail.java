package com.sporlif.activities.user.recover_frg;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sporlif.R;
import com.sporlif.utils.ClientHttpRequest;

import java.net.URL;

import javax.json.Json;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrgEnterMail extends Fragment{

    private Toolbar toolbar;
    private View view;

    private EditText frgEnterMailEmail;
    private Button frgEnterMailButton;
    private Toast toast;
    private Context context;
    private Handler handler;

    private String email;
    private String responseSendMail;
    private String response;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.frg_enter_mail, container, false);

        launchWidgets();
        launchEvents();

        return view;

    }

    public String getEmail(){
        return this.email;
    }

    public String getResponse(){
        return this.response;
    }

    public String getResponseSendMail(){
        return this.responseSendMail;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setResponseSendMail(String responseSendMail){
        this.responseSendMail = responseSendMail;
    }

    public void setResponse(String response){
        this.response = response;
    }

    public void launchWidgets(){

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.frg_enter_mail_tag);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frgEnterMailEmail = (EditText) view.findViewById(R.id.frgEnterMailEmail);
        frgEnterMailButton = (Button) view.findViewById(R.id.frgEnterMailButton);

        handler = new Handler();

    }

    public void launchEvents(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 =
                        Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(),
                                "Toast por defecto", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });

        frgEnterMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setEmail(frgEnterMailEmail.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        setResponseSendMail(sendEmail());

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String message = "";

                                if(getResponseSendMail().equals("Ok"))
                                    if(getResponse().equals("NOFOUNDUSER"))
                                        message = "El usuario no existe";
                                    if(getResponse().equals("NOSEND"))
                                        message = "El correo no puedo ser enviado intentelo de nuevo";
                                    else if(getResponse().equals("OKSEND"))
                                        message = "El correo se ha enviado con exito";
                                else if(getResponseSendMail().equals("TIMEOUT"))
                                    message = "No se ha podido conectar al sevidor intentelo de nuevo mas tarde.";

                                Toast toast1 =
                                        Toast.makeText(((AppCompatActivity)getActivity()).getApplicationContext(),
                                                message, Toast.LENGTH_LONG);

                                toast1.show();
                            }
                        });

                    }
                }).start();

            }
        });

    }

    public String sendEmail(){

        int tries = 0;
        ClientHttpRequest request;

        do {
            try {

                request = new ClientHttpRequest(new URL("https://wsporlif-project.herokuapp.com/?mod=user&op=changedP&filter=" + getEmail()).openConnection());
                request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);
                setResponse(Json.createReader(request.post()).readObject().getString("Response"));

                break;

            } catch (Exception e) {
                e.printStackTrace();
                tries++;
                if (tries == 3) {

                    return "TIMEOUT";

                }
            }

        } while (tries < 3);

        return "Ok";

    }

}
