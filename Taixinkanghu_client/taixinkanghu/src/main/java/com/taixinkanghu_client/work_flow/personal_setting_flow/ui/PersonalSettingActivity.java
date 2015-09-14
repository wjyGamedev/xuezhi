/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.setting.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/5		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.personal_setting_flow.ui;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.work_flow.personal_setting_flow.msg_handler.PersonalSettingMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalSettingActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.click_region_linearlayout) LinearLayout m_clickRegin = null;
	@Bind (R.id.login_tips_tv)             TextView     m_TipTV      = null;
	@Bind (R.id.loginout_tv)               TextView     m_loginoutTV = null;

	private BottomCommon m_bottomCommon = null;

	//logical data
	private PersonalSettingMsgHandler      m_personalSettingMsgHandler      = new PersonalSettingMsgHandler(this);
	private FragmentManager                m_fragmentManager                = getFragmentManager();
	private ClickBottomCommon              m_clickBottomCommon              = new ClickBottomCommon();
	private HandleClickEventOnLogoutDialog m_handleClickEventOnLogoutDialog = new HandleClickEventOnLogoutDialog();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_setting);
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
	 * widget event
	 */
	@OnClick (R.id.click_region_linearlayout)
	public void clickLoginoutRegion()
	{
		m_personalSettingMsgHandler.loginoutAction();
		return;
	}


	/**
	 * override func
	 */
	class ClickBottomCommon implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_personalSettingMsgHandler.go2MainPage();
		}
	}

	public class HandleClickEventOnLogoutDialog implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			//01. 确认，走登出流程
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_personalSettingMsgHandler.requestLogoutAction();
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
		//01. widget
		m_headerCommon = (HeaderCommon)m_fragmentManager.findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.personal_setting_title);

		m_bottomCommon = (BottomCommon)m_fragmentManager.findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.content_main_page);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomCommon);

	}

	private void updateContent()
	{
		m_personalSettingMsgHandler.updateContent();
	}

	/**
	 * func
	 */
	public void popLogoutDialog()
	{
		TipsDialog.GetInstance().setMsg(getString(R.string.tips_loginout),
												  this,
												  m_handleClickEventOnLogoutDialog,
												  m_handleClickEventOnLogoutDialog
									   );
		TipsDialog.GetInstance().show();
		return;

	}

	public void loginUI()
	{
		String phoneNum = DAccount.GetInstance().getMobile();
		m_TipTV.setText(phoneNum);
		m_loginoutTV.setText(getResources().getString(R.string.logout));
		m_loginoutTV.setTextColor(getResources().getColor(R.color.main_color));
	}

	public void logoutUI()
	{
		m_TipTV.setText(getResources().getString(R.string.tips_logout));
		m_loginoutTV.setText(getResources().getString(R.string.login));
		m_loginoutTV.setTextColor(getResources().getColor(R.color.main_color));
	}

}
