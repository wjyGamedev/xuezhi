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
 * 2015/8/19		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.nurse_order_flow.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.module.frame.BaseActivity;
import com.module.util.timer.TimerTaskWrapper;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.data_module.nurse_order_list.data.DNurseOrder;
import com.taixinkanghu_client.work_flow.nurse_order_flow.msg_handler.NurseOrderMsgHandler;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import event.EventBus;


public class NurseOrderActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;    //title

	@Bind (R.id.order_option_rg)   RadioGroup  m_orderOptionRG   = null;
	@Bind (R.id.all_rbtn)          RadioButton m_allRBtn         = null;           //全部
	@Bind (R.id.wait_pay_rbtn)     RadioButton m_waitPayRBtn     = null;    //未支付
	@Bind (R.id.wait_service_rbtn) RadioButton m_waitServiceRBtn = null;    //已完成
	@Bind (R.id.order_info_lv)     ListView    m_orderInfoLV     = null;    //list列表显示区域

	private BottomCommon m_bottomCommon = null;

	//logical
	private NurseOrderMsgHandler m_nurseOrderMsgHandler = new NurseOrderMsgHandler(this);
	private ClickBottomCommon    m_clickBottomCommon    = new ClickBottomCommon();

	private ProgressDialog   m_waitProgressDialog = null;
	private TimerTaskWrapper m_waitTimerTask      = new TimerTaskWrapper();
	private TimerTaskHandler m_timerTaskHandler   = new TimerTaskHandler();


	//TODO:先屏蔽左右滑动
	//	private GestureDetector      m_gestureDetector      = null;

	//func btn
	public static final String FUNC_BTN_TAG_CANCEL_ORDER   = "func_btn_tag_cancel_order";
	public static final String FUNC_BTN_TAG_PAY_ORDER      = "func_btn_tag_pay_order";
	public static final String FUNC_BTN_TAG_COMMENT_ORDER  = "func_btn_tag_comment_order";
	public static final String FUNC_BTN_TAG_REPEAT_ORDER   = "func_btn_tag_repeat_order";
	public static final String FUNC_BTN_TAG_CHANGE_NURSE   = "func_btn_tag_change_nurse";
	public static final String FUNC_BTN_TAG_CANCEL_SERVICE = "func_btn_tag_cancel_service";
	public static final String FUNC_BTN_TAG_PAY_MORE       = "func_btn_tag_pay_more";


	//logical
	private OrdersAllAdapter         m_ordersAllAdapter         = null;
	private OrdersWaitPayAdapter     m_ordersWaitPayAdapter     = null;
	private OrdersWaitServiceAdapter m_ordersWaitServiceAdapter = null;

	private EventBus m_eventBus = EventBus.getDefault();

	private SimpleDateFormat m_simpleDateFormat = new SimpleDateFormat(DateConfig.PATTERN_DATE_MONTH_DAY);
	private DNurseOrder      m_nurseOrder       = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_order);
		ButterKnife.bind(this);

		init();
		initWaitAction();
	}

	@Override
	protected void onStart()
	{
		updateContent();
		super.onStart();
	}

	/**
	 * widget event
	 */
	@OnItemClick (R.id.order_info_lv)
	public void clickOrderItemRegion(AdapterView<?> parent, View view, int position, long id)
	{
		m_nurseOrderMsgHandler.go2NurseInfoAction((int)id);
	}

