/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.main_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.msg_handler;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseUIMsgHandler;
import com.module.storage.OwnerPreferences;
import com.module.storage.StorageWrapper;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DTakeMedicinePerMonth;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineBoxGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicinePromptGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerTakeMedicineAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerTakeMedicineGetHistoryListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAssayDetectionGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicineBoxGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicinePromptGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestTakeMedicineAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestTakeMedicineGetHistoryListEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.AssayDetectionActivity;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.ui.CalenderActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui.DrugAdministrationActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui.DrugStockAddActivity;
import com.xuezhi_client.work_flow.main_page.config.MainConfig;
import com.xuezhi_client.work_flow.main_page.data.DMedicineReminder;
import com.xuezhi_client.work_flow.main_page.data.DTakeMedicineReminder;
import com.xuezhi_client.work_flow.main_page.data.DWaitForRemainder;
import com.xuezhi_client.work_flow.main_page.ui.HomeTabFragment;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuezhi_client.work_flow.main_page.ui.PersonalTabFragment;
import com.xuezhi_client.work_flow.main_page.ui.ServiceTabFragment;
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_page.ui.MedicineReminderActivity;
import com.xuezhi_client.work_flow.register_page.ui.RegisterActivity;
import com.xuezhi_client.work_flow.risk_assessment_page.ui.RiskAssessmentActivity;
import com.xuezhi_client.work_flow.user_protocal_page.ui.UserProtocalActivity;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import calendar.CalendarDay;

public class MainMsgHandler extends BaseUIMsgHandler
{

