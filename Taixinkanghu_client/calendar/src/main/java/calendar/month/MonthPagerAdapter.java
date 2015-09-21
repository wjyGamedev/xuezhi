/**
 * Copyright (c) 213Team
 *
 * @className : calendar.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/16		WangJY		1.0.0		create
 */

package calendar.month;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import calendar.CalendarDay;
import calendar.CalendarUtils;
import calendar.calendar.MaterialCalendarView;
import calendar.config.Config;
import calendar.day.DayFormatter;
import calendar.day.DayViewDecorator;
import calendar.day.DayViewFacade;
import calendar.day.WeekDayFormatter;

public class MonthPagerAdapter extends PagerAdapter
{
	//logical
	private MaterialCalendarView   m_parentCalendarView = null;
	private LinkedList<MonthView>  m_currentMonthViews  = null;    //month view
	private ArrayList<CalendarDay> m_calendarDays       = null;     //month calendar date

	private int m_firstDayOfTheWeek = Config.DEFAULT_FIRST_DAY_OF_THE_WEEK;

	private MonthView.DateChangeListener m_dateChangeListener    = null;
	private Integer                      m_selectedColor         = null;
	private Integer                      m_dateTextAppearance    = null;
	private Integer                      m_weekDayTextAppearance = null;
	private Boolean                      m_showOtherDates        = null;

	private WeekDayFormatter       m_weekDayFormatter = WeekDayFormatter.DEFAULT;
	private DayFormatter           m_dayFormatter     = DayFormatter.DEFAULT;
	private List<DayViewDecorator> m_decorators       = new ArrayList<>();
	private List<DecoratorResult>  m_decoratorResults = null;
	private CalendarDay            m_minDate          = null;
	private CalendarDay            m_maxDate          = null;

	private CalendarDay                       m_beginDate           = null;    //选择的开始日期
	private CalendarDay                       m_endDate             = null;        //选择的结束日期
	private HashMap<CalendarDay, Integer>     m_selectedDateHashMap = new HashMap<>(); //选择的日期和类型(24，12白，12黑)

	//TODO:等待被改进
	private CalendarDay m_selectedDate = null;

	public MonthPagerAdapter(MaterialCalendarView view)
	{
		this.m_parentCalendarView = view;
		m_currentMonthViews = new LinkedList<>();
		m_calendarDays = new ArrayList<>();
		//TODO:等待被整改
		setRangeDates(null, null);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		CalendarDay month     = m_calendarDays.get(position);
		MonthView   monthView = new MonthView(this, container.getContext(), month, m_firstDayOfTheWeek);

		//01. 外部注册event
		monthView.setDateChangeListener(m_dateChangeListener);

		//02. format
		monthView.setWeekDayFormatter(m_weekDayFormatter);
		monthView.setDayFormatter(m_dayFormatter);

		//03. 外观
		if (m_selectedColor != null)
		{
			monthView.setSelectionColor(m_selectedColor);
		}
		if (m_dateTextAppearance != null)
		{
			monthView.setDateTextAppearance(m_dateTextAppearance);
		}
		if (m_weekDayTextAppearance != null)
		{
			monthView.setWeekDayTextAppearance(m_weekDayTextAppearance);
		}

		//04. display format
		if (m_showOtherDates != null)
		{
			monthView.setShowOtherDates(m_showOtherDates);
		}
		monthView.setMinimumDate(m_minDate);
		monthView.setMaximumDate(m_maxDate);

		//05. logical data
//		monthView.setSelectedDate(m_selectedDate);

		container.addView(monthView);
		m_currentMonthViews.add(monthView);

		monthView.setDayViewDecorators(m_decoratorResults);

		return monthView;
	}

	@Override
	public int getItemPosition(Object object)
	{
		if (!(object instanceof MonthView))
		{
			return POSITION_NONE;
		}
		MonthView   monthView = (MonthView)object;
		CalendarDay month     = monthView.getMonth();
		if (month == null)
		{
			return POSITION_NONE;
		}
		int index = m_calendarDays.indexOf(month);
		if (index < 0)
		{
			return POSITION_NONE;
		}
		return index;
	}

