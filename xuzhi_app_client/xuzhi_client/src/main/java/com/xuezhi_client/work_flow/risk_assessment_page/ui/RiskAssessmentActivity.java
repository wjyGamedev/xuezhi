package com.xuezhi_client.work_flow.risk_assessment_page.ui;

import android.app.Activity;
import android.os.Bundle;

import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class RiskAssessmentActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_risk_assessment);
		ButterKnife.bind(this);

		//		init();
	}

}