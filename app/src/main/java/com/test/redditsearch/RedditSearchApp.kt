package com.test.redditsearch

import android.app.Application
import com.test.redditsearch.core.modules.apiModule
import com.test.redditsearch.core.modules.repositoryModule
import com.test.redditsearch.core.modules.retrofitModule
import com.test.redditsearch.core.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class to initialize 3rd party integrations.
 * @author Julius Villagracia
 */
class RedditSearchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@RedditSearchApp)
            modules(
                viewModelModule,
                repositoryModule,
                retrofitModule,
                apiModule
            )
        }
    }
}