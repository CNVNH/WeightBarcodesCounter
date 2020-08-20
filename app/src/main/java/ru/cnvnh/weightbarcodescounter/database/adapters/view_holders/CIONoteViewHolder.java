package ru.cnvnh.weightbarcodescounter.database.adapters.view_holders;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

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
	
	private CIONoteItemCallback MNoteItemCallbackListener;
	
	public CIONoteViewHolder(CioItemNoteBinding binding, CIONoteItemCallback listener)
	{
		super(binding.getRoot());
		
		mBinding = binding;
		mBinding.getRoot().setOnClickListener(this);
		mBinding.getRoot().setOnLongClickListener(this);
		
		MNoteItemCallbackListener = listener;
	}
	
	public void bindTo(CIONote note)
	{
		mBinding.getRoot().setBackgroundResource(note.isSelected ? R.drawable.cio_background_round_selected : R.drawable.cio_background_round);
		
		mBinding.cioNameText.setText(note.name != null && !note.name.isEmpty() ? note.name : "No name");
		mBinding.cioDateText.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date(note.timestamp)));
		mBinding.cioTimeText.setText(new SimpleDateFormat("HH:mm", Locale.US).format(new Date(note.timestamp)));
		
		int textColor = mBinding.getRoot().getContext().getResources().getColor(note.isSelected ? R.color.cio_text_reversed : R.color.cio_text, null);
		
		mBinding.cioNameText.setTextColor(textColor);
		mBinding.cioDateText.setTextColor(textColor);
		mBinding.cioTimeText.setTextColor(textColor);
	}
	
	@Override
	public void onClick(View view)
	{
		MNoteItemCallbackListener.onNoteClick(getAdapterPosition());
	}
	
	@Override
	public boolean onLongClick(View view)
	{
		MNoteItemCallbackListener.onNoteLongClick(getAdapterPosition());
		
		return true;
	}
}
