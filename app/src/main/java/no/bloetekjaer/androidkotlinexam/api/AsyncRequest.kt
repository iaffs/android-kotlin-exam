package no.bloetekjaer.androidkotlinexam.api

import android.os.AsyncTask
import android.util.Log
import no.bloetekjaer.androidkotlinexam.APIListener
import no.bloetekjaer.model.placedetails.PlaceDetailsEntry
import no.bloetekjaer.model.places.PlacesEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AsyncRequest : AsyncTask<Void, String, Void> () {

    private var apiListener: APIListener? = null
    private var shouldGetAll: Boolean? = false
    private var placeId: Long? = null

    fun setParams(shouldGetAll: Boolean, placeId: Long? = null) {
        this.shouldGetAll = shouldGetAll
        this.placeId = placeId
    }
    fun setListener(apiListener: APIListener) {
        this.apiListener = apiListener
    }

    override fun doInBackground(vararg params: Void?): Void? {

        if (!shouldGetAll!! && placeId == null)
            throw Exception("need to call .setParams() first")

        val apiClient = APIClient.getIt
        val service = apiClient?.create(APIService::class.java)!!

        // Switch between getAllPlaces() and getPlaceDetails()
        if (shouldGetAll as Boolean) {
            Log.d("AsyncRequest", "Getting ALL places")
            val call = service.getAllPlaces()
            call.enqueue(object : Callback<PlacesEntry> {
                override fun onFailure(call: Call<PlacesEntry>, t: Throwable) {
                    apiListener?.onPlacesError()
                    Log.e("Async", "Throw: $t")
                }
                override fun onResponse(call: Call<PlacesEntry>, response: Response<PlacesEntry>) {
                    if (response.isSuccessful) response.body()?.let {
                        apiListener?.onPlacesSuccess(it)
                    }
                    else apiListener?.onPlacesError()
                    Log.e("Async", "response: $response")
                }
            })
        } else {
            Log.d("AsyncRequest", "Getting PLACE DETAILS: " + placeId!!)
            val call = service.getPlaceDetails(placeId!!)
            call.enqueue(object : Callback<PlaceDetailsEntry> {

                override fun onFailure(call: Call<PlaceDetailsEntry>, t: Throwable) {
                    apiListener?.onDetailsError()
                    Log.e("Async", "Throw: $t")
                }

                override fun onResponse(call: Call<PlaceDetailsEntry>, response: Response<PlaceDetailsEntry>) {
                    if (response.isSuccessful) response.body()?.let {
                        apiListener?.onDetailsSuccess(it)
                    }
                    else
                        apiListener?.onDetailsError()
                    Log.e("Async", "response: $response")
                }
            })

        }
        return null
    }

    override fun onPostExecute(result: Void?) {
    }
}

