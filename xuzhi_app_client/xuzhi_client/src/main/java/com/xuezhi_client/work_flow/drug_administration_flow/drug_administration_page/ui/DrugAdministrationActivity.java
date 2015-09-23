package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.msg_handler.DrugAdministrationMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private DrugAdministrationMsgHandler m_drugAdministrationMsgHandler = new DrugAdministrationMsgHandler(this);
	private ClickAddBottomBtn            m_clickAddBottomBtn            = new ClickAddBottomBtn();
	private ClickDelBottomBtn            m_clickDelBottomBtn            = new ClickDelBottomBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drug_administration);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.drug_administration_page_title_text);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.setBtnNum(2);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.drug_administration_add_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickAddBottomBtn);
		m_bottomCommon.getCommonBottomBtn2().setText(R.string.drug_administration_del_btn_text);
		m_bottomCommon.getCommonBottomBtn2().setOnClickListener(m_clickDelBottomBtn);
	}

	/**
	 * overrider func
	 */
	class ClickAddBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_drugAdministrationMsgHandler.addMedication_reminder();
		}
	}

	class ClickDelBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_drugAdministrationMsgHandler.delMedication_reminder();
		}
	}
}
