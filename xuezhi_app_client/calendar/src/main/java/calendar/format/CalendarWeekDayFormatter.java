package calendar.format;

import java.util.Calendar;
import java.util.Locale;

import calendar.CalendarUtils;
import calendar.day.WeekDayFormatter;

/**
 * Use a {@linkplain Calendar} to get week day labels.
 *
 * @see Calendar#getDisplayName(int, int, Locale)
 */
public class CalendarWeekDayFormatter implements WeekDayFormatter
{

    private final Calendar calendar;

    /**
     * Format with a specific calendar
     *
     * @param calendar Calendar to retrieve formatting information from
     */
    public CalendarWeekDayFormatter(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Format with a default calendar
     */
    public CalendarWeekDayFormatter() {
        calendar = CalendarUtils.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(int dayOfWeek) {
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }
}
