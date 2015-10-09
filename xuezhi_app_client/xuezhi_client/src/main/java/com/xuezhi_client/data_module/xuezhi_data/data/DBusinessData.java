/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/28		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class DBusinessData
{
	private static DBusinessData s_dBusinessData = new DBusinessData();

	private DMedicalUnitList m_medicalUnitList     = new DMedicalUnitList();
	private Object           m_syncMedicalUnitList = new Object();

	//01. 药品列表
	private DMedicalList m_medicalList     = new DMedicalList();
	private Object       m_syncMedicalList = new Object();

	//02. 药品库存
	private DMedicalStockList m_medicalStockList     = new DMedicalStockList();
	private Object            m_syncMedicalStockList = new Object();

	//03. 用药记录
	private DMedicalHistoryList m_medicalHistoryList     = new DMedicalHistoryList();
	private Object              m_syncMedicalHistoryList = new Object();

	//04. 用药提醒
	private DMedicalPromptList m_medicalPromptList     = new DMedicalPromptList();
	private Object             m_syncMedicalPromptList = new Object();

	//05. 化验检查列表
	private DAssayDetectionList m_assayDetectionList     = new DAssayDetectionList();
	private Object              m_syncAssayDetectionList = new Object();

	private DBusinessData()
	{}

	public static DBusinessData GetInstance()
	{
		return s_dBusinessData;
	}

	//01. 药品单位


	public DMedicalUnitList getMedicalUnitList()
	{
		synchronized (m_syncMedicalUnitList)
		{
			return m_medicalUnitList;
		}
	}

	public void setMedicalUnitList(DMedicalUnitList medicalUnitList)
	{
		synchronized (m_syncMedicalUnitList)
		{
			m_medicalUnitList = medicalUnitList;
		}
	}

	//01. 药品列表
	public DMedicalList getMedicalList()
	{
		synchronized (m_syncMedicalList)
		{
			return m_medicalList;
		}
	}

	public void setMedicalList(DMedicalList medicalList)
	{
		synchronized (m_syncMedicalList)
		{
			m_medicalList = medicalList;
		}
	}

	public boolean serialMedicalList(JSONObject response) throws JSONException
	{
		synchronized (m_syncMedicalList)
		{
			return m_medicalList.serialization(response);
		}
	}

	//02. 库存列表
	public DMedicalStockList getMedicalStockList()
	{
		synchronized (m_syncMedicalStockList)
		{
			return m_medicalStockList;
		}
	}

	public void setMedicalStockList(DMedicalStockList medicalStockList)
	{
		synchronized (m_syncMedicalStockList)
		{
			m_medicalStockList = medicalStockList;
		}
	}

	public boolean serialMedicalStockList(JSONObject response) throws JSONException, ParseException
	{
		synchronized (m_syncMedicalStockList)
		{
			return m_medicalStockList.serialization(response);
		}
	}

	//03. 用药记录
	public DMedicalHistoryList getMedicalHistoryList()
	{
		synchronized (m_syncMedicalHistoryList)
		{
			return m_medicalHistoryList;
		}
	}

	public void setMedicalHistoryList(DMedicalHistoryList medicalHistoryList)
	{
		synchronized (m_syncMedicalHistoryList)
		{
			m_medicalHistoryList = medicalHistoryList;
		}
	}

	public boolean serialMedicalHistoryList(JSONObject response) throws JSONException, ParseException
	{
		synchronized (m_syncMedicalHistoryList)
		{
			return m_medicalHistoryList.serialization(response);
		}
	}

	//04. 用药提醒
	public DMedicalPromptList getMedicalPromptList()
	{
		synchronized (m_syncMedicalPromptList)
		{
			return m_medicalPromptList;
		}
	}

	public void setMedicalPromptList(DMedicalPromptList medicalPromptList)
	{
		synchronized (m_syncMedicalPromptList)
		{
			m_medicalPromptList = medicalPromptList;
		}
	}

	public boolean serialMedicalPromptList(JSONObject response) throws JSONException, ParseException
	{
		synchronized (m_syncMedicalHistoryList)
		{
			return m_medicalPromptList.serialization(response);
		}
	}

	//05. 化验检查页面
	public DAssayDetectionList getAssayDetectionList()
	{
		synchronized (m_syncAssayDetectionList)
		{
			return m_assayDetectionList;
		}
	}

	public void setAssayDetectionList(DAssayDetectionList assayDetectionList)
	{
		synchronized (m_syncAssayDetectionList)
		{
			m_assayDetectionList = assayDetectionList;
		}
	}

	public boolean serialAssayDetectionList(JSONObject response) throws JSONException, ParseException
	{
		synchronized (m_syncAssayDetectionList)
		{
			return m_assayDetectionList.serialization(response);
		}
	}
}
