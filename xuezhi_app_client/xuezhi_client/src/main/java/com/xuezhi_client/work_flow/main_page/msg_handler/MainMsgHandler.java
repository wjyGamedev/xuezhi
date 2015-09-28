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

import android.app.FragmentTransaction;
import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.module.storage.OwnerPreferences;
import com.module.storage.StorageWrapper;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.AssayDetectionActivity;
import com.xuezhi_client.work_flow.calendar_flow.calender_page.ui.CalenderActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_page.ui.DrugAdministrationActivity;
import com.xuezhi_client.work_flow.main_page.ui.HomeTabFragment;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuezhi_client.work_flow.main_page.ui.PersonalTabFragment;
import com.xuezhi_client.work_flow.main_page.ui.ServiceTabFragment;
import com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.ui.MedicationReminderActivity;
import com.xuezhi_client.work_flow.register_page.ui.RegisterActivity;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONException;

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


	//02. 切换到home fragment
	public void go2HomeFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;

		HomeTabFragment homeTabFragment = new HomeTabFragment();

		FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, homeTabFragment);
		transaction.commit();

		return;
	}

	public void go2PersonalFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		PersonalTabFragment personalTabFragment = new PersonalTabFragment();

		FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, personalTabFragment);
		transaction.commit();

		return;
	}

	//02. 切换到service fragment
	public void go2ServiceFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		ServiceTabFragment serviceTabFragment = new ServiceTabFragment();

		FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
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



}
