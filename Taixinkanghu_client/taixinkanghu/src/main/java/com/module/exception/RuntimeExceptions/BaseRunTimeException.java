/**
 * Copyright (c) 213Team
 *
 * @className : app.model.Exception.${type_name}
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

import com.module.exception.TxkhExceptionCode;

public class BaseRunTimeException extends RuntimeException
{
	private Integer m_iErrorCode = TxkhExceptionCode.REX_BEGIN;

	public BaseRunTimeException(Integer errorCode, String message, Throwable cause)
	{
		super(message, cause);
		this.m_iErrorCode = errorCode;
	}

	public BaseRunTimeException(Integer errorCode, Throwable cause)
	{
		this(errorCode, null, cause);
	}

	public BaseRunTimeException(Integer errorCode, String message)
	{
		this(errorCode, message, null);
	}

	public Integer getErrorCode()
	{
		return m_iErrorCode;
	}
}
