/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/28		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.net.config.MedicineBoxConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DMedicineBox
{
	private int    m_ID        = DataConfig.DEFAULT_VALUE;        //库存ID
	private int    m_UID       = DataConfig.DEFAULT_VALUE;    //用户DI
	private int    m_MID       = DataConfig.DEFAULT_VALUE;    //药品ID
	private double m_waringNum = 0.0f;    //库存警告
	private double m_remianNum = 0.0f;    //剩余存量

	private Calendar m_addCalendar     = null;    //添加时间
	private boolean m_medicalReminderState = false;    //药品警报状态，true开启，false关闭
	private Calendar m_warningCalendar = null;    //预警时间


	private SimpleDateFormat m_yearMonthDaySDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_ID = response.getInt(MedicineBoxConfig.ID);
		m_UID = response.getInt(MedicineBoxConfig.UID);
		m_MID = response.getInt(MedicineBoxConfig.MID);
		m_waringNum = response.getDouble(MedicineBoxConfig.WARNING);
		m_remianNum = response.getDouble(MedicineBoxConfig.REMAIN);

		String tmpAddDate = response.getString(MedicineBoxConfig.ADDTIME);
		Date   date       = m_yearMonthDaySDF.parse(tmpAddDate);
		m_addCalendar = Calendar.getInstance();
		m_addCalendar.setTime(date);

		int tmpmedicalReminderState = response.getInt(MedicineBoxConfig.VALID);
		if (tmpmedicalReminderState == 1)
		{
			m_medicalReminderState = true;
		}
		else
		{
			m_medicalReminderState = false;
		}

		postHandler();

		return true;
	}

	private void postHandler()
	{
		double amountPerTime = 0f; //每次用量
		DMedicine medical = DBusinessData.GetInstance().getMedicalList().getMedicalByID(m_MID);
		if (medical == null)
		{
			throw new JsonSerializationException("medical == null!m_MID is invalid![m_MID:="+m_MID+"]");
		}

		amountPerTime = medical.getRose();
		if (amountPerTime == 0)
		{
			throw new JsonSerializationException("amountPerTime == 0![m_MID:="+m_MID+"]");
		}

		double   deltaValue = m_remianNum - m_waringNum;
		Calendar today      = Calendar.getInstance();
		m_warningCalendar = Calendar.getInstance();
		if (deltaValue <= 0)
		{
			m_warningCalendar.setTime(today.getTime());
		}

		int remainDays = (int)Math.floor(deltaValue / amountPerTime);
		int todayYear  = today.get(Calendar.YEAR);
		int todayMonth = today.get(Calendar.MONTH);
		int todayDay   = today.get(Calendar.DAY_OF_MONTH);
		int maxMonths  = today.getActualMaximum(Calendar.MONTH);
		int maxDays    = 0;

		Calendar tmpCalendar = Calendar.getInstance();
		int      beginYear   = todayYear;
		int      beginMonth  = todayMonth;
		int      beginDay    = todayDay;
		for (int index = 0; index < remainDays; ++index)
		{
			//今天
			if (index == 0)
			{
				tmpCalendar.set(todayYear, todayMonth, todayDay);
				continue;
			}

			maxDays = tmpCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (beginDay >= maxDays)
			{
				beginDay = 1;
				if (beginMonth >= maxMonths)
				{
					beginMonth = 1;
					beginYear++;
				}
				else
				{
					beginMonth++;
				}
			}
			else
			{
				beginDay++;
			}

			tmpCalendar.set(beginYear, beginMonth, beginDay);
		}
		m_warningCalendar.setTime(tmpCalendar.getTime());
		return;
	}


	public int getID()
	{
		return m_ID;
	}

	public int getUID()
	{
		return m_UID;
	}

	public int getMID()
	{
		return m_MID;
	}

	public double getWaringNum()
	{
		return m_waringNum;
	}

	public double getRemianNum()
	{
		return m_remianNum;
	}

	public Calendar getAddCalendar()
	{
		return m_addCalendar;
	}

	public boolean isMedicalReminderState()
	{
		return m_medicalReminderState;
	}

	public Calendar getWarningCalendar()
	{
		return m_warningCalendar;
	}
}