//	@OnCheckedChanged (R.id.order_option_rg)
//	public void clickTabRegion(RadioGroup group, int checkedId)
//	{
//		if (checkedId == m_allRBtn.getId())
//		{
//			updateAllContent();
//		}
//		else if (checkedId == m_waitPayRBtn.getId())
//		{
//			updateWaitPayContent();
//		}
//		else if (checkedId == m_waitServiceRBtn.getId())
//		{
//			updateWaitServiceyContent();
//		}
//	}

	@OnClick (R.id.all_rbtn)
	public void clickAllTab()
	{
		updateAllContent();
	}

	@OnClick (R.id.wait_pay_rbtn)
	public void clickWaitPayTab()
	{
		updateWaitPayContent();
	}

	@OnClick (R.id.wait_service_rbtn)
	public void clickServiceTab()
	{
		updateWaitServiceyContent();
	}

	/**
	 * override:func
	 */
	class ClickBottomCommon implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_nurseOrderMsgHandler.go2MainAction();
		}
	}

	class TimerTaskHandler implements TimerTaskWrapper.TimerTaskListener
	{
		@Override
		public void execAction()
		{
			m_waitProgressDialog.dismiss();
		}
	}

	public class HandleClickEventOnDialog_CancelOrderInWaitPay implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			//01. 确认，走取消支付流程。
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_nurseOrderMsgHandler.requestNurseOrderCancelInWaitPayAction();
			}
			//02. 取消,关闭弹出对话框。
			else if (which == DialogInterface.BUTTON_NEGATIVE)
			{
				dialog.dismiss();
			}
			return;
		}
	}

	/**
	 * inner: func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.header_title);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.bottom_title);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomCommon);

		m_allRBtn.setChecked(true);
//		m_gestureDetector = new GestureDetector(this, this);

		//02. logical
		//下面三个放在这里初始化，不放在data 区域，是因为里面用到了m_layoutInflater = LayoutInflater.from(m_context);，所以要保证m_context已经初始化完毕。
		m_ordersAllAdapter = new OrdersAllAdapter(this);
		m_ordersWaitPayAdapter = new OrdersWaitPayAdapter(this);
		m_ordersWaitServiceAdapter = new OrdersWaitServiceAdapter(this);;

		m_waitProgressDialog = new ProgressDialog(this);
		m_waitProgressDialog.setMessage(getString(R.string.wait_tips));
		m_waitTimerTask.setTimerTaskListener(m_timerTaskHandler);

	}

	private void initWaitAction()
	{
		m_waitProgressDialog.show();
		m_waitTimerTask.schedule(DataConfig.DELAY_TIME_MILLISENCENDS);
	}

	public void updateContent()
	{
		int id = m_orderOptionRG.getCheckedRadioButtonId();
		switchContentByTab(id);
	}

	private void switchContentByTab(int id)
	{
		if (id == R.id.all_rbtn)
		{
			updateAllContent();
		}
		else if (id == R.id.wait_pay_rbtn)
		{
			updateWaitPayContent();
		}
		else if (id == R.id.wait_service_rbtn)
		{
			updateWaitServiceyContent();
		}
		else
		{
			updateAllContent();
		}
		return;

	}

	private void updateAllContent()
	{
		m_orderInfoLV.setAdapter(m_ordersAllAdapter);
		m_ordersAllAdapter.notifyDataSetChanged();
	}

	private void updateWaitPayContent()
	{
		m_orderInfoLV.setAdapter(m_ordersWaitPayAdapter);
		m_ordersWaitPayAdapter.notifyDataSetChanged();
	}

	private void updateWaitServiceyContent()
	{
		m_orderInfoLV.setAdapter(m_ordersWaitServiceAdapter);
		m_ordersWaitServiceAdapter.notifyDataSetChanged();
	}

	/**
	 * logical
	 */
	public void stopWaitDialog()
	{
		m_waitProgressDialog.dismiss();
	}

	public NurseOrderMsgHandler getNurseOrderMsgHandler()
	{
		return m_nurseOrderMsgHandler;
	}

	public void popDialog_CancelOrderInWaitPay()
	{
		HandleClickEventOnDialog_CancelOrderInWaitPay cancelOrderListener = new HandleClickEventOnDialog_CancelOrderInWaitPay();
		TipsDialog.GetInstance().setMsg(getString(R.string.nurse_order_cancel_tips), this, cancelOrderListener, cancelOrderListener);
		TipsDialog.GetInstance().show();
		return;
	}
}
