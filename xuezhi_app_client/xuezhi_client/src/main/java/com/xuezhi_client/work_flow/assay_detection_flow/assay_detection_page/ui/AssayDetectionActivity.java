package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.module.data.DGlobal;
import com.module.frame.BaseActivity;
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
import butterknife.OnFocusChange;

/**
 * Created by Administrator on 2015/9/22.
 */
public class AssayDetectionActivity extends BaseActivity
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

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assay_detection);
		ButterKnife.bind(this);

		init();
	}

	/**
	 * widget event
	 */
	@OnFocusChange (R.id.tg_et)
	public void leaveTgETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkTg();
		return;
	}

	@OnFocusChange (R.id.tcho_et)
	public void leaveTchoETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkTcho();
		return;
	}

	@OnFocusChange (R.id.lolc_et)
	public void leaveLolcETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkLolc();
		return;
	}

	@OnFocusChange (R.id.hdlc_et)
	public void leaveHdlcETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkHdlc();
		return;
	}

	//生化
	@OnFocusChange (R.id.atl_et)
	public void leaveAtlETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkAtl();
		return;
	}

	@OnFocusChange (R.id.ast_et)
	public void leaveAstETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkAst();
		return;
	}

	@OnFocusChange (R.id.ck_et)
	public void leaveCkETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkCk();
		return;
	}

	@OnFocusChange (R.id.glu_et)
	public void leaveGluETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkGlu();
		return;
	}

	@OnFocusChange (R.id.hba1c_et)
	public void leaveHba11cETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkHba1c();
		return;
	}

	@OnFocusChange (R.id.scr_et)
	public void leaveScrETEvent(View v, boolean hasFocus)
	{
		if (hasFocus)
			return;

		checkScr();
		return;
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
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getSupportFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.assay_detection_page_title_text);
		m_headerCommon.getRightButton().setVisibility(View.VISIBLE);
		m_headerCommon.getRightButton().setText(R.string.assay_detection_history_btn_text);
		m_headerCommon.getRightButton().setOnClickListener(m_clickHistoryBtn);

		m_bottomCommon = (BottomCommon)getSupportFragmentManager().findFragmentById(R.id.common_bottom_fragment);

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

	private boolean checkTg()
	{
		String tmpTgValue = getTgET().getText().toString();
		if (TextUtils.isEmpty(tmpTgValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.TG);
			return false;
		}
		double tgValue = Double.valueOf(tmpTgValue);
		if (!checkInputValid(tgValue, EnumConfig.AssayDetectionType.TG))
			return false;

		return true;
	}

	private boolean checkTcho()
	{
		String tmpTchoValue = getTchoET().getText().toString();
		if (TextUtils.isEmpty(tmpTchoValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.TCHO);
			return false;
		}
		double tchoValue = Double.valueOf(tmpTchoValue);
		if (!checkInputValid(tchoValue, EnumConfig.AssayDetectionType.TCHO))
			return false;

		return true;
	}

	private boolean checkLolc()
	{
		String tmpLolcValue = getLolcET().getText().toString();
		if (TextUtils.isEmpty(tmpLolcValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.LOLC);
			return false;
		}
		double lolcValue = Double.valueOf(tmpLolcValue);
		if (!checkInputValid(lolcValue, EnumConfig.AssayDetectionType.LOLC))
			return false;

		return true;
	}

	private boolean checkHdlc()
	{
		String tmpHdlcValue = getHdlcET().getText().toString();
		if (TextUtils.isEmpty(tmpHdlcValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.HDLC);
			return false;
		}
		double hdlcValue = Double.valueOf(tmpHdlcValue);
		if (!checkInputValid(hdlcValue, EnumConfig.AssayDetectionType.HDLC))
			return false;

		return true;
	}

	private boolean checkAtl()
	{
		String tmpAtlValue = getAtlET().getText().toString();
		if (TextUtils.isEmpty(tmpAtlValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.ATL);
			return false;
		}
		double atlValue = Double.valueOf(tmpAtlValue);
		if (!checkInputValid(atlValue, EnumConfig.AssayDetectionType.ATL))
			return false;

		return true;
	}

	private boolean checkAst()
	{
		String tmpAstValue = getAstET().getText().toString();
		if (TextUtils.isEmpty(tmpAstValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.AST);
			return false;
		}
		double astValue = Double.valueOf(tmpAstValue);
		if (!checkInputValid(astValue, EnumConfig.AssayDetectionType.AST))
			return false;

		return true;
	}

	private boolean checkCk()
	{
		String tmpCkValue = getCkET().getText().toString();
		if (TextUtils.isEmpty(tmpCkValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.CK);
			return false;
		}
		double ckValue = Double.valueOf(tmpCkValue);
		if (!checkInputValid(ckValue, EnumConfig.AssayDetectionType.CK))
			return false;

		return true;
	}

	private boolean checkGlu()
	{
		String tmpGluValue = getGluET().getText().toString();
		if (TextUtils.isEmpty(tmpGluValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.GLU);
			return false;
		}
		double gluValue = Double.valueOf(tmpGluValue);
		if (!checkInputValid(gluValue, EnumConfig.AssayDetectionType.GLU))
			return false;

		return true;
	}

	private boolean checkHba1c()
	{
		String tmpHba1cValue = getHba1cET().getText().toString();
		if (TextUtils.isEmpty(tmpHba1cValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.HBA1C);
			return false;
		}
		double hba1cValue = Double.valueOf(tmpHba1cValue);
		if (!checkInputValid(hba1cValue, EnumConfig.AssayDetectionType.HBA1C))
			return false;

		return true;
	}

	private boolean checkScr()
	{
		String tmpScrValue = getScrET().getText().toString();
		if (TextUtils.isEmpty(tmpScrValue))
		{
			popNullDialog(EnumConfig.AssayDetectionType.SCR);
			return false;
		}
		double srcValue = Double.valueOf(tmpScrValue);
		if (!checkInputValid(srcValue, EnumConfig.AssayDetectionType.SCR))
			return false;

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
			if (!checkTg())
				return false;

			if (!checkTcho())
				return false;

			if (!checkLolc())
				return false;

			if (!checkHdlc())
				return false;

			//生化
			if (!checkAtl())
				return false;

			if (!checkAst())
				return false;

			if (!checkCk())
				return false;

			if (!checkGlu())
				return false;

			if (!checkHba1c())
				return false;

			if (!checkScr())
				return false;

		}
		catch (NumberFormatException e)
		{
			popErrorDialog(e.toString());
			return false;
		}

		return true;
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