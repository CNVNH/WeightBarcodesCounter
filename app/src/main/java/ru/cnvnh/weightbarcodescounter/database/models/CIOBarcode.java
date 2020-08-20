package ru.cnvnh.weightbarcodescounter.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ru.cnvnh.weightbarcodescounter.database.models.bases.CIOExpandableListItem;

@Entity(tableName = "barcodes",
		foreignKeys = {@ForeignKey(entity = CIONote.class, parentColumns = "timestamp", childColumns = "timestamp", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
public class CIOBarcode extends CIOExpandableListItem
{
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	public long id;
	
	@ColumnInfo(name = "timestamp")
	public long timestamp;
	
	@ColumnInfo(name = "code")
	public String code;
	
	@ColumnInfo(name = "weight")
	public int weight;
	
	@Override
	public int getItemType()
	{
		return BARCODE;
	}
}
