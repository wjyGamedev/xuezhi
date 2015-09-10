package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.patient_info_page.ui;

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
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.patient_info_page.msg_handler.PatientMsgHandler;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/7/28.
 */
public class SelectWeightFragment extends Fragment
{
	//widget
	@Bind (R.id.weight_header_ll)           LinearLayout m_weightHeaderRegionLL = null;
	@Bind (R.id.weight_region_0_35_tv)      TextView     m_0To35WeightBtn       = null;
	@Bind (R.id.weight_region_35_50_tv)     TextView     m_35To50WeightBtn      = null;
	@Bind (R.id.weight_region_50_80_tv)     TextView     m_50To80WeightBtn      = null;
	@Bind (R.id.weight_region_80_120_tv)    TextView     m_80To120WeightBtn     = null;
	@Bind (R.id.weight_region_above_120_tv) TextView     m_above120WeightBtn    = null;

	//logical
	private int               m_weightTitleHight  = DataConfig.DEFAULT_VALUE;
	private PatientMsgHandler m_patientMsgHandler = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_select_weight, container, false);

		init();
		//设置顶部LL控件高度
		updateHeight();

		return view;
	}

	/**
	 * widget event
	 */
	@OnClick (R.id.weight_header_ll)
	public void clickWeightHeaderRegion()
	{
		cancelAction();
		return;
	}

	@OnClick (R.id.weight_region_0_35_tv)
	public void clickAgeRange_0_35()
	{
		m_patientMsgHandler.setWeightRage(EnumConfig.WeightRage.WEIGHT_0_35);
		return;
	}

	@OnClick (R.id.weight_region_35_50_tv)
	public void clickAgeRange_35_50()
	{
		m_patientMsgHandler.setWeightRage(EnumConfig.WeightRage.WEIGHT_35_50);
		return;
	}

	@OnClick (R.id.weight_region_50_80_tv)
	public void clickAgeRange_50_80()
	{
		m_patientMsgHandler.setWeightRage(EnumConfig.WeightRage.WEIGHT_50_80);
		return;
	}

	@OnClick (R.id.weight_region_80_120_tv)
	public void clickAgeRange_80_120()
	{
		m_patientMsgHandler.setWeightRage(EnumConfig.WeightRage.WEIGHT_80_120);
		return;
	}

	@OnClick (R.id.weight_region_above_120_tv)
	public void clickAgeRange_above_75()
	{
		m_patientMsgHandler.setWeightRage(EnumConfig.WeightRage.WEIGHT_MORE_THAN_120);
		return;
	}

	@OnClick (R.id.weight_bottom_ll)
	public void clickAgeBottomRegion()
	{
		cancelAction();
		return;
	}

	/**
	 * inner func
	 */
	private void init()
	{
		PatientActivity activity = (PatientActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("ApoitNursingActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_patientMsgHandler = activity.getPatientMsgHandler();
		if (m_patientMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("appiontmentNursingMsgHandler == null");
			TipsDialog.GetInstance().show();
			return;
		}
		return;

	}

	private void updateHeight()
	{
		if (m_weightTitleHight != 0)
		{
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)m_weightHeaderRegionLL.getLayoutParams();
			layoutParams.height = m_weightTitleHight;
			m_weightHeaderRegionLL.setLayoutParams(layoutParams);
		}
	}

	private void cancelAction()
	{
		FragmentManager     fgManager           = getFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectWeightFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
		return;
	}

	/**
	 * set data
	 */
	public void setWeightTitleHight(int weightTitleHight)
	{
		m_weightTitleHight = weightTitleHight;
	}
}
