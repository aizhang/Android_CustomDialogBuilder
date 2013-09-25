package com.ai.dialog.builder.activitylifecycle;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DoodleDialogActivity extends FragmentActivity {

    private List<Dialog> mDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	mDialogs = new ArrayList<Dialog>();
    }

    public void startManageDialog(Dialog dialog) {
	mDialogs.add(dialog);
    }

    public void stopManageDialog(Dialog dialog) {
	mDialogs.remove(dialog);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	dismissDialogs();
    }

    @Override
    protected void onDestroy() {
	dismissDialogs();
	super.onDestroy();
	
    }
    
    
    protected void dismissDialogs(){
	while (mDialogs.size() > 0) {
	    Dialog dialog = mDialogs.get(0);
	    if (null != dialog && dialog.isShowing()) {
		dialog.dismiss();
		mDialogs.remove(dialog);
	    }
	}
    }

}
