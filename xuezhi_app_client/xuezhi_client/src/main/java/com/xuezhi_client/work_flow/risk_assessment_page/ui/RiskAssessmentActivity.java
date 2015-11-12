package com.xuezhi_client.work_flow.risk_assessment_page.ui;

import android.view.View;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.work_flow.risk_assessment_page.msg_handler.RiskAssessmentMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

/**
 * Created by Administrator on 2015/9/23.
 */
public class RiskAssessmentActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private RiskAssessmentMsgHandler m_riskAssessmentMsgHandler = new RiskAssessmentMsgHandler(this);
	private ClickBottomBtn           m_clickBottomBtn            = new ClickBottomBtn();


	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_risk_assessment);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onDestoryAction()
	{

	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.risk_assessment_page_title_text);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.risk_assessment_bottom_btn_text);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);
	}

	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_riskAssessmentMsgHandler.saveRiskAssessmentInfo();
		}
	}

}
