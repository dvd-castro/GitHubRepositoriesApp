package br.com.davidcastro.githubrepositories.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.davidcastro.githubrepositories.R
import br.com.davidcastro.githubrepositories.view.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        initSupportActionBar()

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun initSupportActionBar() {
        supportActionBar?.setIcon(R.drawable.ic_github)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Repositories"
    }
}