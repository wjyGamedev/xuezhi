package calendar.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import calendar.CalendarDay;
import calendar.CalendarUtils;
import calendar.config.Config;
import calendar.day.DayFormatter;
import calendar.day.DayViewDecorator;
import calendar.day.WeekDayFormatter;
import calendar.format.ArrayWeekDayFormatter;
import calendar.format.DateFormatTitleFormatter;
import calendar.format.MonthArrayTitleFormatter;
import calendar.format.TitleFormatter;
import calendar.month.MonthPagerAdapter;
import calendar.month.MonthView;

/**
 * <p>
 * This class is a calendar widget for displaying and selecting dates.
 * The range of dates supported by this calendar is configurable.
 * A user can select a date by taping on it and can page the calendar to a desired date.
 * </p>
 * <p>
 * By default, the range of dates shown is from 200 years in the past to 200 years in the future.
 * This can be extended or shortened by configuring the minimum and maximum dates.
 * </p>
 * <p>
 * When selecting a date out of range, or when the range changes so the selection becomes outside,
 * The date closest to the previous selection will become selected. This will also trigger the
 * {@linkplain OnDateChangedListener}
 * </p>
 */
public class MaterialCalendarView extends FrameLayout
{
	private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();

	//widget
	private DirectionButton   m_lastBtn               = null;    //左箭头
	private DirectionButton   m_nextBtn               = null;    //右箭头
	private TextView          m_monthTitle            = null;    //月份的TextView
	private LinearLayout      m_topMonthRegionLL      = null;    //左箭头，月份，右箭头的top bar
	private ViewPager         m_monthCalendarRegionVP = null;   //月份的日历显示区域，供翻页显示
	private MonthPagerAdapter m_monthAdapter          = null;    //月份数据容器
	private LinearLayout      m_calendarRootRegionLL  = null;   //日历的跟控件
	private MonthTitleDisplay m_monthTitleDisplay     = null;   //月份显示控件

	//logical
	private HandleDateChangeListener      m_handleDateChangeListener      = new HandleDateChangeListener();
	private HandleMonthPageChangeListener m_handleMonthPageChangeListener = new HandleMonthPageChangeListener();
	private HandleMonthPageTransformer    m_handleMonthPageTransformer    = new HandleMonthPageTransformer();
	private ClickMonthArrow               m_clickMonthArrow               = new ClickMonthArrow();

	private OnDateChangedListener  m_dateChangedListener  = null;
	private OnMonthChangedListener m_monthChangedListener = null;

	private CalendarDay                 m_currentMonth      = null;
	private ArrayList<DayViewDecorator> m_dayViewDecorators = new ArrayList<>();
	private CalendarDay                 m_minDate           = null;
	private CalendarDay                 m_maxDate           = null;

	private CalendarDay m_beginDate = null;
	private CalendarDay m_endDate = null;


	//TODO:以后用外观类替代
	private int      m_monthArrowColor = Color.BLACK;    //左右month箭头一样
	private int      m_selectedColor   = 0;
	private Drawable m_leftArrowMask   = null;
	private Drawable m_rightArrowMask  = null;


	public MaterialCalendarView(Context context)
	{
		this(context, null);
	}

	public MaterialCalendarView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		setClipChildren(false);
		setClipToPadding(false);

