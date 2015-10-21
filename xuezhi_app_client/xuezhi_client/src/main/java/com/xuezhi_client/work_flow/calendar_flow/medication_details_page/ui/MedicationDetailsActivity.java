package com.xuezhi_client.work_flow.calendar_flow.medication_details_page.ui;

import android.os.Bundle;
import android.view.View;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.calendar_flow.medication_details_page.msg_handler.MedicationDetailsMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class MedicationDetailsActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private MedicationDetailsMsgHandler m_medicationDetailsMsgHandler = new MedicationDetailsMsgHandler(this);
	private ClickBottomBtn              m_clickBottomBtn              = new ClickBottomBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_details);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.medication_details_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.medication_details_bottom_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);
	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_medicationDetailsMsgHandler.go2MainPage();
		}
	}

}
