package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.app.Activity;
import android.os.Bundle;

import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class AssayDetectionHistoryInfoActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assay_detection_history_info);
		ButterKnife.bind(this);

		//		init();
	}
}