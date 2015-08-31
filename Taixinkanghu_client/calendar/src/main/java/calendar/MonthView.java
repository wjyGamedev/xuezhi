package calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import calendar.format.DayFormatter;
import calendar.format.WeekDayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;

/**
 * Display a month of {@linkplain DayView}s and
 * seven {@linkplain WeekDayView}s.
 */
@SuppressLint("ViewConstructor")
public class MonthView extends LinearLayout implements View.OnClickListener {

    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 6;
    protected static final int DEFAULT_MONTH_TILE_HEIGHT = DEFAULT_MAX_WEEKS + 1;

    public interface Callbacks {

        void onDateChanged(CalendarDay date);
    }

    private Callbacks callbacks;

    private final ArrayList<WeekDayView> weekDayViews  = new ArrayList<>();
    private final ArrayList<DayView>     monthDayViews = new ArrayList<>();

    private final CalendarDay month;
    private       int         firstDayOfWeek;

    private final Calendar tempWorkingCalendar = CalendarUtils.getInstance();

    private CalendarDay            selection       = null;
    private CalendarDay            minDate         = null;
    private CalendarDay            maxDate         = null;
    //add by wangjinyu 2015-8-12
    private ArrayList<CalendarDay> m_calendarDays  = new ArrayList<>();
    private ArrayList<Integer>     m_typeArrayList = new ArrayList<>();


    private boolean showOtherDates = false;

    private final ArrayList<DecoratorResult> decoratorResults = new ArrayList<>();


