package com.example.macstudent.rfapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by macstudent on 2018-03-05.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("myrealm.realm")
//                .encryptionKey(getKey())
//                .schemaVersion(42)
//                .modules(new MySchemaModule())
//                .migration(new MyMigration())
//                .build();
//// Use the config

    }
}
