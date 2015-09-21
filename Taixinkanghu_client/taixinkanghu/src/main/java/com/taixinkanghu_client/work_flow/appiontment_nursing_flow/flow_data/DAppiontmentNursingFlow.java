/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/8		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data;

import com.module.widget.dialog.TipsDialog;
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
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderConfirmInNormalEvent;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;

public class DAppiontmentNursingFlow
{
	private static DAppiontmentNursingFlow s_appiontmentNursing = new DAppiontmentNursingFlow();

	//01. 预约陪护数据
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
	private DNursingDate m_nursingDate     = null;
	private Object       m_syncNursingDate = new Object();

	//02. 选择护理员数据
	private int    m_selectedNurseID = DataConfig.DEFAULT_VALUE;
	private Object m_syncNurseID     = new Object();

	//03. 护理员详细信息数据

	//04. 确认订单数据
	//TODO:理论上要保存发送给服务器的数据，先不做，以后补充上。
	private int    m_orderID     = DataConfig.DEFAULT_VALUE;
	private Object m_syncOrderID = new Object();

	private String m_orderSerialNum     = null;
	private Object m_syncOrderSerialNum = new Object();

	//TODO:用两个价格，一个是客户端计算后，用来显示，并上送到服务器，不可信；一个是用来支付，来自于服务器下发，可信。
	//下面保存的是服务器下发的价格。显示的价格，每次都动态计算
	private int    m_totalPrice     = DataConfig.DEFAULT_VALUE;
	private Object m_syncTotalPrice = new Object();

	private DAppiontmentNursingFlow()
	{}

	public static DAppiontmentNursingFlow GetInstance()
	{
		return s_appiontmentNursing;
	}

	public void clearupAppitonmentNursing()
	{
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

		synchronized (m_syncNursingDate)
		{
			m_nursingDate = null;
		}
	}

	public void clearupSelectNurse()
	{
		synchronized (m_syncNurseID)
		{
			m_selectedNurseID = DataConfig.DEFAULT_VALUE;
		}
	}

	public void clearupNurseInfo()
	{
		return;
	}

	public void clearupOrderConfirm()
	{
		synchronized (m_syncOrderID)
		{
			m_orderID = DataConfig.DEFAULT_VALUE;
		}

		synchronized (m_syncOrderSerialNum)
		{
			m_orderSerialNum = null;
		}

		synchronized (m_syncTotalPrice)
		{
			m_totalPrice = DataConfig.DEFAULT_VALUE;
		}
	}

	public void clearupAll()
	{
		//01. 预约陪护数据
		clearupAppitonmentNursing();

		//02. 选择护理员数据
		clearupSelectNurse();

		//03. 护理员详细信息数据
		clearupNurseInfo();

		//04. 确认订单数据
		clearupOrderConfirm();

	}

	/**
	 * function:constructor
	 */
	//01. RequestNurseBasicListEvent
	public RequestNurseBasicListEvent constructRequestNurseBasicListEvent()
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

		DNursingDate nursingDate = getNursingDate();
		if (nursingDate == null)
		{
			TipsDialog.GetInstance().setMsg("nursingDate == null");
			TipsDialog.GetInstance().show();
			return null;
		}

		event.setSchedualAll(nursingDate.getSchedualAllDescription());
		event.setSchedualDay(nursingDate.getSchedualDayDescription());
		event.setSchedualNight(nursingDate.getSchedualNightDescription());

