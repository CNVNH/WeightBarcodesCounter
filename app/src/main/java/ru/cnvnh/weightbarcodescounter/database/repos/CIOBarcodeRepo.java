package ru.cnvnh.weightbarcodescounter.database.repos;

import android.content.Context;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

import ru.cnvnh.weightbarcodescounter.database.CIODatabase;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.database.repos.callbacks.CIOBarcodeRepoCallback;

public class CIOBarcodeRepo
{
	private static final String TAG = "CIOBarcodeRepo";
	
	/* ****************************************************************************************** *
	 * * INSTANCE																				* *
	 * ****************************************************************************************** */
	
	private static CIOBarcodeRepo instance;
	
	public static CIOBarcodeRepo getInstance()
	{
		if(instance == null)
		{
			instance = new CIOBarcodeRepo();
		}
		
		return instance;
	}
	
	/* ****************************************************************************************** *
	 * * FUNCTIONS																				* *
	 * ****************************************************************************************** */
	
	public LiveData<List<CIOBarcode>>  getByTimestamp(final Context context, final long timestamp)
	{
		return CIODatabase.getInstance(context).barcodes().getByTimestamp(timestamp);
	}
	
	public void saveBarcode(final Context context, final CIOBarcode barcode)
	{
		Executors.newSingleThreadExecutor().execute(new Runnable()
		{
			@Override
			public void run()
			{
				CIODatabase.getInstance(context).barcodes().insert(barcode);
			}
		});
	}
	
	public void deleteBarcodes(final Context context, final List<CIOBarcode> barcodes, final CIOBarcodeRepoCallback barcodeRepoCallbackListener)
	{
		Executors.newSingleThreadExecutor().execute(new Runnable()
		{
			@Override
			public void run()
			{
				int count = CIODatabase.getInstance(context).barcodes().delete(barcodes);
				
				Looper.prepare();
				
				barcodeRepoCallbackListener.onBarcodesDeleted(count);
				
				Looper.loop();
			}
		});
	}
}
