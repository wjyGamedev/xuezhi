/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.storage.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/5		WangJY		1.0.0		create
 */

package com.module.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.module.exception.RuntimeExceptions.logical.OwnerPreferencesException;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu_client.net.config.RegisterConfig;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class OwnerPreferences extends BaseStorage
{
	private Context m_context = null;
	private SharedPreferences m_setting = null;
	private SharedPreferences.Editor m_editor = null;

	public OwnerPreferences()
	{
	}

	public void init(Context context)
	{
		m_context = context;
		m_setting = m_context.getSharedPreferences(StorageConfig.SETTTING_FILE_NAME, Context.MODE_PRIVATE);
		m_editor = m_setting.edit();

		tryLogin();

	}

	public boolean serialization(JSONObject jsonObject) throws JSONException
	{
		String id = jsonObject.getString(RegisterConfig.ID);
		String code = jsonObject.getString(RegisterConfig.CODE);
		String mobile = jsonObject.getString(RegisterConfig.MOBILE);
		String nick = jsonObject.getString(RegisterConfig.NICK);

		m_editor.putString(RegisterConfig.ID, id);
		m_editor.putString(RegisterConfig.CODE, code);
		m_editor.putString(RegisterConfig.MOBILE, mobile);
		m_editor.putString(RegisterConfig.NICK, nick);
		m_editor.commit();
		return true;
	}

	public boolean tryLogin()
	{
		String id = m_setting.getString(RegisterConfig.ID, "");
		String code = m_setting.getString(RegisterConfig.CODE, "");
		String mobile = m_setting.getString(RegisterConfig.MOBILE, "");
		String nick = m_setting.getString(RegisterConfig.NICK, "");

		HashMap<String, String> data = new HashMap<String, String>();
		data.put(RegisterConfig.ID, id);
		data.put(RegisterConfig.CODE, code);
		data.put(RegisterConfig.MOBILE, mobile);
		data.put(RegisterConfig.NICK, nick);

		JSONObject jsonObject = new JSONObject(data);

		try
		{
			DAccount.GetInstance().serialFromStorage(jsonObject);
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return false;
		}
		return true;

	}
	public boolean logout() throws JSONException
	{
		//01. 有效性检测
		if (m_editor == null)
		{
			throw new OwnerPreferencesException("m_editor == null");
		}

		//02. prefer set null
		m_editor.putString(RegisterConfig.ID, "");
		m_editor.putString(RegisterConfig.CODE, "");
		m_editor.putString(RegisterConfig.MOBILE, "");
		m_editor.putString(RegisterConfig.NICK, "");
		m_editor.commit();

		//03. 更新DAccount
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(RegisterConfig.ID, "");
		data.put(RegisterConfig.CODE, "");
		data.put(RegisterConfig.MOBILE, "");
		data.put(RegisterConfig.NICK, "");

		JSONObject jsonObject = new JSONObject(data);
		DAccount.GetInstance().serialFromStorage(jsonObject);

		return true;

	}

}
