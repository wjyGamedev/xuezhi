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

	private DMedicineUnitList m_medicalUnitList     = new DMedicineUnitList();
	private Object            m_syncMedicalUnitList = new Object();

	//01. 药品列表
	private DMedicineList m_medicalList     = new DMedicineList();
	private Object        m_syncMedicalList = new Object();

	//02. 药品库存
	private DMedicineBoxList m_medicineBoxList     = new DMedicineBoxList();
	private Object           m_syncMedicineBoxList = new Object();

	//03. 用药提醒
	private DMedicinePromptList m_medicinePromptList    = new DMedicinePromptList();
	private Object              m_syncMedicalPromptList = new Object();

	//04. 用药记录
	private DTakeMedicineList m_takeMedicineHistoryList = new DTakeMedicineList();
	private Object            m_syncMedicalHistoryList  = new Object();

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


	public DMedicineUnitList getMedicalUnitList()
	{
		synchronized (m_syncMedicalUnitList)
		{
			return m_medicalUnitList;
		}
	}

	public void setMedicalUnitList(DMedicineUnitList medicalUnitList)
	{
		synchronized (m_syncMedicalUnitList)
		{
			m_medicalUnitList = medicalUnitList;
		}
	}

	//01. 药品列表
	public DMedicineList getMedicineList()
	{
		synchronized (m_syncMedicalList)
		{
			return m_medicalList;
		}
	}

	public void setMedicalList(DMedicineList medicalList)
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
	public DMedicineBoxList getMedicineBoxList()
	{
		synchronized (m_syncMedicineBoxList)
		{
			return m_medicineBoxList;
		}
	}

	public void setMedicineBoxList(DMedicineBoxList medicineBoxList)
	{
		synchronized (m_syncMedicineBoxList)
		{
			m_medicineBoxList = medicineBoxList;
		}
	}

	public boolean serialMedicalStockList(JSONObject response) throws JSONException, ParseException
	{
		synchronized (m_syncMedicineBoxList)
		{
			return m_medicineBoxList.serialization(response);
		}
	}

	//03. 用药记录
	public DTakeMedicineList getTakeMedicineHistoryList()
	{
		synchronized (m_syncMedicalHistoryList)
		{
			return m_takeMedicineHistoryList;
		}
	}

	public void setTakeMedicineHistoryList(DTakeMedicineList takeMedicineHistoryList)
	{
		synchronized (m_syncMedicalHistoryList)
		{
			m_takeMedicineHistoryList = takeMedicineHistoryList;
		}
	}

	public boolean serialMedicalHistoryList(JSONObject response) throws JSONException, ParseException
	{
		synchronized (m_syncMedicalHistoryList)
		{
			return m_takeMedicineHistoryList.serialization(response);
		}
	}

	//04. 用药提醒
	public DMedicinePromptList getMedicinePromptList()
	{
		synchronized (m_syncMedicalPromptList)
		{
			return m_medicinePromptList;
		}
	}

	public void setMedicinePromptList(DMedicinePromptList medicinePromptList)
	{
		synchronized (m_syncMedicalPromptList)
		{
			m_medicinePromptList = medicinePromptList;
		}
	}

	public boolean serialMedicalPromptList(JSONObject response) throws JSONException, ParseException
	{
		synchronized (m_syncMedicalHistoryList)
		{
			return m_medicinePromptList.serialization(response);
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
