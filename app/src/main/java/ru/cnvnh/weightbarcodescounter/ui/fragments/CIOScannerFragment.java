package ru.cnvnh.weightbarcodescounter.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import ru.cnvnh.weightbarcodescounter.R;
import ru.cnvnh.weightbarcodescounter.database.models.CIOBarcode;
import ru.cnvnh.weightbarcodescounter.databinding.CioFragmentScannerBinding;
import ru.cnvnh.weightbarcodescounter.ui.fragments.bases.CIOBaseFragment;
import ru.cnvnh.weightbarcodescounter.ui.view_models.CIOBarcodesViewModel;
import ru.cnvnh.weightbarcodescounter.ui.view_models.CIONoteViewModel;

public class CIOScannerFragment extends CIOBaseFragment implements View.OnClickListener, ZXingScannerView.ResultHandler
{
	private static final String TAG = "CIOScannerFragment";
	
	private CioFragmentScannerBinding mBinding;
	
	private ZXingScannerView mScannerView;
	
	private boolean inScanMode;
	
	private CIONoteViewModel mNoteViewModel;
	private CIOBarcodesViewModel mBarcodesViewModel;
	
	/* ****************************************************************************************** *
	 * * LIFECYCLE																				* *
	 * ****************************************************************************************** */
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		mBinding = CioFragmentScannerBinding.inflate(inflater, container, false);
		
		return mBinding.getRoot();
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		mScannerView = new ZXingScannerView(requireContext());
		mScannerView.setAspectTolerance(0.5f);
		
		mBinding.cioScannerViewHost.addView(mScannerView);
		
		mBinding.cioBarcodeEditText.addTextChangedListener(mBarcodeWatcher);
		
		mBinding.cioToggleFlashButton.setOnClickListener(this);
		mBinding.cioToggleInputButton.setOnClickListener(this);
		mBinding.cioAddButton.setOnClickListener(this);
		
		inScanMode = true;
		
		mNoteViewModel = new ViewModelProvider(requireActivity()).get(CIONoteViewModel.class);
		mBarcodesViewModel = new ViewModelProvider(requireActivity()).get(CIOBarcodesViewModel.class);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		mScannerView.setResultHandler(this);
		mScannerView.startCamera();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		mScannerView.stopCamera();
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
			case R.id.cio_toggle_flash_button:
				toggleFlash();
				break;
			case R.id.cio_toggle_input_button:
				mBinding.cioBarcodeEditText.getText().clear();
				mBinding.cioBarcodeEditText.requestFocus();
				showKeyboard();
				toggleInput();
				break;
			case R.id.cio_add_button:
				addBarcode();
				break;
		}
	}
	
	@Override
	public void handleResult(Result rawResult)
	{
		mBinding.cioBarcodeEditText.setText(rawResult.getText());
		
		toggleInput();
	}
	
	/* ****************************************************************************************** *
	 * * TEXT WATCHERS																			* *
	 * ****************************************************************************************** */
	
	private final TextWatcher mBarcodeWatcher = new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
		{
		
		}
		
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
		{
		
		}
		
		@Override
		public void afterTextChanged(Editable s)
		{
			int l = s.length();
			
			mBinding.cioCodeEditText.setText(s.subSequence(0, Math.min(l, 8)));
			mBinding.cioWeightEditText.setText(l > 8 ? s.subSequence(8, Math.min(l, 13)) : "");
			mBinding.cioAddButton.setVisibility(l == 13 ? View.VISIBLE : View.INVISIBLE);
		}
	};
	
	/* ****************************************************************************************** *
	 * * OBSERVERS																				* *
	 * ****************************************************************************************** */
	
	/* ****************************************************************************************** *
	 * * LISTENERS																				* *
	 * ****************************************************************************************** */
	
	/* ****************************************************************************************** *
	 * * FUNCTIONS																				* *
	 * ****************************************************************************************** */
	
	private void toggleFlash()
	{
		mScannerView.toggleFlash();
		mBinding.cioToggleFlashButton.setImageResource(mScannerView.getFlash() ? R.drawable.cio_ic_flash_off : R.drawable.cio_ic_flash_on);
	}
	
	private void toggleInput()
	{
		if(inScanMode)
		{
			mBinding.cioToggleFlashButton.setVisibility(View.INVISIBLE);
			mBinding.cioToggleInputButton.setImageResource(R.drawable.cio_ic_camera);
			
			mBinding.cioBarcodeLayout.setVisibility(View.VISIBLE);
			
			mScannerView.setFlash(false);
			mScannerView.stopCameraPreview();
		}
		else
		{
			mBinding.cioToggleFlashButton.setVisibility(View.VISIBLE);
			mBinding.cioToggleFlashButton.setImageResource(R.drawable.cio_ic_flash_on);
			mBinding.cioToggleInputButton.setImageResource(R.drawable.cio_ic_keyboard);
			mBinding.cioAddButton.setVisibility(View.INVISIBLE);
			
			mBinding.cioBarcodeLayout.setVisibility(View.INVISIBLE);
			
			mScannerView.resumeCameraPreview(this);
			
			hideKeyboard();
		}
		
		inScanMode = !inScanMode;
	}
	
	private void addBarcode()
	{
		if(!mNoteViewModel.getIsInDatabase())
		{
			mNoteViewModel.saveNote(requireContext());
		}
		
		CIOBarcode barcode = new CIOBarcode();
		barcode.timestamp = mNoteViewModel.getNote().timestamp;
		barcode.code = mBinding.cioCodeEditText.getText().toString();
		barcode.weight = Integer.parseInt(mBinding.cioWeightEditText.getText().toString());
		
		mBarcodesViewModel.saveBarcode(requireContext(), barcode);
		
		toggleInput();
	}
}
