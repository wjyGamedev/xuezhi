/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.main_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.msg_handler;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.module.frame.BaseUIMsgHandler;
import com.module.storage.OwnerPreferences;
import com.module.storage.StorageWrapper;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAddMedicalPromptEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAddMedicalStockEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicalHistoryListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicalStockListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestTakeMedicalEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.AssayDetectionActivity;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.ui.CalenderActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui.DrugAdministrationActivity;
import com.xuezhi_client.work_flow.main_page.ui.HomeTabFragment;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuezhi_client.work_flow.main_page.ui.PersonalTabFragment;
import com.xuezhi_client.work_flow.main_page.ui.ServiceTabFragment;
import com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.ui.MedicationReminderActivity;
import com.xuezhi_client.work_flow.register_page.ui.RegisterActivity;
import com.xuezhi_client.work_flow.risk_assessment_page.ui.RiskAssessmentActivity;
import com.xuezhi_client.work_flow.user_protocal_page.ui.UserProtocalActivity;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONException;

import java.util.Calendar;

public class MainMsgHandler extends BaseUIMsgHandler
{
	public MainMsgHandler(MainActivity activity)
	{
		super(activity);
	}

	//01. update
	public void updateMainContent()
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
		DBusinessMsgHandler.GetInstance().requestMedicalUnitListAction();

		//02. 发送药品列表
		DBusinessMsgHandler.GetInstance().requestMedicalListAction();

		//03.
		RequestMedicalStockListEvent event = new RequestMedicalStockListEvent();
		event.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestMedicalStockListAction(event);

		//03.
		RequestAddMedicalStockEvent requestAddMedicalStockAction = new RequestAddMedicalStockEvent();

		requestAddMedicalStockAction.setUID(DAccount.GetInstance().getId());
		requestAddMedicalStockAction.setMID(String.valueOf(2));
		requestAddMedicalStockAction.setRemainNum(2000);
		requestAddMedicalStockAction.setUnitID(String.valueOf(1));
		requestAddMedicalStockAction.setRemainNum(100);
		requestAddMedicalStockAction.setStatus(true);
		DBusinessMsgHandler.GetInstance().requestAddMedicalStockAction(requestAddMedicalStockAction);

		RequestAddMedicalPromptEvent requestAddMedicalPromptEvent = new RequestAddMedicalPromptEvent();
		requestAddMedicalPromptEvent.setUID(DAccount.GetInstance().getId());
		requestAddMedicalPromptEvent.setRPID(String.valueOf(1));
		Calendar today = Calendar.getInstance();
		requestAddMedicalPromptEvent.setTime(today);
		requestAddMedicalPromptEvent.setDose(10);
		DBusinessMsgHandler.GetInstance().requestAddMedicalPromptAction(requestAddMedicalPromptEvent);


		RequestTakeMedicalEvent requestTakeMedicalEvent = new RequestTakeMedicalEvent();
		requestTakeMedicalEvent.setUID(DAccount.GetInstance().getId());
		requestTakeMedicalEvent.setRPID(String.valueOf(1));
		requestTakeMedicalEvent.setDose(10);
		requestTakeMedicalEvent.setUnitID(String.valueOf(1));
		DBusinessMsgHandler.GetInstance().requestTakeMedicalAction(requestTakeMedicalEvent);


		RequestMedicalHistoryListEvent requestMedicalHistoryListEvent = new RequestMedicalHistoryListEvent();
		requestMedicalHistoryListEvent.setUID(DAccount.GetInstance().getId());
		requestMedicalHistoryListEvent.setCurrMonth(today);
		DBusinessMsgHandler.GetInstance().requestMedicalHistoryListAction(requestMedicalHistoryListEvent);

		return;
	}

	//02. 切换到home fragment
	public void go2HomeFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;

		HomeTabFragment homeTabFragment = new HomeTabFragment();

		FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, homeTabFragment);
		transaction.commit();

		return;
	}

	public void go2PersonalFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		PersonalTabFragment personalTabFragment = new PersonalTabFragment();

		FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, personalTabFragment);
		transaction.commit();

		return;
	}

	//02. 切换到service fragment
	public void go2ServiceFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;
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
		mainActivity.startActivity(new Intent(mainActivity, MedicationReminderActivity.class));

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

}
