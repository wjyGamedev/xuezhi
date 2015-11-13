/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/12		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.module.frame.BaseFragment;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.lineedittext.LineEditText;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler.AssayDetectionMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.OnFocusChange;

public class XuezhiFragment extends BaseFragment
{
	@Bind (R.id.tg_et)              LineEditText         mTgEt;
	@Bind (R.id.tcho_et)            LineEditText         mTchoEt;
	@Bind (R.id.lolc_et)            LineEditText         mLolcEt;
	@Bind (R.id.hdlc_et)            LineEditText         mHdlcEt;
	@Bind (R.id.xuezhi_info)        LinearLayout         mXuezhiInfo;
	@Bind (R.id.tg_horizontal_sv)   HorizontalScrollView mTgHorizontalSv;
	@Bind (R.id.tcho_horizontal_sv) HorizontalScrollView mTchoHorizontalSv;
	@Bind (R.id.lolc_horizontal_sv) HorizontalScrollView mLolcHorizontalSv;
	@Bind (R.id.hdlc_horizontal_sv) HorizontalScrollView mHdlcHorizontalSv;

	private AssayDetectionActivity   m_activity                 = null;
	private AssayDetectionMsgHandler m_assayDetectionMsgHandler = null;

	private String mTgValue   = "";
	private String mTchoValue = "";
	private String mLolcValue = "";
	private String mHdlcValue = "";

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_xuezhi_info, container, false);
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onStart()
	{
		super.onStart();
		updateContent();
	}

	@Override
	public void onDestoryViewAction()
	{
	}

	@Override
	public BaseFragment getOwner()
	{
		return this;
	}

	@OnFocusChange (R.id.tg_et)
	public void OnFocusChangeTgET(View v, boolean hasFocus)
	{
		if (v == null)
			return;

		EditText editText = (EditText)v;
		if (editText == null)
			return;

		if (!hasFocus)
		{
			if (!TextUtils.isEmpty(editText.getText()))
			{
				mTgValue = editText.getText().toString();
			}

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mTgHorizontalSv.smoothScrollTo((int)mTgHorizontalSv.getScrollX() - m_activity
																					  .getWindowManager().getDefaultDisplay().getWidth(),
																			  (int)mTgHorizontalSv.getScrollY()
																			 );
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}
		else
		{
			editText.setCursorVisible(true);

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mTgHorizontalSv.smoothScrollTo((int)mTgHorizontalSv.getScrollX() + m_activity
																					 .getWindowManager().getDefaultDisplay().getWidth(),
																			 (int)mTgHorizontalSv.getScrollY()
																			);
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}

	}

	@OnFocusChange (R.id.tcho_et)
	public void OnFocusChangeTchoET(View v, boolean hasFocus)
	{
		if (v == null)
			return;

		EditText editText = (EditText)v;
		if (editText == null)
			return;

		if (!hasFocus)
		{
			if (!TextUtils.isEmpty(editText.getText()))
			{
				mTchoValue = editText.getText().toString();
			}

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mTchoHorizontalSv.smoothScrollTo((int)mTchoHorizontalSv.getScrollX() - m_activity
																					 .getWindowManager().getDefaultDisplay().getWidth(),
																			 (int)mTchoHorizontalSv.getScrollY()
																			);
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}
		else
		{
			editText.setCursorVisible(true);

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mTchoHorizontalSv.smoothScrollTo((int)mTchoHorizontalSv.getScrollX() + m_activity
																					   .getWindowManager().getDefaultDisplay().getWidth(),
																			   (int)mTchoHorizontalSv.getScrollY()
																			  );
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}

	}

	@OnFocusChange (R.id.lolc_et)
	public void OnFocusChangeLolcET(View v, boolean hasFocus)
	{
		if (v == null)
			return;

		EditText editText = (EditText)v;
		if (editText == null)
			return;

		if (!hasFocus)
		{
			if (!TextUtils.isEmpty(editText.getText()))
			{
				mLolcValue = editText.getText().toString();
			}

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mLolcHorizontalSv.smoothScrollTo((int)mLolcHorizontalSv.getScrollX() - m_activity
																					   .getWindowManager().getDefaultDisplay().getWidth(),
																			   (int)mLolcHorizontalSv.getScrollY()
																			  );
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}
		else
		{
			editText.setCursorVisible(true);

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mLolcHorizontalSv.smoothScrollTo((int)mLolcHorizontalSv.getScrollX() + m_activity
																					   .getWindowManager().getDefaultDisplay().getWidth(),
																			   (int)mLolcHorizontalSv.getScrollY()
																			  );
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}

	}

	@OnFocusChange (R.id.hdlc_et)
	public void OnFocusChangeHdlcET(View v, boolean hasFocus)
	{
		if (v == null)
			return;

		EditText editText = (EditText)v;
		if (editText == null)
			return;

		if (!hasFocus)
		{
			if (!TextUtils.isEmpty(editText.getText()))
			{
				mHdlcValue = editText.getText().toString();
			}

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mHdlcHorizontalSv.smoothScrollTo((int)mHdlcHorizontalSv.getScrollX() - m_activity
																					   .getWindowManager().getDefaultDisplay().getWidth(),
																			   (int)mHdlcHorizontalSv.getScrollY()
																			  );
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}
		else
		{
			editText.setCursorVisible(true);

			new Handler().postDelayed(new Runnable()
									  {
										  public void run()
										  {
											  mHdlcHorizontalSv.smoothScrollTo((int)mHdlcHorizontalSv.getScrollX() + m_activity
																					   .getWindowManager().getDefaultDisplay().getWidth(),
																			   (int)mHdlcHorizontalSv.getScrollY()
																			  );
										  }
									  }, AssayDetectionConfig.DELTA_TIME
									 );
		}

	}

	private void init()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("activity == null", getActivity());
			TipsDialog.GetInstance().show();
			return;
		}
		m_assayDetectionMsgHandler = activity.getAssayDetectionMsgHandler();
		m_activity = (AssayDetectionActivity)getActivity();
	}

	private void updateContent()
	{
		mTgEt.setText(mTgValue);
		mTchoEt.setText(mTchoValue);
		mLolcEt.setText(mLolcValue);
		mHdlcEt.setText(mHdlcValue);
	}

	public String getTgValue()
	{
		return mTgValue;
	}

	public void setTgValue(String tgValue)
	{
		mTgValue = tgValue;
	}

	public String getTchoValue()
	{
		return mTchoValue;
	}

	public void setTchoValue(String tchoValue)
	{
		mTchoValue = tchoValue;
	}

	public String getLolcValue()
	{
		return mLolcValue;
	}

	public void setLolcValue(String lolcValue)
	{
		mLolcValue = lolcValue;
	}

	public String getHdlcValue()
	{
		return mHdlcValue;
	}

	public void setHdlcValue(String hdlcValue)
	{
		mHdlcValue = hdlcValue;
	}
}
