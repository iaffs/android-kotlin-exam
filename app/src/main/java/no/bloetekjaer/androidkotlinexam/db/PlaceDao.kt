package no.bloetekjaer.androidkotlinexam.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteStatement
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_LAT
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_LNG
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_NAME
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_PLACE_ID
import no.bloetekjaer.androidkotlinexam.db.Table.PLACE_TABLE_NAME
import no.bloetekjaer.model.places.Geometry
import no.bloetekjaer.model.places.Place
import no.bloetekjaer.model.places.Property

class PlaceDao(private var context: Context) {

    private var db: DBHandler = DBHandler.getInstance(context)!!

    fun addList(places : List<Place>) {

        // Begin transaction
        db.writableDatabase.beginTransaction()

        val query =
            "INSERT OR IGNORE INTO $PLACE_TABLE_NAME ($COLUMN_PLACE_ID, $COLUMN_NAME, $COLUMN_LAT, $COLUMN_LNG) VALUES (?, ?, ?, ?)"
        val stmt: SQLiteStatement = db.writableDatabase.compileStatement(query)

        // Iterate place list
        places.forEach { place ->
            // Properties
            stmt.run {

                // Properties
                place.properties?.id?.let { bindLong(1, it) }
                bindString(2, place.properties?.name)

                // Geometry
                place.geometry?.coordinates?.get(1)?.let { bindDouble(3, it) }
                place.geometry?.coordinates?.get(0)?.let { bindDouble(4, it) }

                stmt.execute()
                stmt.clearBindings()
            }
        }

        // Set successful flag
        db.writableDatabase.setTransactionSuccessful()

        // End Transaction
        db.writableDatabase.endTransaction()

        // Close DB
        db.writableDatabase.close()
    }

    fun fetchAll(): MutableList<Place> {

        val cursor: Cursor = db.readableDatabase.query(PLACE_TABLE_NAME, null, null, null, null, null, null)
        val placeList = mutableListOf<Place>()

        with(cursor) {
            while (moveToNext()) {
                val placeId:   Long   = getLong(getColumnIndexOrThrow(COLUMN_PLACE_ID))
                val placeName: String = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val placeLat:  Double = getDouble(getColumnIndexOrThrow(COLUMN_LAT))
                val placeLng:  Double = getDouble(getColumnIndexOrThrow(COLUMN_LNG))

                val properties = Property(placeId, placeName)
                val geometry = Geometry(placeLat, placeLng)
                val place = Place(properties, geometry)
                placeList.add(place)
            }
        }
        db.writableDatabase.close()
        return placeList
    }
}



