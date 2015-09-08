/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/19		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_order_list.data;


import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasics;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseSenior;
import com.taixinkanghu_client.data_module.nurse_list.data.DScheduleList;
import com.taixinkanghu_client.net.config.NurseOrderConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DNurseOrder
{
	private SimpleDateFormat m_simpleDateFormat = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	//订单信息
	private String                      m_orderSerialNum = null;    //订单的交易流水号
	private int                         m_hospitalID     = DataConfig.DEFAULT_VALUE; //选择医院ID
	private int                         m_userID         = DataConfig.DEFAULT_VALUE; //用户ID
	private int                         m_nurseID        = DataConfig.DEFAULT_VALUE; //护工ID
	private String                      m_patientName    = null;    //患者名字
	private EnumConfig.GenderStatus     m_genderStatus   = null; //患者性别
	private EnumConfig.AgeRage          m_patientAge     = null; //患者年龄
	private EnumConfig.WeightRage       m_patientWeight  = null;    //患者体重
	private EnumConfig.PatientState     m_patientState   = null; //患者的状态：自理，半自理，不能自理
	private String                      m_patientRemark  = null;     //备注
	private String                      m_phoneNum       = null; //联系的手机号
	private EnumConfig.NurseOrderStatus m_orderStatus    = null; //订单状态
	private Calendar                    m_orderTime      = Calendar.getInstance(); //订单的时间
	private int                         m_orderID        = DataConfig.DEFAULT_VALUE; //订单ID
	private int                         m_totalCharge    = DataConfig.DEFAULT_VALUE; //订单价格
	private DScheduleList               m_scheduleList   = new DScheduleList();    //订单的服务时间
	private int                         m_departmentID   = DataConfig.DEFAULT_VALUE; //选择科室的ID
	private Date                        m_beginDate      = null;    //服务的开始时间
	private Date                        m_endDate        = null;    //服务的结束时间

	//护理员信息
	DNurseBasics m_nurseBasics = new DNurseBasics();
	DNurseSenior m_nurseSenior = new DNurseSenior();


	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_orderSerialNum = response.getString(NurseOrderConfig.ORDER_SERIAL_NUM);
		m_hospitalID = response.getInt(NurseOrderConfig.HOSPITAL_ID);
		m_userID = response.getInt(NurseOrderConfig.USER_ID);
		m_nurseID = response.getInt(NurseOrderConfig.NURSE_ID);
		m_patientName = response.getString(NurseOrderConfig.PATIENT_NAME);

		int genderId = response.getInt(NurseOrderConfig.PATIENT_GENDER);
		m_genderStatus = EnumConfig.GenderStatus.valueOf(genderId);

		String ageRage = response.getString(NurseOrderConfig.PATIENT_AGE);
		m_patientAge = EnumConfig.AgeRage.valueOfFromString(ageRage);

		String weightRage = response.getString(NurseOrderConfig.PATIENT_WEIGHT);
		m_patientWeight = EnumConfig.WeightRage.valueOfFromString(weightRage);


		int patientStatus = response.getInt(NurseOrderConfig.PATIENT_STATUS);
		m_patientState = EnumConfig.PatientState.valueOf(patientStatus);
		m_patientRemark = response.getString(NurseOrderConfig.PATIENT_REMARK);
		m_phoneNum = response.getString(NurseOrderConfig.USER_PHONE_NUM);

		int orderStateID = response.getInt(NurseOrderConfig.ORDER_STATUS);
		m_orderStatus = EnumConfig.NurseOrderStatus.valueOf(orderStateID);

		String orderTime = response.getString(NurseOrderConfig.ORDER_TIME);
		Date   date      = m_simpleDateFormat.parse(orderTime);
		m_orderTime.setTime(date);
		m_totalCharge = response.getInt(NurseOrderConfig.ORDER_TOTAL_CHARGE);
		m_orderID = response.getInt(NurseOrderConfig.ORDER_ID);
		m_scheduleList.serialization(response);
		m_departmentID = response.getInt(NurseOrderConfig.DEPARTMENT_ID);
		//通过m_scheduleList来计算起始和终止日期
		m_beginDate = m_scheduleList.getBeginDate();
		m_endDate = m_scheduleList.getEndDate();

		JSONObject jsonNurseInfo = response.getJSONObject(NurseOrderConfig.NURSE_INFO);
		m_nurseBasics.serialization(jsonNurseInfo);
		m_nurseSenior.serialization(jsonNurseInfo);
		return true;

	}

	public String getOrderSerialNum()
	{
		return m_orderSerialNum;
	}

	public int getHospitalID()
	{
		return m_hospitalID;
	}

	public int getUserID()
	{
		return m_userID;
	}

	public int getNurseID()
	{
		return m_nurseID;
	}

	public String getPatientName()
	{
		return m_patientName;
	}

	public EnumConfig.GenderStatus getGenderStatus()
	{
		return m_genderStatus;
	}

	public EnumConfig.AgeRage getPatientAge()
	{
		return m_patientAge;
	}

	public EnumConfig.WeightRage getPatientWeight()
	{
		return m_patientWeight;
	}

	public EnumConfig.PatientState getPatientState()
	{
		return m_patientState;
	}

	public String getPatientRemark()
	{
		return m_patientRemark;
	}

	public String getPhoneNum()
	{
		return m_phoneNum;
	}

	public EnumConfig.NurseOrderStatus getOrderStatus()
	{
		return m_orderStatus;
	}

	public Calendar getOrderTime()
	{
		return m_orderTime;
	}

	public int getOrderID()
	{
		return m_orderID;
	}

	public int getTotalCharge()
	{
		return m_totalCharge;
	}

	public DScheduleList getScheduleList()
	{
		return m_scheduleList;
	}

	public int getDepartmentID()
	{
		return m_departmentID;
	}

	public DNurseBasics getNurseBasics()
	{
		return m_nurseBasics;
	}

	public DNurseSenior getNurseSenior()
	{
		return m_nurseSenior;
	}

	public Date getBeginDate()
	{
		return m_beginDate;
	}

	public Date getEndDate()
	{
		return m_endDate;
	}
}
