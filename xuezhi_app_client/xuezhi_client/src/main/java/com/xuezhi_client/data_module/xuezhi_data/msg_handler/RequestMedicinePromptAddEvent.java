/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/30		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.net.config.MedicinePromptConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RequestMedicinePromptAddEvent extends BaseNetEvent
{
	private String  m_UID        = null;    //用户ID
	private String  m_MID        = null;
	private String mMUID = "";
	private boolean m_valid      = true;
	private String  m_precaution = "";

	private int mNum = 1;

	private ArrayList<Double>   mDoseArrayList     = new ArrayList<>();
	private ArrayList<Calendar> mCalendarArrayList = new ArrayList<>();

	private SimpleDateFormat m_hmsSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE_SECOND);

	public RequestMedicinePromptAddEvent()
	{
		super(EventID.QUEST_ADD_MEDICAL_PROMPT);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(MedicinePromptConfig.UID, m_UID);
		sendData.put(MedicinePromptConfig.MID, m_MID);

		int tmpValid = m_valid == true ? 1 : 0;
		sendData.put(MedicinePromptConfig.VALID, String.valueOf(tmpValid));
		sendData.put(MedicinePromptConfig.PRECAUTION, String.valueOf(m_precaution));
		sendData.put(MedicinePromptConfig.UNITID, mMUID);

		String keyTime = MedicinePromptConfig.TIME;
		String keyDos  = MedicinePromptConfig.DOSE;
		String display = "";

		sendData.put(MedicinePromptConfig.NUM_PER_DAY, String.valueOf(mNum));
		for (int index = 0; index < mNum; ++index)
		{
			keyTime = MedicinePromptConfig.TIME + MedicinePromptConfig.PRE + String.valueOf(index + 1);
			keyDos = MedicinePromptConfig.DOSE + MedicinePromptConfig.PRE + String.valueOf(index + 1);

			display = m_hmsSDF.format(mCalendarArrayList.get(index).getTime());
			sendData.put(keyTime, display);

			sendData.put(keyDos, String.valueOf(mDoseArrayList.get(index)));
		}

		return sendData;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public void setMID(String MID)
	{
		m_MID = MID;

		Integer mid = 1;
		try
		{
			mid = Integer.valueOf(m_MID);
		}
		catch (NumberFormatException e)
		{
			mid = 1;
		}
		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(mid);
		if (medicine != null)
		{
			mMUID = medicine.getMUID() + "";
		}
		else
		{
			mMUID = "1";
		}
	}

	public void setNum(int num)
	{
		mNum = num;
		mDoseArrayList.clear();

		for (int index = 0; index < mNum; index++)
		{
			mDoseArrayList.add(1.0);
			Calendar today = Calendar.getInstance();
			mCalendarArrayList.add(today);
		}
	}

	public void setValid(boolean valid)
	{
		m_valid = valid;
	}

	public void setPrecaution(String precaution)
	{
		m_precaution = precaution;
	}

	public void setDose(double inputDose, int index)
	{
		if (index >= mDoseArrayList.size())
			return;

		mDoseArrayList.set(index, inputDose);
	}

	public void setTime(Calendar time, int index)
	{
		if (index >= mCalendarArrayList.size())
			return;

		Calendar calendar = mCalendarArrayList.get(index);
		calendar.setTime(time.getTime());
	}

	public void setCalendarArrayList(ArrayList<Calendar> calendarArrayList)
	{
		mCalendarArrayList = calendarArrayList;
	}

	public void setDoseArrayList(ArrayList<Double> doseArrayList)
	{
		mDoseArrayList = doseArrayList;
	}
}
