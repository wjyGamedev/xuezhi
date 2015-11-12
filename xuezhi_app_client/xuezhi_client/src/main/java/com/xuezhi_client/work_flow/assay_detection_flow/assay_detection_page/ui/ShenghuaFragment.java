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
import butterknife.OnFocusChange;

public class ShenghuaFragment extends BaseFragment
{
	@Bind (R.id.atl_et)      LineEditText mAtlEt;
	@Bind (R.id.ast_et)      LineEditText mAstEt;
	@Bind (R.id.ck_et)       LineEditText mCkEt;
	@Bind (R.id.glu_et)      LineEditText mGluEt;
	@Bind (R.id.hba1c_et)    LineEditText mHba1cEt;
	@Bind (R.id.scr_et)      LineEditText mScrEt;
	@Bind (R.id.xuezhi_info) LinearLayout mXuezhiInfo;

	private AssayDetectionActivity m_activity = null;
	private AssayDetectionMsgHandler m_assayDetectionMsgHandler = null;

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_shenghua_info, container, false);
	}

	@Override
	public void onDestoryViewAction()
	{
		init();
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

	@OnFocusChange (R.id.atl_et)
	public void leaveAtlETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		String tmpAtlValue = mAtlEt.getText().toString();
		if (!AssayDetectionConfig.checkAtl(tmpAtlValue))
			return;

		m_activity.setAtlValue(tmpAtlValue);

	}

	@OnFocusChange (R.id.ast_et)
	public void leaveAstETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		String tmpAstValue = mAstEt.getText().toString();
		if (!AssayDetectionConfig.checkAst(tmpAstValue))
			return;

		m_activity.setAstValue(tmpAstValue);

	}

	@OnFocusChange (R.id.ck_et)
	public void leaveCkETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		String tmpCkValue = mCkEt.getText().toString();
		if (!AssayDetectionConfig.checkCk(tmpCkValue))
			return;

		m_activity.setCkValue(tmpCkValue);
	}

	@OnFocusChange (R.id.glu_et)
	public void leaveGluETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		String tmpGluValue = mGluEt.getText().toString();
		if (!AssayDetectionConfig.checkGlu(tmpGluValue))
			return;

		m_activity.setGlucValue(tmpGluValue);
	}

	@OnFocusChange (R.id.hba1c_et)
	public void leaveHba1cETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		String tmpHba1cValue = mHba1cEt.getText().toString();
		if (AssayDetectionConfig.checkHba1c(tmpHba1cValue))
			return;

		m_activity.setHba1cValue(tmpHba1cValue);
	}

	@OnFocusChange (R.id.scr_et)
	public void leaveScrETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		String tmpScrValue = mScrEt.getText().toString();
		if (!AssayDetectionConfig.checkScr(tmpScrValue))
			return;

		m_activity.setScrValue(tmpScrValue);
	}

	public LineEditText getAtlEt()
	{
		return mAtlEt;
	}

	public LineEditText getAstEt()
	{
		return mAstEt;
	}

	public LineEditText getCkEt()
	{
		return mCkEt;
	}

	public LineEditText getGluEt()
	{
		return mGluEt;
	}

	public LineEditText getHba1cEt()
	{
		return mHba1cEt;
	}

	public LineEditText getScrEt()
	{
		return mScrEt;
	}
}
