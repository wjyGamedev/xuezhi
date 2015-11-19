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

public class DBusinessData
{
	private static DBusinessData s_dBusinessData = new DBusinessData();

	private DMedicineUnitList m_medicalUnitList     = new DMedicineUnitList();
	private Object            m_syncMedicalUnitList = new Object();

	private DMedicineCompanyList mMedicineCompanyList     = new DMedicineCompanyList();
	private Object               mSyncMedicineCompanyList = new Object();

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

	//忘记用药记录
	private DNoTakeMedicineList mNoTakeMedicineList     = new DNoTakeMedicineList();
	private Object              mSyncNoTakeMedicineList = new Object();


	//05. 化验检查列表
	private DAssayDetectionList m_assayDetectionList     = new DAssayDetectionList();
	private Object              m_syncAssayDetectionList = new Object();

	private DBusinessData()
	{}

	public static DBusinessData GetInstance()
	{
		return s_dBusinessData;
	}

	//01. 药品公司
	public DMedicineCompanyList getMedicineCompanyList()
	{
		synchronized (mSyncMedicineCompanyList)
		{
			return mMedicineCompanyList;
		}
	}

	//01. 药品单位
	public DMedicineUnitList getMedicalUnitList()
	{
		synchronized (m_syncMedicalUnitList)
		{
			return m_medicalUnitList;
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

	//02. 库存列表
	public DMedicineBoxList getMedicineBoxList()
	{
		synchronized (m_syncMedicineBoxList)
		{
			return m_medicineBoxList;
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

	public DNoTakeMedicineList getNoTakeMedicineList()
	{
		synchronized (mSyncNoTakeMedicineList)
		{
			return mNoTakeMedicineList;
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

	//05. 化验检查页面
	public DAssayDetectionList getAssayDetectionList()
	{
		synchronized (m_syncAssayDetectionList)
		{
			return m_assayDetectionList;
		}
	}

}
