package com.example.androidtask;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PictureDao {

    @Insert
    void insert(Picture item);

    @Update
    void update(Picture item);

    @Delete
    void delete(Picture item);

    @Query("DELETE FROM picture_table")
    void deleteAllItems();

    @Query("SELECT * FROM picture_table ORDER BY id DESC")
    LiveData<List<Picture>> getAllItems();
}
