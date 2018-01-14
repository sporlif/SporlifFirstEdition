package com.sporlif.utils;

import android.content.Context;

import com.sporlif.R;

/**
 * Created by MAURICIO on 30/12/2017.
 */

public class MakeURLRequest {

    public static String getUrlRequest(Context context, String module){

        return context.getString(R.string.url_ws_connection) + context.getString(R.string.url_ws_module) + module;

    }

    public static String getUrlRequest(Context context, String module, String option){

        return context.getString(R.string.url_ws_connection) + context.getString(R.string.url_ws_module) + module + "&" + context.getString(R.string.url_ws_op) + option;

    }

    public static String getUrlRequest(Context context, String module, String option, String filter){

        return context.getString(R.string.url_ws_connection) + context.getString(R.string.url_ws_module) + module + "&" + context.getString(R.string.url_ws_op) + option + "&" + context.getString(R.string.url_ws_filter) + filter;

    }

}
