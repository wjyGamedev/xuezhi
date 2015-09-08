/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.nurse_list.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/7		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.data;

import com.module.widget.dialog.TipsDialog;

import org.json.JSONException;

public class DNurse
{
	private DNurseBasics m_nurseBasics = new DNurseBasics();
	private DNurseSenior m_nurseSenior = new DNurseSenior();

	public void syncNurseBasics(final DNurseBasics nurseBasics)
	{
		try
		{
			m_nurseBasics.copyfrom(nurseBasics);
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

	}

	public void syncNurseSenior(final DNurseSenior nurseSenior)
	{
		try
		{
			m_nurseSenior.copyfrom(nurseSenior);
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}
	}

	public DNurseBasics getNurseBasics()
	{
		return m_nurseBasics;
	}

	public DNurseSenior getNurseSenior()
	{
		return m_nurseSenior;
	}
}
