package ru.cnvnh.weightbarcodescounter.ui.view_models;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import ru.cnvnh.weightbarcodescounter.database.models.CIONote;
import ru.cnvnh.weightbarcodescounter.database.repos.CIONoteRepo;

public class CIONoteViewModel extends ViewModel
{
	private static final String TAG = "CIONoteViewModelV2";
	
	private CIONote mNote;
	
	private boolean mIsInDatabase;
	
	private boolean mIsModified;
	
	public CIONoteViewModel()
	{
		mIsInDatabase = false;
		
		mIsModified = false;
	}
	
	public void createNewNote()
	{
		mNote = new CIONote();
		
		mIsInDatabase = false;
	}
	
	public void setNote(CIONote note)
	{
		mNote = note;
		
		mIsInDatabase = true;
	}
	
	public CIONote getNote()
	{
		return mNote;
	}
	
	public void saveNote(final Context context)
	{
		CIONoteRepo.getInstance().saveNote(context, mNote);
		
		mIsInDatabase = true;
	}
	
	public void updateNote(final Context context)
	{
		CIONoteRepo.getInstance().updateNote(context, mNote);
	}
	
	public boolean getIsInDatabase()
	{
		return mIsInDatabase;
	}
	
	public void setIsModified(boolean isModified)
	{
		mIsModified = isModified;
	}
	
	public boolean getIsModified()
	{
		return mIsModified;
	}
}
