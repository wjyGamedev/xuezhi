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

import java.util.ArrayList;
import java.util.Calendar;

public class DMultiReminder
{
	public final static boolean DEFAULT_STATE_FLAG = false;

	private boolean mStateFlag  = DEFAULT_STATE_FLAG;    //开启状态。
	private int     mMedicineID = DataConfig.DEFAULT_VALUE;    //药品ID
	private String  mPrecaution = "";    //注意事项
	private int     mNumPerDay   = 0; //每天服药几次

	private ArrayList<Double>   mDoseArrayList         = new ArrayList<>();
	private ArrayList<Calendar> mReminderTimeArrayList = new ArrayList<>();

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

	public int getNumPerDay()
	{
		return mNumPerDay;
	}

	public void setNumPerDay(int numPerDay)
	{
		mNumPerDay = numPerDay;
	}

	public ArrayList<Double> getDoseArrayList()
	{
		return mDoseArrayList;
	}

	public void setDoseArrayList(int indexAdd, double dose)
	{
		if (indexAdd >= mDoseArrayList.size())
		{
			for (int index = mDoseArrayList.size(); index <= indexAdd ; ++index)
			{
				mDoseArrayList.add(new Double(0));
			}
		}

		mDoseArrayList.set(indexAdd, dose);
	}

	public ArrayList<Calendar> getReminderTimeArrayList()
	{
		return mReminderTimeArrayList;
	}

	public void setReminderTimeArrayList(int indexReminder, Calendar reminderTime)
	{
		if (indexReminder >= mReminderTimeArrayList.size())
		{
			for (int index = mReminderTimeArrayList.size(); index <= indexReminder ; ++index)
			{
				mReminderTimeArrayList.add(Calendar.getInstance());
			}

		}

		mReminderTimeArrayList.get(indexReminder).setTime(reminderTime.getTime());
	}
}
