package com.westsoft.headlines;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class AlertDialogActivity extends Activity implements OnClickListener {

	public static AlertDialogActivity context = null;
	private MediaPlayer player = new MediaPlayer();
	WakeLock mWakelock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 需要在AndroidManifest里面设置权限，唤醒屏幕
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.FULL_WAKE_LOCK, "AlertDialog");
		mWakelock.acquire();
		// final Window win = getWindow();
		// win.addFlags( WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
		// WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
		// WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// 屏幕解锁，需要设置权限
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		KeyguardLock keyguardLock = keyguardManager
				.newKeyguardLock("AlertDialog");
		keyguardLock.disableKeyguard();
		context = this;
		try {
			Uri localUri = RingtoneManager.getActualDefaultRingtoneUri(context,
					RingtoneManager.TYPE_ALARM);
			if ((player != null) && (localUri != null)) {
				player.setDataSource(context, localUri);
				player.prepare();
				player.setLooping(false);
				player.start();
			}

			AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
			localBuilder.setTitle(R.string.alertName);
//			localBuilder.setMessage(getIntent().getStringExtra("content"));
			localBuilder.setMessage("【诗 42:1】 我的心切慕你，如鹿切慕溪水。");
			localBuilder.setPositiveButton(R.string.positiveButton, this);
			localBuilder.setNegativeButton(R.string.negativeButton, this);
			localBuilder.show();

		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
		} catch (SecurityException localSecurityException) {
			localSecurityException.printStackTrace();
		} catch (IllegalStateException localIllegalStateException) {
			localIllegalStateException.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch (which) {
		case DialogInterface.BUTTON1: {
			Intent intent = new Intent(AlertDialogActivity.this,
					MainActivity.class);
			Bundle b = new Bundle();
			b.putString("datetime", getIntent().getStringExtra("datetime"));
			b.putString("content", getIntent().getStringExtra("content"));
			b.putString("alerttime", getIntent().getStringExtra("alerttime"));
			intent.putExtra("android.intent.extra.INTENT", b);
			startActivity(intent); // 启动转到的Activity
			finish();
		}
		case DialogInterface.BUTTON2: {
			// mWakelock.release();
			player.stop();
			finish();
		}
		}
	}
}