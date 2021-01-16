package co.com.ceiba.mobile.pruebadeingreso.storage.cloud.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import co.com.ceiba.mobile.pruebadeingreso.R;
import rx.Single;

public class InternetAccess<T> {

    private Context context;
    private static boolean isOnline;

    public InternetAccess(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void checkConnectionStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return;

        cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                isOnline = true;
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                isOnline = false;
            }
        });
    }

    public Single<T> noInternet() {
        return Single.create(singleSubscriber ->
                singleSubscriber.onError(new Throwable(context.getString(R.string.no_internet)))
        );
    }

    public Single<T> check(Single<T> t) {
        Log.d("CONNECTION", "CHECK FUNCTION [isOnline] " + isOnline);

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return noInternet();

        Log.d("CONNECTION", "CHECK IF VERSION ANDROID [isOnline] " + isOnline);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                return t;
            }
        }

        Log.d("CONNECTION", "CHECK IF IS ONLINE " + isOnline);
        if (isOnline) return t;

        return noInternet();
    }
}

