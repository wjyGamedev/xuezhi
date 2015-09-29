package com.taixinkanghu_client.work_flow.company_flow.nurse_level_introduction_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.company_flow.nurse_level_introduction_page.msg_handler.NurseLevelMsgHandler;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivityConfig;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/7/16.
 */
public class NurseLevelActivity extends Activity implements GestureDetector.OnGestureListener
{
	//widget
	private HeaderCommon m_headerCommon = null;
	private BottomCommon m_bottomCommon = null;

	//logical
	private NurseLevelMsgHandler m_nurseLevelMsgHandler = new NurseLevelMsgHandler(this);
	private ClickBottomBtn       m_clickBottomBtn       = new ClickBottomBtn();

	private GestureDetector m_GestureDetector;

	@Bind (R.id.worker_level_ic_iv)                              ImageView   m_workerLevelIconIv            = null;
	@Bind (R.id.worker_level_text_tv)                            TextView    m_workerLevelTextTv            = null;
	@Bind (R.id.worker_level_explain_tv)                         TextView    m_workerLevelExplainTv         = null;
	@Bind (R.id.worker_level_nursing_service_content_explain_tv) TextView    m_nurseServiceContentExplainTv = null;
	@Bind (R.id.rg_worker_level)                                 RadioGroup  m_workerLevelRg                = null;
	@Bind (R.id.three_star_btn)                                  RadioButton m_threeStarBtn                 = null;
	@Bind (R.id.four_star_btn)                                   RadioButton m_fourStarBtn                  = null;
	@Bind (R.id.five_star_btn)                                   RadioButton m_fiveStarBtn                  = null;
	@Bind (R.id.six_star_btn)                                    RadioButton m_sixStarBtn                   = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_level);
		ButterKnife.bind(this);

		init();

		m_GestureDetector = new GestureDetector(this, this);

		m_threeStarBtn.setChecked(true);


		m_workerLevelRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
												   {
													   @Override
													   public void onCheckedChanged(RadioGroup group, int checkedId)
													   {
														   if (checkedId == m_threeStarBtn.getId())
														   {
															   m_workerLevelIconIv.setImageResource(R.drawable.ic_star_3);
															   m_workerLevelTextTv.setText(getString(R.string.three_star_worker_text));
															   m_workerLevelExplainTv.setText(getString(R.string
																												.three_star_worker_explain));
															   m_nurseServiceContentExplainTv.setText(getString(R.string
																														.three_star_worker_service_content_explain));
														   }
														   else if (checkedId == m_fourStarBtn.getId())
														   {
															   m_workerLevelIconIv.setImageResource(R.drawable.ic_star_4);
															   m_workerLevelTextTv.setText(getString(R.string.four_star_worker_text));
															   m_workerLevelExplainTv.setText(getString(R.string
																												.four_star_worker_explain));
															   m_nurseServiceContentExplainTv.setText(getString(R.string
																														.four_star_worker_service_content_explain));
														   }
														   else if (checkedId == m_fiveStarBtn.getId())
														   {
															   m_workerLevelIconIv.setImageResource(R.drawable.ic_star_5);
															   m_workerLevelTextTv.setText(getString(R.string.five_star_worker_text));
															   m_workerLevelExplainTv.setText(getString(R.string
																												.five_star_worker_explain));
															   m_nurseServiceContentExplainTv.setText(getString(R.string
																														.five_star_worker_service_content_explain));
														   }
														   else if (checkedId == m_sixStarBtn.getId())
														   {
															   m_workerLevelIconIv.setImageResource(R.drawable.ic_star_6);
															   m_workerLevelTextTv.setText(getString(R.string.six_star_worker_text));
															   m_workerLevelExplainTv.setText(getString(R.string.six_star_worker_explain));
															   m_nurseServiceContentExplainTv.setText(getString(R.string
																														.six_star_worker_service_content_explain));
														   }
													   }
												   }
												  );
	}

	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.nurse_level_title);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.content_main_page);
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
			m_nurseLevelMsgHandler.go2MainPage();
		}
	}

	/**
	 * inner func
	 */


	public boolean dispatchTouchEvent(MotionEvent event)
	{
		if (m_GestureDetector.onTouchEvent(event))
		{
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		if (e2.getX() - e1.getX() > MainActivityConfig.DELTA_MOTION_EVENT)
		{             // 从左向右滑动（左进右出）
			if (m_workerLevelRg.getCheckedRadioButtonId() == m_threeStarBtn.getId())
			{
				m_sixStarBtn.setChecked(true);
			}
			else if (m_workerLevelRg.getCheckedRadioButtonId() == m_fourStarBtn.getId())
			{
				m_threeStarBtn.setChecked(true);
			}
			else if (m_workerLevelRg.getCheckedRadioButtonId() == m_fiveStarBtn.getId())
			{
				m_fourStarBtn.setChecked(true);
			}
			else if (m_workerLevelRg.getCheckedRadioButtonId() == m_sixStarBtn.getId())
			{
				m_fiveStarBtn.setChecked(true);
			}
			return true;
		}
		else if (e2.getX() - e1.getX() < -MainActivityConfig.DELTA_MOTION_EVENT)
		{         // 从右向左滑动（右进左出）
			if (m_workerLevelRg.getCheckedRadioButtonId() == m_threeStarBtn.getId())
			{
				m_fourStarBtn.setChecked(true);
			}
			else if (m_workerLevelRg.getCheckedRadioButtonId() == m_fourStarBtn.getId())
			{
				m_fiveStarBtn.setChecked(true);
			}
			else if (m_workerLevelRg.getCheckedRadioButtonId() == m_fiveStarBtn.getId())
			{
				m_sixStarBtn.setChecked(true);
			}
			else if (m_workerLevelRg.getCheckedRadioButtonId() == m_sixStarBtn.getId())
			{
				m_threeStarBtn.setChecked(true);
			}
			return true;
		}

		return true;
	}

}
