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

import com.module.frame.IBaseAdapter;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerSelectedDay;

import java.util.ArrayList;
import java.util.Calendar;

public class SelectedTakenMedicineHistoryAdapter extends IBaseAdapter
{
	private LayoutInflater m_layoutInflater = null;
	private Calendar                            m_selectedDay = Calendar.getInstance();
	private DTakeMedicinePerSelectedDay m_takeMedicinePerSelectedDay = null;
	@Override
	public int getCount()
	{
		m_takeMedicinePerSelectedDay = DBusinessData.GetInstance().getTakeMedicineHistoryList().getMedicalHistoryBySelectedDay(m_selectedDay);
		if (m_takeMedicinePerSelectedDay == null)
			return 0;

		ArrayList<DTakeMedicine> takeMedicines = m_takeMedicinePerSelectedDay.getTakeMedicines();
		return takeMedicines.size();

	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		m_takeMedicinePerSelectedDay = DBusinessData.GetInstance().getTakeMedicineHistoryList().getMedicalHistoryBySelectedDay(m_selectedDay);
		if (m_takeMedicinePerSelectedDay == null)
			return 0;

		ArrayList<DTakeMedicine> takeMedicines = m_takeMedicinePerSelectedDay.getTakeMedicines();

		if (position >= takeMedicines.size())
		{
			TipsDialog.GetInstance().setMsg("position >= takeMedicines.size()[position:=" + position +
													"][takeMedicines.size():=" +
													takeMedicines.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}

		DTakeMedicine takeMedicine = takeMedicines.get(position);
		return takeMedicine.getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return null;
	}

	public SelectedTakenMedicineHistoryAdapter(Context context)
	{
		super(context);
	}

	public void init(Calendar selectedDay)
	{
		m_selectedDay = selectedDay;
	}

}
final class ViewHolder
{

}
