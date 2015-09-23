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

package com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.msg_handler;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.text.TextUtils;

import com.module.exception.RuntimeExceptions.logical.LogicalRTException;
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
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurserOrderList;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.flow_data.DNursingDate;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.order_confirm_page.ui.SelectPatientStateFragment;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.BaseRepeatFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui.RepeatOrderApoitNursingActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui.SelectAgeFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui.SelectDepartmentFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui.SelectHospitalFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui.SelectSexFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui.SelectWeightFragment;
import com.taixinkanghu_client.work_flow.repeat_order_flow.confirm_order_page.ui.OrderConfirmActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.select_date_page.ui.SelectDateActivity;

import java.util.ArrayList;

public class RepeatOrderApoitNursingMsgHandler extends BaseRepeatFlowUIMsgHandler
{
	public RepeatOrderApoitNursingMsgHandler(RepeatOrderApoitNursingActivity activity)
	{
		super(activity);
	}

	//01. 跳转到主页面
	public void go2MainPage()
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;
		//01. 关闭当前页面
		//因为主页面是独占的，所以不需要显示关闭当前页面

		//02. 清理流程数据
		m_dRepeatOrderFlow.clearupAll();

		//03. 跳转到主页面
		activity.startActivity(new Intent(activity, MainActivity.class));

		return;
	}

	//02. init flow data
	public void loadDataforRepeatOrder(int selectedNurseID)
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		//01. check
		DNurseOrder nurseOrder = DNurserOrderList.GetInstance().getNurseOrderByNurseID(selectedNurseID);
		if (nurseOrder == null)
		{
			activity.popErrorDialog("Input selectedNurseID is invalid![selectedNurseID:=" + selectedNurseID + "]");
			return;
		}

		//02. nurseOrderID
		m_dRepeatOrderFlow.setSelectedNurseID(selectedNurseID);

		//03. DNursingDate， 设置为null
//		Date beginDate = nurseOrder.getBeginDate();
//		if (beginDate == null)
//		{
//			activity.popErrorDialog("beginDate == null");
//			return;
//		}
//		Date endDate = nurseOrder.getEndDate();
//		if (endDate == null)
//		{
//			activity.popErrorDialog("endDate == null");
//			return;
//		}

