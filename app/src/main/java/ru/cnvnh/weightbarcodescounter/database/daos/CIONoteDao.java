package ru.cnvnh.weightbarcodescounter.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.cnvnh.weightbarcodescounter.database.daos.bases.CIOBaseDao;
import ru.cnvnh.weightbarcodescounter.database.models.CIONote;

@Dao
public interface CIONoteDao extends CIOBaseDao<CIONote>
{
	@Query("SELECT * FROM notes")
	LiveData<List<CIONote>> getAll();
}
