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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.module.frame.BaseFragment;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.lineedittext.LineEditText;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler.AssayDetectionMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;

public class ShenghuaFragment extends BaseFragment
{

	@Bind (R.id.atl_et)              LineEditText         mAtlEt;
	@Bind (R.id.ast_et)              LineEditText         mAstEt;
	@Bind (R.id.ck_et)               LineEditText         mCkEt;
	@Bind (R.id.glu_et)              LineEditText         mGluEt;
	@Bind (R.id.hba1c_et)            LineEditText         mHba1cEt;
	@Bind (R.id.scr_et)              LineEditText         mScrEt;
	@Bind (R.id.xuezhi_info)         LinearLayout         mXuezhiInfo;

	private String mAtlValue   = "";
	private String mAstValue   = "";
	private String mCkValue    = "";
	private String mGlucValue  = "";
	private String mHba1cValue = "";
	private String mScrValue   = "";

	private AssayDetectionActivity   m_activity                 = null;
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

	@OnFocusChange (R.id.atl_et)
	public void OnFocusChangeAtlET(View v, boolean hasFocus)
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
				mAtlValue = editText.getText().toString();
			}
		}
		else
		{
			editText.setCursorVisible(true);
		}

	}

	@OnFocusChange (R.id.ast_et)
	public void OnFocusChangeAstET(View v, boolean hasFocus)
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
				mAstValue = editText.getText().toString();
			}
		}
		else
		{
			editText.setCursorVisible(true);
		}
	}

	@OnFocusChange (R.id.ck_et)
	public void OnFocusChangeCkET(View v, boolean hasFocus)
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
				mCkValue = editText.getText().toString();
			}
		}
		else
		{
			editText.setCursorVisible(true);
		}

	}

	@OnFocusChange (R.id.glu_et)
	public void OnFocusChangeGluET(View v, boolean hasFocus)
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
				mGlucValue = editText.getText().toString();
			}
		}
		else
		{
			editText.setCursorVisible(true);
		}
	}

	@OnFocusChange (R.id.hba1c_et)
	public void OnFocusChangeHba1cET(View v, boolean hasFocus)
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
				mHba1cValue = editText.getText().toString();
			}
		}
		else
		{
			editText.setCursorVisible(true);
		}
	}

	@OnFocusChange (R.id.scr_et)
	public void OnFocusChangeScrET(View v, boolean hasFocus)
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
				mScrValue = editText.getText().toString();
			}
		}
		else
		{
			editText.setCursorVisible(true);
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
		mAtlEt.setText(mAtlValue);
		mAstEt.setText(mAstValue);
		mCkEt.setText(mCkValue);
		mGluEt.setText(mGlucValue);
		mHba1cEt.setText(mHba1cValue);
		mScrEt.setText(mScrValue);
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
}
