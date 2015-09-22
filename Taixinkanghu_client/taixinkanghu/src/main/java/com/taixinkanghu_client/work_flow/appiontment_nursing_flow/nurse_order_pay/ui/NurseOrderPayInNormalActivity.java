package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.nurse_order_pay.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.nurse_order_pay.msg_handler.NurseOrderPayInNormalMsgHandler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/8/3.
 */
public class NurseOrderPayInNormalActivity extends BaseActivity
{
	//widget
	private                          HeaderCommon m_headerCommon     = null;
	@Bind (R.id.order_serial_num_tv) TextView     m_orderSerialNumTV = null;
	@Bind (R.id.price_tv)            TextView     m_priceTV          = null;
	@Bind (R.id.cash_region_ll)      LinearLayout m_cashRegionLL     = null;
	@Bind (R.id.cash_rbtn)           RadioButton  m_cashRBtn         = null;
	@Bind (R.id.alipay_region_ll)    LinearLayout m_alipayRegionLL   = null;
	@Bind (R.id.alipay_rbtn)         RadioButton  m_alipayRBtn       = null;
	@Bind (R.id.weixin_region_ll)    LinearLayout m_weixinRegionLL   = null;
	@Bind (R.id.weixin_rbtn)         RadioButton  m_weixinRBtn       = null;
	private                          BottomCommon m_bottomCommon     = null;

	private Button m_payBtn = null;

	//logical
	private NurseOrderPayInNormalMsgHandler  m_nurseOrderPayInNormalMsgHandler  = new NurseOrderPayInNormalMsgHandler(this);
	private ArrayList<RadioButton>           m_radioButtons                     = new ArrayList<>();
	private ClickHeaderCommon                m_clickHeaderCommon                = new ClickHeaderCommon();
	private ClickBottomCommon                m_clickBottomCommon                = new ClickBottomCommon();
	private HandleClickEventOnExitDialog     m_handleClickEventOnExitDialog     = new HandleClickEventOnExitDialog();
	private NurseInServiceDialog             m_nurseInServiceDialog             = new NurseInServiceDialog();
	private HandleClickEventOnTipsCashDialog m_handleClickEventOnTipsCashDialog = new HandleClickEventOnTipsCashDialog();
	private int                              m_selectedID                       = DataConfig.DEFAULT_VALUE;
	private String                           TIPS_CASH_SUCCESS                  = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_order_pay_in_normal);
		ButterKnife.bind(this);

		init();
		initUI();
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
			backAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	/**
	 * widget event
	 */
	@OnClick (R.id.cash_rbtn)
	public void selectCash(View v)
	{
		selectedRadionBtn(v.getId());
	}

	@OnClick (R.id.alipay_rbtn)
	public void selectAlipay(View v)
	{
		selectedRadionBtn(v.getId());
	}

	@OnClick (R.id.weixin_rbtn)
	public void selectWeixin(View v)
	{
		selectedRadionBtn(v.getId());
	}

	/**
	 * overrioe func
	 */
	public class ClickHeaderCommon implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			backAction();
		}
	}

	public class ClickBottomCommon implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			//01. 检测支付方式的有效性
			if (!isSelectedValid())
				return;

			//02. 发送nurse order check action
			m_nurseOrderPayInNormalMsgHandler.requestNurseOrderCheckAction();
			return;
		}
	}

	public class HandleClickEventOnExitDialog implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			//01. 确认，走取消支付流程。
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_nurseOrderPayInNormalMsgHandler.cancelPayAction();
			}
			//02. 取消,关闭弹出对话框。
			else if (which == DialogInterface.BUTTON_NEGATIVE)
			{
				dialog.dismiss();
			}
			return;
		}
	}

	public class NurseInServiceDialog implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_nurseOrderPayInNormalMsgHandler.checkFailedAction();
		}
	}


	public class HandleClickEventOnTipsCashDialog implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_nurseOrderPayInNormalMsgHandler.paySuccessAction();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.pay_page_title);
		m_headerCommon.getBackIBtn().setOnClickListener(m_clickHeaderCommon);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.determine_pay_title);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomCommon);

		m_radioButtons.add(m_cashRBtn);
		m_radioButtons.add(m_alipayRBtn);
		m_radioButtons.add(m_weixinRBtn);

		TIPS_CASH_SUCCESS = getString(R.string.tips_cash_success);
		return;
	}

	private void initUI()
	{
		m_weixinRegionLL.setVisibility(View.GONE);
		m_cashRegionLL.setVisibility(View.VISIBLE);
		m_alipayRegionLL.setVisibility(View.VISIBLE);
	}

	private void updateContent()
	{
		m_nurseOrderPayInNormalMsgHandler.updateContent();
		return;
	}

	public void backAction()
	{
		TipsDialog.GetInstance().setMsg(getString(R.string.cancel_title),
										this,
										m_handleClickEventOnExitDialog,
										m_handleClickEventOnExitDialog
									   );
		TipsDialog.GetInstance().show();
		return;
	}

	public void inServiceAction()
	{
		TipsDialog.GetInstance().setMsg(getString(R.string.error_tips_you_selected_nurse_in_service), this, m_nurseInServiceDialog);
		TipsDialog.GetInstance().show();
		return;
	}

	private void selectedRadionBtn(int selectedID)
	{
		for (RadioButton radioButton : m_radioButtons)
		{
			if (radioButton == null)
				continue;

			if (radioButton.getId() == selectedID)
			{
				radioButton.setChecked(true);
			}
			else
			{
				radioButton.setChecked(false);
			}

		}
		m_selectedID = selectedID;

	}

	public boolean isSelectedValid()
	{
		if (m_selectedID == DataConfig.DEFAULT_VALUE)
		{
			TipsDialog.GetInstance().setMsg(getString(R.string.error_tips_select_pay_option), this);
			TipsDialog.GetInstance().show();
			return false;
		}

		if (m_selectedID == R.id.cash_rbtn || m_selectedID == R.id.alipay_rbtn)
		{
			return true;
		}


		TipsDialog.GetInstance().setMsg(getString(R.string.error_tips_pay_aciton_invalid), this);
		TipsDialog.GetInstance().show();
		return false;
	}


	/**
	 * widget:get
	 */
	public TextView getOrderSerialNumTV()
	{
		return m_orderSerialNumTV;
	}

	public TextView getPriceTV()
	{
		return m_priceTV;
	}

	/**
	 * data:get
	 */
	public int getSelectedID()
	{
		return m_selectedID;
	}

	public boolean isCashPay()
	{
		return (m_selectedID == R.id.cash_rbtn);
	}

	public boolean isAlipay()
	{
		return (m_selectedID == R.id.alipay_rbtn);
	}

	public boolean isWeixinPay()
	{
		return (m_selectedID == R.id.weixin_rbtn);
	}

	/**
	 * action
	 */
	public void cashTipsDialog()
	{
		TipsDialog.GetInstance().setMsg(TIPS_CASH_SUCCESS, this, m_handleClickEventOnTipsCashDialog);
		TipsDialog.GetInstance().show();
		return;
	}

}
