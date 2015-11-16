/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.main_page.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/27		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.data;

import com.third.part.xinge_tengxun.XinGe;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineBox;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerDay;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerMonth;

import java.util.ArrayList;
import java.util.Calendar;

public class DWaitForRemainder
{
	//data
	private ArrayList<DTakeMedicineReminder> m_takeMedicineReminders = new ArrayList<>();
	private ArrayList<DMedicineReminder>     m_medicineReminders     = new ArrayList<>();

	public DWaitForRemainder()
	{
		updateContent();
	}

	public void updateContent()
	{
		if (DBusinessData.GetInstance().getMedicineList().getMedicals().isEmpty())
			return;

		if (DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnits().isEmpty())
			return;

		if (DBusinessData.GetInstance().getMedicineCompanyList().getMedicineCompanies().isEmpty())
			return;

		//刷新用药提醒
		updateTakeMedicineReminder();

		//刷新药箱提醒
		updateMedicineReminder();

	}


	private void updateTakeMedicineReminder()
	{
		ArrayList<DMedicinePrompt> medicinePrompts = DBusinessData.GetInstance().getMedicinePromptList().getMedicalPrompts();
		if (medicinePrompts.isEmpty())
			return;

		Calendar                   today           = Calendar.getInstance();
		DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																  .getMedicalHistoryBySelectedMonth(today
																								   );

		if (takeMedicinePerMonth == null)
			return;

		m_takeMedicineReminders.clear();
		DTakeMedicinePerDay takeMedicinePerDay = takeMedicinePerMonth.getMedicalHistoryBySelectedDay(today);
		for (DMedicinePrompt medicinePrompt : medicinePrompts)
		{
			//TODO:因为是每天用药，所以不用添加是否当日的过滤条件。
			int pid = medicinePrompt.getID();
			boolean bFindFlag = false;

			if (!DTakeMedicineReminder.checkValid(pid))
				continue;

			if (takeMedicinePerDay != null)
			{
				ArrayList<DTakeMedicine> takeMedicines = takeMedicinePerDay.getTakeMedicines();
				if (takeMedicines != null && takeMedicines.isEmpty() == false)
				{
					for (DTakeMedicine takeMedicine : takeMedicines)
					{
						if (takeMedicine.getPID() == pid)
						{
							bFindFlag = true;
							break;
						}
					}
				}
			}

			if (bFindFlag)
			{
				continue;
			}

			DTakeMedicineReminder takeMedicineReminder = new DTakeMedicineReminder(pid);
			m_takeMedicineReminders.add(takeMedicineReminder);
		}

		//添加本地用药提醒通知
		XinGe.GetInstance().readyAddLocalTakeMedicineNotification(m_takeMedicineReminders);
	}

	private void updateMedicineReminder()
	{
		ArrayList<DMedicineBox> medicineBoxes = DBusinessData.GetInstance().getMedicineBoxList().getMedicineBoxs();
		if (medicineBoxes.isEmpty())
			return;

		m_medicineReminders.clear();

		//TODO:目前没把medicinebox中的rose和用药提醒的rose关联起来。
		Calendar today      = Calendar.getInstance();
		int      todayYear  = today.get(Calendar.YEAR);
		int      todayMonth = today.get(Calendar.MONTH);
		int      todayDay   = today.get(Calendar.DAY_OF_MONTH);
		int      tmpYear    = 0;
		int      tmpMonth   = 0;
		int      tmpDay     = 0;
		for (DMedicineBox medicineBox : medicineBoxes)
		{
			int mbid = medicineBox.getID();
			if (!DMedicineReminder.checkValid(mbid))
				continue;

			DMedicineReminder medicineReminder = new DMedicineReminder(mbid);
			Calendar waringTime = medicineReminder.getWaringTime();
			tmpYear = waringTime.get(Calendar.YEAR);
			tmpMonth = waringTime.get(Calendar.MONTH);
			tmpDay = waringTime.get(Calendar.DAY_OF_MONTH);
			if (tmpYear > todayYear)
				continue;

			if (tmpYear == todayYear && tmpMonth > todayMonth)
				continue;

			if (tmpYear == todayYear && tmpMonth == todayMonth && tmpDay > todayDay)
				continue;

			m_medicineReminders.add(medicineReminder);
		}

		//添加本地药品管理通知
		XinGe.GetInstance().readyAddLocalMedicineNotification(m_medicineReminders);
	}

	public ArrayList<DTakeMedicineReminder> getTakeMedicineReminders()
	{
		return m_takeMedicineReminders;
	}

	public ArrayList<DMedicineReminder> getMedicineReminders()
	{
		return m_medicineReminders;
	}
}
