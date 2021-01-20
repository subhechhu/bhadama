package com.subhechhu.bhadama.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.subhechhu.bhadama.AppController;
import com.subhechhu.bhadama.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class APIRequest {

    private static final String TAG = APIRequest.class.getSimpleName();
    final RequestQueue queue;
    final FromAPI fromAPI;

    public APIRequest(FromAPI fromAPI) {
        this.fromAPI = fromAPI;
        queue = Volley.newRequestQueue(AppController.getContext());
    }

    public void makePostRequest(String url, Map<String, Object> params, int requestCode) {
        Log.e(TAG, "post url: " + url);

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params), response -> {
            Log.e(TAG, "post respnse: " + response.toString());
            fromAPI.getResponse(response.toString(), requestCode);
        },
                error -> VolleyLog.e(TAG, "Error: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                Log.e(TAG, "authorization: " + AppController.getPreferenceString(AppController.getContext().getString(R.string.at)));
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", AppController.getPreferenceString(AppController.getContext().getString(R.string.at)));
                return params;
            }
        };
        queue.add(req);
    }

    public void makePutRequest(String url, Map<String, String> params, int requestCode) {
        Log.e(TAG, "post url: " + url);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(params), response -> {
            Log.e(TAG, "post respnse: " + response.toString());
            fromAPI.getResponse(response.toString(), requestCode);
        },
                error -> VolleyLog.e(TAG, "Error: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", AppController.getPreferenceString(AppController.getContext().getString(R.string.at)));
                return params;
            }
        };
        queue.add(req);
    }

    public void makeGetRequest(String url, int requestCode) {
        Log.e(TAG, "get url: " + url);
        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.e(TAG, "getResponse: " + response);
                    fromAPI.getResponse(response, requestCode);
                },
                error -> VolleyLog.e(TAG, "Error: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", AppController.getPreferenceString(AppController.getContext().getString(R.string.at)));
                return params;
            }
        };
        queue.add(req);
    }


    public void makeDeleteRequest(String url, int requestCode) {
        Log.e(TAG, "get url: " + url);
        StringRequest req = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    Log.e(TAG, "getResponse: " + response);
                    fromAPI.getResponse(response, requestCode);
                },
                error -> VolleyLog.e(TAG, "Error: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", AppController.getPreferenceString(AppController.getContext().getString(R.string.at)));
                return params;
            }
        };
        queue.add(req);
    }


    public void makePatchRequest(String url, Map<String, Object> params, int requestCode) {
        Log.e(TAG, "post url: " + url);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PATCH, url, new JSONObject(params), response -> {
            Log.e(TAG, "patch respnse: " + response.toString());
            fromAPI.getResponse(response.toString(), requestCode);
        },
                error -> VolleyLog.e(TAG, "Error: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                Log.e(TAG, "authorization: " + AppController.getPreferenceString(AppController.getContext().getString(R.string.at)));
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", AppController.getPreferenceString(AppController.getContext().getString(R.string.at)));
                return params;
            }
        };
        queue.add(req);
    }

    public void makeImageUploadRequest(String url, byte[] bytes, int requestCode) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                response -> {
                    String resultResponse = new String(response.data);
                    fromAPI.getResponse(resultResponse, requestCode);
                },
                error -> Log.e(TAG, "" + error.getMessage())) {

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".png", bytes));
                return params;
            }
        };
        queue.add(volleyMultipartRequest);

    }

    public interface FromAPI {
        void getResponse(String data, int requestCode);
    }
}
