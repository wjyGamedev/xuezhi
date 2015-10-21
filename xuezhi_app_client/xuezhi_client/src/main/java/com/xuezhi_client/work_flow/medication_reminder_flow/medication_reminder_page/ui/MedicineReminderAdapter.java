/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.IBaseAdapter;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalPrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalPromptList;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MedicineReminderAdapter extends IBaseAdapter
{
	private DMedicalPromptList m_medicalPromptList = null;
	private ArrayList<DMedicalPrompt> m_medicalPromptArrayList = null;
	private LayoutInflater     m_layoutInflater    = null;

	public MedicineReminderAdapter(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		m_medicalPromptList = DBusinessData.GetInstance().getMedicalPromptList();
		m_layoutInflater = LayoutInflater.from(m_context);
	}

	@Override
	public int getCount()
	{
		m_medicalPromptList = DBusinessData.GetInstance().getMedicalPromptList();
		m_medicalPromptArrayList = m_medicalPromptList.getMedicalPrompts();

		if (m_medicalPromptArrayList == null || m_medicalPromptArrayList.isEmpty())
			return 0;

		return m_medicalPromptArrayList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (m_medicalPromptList == null)
			return 0;

		if (position >= m_medicalPromptArrayList.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_medicalPromptArrayList.size()[position:=" + position + "][m_medicalPromptArrayList.size():=" +
													m_medicalPromptArrayList.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}

		DMedicalPrompt medicalPrompt = m_medicalPromptArrayList.get(position);
		return medicalPrompt.getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;

		int medicalID = (int)getItemId(position);
		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_medicine_reminder, null);
			viewHolder = new ViewHolder(convertView, medicalID);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		DMedicalPrompt medicalPrompt = m_medicalPromptList.getMedicalPromptByID(medicalID);
		viewHolder.initContent(medicalPrompt);
		return convertView;
	}
}

final class ViewHolder
{
	//widget
	@Bind (R.id.item_medicine_region_ll) LinearLayout m_itemMedicineRegionLL = null;
	@Bind (R.id.reminder_time_tv) TextView m_reminderTimeTV = null;
	@Bind (R.id.medicine_reminder_state_tv) TextView m_medicineReminderStateTV = null;
	@Bind (R.id.medicine_name_tv) TextView m_medicineName = null;
	@Bind (R.id.dose_tv) TextView m_doseTV = null;

	private View m_view = null;

	//logical
	private int m_medicalPromptID = DataConfig.DEFAULT_VALUE;


	public ViewHolder(View view, int medicalPromptID)
	{
		m_view = view;
		m_medicalPromptID = medicalPromptID;
		ButterKnife.bind(this, m_view);
	}

	public void initContent(DMedicalPrompt medicalPrompt)
	{
		if (medicalPrompt == null)
		{
			TipsDialog.GetInstance().setMsg("input medicalPrompt == null");
			TipsDialog.GetInstance().show();
			return;
		}



	}



}