/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.adapter.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.module.frame;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class IBaseAdapter extends BaseAdapter
{
	public IBaseAdapter(Context context)
	{
		m_context = context;
	}

	protected Context m_context = null;
}
