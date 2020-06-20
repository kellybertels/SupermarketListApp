package org.wit.placemark.activities

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_market.view.*
import org.wit.placemark.R
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.models.SupermarketModel
import org.wit.placemark.activities.PlacemarkAdapter.MainHolder as MainHolder

interface PlacemarkListener {
    fun onPlacemarkClick(placemark: SupermarketModel)
}

class PlacemarkAdapter constructor(private var placemarks: List<SupermarketModel>,
                                   private val listener: PlacemarkListener) : androidx.recyclerview.widget.RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_market, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(placemark: SupermarketModel,  listener : PlacemarkListener) {
            itemView.markTitle.text = placemark.title
            itemView.price.text = placemark.price
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, placemark.image))
            itemView.setOnClickListener { listener.onPlacemarkClick(placemark) }
        }
    }
}