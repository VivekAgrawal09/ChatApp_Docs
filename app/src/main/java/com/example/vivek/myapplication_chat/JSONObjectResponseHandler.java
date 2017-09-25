package com.example.vivek.myapplication_chat;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by pratim on 11/8/16.
 */
public interface JSONObjectResponseHandler {
    void onResponse(JSONObject response);
    void onErrorResponse(VolleyError error);
}
