package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.msg_handler.AppiontmentNursingMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/7/20.
 */
public class SelectSexFragment extends Fragment
{
	//widget
	@Bind (R.id.gender_header_ll) LinearLayout m_genderHeaderLL = null;

	//logical
	private int                          m_genderTitleHight             = DataConfig.DEFAULT_VALUE;
	private AppiontmentNursingMsgHandler m_appiontmentNursingMsgHandler = null;
	private View m_view = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_select_gender, container, false);
		ButterKnife.bind(this, m_view);

		init();
		//设置顶部LL控件高度
		updateHeight();

		return m_view;
	}

	/**
	 * widget event
	 */
	@OnClick (R.id.gender_header_ll)
	public void clickGenderHeader()
	{
		cancelAction();
		return;
	}

	@OnClick (R.id.male_tv)
	public void clickMaleTV()
	{
		m_appiontmentNursingMsgHandler.setGenderStatus(EnumConfig.GenderStatus.MALE);
		cancelAction();
		return;
	}

	@OnClick (R.id.female_tv)
	public void clickFemalTV()
	{
		m_appiontmentNursingMsgHandler.setGenderStatus(EnumConfig.GenderStatus.FEMALE);
		cancelAction();
		return;
	}

	@OnClick (R.id.gender_bottom_ll)
	public void clickGenderBottom()
	{
		cancelAction();
		return;
	}

	/**
	 * inner func
	 */
	private void init()
	{
		ApoitNursingActivity activity = (ApoitNursingActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("ApoitNursingActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_appiontmentNursingMsgHandler = activity.getAppiontmentNursingMsgHandler();
		if (m_appiontmentNursingMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("appiontmentNursingMsgHandler == null");
			TipsDialog.GetInstance().show();
			return;
		}
		return;

	}

	private void updateHeight()
	{
		if (m_genderTitleHight != DataConfig.DEFAULT_VALUE)
		{
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)m_genderHeaderLL.getLayoutParams();
			layoutParams.height = m_genderTitleHight;
			m_genderHeaderLL.setLayoutParams(layoutParams);
		}
	}

	private void cancelAction()
	{
		FragmentManager     fgManager           = getFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectSexFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
		return;
	}

	/**
	 * set data
	 */
	public void setGenderTitleHight(int genderTitleHight)
	{
		m_genderTitleHight = genderTitleHight;
	}

}
