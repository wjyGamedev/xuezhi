package com.xuezhi_client.work_flow.main_page.ui;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class MainActivity extends Activity
{
	//widget

	//	@Bind (R.id.)

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		//init();
	}






}
