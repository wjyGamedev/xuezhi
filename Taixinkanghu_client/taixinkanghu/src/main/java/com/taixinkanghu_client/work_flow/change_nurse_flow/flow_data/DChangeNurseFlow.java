/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.change_nurse_flow.flow_data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/23		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.flow_data;

import com.module.exception.RuntimeExceptions.logical.LogicalRTException;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.department_list.data.DDepartment;
import com.taixinkanghu_client.data_module.department_list.data.DDepartmentList;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospital;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospitalList;
import com.taixinkanghu_client.data_module.nurse_list.data.DFaceImages;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasics;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseList;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseSenior;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.RequestNurseBasicListEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurserOrderList;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;

public class DChangeNurseFlow
{
	private static DChangeNurseFlow s_dChangeNurseFlow = new DChangeNurseFlow();

	//01. 老护理员的数据
	private int    m_oldNurseID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncOldNurseID = new Object();

	//姓名
	private String m_patientName     = null;
	private Object m_syncPatientName = new Object();

	//手机号码：
	private String m_phone     = null;
	private Object m_syncPhone = new Object();

	//性别：男，女
	private EnumConfig.GenderStatus m_genderStatus = null;
	private Object                  m_syncGender   = new Object();

	//年龄
	private EnumConfig.AgeRage m_ageRage     = null;
	private Object             m_syncAgeRage = new Object();

	//体重
	private EnumConfig.WeightRage m_weightRage     = null;
	private Object                m_syncWeightRage = new Object();

	//医院
	private int    m_hospitalID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncHospitalID = new Object();

	//科室
	private int    m_departmenetID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncDepartmenetID = new Object();

	//病人状态
	private EnumConfig.PatientState m_patientState     = null;
	private Object                  m_syncPatientState = new Object();

	//护理时间
	private DNursingDate m_oldNursingDate     = null;
	private Object       m_syncOldNursingDate = new Object();


	//02. 选择护理员的数据(新护理员的数据)
	private int    m_newNurseID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncNewNurseID = new Object();

	//03. 护理员信息

	//04. 护理员订单页面数据
	private DNursingDate   m_newSelectDate     = null;
	private Object m_syncNewSelectDate = new Object();

	//TODO:理论上要保存发送给服务器的数据，先不做，以后补充上。
	private int    m_newOrderID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncNewOrderID = new Object();

	private String m_newOrderSerialNum     = null;
	private Object m_syncNewOrderSerialNum = new Object();

	//TODO:用两个价格，一个是客户端计算后，用来显示，并上送到服务器，不可信；一个是用来支付，来自于服务器下发，可信。
	//下面保存的是服务器下发的价格。显示的价格，每次都动态计算
	private int m_clientTotalPrice = DataConfig.DEFAULT_VALUE;
	private Object m_syncClientTotalPrice = new Object();

	private int    m_totalPrice     = DataConfig.DEFAULT_VALUE;
	private Object m_syncTotalPrice = new Object();


	private DChangeNurseFlow()
	{}

	public static DChangeNurseFlow GetInstance()
	{
		return s_dChangeNurseFlow;
	}

	public void clearupOldNurse()
	{
		synchronized (m_syncOldNurseID)
		{
			m_oldNurseID = DataConfig.DEFAULT_VALUE;
		}
		synchronized (m_syncPatientName)
		{
			m_patientName = null;
		}

		synchronized (m_syncPhone)
		{
			m_phone = null;
		}

		synchronized (m_syncGender)
		{
			m_genderStatus = null;
		}

		synchronized (m_syncAgeRage)
		{
			m_ageRage = null;
		}

		synchronized (m_syncWeightRage)
		{
			m_weightRage = null;
		}

		synchronized (m_syncHospitalID)
		{
			m_hospitalID = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncDepartmenetID)
		{
			m_departmenetID = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncPatientState)
		{
			m_patientState = null;
		}

		synchronized (m_syncOldNursingDate)
		{
			m_oldNursingDate = null;
		}
	}

	public void clearupSelectNurse()
	{
		synchronized (m_syncNewNurseID)
		{
			m_newNurseID = DataConfig.DEFAULT_VALUE;
		}
	}

	public void clearupNurseInfo()
	{}

	public void clearupNurseConfirm()
	{
		synchronized (m_syncNewSelectDate)
		{
			m_newSelectDate     = null;
		}

		synchronized (m_syncNewOrderID)
		{
			m_newOrderID     = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncNewOrderSerialNum)
		{
			m_newOrderSerialNum     = null;
		}

		synchronized (m_syncClientTotalPrice)
		{
			m_clientTotalPrice = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncTotalPrice)
		{
			m_totalPrice     = DataConfig.DEFAULT_VALUE;
		}

	}

