package com.henningta.cryptoquips.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.henningta.cryptoquips.R;

public class AboutDialog extends DialogFragment {

	public static final String TAG = AboutDialog.class.getSimpleName();

	private Context _context;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		_context = activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		builder.setTitle("About");
		builder.setNegativeButton("Close", null);
		AlertDialog dialog = builder.create();
		dialog.setView(createDialogView(dialog.getLayoutInflater()));
		return dialog;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		_context = null;
	}

	private View createDialogView(LayoutInflater inflater) {
		return inflater.inflate(R.layout.fragment_about, null);
	}

}
