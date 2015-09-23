/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.register_account.data;

import android.text.TextUtils;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.storage.OwnerPreferences;
import com.module.storage.StorageWrapper;
import com.xuzhi_client.xuzhi_app_client.R;
import com.xuezhi_client.net.config.config.ProtocalConfig;
import com.xuezhi_client.net.config.config.RegisterConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DAccount implements Serializable
{
	private static DAccount s_dAccount = new DAccount();

	//http into
	private int    m_Status   = ProtocalConfig.HTTP_OK;
	private String m_errorMsg = null;

	//data
	private String m_From   = null;
	private String m_id     = null;
	private String m_code   = null;
	private String m_mobile = null;
	private String m_nick   = null;

	private DAccount()
	{
	}

	public static DAccount GetInstance()
	{
		return s_dAccount;
	}

	public boolean serialFromHttp(JSONObject response) throws JSONException
	{
		m_From = RegisterConfig.FROM_HTTP;

		//http info
		m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);
		//http error
		if (!isHttpSuccess())
		{
			m_errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JSONException(m_errorMsg);
		}

		JSONObject jsonObject = response.getJSONObject(RegisterConfig.USER_KEY);
		if (jsonObject == null)
		{
			String errMsg = DGlobal.GetInstance().getAppContext().getString(R.string.net_error_json_serilization);
			throw new JsonSerializationException(errMsg + ":" + RegisterConfig.NAME);
		}

		//序列化到成员变量
		serializationData(jsonObject);

		//序列化到本地存储
		OwnerPreferences setting = StorageWrapper.GetInstance().getOwnerPreferences();
		setting.serialization(jsonObject);

		return true;

	}

	public boolean serialFromStorage(JSONObject response) throws JSONException
	{
		m_From = RegisterConfig.FROM_STORATE;
		return serializationData(response);
	}

	private boolean serializationData(JSONObject jsonObject) throws JSONException
	{
		m_id = jsonObject.getString(RegisterConfig.ID);
		m_code = jsonObject.getString(RegisterConfig.CODE);
		m_mobile = jsonObject.getString(RegisterConfig.MOBILE);
		m_nick = jsonObject.getString(RegisterConfig.NICK);
		return true;
	}



	public int getStatus()
	{
		return m_Status;
	}

	public boolean isHttpSuccess()
	{
		return (m_Status == ProtocalConfig.HTTP_OK);
	}

	public boolean isRegisterSuccess()
	{
		return (TextUtils.isEmpty(m_code) == false);
	}

	public String getErrorMsg()
	{
		return m_errorMsg;
	}

	public String getId()
	{
		return m_id;
	}

	public String getCode()
	{
		return m_code;
	}

	public String getMobile()
	{
		return m_mobile;
	}

	public String getNick()
	{
		return m_nick;
	}
}
