package com.taixinkanghu_client.work_flow.main_page.ui;

import android.app.FragmentManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.module.data.DGlobal;
import com.module.frame.BaseActivity;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.main_page.msg_handler.MainMsgHandler;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
{
	//widget
	//	@Bind (R.id.header_common_fragment) HeaderCommon m_headerCommon = null;
	private                     HeaderCommon m_headerCommon = null;
	@Bind (R.id.func_region_sv) ScrollView   m_funcRegionSV = null;
	@Bind (R.id.func_region_fl) FrameLayout  m_funcRegionFL = null;
	@Bind (R.id.tabs_rg)        RadioGroup   m_tabsRG       = null;
	@Bind (R.id.home_rbtn)      RadioButton  m_homeRBtn     = null;
	@Bind (R.id.personal_rbtn)  RadioButton  m_personalRBtn = null;
	@Bind (R.id.service_rbtn)   RadioButton  m_serviceRBtn  = null;
	@Bind (R.id.company_rbtn)   RadioButton  m_companyRBtn  = null;

	private PopupMenu m_popupMenu = null;

	//logical
	private MainMsgHandler           m_mainMsgHandler           = new MainMsgHandler(this);
	private FragmentManager          m_fragmentManager          = getFragmentManager();
	private HandleMenuItemClickEvent m_handleMenuItemClickEvent = new HandleMenuItemClickEvent();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);
		ButterKnife.bind(this);

		init();
		initUmengUpdate();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		updateContent();
	}


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ButterKnife.bind(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);

		Rect outRect = new Rect();
		this.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
		Rect outRect2 = new Rect();
		this.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect2);
		DGlobal.GetInstance().setAppRegionHeight(outRect2.bottom);

	}

	/**
	 * widget event
	 */
	@OnClick (R.id.home_rbtn)
	public void clickHomeTab()
	{
		m_mainMsgHandler.go2HomeFragment();
		return;
	}

	@OnClick (R.id.personal_rbtn)
	public void clickPersonalTab()
	{
		m_popupMenu.show();
	}

	@OnClick (R.id.service_rbtn)
	public void clickServiceTab()
	{
		m_mainMsgHandler.go2ServiceFragment();
	}

	@OnClick (R.id.company_rbtn)
	public void clickCompanyTab()
	{
		m_mainMsgHandler.go2CompanyFragment();
	}

	/**
	 * override func
	 */
	private class HandleMenuItemClickEvent implements PopupMenu.OnMenuItemClickListener
	{
		@Override
		public boolean onMenuItemClick(MenuItem item)
		{
			switch (item.getItemId())
			{
			case R.id.nursing_order:
			{
				m_mainMsgHandler.go2NurseOrderAction();
			}
			return true;
			case R.id.shopping_order:
			{
				m_mainMsgHandler.go2ShoppingOrderAction();
			}
			return true;
			case R.id.personal_wealth:
			{
				m_mainMsgHandler.go2PersonalWealthAction();
			}
			return true;
			case R.id.personal_setting:
			{
				m_mainMsgHandler.go2SettingAction();
			}
			return true;
			default:
				break;
			}
			return false;
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)m_fragmentManager.findFragmentById(R.id.common_header_fragment);
		if (m_headerCommon.getBackIBtn() == null)
		{
			TipsDialog.GetInstance().setMsg("m_headerCommon.getBackIBtn() == null", this);
			TipsDialog.GetInstance().show();
			return;
		}
		m_headerCommon.getBackIBtn().setVisibility(View.INVISIBLE);

		if (m_headerCommon.getHeaderTV() == null)
		{
			TipsDialog.GetInstance().setMsg("m_headerCommon.getHeaderTV() == null", this);
			TipsDialog.GetInstance().show();
			return;
		}
		m_headerCommon.getHeaderTV().setTextSize(24.0f);

		m_homeRBtn.setSelected(true);

		m_popupMenu = new PopupMenu(this, m_personalRBtn);
		m_popupMenu.inflate(R.menu.main_personal_popup_menu);
		m_popupMenu.setOnMenuItemClickListener(m_handleMenuItemClickEvent);

	}

	private void initUmengUpdate()
	{
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
	}

	private void updateContent()
	{
		switchUIbyTab();
	}

	private void switchUIbyTab()
	{
		int resID = m_tabsRG.getCheckedRadioButtonId();
		if (resID == R.id.home_rbtn)
		{
			m_mainMsgHandler.go2HomeFragment();
		}
		else if (resID == R.id.personal_rbtn)
		{
			//nothing to do。
			return;
		}
		else if (resID == R.id.service_rbtn)
		{
			m_mainMsgHandler.go2ServiceFragment();
		}
		else if (resID == R.id.company_rbtn)
		{
			m_mainMsgHandler.go2CompanyFragment();
		}
		else
		{
			m_mainMsgHandler.go2HomeFragment();
		}
		return;

	}

	/**
	 * data:get
	 */
	public MainMsgHandler getMainMsgHandler()
	{
		return m_mainMsgHandler;
	}


}
