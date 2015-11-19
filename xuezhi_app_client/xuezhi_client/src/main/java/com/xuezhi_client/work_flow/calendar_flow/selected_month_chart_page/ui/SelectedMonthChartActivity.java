/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.calendar_flow.selected_month_chart_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.calendar_flow.selected_month_chart_page.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.work_flow.calendar_flow.config.CalendarFlowConfig;
import com.xuezhi_client.work_flow.calendar_flow.selected_month_chart_page.msg_handler.SelectedMonthChartMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;

public class SelectedMonthChartActivity extends BaseActivity
{
	private HeaderCommon m_headerCommon = null;
	@Bind (R.id.selected_month_piechart) PieChart mSelectedMonthPiechart;
	private BottomCommon m_bottomCommon = null;

	private SelectedMonthChartMsgHandler mSelectedMonthChartMsgHandler = new SelectedMonthChartMsgHandler(this);
	private ClickBottomBtn               m_clickBottomBtn              = new ClickBottomBtn();

	private SimpleDateFormat mYmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH);

	private Calendar mSelectedMonth = Calendar.getInstance();


	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_calendar_selected_month_chart);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onDestoryAction()
	{

	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			backAction();
		}
	}

	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.calendar_bottom_btn_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.selected_month_chart_page_bottom_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		//当前月份
		Intent intent = getIntent();
		String str    = intent.getStringExtra(CalendarFlowConfig.SELECTED_MONTH);
		try
		{
			Date date = mYmSDF.parse(str);
			mSelectedMonth.setTime(date);
		}
		catch (ParseException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}


		mSelectedMonthPiechart.setUsePercentValues(true);
		mSelectedMonthPiechart.setDescription("");
		mSelectedMonthPiechart.setDragDecelerationFrictionCoef(0.95f);

		mSelectedMonthPiechart.setDrawHoleEnabled(true);
		mSelectedMonthPiechart.setHoleColorTransparent(true);
		mSelectedMonthPiechart.setTransparentCircleColor(Color.WHITE);
		mSelectedMonthPiechart.setTransparentCircleAlpha(110);

		mSelectedMonthPiechart.setHoleRadius(58f);
		mSelectedMonthPiechart.setTransparentCircleRadius(61f);

		mSelectedMonthPiechart.setDrawCenterText(true);

		mSelectedMonthPiechart.setRotationAngle(0);
		// enable rotation of the chart by touch
		mSelectedMonthPiechart.setRotationEnabled(true);

		// mChart.setUnit(" €");
		// mChart.setDrawUnitsInChart(true);

		// add a selection listener
		//		mSelectedMonthPiechart.setOnChartValueSelectedListener(this);

		mSelectedMonthPiechart.setCenterText("test\nby hahaha");

		initChartData();

		mSelectedMonthPiechart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
		// mChart.spin(2000, 0, 360);

		Legend l = mSelectedMonthPiechart.getLegend();
		l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(0f);
		l.setYOffset(0f);
	}

	private void initChartData()
	{
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		for (int index = 0; index < CalendarFlowConfig.SELECTED_MONTH_PIE_CHART_MAX_VALUE; index++)
		{
			yVals1.add(new Entry(1, index));
		}

		int currMaxDays = mSelectedMonth.getMaximum(Calendar.DAY_OF_MONTH);
		int currYear    = mSelectedMonth.get(Calendar.YEAR);
		int currMonth   = mSelectedMonth.get(Calendar.MONTH);
		int currDay     = mSelectedMonth.get(Calendar.DAY_OF_MONTH);

		//01. 获取小于等于mSelectedMonth的该月提醒
		ArrayList<DMedicinePrompt> medicinePrompts = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPrompts();
		if (medicinePrompts.isEmpty())
			return;

		ArrayList<DMedicinePrompt> currMedicinePrompts = new ArrayList<>();
		for (DMedicinePrompt medicinePrompt : medicinePrompts)
		{
			Calendar addCalendar = medicinePrompt.getAddCalendar();
			if (currYear > addCalendar.get(Calendar.YEAR))
			{
				currMedicinePrompts.add(medicinePrompt);
				continue;
			}
			else if (currYear == addCalendar.get(Calendar.YEAR) && currMonth > addCalendar.get(Calendar.MONTH))
			{
				currMedicinePrompts.add(medicinePrompt);
				continue;
			}
			else if (currYear == addCalendar.get(Calendar.YEAR) &&
					currMonth == addCalendar.get(Calendar.MONTH))
			{
				currMedicinePrompts.add(medicinePrompt);
				continue;
			}
		}

		//获取该月的提醒
		String currDate = mYmSDF.format(mSelectedMonth.getTime());
		String display  = String.format(getString(R.string.selected_month_chart_page_take_medicine_analyse), currDate);

		ArrayList<String> xVals = new ArrayList<String>();
		xVals.add(getString(R.string.selected_month_chart_page_no_setting_reminder));
		xVals.add(getString(R.string.selected_month_chart_page_take_medicine_failed));
		xVals.add(getString(R.string.selected_month_chart_page_take_medicine_successed));
		xVals.add(getString(R.string.selected_month_chart_page_take_medicine_wait));

		PieDataSet dataSet = new PieDataSet(yVals1, display);
		dataSet.setSliceSpace(3f);
		dataSet.setSelectionShift(5f);

		// add a lot of colors
		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);

		colors.add(ColorTemplate.getHoloBlue());

		dataSet.setColors(colors);

		PieData data = new PieData(xVals, dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setValueTextColor(Color.WHITE);
		mSelectedMonthPiechart.setData(data);

		// undo all highlights
		mSelectedMonthPiechart.highlightValues(null);

		mSelectedMonthPiechart.invalidate();
	}

	private void backAction()
	{
		mSelectedMonthChartMsgHandler.go2MainPage();
	}


}