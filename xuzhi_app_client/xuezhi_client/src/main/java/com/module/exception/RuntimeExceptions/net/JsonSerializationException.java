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
 * 2015/8/16		WangJY		1.0.0		create
 */

package com.module.exception.RuntimeExceptions.net;

public class JsonSerializationException extends NetRTException
{
	public JsonSerializationException(Throwable cause)
	{
		super(cause);
	}

	public JsonSerializationException(String message)
	{
		super(message);
	}

	public JsonSerializationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
