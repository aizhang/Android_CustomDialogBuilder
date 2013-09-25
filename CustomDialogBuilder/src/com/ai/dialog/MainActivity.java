package com.ai.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ai.dialog.builder.DialogBuilder;
import com.ai.dialog.builder.widget.MsgDialogBuilder;
import com.doodle.dialog.R;

/**
 * A sample for the custom dialog builder.
 * 
 * @author Isaiah Cheung
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private static final String[] texts = { "Text 1", "Text 2", "Text 3",
			"Text 4", "Text 5", "Text 6", "Text 7", "Text 8", "Text 9",
			"Text 10", "Text 11", "Text 12", "Text 13", "Text 14" };

	ViewHolder viewHolder;
	Dialog dialog;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewHolder = new ViewHolder();
		viewHolder.msgBtn = (Button) findViewById(R.id.dialog_general_message);
		viewHolder.noTitleBtn = (Button) findViewById(R.id.dialog_without_title);
		viewHolder.customContentBtn = (Button) findViewById(R.id.dialog_custom_content);
		viewHolder.listviewContentBtn = (Button) findViewById(R.id.dialog_listview_content);
		viewHolder.customBtnsBtn = (Button) findViewById(R.id.dialog_custom_buttons);
		viewHolder.oneBtnBtn = (Button) findViewById(R.id.dialog_one_button);
		viewHolder.noBtnBtn = (Button) findViewById(R.id.dialog_no_btn);

		viewHolder.msgBtn.setOnClickListener(this);
		viewHolder.noTitleBtn.setOnClickListener(this);
		viewHolder.customContentBtn.setOnClickListener(this);
		viewHolder.listviewContentBtn.setOnClickListener(this);
		viewHolder.customBtnsBtn.setOnClickListener(this);
		viewHolder.oneBtnBtn.setOnClickListener(this);
		viewHolder.noBtnBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_general_message:
			MsgDialogBuilder msgBuilder = (MsgDialogBuilder) new MsgDialogBuilder(
					this);
			msgBuilder.setTitle(R.string.app_name);
			msgBuilder.setMsg(R.string.show_dialog_general_message);
			dialog = msgBuilder.build();
			dialog.show();
			break;
		case R.id.dialog_without_title:
			MsgDialogBuilder noTitleBuilder = (MsgDialogBuilder) new MsgDialogBuilder(
					this);
			noTitleBuilder.setTitle(null);
			noTitleBuilder.setMsg(R.string.show_dialog_general_message);
			dialog = noTitleBuilder.build();
			dialog.show();

			break;
		case R.id.dialog_custom_content:
			DialogBuilder customContentBuilder = new DialogBuilder(this) {

				@Override
				public Dialog build() {
					mBuilder.setTitle(R.string.app_name);
					mBuilder.setContentView(mInflater.inflate(
							R.layout.custom_content, null));
					mBuilder.addButton(getString(R.string.dialog_btn_cancel),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					mBuilder.addButton(getString(R.string.dialog_btn_ok),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					return mBuilder.create();
				}
			};

			dialog = customContentBuilder.build();
			dialog.show();
			break;

		case R.id.dialog_listview_content:
			DialogBuilder listBuilder = new DialogBuilder(this) {

				@Override
				public Dialog build() {
					mBuilder.setTitle(R.string.app_name);
					View contentView = mInflater.inflate(
							R.layout.list_dialog_layout, null);
					ListView listView = (ListView) contentView
							.findViewById(R.id.list_dialog_listview);
					listView.setAdapter(new ArrayAdapter<String>(mContext,
							R.layout.dialog_msg_content, texts));
					setListViewHeightBasedOnChildren(listView);
					mBuilder.setContentView(contentView);

					mBuilder.addButton(getString(R.string.dialog_btn_cancel),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					mBuilder.addButton(getString(R.string.dialog_btn_ok),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					return mBuilder.create();
				}
			};
			dialog = listBuilder.build();
			dialog.show();
			break;
		case R.id.dialog_custom_buttons:
			DialogBuilder customButtons = new DialogBuilder(this) {

				@Override
				public Dialog build() {
					mBuilder.setTitle(R.string.app_name);
					mBuilder.setMessage(R.string.show_dialog_custom_btn);
					mBuilder.addButton(getString(R.string.dialog_btn_cancel),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					mBuilder.addButton(getString(R.string.action_settings),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					mBuilder.addButton(getString(R.string.dialog_btn_ok),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					return mBuilder.create();
				}
			};

			dialog = customButtons.build();
			dialog.show();
			break;
		case R.id.dialog_one_button:
			DialogBuilder customBtn = new DialogBuilder(this) {

				@Override
				public Dialog build() {
					mBuilder.setTitle(R.string.app_name);
					mBuilder.setMessage(R.string.show_dialog_one_btn);
					mBuilder.addButton(getString(R.string.dialog_btn_ok),
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
					return mBuilder.create();
				}
			};

			dialog = customBtn.build();
			dialog.show();
			break;
		case R.id.dialog_no_btn:
			DialogBuilder noBtn = new DialogBuilder(this) {

				@Override
				public Dialog build() {
					mBuilder.setTitle(R.string.app_name);
					mBuilder.setMessage(R.string.show_dialog_no_btn);
					return mBuilder.create();
				}
			};

			dialog = noBtn.build();
			dialog.show();
			break;
		default:
			break;
		}

	}

	static class ViewHolder {
		Button msgBtn;
		Button noTitleBtn;
		Button customContentBtn;
		Button listviewContentBtn;
		Button customBtnsBtn;
		Button oneBtnBtn;
		Button noBtnBtn;
	}

}
