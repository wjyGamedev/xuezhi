/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.event.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.register_account.msg_handler;


import com.module.event.BaseNetEvent;
import com.module.event.EventID;

public class RequestRegisterEvent extends BaseNetEvent
{
	private String m_countryZipCode = null;
	private String m_phoneNum = null;
	private String m_authCode = null;

	public RequestRegisterEvent()
	{
		super(EventID.QUEST_REGISTER);
	}

	public void init(String countryZipCode, String phoneNum, String authCode)
	{
		m_countryZipCode = countryZipCode;
		m_phoneNum = phoneNum;
		m_authCode = authCode;
	}

	public String getCountryZipCode()
	{
		return m_countryZipCode;
	}

	public String getPhoneNum()
	{
		return m_phoneNum;
	}

	public String getAuthCode()
	{
		return m_authCode;
	}
}
