package ru.cnvnh.weightbarcodescounter.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.cnvnh.weightbarcodescounter.database.daos.bases.CIOBaseDao;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;

@Dao
public interface CIOBarcodeDao extends CIOBaseDao<CIOBarcode>
{
	@Query("SELECT * FROM barcodes WHERE timestamp = :timestamp")
	LiveData<List<CIOBarcode>> getByTimestamp(final long timestamp);
}
