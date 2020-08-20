package ru.cnvnh.weightbarcodescounter.database.adapters.view_holders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIOBarcodeItemCallback;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemBarcodeBinding;

public class CIOBarcodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
	private static final String TAG = "CIOBarcodeViewHold";
	
	private CioItemBarcodeBinding mBinding;
	
	private CIOBarcodeItemCallback mBarcodeItemCallbackListener;
	
	public CIOBarcodeViewHolder(CioItemBarcodeBinding binding, CIOBarcodeItemCallback listener)
	{
		super(binding.getRoot());
		
		mBinding = binding;
		mBinding.getRoot().setOnClickListener(this);
		mBinding.getRoot().setOnLongClickListener(this);
		
		mBarcodeItemCallbackListener = listener;
	}
	
	public void bindTo(CIOBarcode barcode, boolean atBottom)
	{
		mBinding.getRoot().setBackgroundResource(atBottom ? R.drawable.cio_background_round_bottom : R.drawable.cio_background);
		
		mBinding.cioWeightText.setText(String.format(Locale.US, "%.1f g", (float) barcode.weight / 10.0f));
	}
	
	@Override
	public void onClick(View view)
	{
		mBarcodeItemCallbackListener.onBarcodeClick(getAdapterPosition());
	}
	
	@Override
	public boolean onLongClick(View view)
	{
		mBarcodeItemCallbackListener.onBarcodeLongClick(getAdapterPosition());
		
		return false;
	}
}
