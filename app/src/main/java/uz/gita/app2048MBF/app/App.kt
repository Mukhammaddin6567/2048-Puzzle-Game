package uz.gita.app2048MBF.app

import android.app.Application
import android.util.Log
import timber.log.Timber
import uz.gita.app2048MBF.data.local.MySharedPreferences
import uz.gita.app2048MBF.data.repository.impl.AppRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Timber
        Timber.plant(Timber.DebugTree())
        Timber.d("onCreate()")

        MySharedPreferences.init(this)
        AppRepositoryImpl.init()
    }
}