package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SupermarketModel(var id: Long = 0,
                            var title: String = "",
                            var price: String = "",
                            var image: String = "",
                            var lat : Double = 0.0,
                            var lng: Double = 0.0,
                            var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

