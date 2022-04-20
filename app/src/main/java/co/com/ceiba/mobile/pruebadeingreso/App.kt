package co.com.ceiba.mobile.pruebadeingreso;

import android.app.Application;

import co.com.ceiba.mobile.pruebadeingreso.storage.local.LocalStorage;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocalStorage.initDb(this);
    }

}
