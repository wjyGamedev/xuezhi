package calendar.month;

import calendar.day.DayViewDecorator;
import calendar.day.DayViewFacade;

public class DecoratorResult {
    public final DayViewDecorator decorator;
    public final DayViewFacade    result;

    DecoratorResult(DayViewDecorator decorator, DayViewFacade result)
    {
        this.decorator = decorator;
        this.result = result;
    }
}
