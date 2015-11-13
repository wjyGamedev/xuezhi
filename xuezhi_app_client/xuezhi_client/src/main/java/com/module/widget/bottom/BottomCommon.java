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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.module.frame.BaseFragment;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.OnClick;

public class BottomCommon extends BaseFragment
{

	@Bind (R.id.common_bottom_region_ll) LinearLayout m_commonBottomRegionLL = null;

	//01. 第一个功能按钮
	@Bind (R.id.common_bottom_btn) Button m_commonBottomBtn = null;

	//02. 第二个功能按钮
	@Bind (R.id.common_bottem_btn_region_ll_2) LinearLayout m_commonBottomRegionLL2 = null;

	@Bind (R.id.common_bottom_btn_2) Button m_commonBottomBtn2 = null;

	//03. 第三个功能按钮
	@Bind (R.id.common_bottem_btn_region_ll_3) LinearLayout m_commonBottomRegionLL3 = null;

	@Bind (R.id.common_bottom_btn_3) Button m_commonBottomBtn3 = null;


	private View m_view = null;

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.include_bottom, container, false);
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
		setBtnNum(1);
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

	public Button getCommonBottomBtn2()
	{
		return m_commonBottomBtn2;
	}

	public Button getCommonBottomBtn3()
	{
		return m_commonBottomBtn3;
	}

	public void setBtnNum(int iNum)
	{
		if (iNum < 1 || iNum > 3)
			return;

		if (iNum == 1)
		{
			m_commonBottomRegionLL2.setVisibility(View.GONE);
			m_commonBottomRegionLL3.setVisibility(View.GONE);
		}
		else if (iNum == 2)
		{
			m_commonBottomRegionLL2.setVisibility(View.VISIBLE);
			m_commonBottomRegionLL3.setVisibility(View.GONE);
		}
		else if (iNum == 3)
		{
			m_commonBottomRegionLL2.setVisibility(View.VISIBLE);
			m_commonBottomRegionLL3.setVisibility(View.VISIBLE);
		}

		return;

	}


}
