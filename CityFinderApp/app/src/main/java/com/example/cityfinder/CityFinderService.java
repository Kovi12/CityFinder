package com.example.cityfinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.mongodb.lang.Nullable;


public class CityFinderService extends Service {
    private final ICityFinder.Stub binder = new ICityFinder.Stub() {


        @Override
        public void RetrieveInfo(ICallback callback) {
            Log.d("begin Retrieve info","da ba");
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://data.mongodb-api.com/app/data-tljxf/endpoint/data";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                String responseString = response.toString();
                try {
                    callback.OnSuccess(responseString);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }, Throwable::printStackTrace);
            queue.add(jsonArrayRequest);
        }

        @Override
        public double getDistance(double lat1, double lon1, double lat2, double lon2) {
            double R = 6371;
            double dLat = deg2rad(lat2 - lat1);
            double dLon = deg2rad(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double d = R * c;
            return d;
        };
    };

    private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    public CityFinderService() {
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}