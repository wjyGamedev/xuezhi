package com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.msg_handler;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalStock;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAddMedicalStockEvent;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_administration_setting_page.DrugAdministrationSettingActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/23.
 */
public class DrugAdministrationSettingMsgHandler extends BaseUIMsgHandler
{
	public DrugAdministrationSettingMsgHandler(DrugAdministrationSettingActivity activity)
	{
		super(activity);
	}

	public void saveDrugStockSettingInfo()
	{
		DrugAdministrationSettingActivity drugAdministrationSettingActivity = (DrugAdministrationSettingActivity)m_context;
		int                  drugID            = drugAdministrationSettingActivity.getDrugID();
		String               userID            = "2";
		String               drugStockNum      = String.valueOf(drugAdministrationSettingActivity.getDrugStockNum());
		String               drugAlertNum      = String.valueOf(drugAdministrationSettingActivity.getDrugAlertNum());
		boolean              drugReminderState = drugAdministrationSettingActivity.isDrugReminderState();
		Calendar             m_addCalendar     = Calendar.getInstance();
		Calendar             m_warningCalendar = Calendar.getInstance();
//		EnumConfig.MedicalUnit m_medicalUnit = get;

		//数据验证
		if (drugID == 0)
		{
			TipsDialog.GetInstance().setMsg("请选择药品", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (userID.isEmpty())
		{
			TipsDialog.GetInstance().setMsg("您还没有注册", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (drugStockNum == null)
		{
			TipsDialog.GetInstance().setMsg("请填写药品存量", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (drugAlertNum == null)
		{
			TipsDialog.GetInstance().setMsg("请填写药品警报存量", m_context);
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

		RequestAddMedicalStockEvent requestAddMedicalStockAction = new RequestAddMedicalStockEvent();

		requestAddMedicalStockAction.setUID(userID);
		requestAddMedicalStockAction.setMID(Integer.toString(drugID));
		requestAddMedicalStockAction.setRemainNum(Double.valueOf(drugStockNum));
		requestAddMedicalStockAction.setWaringNum(Double.valueOf(drugAlertNum));
		requestAddMedicalStockAction.setUnitID(String.valueOf(EnumConfig.MedicalUnit.MILLIGRAM.getId()));
		requestAddMedicalStockAction.setStatus(drugReminderState);
		DBusinessMsgHandler.GetInstance().requestAddMedicalStockAction(requestAddMedicalStockAction);

		return;

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

		SimpleDateFormat sdf             = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);
		String           addCalender     = sdf.format(drugStockInfo.getAddCalendar().getTime());
		String           warningCalender = sdf.format(drugStockInfo.getWarningCalendar().getTime());
		String           drugName        = DBusinessData.GetInstance().getMedicalList().getMedicalByID(drugStockInfo.getMID()).getName();

		drugAdministrationSettingActivity.getDrugNameTV().setText(drugName);
		drugAdministrationSettingActivity.getDrugStockNumET().setText(String.valueOf(drugStockInfo.getRemianNum()));
		drugAdministrationSettingActivity.getDrugAlertNumET().setText(String.valueOf(drugStockInfo.getWaringNum()));
		drugAdministrationSettingActivity.getDrugAddDateNumTV().setText(addCalender);
		drugAdministrationSettingActivity.getDrugRunOutDateNumTV().setText(warningCalender);
		drugAdministrationSettingActivity.getDrugReminderStateCB().setChecked(drugStockInfo.isMedicalReminderState());

		return;
	}

}
