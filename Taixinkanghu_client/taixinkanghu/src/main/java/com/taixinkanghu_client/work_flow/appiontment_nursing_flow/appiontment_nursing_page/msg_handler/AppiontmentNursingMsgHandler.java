/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/8		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.msg_handler;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.text.TextUtils;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.department_list.data.DDepartment;
import com.taixinkanghu_client.data_module.department_list.data.DDepartmentList;
import com.taixinkanghu_client.data_module.department_list.msg_handler.AnswerDepartmentListEvent;
import com.taixinkanghu_client.data_module.department_list.msg_handler.DepartmentMsgHandler;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospital;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospitalList;
import com.taixinkanghu_client.data_module.hospital_list.msg_handler.AnswerHospitalListEvent;
import com.taixinkanghu_client.data_module.hospital_list.msg_handler.HospitalMsgHandler;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.NurseListHandler;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.RequestNurseBasicListEvent;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.BaseAppiontmentNursingFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui.ApoitNursingActivity;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui.SelectAgeFragment;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui.SelectDepartmentFragment;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui.SelectHospitalFragment;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui.SelectPatientStateFragment;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui.SelectSexFragment;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui.SelectWeightFragment;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_date_page.ui.SelectDateActivity;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_nurse_page.ui.SelectNurseActivity;

import java.util.ArrayList;

public class AppiontmentNursingMsgHandler extends BaseAppiontmentNursingFlowUIMsgHandler
{
	public AppiontmentNursingMsgHandler(ApoitNursingActivity activity)
	{
		super(activity);
	}

	//01. 使用预约陪护流程的数据来填充当前UI界面。
	public void updateContent()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		//姓名
		String name = m_dAppiontmentNursingFlow.getPatientName();
		if (TextUtils.isEmpty(name) == false)
		{
			activity.getNameET().setText(name);
		}

		//手机号
		String phone = m_dAppiontmentNursingFlow.getPhone();
		if (TextUtils.isEmpty(phone) == false)
		{
			activity.getPhoneNumET().setText(phone);
		}

		//性别
		EnumConfig.GenderStatus genderStatus = m_dAppiontmentNursingFlow.getGenderStatus();
		if (genderStatus != null)
		{
			activity.getGenderTV().setText(genderStatus.getName());
		}

		//年龄
		EnumConfig.AgeRage ageRage = m_dAppiontmentNursingFlow.getAgeRage();
		if (ageRage != null)
		{
			activity.getAgeTV().setText(ageRage.getName());
		}

		//体重
		EnumConfig.WeightRage weightRage = m_dAppiontmentNursingFlow.getWeightRage();
		if (weightRage != null)
		{
			activity.getWeightTV().setText(weightRage.getName());
		}

		//所在医院
		int hospitalID = m_dAppiontmentNursingFlow.getHospitalID();
		//01. 显示全部
		if (hospitalID == 0)
		{
			activity.getHospitalTV().setText(activity.getResources().getString(R.string.content_all));
		}
		//02. 显示hospitalname
		else
		{
			ArrayList<DHospital> hospitals = DHospitalList.GetInstance().getHospitals();
			for (DHospital hospital : hospitals)
			{
				if (hospital.getID() == hospitalID)
				{
					activity.getHospitalTV().setText(hospital.getName());
					break;
				}
			}
		}

		//所在科室
		int                    departmentID   = m_dAppiontmentNursingFlow.getDepartmenetID();
		ArrayList<DDepartment> departmentList = DDepartmentList.GetInstance().getDepartments();
		for (DDepartment department : departmentList)
		{
			if (department.getID() == departmentID)
			{
				activity.getDepartmentTV().setText(department.getName());
				break;
			}
		}

		//病人状态
		EnumConfig.PatientState patientState = m_dAppiontmentNursingFlow.getPatientState();
		if (patientState != null)
		{
			activity.getPatientStateTV().setText(patientState.getName());
		}

