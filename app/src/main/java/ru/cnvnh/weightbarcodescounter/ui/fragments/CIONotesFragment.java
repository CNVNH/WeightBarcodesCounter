package ru.cnvnh.weightbarcodescounter.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.adapters.CIONotesAdapter;
import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIONoteItemCallback;
import ru.cnvnh.weightbarcodescounter.database.adapters.decorations.CIOMarginDecoration;
import ru.cnvnh.weightbarcodescounter.database.models.CIONote;
import ru.cnvnh.weightbarcodescounter.database.repos.callbacks.CIONoteRepoCallback;
import ru.cnvnh.weightbarcodescounter.databinding.CioFragmentNotesBinding;
import ru.cnvnh.weightbarcodescounter.ui.fragments.bases.CIOBaseFragment;
import ru.cnvnh.weightbarcodescounter.ui.view_models.CIONoteViewModel;
import ru.cnvnh.weightbarcodescounter.ui.view_models.CIONotesViewModel;

public class CIONotesFragment extends CIOBaseFragment implements View.OnClickListener, CIONoteItemCallback, CIONoteRepoCallback
{
	private static final String TAG = "CIONotesFragment";
	
	private CioFragmentNotesBinding mBinding;
	
	private CIONotesAdapter mNotesAdapter;
	
	private CIONotesViewModel mNotesViewModel;
	private CIONoteViewModel mNoteViewModelV2;
	
	private boolean mInSelectionMode;
	
	/* ****************************************************************************************** *
	 * * LIFECYCLE																				* *
	 * ****************************************************************************************** */
	
	@Override
	public void onAttach(@NonNull Context context)
	{
		super.onAttach(context);
		
		requireActivity().getOnBackPressedDispatcher().addCallback(this, mOnBackPressedCallback);
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		mBinding = CioFragmentNotesBinding.inflate(inflater, container, false);
		
		return mBinding.getRoot();
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		mNotesAdapter = new CIONotesAdapter(this);
		
		mBinding.cioNotes.setHasFixedSize(true);
		mBinding.cioNotes.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
		mBinding.cioNotes.addItemDecoration(new CIOMarginDecoration((int) getResources().getDimension(R.dimen.cio_margin_decoration_height)));
		mBinding.cioNotes.setAdapter(mNotesAdapter);
		
		mBinding.cioAddButton.setOnClickListener(this);
		mBinding.cioDeleteButton.setOnClickListener(this);
		
		mNotesViewModel = new ViewModelProvider(requireActivity()).get(CIONotesViewModel.class);
		mNotesViewModel.loadAllNotes(requireContext());
		mNotesViewModel.getNotes().observe(getViewLifecycleOwner(), mNotesObserver);
		
		mNoteViewModelV2 = new ViewModelProvider(requireActivity()).get(CIONoteViewModel.class);
		
		mInSelectionMode = false;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		mBinding = null;
	}
	
	/* ****************************************************************************************** *
	 * * CALLBACKS																				* *
	 * ****************************************************************************************** */
	
	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
			case R.id.cio_add_button:
				mNoteViewModelV2.createNewNote();
				NavHostFragment.findNavController(this).navigate(R.id.cio_action_notes_to_note_v2);
				break;
			case R.id.cio_delete_button:
				deleteSelectedNotes();
				break;
		}
	}
	
	private OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false)
	{
		@Override
		public void handleOnBackPressed()
		{
			mNotesAdapter.clearSelection();
			toggleSelectionMode();
		}
	};
	
	@Override
	public void onNoteClick(int pos)
	{
		if(mInSelectionMode)
		{
			mNotesAdapter.toggleNoteSelection(pos);
		}
		else
		{
			showLoadingDialog();
			
			mNoteViewModelV2.setNote(mNotesAdapter.getNote(pos));
			NavHostFragment.findNavController(this).navigate(R.id.cio_action_notes_to_note_v2);
		}
	}
	
	@Override
	public void onNoteLongClick(int pos)
	{
		if(!mInSelectionMode)
		{
			toggleSelectionMode();
			
			mNotesAdapter.toggleNoteSelection(pos);
		}
	}
	
	/* ****************************************************************************************** *
	 * * TEXT WATCHERS																			* *
	 * ****************************************************************************************** */
	
	/* ****************************************************************************************** *
	 * * OBSERVERS																				* *
	 * ****************************************************************************************** */
	
	private Observer<List<CIONote>> mNotesObserver = new Observer<List<CIONote>>()
	{
		@Override
		public void onChanged(List<CIONote> notes)
		{
			mNotesAdapter.setData(notes);
			
			dismissLoadingDialog();
		}
	};
	
	/* ****************************************************************************************** *
	 * * LISTENERS																				* *
	 * ****************************************************************************************** */
	
	@Override
	public void onNotesDeleted(int deletedNotesCount)
	{
		dismissLoadingDialog();
		
		Toast.makeText(requireContext(), deletedNotesCount + " notes deleted", Toast.LENGTH_SHORT).show();
	}
	
	/* ****************************************************************************************** *
	 * * FUNCTIONS																				* *
	 * ****************************************************************************************** */
	
	private void deleteSelectedNotes()
	{
		List<CIONote> selectedNotes = mNotesAdapter.getSelectedNotes();
		
		if(selectedNotes.size() > 0)
		{
			showLoadingDialog();
			
			mNotesViewModel.deleteNotes(requireContext(), selectedNotes, this);
		}
		
		toggleSelectionMode();
	}
	
	private void toggleSelectionMode()
	{
		mInSelectionMode = !mInSelectionMode;
		
		mOnBackPressedCallback.setEnabled(mInSelectionMode);
		
		mBinding.cioDeleteButton.setVisibility(mInSelectionMode ? View.VISIBLE : View.INVISIBLE);
		mBinding.cioAddButton.setVisibility(mInSelectionMode ? View.INVISIBLE : View.VISIBLE);
	}
}
