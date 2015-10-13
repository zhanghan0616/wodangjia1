package com.wodangjia.app;

import android.app.Application;

import com.lidroid.xutils.HttpUtils;
import com.loopj.android.http.PersistentCookieStore;
import com.wodangjia.bean.User;
import com.wodangjia.utils.ACache;

public class App extends Application {
	public static HttpUtils httpclient;// 异步网络请求
	public static User user;
	public static ACache mCache;
	
	@Override
	public void onCreate() {
		mCache=ACache.get(this);
		httpclient = new HttpUtils();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		httpclient.configCookieStore(myCookieStore);
		user=(User) mCache.getAsObject("user");
	}
	
	public static void saveLoginStatus(User user){
		App.user=user;
		mCache.put("user", user);
	}
}
