package no.bloetekjaer.androidkotlinexam

import no.bloetekjaer.model.placedetails.PlaceDetailsEntry
import no.bloetekjaer.model.places.PlacesEntry

interface APIListener {

    fun onPlacesSuccess(placesEntry : PlacesEntry)
    fun onPlacesError()

    fun onDetailsSuccess(placeDetailsEntry : PlaceDetailsEntry)
    fun onDetailsError()

}