    public MonthView(Context context, CalendarDay month, int firstDayOfWeek)
    {
        super(context);
        this.month = month;
        this.firstDayOfWeek = firstDayOfWeek;

        setOrientation(VERTICAL);

        setClipChildren(false);
        setClipToPadding(false);

        Calendar calendar = resetAndGetWorkingCalendar();

        LinearLayout row = makeRow(this);
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++)
        {
            WeekDayView weekDayView = new WeekDayView(context, CalendarUtils.getDayOfWeek(calendar));
            weekDayViews.add(weekDayView);
            row.addView(weekDayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
            calendar.add(DATE, 1);
        }

        calendar = resetAndGetWorkingCalendar();

        for (int r = 0; r < DEFAULT_MAX_WEEKS; r++)
        {
            row = makeRow(this);
            for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++)
            {
                CalendarDay day = CalendarDay.from(calendar);
                DayView dayView = new DayView(context, day);
                dayView.setOnClickListener(this);
                monthDayViews.add(dayView);
                row.addView(dayView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));

                calendar.add(DATE, 1);
            }
        }

        setSelectedDate(CalendarDay.today());
    }


    void setDayViewDecorators(List<DecoratorResult> results) {
        this.decoratorResults.clear();
        if(results != null) {
            this.decoratorResults.addAll(results);
        }
        invalidateDecorators();
    }

    private static LinearLayout makeRow(LinearLayout parent) {
        LinearLayout row = new LinearLayout(parent.getContext());
        row.setOrientation(HORIZONTAL);
        parent.addView(row, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
        return row;
    }

    public void setWeekDayTextAppearance(int taId) {
        for(WeekDayView weekDayView : weekDayViews) {
            weekDayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setDateTextAppearance(int taId) {
        for(DayView dayView : monthDayViews) {
            dayView.setTextAppearance(getContext(), taId);
        }
    }

    public void setShowOtherDates(boolean show) {
        this.showOtherDates = show;
        updateUi();
    }

    public boolean getShowOtherDates() {
        return showOtherDates;
    }

    public CalendarDay getMonth() {
        return month;
    }

    public void setSelectionColor(int color) {
        for(DayView dayView : monthDayViews) {
            dayView.setSelectionColor(color);
        }
    }

    private Calendar resetAndGetWorkingCalendar() {
        month.copyTo(tempWorkingCalendar);
        tempWorkingCalendar.setFirstDayOfWeek(firstDayOfWeek);
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int delta = firstDayOfWeek - dow;
        //If the delta is positive, we want to remove a week
        boolean removeRow = showOtherDates ? delta >= 0 : delta > 0;
        if(removeRow) {
            delta -= DEFAULT_DAYS_IN_WEEK;
        }
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    public void setFirstDayOfWeek(int dayOfWeek) {
        this.firstDayOfWeek = dayOfWeek;

        Calendar calendar = resetAndGetWorkingCalendar();
        calendar.set(DAY_OF_WEEK, dayOfWeek);
        for(WeekDayView dayView : weekDayViews) {
            dayView.setDayOfWeek(calendar);
            calendar.add(DATE, 1);
        }

        calendar = resetAndGetWorkingCalendar();
        for(DayView dayView : monthDayViews) {
            CalendarDay day = CalendarDay.from(calendar);
            dayView.setDay(day);
            calendar.add(DATE, 1);
        }

        updateUi();
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        for(WeekDayView dayView : weekDayViews) {
            dayView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        for(DayView dayView : monthDayViews) {
            dayView.setDayFormatter(formatter);
        }
    }

    public void setMinimumDate(CalendarDay minDate) {
        this.minDate = minDate;
        updateUi();
    }

    public void setMaximumDate(CalendarDay maxDate) {
        this.maxDate = maxDate;
        updateUi();
    }

    public void setSelectedDate(CalendarDay cal) {
        selection = cal;
        updateUi();
    }

    public void clearDateList()
    {
        m_calendarDays.clear();
        for(DayView other : monthDayViews) {
            other.setChecked(false);
        }
    }

	public void setTypeList(ArrayList<Integer> typeArrayList)
	{
		m_typeArrayList = typeArrayList;
	}

    public void loadDateList(ArrayList<CalendarDay> selectedDateList, ArrayList<Integer> typeArrayList, ArrayList<ArrayList<CalendarDay>> dateMonthList)
    {
        //01. 设置本月数据
        m_calendarDays = selectedDateList;
        m_typeArrayList = typeArrayList;

        //02. 清楚之前UI数据
        for(DayView other : monthDayViews) {
            other.setChecked(false);
        }

        //03. 画本月数据UI
        int ourMonth = month.getMonth();
		boolean bClickFlag = false;
        for(DayView dayView : monthDayViews) {
            CalendarDay day = dayView.getDate();

			for (ArrayList<CalendarDay> calendarDayArrayList : dateMonthList)
			{
				for (CalendarDay calendarDay : calendarDayArrayList)
				{
					if (calendarDay == null)
						continue;

					if (calendarDay.getDay() != day.getDay())
						continue;

					if (calendarDay.getMonth() != day.getMonth())
						continue;

					bClickFlag = true;
					break;
				}
			}

            dayView.setupSelection(showOtherDates, day.isInRange(minDate, maxDate), day.getMonth() == ourMonth);
			dayView.setClickable(bClickFlag);
			dayView.setEnabled(bClickFlag);
            boolean isChecked = false;
            for (CalendarDay calendarDay : m_calendarDays)
            {
                if (day.equals(calendarDay))
                {
                    isChecked = true;
                    break;
                }
            }
			dayView.setChecked(isChecked);
        }

        return;
    }

    public void setSelectedDateList(ArrayList<CalendarDay> selectedDateList)
    {
        m_calendarDays = selectedDateList;
        updateUiList();
    }

    private void updateUiList() {
        int ourMonth = month.getMonth();
        for(DayView dayView : monthDayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setupSelection(showOtherDates, false, day.getMonth() == ourMonth);

            boolean isChecked = false;
            for (CalendarDay calendarDay : m_calendarDays)
            {
                if (day.equals(calendarDay))
                {
                    isChecked = true;
                    break;
                }
            }
            dayView.setChecked(isChecked);
        }
        postInvalidate();
    }

    private void updateUi() {
        int ourMonth = month.getMonth();
        for(DayView dayView : monthDayViews) {
            CalendarDay day = dayView.getDate();
            dayView.setupSelection(showOtherDates, day.isInRange(minDate, maxDate), day.getMonth() == ourMonth);
            dayView.setChecked(day.equals(selection));
        }
        postInvalidate();
    }

    public void invalidateDecorators() {
        final DayViewFacade facadeAccumulator = new DayViewFacade();
        for(DayView dayView : monthDayViews) {
            facadeAccumulator.reset();
            for(DecoratorResult result : decoratorResults) {
                if(result.decorator.shouldDecorate(dayView.getDate())) {
                    result.result.applyTo(facadeAccumulator);
                }
            }
            dayView.applyFacade(facadeAccumulator);
        }
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

//    @Override
//    public void onClick(View v) {
//        if(v instanceof DayView) {
//            for(DayView other : monthDayViews) {
//                other.setChecked(false);
//            }
//            DayView dayView = (DayView) v;
//            dayView.setChecked(true);
//
//            CalendarDay date = dayView.getDate();
//            if(date.equals(selection)) {
//                return;
//            }
//            selection = date;
//
//            if(callbacks != null) {
//                callbacks.onDateChanged(dayView.getDate());
//            }
//        }
//    }

	@Override
	public void onClick(View v) {
		if(v instanceof DayView) {
			DayView dayView = (DayView) v;
			if(callbacks != null) {
				callbacks.onDateChanged(dayView.getDate());
			}
		}
	}
}