		return event;
	}

	//02. RequestNurseOrderConfirmInNormalEvent
	public RequestNurseOrderConfirmInNormalEvent constructRequestNurseOrderConfirmInNormalEvent()
	{
		RequestNurseOrderConfirmInNormalEvent event = new RequestNurseOrderConfirmInNormalEvent();
		event.setHospitalID(getHospitalID());
		event.setUserID(DAccount.GetInstance().getId());
		event.setNurseID(getSelectedNurseID());
		event.setNurseDepartmentID(getDepartmenetID());
		event.setPhoneNum(getPhone());
		event.setPatientName(getPatientName());

		if (getGenderStatus() != null)
			event.setGenderID(getGenderStatus().getId());

		if (getAgeRage() != null)
		{
			event.setPatientAge(getAgeRage().getName());
		}

		if (getWeightRage() != null)
		{
			event.setPatientWeight(getWeightRage().getName());
		}

		if (getPatientState() != null)
			event.setPatientStatusID(getPatientState().getId());

		//TODO:以后更改
		event.setPatientRemark("");

		event.setTotalCharge(getTotalChargeDisplay());

		if (getNursingDate() == null)
		{
			TipsDialog.GetInstance().setMsg("nursingDate == null");
			TipsDialog.GetInstance().show();
			//TODO:以后更改为抛出异常
			return null;
		}

		String allDescription = getNursingDate().getSchedualAllDescription();
		event.setAllDescription(allDescription);

		String dayDescription = getNursingDate().getSchedualDayDescription();
		event.setDayDescription(dayDescription);

		String nightDescription = getNursingDate().getSchedualNightDescription();
		event.setNightDescription(nightDescription);

		return event;

	}

	/**
	 * function:get/set
	 */
	//01. 预约陪护func
	public String getPatientName()
	{
		synchronized (m_syncPatientName)
		{
			return m_patientName;
		}
	}

	public void setPatientName(String name)
	{
		synchronized (m_syncPatientName)
		{
			m_patientName = name;
		}
	}

	public String getPhone()
	{
		synchronized (m_syncPhone)
		{
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

	public EnumConfig.AgeRage getAgeRage()
	{
		synchronized (m_syncAgeRage)
		{
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

	public DNursingDate getNursingDate()
	{
		synchronized (m_syncNursingDate)
		{
			return m_nursingDate;
		}
	}

	public void setNursingDate(DNursingDate nursingDate)
	{
		synchronized (m_syncNursingDate)
		{
			m_nursingDate = nursingDate;
		}
	}

	//02. 选择护理员func
	public int getSelectedNurseID()
	{
		synchronized (m_syncNurseID)
		{
			return m_selectedNurseID;
		}
	}

	public void setSelectedNurseID(int selectedNurseID)
	{
		synchronized (m_syncNurseID)
		{
			m_selectedNurseID = selectedNurseID;
		}
	}

	//04. 确认订单数据
	public int getNurseHeaderImgResID()
	{
		int headerImgResID = DFaceImages.getInstance().getImgResIDbyID(getSelectedNurseID());
		if (headerImgResID == DataConfig.DEFAULT_VALUE)
		{
			headerImgResID = DFaceImages.DEFAULT_IMAGE_RES_ID;
		}
		return headerImgResID;
	}

	public String getNurseName()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getSelectedNurseID());
		if (dNurseBasics == null)
			return null;

		return dNurseBasics.getName();
	}

	public String getNursingLevel()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getSelectedNurseID());
		if (dNurseBasics == null)
			return null;

		return dNurseBasics.getNursingLevel();
	}

	public String getNurseJobNum()
	{
		DNurseSenior dNurseSenior = DNurseList.GetInstance().getNurseSeniorByID(getSelectedNurseID());
		if (dNurseSenior == null)
			return null;

		return dNurseSenior.getJobNum();
	}

	public String getServiceDate()
	{
		if (getNursingDate() == null)
		{
			return null;
		}

		return getNursingDate().getDateDescription();
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

		DDepartment department   = DDepartmentList.GetInstance().getDepartmentByID(getDepartmenetID());
		if (department == null)
		{
			return null;
		}
		String departmentName = department.getName();
		return (hospitalName + departmentName);

	}

	public int getAllNum()
	{
		if (getNursingDate() == null)
		{
			return 0;
		}

		return getNursingDate().getAllNum();
	}

	public int getDayNum()
	{
		if (getNursingDate() == null)
		{
			return 0;
		}

		return getNursingDate().getDayNum();
	}

	public int getNightNum()
	{
		if (getNursingDate() == null)
		{
			return 0;
		}

		return getNursingDate().getNightNum();
	}

	public int getChargePerAll()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getSelectedNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_ALL, getPatientState());

	}

	public int getChargePerDay()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getSelectedNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_DAY, getPatientState());
	}

	public int getChargePerNight()
	{
		DNurseBasics dNurseBasics = DNurseList.GetInstance().getNurseBasicByID(getSelectedNurseID());
		if (dNurseBasics == null)
			return 0;

		if (getPatientState() == null)
			return 0;

		return dNurseBasics.getServiceCharge(DataConfig.SELECT_DAY_TYEP_NIGHT, getPatientState());
	}

	public int getTotalChargeDisplay()
	{
		//天数：全天24小时，白天12小时，黑天12小时
		int allNum   = getAllNum();
		int dayNum   = getDayNum();
		int nightNum = getNightNum();

		//单价：全天24小时，白天12小时，黑天12小时
		int chargePerAll   = getChargePerAll();
		int chargePerDay   = getChargePerDay();
		int chargePerNight = getChargePerNight();

		//总价格
		int totalCharge = allNum * chargePerAll + dayNum * chargePerDay + nightNum * chargePerNight;

		return totalCharge;

	}


	public int getOrderID()
	{
		synchronized (m_syncOrderID)
		{
			return m_orderID;
		}
	}

	public void setOrderID(int orderID)
	{
		synchronized(m_syncOrderID)
		{
			m_orderID = orderID;
		}
	}

	public String getOrderSerialNum()
	{
		synchronized(m_syncOrderSerialNum)
		{
			return m_orderSerialNum;
		}
	}

	public void setOrderSerialNum(String orderSerialNum)
	{
		synchronized(m_syncOrderSerialNum)
		{
			m_orderSerialNum = orderSerialNum;
		}
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


}
