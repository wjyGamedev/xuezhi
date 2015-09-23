/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.patient_info_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.repeat_order_flow.patient_info_page.msg_handler;

import android.app.FragmentTransaction;
import android.text.TextUtils;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.department_list.data.DDepartment;
import com.taixinkanghu_client.data_module.department_list.data.DDepartmentList;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospital;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospitalList;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.repeat_order_flow.BaseRepeatFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.repeat_order_flow.patient_info_page.ui.PatientActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.patient_info_page.ui.SelectAgeFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.patient_info_page.ui.SelectSexFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.patient_info_page.ui.SelectWeightFragment;

import java.util.ArrayList;

public class PatientMsgHandler extends BaseRepeatFlowUIMsgHandler
{
	public PatientMsgHandler(PatientActivity activity)
	{
		super(activity);
	}

	//01. 保存填写信息
	public void saveContent()
	{
		PatientActivity activity = (PatientActivity)m_context;

		String name = activity.getNameET().getText().toString().trim();
		if (!TextUtils.isEmpty(name))
			m_dRepeatOrderFlow.setPatientName(name);

		String mobileNum = activity.getPhoneNumET().getText().toString().trim();
		if (!TextUtils.isEmpty(mobileNum))
			m_dRepeatOrderFlow.setPhone(mobileNum);

		return;
	}

	//02. 返回到订单确认页面
	//由于订单确认页面，在该页面之下。当前是从订单确认页面跳转过来的。所以直接销毁本地就可以了。
	public void requestAppiontmentNursingAction()
	{
		PatientActivity activity  = (PatientActivity)m_context;
		activity.finish();
		return;
	}

	//03. 更新数据到UI
	public void updateContent()
	{
		PatientActivity patientActivity  = (PatientActivity)m_context;

		//姓名
		String name = m_dRepeatOrderFlow.getPatientName();
		if (TextUtils.isEmpty(name) == false)
		{
			patientActivity.getNameET().setText(name);
		}

		//手机号
		String phone = m_dRepeatOrderFlow.getPhone();
		if (TextUtils.isEmpty(phone) == false)
		{
			patientActivity.getPhoneNumET().setText(phone);
		}

		//性别
		EnumConfig.GenderStatus genderStatus = m_dRepeatOrderFlow.getGenderStatus();
		if (genderStatus != null)
		{
			patientActivity.getGenderTV().setText(genderStatus.getName());
		}

		//年龄
		EnumConfig.AgeRage ageRage = m_dRepeatOrderFlow.getAgeRage();
		if (ageRage != null)
		{
			patientActivity.getAgeTV().setText(ageRage.getName());
		}

		//体重
		EnumConfig.WeightRage weightRage = m_dRepeatOrderFlow.getWeightRage();
		if (weightRage != null)
		{
			patientActivity.getWeightTV().setText(weightRage.getName());
		}

		//所在医院
		int hospitalID = m_dRepeatOrderFlow.getHospitalID();
		//01. 显示全部
		if (hospitalID == 0)
		{
			patientActivity.getHospitalTV().setText(patientActivity.getString(R.string.content_all));
		}
		//02. 显示hospitalname
		else
		{
			ArrayList<DHospital> hospitals = DHospitalList.GetInstance().getHospitals();
			for (DHospital hospital : hospitals)
			{
				if (hospital.getID() == hospitalID)
				{
					patientActivity.getHospitalTV().setText(hospital.getName());
					break;
				}
			}
		}

		//所在科室
		int                    departmentID   = m_dRepeatOrderFlow.getDepartmenetID();
		ArrayList<DDepartment> departmentList = DDepartmentList.GetInstance().getDepartments();
		for (DDepartment department : departmentList)
		{
			if (department.getID() == departmentID)
			{
				patientActivity.getDepartmentTV().setText(department.getName());
				break;
			}
		}

		//病人状态
		EnumConfig.PatientState patientState = m_dRepeatOrderFlow.getPatientState();
		if (patientState != null)
		{
			patientActivity.getPatientStateTV().setText(patientState.getName());
		}

		//护理时间
		DNursingDate nursingDate = m_dRepeatOrderFlow.getNursingDate();
		if (nursingDate != null)
		{
			String dateDescription = nursingDate.getDateDescription();
			patientActivity.getServiceDateTV().setText(dateDescription);
		}

		return;
	}

	//04. 跳转到选择性别fragment
	public void go2SelectGenderFragment()
	{
		PatientActivity patientActivity  = (PatientActivity)m_context;

		Integer genderTitleHight = patientActivity.getSelectGenderTitleHight();
		SelectSexFragment selectSexFragment = new SelectSexFragment();
		selectSexFragment.setGenderTitleHight(genderTitleHight);

		FragmentTransaction transaction = patientActivity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.patient_page, selectSexFragment, selectSexFragment.getClass().getName());
		transaction.commit();
		return;
	}

	//05. 跳转到选择年龄fragment
	public void go2SelectAgeFragment()
	{
		PatientActivity patientActivity  = (PatientActivity)m_context;

		Integer ageTitleHight = patientActivity.getSelectAgeTitleHight();
		SelectAgeFragment selectAgeFragment = new SelectAgeFragment();
		selectAgeFragment.setAgeTitleHight(ageTitleHight);

		FragmentTransaction transaction = patientActivity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.patient_page, selectAgeFragment, selectAgeFragment.getClass().getName());
		transaction.commit();
		return;
	}

	//06. 跳转到选择体重fragment
	public void go2SelectWeightFragment()
	{
		PatientActivity patientActivity  = (PatientActivity)m_context;

		Integer weightTitleHight = patientActivity.getSelectWeightTitleHight();
		SelectWeightFragment selectWeightFragment = new SelectWeightFragment();
		selectWeightFragment.setWeightTitleHight(weightTitleHight);

		FragmentTransaction transaction = patientActivity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.patient_page, selectWeightFragment, selectWeightFragment.getClass().getName());
		transaction.commit();
		return;
	}

	//07. 数据设置(外部调用)
	public void setGenderStatus(EnumConfig.GenderStatus genderStatus)
	{
		PatientActivity patientActivity  = (PatientActivity)m_context;

		patientActivity.getGenderTV().setText(genderStatus.getName());
		m_dRepeatOrderFlow.setGenderStatus(genderStatus);
	}

	public void setAgeRage(EnumConfig.AgeRage ageRage)
	{
		PatientActivity patientActivity  = (PatientActivity)m_context;

		patientActivity.getAgeTV().setText(ageRage.getName());
		m_dRepeatOrderFlow.setAgeRage(ageRage);
	}

	public void setWeightRage(EnumConfig.WeightRage weightRage)
	{
		PatientActivity patientActivity  = (PatientActivity)m_context;

		patientActivity.getWeightTV().setText(weightRage.getName());
		m_dRepeatOrderFlow.setWeightRage(weightRage);
	}

}
