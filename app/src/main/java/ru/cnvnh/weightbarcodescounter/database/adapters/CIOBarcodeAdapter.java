package ru.cnvnh.weightbarcodescounter.database.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIOBarcodeItemCallback;
import ru.cnvnh.weightbarcodescounter.database.adapters.view_holders.CIOBarcodeViewHolder;
import ru.cnvnh.weightbarcodescounter.database.adapters.view_holders.CIOBarcodeParentViewHolder;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcodeParent;
import ru.cnvnh.weightbarcodescounter.database.models.bases.CIOExpandableListItem;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemBarcodeBinding;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemBarcodeParentBinding;

public class CIOBarcodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private static final String TAG = "CIOBarcodeAdapter";
	
	private List<CIOExpandableListItem> mItems = new ArrayList<>();
	
	private CIOBarcodeItemCallback mBarcodeItemCallbackListener;
	
	public CIOBarcodeAdapter(CIOBarcodeItemCallback listener)
	{
		mBarcodeItemCallbackListener = listener;
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		if(viewType ==  CIOExpandableListItem.BARCODE_PARENT)
		{
			return new CIOBarcodeParentViewHolder(CioItemBarcodeParentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mBarcodeItemCallbackListener);
		}
		else
		{
			return new CIOBarcodeViewHolder(CioItemBarcodeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mBarcodeItemCallbackListener);
		}
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos)
	{
		if(holder.getItemViewType() == CIOExpandableListItem.BARCODE_PARENT)
		{
			((CIOBarcodeParentViewHolder) holder).bindTo((CIOBarcodeParent) mItems.get(pos));
		}
		else
		{
			boolean atBottom;
			
			if(pos < mItems.size() - 1)
			{
				atBottom = mItems.get(pos).getItemType() != mItems.get(pos + 1).getItemType();
			}
			else
			{
				atBottom = true;
			}
			
			((CIOBarcodeViewHolder) holder).bindTo((CIOBarcode) mItems.get(pos), atBottom);
		}
	}
	
	@Override
	public int getItemCount()
	{
		return mItems.size();
	}
	
	@Override
	public int getItemViewType(int pos)
	{
		return mItems.get(pos).getItemType();
	}
	
	public void setData(List<CIOBarcode> barcodes)
	{
		mItems.clear();
		
		boolean inParents;
		
		for(CIOBarcode barcode : barcodes)
		{
			inParents = false;
			
			for(CIOExpandableListItem item : mItems)
			{
				if(((CIOBarcodeParent) item).code.equals(barcode.code))
				{
					((CIOBarcodeParent) item).totalWeight += barcode.weight;
					((CIOBarcodeParent) item).barcodes.add(barcode);
					inParents = true;
					break;
				}
			}
			
			if(!inParents)
			{
				CIOBarcodeParent parent = new CIOBarcodeParent();
				parent.code = barcode.code;
				parent.totalWeight = barcode.weight;
				parent.isExpanded = false;
				parent.barcodes.add(barcode);
				
				mItems.add(parent);
			}
		}
		
		notifyDataSetChanged();
	}
	
	public void onBarcodeClick(int pos)
	{
		Log.d(TAG, "onBarcodeClick");
	}
	
	public void onBarcodeParentClick(int pos)
	{
		Log.d(TAG, "onBarcodeParentClick");
		
		int startPos = pos + 1;
		int count = ((CIOBarcodeParent) mItems.get(pos)).barcodes.size();
		
		if(((CIOBarcodeParent) mItems.get(pos)).isExpanded)
		{
			mItems.removeAll(((CIOBarcodeParent) mItems.get(pos)).barcodes);
			notifyItemRangeRemoved(startPos, count);
		}
		else
		{
			mItems.addAll(startPos, ((CIOBarcodeParent) mItems.get(pos)).barcodes);
			notifyItemRangeInserted(startPos, count);
		}
		
		((CIOBarcodeParent) mItems.get(pos)).isExpanded = !((CIOBarcodeParent) mItems.get(pos)).isExpanded;
		
		notifyItemChanged(pos);
	}
	
	public void onBarcodeLongClick(int pos)
	{
		Log.d(TAG, "onBarcodeLongClick");
	}
	
	public void onBarcodeParentLongClick(int pos)
	{
		Log.d(TAG, "onBarcodeParentLongClick");
		
		mItems.get(pos).isSelected = !mItems.get(pos).isSelected;
		
		notifyItemChanged(pos);
	}
}
