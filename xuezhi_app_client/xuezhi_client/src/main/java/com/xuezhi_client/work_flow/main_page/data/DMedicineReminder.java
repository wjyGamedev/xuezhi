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
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineBox;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.util.LogicalUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DMedicineReminder
{
	private int      m_MBID               = DataConfig.DEFAULT_ID;
	private int      m_MID                = DataConfig.DEFAULT_ID;
	private Calendar m_waringTime         = Calendar.getInstance();
	private Calendar m_exhaustTime        = Calendar.getInstance();
	private String   m_exhaustTimeDisplay = "";
	private String   m_medicineName       = "";
	private boolean  mStatusFlag          = false;

	private SimpleDateFormat m_ymdSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);


	public DMedicineReminder(int MBID)
	{
		m_MBID = MBID;

		//01. VALID
		if (!checkValid(MBID))
		{
			TipsDialog.GetInstance().setMsg("MBID is invalid!![MBID:=" + MBID + "]");
			TipsDialog.GetInstance().show();
			return;
		}

		postHandler();
	}

	public static boolean checkValid(int MBID)
	{
		DMedicineBox medicineBox = DBusinessData.GetInstance().getMedicineBoxList().getMedicineBoxByID(MBID);
		if (medicineBox == null)
			return false;

		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(medicineBox.getMID());
		if (medicine == null)
			return false;

		return true;
	}


	private void postHandler()
	{
		DMedicineBox medicineBox = DBusinessData.GetInstance().getMedicineBoxList().getMedicineBoxByID(m_MBID);

		m_MID = medicineBox.getMID();
		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(medicineBox.getMID());

		double waringNum     = medicineBox.getWaringNum();
		double remianNum     = medicineBox.getRemianNum();
		double amountPerTime = medicine.getDose();
		mStatusFlag = medicineBox.isMedicalReminderState();

		ArrayList<DMedicinePrompt> medicalPrompts = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPrompts();
		for (DMedicinePrompt medicinePrompt : medicalPrompts)
		{
			if (medicinePrompt.getMID() == m_MID)
			{
				amountPerTime = medicinePrompt.getDose();
				break;
			}
		}

		Calendar resultTime = Calendar.getInstance();

		resultTime = LogicalUtil.getExhaustTime(amountPerTime, remianNum);
		m_exhaustTime = resultTime;
		m_exhaustTimeDisplay = m_ymdSDF.format(m_exhaustTime.getTime());

		if (remianNum <= waringNum)
		{
			resultTime = m_exhaustTime;
		}
		else
		{
			resultTime = LogicalUtil.getExhaustTime(amountPerTime, (remianNum - waringNum));
		}
		m_waringTime = resultTime;


		m_medicineName = medicine.getName();

	}

	public int getMBID()
	{
		return m_MBID;
	}

	public int getMID()
	{
		return m_MID;
	}

	public boolean isStatusFlag()
	{
		return mStatusFlag;
	}

	public Calendar getWaringTime()
	{
		return m_waringTime;
	}

	public Calendar getExhaustTime()
	{
		return m_exhaustTime;
	}

	public String getMedicineName()
	{
		return m_medicineName;
	}

	public String getExhaustTimeDisplay()
	{
		return m_exhaustTimeDisplay;
	}

	public boolean equalsObject(DMedicineReminder rht)
	{
		if (m_MBID != rht.getMBID())
			return false;

		if (m_MID != rht.getMID())
			return false;

		if (m_waringTime.get(Calendar.YEAR) != rht.getWaringTime().get(Calendar.YEAR))
			return false;

		if (m_waringTime.get(Calendar.MONTH) != rht.getWaringTime().get(Calendar.MONTH))
			return false;

		if (m_waringTime.get(Calendar.DAY_OF_MONTH) != rht.getWaringTime().get(Calendar.DAY_OF_MONTH))
			return false;

		if (m_waringTime.get(Calendar.HOUR_OF_DAY) != rht.getWaringTime().get(Calendar.HOUR_OF_DAY))
			return false;

		if (m_waringTime.get(Calendar.MINUTE) != rht.getWaringTime().get(Calendar.MINUTE))
			return false;

		if (m_exhaustTime.get(Calendar.YEAR) != rht.getExhaustTime().get(Calendar.YEAR))
			return false;

		if (m_exhaustTime.get(Calendar.MONTH) != rht.getExhaustTime().get(Calendar.MONTH))
			return false;

		if (m_exhaustTime.get(Calendar.DAY_OF_MONTH) != rht.getExhaustTime().get(Calendar.DAY_OF_MONTH))
			return false;

		if (m_exhaustTime.get(Calendar.HOUR_OF_DAY) != rht.getExhaustTime().get(Calendar.HOUR_OF_DAY))
			return false;

		if (m_exhaustTime.get(Calendar.MINUTE) != rht.getExhaustTime().get(Calendar.MINUTE))
			return false;

		if (m_exhaustTimeDisplay.equals(rht.getExhaustTimeDisplay()) == false)
			return false;

		if (m_medicineName.equals(rht.getMedicineName()) == false)
			return false;

		if (mStatusFlag != rht.isStatusFlag())
			return false;

		return true;
	}

	public void copyFrom(DMedicineReminder rht)
	{
		m_MBID = rht.getMBID();
		m_MID = rht.getMID();
		m_waringTime.setTime(rht.getWaringTime().getTime());
		m_exhaustTime.setTime(rht.getExhaustTime().getTime());
		m_exhaustTimeDisplay = rht.getExhaustTimeDisplay();
		m_medicineName = rht.getMedicineName();
		mStatusFlag = rht.isStatusFlag();
	}
}