		init();
		initFacade(attrs);
		initData();

	}

	/**
	 * override func
	 */
	class ClickMonthArrow implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			int id           = v.getId();
			int itemPosIndex = m_monthCalendarRegionVP.getCurrentItem();
			if (id == R.id.month_right_btn)
			{
				itemPosIndex = m_monthCalendarRegionVP.getCurrentItem() + 1;
			}
			else if (id == R.id.month_left_btn)
			{
				itemPosIndex = m_monthCalendarRegionVP.getCurrentItem() - 1;
			}
			m_monthCalendarRegionVP.setCurrentItem(itemPosIndex, true);

			return;
		}
	}

	public class HandleDateChangeListener implements MonthView.DateChangeListener
	{
		@Override
		public void onDateChanged(CalendarDay date)
		{
			boolean bFlag = false;

			//01. 在已经选择范围之内，则更改类型
			if (m_monthAdapter.isInRegion(date))
			{
				m_monthAdapter.clickSelectedDate(date);
			}
			//02. 在选择范围之外，则重新选择/选择结束时间
			else
			{
				if (m_monthAdapter.isWaitSelectBeginDate())
				{
					bFlag = true;
					m_monthAdapter.setBeginDate(date);
				}
				else if (m_monthAdapter.isWaitSelectEndDate())
				{
					bFlag = true;

					CalendarDay beginDate = m_monthAdapter.getBeginDate();
					if (beginDate == null)
					{
						return;
					}

					int beginYear = beginDate.getYear();
					int beginMonth = beginDate.getMonth();
					int beginDay = beginDate.getDay();
					boolean bChangeFlag = false;

					//如果后来选择的日期，小于之前的日期，则置换两个日期。
					if (date.getYear() < beginYear)
					{
						bChangeFlag = true;
					}
					else if (date.getYear() > beginYear)
					{
						bChangeFlag = false;
					}
					else
					{
						//在year == 的情况下，查看month
						if (date.getMonth() < beginMonth)
						{
							bChangeFlag = true;
						}
						else if (date.getMonth() > beginMonth)
						{
							bChangeFlag = false;
						}
						else
						{
							//在month == 的情况下，查看day
							if (date.getDay() < beginDay)
							{
								bChangeFlag = true;
							}
							else if (date.getDay() > beginDay)
							{
								bChangeFlag = false;
							}
							else
							{
								bChangeFlag = false;
							}
						}
					}

					if (bChangeFlag)
					{
						m_monthAdapter.setBeginDate(date);
						m_monthAdapter.setEndDate(beginDate);
					}
					else
					{
						m_monthAdapter.setEndDate(date);
					}
				}
			}

			if (bFlag)
			{
				if (m_dateChangedListener != null)
				{
					m_dateChangedListener.onDateChanged(MaterialCalendarView.this, date);
				}
			}
			return;

		}

	}

	public class HandleMonthPageChangeListener implements ViewPager.OnPageChangeListener
	{
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{
		}

		@Override
		public void onPageSelected(int position)
		{
			m_monthTitleDisplay.setPreviousMonth(m_currentMonth);
			m_currentMonth = m_monthAdapter.getItem(position);

			updateUi();

			if (m_monthChangedListener != null)
			{
				m_monthChangedListener.onMonthChanged(MaterialCalendarView.this, m_currentMonth);
			}

			return;
		}

		@Override
		public void onPageScrollStateChanged(int state)
		{
		}
	}

	public class HandleMonthPageTransformer implements ViewPager.PageTransformer
	{
		@Override
		public void transformPage(View page, float position)
		{
			position = (float)Math.sqrt(1 - Math.abs(position));
			page.setAlpha(position);
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//01. 左箭头
		m_lastBtn = new DirectionButton(getContext());
		m_lastBtn.setId(R.id.month_left_btn);
		m_lastBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		m_lastBtn.setImageResource(R.drawable.mcv_action_previous);
		m_lastBtn.setOnClickListener(m_clickMonthArrow);

		//02. 右箭头
		m_nextBtn = new DirectionButton(getContext());
		m_nextBtn.setId(R.id.month_right_btn);
		m_nextBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		m_nextBtn.setImageResource(R.drawable.mcv_action_next);
		m_nextBtn.setOnClickListener(m_clickMonthArrow);

		//03. 显示月份的textview
		m_monthTitle = new TextView(getContext());
		m_monthTitle.setId(R.id.month_title_tv);
		m_monthTitle.setGravity(Gravity.CENTER);

		//04. 左箭头，月份，右箭头
		m_topMonthRegionLL = new LinearLayout(getContext());
		m_topMonthRegionLL.setId(R.id.top_month_region_ll);
		m_topMonthRegionLL.setOrientation(LinearLayout.HORIZONTAL);
		m_topMonthRegionLL.setClipChildren(false);
		m_topMonthRegionLL.setClipToPadding(false);

		//添加左/右见箭头
		LinearLayout.LayoutParams tmpMonthBtnLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		m_topMonthRegionLL.addView(m_lastBtn, tmpMonthBtnLayoutParams);
		//添加 month textview
		LinearLayout.LayoutParams tmpMonthLayoutParams = new LinearLayout.LayoutParams(0,
																					   LayoutParams.MATCH_PARENT,
																					   Config.DEFAULT_DAYS_IN_WEEK - 2
		);
		m_topMonthRegionLL.addView(m_monthTitle, tmpMonthLayoutParams);
		m_topMonthRegionLL.addView(m_nextBtn, tmpMonthBtnLayoutParams);

		//05. 日历的月份页面
		m_monthAdapter = new MonthPagerAdapter(this);
		m_monthAdapter.setDateChangeListener(m_handleDateChangeListener);

		m_monthCalendarRegionVP = new ViewPager(getContext());
		m_monthCalendarRegionVP.setId(R.id.mcv_pager);
		m_monthCalendarRegionVP.setOffscreenPageLimit(1);
		m_monthCalendarRegionVP.setAdapter(m_monthAdapter);
		m_monthCalendarRegionVP.setOnPageChangeListener(m_handleMonthPageChangeListener);
		m_monthCalendarRegionVP.setPageTransformer(false, m_handleMonthPageTransformer);

		//06. 日历跟控件
		int tileSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
													  Config.DEFAULT_TILE_SIZE_DP,
													  getResources().getDisplayMetrics()
													 );

		m_calendarRootRegionLL = new LinearLayout(getContext());
		m_calendarRootRegionLL.setOrientation(LinearLayout.VERTICAL);
		m_calendarRootRegionLL.setClipChildren(false);
		m_calendarRootRegionLL.setClipToPadding(false);
		LayoutParams p = new LayoutParams(tileSize * Config.DEFAULT_DAYS_IN_WEEK, tileSize * (Config.DEFAULT_MONTH_TILE_HEIGHT + 1)
		);
		p.gravity = Gravity.CENTER;
		//将日历的root控件，添加到该view中。
		addView(m_calendarRootRegionLL, p);
		//添加top month region
		m_calendarRootRegionLL.addView(m_topMonthRegionLL, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
		//添加 month calendar region
		m_calendarRootRegionLL.addView(m_monthCalendarRegionVP, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																							  0,
																							  Config.DEFAULT_MONTH_TILE_HEIGHT
									   )
									  );

		//07. title month and month data
		m_monthTitleDisplay = new MonthTitleDisplay(m_monthTitle);
		m_monthTitleDisplay.setTitleFormatter(DEFAULT_TITLE_FORMATTER);

	}

	private void initFacade(AttributeSet attrs)
	{
		TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialCalendarView, 0, 0);
		try
		{
			//01. 设置每个日期格子的大小
			int tileSize = a.getDimensionPixelSize(R.styleable.MaterialCalendarView_mcv_tileSize, -1);
			if (tileSize > 0)
			{
				setTileSize(tileSize);
			}

			//02. 设置month 左右箭头的sourse颜色
			setMonthArrowColor(a.getColor(R.styleable.MaterialCalendarView_mcv_arrowColor, Color.BLACK
										 )
							  );

			//03. 设置左右箭头的target image
			Drawable leftMask = a.getDrawable(R.styleable.MaterialCalendarView_mcv_leftArrowMask);
			if (leftMask == null)
			{
				leftMask = getResources().getDrawable(R.drawable.mcv_action_previous);
			}
			setLeftArrowMask(leftMask);

			Drawable rightMask = a.getDrawable(R.styleable.MaterialCalendarView_mcv_rightArrowMask);
			if (rightMask == null)
			{
				rightMask = getResources().getDrawable(R.drawable.mcv_action_next);
			}
			setRightArrowMask(rightMask);

			//04. 设置选中日历中日期的颜色
			setSelectionColor(a.getColor(R.styleable.MaterialCalendarView_mcv_selectionColor, getThemeAccentColor(getContext())
										)
							 );

			//05. 设置日期格式
			CharSequence[] array = a.getTextArray(R.styleable.MaterialCalendarView_mcv_weekDayLabels);
			if (array != null)
			{
				setWeekDayFormatter(new ArrayWeekDayFormatter(array));
			}

			//06. 设置top month format
			array = a.getTextArray(R.styleable.MaterialCalendarView_mcv_monthLabels);
			if (array != null)
			{
				setTitleFormatter(new MonthArrayTitleFormatter(array));
			}

			//07. 设置不同字体外观
			setHeaderTextAppearance(a.getResourceId(R.styleable.MaterialCalendarView_mcv_headerTextAppearance,
													R.style.TextAppearance_MaterialCalendarWidget_Header
												   )
								   );
			setWeekDayTextAppearance(a.getResourceId(R.styleable.MaterialCalendarView_mcv_weekDayTextAppearance,
													 R.style.TextAppearance_MaterialCalendarWidget_WeekDay
													)
									);
			setDateTextAppearance(a.getResourceId(R.styleable.MaterialCalendarView_mcv_dateTextAppearance,
												  R.style.TextAppearance_MaterialCalendarWidget_Date
												 )
								 );

			//08. 是否允许点击非本月的日期
			setShowOtherDates(a.getBoolean(R.styleable.MaterialCalendarView_mcv_showOtherDates, false
										  )
							 );

			//09. 给定周的第一天
			int firstDayOfWeek = a.getInt(R.styleable.MaterialCalendarView_mcv_firstDayOfWeek, -1
										 );
			if (firstDayOfWeek < 0)
			{
				firstDayOfWeek = CalendarUtils.getInstance().getFirstDayOfWeek();
			}
			setFirstDayOfWeek(firstDayOfWeek);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			a.recycle();
		}
	}

	private void initData()
	{
		m_currentMonth = CalendarDay.today();
		setCurrentDate(m_currentMonth);
		return;
	}


	/**
	 * logical data:get/set
	 */
	public OnDateChangedListener getDateChangedListener()
	{
		return m_dateChangedListener;
	}

	public void setDateChangedListener(OnDateChangedListener dateChangedListener)
	{
		m_dateChangedListener = dateChangedListener;
	}

	public OnMonthChangedListener getMonthChangedListener()
	{
		return m_monthChangedListener;
	}

	public void setMonthChangedListener(OnMonthChangedListener monthChangedListener)
	{
		m_monthChangedListener = monthChangedListener;
	}

	public MonthTitleDisplay getMonthTitleDisplay()
	{
		return m_monthTitleDisplay;
	}

	public void setMonthTitleDisplay(MonthTitleDisplay monthTitleDisplay)
	{
		m_monthTitleDisplay = monthTitleDisplay;
	}

	public CalendarDay getCurrentMonth()
	{
		return m_currentMonth;
	}

	public void setCurrentMonth(CalendarDay currentMonth)
	{
		m_currentMonth = currentMonth;
	}

	public MonthPagerAdapter getMonthAdapter()
	{
		return m_monthAdapter;
	}

	/**
	 * @return the size of tiles in pixels
	 */
	public int getTileSize()
	{
		return m_calendarRootRegionLL.getLayoutParams().width / Config.DEFAULT_DAYS_IN_WEEK;
	}

	/**
	 * Set the size of each tile that makes up the calendar.
	 * Each day is 1 tile, so the widget is 7 tiles wide and 7 or 8 tiles tall
	 * depending on the visibility of the {@link #m_topMonthRegionLL}.
	 *
	 * @param size the new size for each tile in pixels
	 */
	public void setTileSize(int size)
	{
		int height = 0;
		if (getTopbarVisible())
		{
			height = Config.DEFAULT_MONTH_TILE_HEIGHT + 1;
		}
		else
		{ // topbar.getVisibility() == View.GONE
			height = Config.DEFAULT_MONTH_TILE_HEIGHT;
		}

		LayoutParams layoutParams = new LayoutParams(size * Config.DEFAULT_DAYS_IN_WEEK, size * (height));
		layoutParams.gravity = Gravity.CENTER;

		m_calendarRootRegionLL.setLayoutParams(layoutParams);

		return;
	}

	/**
	 * @param tileSizeDp the new size for each tile in dips
	 * @see #setTileSize(int)
	 */
	public void setTileSizeDp(int tileSizeDp)
	{
		setTileSize((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tileSizeDp, getResources().getDisplayMetrics()
												  )
				   );
	}

	/**
	 * @return the color used for the selection
	 */
	public int getSelectionColor()
	{
		return m_selectedColor;
	}

	/**
	 * @param color The selection color
	 */
	public void setSelectionColor(int color)
	{
		if (color == 0)
		{
			return;
		}
		m_selectedColor = color;
		m_monthAdapter.setSelectionColor(color);
		invalidate();
	}

	/**
	 * @return color used to draw arrows
	 */
	public int getMonthArrowColor()
	{
		return m_monthArrowColor;
	}

	/**
	 * @param color the new color for the paging arrows
	 */
	public void setMonthArrowColor(int color)
	{
		if (color == 0)
		{
			return;
		}
		m_monthArrowColor = color;
		m_lastBtn.setColor(color);
		m_nextBtn.setColor(color);
		invalidate();
	}

	/**
	 * @return icon used for the left arrow
	 */
	public Drawable getLeftArrowMask()
	{
		return m_leftArrowMask;
	}

	/**
	 * @param icon the new icon to use for the left paging arrow
	 */
	public void setLeftArrowMask(Drawable icon)
	{
		m_leftArrowMask = icon;
		m_lastBtn.setImageDrawable(icon);
	}

	/**
	 * @return icon used for the right arrow
	 */
	public Drawable getRightArrowMask()
	{
		return m_rightArrowMask;
	}

	/**
	 * @param icon the new icon to use for the right paging arrow
	 */
	public void setRightArrowMask(Drawable icon)
	{
		m_rightArrowMask = icon;
		m_nextBtn.setImageDrawable(icon);
	}

	/**
	 * facade
	 */
	/**
	 * @param resourceId The text appearance resource id.
	 */
	public void setHeaderTextAppearance(int resourceId)
	{
		m_monthTitle.setTextAppearance(getContext(), resourceId);
	}

	/**
	 * @param resourceId The text appearance resource id.
	 */
	public void setDateTextAppearance(int resourceId)
	{
		m_monthAdapter.setDateTextAppearance(resourceId);
	}

	/**
	 * @param resourceId The text appearance resource id.
	 */
	public void setWeekDayTextAppearance(int resourceId)
	{
		m_monthAdapter.setWeekDayTextAppearance(resourceId);
	}

	private static int getThemeAccentColor(Context context)
	{
		int colorAttr;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			colorAttr = android.R.attr.colorAccent;
		}
		else
		{
			//Get colorAccent defined for AppCompat
			colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
		}
		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(colorAttr, outValue, true);
		return outValue.data;
	}

	/**
	 * logical func
	 */
	/**
	 * @return The current month shown, will be set to first day of the month
	 */
	public CalendarDay getCurrentDate()
	{
		return m_monthAdapter.getItem(m_monthCalendarRegionVP.getCurrentItem());
	}

	private boolean canGoForward()
	{
		return m_monthCalendarRegionVP.getCurrentItem() < (m_monthAdapter.getCount() - 1);
	}

	private boolean canGoBack()
	{
		return m_monthCalendarRegionVP.getCurrentItem() > 0;
	}

	public void updateUi()
	{
		m_monthTitleDisplay.change(m_currentMonth);
		m_lastBtn.setEnabled(canGoBack());
		m_nextBtn.setEnabled(canGoForward());
	}

	/**
	 * By default, only days of one month are shown. If this is set true,
	 * then days from the previous and next months are used to fill the empty space.
	 * This also controls showing dates outside of the min-max range.
	 *
	 * @param showOtherDates show other days, default is false
	 */
	public void setShowOtherDates(boolean showOtherDates)
	{
		m_monthAdapter.setShowOtherDates(showOtherDates);
	}

	/**
	 * @return true if days from previous or next months are shown, otherwise false.
	 */
	public boolean getShowOtherDates()
	{
		return m_monthAdapter.getShowOtherDates();
	}

	/**
	 * Sets the first day of the week.
	 * <p/>
	 * Uses the java.util.Calendar day constants.
	 *
	 * @param day The first day of the week as a java.util.Calendar day constant.
	 * @see Calendar
	 */
	public void setFirstDayOfWeek(int day)
	{
		m_monthAdapter.setFirstDayOfWeek(day);
	}

	/**
	 * @return The first day of the week as a {@linkplain Calendar} day constant.
	 */
	public int getFirstDayOfWeek()
	{
		return m_monthAdapter.getFirstDayOfWeek();
	}


	/**
	 * Add a collection of day decorators
	 *
	 * @param decorators decorators to add
	 */
	public void addDecorators(Collection<? extends DayViewDecorator> decorators)
	{
		if (decorators == null)
		{
			return;
		}

		m_dayViewDecorators.addAll(decorators);
		m_monthAdapter.setDecorators(m_dayViewDecorators);
	}

	/**
	 * Add several day decorators
	 *
	 * @param decorators decorators to add
	 */
	public void addDecorators(DayViewDecorator... decorators)
	{
		addDecorators(Arrays.asList(decorators));
	}

	/**
	 * Add a day decorator
	 *
	 * @param decorator decorator to add
	 */
	public void addDecorator(DayViewDecorator decorator)
	{
		if (decorator == null)
		{
			return;
		}
		m_dayViewDecorators.add(decorator);
		m_monthAdapter.setDecorators(m_dayViewDecorators);
	}

	/**
	 * Remove all decorators
	 */
	public void removeDecorators()
	{
		m_dayViewDecorators.clear();
		m_monthAdapter.setDecorators(m_dayViewDecorators);
	}

	/**
	 * Remove a specific decorator instance. Same rules as {@linkplain List#remove(Object)}
	 *
	 * @param decorator decorator to remove
	 */
	public void removeDecorator(DayViewDecorator decorator)
	{
		m_dayViewDecorators.remove(decorator);
		m_monthAdapter.setDecorators(m_dayViewDecorators);
	}

	/**
	 * Invalidate decorators after one has changed internally. That is, if a decorator mutates, you
	 * should call this method to update the widget.
	 */
	public void invalidateDecorators()
	{
		m_monthAdapter.invalidateDecorators();
	}

	/**
	 * @param calendar a Calendar set to a day to focus the calendar on. Null will do nothing
	 */
	public void setCurrentDate(
			@Nullable
			Calendar calendar)
	{
		setCurrentDate(CalendarDay.from(calendar));
	}

	/**
	 * @param date a Date to focus the calendar on. Null will do nothing
	 */
	public void setCurrentDate(
			@Nullable
			Date date)
	{
		setCurrentDate(CalendarDay.from(date));
	}

	/**
	 * @param day a CalendarDay to focus the calendar on. Null will do nothing
	 */
	public void setCurrentDate(
			@Nullable
			CalendarDay day)
	{
		setCurrentDate(day, true);
	}

	/**
	 * @param day a CalendarDay to focus the calendar on. Null will do nothing
	 * @param useSmoothScroll use smooth scroll when changing months.
	 */
	public void setCurrentDate(
			@Nullable
			CalendarDay day, boolean useSmoothScroll)
	{
		if (day == null)
		{
			return;
		}
		int index = m_monthAdapter.getIndexForDay(day);
		m_monthCalendarRegionVP.setCurrentItem(index, useSmoothScroll);
		updateUi();
	}

	/**
	 * logical select date
	 */
	public CalendarDay getBeginDate()
	{
		return m_monthAdapter.getBeginDate();
	}

	public void setBeginDate(CalendarDay beginDate)
	{
		m_monthAdapter.setBeginDate(beginDate);
	}

	public CalendarDay getEndDate()
	{
		return m_monthAdapter.getEndDate();
	}

	public void setEndDate(CalendarDay endDate)
	{
		m_monthAdapter.setEndDate(endDate);
	}

	public void clearupSelectedDate()
	{
		m_monthAdapter.resetSelectedDate();
	}

	public HashMap<CalendarDay, Integer> getSelectedDateHashMap()
	{
		return m_monthAdapter.getSelectedDateHashMap();
	}









	//TODO:等待被改进

