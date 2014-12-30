package com.test;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityEvent;

@SuppressLint("NewApi")
public class NotificationService extends AccessibilityService {
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (!(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)) {
			return;
		}
		String pkName = event.getPackageName().toString();
		StringBuilder message = new StringBuilder();
		if (!event.getText().isEmpty()) {
			for (CharSequence subText : event.getText()) {
				message.append(subText);
			}
		}
		Parcelable parcelable = event.getParcelableData();

		Notification notification = (Notification) parcelable;

		if (notification != null) {
			Intent intent = new Intent();
			intent.putExtra("NotifyData", notification);
			intent.putExtra("pkname", pkName);
			intent.setAction(".NotificationService");
			if (VERSION.SDK_INT < 19)
				sendBroadcast(intent);
		}
	}

	@Override
	public void onInterrupt() {

	}

	@Override
	protected void onServiceConnected() {
		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
		info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
		info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
		setServiceInfo(info);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return false;
	}

}
