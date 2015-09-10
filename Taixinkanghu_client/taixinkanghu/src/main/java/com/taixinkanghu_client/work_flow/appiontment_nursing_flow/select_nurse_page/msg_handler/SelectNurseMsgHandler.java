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

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_nurse_page.msg_handler;

import android.content.Intent;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseList;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.NurseListHandler;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.RequestNurseBasicListEvent;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.BaseAppiontmentNursingFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.nurse_info_page.ui.NurseInfoActivity;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_nurse_page.ui.SelectNurseActivity;

public class SelectNurseMsgHandler extends BaseAppiontmentNursingFlowUIMsgHandler
{
	public SelectNurseMsgHandler(SelectNurseActivity activity)
	{
		super(activity);
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
		m_dAppiontmentNursingFlow.setSelectedNurseID(nurseID);

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
		RequestNurseBasicListEvent event = m_dAppiontmentNursingFlow.constructRequestNurseBasicListEvent();
		if (event == null)
		{
			TipsDialog.GetInstance().setMsg("event == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}
		NurseListHandler.GetInstance().requestNurseBasicListAction(event);
		return;

	}

}
