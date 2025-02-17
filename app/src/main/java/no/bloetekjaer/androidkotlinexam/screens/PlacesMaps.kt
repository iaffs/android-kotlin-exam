package no.bloetekjaer.androidkotlinexam.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import no.bloetekjaer.androidkotlinexam.R

class PlacesMaps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_maps)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // I put default value on Oslo
        val lat: Double? = intent.getDoubleExtra("LAT", 59.911491)
        val long: Double? = intent.getDoubleExtra("LONG", 10.757933)

        latLng = LatLng(lat!!, long!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Adds marker on chosen lat and long
        mMap.addMarker(MarkerOptions().position(latLng))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

        // Zooms in on marker
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f), 2500, null)
    }
}
