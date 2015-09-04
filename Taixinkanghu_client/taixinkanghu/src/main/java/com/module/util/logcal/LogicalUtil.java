/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.util.logcal.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.module.util.logcal;


import com.taixinkanghu_client.net.config.ProtocalConfig;

import java.util.Date;

public class LogicalUtil
{
	private final static int LENGTH_PHONE_NUM = 11;
	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNumValid(String mobiles) {
		String tmpPhoneNum = mobiles.trim();
		/*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
		String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (tmpPhoneNum.isEmpty() ||
				tmpPhoneNum.equals("") ||
				tmpPhoneNum.length() != LENGTH_PHONE_NUM)
		{
			return false;
		}
		else
		{
			return mobiles.matches(telRegex);
		}
	}

	public static int GetDayNums(Date begin, Date end)
	{
		if (begin == null || end == null)
			return 0;

		long beginTime = begin.getTime();
		long endTime = end.getTime();
		long days = Math.abs((long)(endTime - beginTime) / (24 * 60 * 60 * 1000));
		return (int)days;
	}

	public static boolean IsHttpSuccess(int httpStatus)
	{
		return (httpStatus == ProtocalConfig.HTTP_OK);
	}

}
