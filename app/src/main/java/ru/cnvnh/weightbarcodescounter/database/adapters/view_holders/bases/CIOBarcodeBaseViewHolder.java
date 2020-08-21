package ru.cnvnh.weightbarcodescounter.database.adapters.view_holders.bases;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIOBarcodeItemCallback;

public abstract class CIOBarcodeBaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
	protected CIOBarcodeItemCallback mBarcodeItemCallbackListener;
	
	public CIOBarcodeBaseViewHolder(@NonNull View itemView)
	{
		super(itemView);
	}
}
