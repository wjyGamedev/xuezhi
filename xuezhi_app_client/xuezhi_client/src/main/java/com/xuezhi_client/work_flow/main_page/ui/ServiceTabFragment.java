/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.main_page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/13		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.module.frame.BaseFragment;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.work_flow.main_page.msg_handler.MainMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

public class ServiceTabFragment extends BaseFragment
{
    //widget

    //logical
    private MainMsgHandler m_mainMsgHandler = null;
    private View           m_view           = null;

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_service, container, false);
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

	/**
     * inner func
     */
    private void init()
    {
		MainActivity mainActivity = (MainActivity)getActivity();
		if (mainActivity == null)
		{
			TipsDialog.GetInstance().setMsg("mainActivity == null", getActivity());
			TipsDialog.GetInstance().show();
			return;
		}
		m_mainMsgHandler = mainActivity.getMainMsgHandler();
    }
}
