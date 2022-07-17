package br.com.davidcastro.githubrepositories

import android.app.Application
import android.content.Context
import br.com.davidcastro.githubrepositories.data.di.module
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(module)
        }

        initHawk(this)
    }

    private fun initHawk(context: Context) {
        Hawk.init(context).build()
    }
}