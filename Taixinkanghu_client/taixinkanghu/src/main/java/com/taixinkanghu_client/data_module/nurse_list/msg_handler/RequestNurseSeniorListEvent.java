/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.event.net.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.msg_handler;


import android.text.TextUtils;

import com.module.event.EventID;
import com.taixinkanghu_client.net.config.NurseSeniorListConfig;
import com.module.event.BaseNetEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestNurseSeniorListEvent extends BaseNetEvent
{
	private ArrayList<Integer> m_nurseIDList = new ArrayList<>();

	public RequestNurseSeniorListEvent()
	{
		super(EventID.QUEST_NURSE_SENIOR_LIST);
	}

	public ArrayList<Integer> getNurseIDList()
	{
		return m_nurseIDList;
	}

	public void setNurseIDList(ArrayList<Integer> nurseIDList)
	{
		m_nurseIDList = nurseIDList;
	}

	public HashMap<String, String> getHashMap()
	{
		HashMap<String, String> dataHashMap = new HashMap<String, String>();

		String strIDList = null;
		for (Integer id : m_nurseIDList)
		{
			if (TextUtils.isEmpty(strIDList))
			{
				strIDList = String.valueOf(id) + NurseSeniorListConfig.SPLIT;
			}
			else
			{
				strIDList += String.valueOf(id) + NurseSeniorListConfig.SPLIT;
			}
		}

		dataHashMap.put(NurseSeniorListConfig.ID_LIST, strIDList);
		return dataHashMap;
	}

}
