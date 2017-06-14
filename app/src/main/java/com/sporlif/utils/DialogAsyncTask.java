package com.sporlif.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sporlif.R;

public abstract class DialogAsyncTask<T> extends AsyncTask<T,Integer,T>{

    Context context;
    AlertDialog loadingDialog;

    public DialogAsyncTask(Context context){
        this.context = context;
    }

    protected abstract T task();
    protected abstract void result(T res);

    protected void execute(){
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.dlg_loading_dialog_title);
        builder.setMessage(R.string.dlg_loading_dialog_msg);
        builder.setIcon(R.drawable.ico_info);
        builder.setCancelable(false);
        loadingDialog = builder.create();
        loadingDialog.show();
    }

    @Override
    protected T doInBackground(T... params) {
        return task();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(T s) {
        super.onPostExecute(s);
        loadingDialog.dismiss();
        result(s);
    }

    @Override
    protected void onCancelled(T s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
