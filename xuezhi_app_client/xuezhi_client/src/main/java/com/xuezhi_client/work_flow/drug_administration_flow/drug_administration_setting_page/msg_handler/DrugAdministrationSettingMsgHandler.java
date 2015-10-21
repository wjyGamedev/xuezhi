package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.msg_handler;

import android.widget.Toast;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalStock;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAddMedicalStockEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerSetMedicalStockDoseEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicalStockListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestSetMedicalStockDoseEvent;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.DrugAdministrationSettingActivity;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationSettingMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public DrugAdministrationSettingMsgHandler(DrugAdministrationSettingActivity activity)
	{
		super(activity);
	}

	public void saveDrugStockSettingInfo()
	{
		DrugAdministrationSettingActivity drugAdministrationSettingActivity = (DrugAdministrationSettingActivity)m_context;
		int                  drugID            = drugAdministrationSettingActivity.getDrugID();
		String               userID            = DAccount.GetInstance().getId();
		String               drugStockNum      = String.valueOf(drugAdministrationSettingActivity.getDrugStockNum());
		String               drugAlertNum      = String.valueOf(drugAdministrationSettingActivity.getDrugAlertNum());
		boolean              drugReminderState = drugAdministrationSettingActivity.isDrugReminderState();
		Calendar             m_addCalendar     = Calendar.getInstance();
		Calendar             m_warningCalendar = Calendar.getInstance();
//		EnumConfig.MedicalUnit m_medicalUnit = get;

		//数据验证
		if (drugID == 0)
		{
			TipsDialog.GetInstance().setMsg(m_context.getResources().getString(R.string.drug_administration_setting_click_hint_1_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (userID.isEmpty())
		{
			TipsDialog.GetInstance().setMsg(m_context.getResources().getString(R.string.drug_administration_setting_click_hint_2_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (drugStockNum == null)
		{
			TipsDialog.GetInstance().setMsg(m_context.getResources().getString(R.string.drug_administration_setting_click_hint_3_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (drugAlertNum == null)
		{
			TipsDialog.GetInstance().setMsg(m_context.getResources().getString(R.string.drug_administration_setting_click_hint_4_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (m_addCalendar == null)
		{
			TipsDialog.GetInstance().setMsg("m_addCalendar == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (m_warningCalendar == null)
		{
			TipsDialog.GetInstance().setMsg("m_warningCalendar == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		//		if (drugStockNum <= drugAlertNum)
		//		{
		//			TipsDialog.GetInstance().setMsg("添加数量小于报警数量");
		//			TipsDialog.GetInstance().show();
		//			return;
		//		}

		RequestSetMedicalStockDoseEvent requestAddMedicalStockAction = new RequestSetMedicalStockDoseEvent();

		requestAddMedicalStockAction.setUID(userID);
		requestAddMedicalStockAction.setMID(Integer.toString(drugID));
		requestAddMedicalStockAction.setRemainNum(Double.valueOf(drugStockNum));
		requestAddMedicalStockAction.setWaringNum(Double.valueOf(drugAlertNum));
		requestAddMedicalStockAction.setStatus(drugReminderState);
		DBusinessMsgHandler.GetInstance().requestSetMedicalStockDoseAction(requestAddMedicalStockAction);

		return;

	}

	//保存成功，获取药品库存列表
	public void onEventMainThread(AnswerAddMedicalStockEvent event)
	{
		RequestMedicalStockListEvent event_new = new RequestMedicalStockListEvent();
		event_new.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestMedicalStockListAction(event_new);
	}

	//获取药品库存列表成功，关闭页面，弹出提示
	public void onEventMainThread(AnswerSetMedicalStockDoseEvent event)
	{
		DrugAdministrationSettingActivity drugAdministrationSettingActivity = (DrugAdministrationSettingActivity)m_context;

		//提示保存成功
		Toast.makeText(drugAdministrationSettingActivity,drugAdministrationSettingActivity.getResources().getString(R.string.drug_administration_setting_click_setting_complete_text), Toast.LENGTH_SHORT).show();

		//关闭添加页面
		drugAdministrationSettingActivity.finish();
	}



	public void DrugSettingPagefillingContent()
	{
		//获取选取药品库存ID
		DrugAdministrationSettingActivity drugAdministrationSettingActivity = (DrugAdministrationSettingActivity)m_context;

		if (drugAdministrationSettingActivity == null)
		{
			TipsDialog.GetInstance().setMsg("drugAdministrationSettingActivity == null", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		String selectMedicalStockID = drugAdministrationSettingActivity.getSelectMedicalStockID();

		//获取药品库存信息
		DMedicalStock drugStockInfo = DBusinessData.GetInstance().getMedicalStockList().getMedicalByID(Integer.valueOf
																											   (selectMedicalStockID));

		if (drugStockInfo == null)
		{
			TipsDialog.GetInstance().setMsg("drugStockInfo == null![selectMedicalStockID:=" + selectMedicalStockID + "]");
			TipsDialog.GetInstance().show();
			return;
		}

		SimpleDateFormat sdf             			= new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
		String           addCalender     			= sdf.format(drugStockInfo.getAddCalendar().getTime());
		String           warningCalender 			= sdf.format(drugStockInfo.getWarningCalendar().getTime());
		String           drugName        			= DBusinessData.GetInstance().getMedicalList().getMedicalByID(drugStockInfo.getMID()).getName();
		String			isMedicalReminderStateText 	= null;
		if(drugStockInfo.isMedicalReminderState())
		{
			isMedicalReminderStateText = m_context.getResources().getString(R.string.drug_administration_setting_alert_state_open_text);
		}
		else
		{
			isMedicalReminderStateText = m_context.getResources().getString(R.string.drug_administration_setting_alert_state_close_text);
		}

		drugAdministrationSettingActivity.getDrugNameTV().setText(drugName);
		drugAdministrationSettingActivity.getDrugStockNumET().setText(String.valueOf(drugStockInfo.getRemianNum()));
		drugAdministrationSettingActivity.getDrugAlertNumET().setText(String.valueOf(drugStockInfo.getWaringNum()));
		drugAdministrationSettingActivity.getDrugAddDateNumTV().setText(addCalender);
		drugAdministrationSettingActivity.getDrugRunOutDateNumTV().setText(warningCalender);
		drugAdministrationSettingActivity.getDrugReminderStateCB().setChecked(drugStockInfo.isMedicalReminderState());
		drugAdministrationSettingActivity.getDrugReminderStateCB().setText(isMedicalReminderStateText);

		return;
	}

}
