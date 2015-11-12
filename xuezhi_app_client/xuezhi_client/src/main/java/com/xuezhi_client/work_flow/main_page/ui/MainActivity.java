package com.xuezhi_client.work_flow.main_page.ui;

import android.graphics.Rect;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.module.data.DGlobal;
import com.module.frame.BaseActivity;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.third.part.xinge_tengxun.XinGe;
import com.umeng.update.UmengUpdateAgent;
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
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.func_region_sv) ScrollView  m_funcRegionSV = null;
	@Bind (R.id.func_region_fl) FrameLayout m_funcRegionFL = null;
	@Bind (R.id.tabs_rg)        RadioGroup  m_tabsRG       = null;
	@Bind (R.id.home_rbtn)      RadioButton m_homeRBtn     = null;
	@Bind (R.id.personal_rbtn)  RadioButton m_personalRBtn = null;
	@Bind (R.id.service_rbtn)   RadioButton m_serviceRBtn  = null;

	//logical
	private MainMsgHandler m_mainMsgHandler = new MainMsgHandler(this);

	private AsyncWaitDialog m_asyncWaitDialog = new AsyncWaitDialog();

	private HandleAsyncWaitDialogFinishedEvent m_handleAsyncWaitDialogFinishedEvent = new HandleAsyncWaitDialogFinishedEvent();


	@Override
	protected void onStart()
	{
		super.onStart();
		updateContent();
	}

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_main);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
		initUmengUpdate();
		initXinge();
		initAction();
	}

	@Override
	public void onDestoryAction()
	{

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
	 * override event
	 */
	public class HandleAsyncWaitDialogFinishedEvent implements AsyncWaitDialog.HandleWaitDialogFinishedEvent
	{

		@Override
		public void OnWaitDialogFinished()
		{
			m_asyncWaitDialog.dismiss();
			return;
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
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

		m_asyncWaitDialog.initDefault(this);
		m_asyncWaitDialog.setHandleWaitDialogFinishedEvent(m_handleAsyncWaitDialogFinishedEvent);
		m_asyncWaitDialog.show();
	}

	private void initAction()
	{
		m_mainMsgHandler.initAction();
		return;
	}

	private void initUmengUpdate()
	{
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
	}

	private void initXinge()
	{
//		XGPushConfig.enableDebug(this, true);
		XinGe.GetInstance().initMainContent(this);
	}

	private void updateContent()
	{
		//这里不调用update，依赖于服务器返回event。
		//		m_mainMsgHandler.updateMainContent();
		return;
	}


	/**
	 * data:get
	 */
	public MainMsgHandler getMainMsgHandler()
	{
		return m_mainMsgHandler;
	}

	public AsyncWaitDialog getAsyncWaitDialog()
	{
		return m_asyncWaitDialog;
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
