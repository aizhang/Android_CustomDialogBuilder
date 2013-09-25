package com.ai.dialog.builder;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.doodle.dialog.R;

/**
 * The dialog helper, contains a dialog builder. This helper support set title,
 * customized content view, and multi button. Always use the dialog helper for
 * all dialog.
 * 
 * @author Isaiah Cheung
 * */
public class BaseDialog extends Dialog {

	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}

	public BaseDialog(Context context) {
		super(context);
	}

	/** Helper class for creating a textcustom dialog */
	public static class Builder {

		private Context mContext;
		private int mTheme;

		private String mTitle;
		private CharSequence mMsg;
		private View mContentView;
		private View mTitleView;

		private List<Pair<String, View.OnClickListener>> mBtnList;

		private boolean mContentViewFillParentWithNoScroll = false;

		public Builder(Context activity) {
			this(activity, R.style.Dialog);
		}

		public Builder(Context context, int theme) {
			this.mContext = context;
			this.mTheme = theme;

			mBtnList = new ArrayList<Pair<String, View.OnClickListener>>();
		}

		/**
		 * Set the Dialog message from String
		 * 
		 * @param mTitle
		 * @return
		 */
		public Builder setMessage(CharSequence message) {
			this.mMsg = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param mTitle
		 * @return
		 */
		public Builder setMessage(int message) {
			this.mMsg = (String) mContext.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.mTitle = (String) mContext.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.mTitle = title;
			return this;
		}

		/**
		 * Set a custom title view for the dialog. this will override the title
		 * text which set by setTitle();
		 * 
		 * @param v
		 * @return
		 */
		public Builder setTitleView(View v) {
			this.mTitleView = v;
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.mContentView = v;
			return this;
		}


		/**
		 * @param buttonTextResId
		 *            The resource id
		 * @param listener
		 *            The button onclicklistener
		 * @return
		 */
		public Builder addButton(int buttonTextResId,
				View.OnClickListener listener) {
			return this
					.addButton(mContext.getString(buttonTextResId), listener);
		}

		/**
		 * @param button
		 *            Text The button text
		 * @param listener
		 *            The button onclicklistener;
		 * @return The specified button
		 */
		public Builder addButton(String buttonText,
				View.OnClickListener listener) {
			if (null != buttonText && null != listener) {
				this.mBtnList.add(new Pair<String, View.OnClickListener>(
						buttonText, listener));
			}
			return this;
		}

		public Builder setContentViewFillParent(boolean value) {
			this.mContentViewFillParentWithNoScroll = value;
			return this;
		}

		/** Create the custom dialog */
		public BaseDialog create() {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final BaseDialog dialog = new BaseDialog(mContext, mTheme);
			View layout = inflater.inflate(R.layout.dialog_base, null);

			TextView titleView = (TextView) layout
					.findViewById(R.id.dialog_base_title);
			LinearLayout titleLayout = (LinearLayout) layout
					.findViewById(R.id.dialog_base_title_layout);
			LinearLayout contentLayout = (LinearLayout) layout
					.findViewById(R.id.dialog_base_content_view);
			LinearLayout btnLayout = (LinearLayout) layout
					.findViewById(R.id.dialog_base_btn_layout);
			ScrollView contentScrollView = (ScrollView) layout
					.findViewById(R.id.dialog_base_content_scrollview);
			if (mContentViewFillParentWithNoScroll) {

				// if this, we need to remove scrollview.
				ViewGroup vg = (ViewGroup) layout;
				int index = vg.indexOfChild(contentScrollView);
				contentScrollView.removeAllViews();
				vg.removeView(contentScrollView);
				vg.addView(contentLayout, index);

				contentLayout.setLayoutParams(new TableLayout.LayoutParams(
						TableLayout.LayoutParams.MATCH_PARENT,
						TableLayout.LayoutParams.WRAP_CONTENT, 3));
				contentLayout.setBackgroundDrawable(contentScrollView
						.getBackground());
			}

			if (null == mContentView) {
				mContentView = new TextView(mContext);
				mContentView.setPadding(30, 30, 20, 30);
				((TextView) mContentView).setTextSize(16);
				((TextView) mContentView).setText(mMsg);
			}
			contentLayout.addView(mContentView);

			titleLayout.setGravity(Gravity.CENTER_VERTICAL);
			if (null != mTitleView) {
				titleLayout.removeAllViews();
				titleLayout.addView(mTitleView);

			} else if (null != mTitle) {
				titleView.setText(mTitle);
			} else {
				titleLayout.setVisibility(View.GONE);
			}
			if (mBtnList.size() <= 0) {
				btnLayout.setVisibility(View.GONE);
			} else if (mBtnList.size() == 1) {
				Pair<String, View.OnClickListener> btn = mBtnList.get(0);
				View v = makeBtn(R.drawable.selector_dialog_btn, btn.first,
						btn.second);
				// v.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				// LayoutParams.WRAP_CONTENT));
				btnLayout.addView(v);
			} else {
				Pair<String, View.OnClickListener> first = mBtnList.get(0);
				btnLayout.addView(makeBtn(R.drawable.selector_dialog_btn_left,
						first.first, first.second));
				ImageView divider = new ImageButton(mContext);
				divider.setBackgroundResource(R.drawable.bg_dialogdividingline);
				divider.setLayoutParams(new LinearLayout.LayoutParams(4,
						LinearLayout.LayoutParams.WRAP_CONTENT));
				btnLayout.addView(divider);
				for (int i = 1; i < mBtnList.size() - 1; i++) {
					Pair<String, View.OnClickListener> item = mBtnList.get(i);
					btnLayout.addView(makeBtn(
							R.drawable.selector_dialog_btn_middle, item.first,
							item.second));
					ImageView divider1 = new ImageButton(mContext);
					divider1.setBackgroundResource(R.drawable.bg_dialogdividingline);
					divider1.setLayoutParams(new LinearLayout.LayoutParams(4,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					btnLayout.addView(divider1);
				}

				Pair<String, View.OnClickListener> last = mBtnList.get(mBtnList
						.size() - 1);
				btnLayout.addView(makeBtn(R.drawable.selector_dialog_btn_right,
						last.first, last.second));
			}
			btnLayout.setPadding(0, 0, 0, 0);

			dialog.setContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			return dialog;

		}

		private View makeBtn(int backgroundResid, String text,
				View.OnClickListener listener) {
			return makeBtn(R.layout.dialog_btn, R.id.dialog_base_btn,
					backgroundResid, text, listener);
		}

		private View makeBtn(int layoutId, int textId, int backgroundResid,
				String text, View.OnClickListener listener) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(layoutId, null);
			v = setViewBackground(v, backgroundResid);
			v.setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 3f));

			final TextView textView = (TextView) v.findViewById(textId);
			textView.setText(text);
			v.setOnClickListener(listener);
			v.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						textView.setPressed(true);
						break;
					case MotionEvent.ACTION_CANCEL:
					case MotionEvent.ACTION_UP:
						textView.setPressed(false);
						break;
					default:
						break;
					}

					return false;
				}
			});
			return v;
		}

		private View setViewBackground(View v, int resid) {
			int paddingLeft = v.getPaddingLeft();
			int paddingTop = v.getPaddingTop();
			int paddingRight = v.getPaddingRight();
			int paddingBottom = v.getPaddingBottom();

			v.setBackgroundResource(resid);
			v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
			return v;
		}
	}

	@Override
	public void show() {
		super.show();
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		Window window = getWindow();
		lp.copyFrom(window.getAttributes());
		// This makes the dialog take up the full width
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);
	}
}
