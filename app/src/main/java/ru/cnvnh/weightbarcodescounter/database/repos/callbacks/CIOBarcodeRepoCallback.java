package ru.cnvnh.weightbarcodescounter.database.repos.callbacks;

public interface CIOBarcodeRepoCallback
{
	void onBarcodesDeleted(int deletedBarcodesCount);
}
