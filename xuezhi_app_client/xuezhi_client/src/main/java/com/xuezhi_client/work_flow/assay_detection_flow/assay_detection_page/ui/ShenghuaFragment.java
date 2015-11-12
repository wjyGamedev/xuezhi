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
import butterknife.OnTextChanged;

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

	@OnTextChanged (value=R.id.atl_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterAtlETChanged(Editable s)
	{
		String tmpAtlValue = mAtlEt.getText().toString();
		if (!AssayDetectionConfig.checkAtl(tmpAtlValue))
			return;

		m_activity.setAtlValue(tmpAtlValue);

	}

	@OnTextChanged (value=R.id.ast_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterAstETChanged(Editable s)
	{
		String tmpAstValue = mAstEt.getText().toString();
		if (!AssayDetectionConfig.checkAst(tmpAstValue))
			return;

		m_activity.setAstValue(tmpAstValue);

	}

	@OnTextChanged (value=R.id.ck_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterCkETChanged(Editable s)
	{
		String tmpCkValue = mCkEt.getText().toString();
		if (!AssayDetectionConfig.checkCk(tmpCkValue))
			return;

		m_activity.setCkValue(tmpCkValue);
	}

	@OnTextChanged (value=R.id.glu_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterGluETChanged(Editable s)
	{
		String tmpGluValue = mGluEt.getText().toString();
		if (!AssayDetectionConfig.checkGlu(tmpGluValue))
			return;

		m_activity.setGlucValue(tmpGluValue);
	}

	@OnTextChanged (value=R.id.hba1c_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterHba1cETChanged(Editable s)
	{
		String tmpHba1cValue = mHba1cEt.getText().toString();
		if (AssayDetectionConfig.checkHba1c(tmpHba1cValue))
			return;

		m_activity.setHba1cValue(tmpHba1cValue);
	}

	@OnTextChanged (value=R.id.scr_et, callback= OnTextChanged.Callback.AFTER_TEXT_CHANGED)
	public void afterScrETChanged(Editable s)
	{
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
