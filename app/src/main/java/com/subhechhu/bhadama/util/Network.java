package com.subhechhu.bhadama.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.subhechhu.bhadama.AppController;

public class Network {
    public static boolean getConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isAvailable) {
            Toast.makeText(AppController.getInstance(), "No Internet", Toast.LENGTH_SHORT).show();
        }

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
