package com.example.androidtask;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Picture.class}, version = 1)
public abstract class PictureDatabase extends RoomDatabase {

    private static PictureDatabase instance;

    public abstract PictureDao itemDao();

    public static synchronized PictureDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PictureDatabase.class, "item_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private PictureDao itemDao;

        private PopulateDbAsyncTask(PictureDatabase db){
            itemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Picture("first filename", "Some description", 22.3, 45.3));
            itemDao.insert(new Picture("second filename", "Some sdescription", 24.5, 42.1));
            itemDao.insert(new Picture("third filename", "Some new description", 21.1, 44.3));
            return null;
        }
    }
}
