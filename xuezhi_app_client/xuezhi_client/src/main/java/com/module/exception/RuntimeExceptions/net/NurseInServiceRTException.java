/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.exception.RuntimeExceptions.net.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/19		WangJY		1.0.0		create
 */

package com.module.exception.RuntimeExceptions.net;

public class NurseInServiceRTException extends NetRTException
{
	public NurseInServiceRTException(Throwable cause)
	{
		super(cause);
	}

	public NurseInServiceRTException(String message)
	{
		super(message);
	}

	public NurseInServiceRTException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
