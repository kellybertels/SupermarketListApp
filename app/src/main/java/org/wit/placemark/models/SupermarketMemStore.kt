package org.wit.placemark.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class SupermarketMemStore : SupermarketStore, AnkoLogger {

    val placemarks = ArrayList<SupermarketModel>()

    override fun findAll(): List<SupermarketModel> {
        return placemarks
    }

    override fun create(placemark: SupermarketModel) {
        placemark.id = getId()
        placemarks.add(placemark)
        logAll()
    }

    override fun update(placemark: SupermarketModel) {
        var foundPlacemark: SupermarketModel? = placemarks.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.price = placemark.price
            foundPlacemark.image = placemark.image
            foundPlacemark.lat = placemark.lat
            foundPlacemark.lng = placemark.lng
            foundPlacemark.zoom = placemark.zoom
            logAll()
        }
    }
    override fun delete(market: SupermarketModel) {
        placemarks.remove(market)
    }

    fun logAll(){
        placemarks.forEach {info("${it}")}
    }

}