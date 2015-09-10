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
 * 2015/8/23		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow.ui;

import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;

public class FuncBtnTabObject
{
	private DNurseOrder m_nurseOrder = null;
	private String      m_funcTag    = null;

	public FuncBtnTabObject(DNurseOrder nurseOrder, String funcTag)
	{
		m_nurseOrder = nurseOrder;
		m_funcTag = funcTag;
	}

	public DNurseOrder getNurseOrder()
	{
		return m_nurseOrder;
	}

	public String getFuncTag()
	{
		return m_funcTag;
	}
}
