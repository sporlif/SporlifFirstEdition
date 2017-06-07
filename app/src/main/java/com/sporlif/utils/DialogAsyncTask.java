package com.sporlif.utils;

import android.os.AsyncTask;

public abstract class DialogAsyncTask<T> extends AsyncTask<T,Integer,T>{

    public DialogAsyncTask(){
    }

    protected abstract T task();
    protected abstract void result(T res);

    protected void execute(){
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
