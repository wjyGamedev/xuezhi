package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.msg_handler.DrugAdministrationMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationActivity extends BaseActivity
{
	//widget
	private                           HeaderCommon m_headerCommon      = null;
	private                           BottomCommon m_bottomCommon      = null;
	@Bind (R.id.drug_info_display_lv) ListView     m_drugInfoDisplayLV = null;//显示药品信息列表

	private int selectDelDrugStockID = DataConfig.DEFAULT_VALUE;//选择删除的药品库存id

	//logical
	private DrugAdministrationAdapter     m_drugAdministrationAdapter     = null;
	private DrugAdministrationMsgHandler  m_drugAdministrationMsgHandler  = new DrugAdministrationMsgHandler(this);
	private ClickAddBottomBtn             m_clickAddBottomBtn             = new ClickAddBottomBtn();
	private ClickDelBottomBtn             m_clickDelBottomBtn             = new ClickDelBottomBtn();
	private AsyncWaitDialog               m_asyncWaitDialog               = null;
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
		updateMedicineBoxGetList();
		super.onStart();
	}


	@Override
	protected void onStop()
	{
		super.onStop();
		m_asyncWaitDialog.dismiss();
	}

	public void updateMedicineBoxGetList()
	{
		m_drugAdministrationMsgHandler.requestMedicineBoxGetListAction();
		m_asyncWaitDialog = new AsyncWaitDialog();
		m_asyncWaitDialog.initDefault(this);
		m_asyncWaitDialog.setHandleWaitDialogFinishedEvent(m_handleWaitDialogFinishedEvent);
		m_asyncWaitDialog.show();
	}

	public void updateContent()
	{
		m_asyncWaitDialog.dismiss();
		m_drugAdministrationAdapter.notifyDataSetChanged();
	}


	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.drug_administration_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.drug_administration_add_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickAddBottomBtn);

		m_drugAdministrationAdapter = new DrugAdministrationAdapter(this);
		m_drugInfoDisplayLV.setAdapter(m_drugAdministrationAdapter);

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

	public int getSelectDelDrugStockID()
	{
		return selectDelDrugStockID;
	}

	public void setSelectDelDrugStockID(int selectDelDrugStockID)
	{
		this.selectDelDrugStockID = selectDelDrugStockID;
	}
}
