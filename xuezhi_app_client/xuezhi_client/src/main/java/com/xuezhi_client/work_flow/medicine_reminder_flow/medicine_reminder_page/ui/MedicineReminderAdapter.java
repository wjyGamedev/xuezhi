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

package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.IBaseAdapter;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePrompt;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicinePromptList;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedicineReminderAdapter extends IBaseAdapter
{
	private DMedicinePromptList        m_medicalPromptList      = null;
	private ArrayList<DMedicinePrompt> m_medicalPromptArrayList = null;
	private LayoutInflater             m_layoutInflater         = null;

	public MedicineReminderAdapter(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		m_medicalPromptList = DBusinessData.GetInstance().getMedicinePromptList();
		m_layoutInflater = LayoutInflater.from(m_context);
	}

	@Override
	public int getCount()
	{
		m_medicalPromptList = DBusinessData.GetInstance().getMedicinePromptList();
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
		m_medicalPromptList = DBusinessData.GetInstance().getMedicinePromptList();
		if (m_medicalPromptList == null)
			return 0;

		if (position >= m_medicalPromptArrayList.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_medicalPromptArrayList.size()[position:=" + position +
													"][m_medicalPromptArrayList.size():=" +
													m_medicalPromptArrayList.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}

		DMedicinePrompt medicalPrompt = m_medicalPromptArrayList.get(position);
		return medicalPrompt.getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;

		int MPID = (int)getItemId(position);
		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_medicine_reminder, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		m_medicalPromptList = DBusinessData.GetInstance().getMedicinePromptList();
		DMedicinePrompt medicalPrompt = m_medicalPromptList.getMedicalPromptByID(MPID);
		viewHolder.initContent(medicalPrompt, MPID);
		return convertView;
	}
}

final class ViewHolder
{
	//widget
	@Bind (R.id.item_medicine_region_ll)    LinearLayout m_itemMedicineRegionLL    = null;
	@Bind (R.id.reminder_time_tv)           TextView     m_reminderTimeTV          = null;
	@Bind (R.id.medicine_reminder_state_tv) TextView     m_medicineReminderStateTV = null;
	@Bind (R.id.medicine_name_tv)           TextView     m_medicineName            = null;
	@Bind (R.id.dose_tv)                    TextView     m_doseTV                  = null;
	@Bind (R.id.medicine_uint_tv)           TextView     m_medicineUnit            = null;

	private View m_view = null;

	//logical
	private int m_MPID = DataConfig.DEFAULT_VALUE;

	private SimpleDateFormat m_hmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}

	@OnClick (R.id.item_medicine_region_ll)
	public void clickMedicineReminderItemRegion(View v)
	{
		MedicineReminderActivity activity = (MedicineReminderActivity)v.getContext();
		if (activity == null)
		{
			return;
		}

		activity.getMedicationReminderMsgHandler().go2MedicineReminderSettingPage(m_MPID);

	}

	@OnClick (R.id.medicine_reminder_delete_btn)
	public void clickMedicineReminderDeleteBtn(View v)
	{
		MedicineReminderActivity activity = (MedicineReminderActivity)v.getContext();
		if (activity == null)
		{
			return;
		}

		activity.popDeleteDialog(m_MPID);

	}


	public void initContent(DMedicinePrompt medicalPrompt, int MPID)
	{
		m_MPID = MPID;
		if (medicalPrompt == null)
		{
			TipsDialog.GetInstance().setMsg("input medicalPrompt == null");
			TipsDialog.GetInstance().show();
			return;
		}

		Calendar takeCalendar = medicalPrompt.getTakeCalendar();
		String   hmDisplay    = m_hmSDF.format(takeCalendar.getTime());
		m_reminderTimeTV.setText(hmDisplay);

		if (medicalPrompt.isValid())
		{
			m_medicineReminderStateTV.setText(R.string.medicine_reminder_add_open_content);
		}
		else
		{
			m_medicineReminderStateTV.setText(R.string.medicine_reminder_add_close_content);
		}

		int       MID      = medicalPrompt.getMID();
		DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(MID);
		if (medicine != null)
		{
			m_medicineName.setText(medicine.getName());
			DMedicineUnit medicineUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(medicine.getMUID());
			if (medicineUnit != null)
			{
				m_medicineUnit.setText(medicineUnit.getUnitName());
			}
		}

		double dose = medicalPrompt.getDose();
		m_doseTV.setText(String.valueOf(dose));

	}


}