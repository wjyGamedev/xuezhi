/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_item_info_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/21		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_item_info_page.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetection;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_item_info_page.msg_handler.AssayDetectionHistoryItemInfoMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;

public class AssayDetectionHistoryItemInfoActivity extends BaseActivity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	//记录时间
	@Bind (R.id.record_time_tv) TextView m_recordTimeTV = null;    //甘油三酯

	//血脂
	@Bind (R.id.tg_et)    EditText m_tgET    = null;    //甘油三酯
	@Bind (R.id.tcho_et)  EditText m_tchoET  = null;    //总胆固醇
	@Bind (R.id.lolc_et)  EditText m_lolcET  = null;    //低密度脂蛋白胆固醇
	@Bind (R.id.hdlc_et)  EditText m_hdlcET  = null;    //高密度脂蛋白胆固醇
	//生化
	@Bind (R.id.atl_et)   EditText m_atlET   = null;    //谷丙转氨酶
	@Bind (R.id.ast_et)   EditText m_astET   = null;    //谷草转氨酶
	@Bind (R.id.ck_et)    EditText m_ckET    = null;    //肌酸激酶
	@Bind (R.id.glu_et)   EditText m_gluET   = null;    //空腹血糖
	@Bind (R.id.hba1c_et) EditText m_hba1cET = null;    //糖化血红蛋白
	@Bind (R.id.scr_et)   EditText m_scrET   = null;        //肌酐

	private BottomCommon m_bottomCommon = null;

	//logical
	private AssayDetectionHistoryItemInfoMsgHandler m_assayDetectionHistoryItemInfoMsgHandler = new
			AssayDetectionHistoryItemInfoMsgHandler(
			this
	);
	private BackEvent                               m_backEvent                               = new BackEvent();

	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	private DAssayDetection m_assayDetection = null;

	@Override
	protected void onStart()
	{
		super.onStart();
		updateContent();
	}

	@Override
	public BaseActivity onCreateAction()
	{
		setContentView(R.layout.activity_assay_detection_history_item);
		return this;
	}

	@Override
	public void onAfterCreateAction()
	{
		init();
	}

	@Override
	public void onDestoryAction()
	{

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			m_assayDetectionHistoryItemInfoMsgHandler.backAction();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	/**
	 * overrider func
	 */
	class BackEvent implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_assayDetectionHistoryItemInfoMsgHandler.backAction();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.assay_detection_history_item_title_text);
		m_headerCommon.getBackIBtn().setOnClickListener(m_backEvent);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.assay_detection_history_confirm);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_backEvent);

		Intent intent = getIntent();
		int    itemID = intent.getIntExtra(AssayDetectionConfig.SELECTED_ITEM_ID, DataConfig.DEFAULT_VALUE);
		m_assayDetection = DBusinessData.GetInstance().getAssayDetectionList().getAssayDetectionByID(itemID);
		if (m_assayDetection == null)
		{
			TipsDialog.GetInstance().setMsg("m_assayDetection == null!itemID is invalid![itemID:=" + itemID + "]", this);
			TipsDialog.GetInstance().show();
			return;
		}
	}

	private void updateContent()
	{
		if (m_assayDetection == null)
			return;

		//记录时间
		Calendar calendar = m_assayDetection.getRecordCalendar();
		String displayTime = m_allSDF.format(calendar.getTime());
		m_recordTimeTV.setText(displayTime);

		//血脂
		double tgValue = m_assayDetection.getTgValue();
		m_tgET.setText(String.valueOf(tgValue));

		double tchoValue = m_assayDetection.getTchoValue();
		m_tchoET.setText(String.valueOf(tchoValue));

		double lolcValue = m_assayDetection.getLolcValue();
		m_lolcET.setText(String.valueOf(lolcValue));

		double hdlcValue = m_assayDetection.getHdlcValue();
		m_hdlcET.setText(String.valueOf(hdlcValue));

		//生化
		double atlValue = m_assayDetection.getAtlValue();
		m_atlET.setText(String.valueOf(atlValue));

		double astValue = m_assayDetection.getAstValue();
		m_astET.setText(String.valueOf(astValue));

		double ckValue = m_assayDetection.getCkValue();
		m_ckET.setText(String.valueOf(ckValue));

		double gluValue = m_assayDetection.getGluValue();
		m_gluET.setText(String.valueOf(gluValue));

		double hba1cValue = m_assayDetection.getHba1cValue();
		m_hba1cET.setText(String.valueOf(hba1cValue));

		double scrValue = m_assayDetection.getScrValue();
		m_scrET.setText(String.valueOf(scrValue));

		return;
	}
}
