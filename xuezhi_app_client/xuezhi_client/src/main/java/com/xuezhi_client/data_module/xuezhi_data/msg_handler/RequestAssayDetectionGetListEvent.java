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
import com.xuezhi_client.net.config.AssayDetectionConfig;

import java.util.HashMap;

public class RequestAssayDetectionGetListEvent extends BaseNetEvent
{
	private String m_UID = null;

	public RequestAssayDetectionGetListEvent()
	{
		super(EventID.QUEST_ASSAY_DETECTION_LIST);
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> sendData = new HashMap<String, String>();

		sendData.put(AssayDetectionConfig.UID, m_UID);

		return sendData;
	}

	public void setUID(String UID)
	{
		m_UID = UID;
	}
}
