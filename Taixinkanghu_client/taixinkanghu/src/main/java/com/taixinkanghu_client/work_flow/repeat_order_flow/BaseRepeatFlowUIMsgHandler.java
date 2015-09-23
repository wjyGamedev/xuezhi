/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/9		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.repeat_order_flow;

import android.content.Context;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.work_flow.repeat_order_flow.flow_data.DRepeatOrderFlow;

public class BaseRepeatFlowUIMsgHandler extends BaseUIMsgHandler
{
	protected DRepeatOrderFlow m_dRepeatOrderFlow = DRepeatOrderFlow.GetInstance();

	public BaseRepeatFlowUIMsgHandler(Context context)
	{
		super(context);
	}
}
