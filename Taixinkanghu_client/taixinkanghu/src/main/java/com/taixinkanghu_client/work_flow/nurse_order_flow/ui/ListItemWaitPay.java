/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.nurse_order_page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/22		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow.ui;


import android.app.Activity;
import android.view.View;

import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;

public class ListItemWaitPay extends BaseListItem
{
	public ListItemWaitPay(Activity activity, View view)
	{
		super(activity, view);
	}

	@Override
	public void initFuncWidget(DNurseOrder nurseOrder)
	{
		EnumConfig.NurseOrderStatus orderStatus = nurseOrder.getOrderStatus();
		if (orderStatus == EnumConfig.NurseOrderStatus.WAIT_PAYMENT)
		{
			waitPayfuncAction(this);
		}
		else if (orderStatus == EnumConfig.NurseOrderStatus.WAIT_CASH_PAYMENT)
		{
			waitCashPayfuncAction(this);
		}
		return;
	}
}
