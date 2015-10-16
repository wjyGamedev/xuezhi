/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.widget.dialog.register_page_dialog.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.module.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.module.data.DGlobal;

import com.xuzhi_client.xuzhi_app_client.R;

public class TipsDialog
{
	private static TipsDialog s_tipsDialog = new TipsDialog();

	private final String INFO_I_KNOW  = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.info_i_known);
	private final String INFO_CONFIRM = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.info_confirm);
	private final String INFO_CANCEL  = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.info_cancel);

	private AlertDialog.Builder m_builder = null;
	private String m_msg;


	private TipsDialog()
	{
	}

	public static TipsDialog GetInstance()
	{
		return s_tipsDialog;
	}

	public AlertDialog.Builder setMsg(String msg, Context context)
	{
		m_builder = new AlertDialog.Builder(context);
		m_msg = msg;
		if (m_builder != null)
		{
			m_builder.setPositiveButton(INFO_I_KNOW, null);
			m_builder.setMessage(m_msg);
		}
		return m_builder;
	}

	public AlertDialog.Builder setMsg(String msg)
	{
		Context context = DGlobal.GetInstance().getContext();
		if (context == null)
			return null;

		m_builder = new AlertDialog.Builder(context);
		m_msg = msg;
		if (m_builder != null)
		{
			m_builder.setPositiveButton(INFO_I_KNOW, null);
			m_builder.setMessage(m_msg);
		}

		return m_builder;
	}

	public AlertDialog.Builder setMsg(String msg, Context context, DialogInterface.OnClickListener listener)
	{
		m_builder = new AlertDialog.Builder(context);
		m_msg = msg;
		if (m_builder != null)
		{
			m_builder.setPositiveButton(INFO_I_KNOW, listener);
			m_builder.setMessage(m_msg);
		}

		return m_builder;
	}

	public AlertDialog.Builder setMsg(String msg, DialogInterface.OnClickListener listener)
	{
		Context context = DGlobal.GetInstance().getContext();
		if (context == null)
			return null;

		m_builder = new AlertDialog.Builder(context);
		m_msg = msg;
		if (m_builder != null)
		{
			m_builder.setPositiveButton(INFO_I_KNOW, listener);
			m_builder.setMessage(m_msg);
		}

		return m_builder;
	}

	public AlertDialog.Builder setMsg(String msg, Context context, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener)
	{
		m_builder = new AlertDialog.Builder(context);
		m_msg = msg;
		if (m_builder != null)
		{
			m_builder.setPositiveButton(INFO_CONFIRM, confirmListener);
			m_builder.setNegativeButton(INFO_CANCEL, cancelListener);
			m_builder.setMessage(m_msg);
		}

		return m_builder;
	}

	public AlertDialog.Builder setMsg(String msg, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener)
	{
		Context context = DGlobal.GetInstance().getContext();
		if (context == null)
			return null;

		m_builder = new AlertDialog.Builder(context);
		m_msg = msg;
		if (m_builder != null)
		{
			m_builder.setPositiveButton(INFO_CONFIRM, confirmListener);
			m_builder.setNegativeButton(INFO_CANCEL, cancelListener);
			m_builder.setMessage(m_msg);
		}

		return m_builder;
	}

	public AlertDialog.Builder setMsg(String msg, Context context, String positiveTips, DialogInterface.OnClickListener confirmListener, String negativeTips, DialogInterface.OnClickListener cancelListener)
	{
		if (context == null)
			return null;

		m_builder = new AlertDialog.Builder(context);
		m_msg = msg;
		if (m_builder != null)
		{
			m_builder.setPositiveButton(positiveTips, confirmListener);
			m_builder.setNegativeButton(negativeTips, cancelListener);
			m_builder.setMessage(m_msg);
		}

		return m_builder;
	}

	public void show()
	{
		if (m_builder != null)
		{
			m_builder.show();
		}
	}

}
