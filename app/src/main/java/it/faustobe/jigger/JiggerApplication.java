package it.faustobe.jigger;

import android.app.Application;

import it.faustobe.jigger.data.local.database.AppDatabase;

public class JiggerApplication extends Application {

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getInstance(this);
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
