package ru.cnvnh.weightbarcodescounter.ui.view_models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.cnvnh.weightbarcodescounter.database.models.CIONote;
import ru.cnvnh.weightbarcodescounter.database.repos.CIONoteRepo;
import ru.cnvnh.weightbarcodescounter.database.repos.callbacks.CIONoteRepoCallback;

public class CIONotesViewModel extends ViewModel
{
	private static final String TAG = "CIONotesViewModel";
	
	private LiveData<List<CIONote>> mNotes;
	
	public LiveData<List<CIONote>> getNotes()
	{
		return mNotes;
	}
	
	public void loadAllNotes(final Context context)
	{
		mNotes = CIONoteRepo.getInstance().loadAll(context);
	}
	
	public void deleteNotes(final Context context, final List<CIONote> notes, final CIONoteRepoCallback noteRepoCallbackListener)
	{
		CIONoteRepo.getInstance().deleteNotes(context, notes, noteRepoCallbackListener);
	}
}
