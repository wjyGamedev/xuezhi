package com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePromptList;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicinePerDay;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicinePerMonth;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerDay;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerMonth;
import com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui.SelectedTakenMedicineHistoryActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MedicationDetailsMsgHandler extends BaseUIMsgHandler
{
	public MedicationDetailsMsgHandler(SelectedTakenMedicineHistoryActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;
		activity.finish();
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}

	public void go2TakenFragment()
	{

	}

	public void go2NoTakenFragment()
	{

	}
	//region 111
	public ArrayList<DTakeMedicine> getValidTakeMedicines()
	{
		ArrayList<DTakeMedicine> takeMedicinesArrayList = new ArrayList<>();

		SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;

		Calendar selectedDay = activity.getSelectedDay();
		DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																  .getMedicalHistoryBySelectedMonth(
																		  selectedDay
																								   );
		if (takeMedicinePerMonth == null)
		{
			return takeMedicinesArrayList;
		}
		else
		{
			DTakeMedicinePerDay takeMedicinePerDay = takeMedicinePerMonth.getMedicalHistoryBySelectedDay(selectedDay);
			if (takeMedicinePerDay == null)
			{
				return takeMedicinesArrayList;
			}
			else
			{
				takeMedicinesArrayList = takeMedicinePerDay.getTakeMedicines();
				return takeMedicinesArrayList;
			}
		}
	}

	public ArrayList<DNoTakeMedicine> getValidNoTakeMedicinesExcludeToday()
	{
		ArrayList<DNoTakeMedicine> noTakeMedicinesArrayList = new ArrayList<>();

		SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;

		Calendar selectedDay = activity.getSelectedDay();

		//获取本月未服用数据
		DNoTakeMedicinePerMonth noTakeMedicinePerMonth = DBusinessData.GetInstance().getNoTakeMedicineList()
																	  .getMedicalHistoryBySelectedMonth(selectedDay
																									   );
		if (noTakeMedicinePerMonth == null)
		{
			return noTakeMedicinesArrayList;
		}
		else
		{
			DNoTakeMedicinePerDay noTakeMedicinePerDay = noTakeMedicinePerMonth.getMedicalHistoryBySelectedDay(selectedDay);
			if (noTakeMedicinePerDay == null)
			{
				return noTakeMedicinesArrayList;
			}
			else
			{
				noTakeMedicinesArrayList = noTakeMedicinePerDay.getNoTakeMedicines();
				return noTakeMedicinesArrayList;
			}
		}
	}

	public ArrayList<DMedicinePrompt> getValidNoTakeMedicinesIncludeToday()
	{
		ArrayList<DMedicinePrompt> medicinePromptArrayList = new ArrayList<>();

		SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;

		Calendar selectedDay = activity.getSelectedDay();

		//获取本月未服用数据
		DNoTakeMedicinePerMonth noTakeMedicinePerMonth = DBusinessData.GetInstance().getNoTakeMedicineList()
																	  .getMedicalHistoryBySelectedMonth(selectedDay
																									   );
		if (selectedDay.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
				selectedDay.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
				selectedDay.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))//所选日期是今天
		{
			//获取用户提醒数据
			DMedicinePromptList allMedicinePromptList = DBusinessData.GetInstance().getMedicinePromptList();
			if (allMedicinePromptList != null)
			{
				ArrayList<DMedicinePrompt> medicalPromptsList = allMedicinePromptList.getMedicalPrompts();
				if (medicalPromptsList == null)
				{
					return medicinePromptArrayList;    //没有提醒，未服用可定不会有内容，返回0
				}
				else
				{

					//01. 从今天的提醒中，过滤掉本月已服务数据
					medicinePromptArrayList.clear();
					for (DMedicinePrompt medicinePrompt : medicalPromptsList)
					{
						DTakeMedicinePerMonth tmpTakeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																					 .getMedicalHistoryBySelectedMonth(selectedDay
																													  );

						if (tmpTakeMedicinePerMonth == null)
							continue;

						DTakeMedicinePerDay tmpTakeMedicinePerDay = tmpTakeMedicinePerMonth.getMedicalHistoryBySelectedDay
								(selectedDay);
						if (tmpTakeMedicinePerDay == null)
							continue;

						ArrayList<DTakeMedicine> tmpTakeMedicineArrayList = tmpTakeMedicinePerDay.getTakeMedicines();

						//获取本日已服用列表
						for (DMedicinePrompt medicalPrompt : medicalPromptsList)
						{
							boolean bFindFlag = false;
							for (DTakeMedicine takeMedicine : tmpTakeMedicineArrayList)
							{
								if (medicalPrompt.getID() == takeMedicine.getPID())
								{
									bFindFlag = true;
									break;
								}
							}
							if (!bFindFlag)
							{
								medicinePromptArrayList.add(medicalPrompt);
							}
						}

						return medicinePromptArrayList;
					}
				}
			}
			else
			{
				return medicinePromptArrayList;    //没有提醒，未服用可定不会有内容，返回0
			}
		}

		return medicinePromptArrayList;
	}

	//endregion


}
