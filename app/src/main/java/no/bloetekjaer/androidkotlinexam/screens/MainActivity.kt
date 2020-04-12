package no.bloetekjaer.androidkotlinexam.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.bloetekjaer.androidkotlinexam.APIListener
import no.bloetekjaer.androidkotlinexam.ClickPlacesListener
import no.bloetekjaer.androidkotlinexam.NoForeignLandPlacesAdapter
import no.bloetekjaer.androidkotlinexam.R
import no.bloetekjaer.androidkotlinexam.api.AsyncRequest
import no.bloetekjaer.androidkotlinexam.db.PlaceDao
import no.bloetekjaer.androidkotlinexam.model.placedetails.PlaceDetailsEntry
import no.bloetekjaer.androidkotlinexam.model.places.Place
import no.bloetekjaer.androidkotlinexam.model.places.PlacesEntry

/*
3.) fix some minor changes ex error messages
 */

class MainActivity : AppCompatActivity(),
    APIListener,
    ClickPlacesListener {

    private var asyncReq: AsyncRequest? = null
    private var recyclerView: RecyclerView? = null
    private var searchView: SearchView? = null
    private lateinit var adapter: NoForeignLandPlacesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)

        // check db for many entries
        val places = PlaceDao(this).fetchAll()
        if (hasMany(places)) {
            fillAdapter(places)
            Toast.makeText(this, "getting places from database", Toast.LENGTH_LONG).show()
        } else {
            // get places from api
            getAllPlaces()
            Toast.makeText(this, "getting places from API", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        searchView = searchItem.actionView as SearchView
        searchView!!.setOnCloseListener { true }
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun hasMany(list : List<Place>): Boolean {
        return list.size > 11000
    }

    private fun fillAdapter(placeList: List<Place>) {

        runOnUiThread {
            // Setting up recyclerView
            adapter = NoForeignLandPlacesAdapter(placeList, this)
            recyclerView?.layoutManager =
                LinearLayoutManager(this) // We want the list to be linear & vertical list
            recyclerView?.adapter = adapter // associating the adapter with recyclerView
        }
    }

    private fun getAllPlaces() {
        // Getting all places from API (noforeignland.com)
        asyncReq = AsyncRequest()
        asyncReq!!.setParams(true)
        asyncReq!!.setListener(this)
        asyncReq!!.execute()

    }

    private fun getPlaceDetails(placeId: Long) {

        asyncReq = AsyncRequest()
        asyncReq!!.setParams(false, placeId)
        asyncReq!!.setListener(this)
        asyncReq!!.execute()

    }

    override fun onPlacesSuccess(placesEntry: PlacesEntry) {
        Toast.makeText(
            this@MainActivity,
            "Places success ${placesEntry.placeList?.size}",
            Toast.LENGTH_LONG
        ).show()

        placesEntry.placeList?.let { fillAdapter(it) }

       val placeDao = PlaceDao(applicationContext)
        placesEntry.placeList?.let { placeDao.addList(it) }

    }

    override fun onPlacesError() {
        Toast.makeText(this@MainActivity, "Places error", Toast.LENGTH_LONG).show()
    }

    override fun onDetailsSuccess(placeDetailsEntry: PlaceDetailsEntry) {
        Log.d("details success", placeDetailsEntry.placeDetails?.banner)
        val intent = Intent(this@MainActivity, PlaceDetailsActivity::class.java)
        intent.putExtra("CLICKABLE_CONTENT", placeDetailsEntry.placeDetails)
        startActivity(intent)
    }

    override fun onDetailsError() {
        Toast.makeText(this@MainActivity, "Places details success", Toast.LENGTH_LONG).show()
    }

    override fun onClickPlaceName(place: Place) {
        place.properties?.id?.let { getPlaceDetails(it) }
    }

    override fun onClickIcon(lat: Double, lng: Double) {
        val intent = Intent(this@MainActivity, PlacesMaps::class.java)
            intent.putExtra("LAT", lat)
            intent.putExtra("LONG", lng)
        startActivity(intent)
    }
}

