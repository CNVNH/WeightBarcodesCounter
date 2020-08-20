package ru.cnvnh.weightbarcodescounter.ui.view_models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executors;

import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.database.repos.CIOBarcodeRepo;
import ru.cnvnh.weightbarcodescounter.database.repos.callbacks.CIOBarcodeRepoCallback;

public class CIOBarcodesViewModel extends ViewModel
{
	private static final String TAG = "CIOBarcodesViewModel";
	
	private LiveData<List<CIOBarcode>> mBarcodes;
	
	public LiveData<List<CIOBarcode>> getBarcodes()
	{
		return mBarcodes;
	}
	
	public void loadByTimestamp(final Context context, final long timestamp)
	{
		mBarcodes = CIOBarcodeRepo.getInstance().getByTimestamp(context, timestamp);
	}
	
	public void saveBarcode(final Context context, final CIOBarcode barcode)
	{
		CIOBarcodeRepo.getInstance().saveBarcode(context, barcode);
	}
	
	public void deleteBarcodes(final Context context, final List<CIOBarcode> barcodes, final CIOBarcodeRepoCallback barcodeRepoCallbackListener)
	{
		CIOBarcodeRepo.getInstance().deleteBarcodes(context, barcodes, barcodeRepoCallbackListener);
	}
}
