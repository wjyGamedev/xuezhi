/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
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
import android.widget.TextView;

import com.module.frame.IBaseAdapter;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectedTakenMedicineHistoryInfoAdapter extends IBaseAdapter
{
	private LayoutInflater           m_layoutInflater = null;
	private ArrayList<DTakeMedicine> m_takeMedicines  = new ArrayList<>();

	@Override
	public int getCount()
	{
		return m_takeMedicines.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (position >= m_takeMedicines.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_takeMedicines.size()[position:=" + position +
													"][m_takeMedicines.size():=" +
													m_takeMedicines.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewInfoHolder viewHolder = null;

		int index = (int)getItemId(position);
		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_selected_taken_medicine_history_info, null);
			viewHolder = new ViewInfoHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewInfoHolder)convertView.getTag();
		}

		if (m_takeMedicines.isEmpty())
		{
			TipsDialog.GetInstance().setMsg("m_takeMedicines is empty");
			TipsDialog.GetInstance().show();
			return convertView;
		}

		DTakeMedicine takeMedicine = m_takeMedicines.get(index);

		viewHolder.initContent(takeMedicine);
		return convertView;
	}

	public SelectedTakenMedicineHistoryInfoAdapter(Context context)
	{
		super(context);
	}

	public void init(ArrayList<DTakeMedicine> takeMedicines)
	{
		m_layoutInflater = LayoutInflater.from(m_context);
		m_takeMedicines = takeMedicines;
	}
}

final class ViewInfoHolder
{
	@Bind (R.id.name_tv) TextView m_nameTV = null;
	@Bind (R.id.dose_tv) TextView     m_doseTV = null;

	private View m_view = null;

	private DTakeMedicine m_takeMedicine = null;

	public ViewInfoHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}

	public void initContent(DTakeMedicine takeMedicine)
	{
		m_takeMedicine = takeMedicine;

		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(takeMedicine.getMID());
		if (medicine != null)
		{
			m_nameTV.setText(medicine.getName());
		}

		m_doseTV.setText(String.valueOf(takeMedicine.getDose()));

	}

}
