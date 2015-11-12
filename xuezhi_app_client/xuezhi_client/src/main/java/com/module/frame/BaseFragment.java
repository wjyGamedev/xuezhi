/**
 * Copyright (c) 213Team
 *
 * @className : com.module.frame.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/12		WangJY		1.0.0		create
 */

package com.module.frame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment
{
	private View m_view = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = onCreateViewAction(inflater, container, savedInstanceState);
		ButterKnife.bind(getOwner(), m_view);
		return m_view;
	}

	@Override
	public void onDestroyView()
	{
		onDestoryViewAction();
		ButterKnife.unbind(m_view);
		super.onDestroyView();
	}

	abstract public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	abstract public void onDestoryViewAction();
	abstract public BaseFragment getOwner();
}