	@Override
	public int getCount()
	{
		return m_calendarDays.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		MonthView monthView = (MonthView)object;
		m_currentMonthViews.remove(monthView);
		container.removeView(monthView);
	}

	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}

	/**
	 * data:get/set
	 */
	public void setDecorators(List<DayViewDecorator> decorators)
	{
		this.m_decorators = decorators;
		invalidateDecorators();
	}

	public int getFirstDayOfWeek()
	{
		return m_firstDayOfTheWeek;
	}

	public void setFirstDayOfWeek(int day)
	{
		m_firstDayOfTheWeek = day;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setFirstDayOfWeek(m_firstDayOfTheWeek);
		}
	}

	public int getDateTextAppearance()
	{
		return m_dateTextAppearance == null ? 0 : m_dateTextAppearance;
	}

	public void setDateTextAppearance(int taId)
	{
		if (taId == 0)
		{
			return;
		}
		this.m_dateTextAppearance = taId;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setDateTextAppearance(taId);
		}
	}

	public int getWeekDayTextAppearance()
	{
		return m_weekDayTextAppearance == null ? 0 : m_weekDayTextAppearance;
	}

	public void setWeekDayTextAppearance(int taId)
	{
		if (taId == 0)
		{
			return;
		}
		this.m_weekDayTextAppearance = taId;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setWeekDayTextAppearance(taId);
		}
	}

	public boolean getShowOtherDates()
	{
		return m_showOtherDates;
	}

	public void setShowOtherDates(boolean show)
	{
		this.m_showOtherDates = show;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setShowOtherDates(show);
		}
	}

	public void setWeekDayFormatter(WeekDayFormatter formatter)
	{
		this.m_weekDayFormatter = formatter;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setWeekDayFormatter(formatter);
		}
	}

	public void setDayFormatter(DayFormatter formatter)
	{
		this.m_dayFormatter = formatter;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setDayFormatter(formatter);
		}
	}

	public void setDateChangeListener(MonthView.DateChangeListener dateChangeListener)
	{
		this.m_dateChangeListener = dateChangeListener;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setDateChangeListener(dateChangeListener);
		}
	}

	public void setSelectionColor(int color)
	{
		this.m_selectedColor = color;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setSelectionColor(color);
		}
	}

	public CalendarDay getItem(int position)
	{
		return m_calendarDays.get(position);
	}

	public LinkedList<MonthView> getCurrentMonthViews()
	{
		return m_currentMonthViews;
	}


	/**
	 * func:logical
	 */
	public void invalidateDecorators()
	{
		m_decoratorResults = new ArrayList<>();
		for (DayViewDecorator decorator : m_decorators)
		{
			DayViewFacade facade = new DayViewFacade();
			decorator.decorate(facade);
			if (facade.isDecorated())
			{
				m_decoratorResults.add(new DecoratorResult(decorator, facade));
			}
		}
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setDayViewDecorators(m_decoratorResults);
		}
	}

	public int getIndexForDay(CalendarDay day)
	{
		if (day == null)
		{
			return getCount() / 2;
		}
		if (m_minDate != null && day.isBefore(m_minDate))
		{
			return 0;
		}
		if (m_maxDate != null && day.isAfter(m_maxDate))
		{
			return getCount() - 1;
		}
		for (int i = 0; i < m_calendarDays.size(); i++)
		{
			CalendarDay month = m_calendarDays.get(i);
			if (day.getYear() == month.getYear() && day.getMonth() == month.getMonth())
			{
				return i;
			}
		}
		return getCount() / 2;
	}

	public void setRangeDates(CalendarDay min, CalendarDay max)
	{
		this.m_minDate = min;
		this.m_maxDate = max;
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.setMinimumDate(min);
			monthView.setMaximumDate(max);
		}

		if (min == null)
		{
			Calendar worker = CalendarUtils.getInstance();
			worker.add(Calendar.YEAR, -200);
			min = CalendarDay.from(worker);
		}

		if (max == null)
		{
			Calendar worker = CalendarUtils.getInstance();
			worker.add(Calendar.YEAR, 200);
			max = CalendarDay.from(worker);
		}

		m_calendarDays.clear();

		Calendar worker = CalendarUtils.getInstance();
		min.copyToMonthOnly(worker);
		CalendarDay workingMonth = CalendarDay.from(worker);
		while (!max.isBefore(workingMonth))
		{
			m_calendarDays.add(CalendarDay.from(worker));
			worker.add(Calendar.MONTH, 1);
			worker.set(Calendar.DAY_OF_MONTH, 1);
			workingMonth = CalendarDay.from(worker);
		}

//		CalendarDay prevDate = m_selectedDate;
		notifyDataSetChanged();
