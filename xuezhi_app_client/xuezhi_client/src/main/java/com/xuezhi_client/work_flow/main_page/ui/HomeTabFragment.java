/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.main_page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/13		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.work_flow.main_page.config.MainConfig;
import com.xuezhi_client.work_flow.main_page.msg_handler.MainMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class HomeTabFragment extends Fragment
{
	//widget
	@Bind (R.id.today_reminder_title_tv) TextView m_todayReminderTitleTV = null;

	@Bind (R.id.reminder_header_ll)        LinearLayout m_reminderHeadersLL      = null;
	@Bind (R.id.reminder_bottom_tips_ll)   LinearLayout m_reminderBottomTipsLL   = null;
	@Bind (R.id.reminder_tips_tv)          TextView     m_reminderTipsTV         = null;
	@Bind (R.id.right_func_region_ll)      LinearLayout m_rightFuncRegionLl      = null;
	@Bind (R.id.take_medicine_reminder_iv) ImageView    m_takeMedicineReminderIV = null;

	@Bind (R.id.medicine_reminder_region_ll) LinearLayout m_medicineReminderRegionLL = null;
	@Bind (R.id.medicine_reminder_iv)        ImageView    m_medicineReminderIV       = null;


	@Bind (R.id.medicine_reminder_time_tv) TextView m_medicineReminderTimeTV = null;
	@Bind (R.id.medicine_name_tv)          TextView m_medicineNameTV         = null;
	@Bind (R.id.rose_tv)                   TextView m_roseTV                 = null;
	@Bind (R.id.medicine_unit_tv)          TextView m_medicineUnitTV         = null;
	@Bind (R.id.take_medicine_cbox)        CheckBox m_takeMedicineCBox       = null;
	@Bind (R.id.medicine_reminder_tv)      TextView m_medicineReminderTV     = null;

	@Bind (R.id.func_region_ll) LinearLayout m_funcRegionLL = null;

	//logical
	private MainMsgHandler m_mainMsgHandler = null;
	private View           m_view           = null;

	private HandleTakenSuccessEvent         m_handleTakenSuccessEvent         = new HandleTakenSuccessEvent();
	private HandleNotEnoughMedicineNumEvent m_handleNotEnoughMedicineNumEvent = new HandleNotEnoughMedicineNumEvent();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_home, container, false);
		ButterKnife.bind(this, m_view);

		init();
		initContent();
		return m_view;
	}

	@Override
	public void onStart()
	{
		updateContent();
		super.onStart();
	}


	/**
	 * widget event
	 */
	@OnClick (R.id.assay_detection_region_ll)
	public void clickAssayDetectionRegion()
	{
		m_mainMsgHandler.go2AssayDetectionAction();
		return;
	}

	@OnClick (R.id.medication_reminder_region_ll)
	public void clickMedicationReminderRegion()
	{
		m_mainMsgHandler.go2MedicationReminderAction();
		return;
	}

	@OnClick (R.id.drug_admin_region_ll)
	public void clickDrugAdminRegion()
	{
		m_mainMsgHandler.go2DrugAdminAction();
		return;
	}

	@OnClick (R.id.calendar_region_ll)
	public void clickCalendarRegion()
	{
		m_mainMsgHandler.go2CalendarAction();
		return;
	}

	@OnCheckedChanged (R.id.take_medicine_cbox)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (isChecked)
		{
			m_takeMedicineCBox.setText(R.string.take_medicine_finished);
		}
		else
		{
			m_takeMedicineCBox.setText(R.string.take_medicine_wait);
		}
	}

	@OnClick (R.id.take_medicine_cbox)
	public void clickTakeMedicineCBox()
	{
		m_mainMsgHandler.requestTakeMedicineAddAction();
	}

	@OnClick (R.id.reminder_bottom_tips_ll)
	public void clickReminderBottonRegion()
	{
		//今日无提醒
		if (m_rightFuncRegionLl.getVisibility() != View.VISIBLE)
		{
			return;
		}
		//今日有提醒，已完成
		m_mainMsgHandler.go2MedicationReminderAction();
		return;
	}

	@OnClick (R.id.right_func_region_ll)
	public void clickTakeMedicineRightFuncRegion()
	{
		//今日有提醒，已完成
		m_mainMsgHandler.go2MedicationReminderAction();
		return;
	}

	@OnClick (R.id.medicine_reminder_region_ll)
	public void clickMedicineReminderRegion()
	{
		//01. 多余一个，则打开fragment
		if (m_medicineReminderIV.getVisibility() == View.VISIBLE)
		{
			//TODO:添加fragment。
		}
		return;
	}

	/**
	 * override func
	 */
	public class HandleTakenSuccessEvent implements DialogInterface.OnClickListener
	{

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			dialog.dismiss();
		}
	}

	public class HandleNotEnoughMedicineNumEvent implements DialogInterface.OnClickListener
	{

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_mainMsgHandler.go2MedicineBoxPage();
			}
			else
			{
				dialog.dismiss();
			}
		}
	}

	/**
	 * inner func
	 */
	public void init()
	{
		Activity activity = getActivity();
		if (activity instanceof MainActivity)
		{
			MainActivity mainActivity = (MainActivity)getActivity();
			if (mainActivity == null)
			{
				TipsDialog.GetInstance().setMsg("mainActivity == null", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}
			m_mainMsgHandler = mainActivity.getMainMsgHandler();
		}
	}

	private void initContent()
	{
	}

	private void updateContent()
	{
		m_mainMsgHandler.updateHomeFragmentContent();
	}

	/**
	 * widget:get
	 */
	public TextView getTodayReminderTitleTV()
	{
		return m_todayReminderTitleTV;
	}

	public LinearLayout getReminderHeadersLL()
	{
		return m_reminderHeadersLL;
	}

	public LinearLayout getReminderBottomTipsLL()
	{
		return m_reminderBottomTipsLL;
	}

	public TextView getReminderTipsTV()
	{
		return m_reminderTipsTV;
	}

	public LinearLayout getRightFuncRegionLl()
	{
		return m_rightFuncRegionLl;
	}

	public ImageView getTakeMedicineReminderIV()
	{
		return m_takeMedicineReminderIV;
	}

	public TextView getMedicineReminderTimeTV()
	{
		return m_medicineReminderTimeTV;
	}

	public TextView getMedicineNameTV()
	{
		return m_medicineNameTV;
	}

	public TextView getRoseTV()
	{
		return m_roseTV;
	}

	public TextView getMedicineUnitTV()
	{
		return m_medicineUnitTV;
	}

	public CheckBox getTakeMedicineCBox()
	{
		return m_takeMedicineCBox;
	}

	public TextView getMedicineReminderTV()
	{
		return m_medicineReminderTV;
	}

	public LinearLayout getFuncRegionLL()
	{
		return m_funcRegionLL;
	}

	public MainMsgHandler getMainMsgHandler()
	{
		return m_mainMsgHandler;
	}

	public LinearLayout getMedicineReminderRegionLL()
	{
		return m_medicineReminderRegionLL;
	}

	public ImageView getMedicineReminderIV()
	{
		return m_medicineReminderIV;
	}

	public void popTakenDialog()
	{
		String display = getString(R.string.take_medicine_success);
		TipsDialog.GetInstance().setMsg(display, getActivity(), m_handleTakenSuccessEvent);
		TipsDialog.GetInstance().show();
		return;
	}

	public void popNotEnoughMedicineNum(int mid)
	{
		m_takeMedicineCBox.setChecked(false);
		m_takeMedicineCBox.setText(R.string.take_medicine_wait);

		TipsDialog.GetInstance().setMsg(MainConfig.ERROR_NOT_ENOUGH_MEDICINE_NUM,
										getActivity(),
										MainConfig.INFO_GO_2_MEDICINE_BOX,
										m_handleNotEnoughMedicineNumEvent,
										MainConfig.INFO_NOT_ADD,
										m_handleNotEnoughMedicineNumEvent
									   );
		TipsDialog.GetInstance().show();
	}
}
