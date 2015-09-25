/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/1		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.register_page.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.module.data.DGlobal;
import com.module.frame.BaseActivity;
import com.module.util.logcal.LogicalUtil;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.third.part.sms.DSmsAutho;
import com.third.part.sms.SmsAutho;
import com.third.part.sms.SmsConfig;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.register_account.msg_handler.RequestRegisterEvent;
import com.xuezhi_client.work_flow.register_page.msg_handler.RegisterActivityMsg;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity
{
	//title
	private HeaderCommon m_headerCommon = null;//new HeaderCommon(this);

	@Bind (R.id.phone_num_et)     EditText m_phoneNumTV      = null;    //手机号
	@Bind (R.id.verification_btn) Button   m_verificationBtn = null;    //点击，获取验证码
	@Bind (R.id.auth_code_et)     TextView m_authTV          = null;    //验证码输入区域
	@Bind (R.id.register_btn)     Button   m_registerBtn     = null;    //注册按钮

	//logical
	private final String ERR_INFO_INVALID_PHONE            = DGlobal.GetInstance().getAppContext().getString(R.string.err_info_invalid_phone);
	private final String ERR_INFO_INVALID_COUNTRY_ZIP_CODE = DGlobal.GetInstance().getAppContext().getString(R.string.err_info_invalid_country_zip_code);
	private final String INFO_SECOND                       = DGlobal.GetInstance().getAppContext().getString(R.string.rf_content_second);
	private final String INFO_VERIFICATION_CONTEXT         = DGlobal.GetInstance().getAppContext().getString(R.string.rf_verification_context);

	private RegisterActivityMsg m_registerActivityMsg = new RegisterActivityMsg(this);
	private TimeCount           m_timeCount          = new TimeCount(DataConfig.REGISTER_WAITTING_TIME, DataConfig.REGISTER_DELTA_TIME);
	private String              m_phoneNum           = null;
	private String              m_CountryZipCode     = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ButterKnife.bind(this);

		init();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	protected void onDestroy()
	{
		ButterKnife.unbind(this);
		super.onDestroy();
	}

	@OnEditorAction (R.id.phone_num_et)
	public boolean hideSoftInput(TextView v, int actionId, KeyEvent event)
	{
		if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE)
		{
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
		return true;
	}

	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.rf_title);

		m_verificationBtn.setText(INFO_VERIFICATION_CONTEXT);

		m_verificationBtn.setEnabled(true);

		m_registerActivityMsg.initSmsAutho(this);
		m_registerActivityMsg.requestSupportedCountriesAction();

	}

	private void initPhoneNum()
	{
		String phoneNum = m_phoneNumTV.getText().toString().trim();
		if (TextUtils.isEmpty(m_phoneNum) == true || m_phoneNum.equals(phoneNum) == false)
		{
			m_phoneNum = phoneNum;
		}
	}

	private boolean initCountryZipCode()
	{
		if (TextUtils.isEmpty(m_CountryZipCode) == false)
			return true;

		String strMcc       = DGlobal.GetInstance().getMCC();
		String strCountry[] = null;
		if (!TextUtils.isEmpty(strMcc))
		{
			strCountry = SMSSDK.getCountryByMCC(strMcc);
		}
		else
		{
			strCountry = SMSSDK.getCountry(SmsConfig.DEFAULT_COUNTRY_ID);
		}

		if (strCountry == null)
		{
			return false;
		}

		m_CountryZipCode = strCountry[1];
		HashMap<String, String> coutryCodeRuleMaps = DSmsAutho.GetInstance().getCountryCodeMaps();
		if (coutryCodeRuleMaps == null)
		{
			return false;
		}

		String strPhoneRule = coutryCodeRuleMaps.get(m_CountryZipCode);
		if (strPhoneRule == null)
		{
			return false;
		}

		Pattern pattern = Pattern.compile(strPhoneRule);
		Matcher matcher = pattern.matcher(m_phoneNum);
		if (matcher.matches() == false)
		{
			TipsDialog.GetInstance().setMsg(ERR_INFO_INVALID_COUNTRY_ZIP_CODE, this);
			TipsDialog.GetInstance().show();
			return false;
		}

		return true;
	}

	class TimeCount extends CountDownTimer
	{
		/**
		 * @param millisInFuture The number of millis in the future from the call
		 * to {@link #start()} until the countdown is done and {@link #onFinish()}
		 * is called.
		 * @param countDownInterval The interval along the way to receive
		 * {@link #onTick(long)} callbacks.
		 */
		public TimeCount(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			m_verificationBtn.setEnabled(false);
			m_verificationBtn.setText( millisUntilFinished/1000 + INFO_SECOND);
		}

		@Override
		public void onFinish()
		{
			m_verificationBtn.setText(INFO_VERIFICATION_CONTEXT);
			m_verificationBtn.setEnabled(true);
		}
	}

	/**
	 * action
	 */
	@OnClick (R.id.verification_btn)
	public void handleVerificationAction()
	{
		//01. 初始化手机号
		initPhoneNum();

		//02. 判断手机号码有效性
		if (LogicalUtil.isMobileNumValid(m_phoneNum) == false)
		{
			TipsDialog.GetInstance().setMsg(ERR_INFO_INVALID_PHONE, this);
			TipsDialog.GetInstance().show();
			return;
		}

		//03. 获取当前国家,并查看是否支持
		if (!initCountryZipCode())
			return;

		//03. 发送手机验证
		SmsAutho.GetInstance().getVerificationCode(m_CountryZipCode, m_phoneNum);

		//04.开始倒计时
		startCountingDownAcstion();
	}

	private void startCountingDownAcstion()
	{
		//01. 开始计时
		m_timeCount.start();
		//02. 关闭点击按钮
		m_verificationBtn.setEnabled(false);
	}

	@OnClick (R.id.register_btn)
	public void registerAction()
	{
		//01. 提交验证码
		String authCode = m_authTV.getText().toString().trim();
		if (TextUtils.isEmpty(authCode))
		{
			TipsDialog.GetInstance().setMsg(getString(R.string.err_info_verification_code_is_empty), this);
			TipsDialog.GetInstance().show();
			return;
		}

		initPhoneNum();

		if (!initCountryZipCode())
			return;

		RequestRegisterEvent reqRegisterEvent = new RequestRegisterEvent();
		reqRegisterEvent.init(m_CountryZipCode, m_phoneNum, authCode);
		m_registerActivityMsg.requestRegisterAction(reqRegisterEvent);

	}

}