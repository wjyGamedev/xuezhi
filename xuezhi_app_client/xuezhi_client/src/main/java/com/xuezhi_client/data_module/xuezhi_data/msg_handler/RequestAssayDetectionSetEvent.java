/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;

import com.module.event.BaseNetEvent;
import com.module.event.EventID;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.net.config.AssayDetectionConfig;

import java.util.HashMap;

public class RequestAssayDetectionSetEvent extends BaseNetEvent
{
	private String m_UID = null;
	private String m_ID = null;
	private double m_tgValue = DataConfig.DEFAULT_VALUE;
	private double m_tchoValue = DataConfig.DEFAULT_VALUE;
	private double m_lolcValue = DataConfig.DEFAULT_VALUE;
	private double m_hdlcValue = DataConfig.DEFAULT_VALUE;

	private double m_atlValue = DataConfig.DEFAULT_VALUE;
	private double m_astValue = DataConfig.DEFAULT_VALUE;
	private double m_ckValue = DataConfig.DEFAULT_VALUE;
	private double m_gluValue = DataConfig.DEFAULT_VALUE;
	private double m_hba1cValue = DataConfig.DEFAULT_VALUE;
	private double m_scrValue = DataConfig.DEFAULT_VALUE;

	public RequestAssayDetectionSetEvent()
	{
		super(EventID.QUEST_ADD_ASSAY_DETECTION_INFO);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(AssayDetectionConfig.UID, m_UID);
		sendData.put(AssayDetectionConfig.ID, m_ID);

		sendData.put(AssayDetectionConfig.TG, String.valueOf(m_tgValue));
		sendData.put(AssayDetectionConfig.TCHO, String.valueOf(m_tchoValue));
		sendData.put(AssayDetectionConfig.LOLC, String.valueOf(m_lolcValue));
		sendData.put(AssayDetectionConfig.HDLC, String.valueOf(m_hdlcValue));

		sendData.put(AssayDetectionConfig.ALT, String.valueOf(m_atlValue));
		sendData.put(AssayDetectionConfig.AST, String.valueOf(m_astValue));
		sendData.put(AssayDetectionConfig.CK, String.valueOf(m_ckValue));
		sendData.put(AssayDetectionConfig.GLU, String.valueOf(m_gluValue));
		sendData.put(AssayDetectionConfig.HBA1C, String.valueOf(m_hba1cValue));
		sendData.put(AssayDetectionConfig.SCR, String.valueOf(m_scrValue));

		return sendData;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}

	public void setID(String ID)
	{
		m_ID = ID;
	}

	public void setTgValue(double tgValue)
	{
		m_tgValue = tgValue;
	}

	public void setTchoValue(double tchoValue)
	{
		m_tchoValue = tchoValue;
	}

	public void setLolcValue(double lolcValue)
	{
		m_lolcValue = lolcValue;
	}

	public void setHdlcValue(double hdlcValue)
	{
		m_hdlcValue = hdlcValue;
	}

	public void setAtlValue(double atlValue)
	{
		m_atlValue = atlValue;
	}

	public void setAstValue(double astValue)
	{
		m_astValue = astValue;
	}

	public void setCkValue(double ckValue)
	{
		m_ckValue = ckValue;
	}

	public void setGluValue(double gluValue)
	{
		m_gluValue = gluValue;
	}

	public void setHba1cValue(double hba1cValue)
	{
		m_hba1cValue = hba1cValue;
	}

	public void setScrValue(double scrValue)
	{
		m_scrValue = scrValue;
	}
}