	public void clearupAll()
	{
		//01. 清除老护理员的数据
		clearupOldNurse();

		//02. 清除选择护理员的数据
		clearupSelectNurse();

		//03. 清除护理员信息数据
		clearupNurseInfo();

		//04. 清除护理员订单数据
		clearupNurseConfirm();
	}

	/**
	 * function:constructor
	 */
	//01. RequestNurseBasicListEvent
	public RequestNurseBasicListEvent constructRequestNurseBasicListEvent() throws LogicalRTException
	{
		RequestNurseBasicListEvent event = new RequestNurseBasicListEvent();
		event.setName(getPatientName());
		event.setMobileNum(getPhone());

		if (getGenderStatus() != null)
			event.setGenderID(getGenderStatus().getId());

		if (getAgeRage() != null)
		{
			event.setAgeRage(getAgeRage().getName());
		}

		if (getWeightRage() != null)
		{
			event.setWeightRage(getWeightRage().getName());
		}

		event.setHospitalID(getHospitalID());
		event.setDepartmentID(getDepartmenetID());

		if (getPatientState() != null)
			event.setPatientStateID(getPatientState().getId());

		DNursingDate nursingDate = getOldNursingDate();
		if (nursingDate == null)
		{
			return null;
		}

		event.setSchedualAll(nursingDate.getSchedualAllDescription());
		event.setSchedualDay(nursingDate.getSchedualDayDescription());
		event.setSchedualNight(nursingDate.getSchedualNightDescription());

		return event;
	}


	/**
	 * function:get/set
	 */
	//01. 老护理员的数据
	public int getOldNurseID()
	{
		return m_oldNurseID;
	}

	public void setOldNurseID(int oldNurseID)
	{
		m_oldNurseID = oldNurseID;
	}

	public DNurseOrder getOldNurserOrder()
	{
		//TODO:多线程这里有问题。目前不会遇到多线程问题。
		DNurseOrder nurseOrder = DNurserOrderList.GetInstance().getNurseOrderByNurseID(getOldNurseID());
		if (nurseOrder == null)
		{
			return null;
		}

		return nurseOrder;

	}

