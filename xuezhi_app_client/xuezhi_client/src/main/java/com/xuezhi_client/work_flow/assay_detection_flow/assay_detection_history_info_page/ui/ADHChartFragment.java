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
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.module.frame.BaseFragment;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class ADHChartFragment extends BaseFragment
{
	//widget
	@Bind (R.id.history_chart_fragment_ll) LinearLayout m_historyChartFragmentLL = null;
	@Bind (R.id.chart_vp)                  ViewPager    m_chartVP                = null;
	@Bind (R.id.left_arrow_ib)             ImageButton  m_leftArrowIB            = null;
	@Bind (R.id.title_content_tv)          TextView     m_titleContentTV         = null;
	@Bind (R.id.right_arrow_ib)            ImageButton  m_rightArrowIB           = null;
	@Bind (R.id.point_1_rb) RadioButton m_point1RB = null;
	@Bind (R.id.point_2_rb) RadioButton m_point2RB = null;
	@Bind (R.id.point_3_rb) RadioButton m_point3RB = null;
	@Bind (R.id.point_4_rb) RadioButton m_point4RB = null;
	@Bind (R.id.point_5_rb) RadioButton m_point5RB = null;
	@Bind (R.id.point_6_rb) RadioButton m_point6RB = null;
	@Bind (R.id.point_7_rb) RadioButton m_point7RB = null;
	@Bind (R.id.point_8_rb) RadioButton m_point8RB = null;
	@Bind (R.id.point_9_rb) RadioButton m_point9RB = null;
	@Bind (R.id.point_10_rb) RadioButton m_point10RB = null;


	//logical
	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = null;

	private View            m_view            = null;
	private ADHChartAdapter m_adhChartAdapter = null;
	private int             m_height          = DataConfig.DEFAULT_VALUE;
	private int             m_indexPage       = 0;//血脂all，对应于AssayDetectionType中的ID。

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_history_chart, container, false);
		return m_view;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
		updateHeight();
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
	 * widget func
	 */
	@OnClick (R.id.left_arrow_ib)
	public void clickLeftArrow()
	{
		int tmpIndexPage = m_indexPage;
		if (tmpIndexPage == 0)
		{
			tmpIndexPage = (AssayDetectionConfig.CHART_PAGE_NUM);
		}
		else
		{
			--tmpIndexPage;
		}
		setIndexPage(tmpIndexPage);
		m_chartVP.setCurrentItem(tmpIndexPage);
		return;
	}

	@OnClick (R.id.right_arrow_ib)
	public void clickRightArrow()
	{
		int tmpIndexPage = m_indexPage;
		if (tmpIndexPage == (AssayDetectionConfig.CHART_PAGE_NUM-1))
		{
			tmpIndexPage = 0;
		}
		else
		{
			++tmpIndexPage;
		}
		setIndexPage(tmpIndexPage);
		m_chartVP.setCurrentItem(tmpIndexPage);
		return;
	}

	@OnPageChange (value = R.id.chart_vp, callback = OnPageChange.Callback.PAGE_SELECTED)
	public void onPageSelected(int position)
	{
		setIndexPage(position);
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
		m_chartVP.setOffscreenPageLimit(10);

		setIndexPage(0);
		return;
	}

	private void updateTitleContent()
	{
		//01. ibtns
		if (m_indexPage == 0)
		{
			m_leftArrowIB.setVisibility(View.INVISIBLE);
			m_rightArrowIB.setVisibility(View.VISIBLE);
		}
		else if (m_indexPage == (AssayDetectionConfig.CHART_PAGE_NUM-1))
		{
			m_leftArrowIB.setVisibility(View.VISIBLE);
			m_rightArrowIB.setVisibility(View.INVISIBLE);
		}
		else
		{
			m_leftArrowIB.setVisibility(View.VISIBLE);
			m_rightArrowIB.setVisibility(View.VISIBLE);
		}

		//02. title content
		String title = getTitleContent();
		m_titleContentTV.setText(title);

		return;

	}

	private String getTitleContent()
	{
		EnumConfig.AssayDetectionType assayDetectionType = EnumConfig.AssayDetectionType.valueOf(m_indexPage);
		return assayDetectionType.getName();
	}

	private void updateBottomPoints()
	{
		switch (m_indexPage)
		{
		case 0:
			m_point1RB.setChecked(true);
			return;
		case 1:
			m_point2RB.setChecked(true);
			return;
		case 2:
			m_point3RB.setChecked(true);
			return;
		case 3:
			m_point4RB.setChecked(true);
			return;
		case 4:
			m_point5RB.setChecked(true);
			return;
		case 5:
			m_point6RB.setChecked(true);
			return;
		case 6:
			m_point7RB.setChecked(true);
			return;
		case 7:
			m_point8RB.setChecked(true);
			return;
		case 8:
			m_point9RB.setChecked(true);
			return;
		case 9:
			m_point10RB.setChecked(true);
			return;
		default:
			m_point1RB.setChecked(true);
			return;
		}
	}

	private void setIndexPage(int index)
	{
		if (index < 0 || index >= AssayDetectionConfig.CHART_PAGE_NUM)
		{
			return;
		}

		m_indexPage = index;

		updateTitleContent();
		updateBottomPoints();

		return;
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
		//01.
		updateTitleContent();

		//02.
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
