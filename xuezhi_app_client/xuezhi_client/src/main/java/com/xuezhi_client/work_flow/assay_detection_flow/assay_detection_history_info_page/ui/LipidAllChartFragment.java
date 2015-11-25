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
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.module.frame.BaseFragment;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetection;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetectionList;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LipidAllChartFragment extends BaseFragment
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

	private FloatValueFormatter mFloatValueFormatter = new FloatValueFormatter();

	private SimpleDateFormat m_ymdSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private ArrayList<DAssayDetection> m_assayDetectionArrayList = null;

	private boolean m_isNullContentPage = false;

	//TODO:待测试，从不活动状态，到活动状态，数据会否保存。
	public LipidAllChartFragment()
	{}

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_linechart_single, container, false);

		AssayDetectionHistoryInfoActivity assayDetectionHistoryInfoActivity = (AssayDetectionHistoryInfoActivity)getActivity();
		if (assayDetectionHistoryInfoActivity == null)
		{
			return m_view;
		}

		m_assayDetectionHistoryInfoMsgHandler = assayDetectionHistoryInfoActivity.getAssayDetectionHistoryInfoMsgHandler();
		DAssayDetectionList assayDetectionList = m_assayDetectionHistoryInfoMsgHandler.getAssayDetectionList();
		m_assayDetectionArrayList = assayDetectionList.getAssayDetections();
		int xSize = m_assayDetectionArrayList.size();
		// add data
		int xMax = getXMax(xSize);
		if (xMax <= 0)
		{
			m_isNullContentPage = true;
			return m_view = inflater.inflate(R.layout.fragment_content_null, container, false);
		}
		else
		{
			m_isNullContentPage = false;
		}

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
		return m_view;
	}

	private int getXMax(int xSize)
	{
		//01. 有效性
		if (xSize > m_assayDetectionArrayList.size())
			return 0;

		ArrayList<String> xVals = new ArrayList<String>();
		for (int indexType = 0; indexType < 4; indexType++)
		{
			for (int indexEle = 0; indexEle < xSize; indexEle++)
			{
				DAssayDetection assayDetection = m_assayDetectionArrayList.get(indexEle);
				Calendar recordCalendar = assayDetection.getRecordCalendar();
				String displayDate = m_ymdSDF.format(recordCalendar.getTime());

				double tgValue = getYValue(assayDetection, indexType);

				if (tgValue == 0.0)
				{
					continue;
				}

				if (!isHaveSameVal(displayDate, xVals))
				{
					xVals.add(displayDate);
				}
			}
		}
		return xVals.size();
	}

	@Override
	public void onAfterCreateAction()
	{
		if (!m_isNullContentPage)
		{
			init();
		}
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
	 * override
	 */
	class HandleOnSeekBarChange implements SeekBar.OnSeekBarChangeListener
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			setData(m_assayDetectionArrayList.size(), m_seekBarX.getProgress() + 1);
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

	public class FloatValueFormatter implements YAxisValueFormatter
	{

		private DecimalFormat mFormat;

		public FloatValueFormatter()
		{
			mFormat = new DecimalFormat("##0.00"); // use one decimal
		}

		@Override
		public String getFormattedValue(float v, YAxis yAxis)
		{
			return mFormat.format(v);
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		int xSize = m_assayDetectionArrayList.size();

		int xMax = getXMax(xSize);

		if (xMax > AssayDetectionConfig.CHART_X_AXIS_DEFAULT_LENGTH)
		{
			xMax = AssayDetectionConfig.CHART_X_AXIS_DEFAULT_LENGTH;
		}

		//m_seekBarX 滑动控件没有设置最小值的方法，实际显示数据与控件数据相关联解决最小值问题
		//例如：seekbarTextView显示最小值为1，最大值为2，seekbar最小值为0，最大值为1
		//		当需要setData范围为1-2，控件getProgress为0-1
		if (xMax <= 1)
		{
			xMax = 0;
		}
		else
		{
			xMax -= 1;
		}

		m_seekBarX.setProgress(xMax);
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
		leftAxis.setValueFormatter(mFloatValueFormatter);
		YAxis rightAxis = m_lineChart.getAxisRight();
		rightAxis.setEnabled(false);

		// add data
		setData(xSize, xMax + 1);

		// // restrain the maximum scale-out factor
		// m_lineChart.setScaleMinima(3f, 3f);
		//
		// // center the view to a specific position inside the chart
		// m_lineChart.centerViewPort(10, 50);

		// get the legend (only possible after setting data)
		Legend l = m_lineChart.getLegend();

		// modify the legend ...
		// l.setPosition(LegendPosition.LEFT_OF_CHART);
		l.setForm(Legend.LegendForm.SQUARE);
		l.setWordWrapEnabled(true);
		// dont forget to refresh the drawing
		m_lineChart.invalidate();


	}

	public void updateContent()
	{
		if (!m_isNullContentPage)
		{
			m_lineChart.invalidate();
		}
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

	private int setData(int dataCount, int displayCount)
	{
		//01. 有效性
		if (dataCount > m_assayDetectionArrayList.size())
			return 0;

		//02. x轴
		//03. y轴
		double                 maxValue = 0;
		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		ArrayList<String>      xVals    = new ArrayList<String>();
		for (int indexType = 0; indexType < 4; indexType++)
		{
			ArrayList<Entry> values = new ArrayList<Entry>();

			for (int indexEle = 0; indexEle < dataCount; indexEle++)
			{

				DAssayDetection assayDetection = m_assayDetectionArrayList.get(indexEle);
				Calendar recordCalendar = assayDetection.getRecordCalendar();
				String displayDate = m_ymdSDF.format(recordCalendar.getTime());

				double tgValue = getYValue(assayDetection, indexType);

				if (tgValue == 0.0)
				{
					continue;
				}

				if (!isHaveSameVal(displayDate, xVals))
				{
					if (xVals.size() >= displayCount && displayCount != -1)
						continue;
					xVals.add(displayDate);
				}

				if (findIndexFromXvalsList(xVals, displayDate) != -1)
				{
					values.add(new Entry((float)tgValue, findIndexFromXvalsList(xVals, displayDate), displayDate));
				}

				if (tgValue > maxValue)
				{
					maxValue = tgValue;
				}
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
		String molecule;
		if (displayCount == -1)
		{
			molecule = String.valueOf(xVals.size());
		}
		else
		{
			molecule = String.valueOf(displayCount);
		}
		String denominator = String.valueOf(xVals.size());
		String display     = molecule + "/" + denominator;
		m_xTV.setText(display);

		//04. 修改Y轴最大值，数据的最大值+1/4
		YAxis leftAxis = m_lineChart.getAxisLeft();
		maxValue = maxValue + maxValue / 4;
		leftAxis.setAxisMaxValue((float)maxValue);

		return xVals.size();
	}

	private int findIndexFromXvalsList(ArrayList<String> xVals, String displayDate)
	{
		for (int i = 0; i < xVals.size(); i++)
		{
			if (xVals.get(i).equals(displayDate)) {return i;}
		}
		return -1;
	}

	private boolean isHaveSameVal(String displayDate, ArrayList<String> xVals)
	{
		for (String xVal : xVals)
		{
			if (xVal.equals(displayDate)) {return true;}
		}
		return false;
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

