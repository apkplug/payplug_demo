package com.apkplug.game.speedsliding.lenovo;

import android.app.Application;

import com.apkplug.pay.manager.PayManager;

public class ProxyApplication extends Application {

	public void onCreate() {

		super.onCreate();
		String appid="1PdtD8tsX1S";
		String app_secret="MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQCmpisW494qiEb1xt8HYzGVF8w4FtpNmBQ9mzpJ1aoWLq9WMhYlFCSL7+65Tpp+++N/rAvWr3uYdm3phW8itYvHM5UIQxzs20rxUm4m9kJxUwLU/+2hKm8RPOdcoQZ4CYUdj9vUaGAO/TheevrQKRU4mHpLsPVa7ez2tlHQcd9U1QIBAw==";
		PayManager.getInstance().init(getApplicationContext(),appid,"yang",app_secret);
	}
}
