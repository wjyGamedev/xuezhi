package com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.ui;

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
import com.taixinkanghu_client.work_flow.repeat_order_flow.appointment_nursing_page.msg_handler.RepeatOrderApoitNursingMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/7/28.
 */
public class SelectAgeFragment extends Fragment
{
	//widget
	@Bind (R.id.age_header_ll)         LinearLayout m_ageHeaderRegionLL = null;
	@Bind (R.id.age_range_0_15_tv)     TextView     m_0To15AgeBtn       = null;
	@Bind (R.id.age_range_16_35_tv)    TextView     m_16To35AgeBtn      = null;
	@Bind (R.id.age_range_36_55_tv)    TextView     m_36To55AgeBtn      = null;
	@Bind (R.id.age_range_56_75_tv)    TextView     m_56To75AgeBtn      = null;
	@Bind (R.id.age_range_above_75_tv) TextView     m_above75AgeBtn     = null;

	//logical
	private int                               m_ageTitleHight                     = DataConfig.DEFAULT_VALUE;
	private RepeatOrderApoitNursingMsgHandler m_repeatOrderApoitNursingMsgHandler = null;
	private View                              m_view                              = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_select_age, container, false);
		ButterKnife.bind(this, m_view);

		init();
		//设置顶部LL控件高度
		updateHeight();

		return m_view;
	}

	/**
	 * widget event
	 */
	@OnClick (R.id.age_header_ll)
	public void clickAgeHeaderRegion()
	{
		cancelAction();
		return;
	}

	@OnClick (R.id.age_range_0_15_tv)
	public void clickAgeRange_0_15()
	{
		m_repeatOrderApoitNursingMsgHandler.setAgeRage(EnumConfig.AgeRage.AGE_0_15);
		cancelAction();
		return;
	}

	@OnClick (R.id.age_range_16_35_tv)
	public void clickAgeRange_16_35()
	{
		m_repeatOrderApoitNursingMsgHandler.setAgeRage(EnumConfig.AgeRage.AGE_16_35);
		cancelAction();
		return;
	}

	@OnClick (R.id.age_range_36_55_tv)
	public void clickAgeRange_36_55()
	{
		m_repeatOrderApoitNursingMsgHandler.setAgeRage(EnumConfig.AgeRage.AGE_36_55);
		cancelAction();
		return;
	}

	@OnClick (R.id.age_range_56_75_tv)
	public void clickAgeRange_56_75()
	{
		m_repeatOrderApoitNursingMsgHandler.setAgeRage(EnumConfig.AgeRage.AGE_56_75);
		cancelAction();
		return;
	}

	@OnClick (R.id.age_range_above_75_tv)
	public void clickAgeRange_above_75()
	{
		m_repeatOrderApoitNursingMsgHandler.setAgeRage(EnumConfig.AgeRage.AGE_MORE_THAN_76);
		cancelAction();
		return;
	}

	@OnClick (R.id.age_bottom_ll)
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
		RepeatOrderApoitNursingActivity activity = (RepeatOrderApoitNursingActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("RepeatOrderApoitNursingActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_repeatOrderApoitNursingMsgHandler = activity.getRepeatOrderApoitNursingMsgHandler();
		if (m_repeatOrderApoitNursingMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("appiontmentNursingMsgHandler == null");
			TipsDialog.GetInstance().show();
			return;
		}
		return;

	}

	private void updateHeight()
	{
		if (m_ageTitleHight != DataConfig.DEFAULT_VALUE)
		{
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)m_ageHeaderRegionLL.getLayoutParams();
			layoutParams.height = m_ageTitleHight;
			m_ageHeaderRegionLL.setLayoutParams(layoutParams);
		}
	}

	private void cancelAction()
	{
		FragmentManager     fgManager           = getFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectAgeFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
		return;
	}

	/**
	 * set data
	 */
	public void setAgeTitleHight(int ageTitleHight)
	{
		m_ageTitleHight = ageTitleHight;
	}

}
