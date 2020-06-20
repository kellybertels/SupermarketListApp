package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_supermarket_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.placemark.R
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.SupermarketModel

class SupermarketListActivity : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermarket_list)
        app = application as MainApp

        /*
        //layout and populate for display
        val layoutManager =
            LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager   //recyclerView is a widget in activity_supermarket_list.xmlml
        loadPlacemarks()

        */

        val layoutManager=GridLayoutManager(this,2)
        recyclerView.layoutManager =layoutManager
        loadProducts()



        //enable action bar and set title
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<MarketActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlacemarkClick(placemark: SupermarketModel) {
        startActivityForResult(intentFor<MarketActivity>().putExtra("placemark_edit", placemark), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadProducts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadProducts() {
        showProducts(app.products.findAll())
    }

    fun showProducts (placemarks: List<SupermarketModel>) {
        recyclerView.adapter = PlacemarkAdapter(placemarks, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}