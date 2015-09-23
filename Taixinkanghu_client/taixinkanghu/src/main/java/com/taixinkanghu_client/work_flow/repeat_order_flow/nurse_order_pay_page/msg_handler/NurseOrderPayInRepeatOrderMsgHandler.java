/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.nurse_order_pay.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/10		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.repeat_order_flow.nurse_order_pay_page.msg_handler;

import android.content.Intent;
import android.text.TextUtils;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.NurseListHandler;
import com.taixinkanghu_client.data_module.nurse_list.msg_handler.RequestNurseBasicListEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerNurseOrderAlipayEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.AnswerNurseOrderCheckEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.NurseOrderListHandler;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderAlipayEvent;
import com.taixinkanghu_client.data_module.nurse_order_list.msg_handler.RequestNurseOrderCheckEvent;
import com.taixinkanghu_client.data_module.register_account.data.DAccount;
import com.taixinkanghu_client.net.config.NurseOrderConfig;
import com.taixinkanghu_client.work_flow.main_page.ui.MainActivity;
import com.taixinkanghu_client.work_flow.repeat_order_flow.BaseRepeatFlowUIMsgHandler;
import com.taixinkanghu_client.work_flow.repeat_order_flow.nurse_order_pay_page.ui.NurseOrderPayInRepeatOrderActivity;
import com.third.part.alipay.Config;
import com.third.part.alipay.PayResult;
import com.third.part.alipay.Util;
import com.third.part.alipay.UtilRSA;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NurseOrderPayInRepeatOrderMsgHandler extends BaseRepeatFlowUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public NurseOrderPayInRepeatOrderMsgHandler(NurseOrderPayInRepeatOrderActivity activity)
	{
		super(activity);
	}

	//01. 同步数据到UI上
	public void updateContent()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		String orderSerialNum = m_dRepeatOrderFlow.getOrderSerialNum();
		activity.getOrderSerialNumTV().setText(orderSerialNum);

		int totalPrice = m_dRepeatOrderFlow.getTotalPrice();
		activity.getPriceTV().setText(String.valueOf(totalPrice));

		return;
	}


	//02. 请发发送nurse order check
	public void requestNurseOrderCheckAction()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		RequestNurseOrderCheckEvent event = new RequestNurseOrderCheckEvent();

		String userID = DAccount.GetInstance().getId();
		event.setUserID(userID);

		int orderID = m_dRepeatOrderFlow.getOrderID();
		event.setOrderID(String.valueOf(orderID));

		String payType = null;
		//支付类型
		if (activity.isCashPay())
		{
			payType = "cash";
		}
		else if (activity.isAlipay())
		{
			payType = "alipay";
		}
		else if (activity.isWeixinPay())
		{
			payType = "weinxin";
		}
		else
		{
			TipsDialog.GetInstance().setMsg("pay type is invalid!", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		event.setType(payType);

		NurseOrderListHandler.GetInstance().requestNurseOrderCheckAction(event);
		return;
	}

	//03. 对于nurse order check消息回复的处理
	public void onEventMainThread(AnswerNurseOrderCheckEvent event)
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		if (event.getHttpStatus() == NurseOrderConfig.NURSE_IN_SERVICE)
		{
			activity.inServiceAction();
		}
		else
		{
			checkSuccessAction();
		}
		return;
	}

	//04. nurse order check failed action
	public void checkFailedAction()
	{
		//01. 清除：选择护理员数据，护理员详细信息数据，确认订单数据
		m_dRepeatOrderFlow.clearupAll();

		//03. 跳转到主页面
		go2MainPage();

		return;
	}

	public void go2MainPage()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		//01. 清楚数据
		m_dRepeatOrderFlow.clearupAll();

		//02. 跳转到主页面
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);

		return;

	}

	//05. nurse basic list
	public void requestNurseBasicListAction()
	{
		RequestNurseBasicListEvent event = m_dRepeatOrderFlow.constructRequestNurseBasicListEvent();
		if (event == null)
		{
			TipsDialog.GetInstance().setMsg("event == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}
		NurseListHandler.GetInstance().requestNurseBasicListAction(event);
		return;

	}

	//07. nurse order check success action
	private void checkSuccessAction()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		//01. 现金支付
		if (activity.isCashPay())
		{
			activity.cashTipsDialog();
			return;
		}
		//02. 支付宝支付
		else if (activity.isAlipay())
		{
			requestNurseOrderAlipayAction();
			return;
		}
		else
		{
			TipsDialog.GetInstance().setMsg(activity.getString(R.string.error_tips_pay_aciton_invalid),
											activity
										   );
			TipsDialog.GetInstance().show();
			return;
		}

	}

	//08. 发送 nurse order alipay event
	public void requestNurseOrderAlipayAction()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;
		String payInfo = getPayInfo();

		RequestNurseOrderAlipayEvent reqNurseOrderAlipayEvent = new RequestNurseOrderAlipayEvent();
		reqNurseOrderAlipayEvent.setPayInfo(payInfo);
		reqNurseOrderAlipayEvent.setActivity(activity);

		NurseOrderListHandler.GetInstance().requestNurseOrderAlipayAction(reqNurseOrderAlipayEvent);

		return;

	}

	//09. 获取pay info
	private String getPayInfo()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		//01. 获取order info
		int orderID = m_dRepeatOrderFlow.getOrderID();
		int totalPrice = m_dRepeatOrderFlow.getTotalPrice();
		//测试
		String orderIDInfo = Util.GetNurseOrderInfoInNormal(String.valueOf(orderID));
		String orderInfo = Util.GetNurseOrder(orderIDInfo, "0.01");
		//02. 对订单做RSA 签名
		String sign = signByRSA(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			TipsDialog.GetInstance().setMsg(e.toString(), activity);
			TipsDialog.GetInstance().show();
			return null;
		}

		//03. 完整的符合支付宝参数规范的订单信息
		String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		return payInfo;
	}

	//10. 支付宝模块返回结果处理
	public void onEventMainThread(AnswerNurseOrderAlipayEvent event)
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		//01. 解析支付结果。
		String result = event.getResult();
		PayResult payResult = new PayResult(result);

		// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
		String resultInfo = payResult.getResult();

		String resultStatus = payResult.getResultStatus();

		// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
		if (TextUtils.equals(resultStatus, String.valueOf(Config.PayStatus.PAY_STATUS_SUCCESS.getId())))
		{
			//			RegisterDialog.GetInstance().setMsg(Config.PayStatus.PAY_STATUS_SUCCESS.getName(), this);
			//			RegisterDialog.GetInstance().show();
			//继续进行，支付成功。
			//03. 支付成功,
		}
		// 判断resultStatus 为非“9000”则代表可能支付失败
		else
		{
			// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
			if (TextUtils.equals(resultStatus, String.valueOf(Config.PayStatus.PAY_STATUS_PAY_RESULT_WAIT_CONFIRM.getId())))
			{
				TipsDialog.GetInstance().setMsg(Config.PayStatus.PAY_STATUS_PAY_RESULT_WAIT_CONFIRM.getName(),
												activity
											   );
				TipsDialog.GetInstance().show();
			}
			//系统繁忙，则仅仅提示一下，等待后续处理结果
			else if (TextUtils.equals(resultStatus, String.valueOf(Config.PayStatus.PAY_STATUS_PAY_RESULT_SYSTEM_BUSY.getId())))
			{
				TipsDialog.GetInstance().setMsg(Config.PayStatus.PAY_STATUS_PAY_RESULT_SYSTEM_BUSY.getName(),
												activity
											   );
				TipsDialog.GetInstance().show();
			}
			// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
			else
			{
				//支付失败，则允许继续进行支付。
				TipsDialog.GetInstance().setMsg(activity.getString(R.string.pay_status_failed),
												activity
											   );
				TipsDialog.GetInstance().show();
			}
		}

		//清楚数据
		payEndAction();
		return;

	}

	//11. 支付成功的操作
	public void payEndAction()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		//01. 清除预约陪护交易流程中的数据
		m_dRepeatOrderFlow.clearupAll();

		//02. 跳转到主页面。
		activity.startActivity(new Intent(activity, MainActivity.class));

		return;
	}

	//12. 取消支付的操作
	public void cancelPayAction()
	{
		NurseOrderPayInRepeatOrderActivity activity = (NurseOrderPayInRepeatOrderActivity)m_context;

		//01. 清除交易流程中的数据
		m_dRepeatOrderFlow.clearupAll();

		//02. 跳转到主页面。
		activity.startActivity(new Intent(activity, MainActivity.class));

		return;

	}

	/**
	 * alipay
	 */
	//get the sign type we use. 获取签名方式
	public String getSignType()
	{
		return "sign_type=\"RSA\"";
	}

	//对订单信息进行签名
	private String signByRSA(String orderInfo)
	{
		return UtilRSA.Sign(orderInfo, Config.RSA_PRIVATE);
	}

}
