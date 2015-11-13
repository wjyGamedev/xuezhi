/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.header.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${对应于include_header.xml}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/29		WangJY		1.0.0		create
 */

package com.module.widget.header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.module.frame.BaseFragment;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.OnClick;

public class HeaderCommon extends BaseFragment
{
	@Bind (R.id.title_tv)  TextView    m_headerTV  = null;    //title
	@Bind (R.id.back_ibtn) ImageButton m_backIBtn  = null;    //back btn
	@Bind (R.id.right_btn) Button      m_rightBtn = null;    //right btn

	private View m_view = null;


	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.include_header, container, false);
		return m_view;
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
		m_rightBtn.setVisibility(View.GONE);
	}

	@OnClick (R.id.back_ibtn)
	public void onClick(View v)
	{
		getActivity().finish();
	}

	public void setTitle(int resid)
	{
		m_headerTV.setText(resid);
	}

	public void setTitle(int resid, View.OnClickListener backBtnListener)
	{
		m_headerTV.setText(resid);

		if (backBtnListener != null)
		{
			m_backIBtn.setOnClickListener(backBtnListener);
		}
	}

	public TextView getHeaderTV()
	{
		return m_headerTV;
	}

	public ImageButton getBackIBtn()
	{
		return m_backIBtn;
	}

	public Button getRightButton()
	{
		return m_rightBtn;
	}

}
