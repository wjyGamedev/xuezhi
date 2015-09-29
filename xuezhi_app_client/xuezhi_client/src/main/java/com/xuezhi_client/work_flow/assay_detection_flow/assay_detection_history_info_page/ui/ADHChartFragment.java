/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ADHChartFragment extends Fragment
{
	//widget
	@Bind (R.id.history_chart_fragment_ll) LinearLayout m_historyChartFragmentLL = null;
	@Bind (R.id.chart_vp) ViewPager m_chartVP = null;

	//logical
	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = null;

	private View            m_view            = null;
	private ADHChartAdapter m_adhChartAdapter = null;
	private int             m_height          = DataConfig.DEFAULT_VALUE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_history_chart, container, false);
		ButterKnife.bind(this, m_view);

		init();
		updateHeight();
		return m_view;
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

		//		m_adhChartAdapter = new ADHChartAdapter(getActivity().getSupportFragmentManager());
		m_adhChartAdapter = new ADHChartAdapter(getChildFragmentManager());
		m_chartVP.setAdapter(m_adhChartAdapter);
		m_chartVP.setCurrentItem(0);
		m_chartVP.setOffscreenPageLimit(1);

		//		m_adhChartAdapter = new ADHChartAdapter(getSupportFragmentManager());
		//		m_chartVP.setAdapter(m_adhChartAdapter);
//		m_chartVP.setOffscreenPageLimit(1);

	}
	private void updateHeight()
	{
		if (m_height != DataConfig.DEFAULT_VALUE)
		{
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)m_historyChartFragmentLL.getLayoutParams();
			layoutParams.height = m_height;
			m_historyChartFragmentLL.setLayoutParams(layoutParams);
		}
	}
	public void updateContent()
	{
		m_adhChartAdapter.notifyDataSetChanged();
		return;
	}

	/**
	 * data:set
	 */
	public void setHeight(int height)
	{
		m_height = height;
	}
}
