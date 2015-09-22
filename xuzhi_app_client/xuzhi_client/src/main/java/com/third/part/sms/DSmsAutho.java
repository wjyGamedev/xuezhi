/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.third.party.sms.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.third.part.sms;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class DSmsAutho
{
	private static DSmsAutho s_dSmsAutho = new DSmsAutho();

	private HashMap<String, String> m_countryCodeMaps = new HashMap<String, String>();

	private DSmsAutho()
	{

	}

	public static DSmsAutho GetInstance()
	{
		return  s_dSmsAutho;
	}

	public void setCountryCodeMaps(ArrayList<HashMap<String, String>> countryCodeMaps)
	{
		for (HashMap<String, String> country : countryCodeMaps)
		{
			String code = (String) country.get(SmsConfig.COUNTRY_CODE_KEY);
			String rule = (String) country.get(SmsConfig.COUNTRY_RULE_KEY);
			if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) {
				continue;
			}
			m_countryCodeMaps.put(code, rule);
		}
	}

	public HashMap<String, String> getCountryCodeMaps()
	{
		return m_countryCodeMaps;
	}

	public boolean isSupported(String strCountryZipCode)
	{
		if (TextUtils.isEmpty(strCountryZipCode))
			return false;

		return (m_countryCodeMaps.get(strCountryZipCode) != null);
	}

}
