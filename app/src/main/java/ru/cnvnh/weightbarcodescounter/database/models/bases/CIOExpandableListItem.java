package ru.cnvnh.weightbarcodescounter.database.models.bases;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public abstract class CIOExpandableListItem
{
	public static final int BARCODE_PARENT = 0;
	public static final int BARCODE = 1;
	
	public abstract int getItemType();
	
	@Ignore
	public boolean isSelected;
	
	@ColumnInfo(name = "code")
	public String code;
}
