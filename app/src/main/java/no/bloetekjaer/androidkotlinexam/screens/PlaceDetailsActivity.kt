package no.bloetekjaer.androidkotlinexam.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_place_details.*
import kotlinx.android.synthetic.main.content_place_details.*
import no.bloetekjaer.androidkotlinexam.R
import no.bloetekjaer.model.placedetails.PlaceDetails

class PlaceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val placeDetails: PlaceDetails? = intent.getSerializableExtra("CLICKABLE_CONTENT") as? PlaceDetails
        if (placeDetails != null) {
            if ( ! placeDetails.name.isNullOrEmpty() )
                placeName.text = placeDetails.name

            if( ! placeDetails.comments.isNullOrEmpty() )
                description.text = HtmlCompat.fromHtml(
                placeDetails.comments!!, HtmlCompat.FROM_HTML_MODE_LEGACY)

            if ( ! placeDetails.banner.isNullOrEmpty() )
                Picasso.get().load(placeDetails.banner).into(banner)
        }


        fab.setOnClickListener {
            val intent = Intent(this@PlaceDetailsActivity, PlacesMaps::class.java)
            if (placeDetails != null) {
                intent.putExtra("LAT", placeDetails.lat)
                intent.putExtra("LONG", placeDetails.lon)
            }
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}
