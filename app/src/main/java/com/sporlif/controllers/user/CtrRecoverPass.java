package com.sporlif.controllers.user;

import android.content.Context;

import com.sporlif.R;
import com.sporlif.utils.MakeURLRequest;
import com.sporlif.utils.URLRequest;

import javax.json.Json;

/**
 * Created by MAURICIO on 30/12/2017.
 */

public class CtrRecoverPass {

    private Context context;
    private String responseState;
    private String response;
    private String message;
    private URLRequest urlRequest;

    private Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private String getResponseState() {
        return responseState;
    }

    private void setResponseState(String responseState) {
        this.responseState = responseState;
    }

    private void setResponse(String response){
        this.response = response;
    }

    private String getResponse(){
        return this.response;
    }

    private String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String sendEmail(String email){

        String module = getContext().getString(R.string.module_user);
        String option = getContext().getString(R.string.option_changedP);
        String url = MakeURLRequest.getUrlRequest(getContext(), module, option, email);

        urlRequest = new URLRequest();
        setResponseState(urlRequest.executeRequest(getContext(), url));
        setResponse(Json.createReader(urlRequest.getResponse()).readObject().getString("Response"));

        if(getResponseState().equals(getContext().getString(R.string.successful_request)))
            if(getResponse().equals(getContext().getString(R.string.no_found_user)))
                setMessage(getContext().getString(R.string.frg_enter_mail_no_exist_user));
        if(getResponse().equals(getContext().getString(R.string.unsuccessful_send)))
            setMessage(getContext().getString(R.string.frg_enter_mail_unsuccessful_send));
        else if(getResponse().equals(getContext().getString(R.string.successful_send)))
            setMessage(getResponse());
        else if(getResponseState().equals(getContext().getString(R.string.time_out)))
            setMessage(getContext().getString(R.string.has_not_been_able_to_connect_to_server));

        return getMessage();

    }

}
