package no.bloetekjaer.androidkotlinexam

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.bloetekjaer.androidkotlinexam.model.places.Place
import java.util.*
import kotlin.collections.ArrayList


class NoForeignLandPlacesAdapter (private var list: List<Place>, private var listener: ClickPlacesListener) :
    RecyclerView.Adapter<NoForeignLandPlacesAdapter.PlacesViewHolder>(), Filterable {

    private var listCopy = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place: Place = list[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(chars: CharSequence): FilterResults {
                val tempList = listCopy
                val filterResults = FilterResults()
                val filteredList: MutableList<Place> = ArrayList()

                if (chars.isEmpty()) {
                    filteredList.addAll(tempList)
                } else {
                    val filterPattern = chars.toString().toLowerCase(Locale.getDefault()).trim()
                    tempList.map { place ->
                        if (place.properties?.name!!.toLowerCase(Locale.getDefault())
                                .trim()
                                .contains(filterPattern)) {
                            filteredList.add(place)
                        }
                    }
                }

                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(c: CharSequence, result: FilterResults) {
                val newList: MutableList<Place>? = result.values as MutableList<Place>
                if (!newList.isNullOrEmpty()) {
                    list = newList
                    notifyDataSetChanged()
                }
            }
        }
    }


    inner class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
        :RecyclerView.ViewHolder(inflater.inflate(R.layout.places_name_layout, parent, false)) {
        private var textPlace: TextView? = null

        init {
            val mPlaceName: TextView? = itemView.findViewById(R.id.placesName)
            val mIcon: ImageView? = itemView.findViewById(R.id.iconView)
            textPlace = mPlaceName

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