package com.zhidian.wifibox.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ta.TAApplication;
import com.umeng.analytics.MobclickAgent;
import com.zhidian.wifibox.R;
import com.zhidian.wifibox.util.InfoUtil;
import com.zhidian.wifibox.util.Setting;

/**
 * 关于界面
 * 
 */

public class AboutUsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		Setting setting = new Setting(this);
		TextView versionTv = (TextView) findViewById(R.id.version_tv);
		TextView dateTv = (TextView) findViewById(R.id.date_tv);
		TextView titleText = (TextView) findViewById(R.id.titleText_tv);
		titleText.setText(getString(R.string.title_about_us));
		versionTv.setText("版本信息： "
				+ InfoUtil.getVersionName(TAApplication.getApplication()));
		dateTv.setText("发布日期： " + setting.getString(Setting.UPDATE_TIME));

		findViewById(R.id.leftBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!TAApplication.DEBUG) {
			MobclickAgent.onPageStart("关于我们");
			MobclickAgent.onResume(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (!TAApplication.DEBUG) {
			MobclickAgent.onPageEnd("关于我们");
			MobclickAgent.onPause(this);
		}
	}

}
