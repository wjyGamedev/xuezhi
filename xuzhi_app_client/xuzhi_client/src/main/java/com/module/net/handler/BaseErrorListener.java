/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.net.exception.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.module.net.handler;

import com.android.volley.VolleyError;
import com.module.net.IErrorListener;
import com.module.widget.dialog.TipsDialog;

public class BaseErrorListener extends IErrorListener
{
	public BaseErrorListener()
	{
	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		TipsDialog.GetInstance().setMsg(error.toString());
		TipsDialog.GetInstance().show();
		return;
	}

}
