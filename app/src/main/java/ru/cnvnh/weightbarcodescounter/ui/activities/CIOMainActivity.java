package ru.cnvnh.weightbarcodescounter.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import ru.cnvnh.weightbarcodescounter.databinding.CioActivityMainBinding;
import ru.cnvnh.weightbarcodescounter.ui.activities.bases.CIOBaseActivity;

public class CIOMainActivity extends CIOBaseActivity
{
	private static final String TAG = "CIOMainActivity";
	
	private CioActivityMainBinding mBinding;
	
	/* ****************************************************************************************** *
	 * * LIFECYCLE																				* *
	 * ****************************************************************************************** */
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mBinding = CioActivityMainBinding.inflate(getLayoutInflater());
		setContentView(mBinding.getRoot());
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
