package no.bloetekjaer.androidkotlinexam

import no.bloetekjaer.model.places.Place

interface ClickPlacesListener {

    fun onClickPlaceName(place: Place)

    fun onClickIcon(lat: Double, lng: Double)
}