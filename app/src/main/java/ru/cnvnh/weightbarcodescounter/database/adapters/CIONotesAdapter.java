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
	
	private CIONoteItemCallback mNoteItemCallbackListener;
	
	public CIONotesAdapter(CIONoteItemCallback noteItemCallbackListener)
	{
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
		holder.bindTo(mNotes.get(pos));
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
	
	public void toggleNoteSelection(int pos)
	{
		mNotes.get(pos).isSelected = !mNotes.get(pos).isSelected;
		
		notifyItemChanged(pos);
	}
	
	public void clearSelection()
	{
		for(int i = 0; i < mNotes.size(); i++)
		{
			mNotes.get(i).isSelected = false;
			
			notifyItemChanged(i);
		}
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
