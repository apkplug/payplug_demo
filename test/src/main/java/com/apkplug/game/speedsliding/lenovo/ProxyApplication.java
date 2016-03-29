package com.apkplug.game.speedsliding.lenovo;

import android.app.Application;
import android.content.pm.PackageManager;

import com.apkplug.pay.manager.PayManager;

public class ProxyApplication extends Application {

	public void onCreate() {

		super.onCreate();

		try {
			PayManager.getInstance().init(getApplicationContext(),Constants.APP_ID,Constants.PUBLIC_KEY);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
