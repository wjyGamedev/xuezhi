package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.msg_handler.DrugAdministrationMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationActivity extends Activity
{
	//widget
	private                           HeaderCommon m_headerCommon      = null;
	private                           BottomCommon m_bottomCommon      = null;
	@Bind (R.id.drug_info_display_lv) ListView     m_drugInfoDisplayLV = null;//显示药品信息列表

	//logical
	private DrugAdministrationAdapter     m_drugAdministrationAdapter            = null;
	private DrugAdministrationMsgHandler  m_drugAdministrationMsgHandler  = new DrugAdministrationMsgHandler(this);
	private ClickAddBottomBtn             m_clickAddBottomBtn             = new ClickAddBottomBtn();
	private ClickDelBottomBtn             m_clickDelBottomBtn             = new ClickDelBottomBtn();
	private AsyncWaitDialog               m_asyncWaitDialog               = new AsyncWaitDialog();
	private HandleWaitDialogFinishedEvent m_handleWaitDialogFinishedEvent = new HandleWaitDialogFinishedEvent();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drug_administration);
		ButterKnife.bind(this);

		init();
		initWaitAction();
	}

	private void initWaitAction()
	{
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	public void updateContent()
	{
		//01. 关闭wait dialog
		//		m_waitProgressDialog.dismiss();

		//02. 更新数据
		//		DNurseBasicsList nurseBasicsList = DNurseContainer.GetInstance().getNurseBasicsList();
		//		if (nurseBasicsList == null)
		//		{
		//			TipsDialog.GetInstance().setMsg("nurseBasicsList == null", SelectNurseActivity.this);
		//			TipsDialog.GetInstance().show();
		//			return;
		//		}
		//
		//		ArrayList<DNurseBasics> nurseBasicses = nurseBasicsList.getNurseBasicses();
		//		if (nurseBasicses == null || nurseBasicses.isEmpty())
		//		{
		//			return;
		//		}

		m_drugAdministrationAdapter.notifyDataSetChanged();

		return;

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

		m_drugAdministrationAdapter = new DrugAdministrationAdapter(this);
		m_drugInfoDisplayLV.setAdapter(m_drugAdministrationAdapter);

		//02. logical
		m_asyncWaitDialog.init(this);
		m_asyncWaitDialog.setHandleWaitDialogFinishedEvent(m_handleWaitDialogFinishedEvent);
	}

	/**
	 * overrider func
	 */
	class ClickAddBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_drugAdministrationMsgHandler.go2DrugStockAddPage();
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

	class HandleWaitDialogFinishedEvent implements AsyncWaitDialog.HandleWaitDialogFinishedEvent
	{
		public void OnWaitDialogFinished()
		{
			//01. 关闭等待框
			m_asyncWaitDialog.dismiss();
		}
	}

	public void dismissWaitDialog()
	{
		m_asyncWaitDialog.dismiss();
	}

	public DrugAdministrationMsgHandler getDrugAdministrationMsgHandler()
	{
		return m_drugAdministrationMsgHandler;
	}
}
