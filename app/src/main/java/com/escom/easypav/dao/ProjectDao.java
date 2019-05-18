package com.escom.easypav.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.escom.easypav.entities.Project;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);
}
