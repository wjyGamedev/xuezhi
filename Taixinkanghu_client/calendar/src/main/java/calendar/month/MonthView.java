package calendar.month;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import calendar.CalendarDay;
import calendar.CalendarUtils;
import calendar.WeekDayView;
import calendar.config.Config;
import calendar.day.DayView;
import calendar.day.DayViewFacade;
import calendar.day.DayFormatter;
import calendar.day.WeekDayFormatter;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;

/**
 * Display a month of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
@SuppressLint ("ViewConstructor")
public class MonthView extends LinearLayout implements View.OnClickListener
{

	public interface DateChangeListener
	{
		void onDateChanged(CalendarDay date);
	}

	private MonthPagerAdapter m_monthPagerAdapter = null;
	private DateChangeListener m_dateChangeListener = null;    //外部日期改变的接口

	private final ArrayList<WeekDayView> m_weekDayViews        = new ArrayList<>();
	private final ArrayList<DayView>     m_monthDayViews       = new ArrayList<>();
	private final Calendar               m_tempWorkingCalendar = CalendarUtils.getInstance();
	private       boolean                m_showOtherDates      = false;
	private       CalendarDay            m_currentMonth        = null;
	private       int                    m_firstDayOfWeek      = Config.DEFAULT_FIRST_DAY_OF_THE_WEEK;
	private       CalendarDay            m_minDate             = null;
	private       CalendarDay            m_maxDate             = null;

	private final ArrayList<DecoratorResult> decoratorResults = new ArrayList<>();

	private CalendarDay selection = null;

	public MonthView(MonthPagerAdapter monthPagerAdapter, Context context, CalendarDay month, int firstDayOfWeek)
	{
		super(context);
		m_monthPagerAdapter = monthPagerAdapter;
		this.m_currentMonth = month;
		this.m_firstDayOfWeek = firstDayOfWeek;

		setOrientation(VERTICAL);

		setClipChildren(false);
		setClipToPadding(false);

		Calendar calendar = resetAndGetWorkingCalendar();

		LinearLayout row = makeRow(this);
		for (int i = 0; i < Config.DEFAULT_DAYS_IN_WEEK; i++)
		{
			WeekDayView weekDayView = new WeekDayView(context, CalendarUtils.getDayOfWeek(calendar));
			m_weekDayViews.add(weekDayView);
			row.addView(weekDayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
			calendar.add(DATE, 1);
		}

		calendar = resetAndGetWorkingCalendar();

		for (int r = 0; r < Config.DEFAULT_MAX_WEEKS; r++)
		{
			row = makeRow(this);
			for (int i = 0; i < Config.DEFAULT_DAYS_IN_WEEK; i++)
			{
				CalendarDay day = CalendarDay.from(calendar);
				DayView dayView = new DayView(context, day);
				dayView.setOnClickListener(this);
				m_monthDayViews.add(dayView);
				row.addView(dayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));

				calendar.add(DATE, 1);
			}
		}

		updateSelectedDate(monthPagerAdapter.getBeginDate(), monthPagerAdapter.getEndDate());
		//		setSelectedDate(CalendarDay.today());
	}

	@Override
	public void onClick(View v)
	{
		if (v instanceof DayView)
		{
			DayView dayView = (DayView)v;
			if (m_dateChangeListener != null)
			{
				m_dateChangeListener.onDateChanged(dayView.getDate());
			}
		}
	}

	void setDayViewDecorators(List<DecoratorResult> results)
	{
		this.decoratorResults.clear();
		if (results != null)
		{
			this.decoratorResults.addAll(results);
		}
		invalidateDecorators();
	}

	private static LinearLayout makeRow(LinearLayout parent)
	{
		LinearLayout row = new LinearLayout(parent.getContext());
		row.setOrientation(HORIZONTAL);
		parent.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
		return row;
	}

	public void setWeekDayTextAppearance(int taId)
	{
		for (WeekDayView weekDayView : m_weekDayViews)
		{
			weekDayView.setTextAppearance(getContext(), taId);
		}
	}

	public void setDateTextAppearance(int taId)
	{
		for (DayView dayView : m_monthDayViews)
		{
			dayView.setTextAppearance(getContext(), taId);
		}
	}

	public void setShowOtherDates(boolean show)
	{
		this.m_showOtherDates = show;
		updateUi();
	}

	public boolean getShowOtherDates()
	{
		return m_showOtherDates;
	}

	public CalendarDay getMonth()
	{
		return m_currentMonth;
	}

	public void setSelectionColor(int color)
	{
		for (DayView dayView : m_monthDayViews)
		{
			dayView.setSelectionColor(color);
		}
	}

	private Calendar resetAndGetWorkingCalendar()
	{
		m_currentMonth.copyTo(m_tempWorkingCalendar);
		m_tempWorkingCalendar.setFirstDayOfWeek(m_firstDayOfWeek);
		int dow   = CalendarUtils.getDayOfWeek(m_tempWorkingCalendar);
		int delta = m_firstDayOfWeek - dow;
		//If the delta is positive, we want to remove a week
		boolean removeRow = m_showOtherDates ? delta >= 0 : delta > 0;
		if (removeRow)
		{
			delta -= Config.DEFAULT_DAYS_IN_WEEK;
		}
		m_tempWorkingCalendar.add(DATE, delta);
		return m_tempWorkingCalendar;
	}

	public void setFirstDayOfWeek(int dayOfWeek)
	{
		this.m_firstDayOfWeek = dayOfWeek;

		Calendar calendar = resetAndGetWorkingCalendar();
		calendar.set(DAY_OF_WEEK, dayOfWeek);
		for (WeekDayView dayView : m_weekDayViews)
		{
			dayView.setDayOfWeek(calendar);
			calendar.add(DATE, 1);
		}

		calendar = resetAndGetWorkingCalendar();
		for (DayView dayView : m_monthDayViews)
		{
			CalendarDay day = CalendarDay.from(calendar);
			dayView.setDay(day);
			calendar.add(DATE, 1);
		}

		updateUi();
	}

	public void setWeekDayFormatter(WeekDayFormatter formatter)
	{
		for (WeekDayView dayView : m_weekDayViews)
		{
			dayView.setWeekDayFormatter(formatter);
		}
	}

	public void setDayFormatter(DayFormatter formatter)
	{
		for (DayView dayView : m_monthDayViews)
		{
			dayView.setDayFormatter(formatter);
		}
	}

	public void setMinimumDate(CalendarDay minDate)
	{
		this.m_minDate = minDate;
		updateUi();
	}

	public void setMaximumDate(CalendarDay maxDate)
	{
		this.m_maxDate = maxDate;
		updateUi();
	}

	public boolean isInRegion(CalendarDay tmpCalendarDay)
	{
		if (tmpCalendarDay == null)
			return false;

		return tmpCalendarDay.isInRange(m_minDate, m_maxDate);
	}
	//	public void setSelectedDate(CalendarDay cal)
	//	{
	//		selection = cal;
	//		updateUi();
	//	}

	//	public void clearDateList()
	//	{
	//		m_calendarDays.clear();
	//		for (DayView other : monthDayViews)
	//		{
	//			other.setChecked(false);
	//		}
	//	}

	//	public void setTypeList(ArrayList<Integer> typeArrayList)
	//	{
	//		m_typeArrayList = typeArrayList;
	//	}

	//	public void loadDateList(ArrayList<CalendarDay> selectedDateList, ArrayList<Integer> typeArrayList,
	// ArrayList<ArrayList<CalendarDay>> dateMonthList)
	//	{
	//		//01. 设置本月数据
	//		m_calendarDays = selectedDateList;
	//		m_typeArrayList = typeArrayList;
	//
	//		//02. 清楚之前UI数据
	//		for (DayView other : monthDayViews)
	//		{
	//			other.setChecked(false);
	//		}
	//
	//		//03. 画本月数据UI
	//		int     ourMonth   = month.getMonth();
	//		boolean bClickFlag = false;
	//		for (DayView dayView : monthDayViews)
	//		{
	//			CalendarDay day = dayView.getDate();
	//
	//			for (ArrayList<CalendarDay> calendarDayArrayList : dateMonthList)
	//			{
	//				for (CalendarDay calendarDay : calendarDayArrayList)
	//				{
	//					if (calendarDay == null)
	//						continue;
	//
	//					if (calendarDay.getDay() != day.getDay())
	//						continue;
	//
	//					if (calendarDay.getMonth() != day.getMonth())
	//						continue;
	//
	//					bClickFlag = true;
	//					break;
	//				}
	//			}
	//
	//			dayView.setupSelection(showOtherDates, day.isInRange(minDate, maxDate), day.getMonth() == ourMonth);
	//			dayView.setClickable(bClickFlag);
	//			dayView.setEnabled(bClickFlag);
	//			boolean isChecked = false;
	//			for (CalendarDay calendarDay : m_calendarDays)
	//			{
	//				if (day.equals(calendarDay))
	//				{
	//					isChecked = true;
	//					break;
	//				}
	//			}
	//			dayView.setChecked(isChecked);
	//		}
	//
	//		return;
	//	}

	//	public void setSelectedDateList(ArrayList<CalendarDay> selectedDateList)
	//	{
	//		m_calendarDays = selectedDateList;
	//		updateUiList();
	//	}

	//	private void updateUiList()
	//	{
	//		int ourMonth = month.getMonth();
	//		for (DayView dayView : monthDayViews)
	//		{
	//			CalendarDay day = dayView.getDate();
	//			dayView.setupSelection(showOtherDates, false, day.getMonth() == ourMonth);
	//
	//			boolean isChecked = false;
	//			for (CalendarDay calendarDay : m_calendarDays)
	//			{
	//				if (day.equals(calendarDay))
	//				{
	//					isChecked = true;
	//					break;
	//				}
	//			}
	//			dayView.setChecked(isChecked);
	//		}
	//		postInvalidate();
	//	}

	private void updateUi()
	{
		int ourMonth = m_currentMonth.getMonth();
		for (DayView dayView : m_monthDayViews)
		{
			CalendarDay day = dayView.getDate();
			dayView.setupSelection(m_showOtherDates, isInRegion(day), day.getMonth() == ourMonth);
//			dayView.setChecked(day.equals(selection));
		}
		postInvalidate();
	}

	public void invalidateDecorators()
	{
		final DayViewFacade facadeAccumulator = new DayViewFacade();
		for (DayView dayView : m_monthDayViews)
		{
			facadeAccumulator.reset();
			for (DecoratorResult result : decoratorResults)
			{
				if (result.decorator.shouldDecorate(dayView.getDate(), this))
				{
					result.result.applyTo(facadeAccumulator);
				}
			}
			dayView.applyFacade(facadeAccumulator);
		}
	}

	public void setDateChangeListener(DateChangeListener dateChangeListener)
	{
		this.m_dateChangeListener = dateChangeListener;
	}

	public void resetSelectedDate()
	{
		for (DayView other : m_monthDayViews)
		{
			other.setChecked(false);
		}
	}

	private void updateBeginDate(CalendarDay beginDate)
	{
		if (beginDate == null)
			return;

		int beginYear = beginDate.getYear();
		int beginMonth = beginDate.getMonth();
		int beginDay = beginDate.getDay();
		int ourMonth = m_currentMonth.getMonth();

		for (DayView dayView : m_monthDayViews)
		{
			CalendarDay day = dayView.getDate();
			int dayViewMonth = day.getMonth();
			int dayViewDay = day.getDay();
			dayView.setupSelection(m_showOtherDates, isInRegion(day), dayViewMonth == ourMonth);

			if (dayViewMonth == beginMonth && dayViewDay == beginDay)
			{
				dayView.setChecked(true);
				break;
			}
		}
		postInvalidate();

		return;
	}

	private void updateEndDate(CalendarDay beginDate, CalendarDay endDate)
	{
		if (beginDate == null)
			return;

		if (endDate == null)
			return;

		int ourYear = m_currentMonth.getYear();
		int ourMonth = m_currentMonth.getMonth();
		int beginYear = 0;
		int beginMonth = 0;
		int beginDay = 0;
		int endDay = 0;
		boolean bFlag = false;

		for (beginYear = beginDate.getYear(); beginYear <= endDate.getYear(); beginYear++)
		{
			if (beginYear != ourYear)
				continue;

			for (beginMonth = beginDate.getMonth(); beginMonth <= endDate.getMonth(); beginMonth++)
			{
				if (beginMonth != ourMonth)
					continue;

				bFlag = true;
				if (beginMonth == beginDate.getMonth())
				{
					beginDay = beginDate.getDay();
				}
				else
				{
					beginDay = 1;
				}
				if (beginMonth == endDate.getMonth())
				{
					endDay = endDate.getDay();
				}
				else
				{
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.MONTH, beginMonth);
					endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				}
				break;
			}

			if (bFlag)
				break;

		}

		for (DayView dayView : m_monthDayViews)
		{
			CalendarDay day = dayView.getDate();
			int dayViewMonth = day.getMonth();
			int dayViewDay = day.getDay();
			dayView.setupSelection(m_showOtherDates, isInRegion(day), dayViewMonth == ourMonth);

			boolean isChecked = false;
			if (bFlag)
			{
				if (dayViewMonth == beginMonth)
				{
					if (dayViewDay >= beginDay && dayViewDay <= endDay)
					{
						isChecked = true;
					}
				}
			}
			dayView.setChecked(isChecked);

		}
		postInvalidate();

		return;

	}

	public void updateSelectedDate(CalendarDay beginDate, CalendarDay endDate)
	{
		if (beginDate == null && endDate == null)
			return;

		//01. 只有开始时间
		if (beginDate != null && endDate == null)
		{
			updateBeginDate(beginDate);
		}
		//02. 开始时间和结束时间
		else
		{
			updateEndDate(beginDate, endDate);
		}
		return;

	}

}
