package com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.msg_handler;

import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicineBoxAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicineBoxAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicineBoxGetListEvent;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui.DrugStockAddActivity;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui.SelectDrugFragment;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/30.
 */
public class DrugStockAddMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public DrugStockAddMsgHandler(DrugStockAddActivity activity)
	{
		super(activity);
	}

	public void saveDrugInfo()
	{
		DrugStockAddActivity activity          = (DrugStockAddActivity)m_context;
		int                  drugID            = activity.getDrugID();
		String               userID            = DAccount.GetInstance().getId();
		String               drugStockNum      = activity.getDrugStockNum();
		String               drugAlertNum      = activity.getDrugAlertNum();
		boolean              drugReminderState = activity.getDrugReminderStateCB().isChecked();
		Calendar             m_addCalendar     = Calendar.getInstance();
		Calendar             m_warningCalendar = Calendar.getInstance();
		DMedicine m_medical         = DBusinessData.GetInstance().getMedicalList().getMedicalByID(drugID);

		//数据验证
		if (drugID == -1)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_stock_add_click_hint_1_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (userID.isEmpty())
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_stock_add_click_hint_2_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (drugStockNum.equals("") || drugStockNum.equals("0") || drugStockNum == null)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_stock_add_click_hint_3_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (drugAlertNum.equals("") || drugAlertNum.equals("0") || drugAlertNum == null)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_stock_add_click_hint_4_text), m_context);
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

		if (m_medical == null)
		{
			TipsDialog.GetInstance().setMsg("m_medicalUnit == null,drugID = " + drugID + "", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		int UnitID = m_medical.getMUID();

		//		if (drugStockNum <= drugAlertNum)
		//		{
		//			TipsDialog.GetInstance().setMsg("添加数量小于报警数量");
		//			TipsDialog.GetInstance().show();
		//			return;
		//		}

		RequestMedicineBoxAddEvent requestAddMedicalStockAction = new RequestMedicineBoxAddEvent();

		requestAddMedicalStockAction.setUID(userID);
		requestAddMedicalStockAction.setMID(Integer.toString(drugID));
		requestAddMedicalStockAction.setRemainNum(Double.valueOf(drugStockNum));
		requestAddMedicalStockAction.setWaringNum(Double.valueOf(drugAlertNum));
		requestAddMedicalStockAction.setValid(drugReminderState);
		DBusinessMsgHandler.GetInstance().requestMedicineBoxAddAction(requestAddMedicalStockAction);

		return;
	}

	//保存成功，获取药品库存列表
	public void onEventMainThread(AnswerMedicineBoxAddEvent event)
	{
		DrugStockAddActivity drugStockAddActivity = (DrugStockAddActivity)m_context;

		//提示保存成功
		Toast.makeText(drugStockAddActivity,
					   drugStockAddActivity.getResources().getString(R.string.drug_stock_add_save_complete_text),
					   Toast.LENGTH_SHORT
					  ).show();

		//关闭添加页面
		drugStockAddActivity.finish();

	}

	//打开选择药品弹框
	public void go2SelectDrugFragment()
	{
		DrugStockAddActivity activity = (DrugStockAddActivity)m_context;

		SelectDrugFragment selectDepartmentFragment = new SelectDrugFragment();

		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.drug_stock_add_page, selectDepartmentFragment, selectDepartmentFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//请求发送科室列表
	public void requestDrugListAction()
	{
		DBusinessMsgHandler.GetInstance().requestMedicineGetListAction();
		return;
	}


	public void setMedicalID(int medicalID)
	{
		DrugStockAddActivity activity = (DrugStockAddActivity)m_context;

		//01. set department ui
		ArrayList<DMedicine> medicalList = DBusinessData.GetInstance().getMedicalList().getMedicals();
		for (DMedicine medical : medicalList)
		{
			if (medical.getID() == medicalID)
			{
				activity.getDrugNameTV().setText(medical.getName());
			}
		}

		activity.setDrugID(medicalID);
		//		m_dAppiontmentNursingFlow.setDepartmenetID(medicalID);
	}


	public void setMedicalUnit(int m_medicalUnitID)
	{

		if (m_medicalUnitID == 0)
		{
			return;
		}

		//设置单位文本
		DMedicineUnit medicalUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(m_medicalUnitID);
		String               UnitName    = medicalUnit.getUnitName();
		DrugStockAddActivity activity    = (DrugStockAddActivity)m_context;
		activity.getDrugAlertUnitTV().setText(UnitName);
		activity.getDrugStockUnitTV().setText(UnitName);
	}
}
