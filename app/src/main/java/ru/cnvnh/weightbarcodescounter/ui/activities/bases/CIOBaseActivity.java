package ru.cnvnh.weightbarcodescounter.ui.activities.bases;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.cnvnh.weightbarcodescounter.ui.dialogs.CIOLoadingDialog;

public abstract class CIOBaseActivity extends AppCompatActivity
{
	private static final String TAG = "CIOBaseActivity";
	
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
	
	public void showKeyboard()
	{
		View focusedView = getCurrentFocus();
		
		if(focusedView != null)
		{
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			
			if(imm != null)
			{
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}
	
	public void hideKeyboard(View view)
	{
		if(view != null)
		{
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			
			if(imm != null)
			{
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
		}
	}
	
	public void showLoadingDialog()
	{
		dismissLoadingDialog();
		
		Fragment fragment = getSupportFragmentManager().findFragmentByTag("CIOLoadingDialog");
		
		if(fragment == null)
		{
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			CIOLoadingDialog dialog = CIOLoadingDialog.newInstance();
			dialog.show(ft, "CIOLoadingDialog");
			
			fm.executePendingTransactions();
		}
	}
	
	public void dismissLoadingDialog()
	{
		Fragment fragment = getSupportFragmentManager().findFragmentByTag("CIOLoadingDialog");
		
		if(fragment != null)
		{
			CIOLoadingDialog dialog = (CIOLoadingDialog) fragment;
			dialog.dismiss();
		}
	}
}
