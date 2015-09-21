/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.nurse_order_page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/22		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow.ui;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.util.logcal.LogicalUtil;
import com.module.widget.circleimageview.CircleImageView;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.data_module.nurse_list.data.DFaceImages;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasics;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseSenior;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.work_flow.nurse_order_flow.msg_handler.NurseOrderMsgHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BaseListItem
{
	//widget
	@Bind (R.id.order_click_region_ll) LinearLayout    m_orderCliceRegionLL = null;    //订单点击region
	@Bind (R.id.header_img_iv)         CircleImageView m_nurseHeaderImgIV   = null;        //头像
	@Bind (R.id.nurse_name_tv)         TextView        m_nurseNameTV        = null;            //名字
	@Bind (R.id.order_state_tv)        TextView        m_orderStatus        = null;            //订单状态
	@Bind (R.id.order_begin_date_tv)   TextView        m_orderBeginDate     = null;            //服务的起始时间
	@Bind (R.id.order_end_date_tv)     TextView        m_orderEndDate       = null;            //服务的结束时间
	@Bind (R.id.total_day_num_tv)      TextView        m_totalDayNum        = null;            //服务总天数。
	@Bind (R.id.total_price_tv)        TextView        m_totalPrice         = null;                //总价格
	@Bind (R.id.func_btn_1)            Button          m_funcBtn_1          = null;    //功能按钮1
	@Bind (R.id.func_btn_2)            Button          m_funcBtn_2          = null;    //功能按钮2
	@Bind (R.id.func_btn_3)            Button          m_funcBtn_3          = null;    //功能按钮3
	@Bind (R.id.func_btn_4)            Button          m_funcBtn_4          = null;    //功能按钮4
	@Bind (R.id.func_btn_5)            Button          m_funcBtn_5          = null;    //功能按钮5

	//logical
	protected Activity    m_activity   = null;
	protected View        m_view       = null;
	private   DNurseOrder m_nurseOrder = null;

	private SimpleDateFormat m_simpleDateFormat = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	public BaseListItem(Activity activity, View view)
	{
		m_activity = activity;
		m_view = view;
		ButterKnife.bind(this, view);
	}

	/**
	 * widget:func
	 */
	@OnClick (R.id.func_btn_1)
	public void clickFuncBtn_1(View view)
	{
		funcBtnActions(view);
	}

	@OnClick (R.id.func_btn_2)
	public void clickFuncBtn_2(View view)
	{
		funcBtnActions(view);
	}

	@OnClick (R.id.func_btn_3)
	public void clickFuncBtn_3(View view)
	{
		funcBtnActions(view);
	}

	@OnClick (R.id.func_btn_4)
	public void clickFuncBtn_4(View view)
	{
		funcBtnActions(view);
	}

	@OnClick (R.id.func_btn_5)
	public void clickFuncBtn_5(View view)
	{
		funcBtnActions(view);
	}

	//派生类重载， 用来区分功能区。
	public void initFuncWidget(DNurseOrder nurseOrder)
	{
		return;
	}

	public void initContent(DNurseOrder nurseOrder)
	{
		//01. common content
		if (nurseOrder == null)
		{
			TipsDialog.GetInstance().setMsg("nurseOrder == null");
			TipsDialog.GetInstance().show();
			return;
		}

		DNurseBasics nurseBasics = nurseOrder.getNurseBasics();
		if (nurseBasics == null)
		{
			TipsDialog.GetInstance().setMsg("nurseBasics == null");
			TipsDialog.GetInstance().show();
			return;
		}
		DNurseSenior nurseSenior = nurseOrder.getNurseSenior();
		if (nurseSenior == null)
		{
			TipsDialog.GetInstance().setMsg("nurseSenior == null");
			TipsDialog.GetInstance().show();
			return;
		}
		m_nurseOrder = nurseOrder;

		int headerImgResID = DFaceImages.getInstance().getImgResIDbyID(m_nurseOrder.getNurseID());
		if (headerImgResID == DataConfig.DEFAULT_VALUE)
		{
			headerImgResID = DFaceImages.DEFAULT_IMAGE_RES_ID;
		}
		m_nurseHeaderImgIV.setImageResource(headerImgResID);

		String nurseName = nurseBasics.getName();
		m_nurseNameTV.setText(nurseName);

		EnumConfig.NurseOrderStatus orderStatus = m_nurseOrder.getOrderStatus();
		if (orderStatus != null)
		{
			m_orderStatus.setText(orderStatus.getName());
		}

		Date   beginDate       = m_nurseOrder.getBeginDate();
		String dateDescription = null;
		if (beginDate != null)
		{
			dateDescription = m_simpleDateFormat.format(beginDate);
			m_orderBeginDate.setText(dateDescription);
		}

		Date endDate = m_nurseOrder.getEndDate();
		if (endDate != null)
		{
			dateDescription = m_simpleDateFormat.format(endDate);
			m_orderEndDate.setText(dateDescription);
		}

		int days = LogicalUtil.GetDayNums(beginDate, endDate);
		m_totalDayNum.setText(String.valueOf(days));

		int totalPrice = m_nurseOrder.getTotalCharge();
		m_totalPrice.setText(String.valueOf(totalPrice));

		//02. special content
		initFuncWidget(nurseOrder);
		return;
	}

	private void funcBtnActions(View v)
	{
		NurseOrderActivity nurseOrderActivity = (NurseOrderActivity)m_activity;
		if (nurseOrderActivity == null)
		{
			TipsDialog.GetInstance().setMsg("nurseOrderActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		//01. 设置当前选中的nurseID
		FuncBtnTabObject funcBtnTabObject = (FuncBtnTabObject)v.getTag();
		String           funcTag          = null;
		DNurseOrder      nurseOrder       = null;
		if (funcBtnTabObject != null)
		{
			funcTag = funcBtnTabObject.getFuncTag();
			nurseOrder = funcBtnTabObject.getNurseOrder();
		}

		if (nurseOrder == null)
		{
			TipsDialog.GetInstance().setMsg("nurseOrder == null");
			TipsDialog.GetInstance().show();
			return;
		}

		int                  nurseOrderID         = nurseOrder.getOrderID();
		NurseOrderMsgHandler nurseOrderMsgHandler = nurseOrderActivity.getNurseOrderMsgHandler();
		nurseOrderMsgHandler.setSelectedNurseOrderID(nurseOrderID);

		//02. 根据tag，来进行不同的action
		//0201. 取消订单
		if (TextUtils.equals(funcTag, NurseOrderActivity.FUNC_BTN_TAG_CANCEL_ORDER))
		{
			nurseOrderMsgHandler.cancelOrderInWaitPayAction();
			return;
		}
		//0202. 确认支付
		else if (TextUtils.equals(funcTag, NurseOrderActivity.FUNC_BTN_TAG_PAY_ORDER))
		{
			nurseOrderMsgHandler.readyPayOrderInWaitPayAction();
			return;
		}
		//0203. 评价
		else if (TextUtils.equals(funcTag, NurseOrderActivity.FUNC_BTN_TAG_COMMENT_ORDER))
		{
			nurseOrderMsgHandler.commentAction();
			return;
		}
		//0204. 续订
		else if (TextUtils.equals(funcTag, NurseOrderActivity.FUNC_BTN_TAG_REPEAT_ORDER))
		{
			nurseOrderMsgHandler.repeatOrderAction();
			return;
		}
		//0205. 更换
		else if (TextUtils.equals(funcTag, NurseOrderActivity.FUNC_BTN_TAG_CHANGE_NURSE))
		{
			nurseOrderMsgHandler.changeNurseAction();
			return;
		}
		//0206. 退订
		else if (TextUtils.equals(funcTag, NurseOrderActivity.FUNC_BTN_TAG_CANCEL_SERVICE))
		{
			nurseOrderMsgHandler.cancelOrderInServiceAction();
			return;
		}
		//0207. 补差价
		else if (TextUtils.equals(funcTag, NurseOrderActivity.FUNC_BTN_TAG_PAY_MORE))
		{
			nurseOrderMsgHandler.payMoreAction();
			return;
		}
		return;

	}

	/**
	 * data get
	 */
	public DNurseOrder getNurseOrder()
	{
		return m_nurseOrder;
	}

	/**
	 * func action
	 */
	protected void waitPayfuncAction(BaseListItem baseListItem)
	{
		//只有确认支付，取消订单两个按钮
		m_funcBtn_1.setVisibility(View.VISIBLE);
		m_funcBtn_2.setVisibility(View.VISIBLE);
		m_funcBtn_3.setVisibility(View.GONE);
		m_funcBtn_4.setVisibility(View.GONE);
		m_funcBtn_5.setVisibility(View.GONE);

		//确认支付
		m_funcBtn_1.setText(m_activity.getString(R.string.confirm_pay));

		FuncBtnTabObject funcBtnTabObject01 = new FuncBtnTabObject(baseListItem.getNurseOrder(), NurseOrderActivity
				.FUNC_BTN_TAG_PAY_ORDER);
		m_funcBtn_1.setTag(funcBtnTabObject01);

		//取消订单
		m_funcBtn_2.setText(m_activity.getString(R.string.cancel_order));

		FuncBtnTabObject funcBtnTabObject02 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_CANCEL_ORDER
		);
		m_funcBtn_2.setTag(funcBtnTabObject02);

		return;
	}

	protected void waitCashPayfuncAction(BaseListItem baseListItem)
	{
		//只有取消订单
		m_funcBtn_1.setVisibility(View.VISIBLE);
		m_funcBtn_2.setVisibility(View.GONE);
		m_funcBtn_3.setVisibility(View.GONE);
		m_funcBtn_4.setVisibility(View.GONE);
		m_funcBtn_5.setVisibility(View.GONE);

		//取消订单
		m_funcBtn_1.setText(m_activity.getString(R.string.cancel_order));

		FuncBtnTabObject funcBtnTabObject02 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_CANCEL_ORDER
		);
		m_funcBtn_1.setTag(funcBtnTabObject02);

		return;
	}

	//等待评价
	protected void waitCommentfuncAction(BaseListItem baseListItem)
	{
		//只有确认续订，评论订单两个按钮
		m_funcBtn_1.setVisibility(View.VISIBLE);
		m_funcBtn_2.setVisibility(View.VISIBLE);
		m_funcBtn_3.setVisibility(View.GONE);
		m_funcBtn_4.setVisibility(View.GONE);
		m_funcBtn_5.setVisibility(View.GONE);

		//确认续订
		m_funcBtn_1.setText(m_activity.getString(R.string.repeat_order));

		FuncBtnTabObject funcBtnTabObject01 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_REPEAT_ORDER
		);
		m_funcBtn_1.setTag(funcBtnTabObject01);

		//评论
		m_funcBtn_2.setText(m_activity.getString(R.string.commente_order));

		FuncBtnTabObject funcBtnTabObject04 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_COMMENT_ORDER
		);
		m_funcBtn_2.setTag(funcBtnTabObject04);
	}

	//已取消订单
	protected void cancelfuncAction(BaseListItem baseListItem)
	{
		//只有续订按钮
		m_funcBtn_1.setVisibility(View.VISIBLE);
		m_funcBtn_2.setVisibility(View.GONE);
		m_funcBtn_3.setVisibility(View.GONE);
		m_funcBtn_4.setVisibility(View.GONE);
		m_funcBtn_5.setVisibility(View.GONE);

		//取消订单
		m_funcBtn_1.setText(m_activity.getString(R.string.repeat_order));

		FuncBtnTabObject funcBtnTabObject01 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_REPEAT_ORDER
		);
		m_funcBtn_1.setTag(funcBtnTabObject01);

	}


	protected void waitServicefuncAction(BaseListItem baseListItem)
	{
		//续订、更换、补差价、退订
		m_funcBtn_1.setVisibility(View.VISIBLE);
		m_funcBtn_2.setVisibility(View.VISIBLE);
		m_funcBtn_3.setVisibility(View.VISIBLE);
		m_funcBtn_4.setVisibility(View.VISIBLE);
		m_funcBtn_5.setVisibility(View.GONE);

		//续订
		m_funcBtn_1.setText(m_activity.getString(R.string.repeat_order));

		FuncBtnTabObject funcBtnTabObject01 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_REPEAT_ORDER
		);
		m_funcBtn_1.setTag(funcBtnTabObject01);

		//更换
		m_funcBtn_2.setText(m_activity.getString(R.string.change_nurse));

		FuncBtnTabObject funcBtnTabObject02 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_CHANGE_NURSE
		);
		m_funcBtn_2.setTag(funcBtnTabObject02);

		//补差价
		m_funcBtn_3.setText(m_activity.getString(R.string.pay_more));

		FuncBtnTabObject funcBtnTabObject03 = new FuncBtnTabObject(baseListItem.getNurseOrder(), NurseOrderActivity.FUNC_BTN_TAG_PAY_MORE);
		m_funcBtn_3.setTag(funcBtnTabObject03);

		//退订
		m_funcBtn_4.setText(m_activity.getString(R.string.cancel_service));

		FuncBtnTabObject funcBtnTabObject04 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_CANCEL_SERVICE
		);
		m_funcBtn_4.setTag(funcBtnTabObject04);

	}

	public void moreThanWaitServicefuncAction(BaseListItem baseListItem)
	{
		//续订、更换、补差价、评论，退订
		m_funcBtn_1.setVisibility(View.VISIBLE);
		m_funcBtn_2.setVisibility(View.VISIBLE);
		m_funcBtn_3.setVisibility(View.VISIBLE);
		m_funcBtn_4.setVisibility(View.VISIBLE);
		m_funcBtn_5.setVisibility(View.VISIBLE);

		//续订
		m_funcBtn_1.setText(m_activity.getString(R.string.repeat_order));

		FuncBtnTabObject funcBtnTabObject01 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_REPEAT_ORDER
		);
		m_funcBtn_1.setTag(funcBtnTabObject01);

		//更换
		m_funcBtn_2.setText(m_activity.getString(R.string.change_nurse));

		FuncBtnTabObject funcBtnTabObject02 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_CHANGE_NURSE
		);
		m_funcBtn_2.setTag(funcBtnTabObject02);

		//补差价
		m_funcBtn_3.setText(m_activity.getString(R.string.pay_more));

		FuncBtnTabObject funcBtnTabObject03 = new FuncBtnTabObject(baseListItem.getNurseOrder(), NurseOrderActivity.FUNC_BTN_TAG_PAY_MORE);
		m_funcBtn_3.setTag(funcBtnTabObject03);

		//评论
		m_funcBtn_4.setText(m_activity.getString(R.string.commente_order));

		FuncBtnTabObject funcBtnTabObject04 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_COMMENT_ORDER
		);
		m_funcBtn_4.setTag(funcBtnTabObject04);

		//退订
		m_funcBtn_5.setText(m_activity.getString(R.string.cancel_service));

		FuncBtnTabObject funcBtnTabObject05 = new FuncBtnTabObject(baseListItem.getNurseOrder(),
																   NurseOrderActivity.FUNC_BTN_TAG_CANCEL_SERVICE
		);
		m_funcBtn_5.setTag(funcBtnTabObject05);

		return;

	}

}
