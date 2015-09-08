package com.taixinkanghu_client.work_flow.main_page.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
{
	//widget
	//	@Bind (R.id.header_common_fragment) HeaderCommon m_headerCommon = null;
	private HeaderCommon m_headerCommon = null;
	@Bind (R.id.func_region_sv) ScrollView  m_funcRegionSV = null;
	@Bind (R.id.func_region_fl) FrameLayout m_funcRegionFL = null;
	@Bind (R.id.tabs_rg)        RadioGroup  m_tabsRG       = null;
	@Bind (R.id.home_rbtn)      RadioButton m_homeRBtn     = null;
	@Bind (R.id.personal_rbtn)  RadioButton m_personalRBtn = null;
	@Bind (R.id.service_rbtn)   RadioButton m_serviceRBtn  = null;
	@Bind (R.id.company_rbtn)   RadioButton m_companyRBtn  = null;

	private PopupMenu m_popupMenu = null;

	//logical
	private FragmentManager          m_fragmentManager          = getFragmentManager();
	private HandleMenuItemClickEvent m_handleMenuItemClickEvent = new HandleMenuItemClickEvent();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);
		ButterKnife.bind(this);

		init();
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

	private void updateContent()
	{
		switchUIbyTab();
	}

	private void switchUIbyTab()
	{
		int resID = m_tabsRG.getCheckedRadioButtonId();
		if (resID == R.id.home_rbtn)
		{
			startHomeFragment();
		}
		else if (resID == R.id.personal_rbtn)
		{
			//nothing to do。
			return;
		}
		else if (resID == R.id.service_rbtn)
		{
			startServiceFragment();
		}
		else if (resID == R.id.company_rbtn)
		{
			startCompanyFragment();
		}
		else
		{
			startHomeFragment();
		}
	}

	/**
	 * widget event
	 */
	@OnClick (R.id.home_rbtn)
	public void startHomeFragment()
	{
		HomeTabFragment homeTabFragment = new HomeTabFragment();

		FragmentTransaction transaction = m_fragmentManager.beginTransaction();
		transaction.replace(R.id.func_region_fl, homeTabFragment);
		transaction.commit();

	}

	@OnClick (R.id.personal_rbtn)
	public void onPersionalClickEvent()
	{
		m_popupMenu.show();
	}

	@OnClick (R.id.service_rbtn)
	public void startServiceFragment()
	{
		ServiceTabFragment serviceTabFragment = new ServiceTabFragment();

		FragmentTransaction transaction = m_fragmentManager.beginTransaction();
		transaction.replace(R.id.func_region_fl, serviceTabFragment);
		transaction.commit();
	}

	@OnClick (R.id.company_rbtn)
	public void startCompanyFragment()
	{
		CompanyTabFragment companyTabFragment = new CompanyTabFragment();

		FragmentTransaction transaction = m_fragmentManager.beginTransaction();
		transaction.replace(R.id.func_region_fl, companyTabFragment);
		transaction.commit();
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
				MainActivity.this.nursingOrderAction();
			}
			return true;
			case R.id.shopping_order:
			{
				MainActivity.this.productOrderAction();
			}
			return true;
			case R.id.personal_wealth:
			{
				MainActivity.this.personalWealthAction();
			}
			return true;
			case R.id.personal_setting:
			{
				MainActivity.this.personalSettingAction();
			}
			return true;
			default:
				break;
			}
			return false;
		}
	}

	private void nursingOrderAction()
	{

	}

	private void productOrderAction()
	{

	}

	private void personalWealthAction()
	{

	}

	private void personalSettingAction()
	{

	}

}
