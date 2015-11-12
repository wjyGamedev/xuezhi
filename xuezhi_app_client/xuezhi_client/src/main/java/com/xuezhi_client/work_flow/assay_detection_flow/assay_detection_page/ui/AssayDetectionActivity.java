package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.module.data.DGlobal;
import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler.AssayDetectionMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/9/22.
 */
public class AssayDetectionActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.content_display_region_ll) LinearLayout mContentDisplayRegionLl;
	@Bind (R.id.func_click_region_rgrp)    RadioGroup   mFuncClickRegionRgrp;
	@Bind (R.id.xuezhi_rbtn)               RadioButton  mXuezhiRbtn;
	@Bind (R.id.shenghua_rbtn)             RadioButton  mShenghuaRbtn;

	private BottomCommon m_bottomCommon = null;

	//logical
	private AssayDetectionMsgHandler      m_assayDetectionMsgHandler      = new AssayDetectionMsgHandler(this);
	private ClickHistoryBtn               m_clickHistoryBtn               = new ClickHistoryBtn();
	private ClickBottomBtn                m_clickBottomBtn                = new ClickBottomBtn();
	private PopDialog_ErrorTips           m_popDialog_errorTips           = new PopDialog_ErrorTips();
	private PopDialog_SaveSuccessTips     m_popDialog_saveSuccessTips     = new PopDialog_SaveSuccessTips();
	private AsyncWaitDialog               m_asyncWaitDialog               = new AsyncWaitDialog();
	private HandleWaitDialogFinishedEvent m_handleWaitDialogFinishedEvent = new HandleWaitDialogFinishedEvent();

	private String mTgValue   = "";
	private String mTchoValue = "";
	private String mLolcValue = "";
	private String mHdlcValue = "";

	private String mAtlValue   = "";
	private String mAstValue   = "";
	private String mCkValue    = "";
	private String mGlucValue  = "";
	private String mHba1cValue = "";
	private String mScrValue   = "";


	private final String INFO_SAVE_SUCCESS = DGlobal.GetInstance().getAppContext().getString(R.string.assay_detection_add_success_tips);
	private final String INFO_MAIN_PAGE    = DGlobal.GetInstance().getAppContext().getString(R.string.assay_detection_main_page_content);
	private final String INFO_HISTORY_PAGE = DGlobal.GetInstance().getAppContext().getString(R.string.assay_detection_history_btn_text);

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_assay_detection);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		updateContent();
	}

	@Override
	public void onDestoryAction()
	{

	}

	/**
	 * widget event
	 */
	@OnClick (R.id.xuezhi_rbtn)
	public void clickXuezhiRbtn()
	{
		switchXuezhi();
	}

	@OnClick (R.id.shenghua_rbtn)
	public void clickShenghuaRbtn()
	{
		switchShenghua();
	}

	@OnCheckedChanged(R.id.xuezhi_rbtn)
	public void xuezhiOnCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (!isChecked)
		{
			m_assayDetectionMsgHandler.loadXuezhiData();
		}
	}

	@OnCheckedChanged(R.id.shenghua_rbtn)
	public void shenghuaOnCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (!isChecked)
		{
			m_assayDetectionMsgHandler.loadShenghuaData();
		}
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

			int id = mFuncClickRegionRgrp.getCheckedRadioButtonId();
			if (id == R.id.xuezhi_info)
			{
				m_assayDetectionMsgHandler.loadXuezhiData();
			}
			else if (id == R.id.shenghua_rbtn)
			{
				m_assayDetectionMsgHandler.loadShenghuaData();
			}
			else
			{
				m_assayDetectionMsgHandler.loadXuezhiData();
			}

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

	class PopDialog_SaveSuccessTips implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			//01. 跳到主页面
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_assayDetectionMsgHandler.go2MainPage();
			}
			//02. 跳到历史页面
			else
			{
				m_assayDetectionMsgHandler.go2AssayDetectionHistoryPage();
			}
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
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.assay_detection_page_title_text);
		m_headerCommon.getRightButton().setVisibility(View.VISIBLE);
		m_headerCommon.getRightButton().setText(R.string.assay_detection_history_btn_text);
		m_headerCommon.getRightButton().setOnClickListener(m_clickHistoryBtn);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.assay_detection_save_content);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		m_asyncWaitDialog.initDefault(this);
		m_asyncWaitDialog.setHandleWaitDialogFinishedEvent(m_handleWaitDialogFinishedEvent);

		mXuezhiRbtn.setChecked(true);

	}

	private void updateContent()
	{
		int id = mFuncClickRegionRgrp.getCheckedRadioButtonId();
		if (id == R.id.xuezhi_info)
		{
			switchXuezhi();
		}
		else if (id == R.id.shenghua_rbtn)
		{
			switchShenghua();
		}
		else
		{
			switchXuezhi();
		}
	}

	private void switchXuezhi()
	{
		m_assayDetectionMsgHandler.go2XuezhiFragment();
	}

	private void switchShenghua()
	{
		m_assayDetectionMsgHandler.go2ShenghuaFragment();
	}


	private boolean check()
	{
		try
		{
			//血脂
			if (!AssayDetectionConfig.checkTg(mTgValue))
				return false;

			if (!AssayDetectionConfig.checkTcho(mTchoValue))
				return false;

			if (!AssayDetectionConfig.checkLolc(mLolcValue))
				return false;

			if (!AssayDetectionConfig.checkHdlc(mHdlcValue))
				return false;

			//生化
			if (!AssayDetectionConfig.checkAtl(mAtlValue))
				return false;

			if (!AssayDetectionConfig.checkAst(mAstValue))
				return false;

			if (!AssayDetectionConfig.checkCk(mCkValue))
				return false;

			if (!AssayDetectionConfig.checkGlu(mGlucValue))
				return false;

			if (!AssayDetectionConfig.checkHba1c(mHba1cValue))
				return false;

			if (!AssayDetectionConfig.checkScr(mScrValue))
				return false;

		}
		catch (NumberFormatException e)
		{
			popErrorDialog(e.toString());
			return false;
		}

		return true;
	}


	public AssayDetectionMsgHandler getAssayDetectionMsgHandler()
	{
		return m_assayDetectionMsgHandler;
	}

	public String getScrValue()
	{
		return mScrValue;
	}

	public void setScrValue(String scrValue)
	{
		mScrValue = scrValue;
	}

	public String getHba1cValue()
	{
		return mHba1cValue;
	}

	public void setHba1cValue(String hba1cValue)
	{
		mHba1cValue = hba1cValue;
	}

	public String getGlucValue()
	{
		return mGlucValue;
	}

	public void setGlucValue(String glucValue)
	{
		mGlucValue = glucValue;
	}

	public String getCkValue()
	{
		return mCkValue;
	}

	public void setCkValue(String ckValue)
	{
		mCkValue = ckValue;
	}

	public String getAstValue()
	{
		return mAstValue;
	}

	public void setAstValue(String astValue)
	{
		mAstValue = astValue;
	}

	public String getAtlValue()
	{
		return mAtlValue;
	}

	public void setAtlValue(String atlValue)
	{
		mAtlValue = atlValue;
	}

	public String getHdlcValue()
	{
		return mHdlcValue;
	}

	public void setHdlcValue(String hdlcValue)
	{
		mHdlcValue = hdlcValue;
	}

	public String getLolcValue()
	{
		return mLolcValue;
	}

	public void setLolcValue(String lolcValue)
	{
		mLolcValue = lolcValue;
	}

	public String getTchoValue()
	{
		return mTchoValue;
	}

	public void setTchoValue(String tchoValue)
	{
		mTchoValue = tchoValue;
	}

	public String getTgValue()
	{
		return mTgValue;
	}

	public void setTgValue(String tgValue)
	{
		mTgValue = tgValue;
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

	public void popSaveSuccessDialog()
	{
		AlertDialog.Builder builder = TipsDialog.GetInstance().setMsg(INFO_SAVE_SUCCESS,
																	  this,
																	  INFO_MAIN_PAGE,
																	  m_popDialog_saveSuccessTips,
																	  INFO_HISTORY_PAGE,
																	  m_popDialog_saveSuccessTips
																	 );
		if (builder != null)
		{
			builder.setCancelable(false);
		}
		TipsDialog.GetInstance().show();

		return;

	}


	public void dismissWaitDialog()
	{
		m_asyncWaitDialog.dismiss();
	}
}