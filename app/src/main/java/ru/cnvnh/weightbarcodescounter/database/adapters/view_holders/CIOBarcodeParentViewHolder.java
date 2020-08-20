package ru.cnvnh.weightbarcodescounter.database.adapters.view_holders;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import java.util.Locale;

import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIOBarcodeItemCallback;
import ru.cnvnh.weightbarcodescounter.database.adapters.view_holders.bases.CIOBarcodeBaseViewHolder;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcodeParent;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemBarcodeParentBinding;

public class CIOBarcodeParentViewHolder extends CIOBarcodeBaseViewHolder
{
	private static final String TAG = "CIOBarcodeParentViewHol";
	
	private CioItemBarcodeParentBinding mBinding;
	
	public CIOBarcodeParentViewHolder(CioItemBarcodeParentBinding binding, CIOBarcodeItemCallback listener)
	{
		super(binding.getRoot());
		
		mBinding = binding;
		mBinding.getRoot().setOnClickListener(this);
		mBinding.getRoot().setOnLongClickListener(this);
		mBinding.cioCheckbox.setOnClickListener(this);
		
		mBarcodeItemCallbackListener = listener;
	}
	
	public void bindTo(CIOBarcodeParent parent, boolean showCheckbox)
	{
		Log.d(TAG, "bindTo");
		
		mBinding.getRoot().setBackgroundResource(parent.isExpanded ? R.drawable.cio_background_round_top : R.drawable.cio_background_round);
		
		mBinding.cioCodeText.setText(parent.code);
		mBinding.cioTotalWeightText.setText(String.format(Locale.US, "%.1f g", (float) parent.totalWeight / 10.0f));
		
		mBinding.cioCheckbox.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);
		mBinding.cioCheckbox.setChecked(parent.isSelected);
	}
	
	@Override
	public void onClick(View view)
	{
		Log.d(TAG, "onClick");
		
		int id = view.getId();
		
		if(id == R.id.cio_barcode_parent_item_layout)
		{
			mBarcodeItemCallbackListener.onBarcodeParentClick(getAdapterPosition());
			
		}
		else
		{
			mBarcodeItemCallbackListener.onBarcodeParentCheckboxClick(getAdapterPosition(), mBinding.cioCheckbox.isChecked());
		}
	}
	
	@Override
	public boolean onLongClick(View view)
	{
		Log.d(TAG, "onLongClick");
		
		mBarcodeItemCallbackListener.onBarcodeParentLongClick(getAdapterPosition());
		
		return true;
	}
}
