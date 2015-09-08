/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.appointment_nursing.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/27		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.ui;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.department_list.data.DDepartment;
import com.taixinkanghu_client.data_module.department_list.data.DDepartmentList;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospital;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospitalList;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.data.DAppiontmentNursing;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.data.DNursingDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ApoitNursingActivity extends BaseActivity
{
	//widget
	private                              HeaderCommon m_headerCommon         = null;    //title：预约陪护
	@Bind (R.id.name_region_ll)          LinearLayout m_nameRegionLL         = null;    //姓名点击区域
	@Bind (R.id.name_tv)                 EditText     m_nameTV               = null;    //姓名
	@Bind (R.id.phone_num_region_ll)     LinearLayout m_phoneNumRegionLL     = null;    //手机号码区域
	@Bind (R.id.phone_num_tv)            EditText     m_phoneNumTV           = null;    //手机号码
	@Bind (R.id.gender_region_ll)        LinearLayout m_genderRegionLL       = null;    //性别点击区域
	@Bind (R.id.gender_tv)               TextView     m_genderTV             = null;     //性别
	@Bind (R.id.age_region_ll)           LinearLayout m_ageRegionLL          = null;    //年龄点击区域
	@Bind (R.id.age_tv)                  TextView     m_ageTV                = null;    //年龄
	@Bind (R.id.weight_region_ll)        LinearLayout m_weightRegionLL       = null;    //体重点击区域
	@Bind (R.id.weight_tv)               TextView     m_weightTV             = null;    //体重
	@Bind (R.id.hospital_region_ll)      LinearLayout m_hospitaltRegionLL    = null;    //医院点击区域
	@Bind (R.id.hospital_tv)             TextView     m_hospitalTV           = null;    //医院
	@Bind (R.id.department_region_ll)    LinearLayout m_departmentRegionLL   = null;    //科室点击区域
	@Bind (R.id.department_tv)           TextView     m_departmentTV         = null;    //科室
	@Bind (R.id.patient_state_region_ll) LinearLayout m_patientStateRegionLL = null;    //患者状态点击区域
	@Bind (R.id.patient_state_tv)        TextView     m_patientStateTV       = null;    //患者状态
	@Bind (R.id.service_date_region_ll)  LinearLayout m_serviceDateRegionLL  = null;    //服务时间点击区域
	@Bind (R.id.service_date_tv)         TextView     m_serviceDateTV        = null;    //服务时间
	@Bind (R.id.protocol_tv)             TextView     m_protocolTV           = null;    //用户协议
	private                              BottomCommon m_bottomCommon         = null;    //底部按钮：返回首页

	@Bind (R.id.func_region_sv) Button m_confirmBtn = null;    //确定预约

	//测量高度用的LL
	@Bind (R.id.measuring_height_gender) LinearLayout m_measuringGenderHeightLL = null;    //测量性别下拉框所需高度的LL
	private                              Integer      m_selectGenderTitleHight  = 0;    //性别下拉框所需高度

	@Bind (R.id.measuring_height_age) LinearLayout m_measuringAgeHeightLL = null;    //测量年龄下拉框所需高度的LL
	private                           Integer      m_selectAgeTitleHight  = 0;    //年龄下拉框所需高度

	@Bind (R.id.measuring_height_weight) LinearLayout m_measuringWeightHeightLL = null;    //测量体重下拉框所需高度的LL
	private                              Integer      m_selectWeightTitleHight  = 0;    //体重下拉框所需高度

	@Bind (R.id.measuring_height_patient_state) LinearLayout m_measuringPatientStateHeightLL = null;    //测量患者状态下拉框所需高度的LL
	private                                     Integer      m_selectPatientStateTitleHight  = 0;    //患者状态下拉框所需高度

	//logical
	private FragmentManager m_fragmentManager = getFragmentManager();


	private HandleEditActionEvent             m_handleEditActionEvent               = null;
	private HandlerClickEventAppinmentNursing m_handlerClickEventAppointmentNursing = null;

	private EventBus m_eventBus = EventBus.getDefault();

	private ArrayList<ArrayList<Date>>    m_dateListAll     = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> m_typeListAll     = new ArrayList<>();
	private String                        m_dateDescription = null;

	private DAppiontmentNursing m_dAppiontmentNursing = DAppiontmentNursing.GetInstance().getApoitNursingPage();

	private int m_nurseID = DataConfig.DEFAULT_VALUE;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment_nursing);
		ButterKnife.bind(this);

		init();
		initListener();
		initContent();
		getHight();
	}

	@Override
	protected void onStart()
	{
		updateDate();
		super.onStart();
	}


	private void init()
	{
		//01. widget
		m_headerCommon = (HeaderCommon)m_fragmentManager.findFragmentById(R.id.common_header_fragment);
		//		if (m_headerCommon.getHeaderTV() == null)
		//		{
		//			TipsDialog.GetInstance().setMsg("m_headerCommon.getHeaderTV() == null", this);
		//			TipsDialog.GetInstance().show();
		//			return;
		//		}
		//		m_headerCommon.getHeaderTV().setTextSize(24.0f);
		//		m_confirmBtn = (Button)findViewById(R.id.btn_bottom);

		//测量高度用的LL
		m_handleEditActionEvent = new HandleEditActionEvent(this);
		m_handlerClickEventAppointmentNursing = new HandlerClickEventAppinmentNursing(this);
	}


	private void updateDate()
	{
		//姓名
		String name = m_dAppiontmentNursing.getName();
		if (TextUtils.isEmpty(name) == false)
		{
			m_nameTV.setText(name);
		}

		//手机号
		String phone = m_dAppiontmentNursing.getPhone();
		if (TextUtils.isEmpty(phone) == false)
		{
			m_phoneNumTV.setText(phone);
		}

		//性别
		EnumConfig.GenderStatus genderStatus = m_dAppiontmentNursing.getGenderStatus();
		if (genderStatus != null)
		{
			m_genderTV.setText(genderStatus.getName());
		}

		//年龄
		EnumConfig.AgeRage ageRage = m_dAppiontmentNursing.getAgeRage();
		if (ageRage != null)
		{
			m_ageTV.setText(ageRage.getName());
		}

		//体重
		EnumConfig.WeightRage weightRage = m_dAppiontmentNursing.getWeightRage();
		if (weightRage != null)
		{
			m_weightTV.setText(weightRage.getName());
		}

		//所在医院
		int hospitalID = m_dAppiontmentNursing.getHospitalID();
		//01. 显示全部
		if (hospitalID == 0)
		{
			m_hospitalTV.setText(getResources().getString(R.string.content_all));
		}
		//02. 显示hospitalname
		else
		{
			ArrayList<DHospital> hospitals = DHospitalList.GetInstance().getHospitals();
			for (DHospital hospital : hospitals)
			{
				if (hospital.getID() == hospitalID)
				{
					m_hospitalTV.setText(hospital.getName());
					break;
				}
			}
		}

		//所在科室
		int                    departmentID   = m_dAppiontmentNursing.getDepartmenetID();
		ArrayList<DDepartment> departmentList = DDepartmentList.GetInstance().getDepartments();
		for (DDepartment department : departmentList)
		{
			if (department.getID() == departmentID)
			{
				m_departmentTV.setText(department.getName());
				break;
			}
		}

		//病人状态
		EnumConfig.PatientState patientState = m_dAppiontmentNursing.getPatientState();
		if (patientState != null)
		{
			m_patientStateTV.setText(patientState.getName());
		}

		//护理时间
		DNursingDate nursingDate = m_apoitNursingPage.getNursingDate();
		if (nursingDate != null)
		{
			String dateDescription = nursingDate.getDateDescription();
			m_serviceDateTV.setText(dateDescription);
		}

	}



	private void initListener()
	{
		//01. 点击区域
		m_nameRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_phoneNumRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_genderRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_ageRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_weightRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_hospitaltRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_departmentRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_patientStateRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_serviceDateRegionLL.setOnClickListener(m_handlerClickEventAppointmentNursing);
		//02. 控件点击
		m_protocolTV.setOnClickListener(m_handlerClickEventAppointmentNursing);
		m_confirmBtn.setOnClickListener(m_handlerClickEventAppointmentNursing);
		//03. edittext
		m_nameTV.setOnEditorActionListener(m_handleEditActionEvent);
		m_phoneNumTV.setOnEditorActionListener(m_handleEditActionEvent);

	}

	private void initContent()
	{
		m_headerCommon.setTitle(R.string.appointment_nursing_title);
		m_confirmBtn.setText(R.string.confirm_btn_text);
		m_protocolTV.append(Html.fromHtml("<a href=>" + "《用户协议》" + "</a> "));
	}

	private void getHight()
	{
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

		m_measuringGenderHeightLL.measure(w, h);
		m_selectGenderTitleHight = m_measuringGenderHeightLL.getMeasuredHeight();

		m_measuringAgeHeightLL.measure(w, h);
		m_selectAgeTitleHight = m_measuringAgeHeightLL.getMeasuredHeight();

		m_measuringWeightHeightLL.measure(w, h);
		m_selectWeightTitleHight = m_measuringWeightHeightLL.getMeasuredHeight();

		m_measuringPatientStateHeightLL.measure(w, h);
		m_selectPatientStateTitleHight = m_measuringPatientStateHeightLL.getMeasuredHeight();

	}

	public int getNurseID()
	{
		return m_nurseID;
	}

	public TextView getGenderTV()
	{
		return m_genderTV;
	}


	public TextView getAgeTV()
	{
		return m_ageTV;
	}


	public TextView getWeightTV()
	{
		return m_weightTV;
	}


	public TextView getHospitalTV()
	{
		return m_hospitalTV;
	}


	public Integer getSelectPatientStateTitleHight()
	{
		return m_selectPatientStateTitleHight;
	}


	public Integer getSelectWeightTitleHight()
	{
		return m_selectWeightTitleHight;
	}


	public Integer getSelectAgeTitleHight()
	{
		return m_selectAgeTitleHight;
	}


	public Integer getSelectGenderTitleHight()
	{
		return m_selectGenderTitleHight;
	}


	public String getHospitalName()
	{
		return m_hospitalTV.getText().toString();
	}

	public String getDepartmentName()
	{
		return m_departmentTV.getText().toString();
	}

	public String getPatientState()
	{
		return m_patientStateTV.getText().toString();
	}

	public TextView getPatientStateTV()
	{
		return m_patientStateTV;
	}

	public String getDateDescription()
	{
		return m_serviceDateTV.getText().toString();
	}

	public void setDateDescription(String dateDescription)
	{
		m_dateDescription = dateDescription;
		if (m_serviceDateTV == null)
			return;
		m_serviceDateTV.setText(m_dateDescription);
	}

	public ArrayList<ArrayList<Integer>> getTypeListAll()
	{
		return m_typeListAll;
	}

	public void setTypeListAll(ArrayList<ArrayList<Integer>> typeListAll)
	{
		m_typeListAll = typeListAll;
	}

	public ArrayList<ArrayList<Date>> getDateListAll()
	{
		return m_dateListAll;
	}

	public void setDateListAll(ArrayList<ArrayList<Date>> dateListAll)
	{
		m_dateListAll = dateListAll;
	}


	//焦点设置
	public void setNameFocus()
	{
		m_nameTV.requestFocus();
		Timer timer = new Timer(); //设置定时器
		timer.schedule(new TimerTask()
					   {
						   @Override
						   public void run()
						   { //弹出软键盘的代码
							   InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
							   imm.showSoftInput(m_nameTV, InputMethodManager.RESULT_SHOWN);
							   imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
						   }
					   }, 300
					  );
	}

	public void setPhoneNumFocus()
	{
		m_phoneNumTV.requestFocus();
		Timer timer = new Timer(); //设置定时器
		timer.schedule(new TimerTask()
					   {
						   @Override
						   public void run()
						   { //弹出软键盘的代码
							   InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
							   imm.showSoftInput(m_phoneNumTV, InputMethodManager.RESULT_SHOWN);
							   imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
						   }
					   }, 300
					  );
	}

	//数据设置
	public void setGenderStatus(EnumConfig.GenderStatus genderStatus)
	{
		m_genderTV.setText(genderStatus.getName());

		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		m_apoitNursingPage.setGenderStatus(genderStatus);
	}

	public void setAgeRage(EnumConfig.AgeRage ageRage)
	{
		m_ageTV.setText(ageRage.getName());
		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		m_apoitNursingPage.setAgeRage(ageRage);
	}

	public void setWeightRage(EnumConfig.WeightRage weightRage)
	{
		m_weightTV.setText(weightRage.getName());
		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		m_apoitNursingPage.setWeightRage(weightRage);
	}

	public void setDepartmentID(int departmentID)
	{
		//01. set department ui
		ArrayList<DDepartment> departmentList = DDepartmentList.GetInstance().getDepartments();
		for (DDepartment department : departmentList)
		{
			if (department.getID() == departmentID)
			{
				m_departmentTV.setText(department.getName());
			}
		}

		//02. 保存到数据类中。
		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		m_apoitNursingPage.setDepartmenetID(departmentID);
	}

	public void setHospitalID(int hospitalID)
	{
		//01. 显示全部
		if (hospitalID == 0)
		{
			m_hospitalTV.setText(getResources().getString(R.string.content_all));
		}
		//02. 显示hospitalname
		else
		{
			ArrayList<DHospital> hospitals = DHospitalList.GetInstance().getHospitals();
			for (DHospital hospital : hospitals)
			{
				if (hospital.getID() == hospitalID)
				{
					m_hospitalTV.setText(hospital.getName());
				}
			}
		}
		//03. 保存到数据类中。
		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		m_apoitNursingPage.setHospitalID(hospitalID);
	}

	public void setPatientState(EnumConfig.PatientState patientState)
	{
		m_patientStateTV.setText(patientState.getName());
		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		m_apoitNursingPage.setPatientState(patientState);
	}

	public void confirmAction()
	{
		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		//姓名
		String name = m_nameTV.getText().toString();
		if (!TextUtils.isEmpty(name))
		{
			m_apoitNursingPage.setName(name);
		}

		//手机号码
		String phone = m_phoneNumTV.getText().toString();
		if (!TextUtils.isEmpty(phone))
		{
			m_apoitNursingPage.setPhone(phone);
		}

	}

	/**
	 * EventBus  handler
	 */
	public void onEventMainThread(ConfirmSelectDateEvent event)
	{
		if (event == null)
			return;

		if (event.getNursingDate() == null)
			return;

		if (m_apoitNursingPage == null)
		{
			RegisterDialog.GetInstance().setMsg("m_apoitNursingPage == null", this);
			RegisterDialog.GetInstance().show();
			return;
		}

		setDateDescription(event.getNursingDate().getDateDescription());
		m_apoitNursingPage.setNursingDate(event.getNursingDate());
	}

	//网上复制下来的代码，作用：点击EditText文本框之外任何地方隐藏键盘
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev))
			{

				InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
				if (imm != null)
				{
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev))
		{
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event)
	{
		if (v != null && (v instanceof EditText))
		{
			int[] leftTop = {0, 0};
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
			{
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
}
