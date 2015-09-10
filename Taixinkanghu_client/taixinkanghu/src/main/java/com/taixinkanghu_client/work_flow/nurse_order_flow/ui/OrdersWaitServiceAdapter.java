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
import android.view.ViewGroup;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurserOrderList;

import java.util.ArrayList;

public class OrdersWaitServiceAdapter extends BaseOrdersAdapter
{
	public OrdersWaitServiceAdapter(Activity activity)
	{
		super(activity);
	}

	@Override
	public int getCount()
	{
		ArrayList<DNurseOrder> nurseOrdersWaitService = DNurserOrderList.GetInstance().getNurseOrdersWaitService();
		if (nurseOrdersWaitService == null || nurseOrdersWaitService.isEmpty())
			return 0;

		return nurseOrdersWaitService.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		BaseListItem viewHolder = null;
		NurseOrderActivity nurseOrderActivity = (NurseOrderActivity)m_context;
		if (nurseOrderActivity == null)
		{
			TipsDialog.GetInstance().setMsg("nurseOrderActivity == null");
			TipsDialog.GetInstance().show();
			return null;
		}

		ArrayList<DNurseOrder> nurseOrders = DNurserOrderList.GetInstance().getNurseOrdersWaitService();
		if (position >= nurseOrders.size())
		{
			TipsDialog.GetInstance().setMsg("position >= nurseOrders.size()[position:="+position+"][nurseOrders.size():="+nurseOrders.size()+"]");
			TipsDialog.GetInstance().show();
			return null;
		}

		DNurseOrder nurseOrder = nurseOrders.get(position);
		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_order_info, null);
			viewHolder = new ListItemWaitService(nurseOrderActivity, convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ListItemWaitService)convertView.getTag();
		}

		viewHolder.initContent(nurseOrder);
		return convertView;
	}

}
