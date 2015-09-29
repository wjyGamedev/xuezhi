package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler.AssayDetectionMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class AssayDetectionActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	//血脂
	@Bind (R.id.tg_et)    EditText m_tgET    = null;
	@Bind (R.id.tcho_et)  EditText m_tchoET  = null;
	@Bind (R.id.lolc_et)  EditText m_lolcET  = null;
	@Bind (R.id.hdlc_et)  EditText m_hdlcET  = null;
	//生化
	@Bind (R.id.atl_et)   EditText m_atlET   = null;
	@Bind (R.id.ast_et)   EditText m_astET   = null;
	@Bind (R.id.ck_et)    EditText m_ckET    = null;
	@Bind (R.id.glu_et)   EditText m_gluET   = null;
	@Bind (R.id.hba1c_et) EditText m_hba1cET = null;

	private BottomCommon m_bottomCommon = null;

	//logical
	private AssayDetectionMsgHandler      m_assayDetectionMsgHandler      = new AssayDetectionMsgHandler(this);
	private ClickHistoryBtn               m_clickHistoryBtn               = new ClickHistoryBtn();
	private ClickBottomBtn                m_clickBottomBtn                = new ClickBottomBtn();
	private PopDialog_ErrorTips           m_popDialog_errorTips           = new PopDialog_ErrorTips();
	private AsyncWaitDialog               m_asyncWaitDialog               = new AsyncWaitDialog();
	private HandleWaitDialogFinishedEvent m_handleWaitDialogFinishedEvent = new HandleWaitDialogFinishedEvent();

	@Override

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assay_detection);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.calendar_page_title_text);
		m_headerCommon.getRightButton().setVisibility(View.VISIBLE);
		m_headerCommon.getRightButton().setText(R.string.assay_detection_history_btn_text);
		m_headerCommon.getRightButton().setOnClickListener(m_clickHistoryBtn);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.calendar_bottom_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		m_asyncWaitDialog.init(this);
		m_asyncWaitDialog.setHandleWaitDialogFinishedEvent(m_handleWaitDialogFinishedEvent);

	}

	private boolean check()
	{
		//TODO:添加约束条件
		return true;
	}

	/**
	 * overrider func
	 */
	class ClickHistoryBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_assayDetectionMsgHandler.go2AssayDetectionHistoryPage();
		}
	}

	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (!check())
				return;

			m_assayDetectionMsgHandler.requestAddAssayDetectionInfoAction();
		}
	}

	class PopDialog_ErrorTips implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_assayDetectionMsgHandler.go2MainPage();
			return;
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
	/**
	 * widget:get
	 */
	public EditText getTgET()
	{
		return m_tgET;
	}

	public EditText getTchoET()
	{
		return m_tchoET;
	}

	public EditText getLolcET()
	{
		return m_lolcET;
	}

	public EditText getHdlcET()
	{
		return m_hdlcET;
	}

	public EditText getAtlET()
	{
		return m_atlET;
	}

	public EditText getAstET()
	{
		return m_astET;
	}

	public EditText getCkET()
	{
		return m_ckET;
	}

	public EditText getGluET()
	{
		return m_gluET;
	}

	public EditText getHba1cET()
	{
		return m_hba1cET;
	}

	/**
	 * logical
	 */
	public void popErrorDialog(String error)
	{
		TipsDialog.GetInstance().setMsg(error, this, m_popDialog_errorTips);
		TipsDialog.GetInstance().show();
		return;
	}

	public void dismissWaitDialog()
	{
		m_asyncWaitDialog.dismiss();
	}
}