	//date
	private DWaitForRemainder m_waitForRemainder = new DWaitForRemainder();
	private DTakeMedicineReminder m_takeMedicineReminder = null; //当前显示提醒


	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
		return;
	}

	public MainMsgHandler(MainActivity activity)
	{
		super(activity);
	}

	//01. update
	private void updateMainContent()
	{
		MainActivity activity = (MainActivity)m_context;

		//01. 同步UI
		if (activity.isHomeClicked())
		{
			go2HomeFragment();
			return;
		}
		else if (activity.isPersonalClicked())
		{
			go2PersonalFragment();
			return;
		}
		else if (activity.isSerivceClicked())
		{
			go2ServiceFragment();
			return;
		}

		go2HomeFragment();

		return;

	}

	public void initAction()
	{
		//01. 药品单位
		DBusinessMsgHandler.GetInstance().requestMedicineUnitGetListAction();

		//02. 发送药品列表
		DBusinessMsgHandler.GetInstance().requestMedicineGetListAction();

		return;
	}

	public void onEventMainThread(AnswerMedicineGetListEvent event)
	{
		initActionForLogin();
	}

	private void initActionForLogin()
	{
		//TODO:由于下面消息对于(药品列表)有依赖关系。
		if (!DAccount.GetInstance().isRegisterSuccess())
		{
			//update 今日提醒：吃药，药品
			updateDWaitForRemainder();
			updateMainContent();
			return;
		}

		//0301. 请求发送化验检查列表
		requestAssayDetectionListAction();

		//0302. 请求发送用药提醒列表
//		requestMedicinePromptGetListAction();

		//0303. 请求发送药箱列表
		requestMedicineBoxGetListAction();

		//0304. 请求发送当月的日历
//		requestTakeMedicineGetHistoryListAction();

		return;
	}

	private void requestAssayDetectionListAction()
	{
		RequestAssayDetectionGetListEvent event = new RequestAssayDetectionGetListEvent();
		event.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestAssayDetectionGetListAction(event);
		return;
	}

	private void requestMedicinePromptGetListAction()
	{
		RequestMedicinePromptGetListEvent event = new RequestMedicinePromptGetListEvent();
		event.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestMedicinePromptGetListAction(event);
		return;
	}

	private void requestMedicineBoxGetListAction()
	{
		RequestMedicineBoxGetListEvent event = new RequestMedicineBoxGetListEvent();
		event.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestMedicineBoxGetListAction(event);
		return;
	}

	public void requestTakeMedicineGetHistoryListAction()
	{
		RequestTakeMedicineGetHistoryListEvent event = new RequestTakeMedicineGetHistoryListEvent();
		event.setUID(DAccount.GetInstance().getId());
		Calendar calendar = Calendar.getInstance();
		event.setCurrMonth(calendar);
		DBusinessMsgHandler.GetInstance().requestTakeMedicineGetHistoryListAction(event);
		return;
	}

	public void requestTakeMedicineAddAction()
	{
		if (m_takeMedicineReminder == null)
			return;

		RequestTakeMedicineAddEvent event = new RequestTakeMedicineAddEvent();
		event.setUID(DAccount.GetInstance().getId());
		event.setPID(String.valueOf(m_takeMedicineReminder.getPID()));
		event.setMID(String.valueOf(m_takeMedicineReminder.getMID()));
		event.setDose(m_takeMedicineReminder.getDose());
		DBusinessMsgHandler.GetInstance().requestTakeMedicineAddAction(event);
	}

	public void onEventMainThread(AnswerMedicinePromptGetListEvent event)
	{
//		updateDWaitForRemainder();
		requestTakeMedicineGetHistoryListAction();
	}

	public void onEventMainThread(AnswerMedicineBoxGetListEvent event)
	{
		requestMedicinePromptGetListAction();
	}

	public void onEventMainThread(AnswerTakeMedicineGetHistoryListEvent event)
	{
		//update 今日提醒：吃药，药品
		updateDWaitForRemainder();

		updateMainContent();
	}

	public void onEventMainThread(AnswerTakeMedicineAddEvent event)
	{
		MainActivity activity = (MainActivity)m_context;
		int httpStatus = event.getHttpStatus();
		String errorMsg = event.getErrorMsg();
		int MID = event.getMID();
		Fragment        fragment        = activity.getSupportFragmentManager().findFragmentByTag(HomeTabFragment.class.getName());
		if (fragment == null)
		{
			if (!LogicalUtil.IsHttpSuccess(httpStatus))
			{
				TipsDialog.GetInstance().setMsg(errorMsg, activity);
				TipsDialog.GetInstance().show();
			}
			return;
		}

		HomeTabFragment homeTabFragment = (HomeTabFragment)fragment;
		if (homeTabFragment == null)
		{
			if (!LogicalUtil.IsHttpSuccess(httpStatus))
			{
				TipsDialog.GetInstance().setMsg(errorMsg, activity);
				TipsDialog.GetInstance().show();
			}
			return;
		}

		//01. 成功
		if (LogicalUtil.IsHttpSuccess(httpStatus))
		{
			requestMedicineBoxGetListAction();
			homeTabFragment.popTakenDialog();
			return;
		}
		//02. 药品不足
		else if (httpStatus == -2)
		{
			homeTabFragment.popNotEnoughMedicineNum(MID);
			return;
		}
		//03. 其他BUG
		{
			TipsDialog.GetInstance().setMsg(errorMsg, activity);
			TipsDialog.GetInstance().show();
			return;
		}

	}


	private void updateDWaitForRemainder()
	{
		m_waitForRemainder.updateContent();
		return;
	}

	//02. 切换到home fragment
	public void go2HomeFragment()
	{
		MainActivity activity = (MainActivity)m_context;

		HomeTabFragment homeTabFragment = null;
		Fragment        fragment        = activity.getSupportFragmentManager().findFragmentByTag(HomeTabFragment.class.getName());
		boolean bFlag = false;
		if (fragment != null)
		{
			bFlag = true;
			homeTabFragment = (HomeTabFragment)fragment;
		}
		if (homeTabFragment == null)
		{
			homeTabFragment = new HomeTabFragment();
			bFlag = false;
		}



		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, homeTabFragment, HomeTabFragment.class.getName());
		transaction.commit();

		if (bFlag)
		{
			updateHomeFragmentContent();
		}
		return;
	}

	public void updateHomeFragmentContent()
	{
		MainActivity activity = (MainActivity)m_context;

		//关闭等待框
		AsyncWaitDialog asyncWaitDialog = activity.getAsyncWaitDialog();
		asyncWaitDialog.dismiss();

		Fragment        fragment        = activity.getSupportFragmentManager().findFragmentByTag(HomeTabFragment.class.getName());
		if (fragment == null)
			return;

		HomeTabFragment homeTabFragment = (HomeTabFragment)fragment;
		if (homeTabFragment == null)
			return;

		//01. update 用药提示
		//TODO:缺少没有提醒的处理方案layout
		ArrayList<DTakeMedicineReminder> takeMedicineReminders = m_waitForRemainder.getTakeMedicineReminders();
		Calendar                         minToday              = Calendar.getInstance();

		Calendar tmpTakeTime = Calendar.getInstance();
		//TODO:因为提醒是天天的，所以这里不需要有过滤。
		DTakeMedicineReminder nextTakeMedicineReminder = null;
		for (DTakeMedicineReminder takeMedicineReminder : takeMedicineReminders)
		{
			tmpTakeTime = takeMedicineReminder.getReminderTime();
			int hour = tmpTakeTime.get(Calendar.HOUR_OF_DAY);
			int minute = tmpTakeTime.get(Calendar.MINUTE);
			if (nextTakeMedicineReminder == null)
			{
				nextTakeMedicineReminder = takeMedicineReminder;
				minToday = nextTakeMedicineReminder.getReminderTime();
			}
			if (minToday.get(Calendar.HOUR_OF_DAY) > hour)
			{
				nextTakeMedicineReminder = takeMedicineReminder;
				minToday = nextTakeMedicineReminder.getReminderTime();
			}
			else if (minToday.get(Calendar.HOUR_OF_DAY) == hour && minToday.get(Calendar.MINUTE) > minute)
			{
				nextTakeMedicineReminder = takeMedicineReminder;
				minToday = nextTakeMedicineReminder.getReminderTime();
			}
		}

		//今日提醒有内容
		if (nextTakeMedicineReminder != null)
		{
			updateTakeMedicineReminder(homeTabFragment, MainConfig.TodayReminderState.EXIST, nextTakeMedicineReminder);
		}
		else
		{
			//今日无提醒
			DTakeMedicinePerMonth takeMedicinePerMonth = DBusinessData.GetInstance().getTakeMedicineHistoryList().getMedicalHistoryByMonth(
					Calendar.getInstance()
																																		  );
			ArrayList<DTakeMedicine> m_takeMedicines = null;
			if (takeMedicinePerMonth == null)
			{
				updateTakeMedicineReminder(homeTabFragment, MainConfig.TodayReminderState.NONE, nextTakeMedicineReminder);
			}
			//今日已点击完。
			else
			{
				m_takeMedicines = takeMedicinePerMonth.getTakeMedicines();
				if (m_takeMedicines.isEmpty())
				{
					updateTakeMedicineReminder(homeTabFragment, MainConfig.TodayReminderState.NONE, nextTakeMedicineReminder);
				}
				else
				{
					Calendar today = Calendar.getInstance();
					int todayYear = today.get(Calendar.YEAR);
					int todayMonth = today.get(Calendar.MONTH);
					int todayDay = today.get(Calendar.DAY_OF_MONTH);
					boolean bFindFlag = false;
					for (DTakeMedicine takeMedicine : m_takeMedicines)
					{
						Calendar takeCalendar = takeMedicine.getTakeCalendar();
						if (takeCalendar.get(Calendar.YEAR) == todayYear &&
								takeCalendar.get(Calendar.MONTH) == todayMonth &&
								takeCalendar.get(Calendar.DAY_OF_MONTH) == todayDay)
						{
							bFindFlag = true;
							break;
						}
					}

					if (bFindFlag)
					{
						updateTakeMedicineReminder(homeTabFragment, MainConfig.TodayReminderState.FINISHED, nextTakeMedicineReminder);
					}
					else
					{
						updateTakeMedicineReminder(homeTabFragment, MainConfig.TodayReminderState.NONE, nextTakeMedicineReminder);
					}

				}
			}

		}
		//02. update 药箱提醒
		ArrayList<DMedicineReminder> medicineReminders = m_waitForRemainder.getMedicineReminders();
		TextView medicineReminderTV = homeTabFragment.getMedicineReminderTV();
		ImageView                    medicineReminderIV = homeTabFragment.getMedicineReminderIV();
		//有
		if (medicineReminders.isEmpty() == false)
		{
			String medicineTips02 = "";
			String medicineTips04 = "";
			DMedicineReminder medicineReminder = medicineReminders.get(0);
			Calendar exhaustTime =  medicineReminder.getExhaustTime();
			CalendarDay exhaustDate = CalendarDay.from(exhaustTime);
			Calendar today = Calendar.getInstance();
			CalendarDay todayDate = CalendarDay.from(today);
			if (todayDate.getYear() > exhaustDate.getYear()	||
					(todayDate.getYear() == exhaustDate.getYear() && todayDate.getMonth() > exhaustDate.getMonth())	||
					(todayDate.getYear() == exhaustDate.getYear() && todayDate.getMonth() == exhaustDate.getMonth() && todayDate.getDay() > exhaustDate.getDay())
					)
			{
				medicineTips02 = activity.getString(R.string.medicine_waring_content_05);
				medicineTips04 = activity.getString(R.string.medicine_waring_content_06);
			}
			else
			{
				medicineTips02 = activity.getString(R.string.medicine_waring_content_02);
				medicineTips04 = activity.getString(R.string.medicine_waring_content_04);
			}

			String medicineName = medicineReminder.getMedicineName();
			String medicineTips01 = activity.getString(R.string.medicine_waring_content_01);
			String medicineTips03 = activity.getString(R.string.medicine_waring_content_03);
			String medicineReminderTimeDisplay = medicineReminder.getExhaustTimeDisplay();
			String result = "";

			int num = medicineReminders.size();
			if (num > 1)
			{
				result = medicineName + medicineTips01 + String.valueOf(num) + medicineTips02 + medicineReminderTimeDisplay + medicineTips03;
				medicineReminderIV.setVisibility(View.VISIBLE);
			}
			else
			{
				result = medicineName + medicineTips04 + medicineReminderTimeDisplay + medicineTips03;
				medicineReminderIV.setVisibility(View.INVISIBLE);
			}
			medicineReminderTV.setText(result);
		}
		//没有
		else
		{
			medicineReminderTV.setText(R.string.medicine_not_waring_content);
			medicineReminderIV.setVisibility(View.INVISIBLE);
		}
	}

	private void updateTakeMedicineReminder(HomeTabFragment homeTabFragment, MainConfig.TodayReminderState todayReminderState, DTakeMedicineReminder nextTakeMedicineReminder)
	{
		if (homeTabFragment == null)
			return;

		LinearLayout reminderHeadersLL    = homeTabFragment.getReminderHeadersLL();
		LinearLayout reminderBottomTipsLL = homeTabFragment.getReminderBottomTipsLL();
		LinearLayout m_rightFuncRegionLl  = homeTabFragment.getRightFuncRegionLl();

		TextView m_todayReminderTitleTV = homeTabFragment.getTodayReminderTitleTV();

		TextView m_reminderTipsTV = homeTabFragment.getReminderTipsTV();

		TextView medicineReminderTimeTV = homeTabFragment.getMedicineReminderTimeTV();
		TextView medicineNameTV         = homeTabFragment.getMedicineNameTV();
		TextView roseTV                 = homeTabFragment.getRoseTV();
		TextView medicineUnitTV         = homeTabFragment.getMedicineUnitTV();
		CheckBox m_takeMedicineCBox     = homeTabFragment.getTakeMedicineCBox();

		switch (todayReminderState)
		{
		case NONE:
		{
			reminderHeadersLL.setVisibility(View.GONE);
			reminderBottomTipsLL.setVisibility(View.VISIBLE);
			m_rightFuncRegionLl.setVisibility(View.VISIBLE);

			m_todayReminderTitleTV.setText(R.string.today_remind_title_no_tips);

			m_reminderTipsTV.setText(R.string.take_medication_reminder_conent_none);

		}
		return;
		case FINISHED:
		{
			reminderHeadersLL.setVisibility(View.GONE);
			reminderBottomTipsLL.setVisibility(View.VISIBLE);
			m_rightFuncRegionLl.setVisibility(View.GONE);

			m_todayReminderTitleTV.setText(R.string.today_remind_title_finished_tips);

			m_reminderTipsTV.setText(R.string.take_medicine_reminder_content_finished);
		}
		return;
		case EXIST:
		{
			if (nextTakeMedicineReminder == null)
			{
				reminderHeadersLL.setVisibility(View.GONE);
				reminderBottomTipsLL.setVisibility(View.VISIBLE);
				m_rightFuncRegionLl.setVisibility(View.VISIBLE);

				m_todayReminderTitleTV.setText(R.string.today_remind_title_no_tips);
				m_reminderTipsTV.setText(R.string.take_medication_reminder_conent_none);
				return;
			}
			m_takeMedicineReminder = nextTakeMedicineReminder;

			reminderHeadersLL.setVisibility(View.VISIBLE);
			reminderBottomTipsLL.setVisibility(View.GONE);
			m_rightFuncRegionLl.setVisibility(View.GONE);

			m_todayReminderTitleTV.setText(R.string.today_remind_title_text);

			medicineReminderTimeTV.setText(nextTakeMedicineReminder.getReminderTimeDisplay());
			medicineNameTV.setText(nextTakeMedicineReminder.getMedicineName());
			double tmpRose = nextTakeMedicineReminder.getDose();
			roseTV.setText(String.valueOf(tmpRose));
			medicineUnitTV.setText(nextTakeMedicineReminder.getMedicineUnitDisplay());

			m_takeMedicineCBox.setChecked(false);
			m_takeMedicineCBox.setText(R.string.take_medicine_wait);

		}
		return;
		default:
		{
			if (nextTakeMedicineReminder == null)
			{
				reminderHeadersLL.setVisibility(View.GONE);
				reminderBottomTipsLL.setVisibility(View.VISIBLE);
				m_rightFuncRegionLl.setVisibility(View.VISIBLE);

				m_todayReminderTitleTV.setText(R.string.today_remind_title_no_tips);
				m_reminderTipsTV.setText(R.string.take_medication_reminder_conent_none);
				return;
			}
			m_takeMedicineReminder = nextTakeMedicineReminder;

			reminderHeadersLL.setVisibility(View.VISIBLE);
			reminderBottomTipsLL.setVisibility(View.GONE);
			m_rightFuncRegionLl.setVisibility(View.GONE);

			m_todayReminderTitleTV.setText(R.string.today_remind_title_text);

			medicineReminderTimeTV.setText(nextTakeMedicineReminder.getReminderTimeDisplay());
			medicineNameTV.setText(nextTakeMedicineReminder.getMedicineName());
			double tmpRose = nextTakeMedicineReminder.getDose();
			roseTV.setText(String.valueOf(tmpRose));
			medicineUnitTV.setText(nextTakeMedicineReminder.getMedicineUnitDisplay());
		}
		return;
		}
	}


	public void go2PersonalFragment()
	{
		MainActivity activity = (MainActivity)m_context;

		PersonalTabFragment personalTabFragment = null;
		Fragment            fragment            = activity.getSupportFragmentManager().findFragmentByTag(PersonalTabFragment.class.getName
																												 ());
		if (fragment != null)
		{
			personalTabFragment = (PersonalTabFragment)fragment;
		}
		if (personalTabFragment == null)
		{
			personalTabFragment = new PersonalTabFragment();
		}

		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, personalTabFragment);
		transaction.commit();

		return;
	}

	//02. 切换到service fragment
	public void go2ServiceFragment()
	{
		MainActivity       mainActivity       = (MainActivity)m_context;
		ServiceTabFragment serviceTabFragment = new ServiceTabFragment();

		FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, serviceTabFragment);
		transaction.commit();

		return;
	}


	//04.准备跳转到化验检查页面
	public void go2AssayDetectionAction()
	{
		//01. 未注册
		if (!DAccount.GetInstance().isRegisterSuccess())
		{
			go2RegisterPage();
			return;
		}

		//0202. 跳转页面到化验检查页面
		go2AssayDetectionPage();

		return;
	}

	//05. 准备跳转到用药提醒页面
	public void go2MedicationReminderAction()
	{
		//01. 未注册
		if (!DAccount.GetInstance().isRegisterSuccess())
		{
			go2RegisterPage();
			return;
		}

		//0202. 跳转页面到用药提醒
		go2MedicationReminderPage();

		return;
	}

	//06. 准备跳转到药品管理页面
	public void go2DrugAdminAction()
	{
		//01. 未注册
		if (!DAccount.GetInstance().isRegisterSuccess())
		{
			go2RegisterPage();
			return;
		}

		//0202. 跳转页面到药品管理
		go2DurgAdminPage();

		return;
	}

	//07. 准备跳转到日历页面
	public void go2CalendarAction()
	{
		//01. 未注册
		if (!DAccount.GetInstance().isRegisterSuccess())
		{
			go2RegisterPage();
			return;
		}

		//0202. 跳转页面到日历页面
		go2CalendarPage();

		return;
	}

	//08. 跳转到注册页面
	public void go2RegisterPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, RegisterActivity.class));
		return;
	}

	//09. 登出action
	public void logoutAction()
	{
		//01. 注销登录数据
		OwnerPreferences setting = StorageWrapper.GetInstance().getOwnerPreferences();
		try
		{
			setting.logout();
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString(), m_context);
			TipsDialog.GetInstance().show();
			return;
		}
		return;
	}

	//跳转到化验检测
	private void go2AssayDetectionPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, AssayDetectionActivity.class));

		return;
	}

	//跳转到用药提醒页面
	private void go2MedicationReminderPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, MedicineReminderActivity.class));

		return;
	}

	//跳转到药品管理页面
	private void go2DurgAdminPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, DrugAdministrationActivity.class));

		return;
	}

	//跳转到日历页面
	private void go2CalendarPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, CalenderActivity.class));

		return;
	}

	//跳转到风险评估页面
	public void go2RiskAssessmentPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, RiskAssessmentActivity.class));

		return;
	}

	//跳转到用户需知页面
	public void go2UserProtocalPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, UserProtocalActivity.class));

		return;
	}

	public void go2MedicineBoxPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, DrugStockAddActivity.class));
	}
}
