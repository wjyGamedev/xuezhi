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

package com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.util.timer.SoftInputTimerTask;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.net.config.NurseBasicListConfig;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.user_protocal_page.UserProtocalActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.msg_handler.RepeatOrderApoitNursingMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class RepeatOrderApoitNursingActivity extends BaseActivity
{
	//widget
	private                              HeaderCommon m_headerCommon         = null;    //title：预约陪护
	@Bind (R.id.name_region_ll)          LinearLayout m_nameRegionLL         = null;    //姓名点击区域
	@Bind (R.id.name_et)                 EditText     m_nameET               = null;    //姓名
	@Bind (R.id.phone_num_region_ll)     LinearLayout m_phoneNumRegionLL     = null;    //手机号码区域
	@Bind (R.id.phone_num_et)            EditText     m_phoneNumET           = null;    //手机号码
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

	//测量高度用的LL
	@Bind (R.id.measuring_height_gender) LinearLayout m_measuringGenderHeightLL = null;    //测量性别下拉框所需高度的LL
	private                              int          m_selectGenderTitleHight  = 0;    //性别下拉框所需高度

	@Bind (R.id.measuring_height_age) LinearLayout m_measuringAgeHeightLL = null;    //测量年龄下拉框所需高度的LL
	private                           int          m_selectAgeTitleHight  = 0;    //年龄下拉框所需高度

	@Bind (R.id.measuring_height_weight) LinearLayout m_measuringWeightHeightLL = null;    //测量体重下拉框所需高度的LL
	private                              int          m_selectWeightTitleHight  = 0;    //体重下拉框所需高度

	@Bind (R.id.measuring_height_patient_state) LinearLayout m_measuringPatientStateHeightLL = null;    //测量患者状态下拉框所需高度的LL
	private                                     int          m_selectPatientStateTitleHight  = 0;    //患者状态下拉框所需高度

	//logical
	private RepeatOrderApoitNursingMsgHandler m_repeatOrderApoitNursingMsgHandler = new RepeatOrderApoitNursingMsgHandler(this);
	private FragmentManager                   m_fragmentManager                   = getFragmentManager();
	private BottomCommonClickEvent            m_bottomCommonClickEvent            = new BottomCommonClickEvent();
	private PopDialog_ErrorTips               m_popDialog_errorTips               = new PopDialog_ErrorTips();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment_nursing);
		ButterKnife.bind(this);

		init();
		initHightValues();
	}

	@Override
	protected void onStart()
	{
		m_repeatOrderApoitNursingMsgHandler.updateContent();
		super.onStart();
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

	/**
	 * widget event
	 */
	@OnClick (R.id.name_region_ll)
	public void clickNameRegion()
	{
		m_nameET.requestFocus();

		SoftInputTimerTask softInputTimerTask = new SoftInputTimerTask(this, m_nameET);
		softInputTimerTask.schedule(DataConfig.DELAY_TIME_POP_SOFT_INPUT_IN_MILLISECENDS);
	}

	@OnEditorAction (R.id.name_et)
	public boolean nameEditorAction(TextView v, int actionId, KeyEvent event)
	{
		if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE)
		{
			InputMethodManager imm = (InputMethodManager)RepeatOrderApoitNursingActivity.this.getSystemService(Context
																													   .INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
		return true;
	}

	@OnClick (R.id.phone_num_region_ll)
	public void clickMobileRegion()
	{
		m_phoneNumET.requestFocus();

		SoftInputTimerTask softInputTimerTask = new SoftInputTimerTask(this, m_phoneNumET);
		softInputTimerTask.schedule(DataConfig.DELAY_TIME_POP_SOFT_INPUT_IN_MILLISECENDS);
	}

	@OnEditorAction (R.id.phone_num_et)
	public boolean mobileNumEditorAction(TextView v, int actionId, KeyEvent event)
	{
		if (actionId == EditorInfo.IME_ACTION_NEXT	||
				actionId == EditorInfo.IME_ACTION_DONE)
		{
			InputMethodManager imm = (InputMethodManager)RepeatOrderApoitNursingActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
		return true;
	}

	@OnClick(R.id.gender_region_ll)
	public void clickGenderRegion()
	{
		m_repeatOrderApoitNursingMsgHandler.go2SelectGenderFragment();
		return;
	}

	@OnClick(R.id.age_region_ll)
	public void clickAgeRegion()
	{
		m_repeatOrderApoitNursingMsgHandler.go2SelectAgeFragmnet();
		return;
	}

	@OnClick(R.id.weight_region_ll)
	public void clickWeightRegion()
	{
		m_repeatOrderApoitNursingMsgHandler.go2SelectWeightFragmnet();
		return;
	}

	@OnClick(R.id.hospital_region_ll)
	public void clickHospitalRegion()
	{
		m_repeatOrderApoitNursingMsgHandler.go2SelectHospitalFragment();
		return;
	}

	@OnClick(R.id.department_region_ll)
	public void clickDepartmentRegion()
	{
		m_repeatOrderApoitNursingMsgHandler.go2SelectDepartmentFragment();
		return;
	}

	@OnClick(R.id.patient_state_region_ll)
	public void clickPatientStateRegion()
	{
		m_repeatOrderApoitNursingMsgHandler.go2PatientStateFragment();
		return;
	}

	@OnClick(R.id.service_date_region_ll)
	public void clickServiceDateRegion()
	{
		m_repeatOrderApoitNursingMsgHandler.go2SelectDatePage();
	}

	@OnClick(R.id.protocol_tv)
	public void clickProtocalTV()
	{
		startActivity(new Intent(this, UserProtocalActivity.class));
	}

	/**
	 * override func
	 */
	class BottomCommonClickEvent implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			//01. 检测必填选项
			if (!checkRequiredOption())
			{
				TipsDialog.GetInstance().setMsg(RepeatOrderApoitNursingActivity.this.getString(R.string.ap_err_info_fill_required_options),
												RepeatOrderApoitNursingActivity.this
											   );
				TipsDialog.GetInstance().show();
				return;
			}

			//02. 保存填写信息
			m_repeatOrderApoitNursingMsgHandler.saveContent();

			//03. 发送请求预约陪护event
			m_repeatOrderApoitNursingMsgHandler.requestAppiontmentNursingAction();

			//04. 跳转到确认订单页面
			m_repeatOrderApoitNursingMsgHandler.go2ConfirmOrderPage();

		}
	}

	class PopDialog_ErrorTips  implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_repeatOrderApoitNursingMsgHandler.go2MainPage();
			return;
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//01. widget
		m_headerCommon = (HeaderCommon)m_fragmentManager.findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.appointment_nursing_title);
		m_protocolTV.append(Html.fromHtml("<a href=>" + "《用户协议》" + "</a> "));

		m_bottomCommon = (BottomCommon)m_fragmentManager.findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.confirm_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_bottomCommonClickEvent);

		//02. init
		Intent intent = getIntent();
		int selectedNurseID = intent.getIntExtra(NurseBasicListConfig.ID, DataConfig.DEFAULT_VALUE);
		if (selectedNurseID == DataConfig.DEFAULT_VALUE)
		{
			popErrorDialog("selectedNurseID == DataConfig.DEFAULT_VALUE");
			return;
		}

		m_repeatOrderApoitNursingMsgHandler.loadDataforRepeatOrder(selectedNurseID);

	}

	private void initHightValues()
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

	private boolean checkRequiredOption()
	{
		String hospitalName = m_hospitalTV.getText().toString();
		String departmentName = m_departmentTV.getText().toString();
		String patientState = m_patientStateTV.getText().toString();
		String dateDescription = m_serviceDateTV.getText().toString();

		if (TextUtils.isEmpty(hospitalName) || TextUtils.isEmpty(departmentName) || TextUtils.isEmpty(patientState) || TextUtils.isEmpty(
				dateDescription
																																		))
		{
			return false;
		}
		return true;
	}

	/**
	 * get widget
	 */
	public EditText getNameET()
	{
		return m_nameET;
	}

	public EditText getPhoneNumET()
	{
		return m_phoneNumET;
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

	public TextView getDepartmentTV()
	{
		return m_departmentTV;
	}

	public TextView getPatientStateTV()
	{
		return m_patientStateTV;
	}

	public TextView getServiceDateTV()
	{
		return m_serviceDateTV;
	}

	public RepeatOrderApoitNursingMsgHandler getRepeatOrderApoitNursingMsgHandler()
	{
		return m_repeatOrderApoitNursingMsgHandler;
	}

	public int getSelectGenderTitleHight()
	{
		return m_selectGenderTitleHight;
	}

	public int getSelectAgeTitleHight()
	{
		return m_selectAgeTitleHight;
	}

	public int getSelectWeightTitleHight()
	{
		return m_selectWeightTitleHight;
	}

	public int getSelectPatientStateTitleHight()
	{
		return m_selectPatientStateTitleHight;
	}

	/**
	 * logical
	 */
	public void popErrorDialog(String error)
	{
		TipsDialog.GetInstance().setMsg(error, this, m_popDialog_errorTips);
		TipsDialog.GetInstance().show();
		return;
	}





}
