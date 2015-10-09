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
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.net.config.MedicalPromptListConfig;
import com.xuezhi_client.net.config.MedicalStockListConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DMedicalStock
{
	private int    m_MID       = DataConfig.DEFAULT_VALUE;    //药品ID
	private int    m_UID       = DataConfig.DEFAULT_VALUE;    //用户DI
	private double m_remianNum = 0.0f;    //剩余存量
	private int    m_unitID    = DataConfig.DEFAULT_VALUE;    //药品单位的ID
	private int    m_ID        = DataConfig.DEFAULT_VALUE;        //库存ID
	private int	m_tpID       = DataConfig.DEFAULT_VALUE;        //用药提醒ID

	private boolean m_medicalReminderState = false;    //药品警报状态，true开启，false关闭


	private double m_waringNum = 0.0f;    //库存警告

	private EnumConfig.MedicalUnit m_medicalUnit = EnumConfig.MedicalUnit.MILLIGRAM;    //药品单位

	private Calendar m_addCalendar     = null;    //添加时间
	private Calendar m_warningCalendar = null;    //预警时间

	private SimpleDateFormat m_yearMonthDaySDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private final String NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance().getAppContext().getString(R.string
																											   .net_error_json_serilization);
	//TODO:每次用量
	private       double m_amountPerTime             = 0.0f;    //每次用量

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_MID = response.getInt(MedicalStockListConfig.MID);
		m_UID = response.getInt(MedicalStockListConfig.UID);
		m_remianNum = response.getDouble(MedicalStockListConfig.REMAIN);
		m_unitID = response.getInt(MedicalStockListConfig.UINTID);
		m_waringNum = response.getDouble(MedicalStockListConfig.WARNING);

		String tmpAddDate = response.getString(MedicalStockListConfig.ADDTIME);
		Date   date       = m_yearMonthDaySDF.parse(tmpAddDate);
		m_addCalendar = Calendar.getInstance();
		m_addCalendar.setTime(date);

		m_ID = response.getInt(MedicalStockListConfig.RPID);

		JSONArray jsonArray = response.getJSONArray(MedicalPromptListConfig.LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + MedicalPromptListConfig.LIST);
		}

		if (jsonArray.length() != 0)
		{
			JSONObject    jsonObject   = (JSONObject)jsonArray.get(0);
			m_tpID = jsonObject.getInt(MedicalPromptListConfig.ID);
			m_amountPerTime = jsonObject.getDouble(MedicalPromptListConfig.DOSE);
		}

		postHandler();

		return true;
	}

	private void postHandler()
	{
		double   deltaValue = m_remianNum - m_waringNum;
		Calendar today      = Calendar.getInstance();
		m_warningCalendar = Calendar.getInstance();
		if (deltaValue <= 0)
		{
			m_warningCalendar.setTime(today.getTime());
		}

		//如果每次用药剂量为0，则BUG，日期计算为今天
		if ((int)m_amountPerTime == 0)
		{
//			m_warningCalendar.setTime(today.getTime());
//			TipsDialog.GetInstance().setMsg("m_amountPerTime == 0");
//			TipsDialog.GetInstance().show();
			return;
		}

		int remainDays = (int)Math.floor(deltaValue / m_amountPerTime);
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

	public void setID(int ID)
	{
		m_ID = ID;
	}

	public int getMID()
	{
		return m_MID;
	}

	public void setMID(int MID)
	{
		m_MID = MID;
	}

	public boolean isMedicalReminderState()
	{
		return m_medicalReminderState;
	}

	public void setMedicalReminderState(boolean medicalReminderState)
	{
		m_medicalReminderState = medicalReminderState;
	}

	public double getRemianNum()
	{
		return m_remianNum;
	}

	public void setRemianNum(double remianNum)
	{
		m_remianNum = remianNum;
	}

	public double getWaringNum()
	{
		return m_waringNum;
	}

	public void setWaringNum(double waringNum)
	{
		m_waringNum = waringNum;
	}

	public EnumConfig.MedicalUnit getMedicalUnit()
	{
		return m_medicalUnit;
	}

	public void setMedicalUnit(EnumConfig.MedicalUnit medicalUnit)
	{
		m_medicalUnit = medicalUnit;
	}

	public Calendar getAddCalendar()
	{
		return m_addCalendar;
	}

	public void setAddCalendar(Calendar addCalendar)
	{
		m_addCalendar = addCalendar;
	}

	public Calendar getWarningCalendar()
	{
		return m_warningCalendar;
	}

	public void setWarningCalendar(Calendar warningCalendar)
	{
		m_warningCalendar = warningCalendar;
	}

	public double getAmountPerTime()
	{
		return m_amountPerTime;
	}

	public void setAmountPerTime(double amountPerTime)
	{
		m_amountPerTime = amountPerTime;
	}
}
