/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.exception.RuntimeExceptions.logical.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.module.exception.RuntimeExceptions.logical;

public class OwnerPreferencesException extends LogicalRTException
{
	public OwnerPreferencesException(Throwable cause)
	{
		super(cause);
	}

	public OwnerPreferencesException(String message)
	{
		super(message);
	}

	public OwnerPreferencesException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
