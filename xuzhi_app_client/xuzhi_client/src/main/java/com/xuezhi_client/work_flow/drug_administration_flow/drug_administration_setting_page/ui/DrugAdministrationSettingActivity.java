package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page;

import android.app.Activity;
import android.os.Bundle;

import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationSettingActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drug_administration_setting);
		ButterKnife.bind(this);

		//		init();
	}

}
