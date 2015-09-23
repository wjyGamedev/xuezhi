/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.nurse_info.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/9		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_info_page.msg_handler;

import android.content.Intent;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.data_module.nurse_list.data.DCommentList;
import com.taixinkanghu_client.data_module.nurse_list.data.DFaceImages;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurse;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasics;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseList;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseSenior;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.NurseListHandler;
import com.taixinkanghu_client.work_flow.change_nurse_flow.BaseChangeNurseFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.change_nurse_flow.comment_page.ui.CommentActivity;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_info_page.ui.NurseInfoActivity;
import com.taixinkanghu_client.work_flow.change_nurse_flow.nurse_order_confirm_page.ui.OrderConfirmActivity;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;

public class NurseInfoMsgHandler extends BaseChangeNurseFlowUIMsgHandler
{
	public NurseInfoMsgHandler(NurseInfoActivity activity)
	{
		super(activity);
	}

	//01. 跳转到评论页面
	public void go2CommentPage()
	{
		NurseInfoActivity activity = (NurseInfoActivity)m_context;
		activity.startActivity(new Intent(activity, CommentActivity.class));
		return;
	}

	//02. 返回主页面
	public void go2MainPage()
	{
		//01. 跳转到主页面
		NurseInfoActivity activity = (NurseInfoActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));

		//02. 清楚流程信息
		m_dChangeNurseFlow.clearupAll();
		return;
	}

	//03. 跳转到订单确认页面
	public void go2OrderConfirmPage()
	{
		NurseInfoActivity activity = (NurseInfoActivity)m_context;
		Intent intent = new Intent(activity, OrderConfirmActivity.class);
		activity.startActivity(intent);
		return;
	}

	//04. 使用预约陪护流程的数据来填充当前UI界面。
	public void updateContent()
	{
		//01. 获取当前选中的护工ID，并检查有效性
		int selectedNurseID = m_dChangeNurseFlow.getNewNurseID();
		if (!DNurseList.GetInstance().checkNurseID(selectedNurseID))
		{
			TipsDialog.GetInstance().setMsg("selectedNurseID is invalid![selectedNurseID:=" + selectedNurseID + "]");
			TipsDialog.GetInstance().show();
			return;
		}

		DNurse nurseInfo = DNurseList.GetInstance().getNurseInfoByID(selectedNurseID);
		if (nurseInfo == null)
		{
			TipsDialog.GetInstance().setMsg("nurseInfo == null![selectedNurseID:=" + selectedNurseID + "]");
			TipsDialog.GetInstance().show();
			return;
		}

		//由于从业务逻辑上，该页面是在nurse basic list之后，所以下面的不需要做判断。
		DNurseBasics nurseBasics = nurseInfo.getNurseBasics();

		//由于下面这个消息可能会接收不到，所以需要进行判断是否需要重新发送
		DNurseSenior nurseSenior = nurseInfo.getNurseSenior();
		if (nurseSenior.getID() == DataConfig.DEFAULT_VALUE)
		{
			requestNurseSeniorListAction();
			return;
		}

		//02. update data
		NurseInfoActivity nurseInfoActivity = (NurseInfoActivity)m_context;
		if (nurseInfoActivity == null)
		{
			TipsDialog.GetInstance().setMsg("nurseInfoActivity == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		//nurse basic
		int headerImgResID = DFaceImages.getInstance().getImgResIDbyID(selectedNurseID);
		if (headerImgResID == DataConfig.DEFAULT_VALUE)
		{
			headerImgResID = DFaceImages.DEFAULT_IMAGE_RES_ID;
		}
		nurseInfoActivity.getCircleImageView().setImageResource(headerImgResID);
		nurseInfoActivity.getNameTV().setText(nurseBasics.getName());
		nurseInfoActivity.getNuringLevelTV().setText(nurseBasics.getNursingLevel());
		nurseInfoActivity.getJobNumTV().setText(String.valueOf(nurseSenior.getJobNum()));
		nurseInfoActivity.getSexTV().setText(nurseBasics.getGender());
		nurseInfoActivity.getNuringExpTV().setText(nurseBasics.getNursingExp());
		nurseInfoActivity.getAgeTV().setText(String.valueOf(nurseBasics.getAge()));
		nurseInfoActivity.getHometownTV().setText(nurseBasics.getHomeTown());
		//nurse senior
		nurseInfoActivity.getLanguageLevelTV().setText(nurseSenior.getLanguageLevel());
		nurseInfoActivity.getNationTV().setText(nurseSenior.getNation());
		nurseInfoActivity.getEducationLevelTV().setText(nurseSenior.getEducationLevel());
		nurseInfoActivity.getIntroTV().setText(nurseSenior.getIntro());
		nurseInfoActivity.getCertificateTV().setText(nurseSenior.getCertificate());
		nurseInfoActivity.getServiceContentTV().setText(nurseSenior.getServiceContent());
		//评论

		Integer rate = 0;
		Integer num = 0;
		DCommentList commentList = nurseSenior.getCommentList();
		if (commentList != null)
		{
			rate = (int)commentList.getCommentRate()*100;
			num = commentList.getCommentNum();
		}
		String rateDisplay = m_context.getString(R.string.comment_good_rate) + rate.toString();
		String numDispaly = num.toString() + m_context.getString(R.string.comment_num_person);
		nurseInfoActivity.getCommentRateTV().setText(rateDisplay);
		nurseInfoActivity.getCommentNumTV().setText(numDispaly);

		//价格
		String charge = String.valueOf(nurseBasics.getServiceChargePerAllCare());
		String unit   = nurseInfoActivity.getString(R.string.content_yuan_per_day);
		charge += unit;
		nurseInfoActivity.getServiceChargePerAllCareTV().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerAllHalfCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerAllHalfCare().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerAllCanntCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerAllCanntCare().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerDayCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerDayCare().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerDayHalfCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerDayHalfCare().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerDayCanntCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerDayCanntCare().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerNightCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerNightCare().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerNightHalfCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerNightHalfCare().setText(charge);

		charge = String.valueOf(nurseBasics.getServiceChargePerNightCanntCare());
		charge += unit;
		nurseInfoActivity.getServiceChargePerNightCanntCare().setText(charge);
	}

	//05. 请求发送nurse senior list消息
	public void requestNurseSeniorListAction()
	{
		NurseListHandler.GetInstance().requestNurseSeniorListAction();
		return;
	}

	public void backAction()
	{
		NurseInfoActivity activity = (NurseInfoActivity)m_context;
		activity.finish();

		m_dChangeNurseFlow.clearupNurseInfo();

		return;
	}
}
