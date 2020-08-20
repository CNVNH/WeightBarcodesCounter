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
	
	private boolean mShowCheckboxes;
	
	private CIOBarcodeItemCallback mBarcodeItemCallbackListener;
	
	public CIOBarcodeAdapter(CIOBarcodeItemCallback listener)
	{
		mShowCheckboxes = false;
		
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
			((CIOBarcodeParentViewHolder) holder).bindTo((CIOBarcodeParent) mItems.get(pos), mShowCheckboxes);
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
			
			((CIOBarcodeViewHolder) holder).bindTo((CIOBarcode) mItems.get(pos), atBottom, mShowCheckboxes);
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
	
	public void toggleParentExpanded(int pos)
	{
		CIOBarcodeParent parent = (CIOBarcodeParent) mItems.get(pos);
		
		int startPos = pos + 1;
		int count = parent.barcodes.size();
		
		parent.isExpanded = !parent.isExpanded;
		
		notifyItemChanged(pos);
		
		if(parent.isExpanded)
		{
			mItems.addAll(startPos, parent.barcodes);
			notifyItemRangeInserted(startPos, count);
		}
		else
		{
			mItems.removeAll(parent.barcodes);
			notifyItemRangeRemoved(startPos, count);
		}
	}
	
	public void setCheckedBarcode(int pos, boolean checked)
	{
		CIOBarcode barcode = (CIOBarcode) mItems.get(pos);
		
		barcode.isSelected = checked;
		
		int parentPos = pos - 1;
		
		while(mItems.get(parentPos) instanceof CIOBarcode)
		{
			parentPos--;
		}
		
		CIOBarcodeParent parent = (CIOBarcodeParent) mItems.get(parentPos);
		
		boolean allSelected = true;
		
		for(int i = 0; i < parent.barcodes.size(); i++)
		{
			if(!parent.barcodes.get(i).isSelected)
			{
				allSelected = false;
				
				break;
			}
		}
		
		if(parent.isSelected != allSelected)
		{
			parent.isSelected = allSelected;
			
			notifyItemChanged(parentPos);
		}
		
		notifyItemChanged(pos);
	}
	
	public void setCheckedBarcodeParent(int pos, boolean checked)
	{
		Log.d(TAG, "setCheckedBarcodeParent");
		
		CIOBarcodeParent parent = (CIOBarcodeParent) mItems.get(pos);
		
		parent.isSelected = checked;
		
		for(int i = 0; i < parent.barcodes.size(); i++)
		{
			parent.barcodes.get(i).isSelected = parent.isSelected;
		}
		
		if(parent.isExpanded)
		{
			notifyItemRangeChanged(pos + 1, parent.barcodes.size());
		}
	}
	
	public void showCheckboxesBarcode(int pos)
	{
		showCheckboxes();
		
		setCheckedBarcode(pos, true);
	}
	
	public void showCheckboxesBarcodeParent(int pos)
	{
		showCheckboxes();
		
		setCheckedBarcodeParent(pos, true);
	}
	
	public void showCheckboxes()
	{
		Log.d(TAG, "showCheckboxes");
		
		mShowCheckboxes = true;
		
		notifyDataSetChanged();
	}
	
	public void clearSelection()
	{
		mShowCheckboxes = false;
		
		for(CIOExpandableListItem item : mItems)
		{
			item.isSelected = false;
		}
		
		notifyDataSetChanged();
	}
	
	public List<CIOBarcode> getSelectedBarcodes()
	{
		List<CIOBarcode> barcodes = new ArrayList<>();
		
		for(CIOExpandableListItem item : mItems)
		{
			if(item instanceof CIOBarcodeParent)
			{
				for(CIOBarcode barcode : ((CIOBarcodeParent) item).barcodes)
				{
					if(barcode.isSelected)
					{
						barcodes.add(barcode);
					}
				}
			}
		}
		
		return barcodes;
	}
}
