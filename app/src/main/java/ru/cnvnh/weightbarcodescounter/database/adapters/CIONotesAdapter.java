package ru.cnvnh.weightbarcodescounter.database.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIONoteItemCallback;
import ru.cnvnh.weightbarcodescounter.database.adapters.view_holders.CIONoteViewHolder;
import ru.cnvnh.weightbarcodescounter.database.models.CIONote;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemNoteBinding;

public class CIONotesAdapter extends RecyclerView.Adapter<CIONoteViewHolder>
{
	private List<CIONote> mNotes = new ArrayList<>();
	
	private boolean mShowCheckboxes;
	
	private CIONoteItemCallback mNoteItemCallbackListener;
	
	public CIONotesAdapter(CIONoteItemCallback noteItemCallbackListener)
	{
		mShowCheckboxes = false;
		
		mNoteItemCallbackListener = noteItemCallbackListener;
	}
	
	@NonNull
	@Override
	public CIONoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		return new CIONoteViewHolder(CioItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mNoteItemCallbackListener);
	}
	
	@Override
	public void onBindViewHolder(@NonNull CIONoteViewHolder holder, int pos)
	{
		holder.bindTo(mNotes.get(pos), mShowCheckboxes);
	}
	
	@Override
	public int getItemCount()
	{
		return mNotes.size();
	}
	
	public void setData(List<CIONote> notes)
	{
		mNotes = notes;
		
		notifyDataSetChanged();
	}
	
	public CIONote getNote(int pos)
	{
		return mNotes.get(pos);
	}
	
	public void setChecked(int pos, boolean checked)
	{
		mNotes.get(pos).isSelected = checked;
	}
	
	public void showCheckboxes()
	{
		mShowCheckboxes = true;
		
		notifyDataSetChanged();
	}
	
	public void showCheckboxes(int pos)
	{
		setChecked(pos, true);
		
		showCheckboxes();
	}
	
	public void clearSelection()
	{
		mShowCheckboxes = false;
		
		for(CIONote note : mNotes)
		{
			note.isSelected = false;
		}
		
		notifyDataSetChanged();
	}
	
	public List<CIONote> getSelectedNotes()
	{
		List<CIONote> selectedNotes = new ArrayList<>();
		
		for(CIONote note : mNotes)
		{
			if(note.isSelected)
			{
				selectedNotes.add(note);
			}
		}
		
		return selectedNotes;
	}
}
