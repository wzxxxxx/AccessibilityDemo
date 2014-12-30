package com.test;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	protected NotifyDataReceiver receiver = new NotifyDataReceiver();
	public static String INTENT_ACTION_ANOTHER = ".NotificationService";

	private ArrayList<MyNotify> notifyList = new ArrayList<MyNotify>();
	private ArrayList<String> textList = new ArrayList<String>();

	private static OnListChangeListener listener;
	private LinearLayout layout;
	private ListView listView;
	private static boolean flag2 = false;
	private static HashMap<String, String> downloadNorifies = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (LinearLayout) findViewById(R.id.layout);
		listView = (ListView) findViewById(R.id.listview);

		final MyAdapter adapter = new MyAdapter(notifyList, this);
		listView.setAdapter(adapter);

		listener = new OnListChangeListener() {
			@Override
			public void onListChange() {
				((Activity) MainActivity.this).runOnUiThread(new Runnable() {
					public void run() {
						adapter.notifyDataSetChanged();
					}
				});
			}
		};

		this.registerReceiver(receiver, new IntentFilter(INTENT_ACTION_ANOTHER));
	}

	public static interface OnListChangeListener {
		void onListChange();
	}

	/*
	 * find all textviews from the layout of a notification
	 */
	public void getTextView(ViewGroup layout) {
		if (layout != null && layout.getChildCount() > 0) {
			for (int i = 0; i < layout.getChildCount(); i++) {
				View view = layout.getChildAt(i);
				if (view instanceof TextView) {
					String text = (String) ((TextView) view).getText();
					if (text != null && text.length() > 0) {
						textList.add(text);
					}
					// in case of receiving too much responds from a
					// continuously updated notification(such as a download
					// service)
				} else if (view instanceof ProgressBar) {
					if (view.getVisibility() == View.VISIBLE) {
						flag2 = true;
					}
				} else if (view instanceof ViewGroup) {
					getTextView((ViewGroup) view);
				}
			}
		}

	}

	private class NotifyDataReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Parcelable notifyParcelable = intent
					.getParcelableExtra("NotifyData");
			String pkname = intent.getStringExtra("pkname");

			if (notifyParcelable != null) {
				Notification notification = (Notification) notifyParcelable;
				RemoteViews remoteV = notification.contentView;
				View view = remoteV.apply(MainActivity.this, layout);
				layout.addView(view);
				getTextView(layout);
				layout.removeAllViews();
				if (!flag2) {
					addNotify(pkname);
				} else {
					if (downloadNorifies.get(pkname) == null
							|| !(downloadNorifies.get(pkname).equals(textList
									.get(0)))) {
						downloadNorifies.put(pkname, textList.get(0));
						addNotify(pkname);
					}
					textList.clear();
					flag2 = false;
				}
			}
		}
	}

	private void addNotify(String pkname) {
		MyNotify notify = new MyNotify();
		notify.pkname = pkname;
		int length = textList.size();
		if (length >= 3) {
			notify.title = textList.get(0);
			notify.time = textList.get(1);
			notify.message = textList.get(2);
			notifyList.add(notify);
		}
		textList.clear();
		if (listener != null) {
			listener.onListChange();
		}

	}

	class MyNotify {
		public String pkname;
		public String title;
		public String message;
		public String time;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (receiver == null) {
			receiver = new NotifyDataReceiver();
		}
		registerReceiver(receiver, new IntentFilter(INTENT_ACTION_ANOTHER));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

}