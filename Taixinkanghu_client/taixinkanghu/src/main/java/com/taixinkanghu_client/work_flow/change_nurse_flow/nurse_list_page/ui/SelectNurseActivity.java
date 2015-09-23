/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.select_nurse.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/29		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_list_page.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.util.timer.TimerTaskWrapper;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasics;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasicsList;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseContainer;
import com.taixinkanghu_client.net.config.NurseBasicListConfig;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_list_page.msg_handler.SelectNurseMsgHandler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class SelectNurseActivity extends BaseActivity
{
	//widget
	private                         HeaderCommon m_headerCommon = null;    //title：用户协议
	@Bind (R.id.tips_tv)            TextView     m_tipsTV       = null;
	@Bind (R.id.nurse_display_list) ListView     m_NursesLV     = null;
	private                         BottomCommon m_bottomCommon = null;    //底部按钮：确定

	//logical
	private SelectNurseMsgHandler  m_selectNurseMsgHandler  = new SelectNurseMsgHandler(this);
	private BottomCommonClickEvent m_bottomCommonClickEvent = new BottomCommonClickEvent();
	private PopDialog_ErrorTips    m_popDialog_errorTips    = new PopDialog_ErrorTips();
	private ClickHeaderBack        m_clickHeaderBack        = new ClickHeaderBack();

	private SelectNurseAdapter m_selectNurseAdapter = null;
	private ProgressDialog     m_waitProgressDialog = null;
	private TimerTaskWrapper   m_waitTimerTask      = new TimerTaskWrapper();
	private TimerTaskHandler   m_timerTaskHandler   = new TimerTaskHandler();

	private final static long DELAY_TIME_MILLISENCENDS = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_nurse);
		ButterKnife.bind(this);

		init();
		initWaitAction();
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			m_selectNurseMsgHandler.backAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
	/**
	 * widget event
	 */
	@OnItemClick (R.id.nurse_display_list)
	public void clickNurseItem(AdapterView<?> parent, View view, int position, long id)
	{
		m_selectNurseMsgHandler.go2NurseInfoNurseInfoPage((int)id);
		return;
	}

	/**
	 * override func
	 */
	class BottomCommonClickEvent implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			//01. 返回主页面
			m_selectNurseMsgHandler.go2MainPage();
			return;
		}
	}

	class TimerTaskHandler implements TimerTaskWrapper.TimerTaskListener
	{
		@Override
		public void execAction()
		{
			//01. 有效性判断
			DNurseBasicsList nurseBasicsList = DNurseContainer.GetInstance().getNurseBasicsList();
			if (nurseBasicsList == null)
			{
				TipsDialog.GetInstance().setMsg("nurseBasicsList == null", SelectNurseActivity.this);
				TipsDialog.GetInstance().show();
				return;
			}

			//02. 重新发送nurse basic lsit消息，来获取护工显示列表
			ArrayList<DNurseBasics> nurseBasicses = nurseBasicsList.getNurseBasicses();
			if (nurseBasicses == null || nurseBasicses.isEmpty())
			{
				m_selectNurseMsgHandler.requestNurseBasicList();

				//03. 开启等待对话框
				//initWaitAction();
				return;
			}

		}
	}

	class PopDialog_ErrorTips implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_selectNurseMsgHandler.go2MainPage();
			return;
		}
	}

	class ClickHeaderBack implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_selectNurseMsgHandler.backAction();
		}
	}
	/**
	 * inner func
	 */
	private void init()
	{
		//01. widget
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.select_nurse_title);
		m_headerCommon.getBackIBtn().setOnClickListener(m_clickHeaderBack);

		m_tipsTV.setText(R.string.select_nurse_tips);
		m_tipsTV.setTextColor(getResources().getColor(R.color.main_color));

		m_selectNurseAdapter = new SelectNurseAdapter(this);
		m_NursesLV.setAdapter(m_selectNurseAdapter);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.content_main_page);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_bottomCommonClickEvent);

		//02. logical
		m_waitProgressDialog = new ProgressDialog(this);
		m_waitProgressDialog.setMessage(getString(R.string.wait_tips));
		m_waitTimerTask.setTimerTaskListener(m_timerTaskHandler);

		//03. intent data
		Intent intent = getIntent();
		int oldNurseID = intent.getIntExtra(NurseBasicListConfig.ID, DataConfig.DEFAULT_VALUE);
		if (oldNurseID == DataConfig.DEFAULT_VALUE)
		{
			popErrorDialog("oldNurseID == DataConfig.DEFAULT_VALUE");
			return;
		}
		m_selectNurseMsgHandler.loadDataforChangeNurse(oldNurseID);

		return;
	}

	private void initWaitAction()
	{
		m_waitProgressDialog.show();
		m_waitTimerTask.schedule(DELAY_TIME_MILLISENCENDS);
	}

	public void updateContent()
	{
		//02. 更新数据
		DNurseBasicsList nurseBasicsList = DNurseContainer.GetInstance().getNurseBasicsList();
		if (nurseBasicsList == null)
		{
			TipsDialog.GetInstance().setMsg("nurseBasicsList == null", SelectNurseActivity.this);
			TipsDialog.GetInstance().show();
			return;
		}

		ArrayList<DNurseBasics> nurseBasicses = nurseBasicsList.getNurseBasicses();
		if (nurseBasicses == null || nurseBasicses.isEmpty())
		{
			return;
		}

		//01. 关闭wait dialog
		m_waitProgressDialog.dismiss();
		m_selectNurseAdapter.notifyDataSetChanged();

		return;

	}


	/**
	 * logical
	 */
	public void popErrorDialog(String error)
	{
		TipsDialog.GetInstance().setMsg(error, this, m_popDialog_errorTips);
		TipsDialog.GetInstance().show();
		return;
	}

}
