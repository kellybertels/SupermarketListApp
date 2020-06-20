package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_market.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.models.SupermarketModel
import org.wit.placemark.R
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location

class MarketActivity : AppCompatActivity(), AnkoLogger {

    var market = SupermarketModel()
    lateinit var app : MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)
        app = application as MainApp
        var edit = false

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            market = intent.extras.getParcelable<SupermarketModel>("placemark_edit")
            markTitle.setText(market.title)
            price.setText(market.price)
            placemarkImage.setImageBitmap(readImageFromPath(this, market.image))
            if (market.image != null){
                chooseImage.setText(R.string.change_placemark_image)
            }
            btnAdd.setText(R.string.save_placemark)
        }

        placemarkLocation.setOnClickListener{
            val location = Location(52.245696, -7.139102, 15f)
            if (market.zoom != 0f) {
                location.lat =  market.lat
                location.lng = market.lng
                location.zoom = market.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        btnAdd.setOnClickListener() {
            market.title = markTitle.text.toString()
            market.price = price.text.toString()

            if (market.title.isEmpty()) {
                toast(R.string.enter_placemark_title)
            } else {
                if (edit) {
                    app.products.update(market.copy())
                } else {
                    app.products.create(market.copy())
                }
            }
            info("add Button Pressed: $markTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_market, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.products.delete(market)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    market.image = data.getData().toString()
                    placemarkImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_placemark_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    market.lat = location.lat
                    market.lng = location.lng
                    market.zoom = location.zoom
                }
            }
        }
    }
}