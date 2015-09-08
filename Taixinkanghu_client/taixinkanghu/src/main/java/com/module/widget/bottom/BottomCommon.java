/**
 * Copyright (c) 213Team
 *
 * @className : com.module.widget.bottom.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/8		WangJY		1.0.0		create
 */

package com.module.widget.bottom;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomCommon extends Fragment
{
	@Bind(R.id.common_bottom_region_ll)
	LinearLayout m_commonBottomRegionLL = null;

	@Bind(R.id.common_bottom_btn)
	Button m_commonBottomBtn = null;

	private View m_view = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.include_bottom, container, false);
		ButterKnife.bind(this, m_view);
		return m_view;
	}

	@OnClick (R.id.common_bottom_btn)
	public void onClick(View v)
	{
		getActivity().finish();
	}

	public LinearLayout getCommonBottomRegionLL()
	{
		return m_commonBottomRegionLL;
	}

	public Button getCommonBottomBtn()
	{
		return m_commonBottomBtn;
	}
}
