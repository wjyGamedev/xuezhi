package com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DNoTakeMedicinePerMonth;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerMonth;
import com.xuezhi_client.net.config.TakeMedicineConfig;
import com.xuezhi_client.work_flow.calendar_flow.selected_day_taken_medicine_history_page.msg_handler.MedicationDetailsMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/9/22.
 */
public class SelectedTakenMedicineHistoryActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	@Bind (R.id.taken_rbtn)             RadioButton  mTakenRbtn;
	@Bind (R.id.no_taken_rbtn)          RadioButton  mNoTakenRbtn;
	@Bind (R.id.func_click_region_rgrp) RadioGroup   mFuncClickRegionRgrp;
	@Bind (R.id.taken_tv)               TextView     mTakenTv;
	@Bind (R.id.taken_arrow_iv)         ImageView    mTakenArrowIv;
	@Bind (R.id.taken_num_tv)           TextView     mTakenNumTv;
	@Bind (R.id.taken_arrow_region_fl)  FrameLayout  mTakenArrowRegionFl;
	@Bind (R.id.no_taken_tv)            TextView     mNoTakenTv;
	@Bind (R.id.no_taken_arrow_iv)      ImageView    mNoTakenArrowIv;
	@Bind (R.id.no_taken_num_tv)        TextView     mNoTakenNumTv;
	@Bind (R.id.no_taken_region_fl)     FrameLayout  mNoTakenRegionFl;
	@Bind (R.id.func_region_ll)         LinearLayout mFuncRegionLl;
	private BottomCommon m_bottomCommon = null;

	//logical
	private MedicationDetailsMsgHandler mMedicationDetailsMsgHandler = new MedicationDetailsMsgHandler(this);
	private ClickBottomBtn              m_clickBottomBtn             = new ClickBottomBtn();

	private Calendar mSelectedMonth = Calendar.getInstance();

	private SimpleDateFormat mYmdSDFF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_selected_day_taken_medicine_history);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}


	@Override
	protected void onStart()
	{
		super.onStart();
		updateContent();
	}


	@Override
	public void onDestoryAction()
	{

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			backAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	private void backAction()
	{
		finish();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.medication_details_bottom_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		Intent intent = this.getIntent();
		mSelectedMonth = (Calendar)intent.getSerializableExtra(TakeMedicineConfig.DATE);
		if (mSelectedMonth == null)
		{
			TipsDialog.GetInstance().setMsg("mSelectedMonth == null", this);
			TipsDialog.GetInstance().show();
			return;
		}
		String dateDisplay = mYmdSDFF.format(mSelectedMonth.getTime());
		String headerText  = String.format(getString(R.string.medication_details_page_title_text), dateDisplay);
		m_headerCommon.getHeaderTV().setText(headerText);

		mTakenRbtn.setChecked(true);

	}

	private void updateContent()
	{
		//已服用图标
		DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList()
																  .getMedicalHistoryBySelectedMonth(
				mSelectedMonth
																																			  );
		if (takeMedicinePerMonth == null)
		{
			mTakenArrowRegionFl.setVisibility(View.INVISIBLE);
		}
		else
		{
			if (takeMedicinePerMonth.getTakeMedicinePerDayHashMap().isEmpty())
			{
				mTakenArrowRegionFl.setVisibility(View.INVISIBLE);
			}
			else
			{
				mTakenArrowRegionFl.setVisibility(View.VISIBLE);
				mTakenNumTv.setText(String.valueOf(takeMedicinePerMonth.getTakeMedicinePerDayHashMap().size()));
			}

		}
		//未服用图标
		DNoTakeMedicinePerMonth noTakeMedicinePerMonth = DBusinessData.GetInstance().getNoTakeMedicineList()
																	  .getMedicalHistoryBySelectedMonth(
				mSelectedMonth
																																			 );
		if (noTakeMedicinePerMonth == null)
		{
			mNoTakenRegionFl.setVisibility(View.INVISIBLE);
		}
		else
		{
			if (noTakeMedicinePerMonth.getTakeMedicinePerDayHashMap().isEmpty())
			{
				mNoTakenRegionFl.setVisibility(View.INVISIBLE);
			}
			else
			{
				mNoTakenRegionFl.setVisibility(View.VISIBLE);
				mNoTakenNumTv.setText(String.valueOf(noTakeMedicinePerMonth.getTakeMedicinePerDayHashMap().size())

									 );
			}
		}

		//子fragment
		int selectedID = mFuncClickRegionRgrp.getCheckedRadioButtonId();
		if (selectedID == R.id.taken_rbtn)
		{
			mMedicationDetailsMsgHandler.go2TakenFragment();
		}
		else if (selectedID == R.id.no_taken_rbtn)
		{
			mMedicationDetailsMsgHandler.go2NoTakenFragment();
		}
		else
		{
			mMedicationDetailsMsgHandler.go2TakenFragment();
		}
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
			mMedicationDetailsMsgHandler.go2MainPage();
		}
	}

}
