package com.wodangjia.activity;

import com.example.wodangjialayout.R;
import com.example.wodangjialayout.R.color;
import com.example.wodangjialayout.R.id;
import com.example.wodangjialayout.R.layout;
import com.wodangjia.utils.ActivityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity implements OnClickListener{

	@SuppressLint("ResourceAsColor") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ActivityUtils.setActionBarLayout(getActionBar(),this, R.layout.title_bar_setting);
		ActivityUtils.setTranslucentStatus(getWindow(), true);
		ActivityUtils.setStatusBarColor(R.color.actionbar_bg,this);
		overridePendingTransition(R.anim.anim_right_to_left_in,R.anim.anim_right_to_left_out); 
		initView();
		
	}

	private void initView() {
		findViewById(R.id.btn_back).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			ActivityUtils.finish(this);
			break;

		default:
			break;
		}
		
	}
	@Override
	public void onBackPressed() {
		ActivityUtils.finish(this);
	};


}
