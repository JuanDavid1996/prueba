package co.com.ceiba.mobile.pruebadeingreso

import android.app.Application
import co.com.ceiba.mobile.pruebadeingreso.storage.local.LocalStorage

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LocalStorage.initDb(this)
    }
}