//		DNursingDate nursingDate = new DNursingDate(beginDate, endDate);
		m_dRepeatOrderFlow.setNursingDate(null);

		return;
	}

	//03. 使用预约陪护流程的数据来填充当前UI界面。
	public void updateContent()
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		//姓名
		String name = m_dRepeatOrderFlow.getPatientName();
		if (TextUtils.isEmpty(name) == false)
		{
			activity.getNameET().setText(name);
		}

		//手机号
		String phone = m_dRepeatOrderFlow.getPhone();
		if (TextUtils.isEmpty(phone) == false)
		{
			activity.getPhoneNumET().setText(phone);
		}

		//性别
		EnumConfig.GenderStatus genderStatus = m_dRepeatOrderFlow.getGenderStatus();
		if (genderStatus != null)
		{
			activity.getGenderTV().setText(genderStatus.getName());
		}

		//年龄
		EnumConfig.AgeRage ageRage = m_dRepeatOrderFlow.getAgeRage();
		if (ageRage != null)
		{
			activity.getAgeTV().setText(ageRage.getName());
		}

		//体重
		EnumConfig.WeightRage weightRage = m_dRepeatOrderFlow.getWeightRage();
		if (weightRage != null)
		{
			activity.getWeightTV().setText(weightRage.getName());
		}

		//所在医院
		int hospitalID = m_dRepeatOrderFlow.getHospitalID();
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
		int                    departmentID   = m_dRepeatOrderFlow.getDepartmenetID();
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
		EnumConfig.PatientState patientState = m_dRepeatOrderFlow.getPatientState();
		if (patientState != null)
		{
			activity.getPatientStateTV().setText(patientState.getName());
		}

		//护理时间
		DNursingDate nursingDate = m_dRepeatOrderFlow.getNursingDate();
		if (nursingDate != null)
		{
			String dateDescription = nursingDate.getDateDescription();
			activity.getServiceDateTV().setText(dateDescription);
		}

	}

	//02. 数据设置(外部调用)
	public void setGenderStatus(EnumConfig.GenderStatus genderStatus)
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		activity.getGenderTV().setText(genderStatus.getName());
		m_dRepeatOrderFlow.setGenderStatus(genderStatus);
	}

	public void setAgeRage(EnumConfig.AgeRage ageRage)
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		activity.getAgeTV().setText(ageRage.getName());
		m_dRepeatOrderFlow.setAgeRage(ageRage);
	}

	public void setWeightRage(EnumConfig.WeightRage weightRage)
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		activity.getWeightTV().setText(weightRage.getName());
		m_dRepeatOrderFlow.setWeightRage(weightRage);
	}

	public void setDepartmentID(int departmentID)
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		//01. set department ui
		ArrayList<DDepartment> departmentList = DDepartmentList.GetInstance().getDepartments();
		for (DDepartment department : departmentList)
		{
			if (department.getID() == departmentID)
			{
				activity.getDepartmentTV().setText(department.getName());
			}
		}

		m_dRepeatOrderFlow.setDepartmenetID(departmentID);
	}

	public void setHospitalID(int hospitalID)
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

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
		m_dRepeatOrderFlow.setHospitalID(hospitalID);

	}

	public void setPatientState(EnumConfig.PatientState patientState)
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		activity.getPatientStateTV().setText(patientState.getName());
		m_dRepeatOrderFlow.setPatientState(patientState);
		return;

	}

	//03. 存储date到DAppiontmentNursing中
	//这里仅需要姓名和手机号码，因为其他数据在设置的时候，就已经进行了同步。
	public void saveContent()
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		String name = activity.getNameET().getText().toString().trim();
		if (!TextUtils.isEmpty(name))
			m_dRepeatOrderFlow.setPatientName(name);

		String mobileNum = activity.getPhoneNumET().getText().toString().trim();
		if (!TextUtils.isEmpty(mobileNum))
			m_dRepeatOrderFlow.setPhone(mobileNum);

		return;

	}

	//04. 请求发预约陪护信息
	public void requestAppiontmentNursingAction()
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;
		try
		{
			RequestNurseBasicListEvent event = m_dRepeatOrderFlow.constructRequestNurseBasicListEvent();
			NurseListHandler.GetInstance().requestNurseBasicListAction(event);
			return;
		}
		catch (LogicalRTException e)
		{
			TipsDialog.GetInstance().setMsg(activity.getString(R.string.ap_err_info_fill_required_options), activity);
			TipsDialog.GetInstance().show();
		}
		return;

	}

	//05. 跳转到护理员列表玉面
	public void go2ConfirmOrderPage()
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;
		activity.startActivity(new Intent(activity, OrderConfirmActivity.class));
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
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;
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
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;
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
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

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
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

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
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

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
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		SelectHospitalFragment selectHospitalFragment = new SelectHospitalFragment();

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectHospitalFragment, selectHospitalFragment.getClass().getName());
		transaction.commit();

	}

	//12. 跳转到选择科室列表fragment
	public void go2SelectDepartmentFragment()
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

		SelectDepartmentFragment selectDepartmentFragment = new SelectDepartmentFragment();

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.appointment_nursing_page, selectDepartmentFragment, selectDepartmentFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//13. 跳转到选择护理员状态页面
	public void go2PatientStateFragment()
	{
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;

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
		//01. 清空UI
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)m_context;
		activity.getServiceDateTV().setText("");

		//02. 跳转到选择日期页面
		activity.startActivity(new Intent(activity, SelectDateActivity.class));
		return;
	}



}
