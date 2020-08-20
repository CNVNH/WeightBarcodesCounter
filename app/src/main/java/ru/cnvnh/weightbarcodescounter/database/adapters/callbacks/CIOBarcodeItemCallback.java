package ru.cnvnh.weightbarcodescounter.database.adapters.callbacks;

public interface CIOBarcodeItemCallback
{
	void onBarcodeClick(int pos);
	void onBarcodeLongClick(int pos);
	void onBarcodeParentClick(int pos);
	void onBarcodeParentLongClick(int pos);
}
