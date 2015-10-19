package com.xuezhi_client.work_flow.main_page.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.module.data.DGlobal;
import com.module.frame.BaseActivity;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.main_page.msg_handler.MainMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/9/22.
 */
public class MainActivity extends BaseActivity
{
	//widget
	private                     HeaderCommon m_headerCommon = null;

	@Bind (R.id.func_region_sv) ScrollView   m_funcRegionSV = null;
	@Bind (R.id.func_region_fl) FrameLayout  m_funcRegionFL = null;
	@Bind (R.id.tabs_rg)        RadioGroup   m_tabsRG       = null;
	@Bind (R.id.home_rbtn)      RadioButton  m_homeRBtn     = null;
	@Bind (R.id.personal_rbtn)  RadioButton  m_personalRBtn = null;
	@Bind (R.id.service_rbtn)   RadioButton  m_serviceRBtn  = null;

	//logical
	private MainMsgHandler           m_mainMsgHandler           = new MainMsgHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		init();
		initAction();
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
		m_mainMsgHandler.go2PersonalFragment();
	}

	@OnClick (R.id.service_rbtn)
	public void clickServiceTab()
	{
		m_mainMsgHandler.go2ServiceFragment();
	}


	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
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

		m_homeRBtn.setSelected(true);

	}
	private void initAction()
	{
		m_mainMsgHandler.initAction();
		return;
	}

	private void updateContent()
	{
		m_mainMsgHandler.updateMainContent();
		return;
	}

	/**
	 * data:get
	 */
	public MainMsgHandler getMainMsgHandler()
	{
		return m_mainMsgHandler;
	}

	/**
	 * logical:func
	 */
	public boolean isHomeClicked()
	{
		int resID = m_tabsRG.getCheckedRadioButtonId();
		return (resID == R.id.home_rbtn);
	}

	public boolean isPersonalClicked()
	{
		int resID = m_tabsRG.getCheckedRadioButtonId();
		return (resID == R.id.personal_rbtn);
	}

	public boolean isSerivceClicked()
	{
		int resID = m_tabsRG.getCheckedRadioButtonId();
		return (resID == R.id.service_rbtn);
	}


}
