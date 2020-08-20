package ru.cnvnh.weightbarcodescounter.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "notes")
public class CIONote
{
	@PrimaryKey()
	@ColumnInfo(name = "timestamp")
	public long timestamp;
	
	@ColumnInfo(name = "name")
	public String name;
	
	@Ignore
	public boolean isSelected;
	
	public CIONote()
	{
		timestamp = Calendar.getInstance().getTimeInMillis();
		
		isSelected = false;
	}
}
