package ru.cnvnh.weightbarcodescounter.database.repos;

import android.content.Context;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

import ru.cnvnh.weightbarcodescounter.database.CIODatabase;
import ru.cnvnh.weightbarcodescounter.database.models.CIONote;
import ru.cnvnh.weightbarcodescounter.database.repos.callbacks.CIONoteRepoCallback;

public class CIONoteRepo
{
	private static final String TAG = "CIONoteRepo";
	
	/* ****************************************************************************************** *
	 * * INSTANCE																				* *
	 * ****************************************************************************************** */
	
	private static CIONoteRepo instance;
	
	public static CIONoteRepo getInstance()
	{
		if(instance == null)
		{
			instance = new CIONoteRepo();
		}
		
		return instance;
	}
	
	/* ****************************************************************************************** *
	 * * FUNCTIONS																				* *
	 * ****************************************************************************************** */
	
	public LiveData<List<CIONote>> loadAll(final Context context)
	{
		return CIODatabase.getInstance(context).notes().getAll();
	}
	
	public void saveNote(final Context context, final CIONote note)
	{
		Executors.newSingleThreadExecutor().execute(new Runnable()
		{
			@Override
			public void run()
			{
				CIODatabase.getInstance(context).notes().insert(note);
			}
		});
	}
	
	public void updateNote(final Context context, final CIONote note)
	{
		Executors.newSingleThreadExecutor().execute(new Runnable()
		{
			@Override
			public void run()
			{
				CIODatabase.getInstance(context).notes().update(note);
			}
		});
	}
	
	public void deleteNotes(final Context context, final List<CIONote> notes, final CIONoteRepoCallback noteCallbackListener)
	{
		Executors.newSingleThreadExecutor().execute(new Runnable()
		{
			@Override
			public void run()
			{
				int count = CIODatabase.getInstance(context).notes().delete(notes);
				
				Looper.prepare();
				
				noteCallbackListener.onNotesDeleted(count);
				
				Looper.loop();
			}
		});
	}
}
