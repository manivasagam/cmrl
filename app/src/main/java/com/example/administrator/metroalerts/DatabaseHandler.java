package com.example.administrator.metroalerts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    private static final String TABLE_TRAIN = "train";
    private static final String TABLE_STATION = "station";
    private static final String TABLE_ROUTE = "route";
    private static final String TABLE_TRAIN_ROUTE = "train_route";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROUTE_TABLE = "CREATE TABLE `route` (`sid1` INTEGER NOT NULL,`sid2` INTEGER NOT NULL,`distance` INTEGER,`rid` INTEGER PRIMARY KEY AUTOINCREMENT)";
        String CREATE_STATION_TABLE = "CREATE TABLE station ( `sid` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL, `longitude` TEXT NOT NULL, `latitude` TEXT NOT NULL, `contact_number` INTEGER, `feeder_services` TEXT)";
        String CREATE_TRAIN_TABLE = "CREATE TABLE `train` ( `tid`  INTEGER, `name` TEXT NOT NULL,`descp`  TEXT,PRIMARY KEY(tid))";
        String CREATE_TRAIN_ROUTE_TABLE = "CREATE TABLE train_route ( `tid`  INTEGER,  `rid`  INTEGER,  `arrival_time`  TEXT, `time_interval` TEXT  , `fare`  INTEGER,  PRIMARY KEY(tid,rid,arrival_time))";



        db.execSQL(CREATE_ROUTE_TABLE);
        db.execSQL(CREATE_STATION_TABLE);
        db.execSQL(CREATE_TRAIN_TABLE);
        db.execSQL(CREATE_TRAIN_ROUTE_TABLE);

        db.execSQL("INSERT INTO `train_route` VALUES (1,1,'08:25','12',122);");
        db.execSQL("INSERT INTO `train_route` VALUES (2,1,'05:11','12',111);");
        db.execSQL("INSERT INTO `train_route` VALUES (1,2,'10:25','11',333);");
        db.execSQL("INSERT INTO `train_route` VALUES (3,4,'21:10','11',111);");
        db.execSQL("INSERT INTO `train_route` VALUES (4,3,'13:11','12',1111);");

        db.execSQL("INSERT INTO `train` VALUES (1,'train1',NULL);");
        db.execSQL("INSERT INTO `train` VALUES (2,'train2',NULL);");
        db.execSQL("INSERT INTO `train` VALUES (3,'train3',NULL);");
        db.execSQL("INSERT INTO `train` VALUES (4,'train4',NULL);");
        db.execSQL("INSERT INTO `train` VALUES (5,'train5',NULL);");

        db.execSQL("INSERT INTO `station` VALUES (1,'station1','31.1','31	',3123,NULL); ");
        db.execSQL("INSERT INTO `station` VALUES (2,'station2','32','32.5',3232423,NULL);");
        db.execSQL("INSERT INTO `station` VALUES (3,'station3','31.2','32',322131,NULL); ");
        db.execSQL("INSERT INTO `station` VALUES (4,'station4','33','33',34234234,NULL); ");

        db.execSQL("INSERT INTO `route` VALUES (1,2,12,1); ");
        db.execSQL("INSERT INTO `route` VALUES (1,3,11,2); ");
        db.execSQL("INSERT INTO `route` VALUES (2,4,12,3); ");
        db.execSQL("INSERT INTO `route` VALUES (3,4,8,4);  ");
        db.execSQL("INSERT INTO `route` VALUES (4,2,111,5);");
        db.execSQL("INSERT INTO `route` VALUES (4,1,11,6); ");


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAIN_ROUTE);
        // Create tables again
        onCreate(db);
    }

    public List<String> getAlLStations() {
        List<String> StationsList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "select name from station;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // Adding contact to list
                StationsList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return contact list
        return StationsList;
    }
    public List<TrainInfo> getStationInfo(String station) {
        List<TrainInfo> StationsList = new ArrayList<TrainInfo>();
        // Select All Query
        String selectQuery = "select train.name,train.descp,train_route.fare,train_route.time_interval,train_route.arrival_time,route.distance," +
                "station.name from route inner join train_route on route.rid = train_route.rid inner join train on train.tid = train_route.tid" +
                " inner join station on station.sid = route.sid2 where route.sid1 =(select station.sid from station where station.name='" +
                station +
                "') " +
                "order by train_route.arrival_time;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // Adding contact to list
                TrainInfo info = new TrainInfo();
                info.name = cursor.getString(0);
                info.descp = cursor.getString(1);
                info.fare = cursor.getString(2);
                info.time_interval = cursor.getString(3);
                info.arrival_time = cursor.getString(4);
                info.distance = cursor.getString(5);
                info.Stationname = cursor.getString(6);

                StationsList.add(info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return StationsList;
    }
    public List<TrainInfo> GetFindRoute(String stationFrom,String stationTo) {
        List<TrainInfo> StationsList = new ArrayList<TrainInfo>();
        // Select All Query
        String selectQuery = "select train.name,train.descp,train_route.fare,train_route.time_interval,train_route.arrival_time," +
                "route.distance from route inner join train_route on route.rid = train_route.rid inner join train on train.tid = " +
                "train_route.tid where route.sid1 =(select station.sid from station where station.name='" +
                stationFrom +
                "') and route.sid2 " +
                "=(select station.sid from station where station.name='" +
                stationTo +
                "') order by train_route.arrival_time;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // Adding contact to list
                TrainInfo info = new TrainInfo();
                info.name = cursor.getString(0);
                info.descp = cursor.getString(1);
                info.fare = cursor.getString(2);
                info.time_interval = cursor.getString(3);
                info.arrival_time = cursor.getString(4);
                info.distance = cursor.getString(5);
                //info.Stationname = cursor.getString(6);

                StationsList.add(info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return StationsList;
    }

    public List<TrainInfo> GetTrainTimings(String TrainName) {
        List<TrainInfo> StationsList = new ArrayList<TrainInfo>();
        // Select All Query
        String selectQuery = "select train.name,train.descp,train_route.fare,train_route.time_interval," +
                "train_route.arrival_time,route.distance,station1.name,station2.name from route inner join" +
                " train_route on route.rid = train_route.rid inner join train on train.tid = train_route.tid" +
                " inner join station station1 on station1.sid = route.sid1inner join station station2 on station2.sid =" +
                " route.sid2 where train_route.tid= (select train.tid from train where train.name='" +
                TrainName +
                "')order by train_route.arrival_time;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // Adding contact to list
                TrainInfo info = new TrainInfo();
                info.name = cursor.getString(0);
                info.descp = cursor.getString(1);
                info.fare = cursor.getString(2);
                info.time_interval = cursor.getString(3);
                info.arrival_time = cursor.getString(4);
                info.distance = cursor.getString(5);
                info.Stationname = cursor.getString(6)+"--"+cursor.getString(7);

                StationsList.add(info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return StationsList;
    }

    public List<String> getAllTrains() {
        List<String> StationsList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "select name from train;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // Adding contact to list
                StationsList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return contact list
        return StationsList;
    }
}