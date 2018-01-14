package com.sporlif.utils;

import android.content.Context;

import com.sporlif.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by MAURICIO on 01/01/2018.
 */

public class URLRequest {

    private InputStream response;

    public InputStream getResponse() {
        return response;
    }

    private void setResponse(InputStream response) {
        this.response = response;
    }

    public String executeRequest(Context context, String urlRequest){

        int tries = 0;
        ClientHttpRequest request;

        do {
            try {

                request = new ClientHttpRequest(new URL(urlRequest).openConnection());
                request.setConnectTimeout(ClientHttpRequest.CONNECT_TIMEOUT);

                setResponse(request.post());

                break;

            } catch (Exception e) {
                e.printStackTrace();
                tries++;
                if (tries == Integer.parseInt(context.getString(R.string.limit_tries_for_request))) {

                    return context.getString(R.string.time_out);

                }
            }

        } while (tries < Integer.parseInt(context.getString(R.string.limit_tries_for_request)));

        return context.getString(R.string.successful_request);

    }

}
