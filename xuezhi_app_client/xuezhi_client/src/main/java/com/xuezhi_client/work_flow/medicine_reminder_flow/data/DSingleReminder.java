/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medicine_reminder_flow.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medicine_reminder_flow.data;

import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;

import java.util.Calendar;

public class DSingleReminder
{
	public final static boolean DEFAULT_STATE_FLAG = false;
	private             boolean mStateFlag        = DEFAULT_STATE_FLAG;    //开启状态。
	private             int     mMedicineID       = DataConfig.DEFAULT_VALUE;    //药品ID
	private             String  mPrecaution       = "";    //

	private double   mDose         = 0f;    //用药剂量
	private Calendar mReminderTime = null;    //提醒时间

	public boolean init(int MPID)
	{
		DMedicinePrompt medicinePrompt = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPromptByID(MPID);
		if (medicinePrompt == null)
		{
			return false;
		}

		mStateFlag = medicinePrompt.isValid();
		mMedicineID = medicinePrompt.getMID();
		mPrecaution = medicinePrompt.getPrecaution();

		mDose = medicinePrompt.getDose();
		mReminderTime = medicinePrompt.getTakeCalendar();
		return true;
	}

	public boolean isStateFlag()
	{
		return mStateFlag;
	}

	public void setStateFlag(boolean stateFlag)
	{
		mStateFlag = stateFlag;
	}

	public int getMedicineID()
	{
		return mMedicineID;
	}

	public void setMedicineID(int medicineID)
	{
		mMedicineID = medicineID;
	}

	public String getPrecaution()
	{
		return mPrecaution;
	}

	public void setPrecaution(String precaution)
	{
		mPrecaution = precaution;
	}

	public double getDose()
	{
		return mDose;
	}

	public void setDose(double dose)
	{
		mDose = dose;
	}

	public Calendar getReminderTime()
	{
		return mReminderTime;
	}

	public void setReminderTime(Calendar reminderTime)
	{
		mReminderTime = reminderTime;
	}
}
