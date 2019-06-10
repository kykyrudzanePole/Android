package com.example.androidtask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PictureViewModel extends AndroidViewModel {
    private PictureRepository repository;
    private LiveData<List<Picture>> allItems;

    public PictureViewModel(@NonNull Application application) {
        super(application);
        repository = new PictureRepository(application);
        allItems = repository.getAllItems();
    }

    public void insert (Picture item){
        repository.insert(item);
    }

    public void update(Picture item){
        repository.update(item);
    }

    public void delete(Picture item){
        repository.delete(item);
    }

    public void deleteAllItems(){
        repository.deleteAllItems();
    }

    public LiveData<List<Picture>> getAllItems(){
        return allItems;
    }
}
