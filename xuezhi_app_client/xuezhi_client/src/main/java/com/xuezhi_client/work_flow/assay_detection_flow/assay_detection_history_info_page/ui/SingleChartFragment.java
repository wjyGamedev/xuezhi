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

public class SingleChartFragment extends Fragment
{
	//widget
	//	@Bind (R.id.chart1)   LineChart m_lineChart = null;
	//	@Bind (R.id.seekBar1) SeekBar   m_seekBarX  = null;
	//	@Bind (R.id.seekBar2) SeekBar   m_seekBarY  = null;
	//	@Bind (R.id.tvXMax)   TextView  m_xTV       = null;
	//	@Bind (R.id.tvYMax)   TextView  m_yTV       = null;
	private LineChart m_lineChart = null;
	private SeekBar   m_seekBarX  = null;
	private SeekBar   m_seekBarY  = null;
	private TextView  m_xTV       = null;
	private TextView  m_yTV       = null;

	private View m_view = null;

	//logical
	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = null;
	private HandleOnSeekBarChange               m_handleOnSeekBarChange               = new HandleOnSeekBarChange();

	private HandleOnChartValueSelected m_handleOnChartValueSelected = new HandleOnChartValueSelected();

	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	private EnumConfig.AssayDetectionType m_assayDetectionType = null;
	ArrayList<DAssayDetection> m_assayDetectionArrayList = null;

	//TODO:待测试，从不活动状态，到活动状态，数据会否保存。
	public SingleChartFragment()
	{}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle args   = getArguments();
		int    typeID = args.getInt(AssayDetectionConfig.ASSAY_DETECTION_TYPE);
		m_assayDetectionType = EnumConfig.AssayDetectionType.valueOf(typeID);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_linechart_single, container, false);
		//TODO:由于嵌套fragment，所以不能用bind
		//		ButterKnife.bind(this, m_view);
		m_lineChart = (LineChart)m_view.findViewById(R.id.chart1);
		m_seekBarX = (SeekBar)m_view.findViewById(R.id.seekBar1);
		m_seekBarY = (SeekBar)m_view.findViewById(R.id.seekBar2);
		m_xTV = (TextView)m_view.findViewById(R.id.tvXMax);
		m_yTV = (TextView)m_view.findViewById(R.id.tvYMax);

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
			m_xTV.setText("" + (m_seekBarX.getProgress() + 1));
			m_yTV.setText("" + (m_seekBarY.getProgress()));

			setData(m_seekBarX.getProgress());

			// redraw
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
		int xMax = AssayDetectionConfig.CHART_X_AXIS_DEFAULT_LENGTH;
		if (xMax < m_assayDetectionArrayList.size())
		{
			xMax = m_assayDetectionArrayList.size();
		}
		m_seekBarX.setProgress(xSize);
		m_seekBarX.setMax(xMax);
		m_seekBarX.setOnSeekBarChangeListener(m_handleOnSeekBarChange);

		m_seekBarY.setProgress(100);
		m_seekBarY.setOnSeekBarChangeListener(m_handleOnSeekBarChange);

		m_lineChart.setOnChartValueSelectedListener(m_handleOnChartValueSelected);
		m_lineChart.setDrawGridBackground(false);

		// no description text
		m_lineChart.setDescription("");

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

		// set the marker to the chart
		m_lineChart.setMarkerView(mv);

		// enable/disable highlight indicators (the lines that indicate the
		// highlighted Entry)
		m_lineChart.setHighlightEnabled(false);

		XAxis xl = m_lineChart.getXAxis();
		xl.setAvoidFirstLastClipping(true);

		YAxis leftAxis = m_lineChart.getAxisLeft();
		leftAxis.setInverted(true);

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

	private double getYValue(DAssayDetection assayDetection)
	{
		if (assayDetection == null)
			return 0f;

		if (m_assayDetectionType == null)
			return 0f;

		switch (m_assayDetectionType)
		{
		case TG:
			return assayDetection.getTgValue();
		case TCHO:
			return assayDetection.getTchoValue();
		case LOLC:
			return assayDetection.getLolcValue();
		case HDLC:
			return assayDetection.getHdlcValue();
		case ATL:
			return assayDetection.getAtlValue();
		case AST:
			return assayDetection.getAstValue();
		case CK:
			return assayDetection.getCkValue();
		case GLU:
			return assayDetection.getGluValue();
		case HBA1C:
			return assayDetection.getHba1cValue();
		case SCR:
			return assayDetection.getScrValue();
		default:
			return assayDetection.getTgValue();
		}
	}

	private String getYName()
	{
		if (m_assayDetectionType == null)
			return AssayDetectionConfig.CHART_X_AXIS_DEFAULT_NAME;

		switch (m_assayDetectionType)
		{
		case TG:
		case TCHO:
		case LOLC:
		case HDLC:
		case ATL:
		case AST:
		case CK:
		case GLU:
		case HBA1C:
		case SCR:
			return m_assayDetectionType.getName();
		default:
			return AssayDetectionConfig.CHART_X_AXIS_DEFAULT_NAME;
		}

	}

	private void setData(int count)
	{
		if (count > m_assayDetectionArrayList.size())
			return;

		if (count < AssayDetectionConfig.CHART_X_AXIS_DEFAULT_LENGTH)
		{
			count = AssayDetectionConfig.CHART_X_AXIS_DEFAULT_LENGTH;
		}

		ArrayList<String> xVals = new ArrayList<String>();
		for (int indexX = 0; indexX < count; indexX++)
		{
			DAssayDetection assayDetection = m_assayDetectionArrayList.get(indexX);
			Calendar recordCalendar = assayDetection.getRecordCalendar();
 			String displayDate = m_allSDF.format(recordCalendar.getTime());
			xVals.add(displayDate);
		}

		ArrayList<Entry> yVals = new ArrayList<Entry>();
		for (int indexY = 0; indexY < count; indexY++)
		{
			DAssayDetection assayDetection = m_assayDetectionArrayList.get(indexY);
			double yValue = getYValue(assayDetection);
			yVals.add(new Entry((float)yValue, indexY));
		}

		// create a dataset and give it a type
		String yName = getYName();
		LineDataSet set1 = new LineDataSet(yVals, yName);
		set1.setLineWidth(1.5f);
		set1.setCircleSize(4f);

		// create a data object with the datasets
		LineData data = new LineData(xVals, set1);

		// set data
		m_lineChart.setData(data);
	}

	/**
	 * date:get
	 */
	public EnumConfig.AssayDetectionType getAssayDetectionType()
	{
		return m_assayDetectionType;
	}

	/**
	 * logical func
	 */
	public static Fragment newInstance(EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
		{
			return null;
		}

		SingleChartFragment singleChartFragment = new SingleChartFragment();
		Bundle              args                = new Bundle();
		args.putInt(AssayDetectionConfig.ASSAY_DETECTION_TYPE, assayDetectionType.getId());
		singleChartFragment.setArguments(args);

		return singleChartFragment;
	}

}
