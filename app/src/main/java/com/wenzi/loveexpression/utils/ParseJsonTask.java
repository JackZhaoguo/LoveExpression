package com.wenzi.loveexpression.utils;

import android.os.AsyncTask;

/**
 * Parse the json asynchronously..
 */
public class ParseJsonTask<T> extends AsyncTask<Object, Integer, T> {
    private OnJsonParserListener mListener;
    private Class<T> mClass;
    private String mJsonStr;

    public ParseJsonTask(String jsonStr, Class<T> tclass, OnJsonParserListener listener) {
        mJsonStr = jsonStr;
        mClass = tclass;
        mListener = listener;
    }

    @Override
    protected T doInBackground(Object... params) {
        T result= JsonParser.parseJson(mJsonStr, mClass);
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onPostExecute(T result) {
        if (isCancelled()) {
            mListener = null;
            return;
        }

        if(mListener != null) {
            mListener.onParseResult(result);
        }
        mListener = null;
    }
}