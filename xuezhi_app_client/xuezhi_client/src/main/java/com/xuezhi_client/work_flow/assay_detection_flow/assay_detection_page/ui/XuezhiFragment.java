/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/12		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.module.frame.BaseFragment;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.lineedittext.LineEditText;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler.AssayDetectionMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class XuezhiFragment extends BaseFragment
{
	@Bind (R.id.tg_et)       LineEditText mTgEt;
	@Bind (R.id.tcho_et)     LineEditText mTchoEt;
	@Bind (R.id.lolc_et)     LineEditText mLolcEt;
	@Bind (R.id.hdlc_et)     LineEditText mHdlcEt;
	@Bind (R.id.xuezhi_info) LinearLayout mXuezhiInfo;

	private AssayDetectionActivity m_activity = null;
	private AssayDetectionMsgHandler m_assayDetectionMsgHandler = null;

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
	public void onDestoryViewAction()
	{
	}

	@Override
	public BaseFragment getOwner()
	{
		return this;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: inflate a fragment view
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnTextChanged (value=R.id.tg_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterTgETChanged(Editable s)
	{
		String tmpTgValue = mTgEt.getText().toString();
		if (AssayDetectionConfig.checkTg(tmpTgValue))
			return;

		m_activity.setTgValue(tmpTgValue);
	}

	@OnTextChanged (value=R.id.tcho_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterTchoETChanged(Editable s)
	{
		String tmpTchoValue = mTchoEt.getText().toString();
		if (AssayDetectionConfig.checkTcho(tmpTchoValue))
			return;

		m_activity.setTchoValue(tmpTchoValue);
	}

	@OnTextChanged (value=R.id.lolc_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterLolcETChanged(Editable s)
	{
		String tmpLolcValue = mLolcEt.getText().toString();
		if (AssayDetectionConfig.checkLolc(tmpLolcValue))
			return;

		m_activity.setLolcValue(tmpLolcValue);
	}

	@OnTextChanged (value=R.id.hdlc_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterHdlcETChanged(Editable s)
	{
		String tmpHdlcValue = mHdlcEt.getText().toString();
		if (AssayDetectionConfig.checkHdlc(tmpHdlcValue))
			return;

		m_activity.setHdlcValue(tmpHdlcValue);
	}

	public LineEditText getTgEt()
	{
		return mTgEt;
	}

	public LineEditText getTchoEt()
	{
		return mTchoEt;
	}

	public LineEditText getLolcEt()
	{
		return mLolcEt;
	}

	public LineEditText getHdlcEt()
	{
		return mHdlcEt;
	}
}
