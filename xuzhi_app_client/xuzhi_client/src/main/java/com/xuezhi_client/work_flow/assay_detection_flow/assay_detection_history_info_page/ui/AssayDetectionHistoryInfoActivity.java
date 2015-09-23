package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class AssayDetectionHistoryInfoActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = new AssayDetectionHistoryInfoMsgHandler(this);
	private ClickChartDisplayBottomBtn          m_clickChartDisplayBottomBtn          = new ClickChartDisplayBottomBtn();
	private ClickListDisplayBottomBtn           m_clickListDisplayBottomBtn           = new ClickListDisplayBottomBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assay_detection_history_info);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.assay_detection_history_info_page_title_text);
		m_bottomCommon.setBtnNum(2);
		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.assay_detection_history_info_chart_display_btn_text);
		m_bottomCommon.getCommonBottomBtn2().setText(R.string.assay_detection_history_info_list_display_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickChartDisplayBottomBtn);
		m_bottomCommon.getCommonBottomBtn2().setOnClickListener(m_clickListDisplayBottomBtn);
	}

	/**
	 * overrider func
	 */
	class ClickChartDisplayBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_assayDetectionHistoryInfoMsgHandler.showChartDisplay();
		}
	}

	class ClickListDisplayBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_assayDetectionHistoryInfoMsgHandler.showListDisplay();
		}
	}

}