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
import android.widget.ListView;
import android.widget.TextView;

import com.module.frame.IBaseAdapter;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerDay;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerMonth;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectedTakenMedicineHistoryAdapter extends IBaseAdapter
{
	private LayoutInflater                      m_layoutInflater       = null;
	private Calendar                            m_selectedDay          = Calendar.getInstance();
	private DTakeMedicinePerMonth               m_takeMedicinePerMonth = null;
	private DTakeMedicinePerDay                 m_takeMedicinePerDay   = null;
	private ArrayList<DTakeMedicine>            m_takeMedicines        = new ArrayList<>();
	private ArrayList<ArrayList<DTakeMedicine>> m_takeMedicinePerTime  = new ArrayList<>();

	@Override
	public int getCount()
	{
		return m_takeMedicinePerTime.size();

	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (position >= m_takeMedicinePerTime.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_takeMedicinePerTime.size()[position:=" + position +
													"][m_takeMedicinePerTime.size():=" +
													m_takeMedicinePerTime.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;

		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_selected_taken_medicine_history, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		int index = (int)getItemId(position);
		if (!m_takeMedicinePerTime.isEmpty())
		{
			ArrayList<DTakeMedicine> takeMedicines = m_takeMedicinePerTime.get(index);
			viewHolder.initContent(takeMedicines, takeMedicines.get(0).getTakeCalendar(), position);
		}
		return convertView;
	}

	public SelectedTakenMedicineHistoryAdapter(Context context)
	{
		super(context);
	}

	private void setByHourMinute(DTakeMedicine takeMedicine)
	{
		Calendar targetCalendar = takeMedicine.getTakeCalendar();
		if (m_takeMedicinePerTime.isEmpty())
		{
			ArrayList<DTakeMedicine> takeMedicines = new ArrayList<>();
			takeMedicines.add(takeMedicine);
			m_takeMedicinePerTime.add(takeMedicines);
			return;
		}

		for (ArrayList<DTakeMedicine> takeMedicines1 : m_takeMedicinePerTime)
		{
			if (takeMedicines1.isEmpty())
			{
				ArrayList<DTakeMedicine> tmpTakeMedicines = new ArrayList<>();
				tmpTakeMedicines.add(takeMedicine);
				m_takeMedicinePerTime.add(tmpTakeMedicines);
				return;
			}

			DTakeMedicine takeMedicine1 = takeMedicines1.get(0);
			Calendar takeCalendar1 = takeMedicine1.getTakeCalendar();
			if (takeCalendar1.get(Calendar.HOUR_OF_DAY) != targetCalendar.get(Calendar.HOUR_OF_DAY))
				continue;

			if (takeCalendar1.get(Calendar.MINUTE) != targetCalendar.get(Calendar.MINUTE))
				continue;

			takeMedicines1.add(takeMedicine);
			return;
		}

		ArrayList<DTakeMedicine> takeMedicines = new ArrayList<>();
		takeMedicines.add(takeMedicine);
		m_takeMedicinePerTime.add(takeMedicines);
		return;
	}

	public void init(Calendar selectedDay)
	{
		m_layoutInflater = LayoutInflater.from(m_context);
		m_selectedDay = selectedDay;
		m_takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList().getMedicalHistoryBySelectedMonth(m_selectedDay
																														  );
		if (m_takeMedicinePerMonth == null)
			return;

		m_takeMedicinePerDay = m_takeMedicinePerMonth.getMedicalHistoryBySelectedDay(selectedDay);
		if (m_takeMedicinePerDay == null)
			return;

		m_takeMedicines = m_takeMedicinePerDay.getTakeMedicines();
		for (DTakeMedicine takeMedicine : m_takeMedicines)
		{
			setByHourMinute(takeMedicine);
		}
	}

}

final class ViewHolder
{
	//widget
	@Bind (R.id.count_tv)                  TextView m_countTV                = null;
	@Bind (R.id.taken_medicine_time_tv)    TextView m_takenMedicineTimeTV    = null;
	@Bind (R.id.taken_medicine_history_lv) ListView m_takenMedicineHistoryLV = null;

	private View m_view = null;

	private Calendar                                m_selectedDay                             = Calendar.getInstance();
	private SelectedTakenMedicineHistoryInfoAdapter m_selectedTakenMedicineHistoryInfoAdapter = null;

	private SimpleDateFormat m_hmSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);

	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}

	public void initContent(ArrayList<DTakeMedicine> takeMedicines, Calendar selectedDay, int position)
	{
		m_selectedDay = selectedDay;
		if (m_selectedDay == null)
		{
			TipsDialog.GetInstance().setMsg("m_selectedDay == null");
			TipsDialog.GetInstance().show();
			return;
		}
		SelectedTakenMedicineHistoryActivity activity = (SelectedTakenMedicineHistoryActivity)m_view.getContext();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("SelectedTakenMedicineHistoryActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		int    count        = position + 1;
		String countDisplay = String.valueOf(count);
		m_countTV.setText(countDisplay);

		String timeDisplay = m_hmSDF.format(selectedDay.getTime());
		m_takenMedicineTimeTV.setText(timeDisplay);

		m_selectedTakenMedicineHistoryInfoAdapter = new SelectedTakenMedicineHistoryInfoAdapter(activity);
		m_selectedTakenMedicineHistoryInfoAdapter.init(takeMedicines);
		m_takenMedicineHistoryLV.setAdapter(m_selectedTakenMedicineHistoryInfoAdapter);
		LogicalUtil.SetListViewHeightBasedOnChildren(m_takenMedicineHistoryLV);
	}
}
