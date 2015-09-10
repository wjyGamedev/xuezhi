/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.show_flow.shopping_show.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/11		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.show_flow.shopping_show.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.show_flow.shopping_show.ui.ShoppingShowActivity;

public class ShoppingShowMsgHandler extends BaseUIMsgHandler
{
	public ShoppingShowMsgHandler(ShoppingShowActivity activity)
	{
		super(activity);
	}

	public void go2MainPage()
	{
		ShoppingShowActivity activity = (ShoppingShowActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
