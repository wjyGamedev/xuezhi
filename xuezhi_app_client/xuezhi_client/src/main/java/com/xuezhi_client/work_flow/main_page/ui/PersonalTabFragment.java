/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.main_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/25		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.ui;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.work_flow.main_page.msg_handler.MainMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalTabFragment extends Fragment
{
	//widget
	//login/out region
	@Bind(R.id.loginout_region_ll) LinearLayout m_loginoutRegionLL = null;
	@Bind(R.id.loginout_tips_tv) TextView m_loginoutTipsTV = null;
	@Bind(R.id.loginout_tv) TextView m_loginoutTV = null;

	//风险评估/调查问卷
	@Bind(R.id.risk_assessment_region_ll) LinearLayout m_riskAssessmentRegionLL = null;

	//用户须知/用户协议
	@Bind(R.id.user_procotal_region_ll) LinearLayout m_userProcotalRegionLL = null;

	//logical
	private MainMsgHandler m_mainMsgHandler = null;
	private HandleClickEventOnLogoutDialog m_handleClickEventOnLogoutDialog = new HandleClickEventOnLogoutDialog();


	private View           m_view           = null;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_personal_setting, container, false);
		ButterKnife.bind(this, m_view);

		init();
		return m_view;
	}

	@Override
	public void onStart()
	{
		updateContent();
		super.onStart();
	}

	@OnClick(R.id.loginout_region_ll)
	public void clickLoginoutRegion()
	{
		boolean loginFlag = DAccount.GetInstance().isRegisterSuccess();

		//01. 未注册
		if (loginFlag == false)
		{
			//跳转到注册页面
			m_mainMsgHandler.go2RegisterPage();
			return;
		}

		//02. 注册，进行登出流程。
		TipsDialog.GetInstance().setMsg(getString(R.string.tips_loginout),
										this.getActivity(),
										m_handleClickEventOnLogoutDialog,
										m_handleClickEventOnLogoutDialog
									   );
		TipsDialog.GetInstance().show();
		return;
	}


	/**
	 * override:func
	 */
	public class HandleClickEventOnLogoutDialog implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			//01. 确认，走登出流程
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				//清除数据
				m_mainMsgHandler.logoutAction();

				//update ui
				updateContent();
			}
			//02. 取消,关闭弹出对话框。
			else if (which == DialogInterface.BUTTON_NEGATIVE)
			{
				dialog.dismiss();
			}
			return;
		}
	}

	/**
	 * inner func
	 */
	private void init()
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

	private void updateContent()
	{
		boolean loginFlag = DAccount.GetInstance().isRegisterSuccess();

		//01. 未注册
		if (loginFlag == false)
		{
			switchUI2Logout();
			return;
		}

		//02. 注册
		switchUI2Login();

		return;
	}

	private void switchUI2Login()
	{
		String phoneNum = DAccount.GetInstance().getMobile();
		m_loginoutTipsTV.setText(phoneNum);
		m_loginoutTV.setText(getResources().getString(R.string.logout));

		return;
	}

	private void switchUI2Logout()
	{
		m_loginoutTipsTV.setText(getResources().getString(R.string.tips_logout));
		m_loginoutTV.setText(getResources().getString(R.string.login));

		return;
	}

}
