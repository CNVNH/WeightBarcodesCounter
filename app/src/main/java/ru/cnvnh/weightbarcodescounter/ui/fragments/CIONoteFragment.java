package ru.cnvnh.weightbarcodescounter.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.adapters.CIOBarcodeAdapter;
import ru.cnvnh.weightbarcodescounter.database.adapters.callbacks.CIOBarcodeItemCallback;
import ru.cnvnh.weightbarcodescounter.database.adapters.decorations.CIOMarginDecoration;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.databinding.CioFragmentNoteBinding;
import ru.cnvnh.weightbarcodescounter.ui.fragments.bases.CIOBaseFragment;
import ru.cnvnh.weightbarcodescounter.ui.view_models.CIOBarcodesViewModel;
import ru.cnvnh.weightbarcodescounter.ui.view_models.CIONoteViewModel;

public class CIONoteFragment extends CIOBaseFragment implements View.OnClickListener, CIOBarcodeItemCallback
{
	private static final String TAG = "CIONoteFragmentV2";
	
	private static final int CIO_REQUEST_PERMISSION_CAMERA = 27090;
	
	private CioFragmentNoteBinding mBinding;
	
	private CIOBarcodeAdapter mBarcodesAdapter;
	
	private CIONoteViewModel mNoteViewModel;
	private CIOBarcodesViewModel mBarcodesViewModel;
	
	/* ****************************************************************************************** *
	 * * LIFECYCLE																				* *
	 * ****************************************************************************************** */
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		mBinding = CioFragmentNoteBinding.inflate(inflater, container, false);
		
		return mBinding.getRoot();
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		mBarcodesAdapter = new CIOBarcodeAdapter(this);
		
		mBinding.cioBarcodes.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
//		mBinding.cioBarcodes.addItemDecoration(new CIOMarginDecoration((int) getResources().getDimension(R.dimen.cio_margin_decoration_height)));
		mBinding.cioBarcodes.setAdapter(mBarcodesAdapter);
		
		mBinding.cioScanButton.setOnClickListener(this);
		mBinding.cioDeleteButton.setOnClickListener(this);
		
		mNoteViewModel = new ViewModelProvider(requireActivity()).get(CIONoteViewModel.class);
		
		mBinding.cioNoteNameEditText.setText(mNoteViewModel.getNote().name);
		mBinding.cioNoteNameEditText.addTextChangedListener(mNoteNameTextWatcher);
		
		mBarcodesViewModel = new ViewModelProvider(requireActivity()).get(CIOBarcodesViewModel.class);
		mBarcodesViewModel.loadByTimestamp(requireContext(), mNoteViewModel.getNote().timestamp);
		mBarcodesViewModel.getBarcodes().observe(getViewLifecycleOwner(), mBarcodesObserver);
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		if(mNoteViewModel.getIsInDatabase() && mNoteViewModel.getIsModified())
		{
			mNoteViewModel.getNote().name = mBinding.cioNoteNameEditText.getText().toString();
			mNoteViewModel.updateNote(requireContext());
		}
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
			case R.id.cio_scan_button:
				if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
				{
					ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, CIO_REQUEST_PERMISSION_CAMERA);
					break;
				}
				NavHostFragment.findNavController(this).navigate(R.id.cio_action_note_to_scanner);
				break;
			case R.id.cio_delete_button:
				Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show();
				break;
		}
	}
	
	@Override
	public void onBarcodeClick(int pos)
	{
		mBarcodesAdapter.onBarcodeClick(pos);
	}
	
	@Override
	public void onBarcodeParentClick(int pos)
	{
		mBarcodesAdapter.onBarcodeParentClick(pos);
	}
	
	@Override
	public void onBarcodeLongClick(int pos)
	{
		mBarcodesAdapter.onBarcodeLongClick(pos);
	}
	
	@Override
	public void onBarcodeParentLongClick(int pos)
	{
		mBarcodesAdapter.onBarcodeParentLongClick(pos);
	}
	
	/* ****************************************************************************************** *
	 * * TEXT WATCHERS																			* *
	 * ****************************************************************************************** */
	
	private TextWatcher mNoteNameTextWatcher = new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
		{
		
		}
		
		@Override
		public void onTextChanged(CharSequence s, int i, int i1, int i2)
		{
		
		}
		
		@Override
		public void afterTextChanged(Editable s)
		{
			mNoteViewModel.setIsModified(!s.toString().equals(mNoteViewModel.getNote().name));
		}
	};
	
	/* ****************************************************************************************** *
	 * * OBSERVERS																				* *
	 * ****************************************************************************************** */
	
	private Observer<List<CIOBarcode>> mBarcodesObserver = new Observer<List<CIOBarcode>>()
	{
		@Override
		public void onChanged(List<CIOBarcode> barcodes)
		{
			mBarcodesAdapter.setData(barcodes);
			
			dismissLoadingDialog();
		}
	};
	
	/* ****************************************************************************************** *
	 * * LISTENERS																				* *
	 * ****************************************************************************************** */
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if(requestCode == CIO_REQUEST_PERMISSION_CAMERA  && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			NavHostFragment.findNavController(this).navigate(R.id.cio_action_note_to_scanner);
		}
	}
	
	/* ****************************************************************************************** *
	 * * FUNCTIONS																				* *
	 * ****************************************************************************************** */
}
