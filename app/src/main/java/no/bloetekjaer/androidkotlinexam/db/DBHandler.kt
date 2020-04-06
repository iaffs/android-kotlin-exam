package no.bloetekjaer.androidkotlinexam.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_ID
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_LAT
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_LAT_TYPE
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_LNG
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_LNG_TYPE
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_NAME
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_NAME_TYPE
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_PLACE_ID
import no.bloetekjaer.androidkotlinexam.db.Table.COLUMN_PLACE_ID_TYPE

class DBHandler(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE ${Table.PLACE_TABLE_NAME} " +
                "(" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                " $COLUMN_PLACE_ID $COLUMN_PLACE_ID_TYPE NOT NULL UNIQUE, " +
                " $COLUMN_NAME $COLUMN_NAME_TYPE, " +
                " $COLUMN_LAT $COLUMN_LAT_TYPE," +
                " $COLUMN_LNG $COLUMN_LNG_TYPE" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private var mInstance: DBHandler? = null
        private const val DB_NAME = "db_places"
        private const val DB_VERSION = 1

        fun getInstance(context: Context): DBHandler? {
            if (mInstance == null)
                mInstance = DBHandler(context.applicationContext)

            return mInstance
        }
    }



}

/*
 "CREATE TABLE ${PlaceTable.PLACE_TABLE_NAME} " +
                "(" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                " $COLUMN_PLACE_ID $COLUMN_PLACE_ID_TYPE NOT NULL UNIQUE, " +
                " $COLUMN_NAME $COLUMN_NAME_TYPE, " +
                " $COLUMN_ICON $COLUMN_ICON_TYPE," +
                " $COLUMN_LAT $COLUMN_LAT_TYPE," +
                " $COLUMN_LNG $COLUMN_LNG_TYPE" +
                " $COLUMN_COUNTRY_CODE $COLUMN_COUNTRY_CODE_TYPE" +
                " $COLUMN_COMMENTS $COLUMN_COMMENTS_TYPE" +
                " $COLUMN_STARS $COLUMN_STARS_TYPE" +
                " $COLUMN_BANNER $COLUMN_BANNER_TYPE" +
                " $COLUMN_IMAGES_JSON $COLUMN__IMAGES_JSON_TYPE" +
                ")"


 */