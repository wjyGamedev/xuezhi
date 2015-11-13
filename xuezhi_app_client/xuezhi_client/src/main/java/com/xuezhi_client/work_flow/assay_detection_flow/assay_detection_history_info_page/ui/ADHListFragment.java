/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.module.frame.BaseFragment;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;

public class ADHListFragment extends BaseFragment
{
	//widget
	@Bind (R.id.assay_detection_list_lv) ListView m_assayDetectionListLV = null;

	//logical
	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = null;

	private View           m_view           = null;
	private ADHListAdapter m_ADHListAdapter = null;

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_history_list, container, false);
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

	@Override
	public void onStart()
	{
		updateContent();
		super.onStart();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("activity == null", getActivity());
			TipsDialog.GetInstance().show();
			return;
		}
		m_assayDetectionHistoryInfoMsgHandler = activity.getAssayDetectionHistoryInfoMsgHandler();

		m_ADHListAdapter = new ADHListAdapter(getActivity());
		m_assayDetectionListLV.setAdapter(m_ADHListAdapter);

	}

	public void updateContent()
	{
		m_ADHListAdapter.notifyDataSetChanged();
		return;
	}
}
