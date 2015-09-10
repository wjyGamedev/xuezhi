/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/9		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow;

import android.content.Context;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DAppiontmentNursingFlow;

public class BaseAppiontmentNursingFlowUIMsgHandler extends BaseUIMsgHandler
{
	protected DAppiontmentNursingFlow m_dAppiontmentNursingFlow = DAppiontmentNursingFlow.GetInstance();

	public BaseAppiontmentNursingFlowUIMsgHandler(Context context)
	{
		super(context);
	}
}
