/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_nurse.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/9		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_list_page.msg_handler;

import android.content.Intent;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseList;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.AnswerNurseBasicListEvent;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.NurseListHandler;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.RequestNurseBasicListEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurserOrderList;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.change_nurse_flow.BaseChangeNurseFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_info_page.ui.NurseInfoActivity;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_list_page.ui.SelectNurseActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;

import java.util.Date;

public class SelectNurseMsgHandler extends BaseChangeNurseFlowUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public SelectNurseMsgHandler(SelectNurseActivity activity)
	{
		super(activity);
	}

	//01. init flow data
	public void loadDataforChangeNurse(int oldNurseID)
	{
		SelectNurseActivity activity = (SelectNurseActivity)m_context;

		//01. check
		DNurseOrder nurseOrder = DNurserOrderList.GetInstance().getNurseOrderByNurseID(oldNurseID);
		if (nurseOrder == null)
		{
			activity.popErrorDialog("Input selectedNurseID is invalid![selectedNurseID:=" + oldNurseID + "]");
			return;
		}

		//02. set data
		m_dChangeNurseFlow.setOldNurseID(oldNurseID);

		//03. DNursingDate
		Date beginDate = nurseOrder.getBeginDate();
		if (beginDate == null)
		{
			activity.popErrorDialog("beginDate == null");
			return;
		}
		Date endDate = nurseOrder.getEndDate();
		if (endDate == null)
		{
			activity.popErrorDialog("endDate == null");
			return;
		}
		DNursingDate nursingDate = new DNursingDate(beginDate, endDate);
		m_dChangeNurseFlow.setOldNursingDate(nursingDate);

		return;

	}


	//01. 请求跳转到护工信息页面
	public void go2NurseInfoNurseInfoPage(int nurseID)
	{
		//01. 判断nurse id 有效性
		if (!DNurseList.GetInstance().checkNurseID(nurseID))
		{
			TipsDialog.GetInstance().setMsg("nurseID is invalid![nurseID:="+nurseID+"]");
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 同步数据到DAppiontmentNursing
		m_dChangeNurseFlow.setNewNurseID(nurseID);

		//03. 跳转到护工信息页面
		SelectNurseActivity selectNurseActivity = (SelectNurseActivity)m_context;
		if (selectNurseActivity == null)
		{
			TipsDialog.GetInstance().setMsg("selectNurseActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		Intent intent = new Intent(selectNurseActivity, NurseInfoActivity.class);
		selectNurseActivity.startActivity(intent);

		return;

	}

	//02. 请求nurse basic list event
	public void requestNurseBasicList()
	{
		RequestNurseBasicListEvent event = m_dChangeNurseFlow.constructRequestNurseBasicListEvent();
		if (event == null)
		{
			TipsDialog.GetInstance().setMsg("event == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}
		NurseListHandler.GetInstance().requestNurseBasicListAction(event);
		return;

	}

	//03. 返回主页面
	public void go2MainPage()
	{
		//01. 跳转到主页面
		SelectNurseActivity activity = (SelectNurseActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));

		//02. 清楚流程信息
		m_dChangeNurseFlow.clearupAll();
		return;
	}

	//04. 同步nurse basic list 到UI
	public void onEventMainThread(AnswerNurseBasicListEvent event)
	{
		SelectNurseActivity activity = (SelectNurseActivity)m_context;
		activity.updateContent();
		return;
	}

	public void backAction()
	{
		go2MainPage();
	}
}
