package org.wit.placemark.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.*
import java.util.*

val JSON_FILE = "placemarks.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<SupermarketModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SupermarketJSONStore : SupermarketStore, AnkoLogger {

    val context: Context
    var placemarks = mutableListOf<SupermarketModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SupermarketModel> {
        return placemarks
    }

    override fun create(placemark: SupermarketModel) {
        placemark.id = generateRandomId()
        placemarks.add(placemark)
        serialize()
    }


    override fun update(placemark: SupermarketModel) {
        val placemarksList = findAll() as ArrayList<SupermarketModel>
        var foundPlacemark: SupermarketModel? = placemarksList.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.price = placemark.price
            foundPlacemark.image = placemark.image
            foundPlacemark.lat = placemark.lat
            foundPlacemark.lng = placemark.lng
            foundPlacemark.zoom = placemark.zoom
        }
    }
    override fun delete(placemark: SupermarketModel) {
        placemarks.remove(placemark)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(placemarks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        placemarks = Gson().fromJson(jsonString, listType)
    }
}
