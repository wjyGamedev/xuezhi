/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.nurse_order_flow.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow;

import android.content.Context;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.work_flow.nurse_order_flow.flow_data.DNurseOrderFlow;

public class BaseNurseOrderFlowUIMsgHandler extends BaseUIMsgHandler
{
	protected DNurseOrderFlow m_dNurseOrderFlow = DNurseOrderFlow.GetInstance();

	public BaseNurseOrderFlowUIMsgHandler(Context context)
	{
		super(context);
	}
}