//	/**
//	 * @return the currently selected day, or null if no selection
//	 */
//	public CalendarDay getSelectedDate()
//	{
//		return m_monthAdapter.getSelectedDate();
//	}


//	/**
//	 * @param calendar a Calendar set to a day to select. Null to clear selection
//	 */
//	public void setSelectedDate(
//			@Nullable
//			Calendar calendar)
//	{
//		setSelectedDate(CalendarDay.from(calendar));
//	}

//	/**
//	 * @param date a Date to set as selected. Null to clear selection
//	 */
//	public void setSelectedDate(
//			@Nullable
//			Date date)
//	{
//		setSelectedDate(CalendarDay.from(date));
//	}
//
//	/**
//	 * @param day a CalendarDay to set as selected. Null to clear selection
//	 */
//	public void setSelectedDate(
//			@Nullable
//			CalendarDay day)
//	{
//		m_monthAdapter.setSelectedDate(day);
//		setCurrentDate(day);
//	}

	private void setRangeDates(CalendarDay min, CalendarDay max)
	{
		CalendarDay c = m_currentMonth;
		m_monthAdapter.setRangeDates(min, max);
		m_currentMonth = c;
		int position = m_monthAdapter.getIndexForDay(c);
		m_monthCalendarRegionVP.setCurrentItem(position, false);
	}

	/**
	 * @return the minimum selectable date for the calendar, if any
	 */
	public CalendarDay getMinimumDate()
	{
		return m_minDate;
	}

	/**
	 * @param calendar set the minimum selectable date, null for no minimum
	 */
	public void setMinimumDate(
			@Nullable
			Calendar calendar)
	{
		setMinimumDate(CalendarDay.from(calendar));
	}

	/**
	 * @param date set the minimum selectable date, null for no minimum
	 */
	public void setMinimumDate(
			@Nullable
			Date date)
	{
		setMinimumDate(CalendarDay.from(date));
	}

	/**
	 * @param calendar set the minimum selectable date, null for no minimum
	 */
	public void setMinimumDate(
			@Nullable
			CalendarDay calendar)
	{
		m_minDate = calendar;
		setRangeDates(m_minDate, m_maxDate);
	}

	/**
	 * @return the maximum selectable date for the calendar, if any
	 */
	public CalendarDay getMaximumDate()
	{
		return m_maxDate;
	}

	/**
	 * @param calendar set the maximum selectable date, null for no maximum
	 */
	public void setMaximumDate(
			@Nullable
			Calendar calendar)
	{
		setMaximumDate(CalendarDay.from(calendar));
	}

	/**
	 * @param date set the maximum selectable date, null for no maximum
	 */
	public void setMaximumDate(
			@Nullable
			Date date)
	{
		setMaximumDate(CalendarDay.from(date));
	}

	/**
	 * @param calendar set the maximum selectable date, null for no maximum
	 */
	public void setMaximumDate(
			@Nullable
			CalendarDay calendar)
	{
		m_maxDate = calendar;
		setRangeDates(m_minDate, m_maxDate);
	}


	/**
	 * Set a formatter for weekday labels.
	 *
	 * @param formatter the new formatter, null for default
	 */
	public void setWeekDayFormatter(WeekDayFormatter formatter)
	{
		m_monthAdapter.setWeekDayFormatter(formatter == null ? WeekDayFormatter.DEFAULT : formatter);
	}

	/**
	 * Set a formatter for day labels.
	 *
	 * @param formatter the new formatter, null for default
	 */
	public void setDayFormatter(DayFormatter formatter)
	{
		m_monthAdapter.setDayFormatter(formatter == null ? DayFormatter.DEFAULT : formatter);
	}

	/**
	 * Set a {@linkplain WeekDayFormatter}
	 * with the provided week day labels
	 *
	 * @param weekDayLabels Labels to use for the days of the week
	 * @see calendar.format.ArrayWeekDayFormatter
	 * @see #setWeekDayFormatter(WeekDayFormatter)
	 */
	public void setWeekDayLabels(CharSequence[] weekDayLabels)
	{
		setWeekDayFormatter(new ArrayWeekDayFormatter(weekDayLabels));
	}

	/**
	 * Set a {@linkplain WeekDayFormatter}
	 * with the provided week day labels
	 *
	 * @param arrayRes String array resource of week day labels
	 * @see calendar.format.ArrayWeekDayFormatter
	 * @see #setWeekDayFormatter(WeekDayFormatter)
	 */
	public void setWeekDayLabels(
			@ArrayRes
			int arrayRes)
	{
		setWeekDayLabels(getResources().getTextArray(arrayRes));
	}

	/**
	 * Set a custom formatter for the month/year title
	 *
	 * @param titleFormatter new formatter to use, null to use default formatter
	 */
	public void setTitleFormatter(TitleFormatter titleFormatter)
	{
		m_monthTitleDisplay.setTitleFormatter(titleFormatter == null ? DEFAULT_TITLE_FORMATTER : titleFormatter);
		updateUi();
	}

	/**
	 * Set a {@linkplain calendar.format.TitleFormatter}
	 * using the provided month labels
	 *
	 * @param monthLabels month labels to use
	 * @see calendar.format.MonthArrayTitleFormatter
	 * @see #setTitleFormatter(calendar.format.TitleFormatter)
	 */
	public void setTitleMonths(CharSequence[] monthLabels)
	{
		setTitleFormatter(new MonthArrayTitleFormatter(monthLabels));
	}

	/**
	 * Set a {@linkplain calendar.format.TitleFormatter}
	 * using the provided month labels
	 *
	 * @param arrayRes String array resource of month labels to use
	 * @see calendar.format.MonthArrayTitleFormatter
	 * @see #setTitleFormatter(calendar.format.TitleFormatter)
	 */
	public void setTitleMonths(
			@ArrayRes
			int arrayRes)
	{
		setTitleMonths(getResources().getTextArray(arrayRes));
	}

	/**
	 * Sets the visibility {@link #m_topMonthRegionLL}, which contains
	 * the previous month button {@link #m_lastBtn}, next month button {@link #m_nextBtn},
	 * and the month title {@link #m_monthTitle}.
	 *
	 * @param visible Boolean indicating if the m_topMonthRegionLL is visible
	 */
	public void setTopbarVisible(boolean visible)
	{
		int tileSize = getTileSize();
		if (visible)
		{
			m_topMonthRegionLL.setVisibility(View.VISIBLE);
		}
		else
		{
			m_topMonthRegionLL.setVisibility(View.GONE);
		}
		setTileSize(tileSize);
	}

	/**
	 * @return true if the m_topMonthRegionLL is visible
	 */
	public boolean getTopbarVisible()
	{
		return m_topMonthRegionLL.getVisibility() == View.VISIBLE;
	}

	//TODO:之后在弄存储部分。
