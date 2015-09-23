/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/29		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_info_page.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.circleimageview.CircleImageView;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.AnswerNurseSeniorListEvent;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_info_page.msg_handler.NurseInfoMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NurseInfoActivity extends BaseActivity
{
	//widget
	private                                           HeaderCommon    m_headerCommon                 = null;
	//nurse basic
	@Bind (R.id.header_img_civ)                       CircleImageView m_circleImageView              = null;    //头像
	@Bind (R.id.name_tv)                              TextView        m_nameTV                       = null;         //姓名
	@Bind (R.id.nuring_level_tv)                      TextView        m_nuringLevelTV                = null;         //护理级别
	@Bind (R.id.job_num_tv)                           TextView        m_jobNumTV                     = null;     //工号
	@Bind (R.id.sex_tv)                               TextView        m_sexTV                        = null;     //性别
	@Bind (R.id.nuring_exp_tv)                        TextView        m_NuringExpTV                  = null;     //护理经验
	@Bind (R.id.age_tv)                               TextView        m_ageTV                        = null;     //年龄
	@Bind (R.id.hometown_tv)                          TextView        m_hometownTV                   = null;     //籍贯
	//nurse senior
	@Bind (R.id.language_level_tv)                    TextView        m_languageLevelTV              = null;     //语言等级
	@Bind (R.id.nation_tv)                            TextView        m_nationTV                     = null;    //民族
	@Bind (R.id.education_level_tv)                   TextView        m_educationLevelTV             = null;    //教育程度
	@Bind (R.id.intro_tv)                             TextView        m_introTV                      = null;    //自我介绍
	@Bind (R.id.certificate_tv)                       TextView        m_certificateTV                = null;    //持有证书
	@Bind (R.id.service_content_tv)                   TextView        m_serviceContentTV             = null;    //服务内容
	@Bind (R.id.comment_rate_tv)                      TextView        m_commentRateTV                = null;    //好评率
	@Bind (R.id.comment_num_tv)                       TextView        m_commentNumTV                 = null;    //好评数量
	//价格
	@Bind (R.id.service_charge_per_all_care_tv)       TextView        m_serviceChargePerAllCareTV    = null;    //24小时，可自理
	@Bind (R.id.service_charge_per_all_half_care_tv)  TextView        m_serviceChargePerAllHalfCare  = null;    //24小时，半自理
	@Bind (R.id.service_charge_per_all_cannt_care_tv) TextView        m_serviceChargePerAllCanntCare = null;    //24小时，不可自理

	@Bind (R.id.service_charge_per_day_care_tv)       TextView m_serviceChargePerDayCare      = null;     //12白，可自理
	@Bind (R.id.service_charge_per_day_half_care_tv)  TextView m_serviceChargePerDayHalfCare  = null;    //12白，半自理
	@Bind (R.id.service_charge_per_day_cannt_care_tv) TextView m_serviceChargePerDayCanntCare = null;    //12白，不可自理

	@Bind (R.id.service_charge_per_night_care_tv)       TextView m_serviceChargePerNightCare      = null;   //12黑，可自理
	@Bind (R.id.service_charge_per_night_half_care_tv)  TextView m_serviceChargePerNightHalfCare  = null;  //12黑，半自理
	@Bind (R.id.service_charge_per_night_cannt_care_tv) TextView m_serviceChargePerNightCanntCare = null;  //12黑，不可自理

	//click region and buttion
	@Bind (R.id.btn_reviews)   LinearLayout m_ReviewsBtn  = null;
	@Bind (R.id.btn_goto_main) Button       m_gotoMainBtn = null;
	@Bind (R.id.btn_select)    Button       m_selectBtn   = null;

	//logical
	private NurseInfoMsgHandler m_nurseInfoMsgHandler = new NurseInfoMsgHandler(this);
	private ClickHeaderBack        m_clickHeaderBack              = new ClickHeaderBack();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_info);
		ButterKnife.bind(this);

		init();
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
			m_nurseInfoMsgHandler.backAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
	/**
	 * widget event
	 */
	@OnClick (R.id.btn_reviews)
	public void clickCommentRegion()
	{
		m_nurseInfoMsgHandler.go2CommentPage();
		return;
	}

	@OnClick (R.id.btn_goto_main)
	public void clickGotoMainBtn()
	{
		m_nurseInfoMsgHandler.go2MainPage();
		return;
	}

	@OnClick (R.id.btn_select)
	public void clickSelectBtn()
	{
		m_nurseInfoMsgHandler.go2OrderConfirmPage();
		return;
	}

	/**
	 * override func
	 */
	class ClickHeaderBack implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_nurseInfoMsgHandler.backAction();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//widget
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.nurse_info);
		m_headerCommon.getBackIBtn().setOnClickListener(m_clickHeaderBack);

	}

	public void updateContent()
	{
		m_nurseInfoMsgHandler.updateContent();
	}

	/**
	 * get widget
	 */
	public CircleImageView getCircleImageView()
	{
		return m_circleImageView;
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

	public TextView getSexTV()
	{
		return m_sexTV;
	}

	public TextView getNuringExpTV()
	{
		return m_NuringExpTV;
	}

	public TextView getAgeTV()
	{
		return m_ageTV;
	}

	public TextView getHometownTV()
	{
		return m_hometownTV;
	}

	public TextView getLanguageLevelTV()
	{
		return m_languageLevelTV;
	}

	public TextView getNationTV()
	{
		return m_nationTV;
	}

	public TextView getEducationLevelTV()
	{
		return m_educationLevelTV;
	}

	public TextView getIntroTV()
	{
		return m_introTV;
	}

	public TextView getCertificateTV()
	{
		return m_certificateTV;
	}

	public TextView getServiceContentTV()
	{
		return m_serviceContentTV;
	}

	public TextView getCommentRateTV()
	{
		return m_commentRateTV;
	}

	public TextView getCommentNumTV()
	{
		return m_commentNumTV;
	}

	public TextView getServiceChargePerAllCareTV()
	{
		return m_serviceChargePerAllCareTV;
	}

	public TextView getServiceChargePerAllHalfCare()
	{
		return m_serviceChargePerAllHalfCare;
	}

	public TextView getServiceChargePerAllCanntCare()
	{
		return m_serviceChargePerAllCanntCare;
	}

	public TextView getServiceChargePerDayCare()
	{
		return m_serviceChargePerDayCare;
	}

	public TextView getServiceChargePerDayHalfCare()
	{
		return m_serviceChargePerDayHalfCare;
	}

	public TextView getServiceChargePerDayCanntCare()
	{
		return m_serviceChargePerDayCanntCare;
	}

	public TextView getServiceChargePerNightCare()
	{
		return m_serviceChargePerNightCare;
	}

	public TextView getServiceChargePerNightHalfCare()
	{
		return m_serviceChargePerNightHalfCare;
	}

	public TextView getServiceChargePerNightCanntCare()
	{
		return m_serviceChargePerNightCanntCare;
	}

	/**
	 * EventBus  handler
	 */
	public void onEventMainThread(AnswerNurseSeniorListEvent event)
	{
		updateContent();
	}

}
