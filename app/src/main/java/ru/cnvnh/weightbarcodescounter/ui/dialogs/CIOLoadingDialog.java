package ru.cnvnh.weightbarcodescounter.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.cnvnh.weightbarcodescounter.databinding.CioDialogLoadingBinding;
import ru.cnvnh.weightbarcodescounter.ui.dialogs.bases.CIOBaseDialog;

public class CIOLoadingDialog extends CIOBaseDialog
{
	private static final String TAG = "CIOLoadingDialog";
	
	private CioDialogLoadingBinding mBinding;
	
	/* ****************************************************************************************** *
	 * * INSTANCE																				* *
	 * ****************************************************************************************** */
	
	public static CIOLoadingDialog newInstance()
	{
		return new CIOLoadingDialog();
	}
	
	/* ****************************************************************************************** *
	 * * LIFECYCLE																				* *
	 * ****************************************************************************************** */
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setRetainInstance(false);
		setCancelable(false);
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		mBinding = CioDialogLoadingBinding.inflate(inflater, container, false);
		
		return mBinding.getRoot();
	}
	
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		return dialog;
	}
	
	@Override
	public void onDestroyView()
	{
		if(getDialog() != null && getRetainInstance())
		{
			getDialog().setDismissMessage(null);
		}
		
		super.onDestroyView();
	}
	
	/* ****************************************************************************************** *
	 * * CALLBACKS																				* *
	 * ****************************************************************************************** */
	
	/* ****************************************************************************************** *
	 * * TEXT WATCHERS																			* *
	 * ****************************************************************************************** */
	
	/* ****************************************************************************************** *
	 * * OBSERVERS																				* *
	 * ****************************************************************************************** */
	
	/* ****************************************************************************************** *
	 * * LISTENERS																				* *
	 * ****************************************************************************************** */
	
	/* ****************************************************************************************** *
	 * * FUNCTIONS																				* *
	 * ****************************************************************************************** */
}
