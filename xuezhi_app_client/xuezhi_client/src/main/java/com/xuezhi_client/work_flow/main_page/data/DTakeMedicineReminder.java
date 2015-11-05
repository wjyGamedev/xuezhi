/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.main_page.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/27		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.data;

import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DTakeMedicineReminder
{
	private int      m_PID                 = DataConfig.DEFAULT_ID;
	private int      m_MID                 = DataConfig.DEFAULT_ID;
	private String   m_medicineName        = "";
	private double   m_dose                = 0f;
	private int      m_MUID                = DataConfig.DEFAULT_ID;
	private String   m_medicineUnitDisplay = null;
	private Calendar m_reminderTime        = Calendar.getInstance();
	private String   m_reminderTimeDisplay = "";
	private boolean m_stateFlag = false;

	private SimpleDateFormat m_hmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

	public DTakeMedicineReminder(int PID)
	{
		m_PID = PID;
		if (!checkValid(PID))
		{
			TipsDialog.GetInstance().setMsg("PID is invalid!![PID:=" + PID + "]");
			TipsDialog.GetInstance().show();
			return;
		}
		postHandler();
	}

	public static boolean checkValid(int PID)
	{
		DMedicinePrompt medicinePrompt = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPromptByID(PID);
		if (medicinePrompt == null)
			return false;

		int       MID      = medicinePrompt.getMID();
		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(MID);
		if (medicine == null)
			return false;

		return true;
	}

	private void postHandler()
	{
		DMedicinePrompt medicinePrompt = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPromptByID(m_PID);
		if (medicinePrompt == null)
		{
			return;
		}

		m_MID = medicinePrompt.getMID();
		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(m_MID);
		if (medicine == null)
		{
			return;
		}

		m_medicineName = medicine.getName();
		m_dose = medicinePrompt.getDose();
		m_MUID = medicine.getMUID();

		DMedicineUnit medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(m_MUID);
		if (medicineUnit == null)
		{
			TipsDialog.GetInstance().setMsg("medicineUnit == null!m_MUID is invalid![m_MUID:="+m_MUID+"]");
			TipsDialog.GetInstance().show();
			return;
		}
		m_medicineUnitDisplay = medicineUnit.getUnitName();

		m_reminderTime = medicinePrompt.getTakeCalendar();
		m_reminderTimeDisplay = m_hmSDF.format(m_reminderTime.getTime());
		m_stateFlag = medicinePrompt.isValid();
	}

	public int getPID()
	{
		return m_PID;
	}

	public int getMID()
	{
		return m_MID;
	}

	public String getMedicineName()
	{
		return m_medicineName;
	}

	public double getDose()
	{
		return m_dose;
	}

	public int getMUID()
	{
		return m_MUID;
	}

	public String getMedicineUnitDisplay()
	{
		return m_medicineUnitDisplay;
	}

	public Calendar getReminderTime()
	{
		return m_reminderTime;
	}

	public String getReminderTimeDisplay()
	{
		return m_reminderTimeDisplay;
	}

	public boolean isStateFlag()
	{
		return m_stateFlag;
	}
}

