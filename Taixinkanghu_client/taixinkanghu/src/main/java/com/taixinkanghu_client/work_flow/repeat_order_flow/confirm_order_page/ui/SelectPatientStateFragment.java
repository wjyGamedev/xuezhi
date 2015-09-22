package com.taixinkanghu_client.work_flow.repeat_order_flow.confirm_order_page.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.work_flow.repeat_order_flow.confirm_order_page.msg_handler.OrderConfirmMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPatientStateFragment extends Fragment
{
	//widget
	@Bind (R.id.patient_state_header_ll)           LinearLayout m_titleLL           = null;
	@Bind (R.id.patient_state_care_myself_tv)      TextView     m_careMyselfBtn     = null;
	@Bind (R.id.patient_state_half_care_myself_tv) TextView     m_halfCareMyselfBtn = null;
	@Bind (R.id.patient_state_not_care_myself_tv)  TextView     m_notCareMyselfBtn  = null;

	//logical
	private int                    m_patientStateTitleHight = 0;
	private OrderConfirmMsgHandler m_orderConfirmMsgHandler = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_select_patient_state, container, false);
		ButterKnife.bind(this, view);

		init();
		//设置底部LL控件高度
		updateHeight();

		return view;
	}

	/**
	 * widget event
	 */
	@OnClick (R.id.patient_state_header_ll)
	public void clickPatientStateHeaderRegion()
	{
		cancelAction();
		return;
	}

	@OnClick (R.id.patient_state_care_myself_tv)
	public void clickPatientStateItemCareMyself()
	{
		m_orderConfirmMsgHandler.setPatientState(EnumConfig.PatientState.PATIENT_STATE_CARE_MYSELF);
		cancelAction();
		return;
	}

	@OnClick (R.id.patient_state_half_care_myself_tv)
	public void clickPatientStateItemHalfCareMyself()
	{
		m_orderConfirmMsgHandler.setPatientState(EnumConfig.PatientState.PATIENT_STATE_HALF_CARE_MYSELF);
		cancelAction();
		return;
	}

	@OnClick (R.id.patient_state_not_care_myself_tv)
	public void clickPatientStateItemCanntCareMyself()
	{
		m_orderConfirmMsgHandler.setPatientState(EnumConfig.PatientState.PATIENT_STATE_CANNT_CARE_MYSELF);
		cancelAction();
		return;
	}

	@OnClick (R.id.patient_state_bottom_ll)
	public void clickPatientStateBottomRegion()
	{
		cancelAction();
		return;
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//01. m_orderConfirmMsgHandler
		OrderConfirmActivity activity = (OrderConfirmActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("ApoitNursingActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_orderConfirmMsgHandler = activity.getOrderConfirmMsgHandler();
		if (m_orderConfirmMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("m_orderConfirmMsgHandler == null");
			TipsDialog.GetInstance().show();
			return;
		}
	}

	private void updateHeight()
	{
		if (m_patientStateTitleHight != 0)
		{
			LinearLayout.LayoutParams Lp = (LinearLayout.LayoutParams)m_titleLL.getLayoutParams();
			Lp.height = m_patientStateTitleHight;
			m_titleLL.setLayoutParams(Lp);
		}
	}

	private void cancelAction()
	{
		//蒙版点击一下之后消失的处理
		FragmentManager      fgManager           = getFragmentManager();
		Fragment fragment            = fgManager.findFragmentByTag(SelectPatientStateFragment.class.getName());
		FragmentTransaction  fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
	}

	/**
	 * data:set
	 */
	public void setPatientStateTitleHight(Integer patientStateTitleHight)
	{
		m_patientStateTitleHight = patientStateTitleHight;
	}

}