		//护理时间
		DNursingDate nursingDate = m_dAppiontmentNursingFlow.getNursingDate();
		if (nursingDate != null)
		{
			String dateDescription = nursingDate.getDateDescription();
			activity.getServiceDateTV().setText(dateDescription);
		}

	}

	//02. 数据设置(外部调用)
	public void setGenderStatus(EnumConfig.GenderStatus genderStatus)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		activity.getGenderTV().setText(genderStatus.getName());
		m_dAppiontmentNursingFlow.setGenderStatus(genderStatus);
	}

	public void setAgeRage(EnumConfig.AgeRage ageRage)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		activity.getAgeTV().setText(ageRage.getName());
		m_dAppiontmentNursingFlow.setAgeRage(ageRage);
	}

	public void setWeightRage(EnumConfig.WeightRage weightRage)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		activity.getWeightTV().setText(weightRage.getName());
		m_dAppiontmentNursingFlow.setWeightRage(weightRage);
	}

	public void setDepartmentID(int departmentID)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		//01. set department ui
		ArrayList<DDepartment> departmentList = DDepartmentList.GetInstance().getDepartments();
		for (DDepartment department : departmentList)
		{
			if (department.getID() == departmentID)
			{
				activity.getDepartmentTV().setText(department.getName());
			}
		}

		m_dAppiontmentNursingFlow.setDepartmenetID(departmentID);
	}

	public void setHospitalID(int hospitalID)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		//01. 显示全部
		if (hospitalID == 0)
		{
			activity.getHospitalTV().setText(activity.getString(R.string.content_all));
		}
		//02. 显示hospitalname
		else
		{
			ArrayList<DHospital> hospitals = DHospitalList.GetInstance().getHospitals();
			for (DHospital hospital : hospitals)
			{
				if (hospital.getID() == hospitalID)
				{
					activity.getHospitalTV().setText(hospital.getName());
				}
			}
		}

		//03. 保存到数据类中。
		m_dAppiontmentNursingFlow.setHospitalID(hospitalID);

	}

	public void setPatientState(EnumConfig.PatientState patientState)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		activity.getPatientStateTV().setText(patientState.getName());
		m_dAppiontmentNursingFlow.setPatientState(patientState);
		return;

	}

	//03. 存储date到DAppiontmentNursing中
	//这里仅需要姓名和手机号码，因为其他数据在设置的时候，就已经进行了同步。
	public void saveContent()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		String name = activity.getNameET().getText().toString().trim();
		if (!TextUtils.isEmpty(name))
			m_dAppiontmentNursingFlow.setPatientName(name);

		String mobileNum = activity.getPhoneNumET().getText().toString().trim();
		if (!TextUtils.isEmpty(mobileNum))
			m_dAppiontmentNursingFlow.setPhone(mobileNum);

		return;

	}

	//04. 请求发预约陪护信息
	public void requestAppiontmentNursingAction()
	{
		RequestNurseBasicListEvent event = m_dAppiontmentNursingFlow.constructRequestNurseBasicListEvent();
		if (event == null)
		{
			TipsDialog.GetInstance().setMsg("event == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}
		NurseListHandler.GetInstance().requestNurseBasicListAction(event);
	}

	//05. 跳转到护理员列表玉面
	public void go2SelectNursePage()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;
		activity.startActivity(new Intent(activity, SelectNurseActivity.class));
		return;
	}

	//06. 请求发送医院列表
	public void requestHospitalListAction()
	{
		HospitalMsgHandler.GetInstance().requestHospitalListAction();
		return;
	}

	public void onEventMainThread(AnswerHospitalListEvent event)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;
		Fragment             fragment = activity.getFragmentManager().findFragmentByTag(SelectHospitalFragment.class.getName());
		if (fragment == null)
		{
			return;
		}

		SelectHospitalFragment selectHospitalFragment = (SelectHospitalFragment)fragment;
		selectHospitalFragment.loadHospitalList();
		return;
	}

	//07. 请求发送科室列表
	public void requestDepartmentListAction()
	{
		DepartmentMsgHandler.GetInstance().requestDepartmentListAction();
		return;
	}

	public void onEventMainThread(AnswerDepartmentListEvent event)
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;
		Fragment             fragment = activity.getFragmentManager().findFragmentByTag(SelectDepartmentFragment.class.getName());
		if (fragment == null)
		{
			return;
		}

		SelectDepartmentFragment selectDepartmentFragment = (SelectDepartmentFragment)fragment;
		selectDepartmentFragment.loadDepartmentList();
		return;
	}

	//08. 跳转到选择性别fragment
	public void go2SelectGenderFragment()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		SelectSexFragment selectSexFragment = new SelectSexFragment();
		selectSexFragment.setGenderTitleHight(activity.getSelectGenderTitleHight());

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectSexFragment, selectSexFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//09. 跳转到选择年龄fragment
	public void go2SelectAgeFragmnet()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		SelectAgeFragment selectAgeFragment = new SelectAgeFragment();
		selectAgeFragment.setAgeTitleHight(activity.getSelectAgeTitleHight());

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectAgeFragment, selectAgeFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//10. 跳转到选择体重fragment
	public void go2SelectWeightFragmnet()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		SelectWeightFragment selectWeightFragment = new SelectWeightFragment();
		selectWeightFragment.setWeightTitleHight(activity.getSelectWeightTitleHight());

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectWeightFragment, selectWeightFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//11. 跳转到选择医院列表fragment
	public void go2SelectHospitalFragment()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		SelectHospitalFragment selectHospitalFragment = new SelectHospitalFragment();

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectHospitalFragment, selectHospitalFragment.getClass().getName());
		transaction.commit();

	}

	//12. 跳转到选择科室列表fragment
	public void go2SelectDepartmentFragment()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		SelectDepartmentFragment selectDepartmentFragment = new SelectDepartmentFragment();

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectDepartmentFragment, selectDepartmentFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//13. 跳转到选择护理员状态页面
	public void go2PatientStateFragment()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;

		SelectPatientStateFragment selectPatientStateFragment = new SelectPatientStateFragment();
		selectPatientStateFragment.setPatientStateTitleHight(activity.getSelectPatientStateTitleHight());

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectPatientStateFragment, selectPatientStateFragment.getClass().getName()
						   );
		transaction.commit();

		return;

	}

	//14. 跳转到选择日期页面
	public void go2SelectDatePage()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)m_context;
		activity.startActivity(new Intent(activity, SelectDateActivity.class));
		return;
	}

}
