package org.wit.placemark.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.placemark.models.SupermarketJSONStore
import org.wit.placemark.models.SupermarketStore

class MainApp : Application(), AnkoLogger {

    lateinit var products: SupermarketStore

    override fun onCreate() {
        super.onCreate()
        products = SupermarketJSONStore(applicationContext)
        info("Placemark started")
    }
}