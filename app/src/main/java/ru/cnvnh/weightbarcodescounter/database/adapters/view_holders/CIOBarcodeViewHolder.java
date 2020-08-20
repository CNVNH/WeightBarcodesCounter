package ru.cnvnh.weightbarcodescounter.database.adapters.view_holders;

import android.util.Log;
import android.view.View;

import java.util.Locale;

import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIOBarcodeItemCallback;
import ru.cnvnh.weightbarcodescounter.database.adapters.view_holders.bases.CIOBarcodeBaseViewHolder;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemBarcodeBinding;

public class CIOBarcodeViewHolder extends CIOBarcodeBaseViewHolder
{
	private static final String TAG = "CIOBarcodeViewHold";
	
	private CioItemBarcodeBinding mBinding;
	
	public CIOBarcodeViewHolder(CioItemBarcodeBinding binding, CIOBarcodeItemCallback listener)
	{
		super(binding.getRoot());
		
		mBinding = binding;
		mBinding.getRoot().setOnClickListener(this);
		mBinding.getRoot().setOnLongClickListener(this);
		mBinding.cioCheckbox.setOnClickListener(this);
		
		mBarcodeItemCallbackListener = listener;
	}
	
	public void bindTo(CIOBarcode barcode, boolean atBottom, boolean showCheckbox)
	{
		Log.d(TAG, "bindTo");
		
		mBinding.getRoot().setBackgroundResource(atBottom ? R.drawable.cio_background_round_bottom : R.drawable.cio_background);
		
		mBinding.cioWeightText.setText(String.format(Locale.US, "%.1f g", (float) barcode.weight / 10.0f));
		
		mBinding.cioCheckbox.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);
		mBinding.cioCheckbox.setChecked(barcode.isSelected);
	}
	
	@Override
	public void onClick(View view)
	{
		Log.d(TAG, "onClick");
		
		int id = view.getId();
		
		if(id == R.id.cio_barcode_item_layout)
		{
			mBarcodeItemCallbackListener.onBarcodeClick(getAdapterPosition());
		}
		else if(id == R.id.cio_checkbox)
		{
			mBarcodeItemCallbackListener.onBarcodeCheckboxClick(getAdapterPosition(), mBinding.cioCheckbox.isChecked());
		}
	}
	
	@Override
	public boolean onLongClick(View view)
	{
		Log.d(TAG, "onLongClick");
		
		mBarcodeItemCallbackListener.onBarcodeLongClick(getAdapterPosition());
		
		return true;
	}
}
