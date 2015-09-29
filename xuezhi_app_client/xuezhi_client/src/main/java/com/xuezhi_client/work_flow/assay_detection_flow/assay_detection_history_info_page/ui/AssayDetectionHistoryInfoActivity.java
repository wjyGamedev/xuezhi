package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class AssayDetectionHistoryInfoActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.history_region_fl) FrameLayout m_historyRegionFL = null;
	@Bind (R.id.history_tabs_rg)   RadioGroup  m_tabsRG          = null;
	@Bind (R.id.chart_rbtn)        RadioButton m_chartRBtn = null;
	@Bind (R.id.list_rbtn)         RadioButton m_listRBtn = null;

	//logical
	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = new AssayDetectionHistoryInfoMsgHandler(this);
	private ClickChartDisplayBottomBtn          m_clickChartDisplayBottomBtn          = new ClickChartDisplayBottomBtn();
	private ClickListDisplayBottomBtn           m_clickListDisplayBottomBtn           = new ClickListDisplayBottomBtn();
	private PopDialog_ErrorTips           m_popDialog_errorTips           = new PopDialog_ErrorTips();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assay_detection_history_info);
		ButterKnife.bind(this);

		init();
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	/**
	 * overrider func
	 */
	class ClickChartDisplayBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_assayDetectionHistoryInfoMsgHandler.go2ChartFragment();
		}
	}

	class ClickListDisplayBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_assayDetectionHistoryInfoMsgHandler.go2ListFragment();
		}
	}

	class PopDialog_ErrorTips implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_assayDetectionHistoryInfoMsgHandler.go2MainPage();
			return;
		}
	}
	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.assay_detection_history_info_page_title_text);

		m_chartRBtn.setText(R.string.assay_detection_history_info_chart_display_btn_text);
		m_chartRBtn.setOnClickListener(m_clickChartDisplayBottomBtn);
		m_chartRBtn.setChecked(true);

		m_listRBtn.setText(R.string.assay_detection_history_info_list_display_btn_text);
		m_listRBtn.setOnClickListener(m_clickListDisplayBottomBtn);

		return;
	}

	private void updateContent()
	{
		int selectedID = m_tabsRG.getCheckedRadioButtonId();
		if (selectedID == R.id.chart_rbtn)
		{
			m_assayDetectionHistoryInfoMsgHandler.go2ChartFragment();
		}
		else if (selectedID == R.id.list_rbtn)
		{
			m_assayDetectionHistoryInfoMsgHandler.go2ListFragment();
		}
		else
		{
			m_assayDetectionHistoryInfoMsgHandler.go2ChartFragment();
		}
	}

	/**
	 * data:get
	 */
	public AssayDetectionHistoryInfoMsgHandler getAssayDetectionHistoryInfoMsgHandler()
	{
		return m_assayDetectionHistoryInfoMsgHandler;
	}

	/**
	 * logical
	 */
	public void popErrorDialog(String error)
	{
		TipsDialog.GetInstance().setMsg(error, this, m_popDialog_errorTips);
		TipsDialog.GetInstance().show();
		return;
	}
}