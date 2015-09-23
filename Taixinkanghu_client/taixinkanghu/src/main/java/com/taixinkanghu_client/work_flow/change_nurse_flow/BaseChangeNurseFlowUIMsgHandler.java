/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.change_nurse_flow.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/23		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow;

import android.content.Context;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.work_flow.change_nurse_flow.flow_data.DChangeNurseFlow;

public class BaseChangeNurseFlowUIMsgHandler extends BaseUIMsgHandler
{
	protected DChangeNurseFlow m_dChangeNurseFlow = DChangeNurseFlow.GetInstance();

	public BaseChangeNurseFlowUIMsgHandler(Context context)
	{
		super(context);
	}
}
