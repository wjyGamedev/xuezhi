/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.header.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${对应于include_header.xml}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/29		WangJY		1.0.0		create
 */

package com.module.widget.header;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeaderCommon extends Fragment
{
	@Bind (R.id.title_tv)  TextView    m_headerTV = null;    //title
	@Bind (R.id.back_ibtn) ImageButton m_backIBtn = null;    //back btn

	private View m_view = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.include_header, container, false);
		ButterKnife.bind(this, m_view);
		return m_view;
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
}