//	@Override
//	protected Parcelable onSaveInstanceState()
//	{
//		SavedState ss = new SavedState(super.onSaveInstanceState());
//		ss.color = getSelectionColor();
//		ss.dateTextAppearance = m_monthAdapter.getDateTextAppearance();
//		ss.weekDayTextAppearance = m_monthAdapter.getWeekDayTextAppearance();
//		ss.showOtherDates = getShowOtherDates();
//		ss.minDate = getMinimumDate();
//		ss.maxDate = getMaximumDate();
//		ss.selectedDate = getSelectedDate();
//		ss.firstDayOfWeek = getFirstDayOfWeek();
//		ss.tileSizePx = getTileSize();
//		ss.topbarVisible = getTopbarVisible();
//		return ss;
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Parcelable state)
//	{
//		SavedState ss = (SavedState)state;
//		super.onRestoreInstanceState(ss.getSuperState());
//		setSelectionColor(ss.color);
//		setDateTextAppearance(ss.dateTextAppearance);
//		setWeekDayTextAppearance(ss.weekDayTextAppearance);
//		setShowOtherDates(ss.showOtherDates);
//		setRangeDates(ss.minDate, ss.maxDate);
//		setSelectedDate(ss.selectedDate);
//		setFirstDayOfWeek(ss.firstDayOfWeek);
//		setTileSize(ss.tileSizePx);
//		setTopbarVisible(ss.topbarVisible);
//	}
//
//	@Override
//	protected void dispatchSaveInstanceState(SparseArray<Parcelable> container)
//	{
//		//super.dispatchSaveInstanceState(container);
//		super.dispatchFreezeSelfOnly(container);
//	}
//
//	@Override
//	protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container)
//	{
//		//super.dispatchRestoreInstanceState(container);
//		super.dispatchThawSelfOnly(container);
//	}
//
//
//	public static class SavedState extends BaseSavedState
//	{
//
//		int         color                 = 0;
//		int         dateTextAppearance    = 0;
//		int         weekDayTextAppearance = 0;
//		boolean     showOtherDates        = false;
//		CalendarDay minDate               = null;
//		CalendarDay maxDate               = null;
//		CalendarDay selectedDate          = null;
//		int         firstDayOfWeek        = Calendar.SUNDAY;
//		int         tileSizePx            = 0;
//		boolean     topbarVisible         = true;
//
//		SavedState(Parcelable superState)
//		{
//			super(superState);
//		}
//
//		@Override
//		public void writeToParcel(Parcel out, int flags)
//		{
//			super.writeToParcel(out, flags);
//			out.writeInt(color);
//			out.writeInt(dateTextAppearance);
//			out.writeInt(weekDayTextAppearance);
//			out.writeInt(showOtherDates ? 1 : 0);
//			out.writeParcelable(minDate, 0);
//			out.writeParcelable(maxDate, 0);
//			out.writeParcelable(selectedDate, 0);
//			out.writeInt(firstDayOfWeek);
//			out.writeInt(tileSizePx);
//			out.writeInt(topbarVisible ? 1 : 0);
//		}
//
//		public static final Creator<SavedState> CREATOR = new Creator<SavedState>()
//		{
//			public SavedState createFromParcel(Parcel in)
//			{
//				return new SavedState(in);
//			}
//
//			public SavedState[] newArray(int size)
//			{
//				return new SavedState[size];
//			}
//		};
//
//		private SavedState(Parcel in)
//		{
//			super(in);
//			color = in.readInt();
//			dateTextAppearance = in.readInt();
//			weekDayTextAppearance = in.readInt();
//			showOtherDates = in.readInt() == 1;
//			ClassLoader loader = CalendarDay.class.getClassLoader();
//			minDate = in.readParcelable(loader);
//			maxDate = in.readParcelable(loader);
//			selectedDate = in.readParcelable(loader);
//			firstDayOfWeek = in.readInt();
//			tileSizePx = in.readInt();
//			topbarVisible = in.readInt() == 1;
//		}
//	}


}

