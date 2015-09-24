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

package com.module.exception.Exceptions;


import com.module.exception.ExceptionCode;

public class BaseException
		extends Exception
{
	private Integer m_iErrorCode = ExceptionCode.EX_BEGIN;

	public BaseException(Integer errorCode, Throwable cause)
	{
		this(errorCode, null, cause);
	}

	public BaseException(String message, Throwable cause)
	{
		this(ExceptionCode.EX_BEGIN, message, cause);
	}

	public BaseException(Integer errorCode, String message, Throwable cause)
	{
		super(message, cause);
		this.m_iErrorCode = errorCode;
	}

	public Integer getErrorCode()
	{
		return m_iErrorCode;
	}

}
