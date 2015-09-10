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

package com.taixinkanghu_client.work_flow.main_page.msg_handler;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.widget.Toast;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.work_flow.main_page.ui.CompanyTabFragment;
import com.taixinkanghu_client.work_flow.main_page.ui.HomeTabFragment;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.ServiceTabFragment;
import com.taixinkanghu_client.work_flow.nurse_order_flow.ui.NurseOrderActivity;
import com.taixinkanghu_client.work_flow.register_flow.ui.RegisterActivity;

public class MainMsgHandler extends BaseUIMsgHandler
{
	public MainMsgHandler(MainActivity activity)
	{
		super(activity);
	}

	//01. 切换到home fragment
	public void go2HomeFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;

		HomeTabFragment homeTabFragment = new HomeTabFragment();

		FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, homeTabFragment);
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

	//03. 切换到company fragment
	public void go2CompanyFragment()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		CompanyTabFragment companyTabFragment = new CompanyTabFragment();

		FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.func_region_fl, companyTabFragment);
		transaction.commit();

		return;
	}

	//04. 跳转到nurse order page
	public void go2NurseOrderAction()
	{
		//01. 未注册
		if (!DAccount.GetInstance().isRegisterSuccess())
		{
			go2RegisterPage();
			return;
		}

		//02. 已注册
		//0201. 发送event，请求获取nurse order list
		requestNurseOrderListAction();

		//0202. 跳转页面到nurse order
		go2NurseOrderPage();

		return;
	}

	//05. 跳转到注册页面
	private void go2RegisterPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, RegisterActivity.class));

		return;
	}

	//06. 跳转到nurse order
	private void go2NurseOrderPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, NurseOrderActivity.class));

		return;
	}

	//07. nurse order list action
	private void requestNurseOrderListAction()
	{
		NurseOrderListHandler.GetInstance().requestNurseOrderListAction();
		return;
	}

	//08. 跳转到shopping order action
	public void go2ShoppingOrderAction()
	{
		Toast.makeText(m_context, R.string.function_is_not_open, Toast.LENGTH_SHORT).show();
	}

	//09. 跳转到personal wealth action
	public void go2PersonalWealthAction()
	{
		//01. 未注册
		if (!DAccount.GetInstance().isRegisterSuccess())
		{
			go2RegisterPage();
			return;
		}

		//02. 已注册
		go2PersonalWealthPage();

		return;

	}

	//10. 跳转到personal wealth page
	private void go2PersonalWealthPage()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, MyWealthActivity.class));
		return;
	}


	//11. 跳转到setting page
	public void go2SettingAction()
	{
		MainActivity mainActivity = (MainActivity)m_context;
		mainActivity.startActivity(new Intent(mainActivity, SettingActivity.class));
	}


}
