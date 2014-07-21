package com.ai.dialog.builder;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ai.dialog.R;

/**
 * The Base Dialog Builder.
 * 
 * @author leo
 * 
 */
public abstract class DialogBuilder {
	protected Context mContext;
	protected String title;
	protected String mLeftButtonText, mRightButtonText;
	protected LayoutInflater mInflater;

	protected View.OnClickListener mLeftButtonClickListener,
			mRightButtonClickListener;

	protected Dialog mDialog;
	protected BaseDialog.Builder mBuilder;
	protected OnResult mOnResult;

	public DialogBuilder(Context context) {
		this.mContext = context;
		mLeftButtonText = mContext.getString(R.string.dialog_btn_cancel);
		mRightButtonText = mContext.getString(R.string.dialog_btn_ok);
		mBuilder = new BaseDialog.Builder(mContext);
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(int resId) {
		this.title = mContext.getString(resId);
	}

	public void setOnResult(OnResult onResult) {
		mOnResult = onResult;
	}

	public void setLeftButton(String buttonText,
			View.OnClickListener onClickListener) {
		mLeftButtonText = buttonText;
		mLeftButtonClickListener = onClickListener;
	}

	public void setRightButton(String buttonText,
			View.OnClickListener onClickListener) {
		mRightButtonText = buttonText;
		mRightButtonClickListener = onClickListener;
	}

	public abstract Dialog build();

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			ViewGroup.LayoutParams params = listItem.getLayoutParams();
			if (null == params || params.height <= 0) {
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();

			} else {
				totalHeight += params.height;
			}
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);

	}

	public interface OnResult {
		public void onOk();

		public void onCancel();
	}

	public Context getContext() {
		return mContext;
	}

}
