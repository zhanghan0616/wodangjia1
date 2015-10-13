package com.wodangjia.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.wodangjialayout.R;
import com.lidroid.xutils.HttpUtils;
import com.wodangjia.app.App;
import com.wodangjia.utils.ActivityUtils;
import com.wodangjia.utils.Config;
import com.wodangjia.utils.JsonUtils;
import com.wodangjia.utils.StringUtils;

public class FastLoginActivity extends Activity implements OnClickListener {
	private EditText et_phone, et_code;
	private Handler handler;
	private Button btn_getCode;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SMSSDK.initSDK(this, Config.SMSSDK_APPKEY, Config.SMSSDK_APPSECRET);
//		if(App.mCache.getAsObject("httpclient")==null){
//			System.out.println("用户没有登录");
//			finish();
//			return;
//		}
		SMSSDK.registerEventHandler(eh); // 注册短信回调
		setContentView(R.layout.activity_fast_login);
		handler=new Handler(){

			@Override
			public void handleMessage(Message message) {
				switch (message.what) {
				case Config.MSG_WHAT_SHOWTOAST:
					StringUtils.showToast(FastLoginActivity.this, message.getData().getString(Config.KEY_MSG));
					break;
				default:
					break;
				}
			}
			
		};
		ActivityUtils.setActionBarLayout(getActionBar(), this,R.layout.title_bar_fastlogin);
		ActivityUtils.setTranslucentStatus(getWindow(), true);
		ActivityUtils.setStatusBarColor(R.color.actionbar_bg, this);
		overridePendingTransition(R.anim.anim_right_to_left_in,R.anim.anim_right_to_left_out); 
		initView();

	}

	private void initView() {
		et_code = (EditText) findViewById(R.id.et_code);
		et_phone = (EditText) findViewById(R.id.et_phone);
		findViewById(R.id.activity_login_tv_common).setOnClickListener(this);
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_fast_login).setOnClickListener(this);
		btn_getCode=(Button) findViewById(R.id.btn_getCode);
		btn_getCode.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		System.out.println("---->");
		switch (v.getId()) {
		case R.id.activity_login_tv_common:
		case R.id.btn_back:
			ActivityUtils.finish(this);
			break;
		case R.id.btn_fast_login:
			fastLogin();
			break;
		case R.id.btn_getCode:
			getCode();
			break;
		default:
			break;
		}

	}

	private void getCode() {
		if (!StringUtils.isPhone(this, et_phone)) {
			return;
		}
		String phone = et_phone.getText().toString();
		
		SMSSDK.getVerificationCode(Config.COUNTRY_CODE_CHINA,phone);
		btn_getCode.setEnabled(false);
		new DownTimer(60000, 1000).start();
	
	}

	private void fastLogin() {
		// 验证手机号码和验证码的格式
		if (!StringUtils.fastLoginCheck(this, et_phone, et_code)) {
			return;
		}
		String phone = et_phone.getText().toString();
		String code = et_code.getText().toString();
		HttpUtils httpclient = App.httpclient;
//		RequestParams params = new RequestParams();
//		params.put(Config.KEY_ACTION, Config.ACTION_LOGIN_FAST);
//		params.put(Config.KEY_PHONE, phone);
//		params.put(Config.KEY_CODE, code);
//		client.post(Config.API_URL, params, new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, Header[] headers,
//					JSONObject response) {
//				int status = JsonUtils.getInt(response, Config.KEY_STATUS);
//				switch (status) {
//				case 0:
//					StringUtils.showToast(FastLoginActivity.this, "登录成功！");
//					
//					break;
//				case 1:
//					StringUtils.showToast(FastLoginActivity.this,
//							Config.ERROR_CODE_ERR);
//					break;
//				case 2:
//					StringUtils.showToast(FastLoginActivity.this,
//							Config.ERROR_SERVER_EXCEPTION);
//					break;
//				default:
//					break;
//				}
//
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					Throwable throwable, JSONObject errorResponse) {
//				StringUtils.showToast(FastLoginActivity.this,
//						Config.ERROR_UNCONNECTION_INTERNET);
//			}
//		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();

	};

	EventHandler eh = new EventHandler() {
		
		@Override
		public void afterEvent(int event, int result, Object data) {

			if (result == SMSSDK.RESULT_COMPLETE) {
				// 回调完成
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					// 提交验证码成功
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					// 获取验证码成功
					System.out.println("获取验证码成功");
					Message message=new Message();
					Bundle bundle=new Bundle();
					bundle.putString(Config.KEY_MSG, Config.SUCCESS_SEND_CODE);
					message.setData(bundle);
					handler.sendMessage(message);
					
				} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
					// 返回支持发送验证码的国家列表
				}
			} else {
				((Throwable) data).printStackTrace();
				System.err.println("------获取验证码出错---------");
				Message message=new Message();
				message.what=Config.MSG_WHAT_SHOWTOAST;
				Bundle bundle=new Bundle();
				bundle.putString(Config.KEY_MSG, Config.ERROR_SEND_CODE);
				message.setData(bundle);
				handler.sendMessage(message);
			}
		}
	};
	@Override
	public void onBackPressed() {
		ActivityUtils.finish(this);
	};
	class DownTimer extends CountDownTimer {

		public DownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_getCode.setText(millisUntilFinished/1000+"秒");
		}

		@Override
		public void onFinish() {
			btn_getCode.setText("重新获取");
			btn_getCode.setEnabled(true);
		}
	}
}
