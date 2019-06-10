package com.example.androidtask;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PictureRepository {
    private PictureDao itemDao;
    private LiveData<List<Picture>> allItems;

    public PictureRepository(Application application){
        PictureDatabase database = PictureDatabase.getInstance(application);
        itemDao = database.itemDao();
        allItems = itemDao.getAllItems();
    }

    public void insert(Picture item){
        new InsertItemAsyncTask(itemDao).execute(item);
    }

    public void update(Picture item){
        new UpdateItemAsyncTask(itemDao).execute(item);
    }

    public void delete(Picture item){
        new DeleteItemAsyncTask(itemDao).execute(item);
    }

    public void deleteAllItems(){
        new DeleteAllItemsAsyncTask(itemDao).execute();
    }

    public LiveData<List<Picture>> getAllItems(){
        return allItems;
    }

    private static class InsertItemAsyncTask extends AsyncTask<Picture, Void, Void>{
        private PictureDao itemDao;

        private InsertItemAsyncTask(PictureDao itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Picture... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Picture, Void, Void>{
        private PictureDao itemDao;

        private UpdateItemAsyncTask(PictureDao itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Picture... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Picture, Void, Void>{
        private PictureDao itemDao;

        private DeleteItemAsyncTask(PictureDao itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Picture... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllItemsAsyncTask extends AsyncTask<Void, Void, Void>{
        private PictureDao itemDao;

        private DeleteAllItemsAsyncTask(PictureDao itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteAllItems();
            return null;
        }
    }
}
