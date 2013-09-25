package com.ai.dialog.builder.activitylifecycle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

/**
 * Dialog with Activity Life Cycle.
 * This dialog will 
 * 
 * @author Isaiah Cheung
 * 
 */
public class DialogWithActivityLifeCycle extends Dialog {

	public DialogWithActivityLifeCycle(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public DialogWithActivityLifeCycle(Context context, int theme) {
		super(context, theme);
		if (context instanceof Activity) {
			setOwnerActivity((Activity) context);
		}
	}

	public DialogWithActivityLifeCycle(Context context) {
		super(context);
		if (context instanceof Activity) {
			setOwnerActivity((Activity) context);
		}
	}

	@Override
	public void show() {
		super.show();
		Activity owner = getOwnerActivity();
		if (owner instanceof DoodleDialogActivity) {
			DoodleDialogActivity dda = (DoodleDialogActivity) owner;
			dda.startManageDialog(this);
		}
	}

	@Override
	public void dismiss() {
		super.dismiss();
		Activity owner = getOwnerActivity();
		if (owner instanceof DoodleDialogActivity) {
			DoodleDialogActivity dda = (DoodleDialogActivity) owner;
			dda.stopManageDialog(this);
		}
	}

}