	public String getPatientName()
	{
		synchronized (m_syncPatientName)
		{
			if (m_patientName == null)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return null;

				m_patientName = nurseOrder.getPatientName();
			}
			return m_patientName;
		}
	}

	public void setPatientName(String patientName)
	{
		synchronized (m_syncPatientName)
		{
			m_patientName = patientName;
		}
	}

	public String getPhone()
	{
		synchronized (m_syncPhone)
		{
			if (m_phone == null)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return null;

				m_phone = nurseOrder.getPhoneNum();
			}

			return m_phone;
		}
	}

	public void setPhone(String phone)
	{
		synchronized (m_syncPhone)
		{
			m_phone = phone;
		}
	}

	public EnumConfig.GenderStatus getGenderStatus()
	{
		synchronized (m_syncGender)
		{
			if (m_genderStatus == null)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return null;

				m_genderStatus = nurseOrder.getGenderStatus();
			}
			return m_genderStatus;
		}
	}

	public void setGenderStatus(EnumConfig.GenderStatus genderStatus)
	{
		synchronized (m_syncGender)
		{
			m_genderStatus = genderStatus;
		}
	}

	//03. EnumConfig.AgeRage
	public EnumConfig.AgeRage getAgeRage()
	{
		synchronized (m_syncAgeRage)
		{
			if (m_ageRage == null)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return null;

				m_ageRage = nurseOrder.getPatientAge();
			}
			return m_ageRage;
		}
	}

	public void setAgeRage(EnumConfig.AgeRage ageRage)
	{
		synchronized (m_syncAgeRage)
		{
			m_ageRage = ageRage;
		}
	}

	public EnumConfig.WeightRage getWeightRage()
	{
		synchronized (m_syncWeightRage)
		{
			if (m_weightRage == null)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return null;

				m_weightRage = nurseOrder.getPatientWeight();
			}
			return m_weightRage;
		}

	}

	public void setWeightRage(EnumConfig.WeightRage weightRage)
	{
		synchronized (m_syncWeightRage)
		{
			m_weightRage = weightRage;
		}
	}

	public int getHospitalID()
	{
		synchronized (m_syncHospitalID)
		{
			if (m_hospitalID == DataConfig.DEFAULT_VALUE)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return DataConfig.DEFAULT_VALUE;

				m_hospitalID = nurseOrder.getHospitalID();
			}
			return m_hospitalID;
		}
	}

	public void setHospitalID(int hospitalID)
	{
		synchronized (m_syncHospitalID)
		{
			m_hospitalID = hospitalID;
		}
	}

	public int getDepartmenetID()
	{
		synchronized (m_syncDepartmenetID)
		{
			if (m_departmenetID == DataConfig.DEFAULT_VALUE)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return DataConfig.DEFAULT_VALUE;

				m_departmenetID = nurseOrder.getDepartmentID();
			}
			return m_departmenetID;
		}
	}

	public void setDepartmenetID(int departmenetID)
	{
		synchronized (m_syncDepartmenetID)
		{
			m_departmenetID = departmenetID;
		}
	}

	public EnumConfig.PatientState getPatientState()
	{
		synchronized (m_syncPatientState)
		{
			if (m_patientState == null)
			{
				DNurseOrder nurseOrder = getOldNurserOrder();
				if (nurseOrder == null)
					return null;

				return nurseOrder.getPatientState();
			}
			return m_patientState;
		}

	}

	public void setPatientState(EnumConfig.PatientState patientState)
	{
		synchronized (m_syncPatientState)
		{
			m_patientState = patientState;
		}
	}

	public DNursingDate getOldNursingDate()
	{
		synchronized (m_syncOldNursingDate)
		{
			return m_oldNursingDate;
		}
	}

	public void setOldNursingDate(DNursingDate nursingDate)
	{
		synchronized (m_syncOldNursingDate)
		{
			m_oldNursingDate = nursingDate;
		}
	}

	//02. 选择护理员的数据
	public int getNewNurseID()
	{
		return m_newNurseID;
	}

	public void setNewNurseID(int newNurseID)
	{
		m_newNurseID = newNurseID;
	}


	//04. 确认订单数据
	public int getNewNurseHeaderImgResID()
	{
		int headerImgResID = DFaceImages.getInstance().getImgResIDbyID(getNewNurseID());
		if (headerImgResID == DataConfig.DEFAULT_VALUE)
		{
			headerImgResID = DFaceImages.DEFAULT_IMAGE_RES_ID;
		}
		return headerImgResID;
	}

	public int getOldNurseHeaderImgResID()
	{
		int headerImgResID = DFaceImages.getInstance().getImgResIDbyID(getOldNurseID());
		if (headerImgResID == DataConfig.DEFAULT_VALUE)
		{
			headerImgResID = DFaceImages.DEFAULT_IMAGE_RES_ID;
		}
		return headerImgResID;
	}

	public String getNewNurseName()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getNewNurseID());
		if (dNurseBasics == null)
			return null;

		return dNurseBasics.getName();
	}

	public String getOldNurseName()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getOldNurseID());
		if (dNurseBasics == null)
			return null;

		return dNurseBasics.getName();
	}

	public String getNewNursingLevel()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getNewNurseID());
		if (dNurseBasics == null)
			return null;

		return dNurseBasics.getNursingLevel();
	}

	public String getOldNursingLevel()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getOldNurseID());
		if (dNurseBasics == null)
			return null;

		return dNurseBasics.getNursingLevel();
	}

	public String getNewNurseJobNum()
	{
		DNurseSenior dNurseSenior = DNurseList.GetInstance().getNurseSeniorByID(getNewNurseID());
		if (dNurseSenior == null)
			return null;

		return dNurseSenior.getJobNum();
	}

	public String getOldNurseJobNum()
	{
		DNurseSenior dNurseSenior = DNurseList.GetInstance().getNurseSeniorByID(getOldNurseID());
		if (dNurseSenior == null)
			return null;

		return dNurseSenior.getJobNum();
	}

	public DNursingDate getNewSelectDate()
	{
		return m_newSelectDate;
	}

	public void setNewSelectDate(DNursingDate newSelectDate)
	{
		m_newSelectDate = newSelectDate;
	}


	public String getNewServiceDateDisplay()
	{
		if (getNewSelectDate() == null)
		{
			return null;
		}

		return getNewSelectDate().getDateDescription();
	}

	public String getOldServiceDateDisplay()
	{
		if (getOldNursingDate() == null)
		{
			return null;
		}

		return getOldNursingDate().getDateDescription();
	}

	public String getServiceAddress()
	{
		String    serviceAddress = null;
		DHospital hospital       = DHospitalList.GetInstance().getHospitalByID(getHospitalID());
		if (hospital == null)
		{
			return null;
		}
		String hospitalName = hospital.getName();

		DDepartment department = DDepartmentList.GetInstance().getDepartmentByID(getDepartmenetID());
		if (department == null)
		{
			return null;
		}
		String departmentName = department.getName();
		return (hospitalName + departmentName);

	}

	public int getNewAllNum()
	{
		if (getNewSelectDate() == null)
		{
			return 0;
		}

		return getNewSelectDate().getAllNum();
	}

	public int getOldAllNum()
	{
		if (getOldNursingDate() == null)
		{
			return 0;
		}

		return getOldNursingDate().getAllNum();
	}

	public int getNewDayNum()
	{
		if (getNewSelectDate() == null)
		{
			return 0;
		}

		return getNewSelectDate().getDayNum();
	}

	public int getOldDayNum()
	{
		if (getOldNursingDate() == null)
		{
			return 0;
		}

		return getOldNursingDate().getDayNum();
	}

	public int getNewNightNum()
	{
		if (getNewSelectDate() == null)
		{
			return 0;
		}

		return getNewSelectDate().getNightNum();
	}

	public int getOldNightNum()
	{
		if (getOldNursingDate() == null)
		{
			return 0;
		}

		return getOldNursingDate().getNightNum();
	}

	public int getNewChargePerAll()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getNewNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_ALL, getPatientState());

	}

	public int getOldChargePerAll()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getOldNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_ALL, getPatientState());

	}

	public int getNewChargePerDay()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getNewNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_DAY, getPatientState());
	}

	public int getOldChargePerDay()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getOldNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_DAY, getPatientState());
	}

	public int getNewChargePerNight()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getNewNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_NIGHT, getPatientState());
	}

	public int getOldChargePerNight()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getOldNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_NIGHT, getPatientState());
	}

	public int getNewTotalChargeDisplay()
	{
		//天数：全天24小时，白天12小时，黑天12小时
		int allNum   = getNewAllNum();
		int dayNum   = getNewDayNum();
		int nightNum = getNewNightNum();

		//单价：全天24小时，白天12小时，黑天12小时
		int chargePerAll   = getNewChargePerAll();
		int chargePerDay   = getNewChargePerDay();
		int chargePerNight = getNewChargePerNight();

		//总价格
		int totalCharge = allNum * chargePerAll + dayNum * chargePerDay + nightNum * chargePerNight;

		return totalCharge;

	}

	public int getNewNeedPayTotalChargeDisplay()
	{
		//天数：全天24小时，白天12小时，黑天12小时
		int allNum   = getNewAllNum();
		int dayNum   = getNewDayNum();
		int nightNum = getNewNightNum();

		//单价：全天24小时，白天12小时，黑天12小时
		int chargePerAll   = getOldChargePerAll();
		int chargePerDay   = getOldChargePerDay();
		int chargePerNight = getOldChargePerNight();

		//总价格
		int oldDeltalTotalCharge = allNum * chargePerAll + dayNum * chargePerDay + nightNum * chargePerNight;
		int newTotalCharge = getNewTotalChargeDisplay();

		return (newTotalCharge - oldDeltalTotalCharge);

	}


	public int getOldTotalChargeDisplay()
	{
		//天数：全天24小时，白天12小时，黑天12小时
		int allNum   = getOldAllNum();
		int dayNum   = getOldDayNum();
		int nightNum = getOldNightNum();

		//单价：全天24小时，白天12小时，黑天12小时
		int chargePerAll   = getOldChargePerAll();
		int chargePerDay   = getOldChargePerDay();
		int chargePerNight = getOldChargePerNight();

		//总价格
		int totalCharge = allNum * chargePerAll + dayNum * chargePerDay + nightNum * chargePerNight;

		return totalCharge;

	}

	public int getTotalPrice()
	{
		synchronized (m_syncTotalPrice)
		{
			return m_totalPrice;
		}
	}

	public void setTotalPrice(int totalPrice)
	{
		synchronized (m_syncTotalPrice)
		{
			m_totalPrice = totalPrice;
		}
	}

	public String getNewOrderSerialNum()
	{
		synchronized (m_syncNewOrderSerialNum)
		{
			return m_newOrderSerialNum;
		}
	}

	public void setNewOrderSerialNum(String newOrderSerialNum)
	{
		synchronized (m_syncNewOrderSerialNum)
		{
			m_newOrderSerialNum = newOrderSerialNum;
		}
	}

	public int getNewOrderID()
	{
		synchronized (m_syncTotalPrice)
		{
			return m_newOrderID;
		}
	}

	public void setNewOrderID(int newOrderID)
	{
		synchronized (m_syncTotalPrice)
		{
			m_newOrderID = newOrderID;
		}
	}

	public int getClientTotalPrice()
	{
		synchronized (m_syncClientTotalPrice)
		{
			return m_clientTotalPrice;
		}
	}

	public void setClientTotalPrice(int clientTotalPrice)
	{
		synchronized (m_syncClientTotalPrice)
		{
			m_clientTotalPrice = clientTotalPrice;
		}
	}
}
