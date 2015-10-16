package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.module.data.DGlobal;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.dialog.AsyncWaitDialog;
import com.module.widget.dialog.TipsDialog;
import com.module.widget.header.HeaderCommon;
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler.AssayDetectionMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/22.
 */
public class AssayDetectionActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;

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
	private AssayDetectionMsgHandler      m_assayDetectionMsgHandler      = new AssayDetectionMsgHandler(this);
	private ClickHistoryBtn               m_clickHistoryBtn               = new ClickHistoryBtn();
	private ClickBottomBtn                m_clickBottomBtn                = new ClickBottomBtn();
	private PopDialog_ErrorTips           m_popDialog_errorTips           = new PopDialog_ErrorTips();
	private PopDialog_SaveSuccessTips     m_popDialog_saveSuccessTips     = new PopDialog_SaveSuccessTips();
	private AsyncWaitDialog               m_asyncWaitDialog               = new AsyncWaitDialog();
	private HandleWaitDialogFinishedEvent m_handleWaitDialogFinishedEvent = new HandleWaitDialogFinishedEvent();


	private final String INFO_SAVE_SUCCESS = DGlobal.GetInstance().getAppContext().getString(R.string.assay_detection_add_success_tips);
	private final String INFO_MAIN_PAGE    = DGlobal.GetInstance().getAppContext().getString(R.string.assay_detection_main_page_content);
	private final String INFO_HISTORY_PAGE = DGlobal.GetInstance().getAppContext().getString(R.string.assay_detection_history_btn_text);

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assay_detection);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.assay_detection_page_title_text);
		m_headerCommon.getRightButton().setVisibility(View.VISIBLE);
		m_headerCommon.getRightButton().setText(R.string.assay_detection_history_btn_text);
		m_headerCommon.getRightButton().setOnClickListener(m_clickHistoryBtn);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);

		m_bottomCommon.getCommonBottomBtn().setText(R.string.assay_detection_save_content);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		m_asyncWaitDialog.init(this);
		m_asyncWaitDialog.setHandleWaitDialogFinishedEvent(m_handleWaitDialogFinishedEvent);

	}

	private void popNullDialog(EnumConfig.AssayDetectionType assayDetectionType)
	{
		String error = getString(R.string.assay_detection_error_tips_pleast_input);
		if (assayDetectionType != null)
		{
			error = error + assayDetectionType.getName();
		}
		TipsDialog.GetInstance().setMsg(error, this);
		TipsDialog.GetInstance().show();
		return;
	}

	private boolean checkInputValid(double inputValue, EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
		{
			return false;
		}

		double minValue = AssayDetectionConfig.getMinValue(assayDetectionType);
		double maxValue = AssayDetectionConfig.getMaxValue(assayDetectionType);
		if (inputValue < minValue || inputValue > maxValue)
		{
			String error01 = getString(R.string.assay_detection_error_tips_invalid_input_01);
			String error02 = getString(R.string.assay_detection_error_tips_invalid_input_02);
			String error03 = getString(R.string.assay_detection_error_tips_repeat_input);
			String min = String.valueOf(minValue);
			String max = String.valueOf(maxValue);
			error01 = error01 + assayDetectionType.getName() + error02 + "[" + min + "~" + max + "]" + error03;
			TipsDialog.GetInstance().setMsg(error01, this);
			TipsDialog.GetInstance().show();

			return false;
		}

		return true;
	}

	private boolean check()
	{
		//TODO:添加约束条件
		String error = "";
		String repeatInput = getString(R.string.assay_detection_error_tips_repeat_input);
		try
		{
			//血脂
			//甘油三脂
			String tmpTgValue = getTgET().getText().toString();
			if (TextUtils.isEmpty(tmpTgValue))
			{
				popNullDialog(EnumConfig.AssayDetectionType.TG);
				return false;
			}
			double tgValue = Double.valueOf(tmpTgValue);
			if (!checkInputValid(tgValue, EnumConfig.AssayDetectionType.TG))
				return false;

			String tmpTchoValue = getTchoET().getText().toString();
			if (TextUtils.isEmpty(tmpTgValue))
			{
				popNullDialog(EnumConfig.AssayDetectionType.TCHO);
				return false;
			}
			double tchoValue = Double.valueOf(tmpTchoValue);

			String tmpLolcValue = getLolcET().getText().toString();
			double lolcValue = Double.valueOf(tmpLolcValue);

			String tmpHdlcValue = getHdlcET().getText().toString();
			double hdlcValue = Double.valueOf(tmpHdlcValue);


			String tmpAtlValue = getAtlET().getText().toString();
			double atlValue = Double.valueOf(tmpAtlValue);

			String tmpAstValue = getAstET().getText().toString();
			double astValue = Double.valueOf(tmpAstValue);

			String tmpCkValue = getCkET().getText().toString();
			double ckValue = Double.valueOf(tmpCkValue);

			String tmpGlucValue = getGluET().getText().toString();
			double glucValue = Double.valueOf(tmpGlucValue);

			String tmpHba1cValue = getHba1cET().getText().toString();
			double hba1cValue = Double.valueOf(tmpHba1cValue);

			String tmpScrValue = getScrET().getText().toString();
			double srcValue = Double.valueOf(tmpScrValue);

		}
		catch (NumberFormatException e)
		{
			popErrorDialog(e.toString());
			return false;
		}

		return true;
	}

	/**
	 * overrider func
	 */
	class ClickHistoryBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_assayDetectionMsgHandler.go2AssayDetectionHistoryPage();
		}
	}

	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (!check())
				return;

			m_assayDetectionMsgHandler.requestAddAssayDetectionInfoAction();
		}
	}

	class PopDialog_ErrorTips implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			m_assayDetectionMsgHandler.go2MainPage();
			return;
		}
	}

	class PopDialog_SaveSuccessTips implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			//01. 跳到主页面
			if (which == DialogInterface.BUTTON_POSITIVE)
			{
				m_assayDetectionMsgHandler.go2MainPage();
			}
			//02. 跳到历史页面
			else
			{
				m_assayDetectionMsgHandler.go2AssayDetectionHistoryPage();
			}
			return;
		}
	}

	class HandleWaitDialogFinishedEvent implements AsyncWaitDialog.HandleWaitDialogFinishedEvent
	{
		public void OnWaitDialogFinished()
		{
			//01. 关闭等待框
			m_asyncWaitDialog.dismiss();
		}
	}

	/**
	 * widget:get
	 */
	public EditText getTgET()
	{
		return m_tgET;
	}

	public EditText getTchoET()
	{
		return m_tchoET;
	}

	public EditText getLolcET()
	{
		return m_lolcET;
	}

	public EditText getHdlcET()
	{
		return m_hdlcET;
	}

	public EditText getAtlET()
	{
		return m_atlET;
	}

	public EditText getAstET()
	{
		return m_astET;
	}

	public EditText getCkET()
	{
		return m_ckET;
	}

	public EditText getGluET()
	{
		return m_gluET;
	}

	public EditText getHba1cET()
	{
		return m_hba1cET;
	}

	public EditText getScrET()
	{
		return m_scrET;
	}

	/**
	 * logical
	 */
	public void popErrorDialog(String error)
	{
		TipsDialog.GetInstance().setMsg(error, this, m_popDialog_errorTips);
		TipsDialog.GetInstance().show();
		return;
	}

	public void popSaveSuccessDialog()
	{
		AlertDialog.Builder builder = TipsDialog.GetInstance().setMsg(INFO_SAVE_SUCCESS,
																	  this,
																	  INFO_MAIN_PAGE,
																	  m_popDialog_saveSuccessTips,
																	  INFO_HISTORY_PAGE,
																	  m_popDialog_saveSuccessTips
																	 );
		if (builder != null)
		{
			builder.setCancelable(false);
		}
		TipsDialog.GetInstance().show();

		return;

	}


	public void dismissWaitDialog()
	{
		m_asyncWaitDialog.dismiss();
	}
}