//		setSelectedDate(prevDate);
//		if (prevDate != null)
//		{
//			if (!prevDate.equals(m_selectedDate))
//			{
//				m_dateChangeListener.onDateChanged(m_selectedDate);
//			}
//		}
	}

	private CalendarDay getValidSelectedDate(CalendarDay date)
	{
		if (date == null)
		{
			return null;
		}
		if (m_minDate != null && m_minDate.isAfter(date))
		{
			return m_minDate;
		}
		if (m_maxDate != null && m_maxDate.isBefore(date))
		{
			return m_maxDate;
		}
		return date;
	}

	public CalendarDay getBeginDate()
	{
		return m_beginDate;
	}

	public void setBeginDate(CalendarDay beginDate)
	{
		m_beginDate = getValidSelectedDate(beginDate);
		updateBeginDate();
		return;
	}

	public CalendarDay getEndDate()
	{
		return m_endDate;
	}

	public void setEndDate(CalendarDay endDate)
	{
		m_endDate = getValidSelectedDate(endDate);
		updateEndDate();
	}

	private void updateBeginDate()
	{
		if (m_beginDate == null)
			return;

		//02. 同步到MonthView中
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.updateSelectedDate(m_beginDate, null);
		}

		invalidateDecorators();
		return;
	}

	public HashMap<CalendarDay, Integer> getSelectedDateHashMap()
	{
		return m_selectedDateHashMap;
	}

	private void updateEndDate()
	{
		if (m_beginDate == null)
			return;

		if (m_endDate == null)
			return;

		//01. 同步本地数据
		int beginDay = 0;
		int endDay = 0;
		for (int beginYear = m_beginDate.getYear(); beginYear <= m_endDate.getYear(); beginYear++)
		{
			for (int beginMonth = m_beginDate.getMonth(); beginMonth <= m_endDate.getMonth(); beginMonth++)
			{
				if (beginMonth == m_beginDate.getMonth())
				{
					beginDay = m_beginDate.getDay();
				}
				else
				{
					beginDay = 1;
				}
				if (beginMonth == m_endDate.getMonth())
				{
					endDay = m_endDate.getDay();
				}
				else
				{
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.MONTH, beginMonth);
					endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				}

				for (; beginDay <= endDay; beginDay++)
				{
					Calendar calendar = Calendar.getInstance();

					calendar.set(beginYear, beginMonth, beginDay);
					CalendarDay calendarDay = CalendarDay.from(calendar);
					//0:=EnumConfig.NurseServiceDayStatus.ALL.getId()
					m_selectedDateHashMap.put(calendarDay, 0);
				}
			}
		}

		//02. 同步到MonthView中
		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.updateSelectedDate(m_beginDate, m_endDate);
		}

		invalidateDecorators();
		return;
	}

	public boolean isWaitSelectBeginDate()
	{
		if (isWaitResetDate())
		{
			resetSelectedDate();
		}

		if (m_beginDate == null && m_endDate == null)
		{
			return true;
		}
		return false;
	}

	public boolean isWaitSelectEndDate()
	{
		if (m_beginDate != null && m_endDate == null)
		{
			return true;
		}
		return false;
	}

	public boolean isWaitResetDate()
	{
		if (m_beginDate != null && m_endDate != null)
		{
			return true;
		}
		return false;
	}

	public boolean isInRegion(CalendarDay calendarDay)
	{
		if (m_beginDate == null	|| m_endDate == null)
			return false;

//		return calendarDay.isInRange(m_beginDate, m_endDate);
		return m_selectedDateHashMap.containsKey(calendarDay);

	}

	public void clickSelectedDate(CalendarDay calendarDay)
	{
		if (!m_selectedDateHashMap.containsKey(calendarDay))
			return;

		int selectedType = m_selectedDateHashMap.get(calendarDay);
		//3:=EnumConfig.NurseServiceDayStatus.MAX.getID();
 		int resustType = (++selectedType) % 3;
		m_selectedDateHashMap.put(calendarDay, resustType);

		invalidateDecorators();

		return;
	}

	public void resetSelectedDate()
	{
		clearupSelectedDate();

		for (MonthView monthView : m_currentMonthViews)
		{
			monthView.resetSelectedDate();
		}

		invalidateDecorators();
	}

	public void clearupSelectedDate()
	{
		m_beginDate = null;
		m_endDate = null;
		m_selectedDateHashMap.clear();
	}

	//TODO:等待被改进
//	public CalendarDay getSelectedDate()
//	{
//		return m_selectedDate;
//	}
//
//	public void setSelectedDate(
//			@Nullable
//			CalendarDay date)
//	{
//		CalendarDay prevDate = m_selectedDate;
//		this.m_selectedDate = getValidSelectedDate(date);
//		for (MonthView monthView : m_currentMonthViews)
//		{
//			monthView.setSelectedDate(m_selectedDate);
//		}
//
//		if (date == null && prevDate != null)
//		{
//			m_dateChangeListener.onDateChanged(null);
//		}
//	}


}
