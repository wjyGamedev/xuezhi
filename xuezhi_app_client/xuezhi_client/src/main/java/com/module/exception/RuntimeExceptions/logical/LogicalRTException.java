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

import com.module.exception.RuntimeExceptions.BaseRunTimeException;
import com.module.exception.ExceptionCode;

public class LogicalRTException extends BaseRunTimeException
{
	public LogicalRTException(Throwable cause)
	{
		super(ExceptionCode.REX_LOGICAL, cause);
	}

	public LogicalRTException(String message)
	{
		super(ExceptionCode.REX_LOGICAL, message);
	}

	public LogicalRTException(String message, Throwable cause)
	{
		super(ExceptionCode.REX_LOGICAL, message, cause);
	}
}
