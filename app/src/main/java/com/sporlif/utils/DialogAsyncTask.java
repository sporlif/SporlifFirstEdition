package com.sporlif.utils;

import android.os.AsyncTask;

public abstract class DialogAsyncTask extends AsyncTask<Object,Integer,Object>{

    public DialogAsyncTask(){
    }

    protected abstract Object task();

    protected void execute(){
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... params) {
        System.out.println("Haciendo en background");
        return task();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object s) {
        super.onPostExecute(s);

        System.out.println("resultado: "+s.toString());
    }

    @Override
    protected void onCancelled(Object s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
