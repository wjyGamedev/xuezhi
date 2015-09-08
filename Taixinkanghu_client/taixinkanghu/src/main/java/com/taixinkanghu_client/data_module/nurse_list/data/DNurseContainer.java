/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/18		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class DNurseContainer
{
	/**
	 * 数据区
	 */
	private static DNurseContainer s_dNurseList = new DNurseContainer();

	private DNurseBasicsList m_nurseBasicsList = new DNurseBasicsList();
	private DNurseSeniorList m_nurseSeniorList = new DNurseSeniorList();

	private DNurseContainer()
	{
	}

	public static DNurseContainer GetInstance()
	{
		return s_dNurseList;
	}

	public boolean serialBasiclist(JSONObject response) throws JSONException
	{
		return m_nurseBasicsList.serialization(response);
	}

	public boolean serialSeniorList(JSONObject response) throws JSONException, ParseException
	{
		return m_nurseSeniorList.serialization(response);
	}

	public DNurseBasicsList getNurseBasicsList()
	{
		return m_nurseBasicsList;
	}

	public DNurseSeniorList getNurseSeniorList()
	{
		return m_nurseSeniorList;
	}
}
