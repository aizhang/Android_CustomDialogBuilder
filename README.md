Android_CustomDialogBuilder
===========================
Android_CustomDialogBuilder is a open-source project for custom android dialog.

You could use Android_CustomDialogBuilder to custom dialogs more effectively.

Usage:

       DialogBuilder customBuilder = new DialogBuilder(this) {

				@Override
				public Dialog build() {
					mBuilder.setTitle(R.string.app_name);
					
					//mBuilder.setMessage(R.string.show_dialog_custom_btn);
					mBuilder.setContentView(mInflater.inflate(
							R.layout.custom_content, null));

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

			Dialog dialog = customBuilder.build();
			dialog.show();
	
Go to the smaple code(MainActivity.java) and the custom builder smaple(MsgBuilder.java) for more details.
