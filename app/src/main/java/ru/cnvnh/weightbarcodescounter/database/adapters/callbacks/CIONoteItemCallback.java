package ru.cnvnh.weightbarcodescounter.database.adapters.callbacks;

public interface CIONoteItemCallback
{
	void onNoteClick(int pos);
	void onNoteLongClick(int pos);
	void onCheckboxClick(int pos, boolean checked);
}
