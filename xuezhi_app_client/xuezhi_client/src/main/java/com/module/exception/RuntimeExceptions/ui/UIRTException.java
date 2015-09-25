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

package com.module.exception.RuntimeExceptions.ui;

import com.module.exception.RuntimeExceptions.BaseRunTimeException;
import com.module.exception.ExceptionCode;

public class UIRTException extends BaseRunTimeException
{
	public UIRTException(Throwable cause)
	{
		super(ExceptionCode.REX_UI, cause);
	}

	public UIRTException(String message)
	{
		super(ExceptionCode.REX_UI, message);
	}

	public UIRTException(String message, Throwable cause)
	{
		super(ExceptionCode.REX_UI, message, cause);
	}
}
