package ru.cnvnh.weightbarcodescounter.database.models;

import java.util.ArrayList;
import java.util.List;

import ru.cnvnh.weightbarcodescounter.database.models.bases.CIOExpandableListItem;

public class CIOBarcodeParent extends CIOExpandableListItem
{
	public int totalWeight;
	
	public boolean isExpanded;
	
	public List<CIOBarcode> barcodes = new ArrayList<>();
	
	public CIOBarcodeParent()
	{
		isSelected = false;
		isExpanded = false;
	}
	
	@Override
	public int getItemType()
	{
		return BARCODE_PARENT;
	}
}
