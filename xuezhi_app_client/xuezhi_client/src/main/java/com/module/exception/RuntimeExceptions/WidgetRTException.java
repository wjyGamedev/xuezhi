/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.exception.RuntimeExceptions.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/22		WangJY		1.0.0		create
 */

package com.module.exception.RuntimeExceptions;


import com.module.exception.ExceptionCode;

public class WidgetRTException extends BaseRunTimeException
{
	public WidgetRTException(Throwable cause)
	{
		super(ExceptionCode.REX_WIDGET, cause);
	}

	public WidgetRTException(String message)
	{
		super(ExceptionCode.REX_WIDGET, message);
	}

	public WidgetRTException(String message, Throwable cause)
	{
		super(ExceptionCode.REX_WIDGET, message, cause);
	}
}

