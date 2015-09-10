/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.third.party.alipay.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/21		WangJY		1.0.0		create
 */

package com.third.part.alipay;

import com.module.net.NetConfig;

public class Util
{
	public static String GetNurseOrderInfoInNormal(String orderID)
	{
		//01. 正常支付
		return "rid="+ orderID + "taixintype="+"order";
	}

	public static String GetNurseOrderInfoInPayMore(String orderID)
	{
		//01. 补差价
		return "rid="+ orderID + "taixintype="+"addon";
	}

	public static String GetNurseOrder(String nurseOrderID, String price)
	{
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + Config.PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Config.TARGET_RECEIVE_ACCOUNT + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + nurseOrderID + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + "nurse_name" + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + "nurse_info" + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + NetConfig.s_nurserOrderAlipayServerNoticePageURL + "\"";//"http://notify.msp.hk/notify.htm" + "\"";
//		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}




}
