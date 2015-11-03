/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medicine_reminder_flow.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/31		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medicine_reminder_flow.data;

import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;

import java.util.Calendar;

public class DMedicineReminderDisplay
{
	private boolean  m_stateFlag     = false;    //开启状态。
	private Calendar m_remainderTime = null;    //提醒时间
	private int      m_medicineID    = DataConfig.DEFAULT_VALUE;    //药品ID
	private double   m_dose          = 0f;    //用药剂量

	public DMedicineReminderDisplay()
	{
	}

	public boolean init(int MPID)
	{
		DMedicinePrompt medicinePrompt = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPromptByID(MPID);
		if (medicinePrompt == null)
		{
			return false;
		}

		m_stateFlag = medicinePrompt.isValid();
		m_remainderTime = medicinePrompt.getTakeCalendar();
		m_medicineID = medicinePrompt.getMID();
		m_dose = medicinePrompt.getDose();
		return true;
	}


	public boolean isStateFlag()
	{
		return m_stateFlag;
	}

	public void setStateFlag(boolean stateFlag)
	{
		m_stateFlag = stateFlag;
	}

	public Calendar getRemainderTime()
	{
		return m_remainderTime;
	}

	public void setRemainderTime(Calendar remainderTime)
	{
		m_remainderTime = remainderTime;
	}

	public int getMedicineID()
	{
		return m_medicineID;
	}

	public void setMedicineID(int medicineID)
	{
		m_medicineID = medicineID;
	}

	public double getDose()
	{
		return m_dose;
	}

	public void setDose(double dose)
	{
		m_dose = dose;
	}
}
