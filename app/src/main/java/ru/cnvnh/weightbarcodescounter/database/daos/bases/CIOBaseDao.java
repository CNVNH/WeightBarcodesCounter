package ru.cnvnh.weightbarcodescounter.database.daos.bases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CIOBaseDao<T>
{
	@Insert
	long insert(T obj);
	
	@Update
	int update(T obj);
	
	@Delete
	int delete(List<T> objs);
}
