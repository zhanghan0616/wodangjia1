package com.wodangjia.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.wodangjia.bean.User;

public class Config {

	public static final String APP_ID = "com.example.wodangjialayout";
	
	
	public static final String CHARSET = "UTF-8";

//	public static final String API_URL = "http://169.254.28.101:8080/wdj/api";
	public static final String API_URL = "http://192.168.191.1:8080/wdj/api";
	public static final String UPLOAD_URL = "http://192.168.191.1:8080/wdj/upload";
//	public static final String API_URL_USER = API_URL + "/usermanager";
//	public static final String API_URL_GOODS = API_URL + "/goodsmanager";
	
	//smssdk 接口
	public static final String SMSSDK_APPKEY="ada9569df712";
	public static final String SMSSDK_APPSECRET="14eceb0fc51f9c80a384988ad105adbd";
	public static final String COUNTRY_CODE_CHINA="86";
	

	public static final String KEY_ACTION = "action";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_STATUS = "status";
	public static final String KEY_CODE = "code";
	public static final String KEY_PASSWORD_MD5="password_md5";
	public static final String KEY_USER="user";
	public static final String KEY_MSG="msg";
	
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_LOGIN_FAST = "login_fast";
	public static final String ACTION_SEND_CODE = "send_code";

	public static final int RESULT_STATUS_SUCCESS = 0;
	public static final int RESULT_STATUS_FAIL_LOGIN = 1;
	public static final int RESULT_STATUS_INVALID_TOKEN = 2;
	public static final int RESULT_STATUS_NOTFOUND = 3;
	

	public static final String SUCCESS_SEND_CODE = "验证码已发送至您的手机,有效时间5分钟，请及时填写！";
	public static final String ERROR_SEND_CODE = "验证码发送失败，请检查网络稍候重试！";
	public static final String ERROR_CODE_ERR = "验证码无效！";
	public static final String ERROR_LOGIN_FAIL = "登录失败，手机号或密码错误！";
	public static final String ERROR_UNCONNECTION_INTERNET = "连接服务器失败，请检查网络！";
	public static final String ERROR_PHONE_FORMAT = "手机号码格式错误！";
	public static final String ERROR_PHONE_NULL = "手机号码不能为空！";
	public static final String ERROR_PASSWORD_NULL = "密码不能为空！";
	public static final String ERROR_PASSWORD_FORMAT = "密码长度应为6-16位！";
	public static final String ERROR_VERCODE_NULL = "验证码不能为空！";
	public static final String ERROR_VERCODE_FORMAT = "验证码为6位数字！";
	public static final String ERROR_SERVER_EXCEPTION = "服务器异常，请联系管理员！";


	public static final int MSG_WHAT_SHOWTOAST=0;


	public static final int REQUEST_CODE_OPENPICS=11;
	public static final int REQUEST_CODE_CAMERA=22;
	public static final int REQUEST_CODE_ZOOM=33;


	


	


	
	

}
