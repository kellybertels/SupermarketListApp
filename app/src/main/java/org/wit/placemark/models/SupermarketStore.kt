package org.wit.placemark.models

interface SupermarketStore {
    fun findAll(): List<SupermarketModel>
    fun create(placemark: SupermarketModel)
    fun update(placemark: SupermarketModel)
    fun delete(placemark: SupermarketModel)
}