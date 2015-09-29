package com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.msg_handler.DrugStockAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/30.
 */
public class DrugStockAddActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;
	@Bind (R.id.drug_reminder_state_rb) RadioButton m_drugReminderStateRB = null;

	private ClickSaveBottomBtn     m_clickSaveBottomBtn     = new ClickSaveBottomBtn();
	private DrugStockAddMsgHandler m_drugStockAddMsgHandler = new DrugStockAddMsgHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drug_stock_add);
		ButterKnife.bind(this);

		init();
		//		initWaitAction();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.drug_stock_add_page_title_text);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.drug_stock_add_save_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickSaveBottomBtn);

	}

	/**
	 * overrider func
	 */
	class ClickSaveBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_drugStockAddMsgHandler.saveDrugInfo();
		}
	}


	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	public void updateContent()
	{

	}

	//网上复制下来的代码，作用：点击EditText文本框之外任何地方隐藏键盘
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev))
			{

				InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
				if (imm != null)
				{
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev))
		{
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event)
	{
		if (v != null && (v instanceof EditText))
		{
			int[] leftTop = {0, 0};
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
			{
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
}
