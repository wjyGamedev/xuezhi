/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/4		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.IBaseAdapter;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineCompany;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicinePerMonth;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.msg_handler.MedicationDetailsMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectedTakenMedicineHistoryAdapter extends IBaseAdapter
{
	private LayoutInflater m_layoutInflater = null;
	private Calendar       m_selectedDay    = Calendar.getInstance();
	private boolean        mTakenFlag       = false;

	private ArrayList<DTakeMedicine>   mTakeMedicineArrayList        = new ArrayList<>();
	private ArrayList<DNoTakeMedicine> mNoTakeMedicineArrayList      = new ArrayList<>();
	private ArrayList<DMedicinePrompt> mTodayNoTakeMedicineArrayList = new ArrayList<>();

	@Override
	public int getCount()
	{
		if (mTakenFlag)
		{
			SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;
			MedicationDetailsMsgHandler msgHandler = activity.getMedicationDetailsMsgHandler();
			mTakeMedicineArrayList = msgHandler.getValidTakeMedicines();
			return mTakeMedicineArrayList.size();
		}
		else
		{
			//获取本月未服用数据
			DNoTakeMedicinePerMonth noTakeMedicinePerMonth = DBusinessData.GetInstance().getNoTakeMedicineList()
																		  .getMedicalHistoryBySelectedMonth(m_selectedDay
																										   );
			if (m_selectedDay.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
					m_selectedDay.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
					m_selectedDay.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))//所选日期是今天
			{
				SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;
				MedicationDetailsMsgHandler msgHandler = activity.getMedicationDetailsMsgHandler();
				mTodayNoTakeMedicineArrayList = msgHandler.getValidNoTakeMedicinesIncludeToday();
				return mTodayNoTakeMedicineArrayList.size();
			}
			else    //不是今天
			{
				SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_context;
				MedicationDetailsMsgHandler msgHandler = activity.getMedicationDetailsMsgHandler();
				mNoTakeMedicineArrayList = msgHandler.getValidNoTakeMedicinesExcludeToday();
				return mNoTakeMedicineArrayList.size();
			}
		}
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (mTakenFlag)
		{
			if (position >= mTakeMedicineArrayList.size())
			{
				return 0;
			}
			else
			{
				return position;
			}
		}
		else
		{
			if (m_selectedDay.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
					m_selectedDay.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
					m_selectedDay.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))//所选日期是今天
			{
				if (position >= mTodayNoTakeMedicineArrayList.size())
				{
					return 0;
				}
				else
				{
					return position;
				}

			}
			else
			{
				if (position >= mNoTakeMedicineArrayList.size())
				{
					return 0;
				}
				else
				{
					return position;
				}
			}
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;

		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_selected_taken_medicine_history_info_new, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		int index = (int)getItemId(position);
		if (mTakenFlag)
		{
			viewHolder.initTakenContent(mTakeMedicineArrayList, index);
		}
		else
		{
			if (m_selectedDay.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
					m_selectedDay.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
					m_selectedDay.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))//所选日期是今天
			{
				viewHolder.initTodayNoTakenContent(mTodayNoTakeMedicineArrayList, index);
			}
			else
			{
				viewHolder.initNoTakenContent(mNoTakeMedicineArrayList, index);
			}
		}

		return convertView;
	}

	public SelectedTakenMedicineHistoryAdapter(Context context)
	{
		super(context);
	}

	public void init(Calendar selectedDay)
	{
		m_layoutInflater = LayoutInflater.from(m_context);
		m_selectedDay = selectedDay;
	}

	public void setTaken(boolean takenFlag)
	{
		mTakenFlag = takenFlag;
	}

}

final class ViewHolder
{
	//widget
	@Bind (R.id.medicine_details_region_ll) LinearLayout mMedicineDetailsRegionLl;
	@Bind (R.id.taken_medicine_time_tv)     TextView     mTakenMedicineTimeTv;
	@Bind (R.id.name_tv)                    TextView     mNameTv;
	@Bind (R.id.dose_tv)                    TextView     mDoseTv;
	@Bind (R.id.dose_unit_tv)               TextView     mDoseUnitTv;

	private View m_view = null;

	private DTakeMedicine   mTakeMedicine        = null;
	private DNoTakeMedicine mNoTakeMedicine      = null;
	private DMedicinePrompt mTodayNoTakeMedicine = null;

