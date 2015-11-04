package com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.net.config.TakeMedicineConfig;
import com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.msg_handler.MedicationDetailsMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class SelectedTakenMedicineHistoryActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.selected_day_taken_medicine_lv) ListView m_selectedDayTakenMedicineLV = null;

	private BottomCommon m_bottomCommon = null;

	//logical
	private MedicationDetailsMsgHandler m_medicationDetailsMsgHandler = new MedicationDetailsMsgHandler(this);
	private ClickBottomBtn              m_clickBottomBtn              = new ClickBottomBtn();

	private SelectedTakenMedicineHistoryAdapter m_selectedTakenMedicineHistoryAdapter = null;
	private Calendar                            m_selectedDay                         = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_details);
		ButterKnife.bind(this);

		init();
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.medication_details_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.medication_details_bottom_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		Intent intent = this.getIntent();
		m_selectedDay = (Calendar)intent.getSerializableExtra(TakeMedicineConfig.DATE);
		if (m_selectedDay == null)
		{
			TipsDialog.GetInstance().setMsg("m_selectedDay == null", this);
			TipsDialog.GetInstance().show();
			return;
		}

		m_selectedTakenMedicineHistoryAdapter = new SelectedTakenMedicineHistoryAdapter(this);
		m_selectedDayTakenMedicineLV.setAdapter(m_selectedTakenMedicineHistoryAdapter);

	}

	private void updateContent()
	{
		m_selectedTakenMedicineHistoryAdapter.notifyDataSetChanged();
		return;
	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_medicationDetailsMsgHandler.go2MainPage();
		}
	}

}
