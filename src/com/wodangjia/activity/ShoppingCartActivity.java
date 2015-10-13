package com.wodangjia.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.wodangjialayout.R;
import com.wodangjia.utils.ActivityUtils;
import com.wodangjia.utils.StringUtils;

public class ShoppingCartActivity extends Activity implements OnClickListener {

	@SuppressLint("ResourceAsColor") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_cart);
		ActivityUtils.setActionBarLayout(getActionBar(),this, R.layout.title_bar_shopping_cart);
		ActivityUtils.setTranslucentStatus(getWindow(), true);
		ActivityUtils.setStatusBarColor(R.color.actionbar_bg,this);
		overridePendingTransition(R.anim.anim_right_to_left_in,R.anim.anim_right_to_left_out); 
		initView();
	}

	private void initView() {
		findViewById(R.id.action_bar_edit).setOnClickListener(this);
		findViewById(R.id.btn_back).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back://顶部actionbar 返回
			StringUtils.showToast(this, "单击了返回");
			break;
		case R.id.action_bar_edit://顶部编辑
			StringUtils.showToast(this, "单击了编辑");
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
