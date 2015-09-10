/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.nurse_order_comment.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.comment_nurse_order_flow.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.circleimageview.CircleImageView;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.work_flow.comment_nurse_order_flow.msg_handler.CommentNurseOrderMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentNurseOrderActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.header_img_civ)      CircleImageView m_circleImageView   = null;    //头像
	@Bind (R.id.name_tv)             TextView        m_nameTV            = null;         //姓名
	@Bind (R.id.job_num_tv)          TextView        m_jobNumTV          = null;     //工号
	@Bind (R.id.nuring_level_tv)     TextView        m_nuringLevelTV     = null;         //护理级别
	@Bind (R.id.func_region_rgrp)    RadioGroup      m_funcRegionRGrp    = null; //单选按钮组
	@Bind (R.id.very_satisfied_rbtn) RadioButton     m_verySatisfiedRBtn = null;    //非常满意
	@Bind (R.id.satisfied_rbtn)      RadioButton     m_satisfiedRBtn     = null;    //满意
	@Bind (R.id.general_rbtn)        RadioButton     m_generalRBtn       = null;    //一般
	@Bind (R.id.comment_content_et)  EditText        m_commentContentET  = null;    //评论内容
	@Bind (R.id.goto_main_btn)       RadioButton     m_toMainBtn         = null;    //返回到主页面
	@Bind (R.id.comment_btn)         RadioButton     m_commentBtn        = null;    //提交评论

	private CommentNurseOrderMsgHandler m_nurseOrderCommentMsgHandler = new CommentNurseOrderMsgHandler(this);
	private ClickHeaderCommon           m_clickHeaderCommon           = new ClickHeaderCommon();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_order_comment);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * widget event
	 */
	@OnClick (R.id.goto_main_btn)
	public void clickGotoMainBtn()
	{
		m_nurseOrderCommentMsgHandler.go2MainPage();
		return;
	}

	@OnClick (R.id.comment_btn)
	public void clickCommentBtn()
	{
		if (!checkValid())
		{
			return;
		}
		m_nurseOrderCommentMsgHandler.requestCommentNurseOrderAction();
		return;
	}

	/**
	 * override
	 */
	class ClickHeaderCommon implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			m_nurseOrderCommentMsgHandler.backAction();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//widget
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.comment_nurse_order_header_content);
		m_headerCommon.getBackIBtn().setOnClickListener(m_clickHeaderCommon);
	}

	private boolean checkValid()
	{
		int selectID = m_funcRegionRGrp.getCheckedRadioButtonId();
		if (selectID != R.id.very_satisfied_rbtn	&&
				selectID != R.id.satisfied_rbtn	&&
				selectID != R.id.general_rbtn)
		{
			TipsDialog.GetInstance().setMsg(getString(R.string.error_tips_select_comment_level), this);
			TipsDialog.GetInstance().show();
			return false;
		}

		String commentContent = getCommentContent();
		if (!TextUtils.isEmpty(commentContent))
		{
			TipsDialog.GetInstance().setMsg(getString(R.string.error_tips_write_comment_content), this);
			TipsDialog.GetInstance().show();
			return false;
		}

		return true;

	}

	/**
	 * data get
	 */
	public EnumConfig.CommentLevel getCommentLevel()
	{
		int selectID = m_funcRegionRGrp.getCheckedRadioButtonId();
		if (selectID == R.id.very_satisfied_rbtn)
		{
			return EnumConfig.CommentLevel.COMMENT_LEVEL_VERY_STATISFIED;
		}
		if (selectID == R.id.satisfied_rbtn)
		{
			return EnumConfig.CommentLevel.COMMENT_LEVEL_STATISFIED;
		}
		if (selectID == R.id.general_rbtn)
		{
			return EnumConfig.CommentLevel.COMMENT_LEVEL_GENERAL;
		}

		//TODO:出现了BUG
		return EnumConfig.CommentLevel.COMMENT_LEVEL_VERY_STATISFIED;

	}

	public String getCommentContent()
	{
		return m_commentContentET.getText().toString().trim();
	}


}