	private SimpleDateFormat mHmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}

	public void initTakenContent(ArrayList<DTakeMedicine> takeMedicines, int index)
	{
		if (takeMedicines.isEmpty())
		{
			mMedicineDetailsRegionLl.setVisibility(View.INVISIBLE);
		}
		else
		{
			mMedicineDetailsRegionLl.setVisibility(View.VISIBLE);
			if (index >= takeMedicines.size())
			{
				mTakeMedicine = takeMedicines.get(0);
			}
			else
			{
				mTakeMedicine = takeMedicines.get(index);
			}

			Calendar takeCalendar = mTakeMedicine.getTakeCalendar();
			String takeDisplay = mHmSDF.format(takeCalendar.getTime());
			mTakenMedicineTimeTv.setText(takeDisplay);

			int mid = mTakeMedicine.getMID();
			DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(mid);
			String medicineName = "";
			if (medicine != null)
			{
				medicineName = medicine.getName();
				int cid = medicine.getCID();
				DMedicineCompany medicineCompany = DBusinessData.GetInstance().getMedicineCompanyList().getMedicineCompanyByID(cid);
				if (medicineCompany != null)
				{
					medicineName = medicineName + "(" + medicineCompany.getName() + ")";
				}
			}
			mNameTv.setText(medicineName);

			double dose = 0;
			if (medicine != null)
			{
				dose = medicine.getDose();
			}
			mDoseTv.setText(String.valueOf(dose));

			String unit = "";
			if (medicine != null)
			{
				int unitID = medicine.getMUID();
				DMedicineUnit medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(unitID);
				if (medicineUnit != null)
				{
					unit = medicineUnit.getUnitName();
				}
			}
			mDoseUnitTv.setText(unit);

		}
	}

	public void initNoTakenContent(ArrayList<DNoTakeMedicine> noTakeMedicines, int index)
	{
		if (noTakeMedicines.isEmpty())
		{
			mMedicineDetailsRegionLl.setVisibility(View.INVISIBLE);
		}
		else
		{
			mMedicineDetailsRegionLl.setVisibility(View.VISIBLE);
			if (index >= noTakeMedicines.size())
			{
				mNoTakeMedicine = noTakeMedicines.get(0);
			}
			else
			{
				mNoTakeMedicine = noTakeMedicines.get(index);
			}

			Calendar takeCalendar = mNoTakeMedicine.getTakeCalendar();
			String takeDisplay = mHmSDF.format(takeCalendar.getTime());
			mTakenMedicineTimeTv.setText(takeDisplay);

			int mid = mNoTakeMedicine.getMID();
			DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(mid);
			String medicineName = "";
			if (medicine != null)
			{
				medicineName = medicine.getName();
				int cid = medicine.getCID();
				DMedicineCompany medicineCompany = DBusinessData.GetInstance().getMedicineCompanyList().getMedicineCompanyByID(cid);
				if (medicineCompany != null)
				{
					medicineName = medicineName + "(" + medicineCompany.getName() + ")";
				}
			}
			mNameTv.setText(medicineName);

			double dose = 0;
			if (medicine != null)
			{
				dose = medicine.getDose();
			}
			mDoseTv.setText(String.valueOf(dose));

			String unit = "";
			if (medicine != null)
			{
				int unitID = medicine.getMUID();
				DMedicineUnit medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(unitID);
				if (medicineUnit != null)
				{
					unit = medicineUnit.getUnitName();
				}
			}
			mDoseUnitTv.setText(unit);
		}
	}

	public void initTodayNoTakenContent(ArrayList<DMedicinePrompt> todayNoTakeMedicineArrayList, int index)
	{
		if (todayNoTakeMedicineArrayList.isEmpty())
		{
			mMedicineDetailsRegionLl.setVisibility(View.INVISIBLE);
		}
		else
		{
			mMedicineDetailsRegionLl.setVisibility(View.VISIBLE);
			if (index >= todayNoTakeMedicineArrayList.size())
			{
				mTodayNoTakeMedicine = todayNoTakeMedicineArrayList.get(0);
			}
			else
			{
				mTodayNoTakeMedicine = todayNoTakeMedicineArrayList.get(index);
			}

			Calendar takeCalendar = mTodayNoTakeMedicine.getTakeCalendar();
			String takeDisplay = mHmSDF.format(takeCalendar.getTime());
			mTakenMedicineTimeTv.setText(takeDisplay);

			int mid = mTodayNoTakeMedicine.getMID();
			DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(mid);
			String medicineName = "";
			if (medicine != null)
			{
				medicineName = medicine.getName();
				int cid = medicine.getCID();
				DMedicineCompany medicineCompany = DBusinessData.GetInstance().getMedicineCompanyList().getMedicineCompanyByID(cid);
				if (medicineCompany != null)
				{
					medicineName = medicineName + "(" + medicineCompany.getName() + ")";
				}
			}
			mNameTv.setText(medicineName);

			double dose = 0;
			if (medicine != null)
			{
				dose = medicine.getDose();
			}
			mDoseTv.setText(String.valueOf(dose));

			String unit = "";
			if (medicine != null)
			{
				int unitID = medicine.getMUID();
				DMedicineUnit medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(unitID);
				if (medicineUnit != null)
				{
					unit = medicineUnit.getUnitName();
				}
			}
			mDoseUnitTv.setText(unit);
		}
	}
}
