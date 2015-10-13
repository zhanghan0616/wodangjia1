package com.wodangjia.activity;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import krelve.view.Kanner;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.example.wodangjialayout.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.wodangjia.app.App;
import com.wodangjia.utils.ActivityUtils;
import com.wodangjia.utils.Config;
import com.wodangjia.utils.StringUtils;

public class IndexActivity extends Activity implements OnClickListener {
	private RadioGroup radioGroup;
	private ViewPager viewPager;
	private ArrayList<View> viewsList;
	private Kanner kanner;
	private String[][] kannerImgs;
	private View buyView, saleView, chatView, myView;
	private HttpUtils httpclient = App.httpclient;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		ActivityUtils.setActionBarLayout(getActionBar(), IndexActivity.this,
				R.layout.title_bar_index_buy);
		ActivityUtils.setTranslucentStatus(getWindow(), true);
		ActivityUtils.setStatusBarColor(R.color.actionbar_bg, this);
		initView();
		initData();
	}

	private void initData() {
		kannerImgs = new String[][] {
				{
						"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg",
						"http://www.baidu.com" },
				{
						"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg",
						"http://www.baidu.com" },
				{
						"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg",
						"http://www.baidu.com" },
				{
						"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg",
						"http://www.baidu.com" } };
		kanner = (Kanner) buyView.findViewById(R.id.kanner);
		kanner.setImagesUrl(kannerImgs);

	}

	private void initView() {
		
		radioGroup = (RadioGroup) findViewById(R.id.bottom_radiogroup);
		viewsList = new ArrayList<View>();
		viewPager = (ViewPager) findViewById(R.id.content);

		LayoutInflater mInflater = LayoutInflater.from(IndexActivity.this);
		buyView = mInflater.inflate(R.layout.index_buy, null);
		saleView = mInflater.inflate(R.layout.index_sale, null);
		chatView = mInflater.inflate(R.layout.activity_login, null);
		myView = mInflater.inflate(R.layout.index_my, null);
		viewsList.add(buyView);
		viewsList.add(saleView);
		viewsList.add(chatView);
		viewsList.add(myView);

		// 初始化myview中的view
		myView.findViewById(R.id.index_my_iv_photo).setOnClickListener(this);
		myView.findViewById(R.id.index_my_rl_address).setOnClickListener(this);
		myView.findViewById(R.id.index_my_rl_buyedgoods).setOnClickListener(
				this);
		myView.findViewById(R.id.index_my_rl_collection).setOnClickListener(
				this);
		myView.findViewById(R.id.index_my_rl_looked).setOnClickListener(this);
		myView.findViewById(R.id.index_my_rl_password).setOnClickListener(this);
		myView.findViewById(R.id.index_my_rl_phone).setOnClickListener(this);
		myView.findViewById(R.id.index_my_rl_wallet).setOnClickListener(this);
		myView.findViewById(R.id.index_my_rl_userinfo).setOnClickListener(this);

		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// 当前界面显示的是哪一个视图
				View view = viewsList.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return viewsList.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// 销毁当前页视图
				View view = viewsList.get(position);
				container.removeView(view);
			}

		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.buy:
					loadBuyTitleBarLayout();
					break;
				case R.id.sale:
					loadSaleTitleBarLayout();
					break;
				case R.id.chat:
					loadChatTitleBarLayout();
					break;
				case R.id.my:
					loadMyTitleBarLayout();
					break;
				}
			}
		});
		RadioButton rbBuy=(RadioButton) findViewById(R.id.buy);
		rbBuy.setChecked(true);

	}

	protected void loadMyTitleBarLayout() {
		ActivityUtils.setActionBarLayout(getActionBar(), IndexActivity.this,
				R.layout.title_bar_index_my);
		viewPager.setCurrentItem(3, false);
		findViewById(R.id.my_title_setting).setOnClickListener(this);

	}

	protected void loadChatTitleBarLayout() {
		ActivityUtils.setActionBarLayout(getActionBar(), IndexActivity.this,
				R.layout.title_bar_index_chat);
		viewPager.setCurrentItem(2, false);
		findViewById(R.id.chat_title_photo).setOnClickListener(this);
		findViewById(R.id.chat_title_plus).setOnClickListener(this);
		RadioGroup chatRadioGroup = (RadioGroup) findViewById(R.id.chat_title_radiogroup);
		chatRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.chat_title_message:// 选中消息时
							StringUtils.showToast(IndexActivity.this, "选中消息时");
							break;
						case R.id.chat_title_contacts:// 选中联系人时
							StringUtils.showToast(IndexActivity.this, "选中联系人时");
							break;
						default:
							break;
						}

					}
				});

	}

	protected void loadSaleTitleBarLayout() {
		// TODO Auto-generated method stub
		ActivityUtils.setActionBarLayout(getActionBar(), IndexActivity.this,
				R.layout.title_bar_index_sale);
		viewPager.setCurrentItem(1, false);

	}

	protected void loadBuyTitleBarLayout() {
		ActivityUtils.setActionBarLayout(getActionBar(), IndexActivity.this,
				R.layout.title_bar_index_buy);
		viewPager.setCurrentItem(0, false);
		findViewById(R.id.buy_title_shoppingcart).setOnClickListener(this);
		findViewById(R.id.buy_rl_search).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.index_my_iv_photo:// 我的页面里的用户头像
			startActivity(new Intent(this, LoginActivity.class));
			break;
		case R.id.chat_title_photo:// 聊天顶部用户头像
			StringUtils.showToast(this, "聊天顶部用户头像");
			break;
		case R.id.chat_title_plus:// 聊天顶部加号
			StringUtils.showToast(this, "聊天顶部加号");
			break;
		case R.id.buy_title_shoppingcart:// 买页面顶部购物车点击
			StringUtils.showToast(this, "买页面顶部购物车点击");
			startActivity(new Intent(this,ShoppingCartActivity.class));
			break;
		case R.id.buy_rl_search:// 买页面顶部 搜索框点击
			StringUtils.showToast(this, "买页面顶部 搜索框点击");
			break;
		case R.id.my_title_setting:// 我的界面设置点击
			StringUtils.showToast(this, "我的界面设置点击");
			startActivity(new Intent(this, SettingActivity.class));
			
			break;
		case R.id.index_my_rl_address:// 我的界面收货地址被点击
			StringUtils.showToast(this, "我的界面收货地址被点击");
			break;
		case R.id.index_my_rl_buyedgoods:// 我的界面已买到的商品被点击
			StringUtils.showToast(this, "我的界面已买到的商品被点击");
			break;
		case R.id.index_my_rl_collection:// 我的界面我的手藏被点击
			StringUtils.showToast(this, "我的界面我的手藏被点击");
			break;
		case R.id.index_my_rl_looked:// 我的界面浏览记录被点击
			StringUtils.showToast(this, "我的界面浏览记录被点击");
			break;
		case R.id.index_my_rl_password:// 我的界面登录密码被点击
			StringUtils.showToast(this, "我的界面登录密码被点击");
			break;
		case R.id.index_my_rl_phone:// 我的界面绑定手机被点击
			StringUtils.showToast(this, "我的界面绑定手机被点击");
			break;
		case R.id.index_my_rl_userinfo:// 我的界面个人资料被点击
			StringUtils.showToast(this, "我的界面个人资料被点击");
			startActivity(new Intent(this,UserInfoActivity.class));
			break;
		case R.id.index_my_rl_wallet:// 我的界面钱包被点击
			StringUtils.showToast(this, "我的界面钱包被点击");
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (App.user != null) {
			StringUtils.showToast(this, "已经是登录状态");
		}
	}

}
