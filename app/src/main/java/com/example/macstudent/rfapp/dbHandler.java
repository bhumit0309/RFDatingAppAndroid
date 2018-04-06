package com.example.macstudent.rfapp;

/**
 * Created by macstudent on 2018-01-26.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class dbHandler extends  SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "detailsManager";

    // Contacts table name
    private static final String TABLE_DETAILS = "details";

    // Contacts Table Columns names
    private static final String NAME = "displayName";
    private static final String GENDER = "gender";
    private static final String CITY = "city";
    private static final String BIO = "bio";
    private static final String LAT = "lat";
    private static final String LONG = "long";
    private static final String HEIGHT = "height";
    private static final String IMAGE = "image";
    private static final String ID = "id";


    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DETAILS + "("
                + NAME + " TEXT," + BIO + " TEXT,"
                + CITY + " TEXT," + GENDER + " TEXT,"
                + HEIGHT + " TEXT," + LAT + " TEXT,"
                + LONG + " TEXT," + IMAGE + " TEXT,"
                + ID + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);

        // Create tables again
        onCreate(db);
    }


    // Adding new contact
    void addDetail(AndroidVersion androidVersion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, androidVersion.getDisplayName()); //Name
        values.put(BIO, androidVersion.getSignature()); //Name
        values.put(CITY, androidVersion.getCity()); //City
        values.put(GENDER, androidVersion.getGender()); //Gender
        values.put(HEIGHT, androidVersion.getHeight()); //Height
        values.put(LAT, androidVersion.getLatitude()); //Lat
        values.put(LONG, androidVersion.getLongitude()); //Long
        values.put(IMAGE, androidVersion.getMainImage()); //Image
        values.put(ID, androidVersion.getId()); //ID

        // Inserting Row
        db.insert(TABLE_DETAILS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    AndroidVersion getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DETAILS, new String[] { NAME,
                        GENDER, CITY, BIO, LAT, LONG }, NAME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        AndroidVersion androidVersion = new AndroidVersion(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)), Double.parseDouble(cursor.getString(5)),
                Double.parseDouble(cursor.getString(6)), cursor.getString(7), cursor.getString(8));
        // return obj
        return androidVersion;
    }
}
