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


import com.module.data.DGlobal;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;

public class Config
{
	//商户PID
	public static final String PARTNER = "2088021397082251";

	//商户收款账号
	public static final String TARGET_RECEIVE_ACCOUNT = "taixinkanghu2015@163.com";

	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL0UUmZFoc7gjawOQ7JerkqyY6eIusfdfiK1/wDAAT5Js6s0cbbq8E8yPRJJxirCL0Td3Z15Zss1u+fH6v23Y+0fpDpa0dxJe9Ule+/5m8N96FKyuQNtjYuzbDRgG+g2SaYNAFXR60atACWIY1OCvjLKj8o9fFBPpuRABy8F6s5tAgMBAAECgYEAk3ZN7UVRNK8czpsxYONGuyr1XavdQBMWVKvr/QHB9no0jcmRBj+ku59K0gCHdMGNbAA83pvX95QowFPCia2FWAqsxoCKK88mTMb01jia76Bd9KQy4gkUq74Y3Q14uK4bOxidrP/jWgwa1r6W9rZet6fTzISYcUZ4cfELZdJR/gECQQDeGkPPil0zk4Lo5IPuwC8URIhSpyEaj7YSD9rq3lWzfHfJfxSKVN1Y8YLyJQU49kHXf3y4iwWzyiKOMWWRkN1RAkEA2e/P7l3AwHhZYfe4vz3ymRfs0t6a0kv8FSL+wBmuC0vpm0B5v7ZWLZtNeTR2XtR+Ho+o9FlmqZjq/4q+XenoXQJBAJYHi/Lv9lFWvcFwS6bFb4fzW1rpxrTYixbcWvb4xU9/LrOQAmIHPZZNXjUPyN1Xi9Z0Kd3HG0z9qCYBPCj+gGECQGAe5h2i2gXJztpnsOMgRmSBaeFjbvN7sfX8llFRwjKXS7Q+zrPgjsfUrGnd2qtemzWNdR44ZBA/Mr58ihPPu70CQCof/BsoDFZiN91+0bP12umugWYFYT3ddrJYTPSzEver0FX5kGaQxjOgXycI/2ySTDUJZ6baoMUlqmWgNNBmedY=";

	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private static final String INFO_PAY_STATUS_SUCCESS = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.pay_status_success);
	private static final String INFO_PAY_STATUS_PAY_RESULT_WAIT_CONFIRM = DGlobal.GetInstance().getAppContext().getResources().getString(R.string.pay_status_pay_result_wait_confirm);

	//返回status
	public enum PayStatus
	{
		PAY_STATUS_SUCCESS(INFO_PAY_STATUS_SUCCESS, 9000),
		PAY_STATUS_PAY_RESULT_WAIT_CONFIRM(INFO_PAY_STATUS_PAY_RESULT_WAIT_CONFIRM, 8000);

		private String m_name = null;
		private int    m_id   = 0;

		private PayStatus(String name, int id)
		{
			m_name = name;
			m_id = id;
		}

		public String getName()
		{
			return m_name;
		}

		public void setName(String name)
		{
			m_name = name;
		}

		public int getId()
		{
			return m_id;
		}

		public void setId(int id)
		{
			m_id = id;
		}
	}


}
