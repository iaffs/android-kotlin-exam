package no.bloetekjaer.androidkotlinexam

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import no.bloetekjaer.model.places.Place


class NoForeignLandPlacesAdapter (private var list: List<Place>, private var listener: ClickPlacesListener) :
    RecyclerView.Adapter<NoForeignLandPlacesAdapter.PlacesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place: Place = list[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int = list.size

    /*override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filteredList: MutableList<Place> = ArrayList()
                for (row in list) {
                    if (row.getProperties()?.name!!.toLowerCase(Locale.getDefault())
                            .contains(charString.toLowerCase(Locale.getDefault()))) {
                        filteredList.add(row)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(c: CharSequence, res: FilterResults) {
                list = res.values as List<Place>
                notifyDataSetChanged()
            }
        }
    }*/


    inner class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
        :RecyclerView.ViewHolder(inflater.inflate(R.layout.places_name_layout, parent, false)) {
        private var textPlace: TextView? = null

        init {
            val mPlaceName: TextView? = itemView.findViewById(R.id.placesName)
            val mIcon: ImageView? = itemView.findViewById(R.id.iconView)
            textPlace = mPlaceName

            // val place: Place = list[adapterPosition]

            // Place Name click Listener
            mPlaceName?.setOnClickListener {
                listener.onClickPlaceName(list[adapterPosition])
            }


            // Map Icon click listener
            mIcon?.setOnClickListener {
                val lat = list[adapterPosition].geometry!!.coordinates!![0]
                val long = list[adapterPosition].geometry!!.coordinates!![1]
                listener.onClickIcon(lat, long)
            }
        }

        fun bind(place: Place) {
            textPlace?.text = place.properties?.name
        }
    }
}