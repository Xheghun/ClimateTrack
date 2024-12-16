package com.xheghun.climatetrack

import android.app.Application
import com.xheghun.climatetrack.domain.di.domainModule
import com.xheghun.climatetrack.presentation.di.uiModule
import com.xheghun.climatetracker.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ClimateTrack : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ClimateTrack)
            modules(
                listOf(
                    uiModule,
                    domainModule,
                    dataModule(this@ClimateTrack)
                )
            )
        }

    }
}