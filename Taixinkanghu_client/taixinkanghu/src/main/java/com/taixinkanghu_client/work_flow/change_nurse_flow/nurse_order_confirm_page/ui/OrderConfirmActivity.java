package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.circleimageview.CircleImageView;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.msg_handler.OrderConfirmMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderConfirmActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	//选择更换时间
	@Bind (R.id.select_begin_date_region_ll) LinearLayout m_selectBeginDateRegionLL = null;
	@Bind (R.id.select_begin_date_tv)        TextView     m_selectBeginDateTV       = null;

	//tab标签相关
	@Bind (R.id.func_tab_region_ll) LinearLayout m_funcTabRegionLL = null;
	@Bind (R.id.change_nurse_rgrp)  RadioGroup   m_changeNurseRGrp = null;
	@Bind (R.id.new_nurse_rbtn)     RadioButton  m_newNurseRBtn    = null;
	@Bind (R.id.old_nurse_rbtn)     RadioButton  m_oldNurseRBtn    = null;

	//订单内容
	@Bind (R.id.header_img_civ)          CircleImageView m_nurseHeadImgIV       = null;    //头像
	@Bind (R.id.name_tv)                 TextView        m_nameTV               = null;        //姓名
	@Bind (R.id.nuring_level_tv)         TextView        m_nuringLevelTV        = null;    //护理级别
	@Bind (R.id.job_num_tv)              TextView        m_jobNumTV             = null;        //工号
	@Bind (R.id.service_date_tv)         TextView        m_serviceDateTV        = null;    //服务时间
	@Bind (R.id.service_address_tv)      TextView        m_serviceAddressTV     = null;    //服务地点
	@Bind (R.id.patient_region_ll)       LinearLayout    m_patientStateRegionLL = null;    //被护理人点击区域
	@Bind (R.id.patient_name_tv)         TextView        m_patientNameTV        = null;    //被护理人姓名
	@Bind (R.id.patient_state_region_ll) LinearLayout    m_patientRegionLL      = null;    //被护理人状态点击区域
	@Bind (R.id.patient_state_tv)        TextView        m_patientStateTV       = null;    //被护理人状态
	//订单金额
	@Bind (R.id.all_region_ll)           LinearLayout    m_allRegionLL          = null;    //全天24小时点击区域
	@Bind (R.id.all_num_tv)              TextView        m_allNumTV             = null;    //24小时服务天数
	@Bind (R.id.charge_per_all_tv)       TextView        m_chargePerAllTV       = null;    //24小时服务单价
	@Bind (R.id.all_coeff_tv)            TextView        m_allCoeffTV           = null;    //24小时服务天数作为系数
	@Bind (R.id.day_region_ll)           LinearLayout    m_dayRegionLL          = null;    //白天12小时点击区域
	@Bind (R.id.day_num_tv)              TextView        m_dayNumTV             = null;    //白天12小时服务天数
	@Bind (R.id.charge_per_day_tv)       TextView        m_chargePerDayTV       = null;    //白天12小时服务单价
	@Bind (R.id.day_coeff_tv)            TextView        m_dayCoeffTV           = null;    //白天12小时服务天数作为系数
	@Bind (R.id.night_region_ll)         LinearLayout    m_nightRegionLL        = null;    //黑天12小时点击区域
	@Bind (R.id.night_num_tv)            TextView        m_nightNumTV           = null;    //黑天12小时服务天数
	@Bind (R.id.charge_per_night_tv)     TextView        m_chargePerNightTV     = null;    //黑天12小时服务单价
	@Bind (R.id.night_coeff_tv)          TextView        m_NightCoeffTV         = null;    //黑天12小时服务天数作为系数
	@Bind (R.id.total_charge_tv)         TextView        m_TotalChargeTV        = null;    //总价格

	//测量高度用的LL
	@Bind (R.id.measuring_height_patient_state) LinearLayout m_measuringPatientStateHeightLL = null;    //测量患者状态下拉框所需高度的LL
	//Bottom
	private                                     BottomCommon m_bottomCommon                  = null;

	//logical
	private OrderConfirmMsgHandler m_orderConfirmMsgHandler       = new OrderConfirmMsgHandler(this);
	private Integer                m_selectPatientStateTitleHight = 0;    //患者状态下拉框所需高度
	private ClickHeaderBack        m_clickHeaderBack              = new ClickHeaderBack();
	private ClickBottomBtn         m_clickBottomBtn               = new ClickBottomBtn();
	private PopDialog_ErrorTips    m_popDialog_errorTips          = new PopDialog_ErrorTips();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order_in_change_nurse);
		ButterKnife.bind(this);

		init();
		initHightValues();
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			m_orderConfirmMsgHandler.backAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	/**
	 * widget event
	 */
	@OnClick (R.id.select_begin_date_region_ll)
	public void clickSelectDate()
	{
		m_orderConfirmMsgHandler.selectBeginDateAction();
	}


	@OnClick (R.id.new_nurse_rbtn)
	public void clickNewNurseRBtn()
	{
		m_orderConfirmMsgHandler.switchNewNurseDisplay();
	}

	@OnClick (R.id.old_nurse_rbtn)
	public void clickOldNurseRBtn()
	{
		m_orderConfirmMsgHandler.switchOldNurseDisplay();
	}


	/**
	 * override func
	 */
	class ClickHeaderBack implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_orderConfirmMsgHandler.backAction();
		}
	}

	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (check())
			{
				m_orderConfirmMsgHandler.requestNurseOrderConfirmAction();
			}
		}
	}

	class PopDialog_ErrorTips  implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_orderConfirmMsgHandler.go2MainPage();
			return;
		}
	}
	/**
	 * inner func
	 */
	private void init()
	{
		//widget
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.determine_order_title);
		m_headerCommon.getBackIBtn().setOnClickListener(m_clickHeaderBack);

		m_newNurseRBtn.setChecked(true);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.determine_appointment_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);
	}

	private void initHightValues()
	{
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

		m_measuringPatientStateHeightLL.measure(w, h);
		m_selectPatientStateTitleHight = m_measuringPatientStateHeightLL.getMeasuredHeight();
	}

	private void updateContent()
	{
		m_orderConfirmMsgHandler.updateContent();
	}

	private boolean check()
	{
		if (TextUtils.isEmpty(m_selectBeginDateTV.getText()))
		{
			TipsDialog.GetInstance().setMsg(getString(R.string.tips_select_nursing_date), this);
			TipsDialog.GetInstance().show();
			return false;
		}
		return true;
	}


	/**
	 * widget:get
	 */
	public TextView getSelectBeginDateTV()
	{
		return m_selectBeginDateTV;
	}

	public RadioGroup getChangeNurseRGrp()
	{
		return m_changeNurseRGrp;
	}

	public RadioButton getNewNurseRBtn()
	{
		return m_newNurseRBtn;
	}

	public RadioButton getOldNurseRBtn()
	{
		return m_oldNurseRBtn;
	}

	public CircleImageView getNurseHeadImgIV()
	{
		return m_nurseHeadImgIV;
	}

	public TextView getNameTV()
	{
		return m_nameTV;
	}

	public TextView getNuringLevelTV()
	{
		return m_nuringLevelTV;
	}

	public TextView getJobNumTV()
	{
		return m_jobNumTV;
	}

	public TextView getServiceDateTV()
	{
		return m_serviceDateTV;
	}

	public TextView getServiceAddressTV()
	{
		return m_serviceAddressTV;
	}

	public TextView getPatientNameTV()
	{
		return m_patientNameTV;
	}

	public TextView getPatientStateTV()
	{
		return m_patientStateTV;
	}

	public LinearLayout getAllRegionLL()
	{
		return m_allRegionLL;
	}

	public TextView getAllNumTV()
	{
		return m_allNumTV;
	}

	public TextView getChargePerAllTV()
	{
		return m_chargePerAllTV;
	}

	public TextView getAllCoeffTV()
	{
		return m_allCoeffTV;
	}

	public LinearLayout getDayRegionLL()
	{
		return m_dayRegionLL;
	}

	public TextView getDayNumTV()
	{
		return m_dayNumTV;
	}

	public TextView getChargePerDayTV()
	{
		return m_chargePerDayTV;
	}

	public TextView getDayCoeffTV()
	{
		return m_dayCoeffTV;
	}

	public LinearLayout getNightRegionLL()
	{
		return m_nightRegionLL;
	}

	public TextView getNightNumTV()
	{
		return m_nightNumTV;
	}

	public TextView getChargePerNightTV()
	{
		return m_chargePerNightTV;
	}

	public TextView getNightCoeffTV()
	{
		return m_NightCoeffTV;
	}

	public TextView getTotalChargeTV()
	{
		return m_TotalChargeTV;
	}


	/**
	 * data:get
	 */
	public Integer getSelectPatientStateTitleHight()
	{
		return m_selectPatientStateTitleHight;
	}

	public OrderConfirmMsgHandler getOrderConfirmMsgHandler()
	{
		return m_orderConfirmMsgHandler;
	}

	/**
	 * logical
	 */
	public boolean isNewNurseSelected()
	{
		int resID = m_changeNurseRGrp.getCheckedRadioButtonId();
		return (resID == R.id.new_nurse_rbtn);
	}

	public boolean isOldNurseSelected()
	{
		return (!isNewNurseSelected());
	}

	public void popErrorDialog(String error)
	{
		TipsDialog.GetInstance().setMsg(error, this, m_popDialog_errorTips);
		TipsDialog.GetInstance().show();
		return;
	}
}
