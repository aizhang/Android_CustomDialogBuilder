package com.ai.dialog.builder.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ai.dialog.builder.DialogBuilder;
import com.doodle.dialog.R;

/**
 * A reference implementation of DialogBuilder. Further more, just extends the
 * DialogBuilder and give your own implementation.
 * 
 * @author Isaiah Cheung
 * 
 */
public class MsgDialogBuilder extends DialogBuilder {

	String msg;
	boolean mCancelHided = false;

	public MsgDialogBuilder(Context context) {
		super(context);
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setMsg(int resId) {
		this.msg = mContext.getString(resId);
	}

	public void hideCancelButton() {
		mCancelHided = true;
	}

	public void showCancelButton() {
		mCancelHided = false;
	}

	public Dialog build() {
		mBuilder.setTitle(title);
		TextView contentView = (TextView) mInflater.inflate(
				R.layout.dialog_msg_content, null);
		contentView.setText(msg);
		// mBuilder.setMessage(msg);
		mBuilder.setContentView(contentView);
		setLeftButton(mContext.getString(R.string.dialog_btn_cancel),
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mDialog.dismiss();

						if (null != mOnResult) {
							mOnResult.onCancel();
						}
					}
				});
		setRightButton(mContext.getString(R.string.dialog_btn_ok),
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mDialog.dismiss();
						if (null != mOnResult) {
							mOnResult.onOk();
						}
					}
				});
		if (!mCancelHided) {
			mBuilder.addButton(mLeftButtonText, mLeftButtonClickListener);
		}
		mBuilder.addButton(mRightButtonText, mRightButtonClickListener);

		mDialog = mBuilder.create();

		return mDialog;
	}

}
