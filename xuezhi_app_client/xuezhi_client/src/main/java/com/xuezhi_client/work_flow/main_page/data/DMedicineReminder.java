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

		double waringNum  = medicineBox.getWaringNum();
		double remianNum = medicineBox.getRemianNum();
		double amountPerTime = medicine.getDose();

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
}
