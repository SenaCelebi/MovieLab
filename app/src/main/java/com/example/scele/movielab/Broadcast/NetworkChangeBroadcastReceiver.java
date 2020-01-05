package com.example.scele.movielab.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if(!netCheck(context))
        {
            Toast.makeText(context, "The application can not reach to server at the moment. Network connection is not available.",Toast.LENGTH_LONG).show();
        }
    }

    private boolean netCheck(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
        return nInfo != null && nInfo.isConnected();
    }

}
