/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.select_date.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/12		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.ui;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.module.widget.wheelview.TosGallery;
import com.module.widget.wheelview.WheelView;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.msg_handler.OrderConfirmMsgHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectDateFragment extends Fragment
{
	protected class DateEle
	{

		public String m_content = null;
		public int    m_color   = Color.BLACK;
		public Date   m_date    = null;

		public DateEle(String text, Date date)
		{
			m_content = text;
			m_date = date;
		}

		public Date getDate()
		{
			return m_date;
		}
	}

	//widget
	@Bind (R.id.start_date_wheelview) WheelView    m_beginDateWheel   = null;
	@Bind (R.id.end_date_wheelview)   WheelView    m_endDateWheel     = null;
	@Bind (R.id.confirm_btn)          Button       m_confirmBtn       = null;
	@Bind (R.id.cancel_btn)           Button       m_cancelBtn        = null;
	@Bind (R.id.header_background_ll) LinearLayout m_headerBackground = null;
	@Bind (R.id.bottom_background_ll) LinearLayout m_bottomBackground = null;

	private View m_view = null;

	//logical
	private ArrayList<DateEle> m_beginDateDisplay = new ArrayList<>();
	private ArrayList<DateEle> m_endDateDisplay   = new ArrayList<>();
	private int                m_beginIndexPos    = 0;
	private int                m_endIndexPos      = 0;
	private WheelViewAdapter   m_beginAdapter     = null;
	private WheelViewAdapter   m_endAdapter       = null;

	private OrderConfirmMsgHandler         m_orderConfirmMsgHandler         = null;
	private HandleEndFlingOnBeginDateWheel m_handleEndFlingOnBeginDateWheel = new HandleEndFlingOnBeginDateWheel();
	private HandleEndFlingOnEndDateWheel   m_handleEndFlingOnEndDateWheel   = new HandleEndFlingOnEndDateWheel();
	private SimpleDateFormat               m_simpleDateFormat               = new SimpleDateFormat(DateConfig.PATTERN_DATE_MONTH_DAY_WEEK);


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_select_date, container, false);
		ButterKnife.bind(this, m_view);

		init();
		initContent();

		return m_view;
	}

	/**
	 * bind func
	 */
	@OnClick(R.id.header_background_ll)
	public void clickHeaderBGRegion()
	{
		m_orderConfirmMsgHandler.cancelSelectDateAction();
	}

	@OnClick(R.id.confirm_btn)
	public void clickConfirmBtn()
	{
		Date  beginDate = getBeginDate();
		if (beginDate == null)
		{
			OrderConfirmActivity activity = (OrderConfirmActivity)getActivity();
			activity.popErrorDialog("beginDate == null");
			return;
		}
		Date endDate = getEndDate();
		if (endDate == null)
		{
			OrderConfirmActivity activity = (OrderConfirmActivity)getActivity();
			activity.popErrorDialog("endDate == null");
			return;
		}
		DNursingDate selectDate = new DNursingDate(beginDate, endDate);
		m_orderConfirmMsgHandler.selectNursingDate(selectDate);
		m_orderConfirmMsgHandler.cancelSelectDateAction();

		return;
	}

	@OnClick(R.id.cancel_btn)
	public void clickCancelBtn()
	{
		m_orderConfirmMsgHandler.cancelSelectDateAction();
	}

	@OnClick(R.id.bottom_background_ll)
	public void clickBottomBGRegion()
	{
		m_orderConfirmMsgHandler.cancelSelectDateAction();
	}




	/**
	 * override:func
	 */
	public class HandleEndFlingOnBeginDateWheel implements TosGallery.OnEndFlingListener
	{
		@Override
		public void onEndFling(TosGallery v)
		{
			int pos = v.getSelectedItemPosition();
			m_beginIndexPos = pos;
		}
	}

	public class HandleEndFlingOnEndDateWheel implements TosGallery.OnEndFlingListener
	{
		@Override
		public void onEndFling(TosGallery v)
		{
			//位置不动
			m_endIndexPos = 0;
			return;
		}
	}

	private void init()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)getActivity();

		m_orderConfirmMsgHandler = activity.getOrderConfirmMsgHandler();
		if (m_orderConfirmMsgHandler == null)
		{
			activity.popErrorDialog("m_orderConfirmMsgHandler == null");
			return;
		}

	}

	private void initContent()
	{
		OrderConfirmActivity activity = (OrderConfirmActivity)getActivity();


		//01. m_beginDateDisplay
		ArrayList<Calendar> calendarArrayList = m_orderConfirmMsgHandler.getSelectedDateRegion();
		if (calendarArrayList.isEmpty())
		{
			activity.popErrorDialog("calendarArrayList.isEmpty()");
			return;
		}

		for (Calendar calendar : calendarArrayList)
		{
			Date tmpDate = calendar.getTime();
			String tmpContent = m_simpleDateFormat.format(tmpDate);
			DateEle tmpDateEle = new DateEle(tmpContent, tmpDate);
			m_beginDateDisplay.add(tmpDateEle);
		}

		//02. m_endDateDisplay
		m_endDateDisplay.add(m_beginDateDisplay.get(m_beginDateDisplay.size() - 1));

		//03. m_beginIndexPos,m_endIndexPos.
		m_beginIndexPos = 0;
		m_endIndexPos = 0;

		//04. widget
		m_beginDateWheel.setSoundEffectsEnabled(true);
		m_endDateWheel.setSoundEffectsEnabled(true);

		m_beginAdapter = new WheelViewAdapter(getActivity(), m_beginDateDisplay);
		m_endAdapter = new WheelViewAdapter(getActivity(), m_endDateDisplay);

		m_beginDateWheel.setAdapter(m_beginAdapter);
		m_endDateWheel.setAdapter(m_endAdapter);

		m_beginDateWheel.setSelection(m_beginIndexPos);
		m_endDateWheel.setSelection(m_endIndexPos);

		m_beginDateWheel.setOnEndFlingListener(m_handleEndFlingOnBeginDateWheel);
		m_endDateWheel.setOnEndFlingListener(m_handleEndFlingOnEndDateWheel);

	}

	public Date getBeginDate()
	{
		if (m_beginIndexPos >= m_beginDateDisplay.size())
			return null;

		DateEle dateEle = m_beginDateDisplay.get(m_beginIndexPos);
		return dateEle.getDate();
	}

	public Date getEndDate()
	{
		if (m_endIndexPos >= m_endDateDisplay.size())
			return null;

		DateEle dateEle = m_endDateDisplay.get(m_endIndexPos);
		return dateEle.getDate();
	}
}
