/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_date_flow.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/15		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.repeat_order_flow.select_date_page.msg_handler;

import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.repeat_order_flow.BaseRepeatFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.repeat_order_flow.select_date_page.ui.SelectDateActivity;

public class SelectDateMsgHandler extends BaseRepeatFlowUIMsgHandler
{
	public SelectDateMsgHandler(SelectDateActivity activity)
	{
		super(activity);
	}

	public void setNursingDate(DNursingDate nursingDate)
	{
		//01. 发送event
		m_dRepeatOrderFlow.setNursingDate(nursingDate);

		//02. 关闭当前UI
		SelectDateActivity activity = (SelectDateActivity)m_context;
		activity.finish();

		return;
	}
}
