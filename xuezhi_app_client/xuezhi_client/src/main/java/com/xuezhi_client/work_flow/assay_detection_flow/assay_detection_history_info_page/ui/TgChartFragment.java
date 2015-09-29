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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler.AssayDetectionHistoryInfoMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

import butterknife.Bind;

public class TgChartFragment extends Fragment
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


	@Bind (R.id.assay_detection_list_lv) ListView m_assayDetectionListLV = null;

	//logical
	private AssayDetectionHistoryInfoMsgHandler m_assayDetectionHistoryInfoMsgHandler = null;
	private HandleOnSeekBarChange               m_handleOnSeekBarChange               = new HandleOnSeekBarChange();
	private HandleOnChartValueSelected          m_handleOnChartValueSelected          = new HandleOnChartValueSelected();

	private View m_view = null;

	public TgChartFragment()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_linechart, container, false);
		//TODO:由于嵌套fragment，所以不能用bind
//		ButterKnife.bind(this, m_view);
		m_lineChart = (LineChart)m_view.findViewById(R.id.chart1);
		m_seekBarX = (SeekBar)m_view.findViewById(R.id.seekBar1);
		m_seekBarY = (SeekBar)m_view.findViewById(R.id.seekBar2);
		m_xTV = (TextView)m_view.findViewById(R.id.tvXMax);
		m_yTV = (TextView)m_view.findViewById(R.id.tvYMax);

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

			setData(m_seekBarX.getProgress() + 1, m_seekBarY.getProgress());

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
//		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)getActivity();
//		if (activity == null)
//		{
//			TipsDialog.GetInstance().setMsg("activity == null", getActivity());
//			TipsDialog.GetInstance().show();
//			return;
//		}
//		m_assayDetectionHistoryInfoMsgHandler = activity.getAssayDetectionHistoryInfoMsgHandler();

		m_seekBarX.setProgress(45);
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
		MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.item_tips_view_linechart);

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
		setData(25, 50);

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
		//		m_ADHListAdapter.notifyDataSetChanged();
		m_lineChart.invalidate();
		return;
	}

	private void setData(int count, float range)
	{

		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++)
		{
			xVals.add((i % 30) + "/" + (i % 12) + "/14");
		}

		ArrayList<Entry> yVals = new ArrayList<Entry>();

		for (int i = 0; i < count; i++)
		{
			float mult = (range + 1);
			float val = (float)(Math.random() * mult) + 3;// + (float)
			// ((mult *
			// 0.1) / 10);
			yVals.add(new Entry(val, i));
		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");

		set1.setLineWidth(1.5f);
		set1.setCircleSize(4f);

		// create a data object with the datasets
		LineData data = new LineData(xVals, set1);

		// set data
		m_lineChart.setData(data);
	}

	/**
	 * logical func
	 */
	public static Fragment newInstance() {
		return new TgChartFragment();
	}

}

class MyMarkerView extends MarkerView
{

	private TextView tvContent;

	public MyMarkerView(Context context, int layoutResource) {
		super(context, layoutResource);

		tvContent = (TextView) findViewById(R.id.tvContent);
	}

	// callbacks everytime the MarkerView is redrawn, can be used to update the
	// content (user-interface)
	@Override
	public void refreshContent(Entry e, Highlight highlight) {

		if (e instanceof CandleEntry) {

			CandleEntry ce = (CandleEntry) e;

			tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
		} else {

			tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
		}
	}

	@Override
	public int getXOffset() {
		// this will center the marker-view horizontally
		return -(getWidth() / 2);
	}

	@Override
	public int getYOffset() {
		// this will cause the marker-view to be above the selected value
		return -getHeight();
	}
}