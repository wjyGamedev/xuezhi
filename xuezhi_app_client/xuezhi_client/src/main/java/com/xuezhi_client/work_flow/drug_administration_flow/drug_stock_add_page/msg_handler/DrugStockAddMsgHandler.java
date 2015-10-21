package com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.msg_handler;

import android.app.FragmentTransaction;
import android.widget.Toast;

import com.module.frame.BaseUIMsgHandler;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedical;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicalUnit;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAddMedicalStockEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerMedicalStockListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAddMedicalStockEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestMedicalStockListEvent;
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
		DMedical             m_medical         = DBusinessData.GetInstance().getMedicalList().getMedicalByID(drugID);

		if (m_medical == null)
		{
			TipsDialog.GetInstance().setMsg("m_medicalUnit == null,drugID = " + drugID + "", m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		int UnitID = m_medical.getMUID();

		//数据验证
		if (drugID == 0)
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

		if (drugStockNum == null)
		{
			TipsDialog.GetInstance().setMsg(m_context.getString(R.string.drug_stock_add_click_hint_3_text), m_context);
			TipsDialog.GetInstance().show();
			return;
		}

		if (drugAlertNum == null)
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
		requestAddMedicalStockAction.setUnitID(String.valueOf(UnitID));
		requestAddMedicalStockAction.setStatus(drugReminderState);
		DBusinessMsgHandler.GetInstance().requestAddMedicalStockAction(requestAddMedicalStockAction);

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
	public void onEventMainThread(AnswerMedicalStockListEvent event)
	{
		DrugStockAddActivity drugStockAddActivity = (DrugStockAddActivity)m_context;
		drugStockAddActivity.updateContent();

		//提示保存成功
		Toast.makeText(drugStockAddActivity, drugStockAddActivity.getResources().getString(R.string.drug_stock_add_save_complete_text), Toast.LENGTH_SHORT).show();

		//关闭添加页面
		drugStockAddActivity.finish();
	}


	//打开选择药品弹框
	public void go2SelectDrugFragment()
	{
		DrugStockAddActivity activity = (DrugStockAddActivity)m_context;

		SelectDrugFragment selectDepartmentFragment = new SelectDrugFragment();

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.drug_stock_add_page, selectDepartmentFragment, selectDepartmentFragment.getClass().getName());
		transaction.commit();

		return;
	}

	//请求发送科室列表
	public void requestDrugListAction()
	{
		DBusinessMsgHandler.GetInstance().requestMedicalListAction();
		return;
	}


	public void setMedicalID(int medicalID)
	{
		DrugStockAddActivity activity = (DrugStockAddActivity)m_context;

		//01. set department ui
		ArrayList<DMedical> medicalList = DBusinessData.GetInstance().getMedicalList().getMedicals();
		for (DMedical medical : medicalList)
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
		DMedicalUnit medicalUnit = DBusinessData.GetInstance().getMedicalUnitList().getMedicalUnitByID(m_medicalUnitID);
		String       UnitName    = medicalUnit.getUnitName();
		DrugStockAddActivity activity = (DrugStockAddActivity)m_context;
		activity.getDrugAlertUnitTV().setText(UnitName);
		activity.getDrugStockUnitTV().setText(UnitName);
	}
}