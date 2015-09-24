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
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.module.exception.RuntimeExceptions;


import com.module.exception.TxkhExceptionCode;

public class HandlerRTException extends BaseRunTimeException
{
	public HandlerRTException(Throwable cause)
	{
		super(TxkhExceptionCode.REX_HANDLE, cause);
	}

	public HandlerRTException(String message)
	{
		super(TxkhExceptionCode.REX_HANDLE, message);
	}

	public HandlerRTException(String message, Throwable cause)
	{
		super(TxkhExceptionCode.REX_HANDLE, message, cause);
	}
}
