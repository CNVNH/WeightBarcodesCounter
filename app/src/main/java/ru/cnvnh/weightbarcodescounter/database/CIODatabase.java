package ru.cnvnh.weightbarcodescounter.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ru.cnvnh.weightbarcodescounter.database.converters.CIODateConverter;
import ru.cnvnh.weightbarcodescounter.database.daos.CIOBarcodeDao;
import ru.cnvnh.weightbarcodescounter.database.daos.CIONoteDao;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.database.models.CIONote;

@Database(entities = {CIONote.class, CIOBarcode.class}, exportSchema = false, version = 4)
@TypeConverters(CIODateConverter.class)
public abstract class CIODatabase extends RoomDatabase
{
	private static final String TAG = "CIODatabase";
	
	/* ****************************************************************************************** *
	 * * INSTANCE																				* *
	 * ****************************************************************************************** */
	
	private static CIODatabase instance;
	
	public static synchronized CIODatabase getInstance(final Context context)
	{
		if(instance == null)
		{
			instance = Room.databaseBuilder(context.getApplicationContext(), CIODatabase.class, "cio_weight_barcodes_counter_database").fallbackToDestructiveMigration().build();
		}
		
		return instance;
	}
	
	/* ****************************************************************************************** *
	 * * DAOS																					* *
	 * ****************************************************************************************** */
	
	public abstract CIONoteDao notes();
	public abstract CIOBarcodeDao barcodes();
}
