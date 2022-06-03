package br.com.davidcastro.githubrepositories

import android.app.Application
import br.com.davidcastro.githubrepositories.data.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(module)
        }
    }
}