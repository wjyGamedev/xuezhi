package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.msg_handler;

import android.content.DialogInterface;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineBox;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineBoxSetEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicineBoxSetEvent;
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
		int                               drugID                            = drugAdministrationSettingActivity.getDrugID();
		String                            selectMedicalStockID              = drugAdministrationSettingActivity.getSelectMedicalStockID();
		String                            userID                            = DAccount.GetInstance().getId();
		String                            newDrugStockNum                   = drugAdministrationSettingActivity.getNewDrugStockNum();
		String                            newDrugAlertNum                   = drugAdministrationSettingActivity.getNewDrugAlertNum();
		boolean                           newDrugReminderState              = drugAdministrationSettingActivity.isNewDrugReminderState();
		Calendar                          m_addCalendar                     = Calendar.getInstance();
		Calendar                          m_warningCalendar                 = Calendar.getInstance();

		//获取老数据
		double  oldDrugStockNum      = drugAdministrationSettingActivity.getOldDrugStockNum();
		double  oldDrugAlertNum      = drugAdministrationSettingActivity.getOldDrugAlertNum();
		boolean oldDrugReminderState = drugAdministrationSettingActivity.isOldDrugReminderState();

		//数据验证
		if (drugID == -1)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_administration_setting_click_hint_1_text), m_context
										   );
			TipsDialog.GetInstance().show();
			return;
		}

		if (userID.isEmpty())
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_administration_setting_click_hint_2_text), m_context
										   );
			TipsDialog.GetInstance().show();
			return;
		}

		if (newDrugStockNum.equals("") || newDrugStockNum.equals("0") || newDrugStockNum == null)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_administration_setting_click_hint_3_text), m_context
										   );
			TipsDialog.GetInstance().show();
			return;
		}

		if (newDrugAlertNum.equals("") || newDrugAlertNum.equals("0") || newDrugAlertNum == null)
		{
			TipsDialog.GetInstance().setMsg(m_context.getResources().getString(R.string.drug_administration_setting_click_hint_4_text),
											m_context
										   );
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

		double drugStockNumValue = Double.valueOf(newDrugStockNum);
		double drugAlertNumValue = Double.valueOf(newDrugAlertNum);
		if (drugStockNumValue <= drugAlertNumValue)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_administration_setting_click_hint_5_text));
			TipsDialog.GetInstance().show();
			return;
		}

		ClickEventOnDialogBtn clickEventOnDialogBtn = new ClickEventOnDialogBtn();
		if (oldDrugStockNum == drugStockNumValue && drugAlertNumValue == oldDrugAlertNum && newDrugReminderState == oldDrugReminderState)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_administration_setting_click_hint_6_text),
											m_context,
											m_context.getString(R.string.drug_administration_setting_click_dialog_positive_text),
											clickEventOnDialogBtn,
											m_context.getString(R.string.drug_administration_setting_click_dialog_negative_text),
											clickEventOnDialogBtn);
			TipsDialog.GetInstance().show();
			return;
		}


		RequestMedicineBoxSetEvent requestAddMedicalStockAction = new RequestMedicineBoxSetEvent();

		requestAddMedicalStockAction.setUID(userID);
		requestAddMedicalStockAction.setMBID(selectMedicalStockID);
		requestAddMedicalStockAction.setRemainNum(Double.valueOf(newDrugStockNum));
		requestAddMedicalStockAction.setWaringNum(Double.valueOf(newDrugAlertNum));
		requestAddMedicalStockAction.setValid(newDrugReminderState);
		DBusinessMsgHandler.GetInstance().requestMedicineBoxSetAction(requestAddMedicalStockAction);

		return;
	}

	class ClickEventOnDialogBtn implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if (which == DialogInterface.BUTTON_NEGATIVE)
			{
				DrugAdministrationSettingActivity acitvity = (DrugAdministrationSettingActivity)m_context;
				acitvity.finish();
			}
		}
	}

	//设置成功
	public void onEventMainThread(AnswerMedicineBoxSetEvent event)
	{
		DrugAdministrationSettingActivity drugAdministrationSettingActivity = (DrugAdministrationSettingActivity)m_context;

		//提示保存成功
		TipsDialog.GetInstance().setMsg(drugAdministrationSettingActivity.getResources().getString(R.string.drug_administration_setting_click_setting_complete_text));
		TipsDialog.GetInstance().show();

		//关闭添加页面
		drugAdministrationSettingActivity.finish();
	}


	public void DrugSettingPageFillingContent()
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
		drugAdministrationSettingActivity.setSelectMedicalStockID(selectMedicalStockID);

		//获取药品库存信息
		DMedicineBox drugStockInfo = DBusinessData.GetInstance().getMedicineBoxList().getMedicineBoxByID(Integer.valueOf(
																												 selectMedicalStockID
																														)
																										);

		if (drugStockInfo == null)
		{
			TipsDialog.GetInstance().setMsg("drugStockInfo == null![selectMedicalStockID:=" + selectMedicalStockID + "]");
			TipsDialog.GetInstance().show();
			return;
		}

		SimpleDateFormat sdf             = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
		String           addCalender     = sdf.format(drugStockInfo.getAddCalendar().getTime());
		String           warningCalender = sdf.format(drugStockInfo.getWarningTime().getTime());
		String drugName = DBusinessData.GetInstance().getMedicineList().getMedicineByID(drugStockInfo.getMID()
																					   ).getName();
		String        isMedicalReminderStateText = null;
		int           drugID                     = drugStockInfo.getMID();
		DMedicineUnit drugUnit                   = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(drugID);
		String        drugUnitName               = drugUnit.getUnitName();

		if (drugStockInfo.isMedicalReminderState())
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
		drugAdministrationSettingActivity.getDrugStockUnitTV().setText(drugUnitName);
		drugAdministrationSettingActivity.getDrugAlertUnitTV().setText(drugUnitName);

		drugAdministrationSettingActivity.setDrugID(drugStockInfo.getMID());
		drugAdministrationSettingActivity.setOldDrugReminderState(drugStockInfo.isMedicalReminderState());
		drugAdministrationSettingActivity.setOldDrugStockNum(drugStockInfo.getRemianNum());
		drugAdministrationSettingActivity.setOldDrugAlertNum(drugStockInfo.getWaringNum());

		return;
	}

}
