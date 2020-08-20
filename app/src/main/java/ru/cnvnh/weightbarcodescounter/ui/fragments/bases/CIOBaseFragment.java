package ru.cnvnh.weightbarcodescounter.ui.fragments.bases;

import androidx.fragment.app.Fragment;

import ru.cnvnh.weightbarcodescounter.ui.activities.bases.CIOBaseActivity;

public abstract class CIOBaseFragment extends Fragment
{
	private static final String TAG = "CIOBaseFragment";
	
	/* ****************************************************************************************** *
	 * * LIFECYCLE																				* *
	 * ****************************************************************************************** */
	
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
	
	protected void showKeyboard()
	{
		((CIOBaseActivity) requireActivity()).showKeyboard();
	}
	
	protected void hideKeyboard()
	{
		((CIOBaseActivity) requireActivity()).hideKeyboard(getView());
	}
	
	protected void showLoadingDialog()
	{
		((CIOBaseActivity) requireActivity()).showLoadingDialog();
	}
	
	protected void dismissLoadingDialog()
	{
		((CIOBaseActivity) requireActivity()).dismissLoadingDialog();
	}
}
