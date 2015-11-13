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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetection;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetectionList;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LipidAllChartFragment extends Fragment
{
	//widget
	private LineChart    m_lineChart = null;
	private SeekBar      m_seekBarX  = null;
	private SeekBar      m_seekBarY  = null;
	private TextView     m_xTV       = null;
	private TextView     m_yTV       = null;
	private LinearLayout m_xAxisLL   = null;
	private LinearLayout m_yAxisLL   = null;

	private View m_view = null;

	//logical
	private int[] m_colors = new int[]{AssayDetectionConfig.COLOR_TG, AssayDetectionConfig.COLOR_TCHO, AssayDetectionConfig.COLOR_LOLC,
			AssayDetectionConfig.COLOR_HDLC};


	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = null;
	private HandleOnSeekBarChange               m_handleOnSeekBarChange               = new HandleOnSeekBarChange();
	private HandleOnChartValueSelected          m_handleOnChartValueSelected          = new HandleOnChartValueSelected();

	private SimpleDateFormat m_ymdSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	ArrayList<DAssayDetection> m_assayDetectionArrayList = null;

	//TODO:待测试，从不活动状态，到活动状态，数据会否保存。
	public LipidAllChartFragment()
	{}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_linechart_single, container, false);
		//TODO:由于嵌套fragment，所以不能用bind
		//		ButterKnife.bind(this, m_view);
		m_lineChart = (LineChart)m_view.findViewById(R.id.chart1);
		m_seekBarX = (SeekBar)m_view.findViewById(R.id.seekBarX);
		m_seekBarY = (SeekBar)m_view.findViewById(R.id.seekBarY);
		m_xTV = (TextView)m_view.findViewById(R.id.tvXMax);
		m_yTV = (TextView)m_view.findViewById(R.id.tvYMax);
		m_xAxisLL = (LinearLayout)m_view.findViewById(R.id.x_axis_region_ll);
		m_yAxisLL = (LinearLayout)m_view.findViewById(R.id.y_axis_region_ll);
		m_yAxisLL.setVisibility(View.GONE);

		AssayDetectionHistoryInfoActivity assayDetectionHistoryInfoActivity = (AssayDetectionHistoryInfoActivity)getActivity();
		if (assayDetectionHistoryInfoActivity == null)
		{
			return m_view;
		}

		m_assayDetectionHistoryInfoMsgHandler = assayDetectionHistoryInfoActivity.getAssayDetectionHistoryInfoMsgHandler();
		DAssayDetectionList assayDetectionList = m_assayDetectionHistoryInfoMsgHandler.getAssayDetectionList();
		m_assayDetectionArrayList = assayDetectionList.getAssayDetections();

		init();
		return m_view;
	}

	@Override
	public void onStart()
	{
		updateContent();
		super.onStart();
	}

	/**
	 * override
	 */
	class HandleOnSeekBarChange implements SeekBar.OnSeekBarChangeListener
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			setData(m_seekBarX.getProgress());
			m_lineChart.invalidate();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
			return;
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			return;
		}
	}

	class HandleOnChartValueSelected implements OnChartValueSelectedListener
	{

		@Override
		public void onValueSelected(Entry entry, int i, Highlight highlight)
		{
			return;
		}

		@Override
		public void onNothingSelected()
		{
			return;
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		int xSize = m_assayDetectionArrayList.size();
		if (xSize > AssayDetectionConfig.CHART_X_AXIS_DEFAULT_LENGTH)
		{
			xSize = AssayDetectionConfig.CHART_X_AXIS_DEFAULT_LENGTH;
		}
		int xMax = m_assayDetectionArrayList.size();
		m_seekBarX.setProgress(xSize);
		m_seekBarX.setMax(xMax);
		m_seekBarX.setOnSeekBarChangeListener(m_handleOnSeekBarChange);

		m_seekBarY.setProgress(100);
		m_seekBarY.setOnSeekBarChangeListener(m_handleOnSeekBarChange);

		m_lineChart.setOnChartValueSelectedListener(m_handleOnChartValueSelected);
		m_lineChart.setDrawGridBackground(false);

		// no description text
		m_lineChart.setDescription("");
		String nullTips = getActivity().getString(R.string.assay_detection_history_info_list_display_null_content);
		m_lineChart.setNoDataTextDescription(nullTips);

		// enable value highlighting
		m_lineChart.setHighlightEnabled(true);

		// enable touch gestures
		m_lineChart.setTouchEnabled(true);

		// enable scaling and dragging
		m_lineChart.setDragEnabled(true);
		m_lineChart.setScaleEnabled(true);

		// if disabled, scaling can be done on x- and y-axis separately
		m_lineChart.setPinchZoom(true);
		// set an alternative background color
		// m_lineChart.setBackgroundColor(Color.GRAY);

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it
		ChartMarkerView mv = new ChartMarkerView(getActivity(), R.layout.item_tips_view_linechart);
		m_lineChart.setMarkerView(mv);

		// enable/disable highlight indicators (the lines that indicate the
		// highlighted Entry)
		m_lineChart.setHighlightEnabled(false);

		XAxis xl = m_lineChart.getXAxis();
		xl.setAvoidFirstLastClipping(true);

		YAxis leftAxis = m_lineChart.getAxisLeft();
		leftAxis.setInverted(false);

		YAxis rightAxis = m_lineChart.getAxisRight();
		rightAxis.setEnabled(false);

		// add data
		setData(xSize);

		// // restrain the maximum scale-out factor
		// m_lineChart.setScaleMinima(3f, 3f);
		//
		// // center the view to a specific position inside the chart
		// m_lineChart.centerViewPort(10, 50);

		// get the legend (only possible after setting data)
		Legend l = m_lineChart.getLegend();

		// modify the legend ...
		// l.setPosition(LegendPosition.LEFT_OF_CHART);
		l.setForm(Legend.LegendForm.LINE);

		// dont forget to refresh the drawing
		m_lineChart.invalidate();


	}

	public void updateContent()
	{
		m_lineChart.invalidate();
		return;
	}

	private String getLineName(int index)
	{
		//01. 通过index，来获取类型。这里是血脂all，所以index：0->1（TG:id）
		EnumConfig.AssayDetectionType assayDetectionType = getAssayDetectionTypeByIndex(index);

		//02.
		String yName    = AssayDetectionConfig.getName(assayDetectionType);
		String yUnit    = AssayDetectionConfig.getUnit(assayDetectionType);
		String unitTips = getActivity().getString(R.string.assay_detection_history_unit_tips);
		yName = yName + "(" + unitTips + yUnit + ")";

		return yName;
	}

	private EnumConfig.AssayDetectionType getAssayDetectionTypeByIndex(int index)
	{
		switch (index)
		{
			case 0:
				return EnumConfig.AssayDetectionType.TG;
			case 1:
				return EnumConfig.AssayDetectionType.TCHO;
			case 2:
				return EnumConfig.AssayDetectionType.LOLC;
			case 3:
				return EnumConfig.AssayDetectionType.HDLC;
			default:
				return EnumConfig.AssayDetectionType.TG;
		}
	}

	private double getYValue(DAssayDetection assayDetection, int type)
	{
		if (assayDetection == null)
			return 0f;

		EnumConfig.AssayDetectionType assayDetectionType = getAssayDetectionTypeByIndex(type);

		switch (assayDetectionType)
		{
			case TG:
				return assayDetection.getTgValue();
			case TCHO:
				return assayDetection.getTchoValue();
			case LOLC:
				return assayDetection.getLolcValue();
			case HDLC:
				return assayDetection.getHdlcValue();
			default:
				return assayDetection.getTgValue();
		}
	}

	private void setData(int count)
	{
		//01. 有效性
		if (count > m_assayDetectionArrayList.size())
			return;

		//02. x轴
		ArrayList<String> xVals = new ArrayList<String>();
		for (int indexX = 0; indexX < count; indexX++)
		{
			DAssayDetection assayDetection = m_assayDetectionArrayList.get(indexX);
			Calendar recordCalendar = assayDetection.getRecordCalendar();
			String displayDate = m_ymdSDF.format(recordCalendar.getTime());
			xVals.add(displayDate);
		}

		//03. y轴
		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		for (int indexType = 0; indexType < 4; indexType++)
		{
			ArrayList<Entry> values = new ArrayList<Entry>();

			for (int indexEle = 0; indexEle < count; indexEle++)
			{
				DAssayDetection assayDetection = m_assayDetectionArrayList.get(indexEle);
				double tgValue = getYValue(assayDetection, indexType);

				Calendar recordCalendar = assayDetection.getRecordCalendar();
				String displayDate = m_ymdSDF.format(recordCalendar.getTime());

				values.add(new Entry((float)tgValue, indexEle, displayDate));
			}

			String dateSetName = getLineName(indexType);
			LineDataSet lineDataSet = new LineDataSet(values, dateSetName);
			lineDataSet.setLineWidth(1.5f);
			lineDataSet.setCircleSize(4f);

			int color = m_colors[indexType % m_colors.length];
			lineDataSet.setColor(color);
			lineDataSet.setCircleColor(color);
			dataSets.add(lineDataSet);
		}

		LineData data = new LineData(xVals, dataSets);
		m_lineChart.setData(data);

		//02. label tip
		String molecule    = String.valueOf(count);
		String denominator = String.valueOf(m_assayDetectionArrayList.size());
		String display     = molecule + "/" + denominator;
		m_xTV.setText(display);

		//04. 修改Y轴最大值，数据的最大值+1/4
	}

	/**
	 * logical func
	 */
	public static Fragment newInstance()
	{
		LipidAllChartFragment lipidAllChartFragment = new LipidAllChartFragment();
		return lipidAllChartFragment;
	}

}

