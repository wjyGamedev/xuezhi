/**
 * Copyright (c) 213Team
 *
 * @className : app.model.Exception.RuntimeExceptions.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/6		WangJY		1.0.0		create
 */

package com.module.exception.RuntimeExceptions;

import com.module.exception.ExceptionCode;

public class ManagerRTException extends BaseRunTimeException
{
	public ManagerRTException(Throwable cause)
	{
		super(ExceptionCode.REX_MANAGER_UNIMPLEMENT, cause);
	}

	public ManagerRTException(String message)
	{
		super(ExceptionCode.REX_MANAGER_UNIMPLEMENT, message);
	}

	public ManagerRTException(String message, Throwable cause)
	{
		super(ExceptionCode.REX_MANAGER_UNIMPLEMENT, message, cause);
	}

}
