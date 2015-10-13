package com.wodangjia.activity;

import java.io.File;
import java.net.URI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.example.wodangjialayout.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wodangjia.app.App;
import com.wodangjia.utils.ActivityUtils;
import com.wodangjia.utils.Config;
import com.wodangjia.utils.StringUtils;
import com.wodangjia.view.CircleImageView;

public class UserInfoActivity extends Activity implements OnClickListener {

	private CircleImageView civ_photo;
	Dialog dialog;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		ActivityUtils.setActionBarLayout(getActionBar(), this,R.layout.title_bar_userinfo);
		ActivityUtils.setTranslucentStatus(getWindow(), true);
		ActivityUtils.setStatusBarColor(R.color.actionbar_bg, this);
		overridePendingTransition(R.anim.anim_right_to_left_in,R.anim.anim_right_to_left_out); 
		initView();
	}

	// 初始化视图 绑定事件监听器
	private void initView() {
		findViewById(R.id.btn_back).setOnClickListener(this);
		civ_photo=(CircleImageView) findViewById(R.id.userinfo_civ_photo);
		civ_photo.setOnClickListener(this);
		findViewById(R.id.userinfo_rl_photo).setOnClickListener(this);
		findViewById(R.id.userinfo_rl_nickname).setOnClickListener(this);
		findViewById(R.id.userinfo_rl_gender).setOnClickListener(this);
		findViewById(R.id.userinfo_rl_school).setOnClickListener(this);
		findViewById(R.id.userinfo_rl_buyer_credit).setOnClickListener(this);
		findViewById(R.id.userinfo_rl_seller_credit).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_back:// 点击了返回
			StringUtils.showToast(this, "返回");
			ActivityUtils.finish(this);
			break;
		case R.id.userinfo_civ_photo:// 点击了头像图片
			StringUtils.showToast(this, "点击了头像图片");
			showPhotoDialog();
			break;
		case R.id.userinfo_rl_photo:// 点击了头像rl
			StringUtils.showToast(this, "点击了头像布局");
			break;
		case R.id.userinfo_rl_nickname:// 点击了昵称布局
			StringUtils.showToast(this, "点击了昵称布局");
			break;
		case R.id.userinfo_rl_gender:// 点击了性别布局
			StringUtils.showToast(this, "点击了性别布局");
			break;
		case R.id.userinfo_rl_school:// 点击了学校布局
			StringUtils.showToast(this, "点击了学校布局");
			break;
		case R.id.userinfo_rl_buyer_credit:// 点击了买家信誉度
			StringUtils.showToast(this, "点击了买家信誉度");
			break;
		case R.id.openCamera://打开相机
			openCamera();
			break;
		case R.id.openPhones://打开图库
			openPhones();
			break;
		case R.id.cancel://取消
			dialog.cancel();
			break;

		default:
			break;
		}

	}
	private void openPhones() {
		dialog.dismiss();
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
		startActivityForResult(intent, Config.REQUEST_CODE_OPENPICS);
	}

	private void openCamera() {
		dialog.dismiss();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(UserInfoActivity.this.getExternalCacheDir(),"temp_photo.png")));
		startActivityForResult(intent,  Config.REQUEST_CODE_CAMERA);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("resultCode:"+resultCode);
		switch (requestCode) {
		// 如果是直接从相册获取
		case Config.REQUEST_CODE_OPENPICS:
			if(data!=null)
				startPhotoZoom(data.getData());
			break;
		// 如果是调用相机拍照时
		case Config.REQUEST_CODE_CAMERA:
			System.out.println("----"+data);
			if(resultCode==RESULT_OK){
				File file =new File(this.getExternalCacheDir(),"temp_photo.png");
				startPhotoZoom(Uri.fromFile(file));
			}
			break;
		// 取得裁剪后的图片
		case Config.REQUEST_CODE_ZOOM:
			if(resultCode==RESULT_OK){
			if(data != null){
//				dataIntent = data;
				setPicToView(data);
				uploadPhoto();
				}
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 500);
		intent.putExtra("outputY", 500);
		intent.putExtra("return-data", true);
		intent.putExtra("output", Uri.fromFile(new File(this.getExternalCacheDir(),"temp.png")));
		startActivityForResult(intent, Config.REQUEST_CODE_ZOOM);
	}
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			civ_photo.setImageBitmap(photo);
		}
	}
	private void showPhotoDialog() {
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,null);
		view.findViewById(R.id.openCamera).setOnClickListener(this);
		view.findViewById(R.id.openPhones).setOnClickListener(this);
		view.findViewById(R.id.cancel).setOnClickListener(this);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	@Override
	public void onBackPressed() {
		ActivityUtils.finish(this);
	};
	void uploadPhoto(){
		String uploadHost=Config.UPLOAD_URL;  //服务器接收地址  
		RequestParams params=new RequestParams();  
		params.addBodyParameter("msg","上传图片");   
		params.addBodyParameter("img1", new File(this.getExternalCacheDir(),"temp.png"));  //filePath是手机获取的图片地址  
		sendImgToServer(params,uploadHost);  
	}

	private void sendImgToServer(RequestParams params, String uploadHost) {
		 App.httpclient.send(HttpRequest.HttpMethod.POST, uploadHost, params,new RequestCallBack<String>() {  
		        @Override  
		        public void onStart() {  
		            //上传开始  
		        	System.out.println("开始上传");
		        }  
		        @Override  
		        public void onLoading(long total, long current,boolean isUploading) {  
		            //上传中  
		        	System.out.println("上传中  ");
		        }  
		        @Override  
		        public void onSuccess(ResponseInfo<String> responseInfo) {  
		            //上传成功，这里面的返回值，就是服务器返回的数据  
		            //使用 String result = responseInfo.result 获取返回值  
		        	System.out.println("上传成功  ");
		        }  
		        @Override  
		        public void onFailure(HttpException error, String msg) {  
		            //上传失败  
		        	System.out.println("上传失败  ");
		        }  
		    });  
		
	}

}
