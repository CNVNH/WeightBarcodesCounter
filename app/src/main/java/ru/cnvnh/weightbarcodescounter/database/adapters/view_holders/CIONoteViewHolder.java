package ru.cnvnh.weightbarcodescounter.database.adapters.view_holders;

import android.view.View;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIONoteItemCallback;
import ru.cnvnh.weightbarcodescounter.database.models.CIONote;
import ru.cnvnh.weightbarcodescounter.databinding.CioItemNoteBinding;

public class CIONoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
	private static final String TAG = "CIONoteViewHolder";
	
	private CioItemNoteBinding mBinding;
	
	private CIONoteItemCallback mNoteItemCallbackListener;
	
	public CIONoteViewHolder(CioItemNoteBinding binding, CIONoteItemCallback listener)
	{
		super(binding.getRoot());
		
		mBinding = binding;
		mBinding.getRoot().setOnClickListener(this);
		mBinding.getRoot().setOnLongClickListener(this);
		mBinding.cioCheckbox.setOnClickListener(this);
		
		mNoteItemCallbackListener = listener;
	}
	
	public void bindTo(CIONote note, boolean showCheckbox)
	{
		mBinding.cioNameText.setText(note.name != null && !note.name.isEmpty() ? note.name : "No name");
		mBinding.cioDateText.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date(note.timestamp)));
		mBinding.cioTimeText.setText(new SimpleDateFormat("HH:mm", Locale.US).format(new Date(note.timestamp)));
		
		mBinding.cioCheckbox.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);
		mBinding.cioCheckbox.setChecked(note.isSelected);
	}
	
	@Override
	public void onClick(View view)
	{
		int id = view.getId();
		
		if(id == R.id.cio_note_item_layout)
		{
			mNoteItemCallbackListener.onNoteClick(getAdapterPosition());
		}
		else
		{
			mNoteItemCallbackListener.onCheckboxClick(getAdapterPosition(), mBinding.cioCheckbox.isChecked());
		}
	}
	
	@Override
	public boolean onLongClick(View view)
	{
		mNoteItemCallbackListener.onNoteLongClick(getAdapterPosition());
		
		return true;
	}
}
