/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.data_module.nurse_list.data.${type_name}
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DNurseList
{
	private static DNurseList s_nurseList = new DNurseList();

	private HashMap<Integer, DNurse> m_nurseHashMap = new HashMap<>();

	private DNurseList()
	{}

	public static DNurseList GetInstance()
	{
		return s_nurseList;
	}

	public synchronized void syncNurseBasics(int nurseID, final DNurseBasics targetNurseBasics)
	{
		DNurse nurse = null;
		//01. 更新
		if (m_nurseHashMap.containsKey(nurseID))
		{
			nurse = m_nurseHashMap.get(nurseID);
		}
		//02. add new
		else
		{
			nurse = new DNurse();
			m_nurseHashMap.put(nurseID, nurse);
		}

		DNurseBasics srcNurseBasics = nurse.getNurseBasics();
		try
		{
			srcNurseBasics.copyfrom(targetNurseBasics);
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}
		return;

	}

	public synchronized void syncNurseSenior(int nurseID, final DNurseSenior targetNurseSenior)
	{
		DNurse nurse = null;
		//01. 更新
		if (m_nurseHashMap.containsKey(nurseID))
		{
			nurse = m_nurseHashMap.get(nurseID);
		}
		//02. add new
		else
		{
			nurse = new DNurse();
			m_nurseHashMap.put(nurseID, nurse);
		}

		DNurseSenior srcNurseSenior = nurse.getNurseSenior();
		try
		{
			srcNurseSenior.copyfrom(targetNurseSenior);
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}
		return;
	}

	public synchronized ArrayList<DNurseBasics> getNurseBasicList()
	{
		ArrayList<DNurseBasics> nurseBasicseList = new ArrayList<>();
		for (Map.Entry<Integer, DNurse> entry : m_nurseHashMap.entrySet())
		{
			DNurse tmpNurse = entry.getValue();
			if (tmpNurse == null)
				continue;

			DNurseBasics tmpNurseBasics = tmpNurse.getNurseBasics();
			nurseBasicseList.add(tmpNurseBasics);
		}
		return nurseBasicseList;
	}

	public synchronized ArrayList<DNurseSenior> getNurseSeniorList()
	{
		ArrayList<DNurseSenior> nurseSenioreList = new ArrayList<>();
		for (Map.Entry<Integer, DNurse> entry : m_nurseHashMap.entrySet())
		{
			DNurse tmpNurse = entry.getValue();
			if (tmpNurse == null)
				continue;

			DNurseSenior tmpNurseSenior = tmpNurse.getNurseSenior();
			nurseSenioreList.add(tmpNurseSenior);
		}
		return nurseSenioreList;
	}

	public synchronized boolean checkNurseID(int nurseID)
	{
		return m_nurseHashMap.containsKey(nurseID);
	}

	public synchronized DNurse getNurseInfoByID(int nurseID)
	{
		if (!checkNurseID(nurseID))
		{
			return null;
		}

		return m_nurseHashMap.get(nurseID);

	}

	public synchronized DNurseBasics getNurseBasicByID(int nurseID)
	{
		DNurse tmpDNurse = getNurseInfoByID(nurseID);
		return tmpDNurse.getNurseBasics();
	}

	public synchronized DNurseSenior getNurseSeniorByID(int nurseID)
	{
		DNurse tmpDNurse = getNurseInfoByID(nurseID);
		return tmpDNurse.getNurseSenior();
	}

}
