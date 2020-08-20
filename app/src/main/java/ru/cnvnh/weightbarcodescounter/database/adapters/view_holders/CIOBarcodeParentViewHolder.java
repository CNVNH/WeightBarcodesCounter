package ru.cnvnh.weightbarcodescounter.database.adapters.view_holders;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIOBarcodeItemCallback;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcodeParent;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemBarcodeParentBinding;

public class CIOBarcodeParentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
	private static final String TAG = "CIOBarcodeParentViewHol";
	
	private CioItemBarcodeParentBinding mBinding;
	
	private CIOBarcodeItemCallback mBarcodeItemCallbackListener;
	
	public CIOBarcodeParentViewHolder(CioItemBarcodeParentBinding binding, CIOBarcodeItemCallback listener)
	{
		super(binding.getRoot());
		
		mBinding = binding;
		mBinding.getRoot().setOnClickListener(this);
		mBinding.getRoot().setOnLongClickListener(this);
		
		mBarcodeItemCallbackListener = listener;
	}
	
	public void bindTo(CIOBarcodeParent parent)
	{
		Log.d(TAG, "bindTo: isExpanded = " + parent.isExpanded + " , isSelected = " + parent.isSelected);
		
		mBinding.getRoot().setBackgroundResource(parent.isExpanded ? (parent.isSelected ? R.drawable.cio_background_round_top_selected : R.drawable.cio_background_round_top) : (parent.isSelected ? R.drawable.cio_background_round_selected : R.drawable.cio_background_round));
		
		mBinding.cioCodeText.setText(parent.code);
		mBinding.cioTotalWeightText.setText(String.format(Locale.US, "%.1f g", (float) parent.totalWeight / 10.0f));
		
		int textColor = mBinding.getRoot().getContext().getResources().getColor(parent.isSelected ? R.color.cio_text_reversed : R.color.cio_text, null);
		
		mBinding.cioCodeText.setTextColor(textColor);
		mBinding.cioTotalWeightText.setTextColor(textColor);
	}
	
	@Override
	public void onClick(View view)
	{
		mBarcodeItemCallbackListener.onBarcodeParentClick(getAdapterPosition());
	}
	
	@Override
	public boolean onLongClick(View view)
	{
		mBarcodeItemCallbackListener.onBarcodeParentLongClick(getAdapterPosition());
		
		return false;
	}
}
