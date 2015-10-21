/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.module.widget.dialog.TipsDialog;
import com.module.widget.fragment.select_list_fragment.SelectListFragment;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedical;
import com.xuezhi_client.work_flow.medication_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

public class SelectMedicineFragment extends SelectListFragment
{
	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = null;
	private ItemClickEvent                m_itemClickEvent                = new ItemClickEvent();

	@Override
	public void init()
	{
		//01. m_medicineReminderAddMsgHandler
		MedicineReminderAddActivity activity = (MedicineReminderAddActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("MedicineReminderAddActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_medicineReminderAddMsgHandler = activity.getMedicineReminderAddMsgHandler();
		if (m_medicineReminderAddMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("m_medicineReminderAddMsgHandler == null", activity);
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 设置提示信息
		m_contentTipsTV.setText(activity.getString(R.string.medicine_reminder_add_select_medicine_tips));
		m_moreContentTipsTV.setVisibility(View.INVISIBLE);

	}

	@Override
	public void initWidgetListener()
	{
	}

	@Override
	public void updateContent()
	{
		initBtns();
		initBtnListeners();
	}


	@Override
	public void impClickHeaderRegion()
	{

	}

	@Override
	public void impClickBottomRegion()
	{

	}


	/**
	 * override func
	 */
	class ItemClickEvent implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			//01. 获取tag
			Object tagObj = v.getTag();
			if (tagObj == null)
			{
				TipsDialog.GetInstance().setMsg("tagObj == null", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}
			String tag = (String)tagObj;
			if (tag == null)
			{
				TipsDialog.GetInstance().setMsg("tag == null", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			//02. update department ID
			int indexMedical = DataConfig.DEFAULT_VALUE;
			try
			{
				indexMedical = Integer.valueOf(tag);
			}
			catch (NumberFormatException e)
			{
				TipsDialog.GetInstance().setMsg(e.toString(), getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			ArrayList<DMedical> medicalArrayList = DBusinessData.GetInstance().getMedicalList().getMedicals();
			if (medicalArrayList == null)
			{
				TipsDialog.GetInstance().setMsg("medicalArrayList == null", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}
			if (indexMedical >= medicalArrayList.size())
			{
				TipsDialog.GetInstance().setMsg("indexMedical >= medicalArrayList.size()[indexMedical:="+indexMedical+"][medicalArrayList.size:=+"+medicalArrayList.size()+"+]", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			DMedical medical = medicalArrayList.get(indexMedical);
			int id = medical.getID();

			m_medicineReminderAddMsgHandler.setMedicalID(id);

			//03. 关闭当前科室选择
			cancelAction();
			return;
		}
	}

	/**
	 * inner func
	 */
	private void initBtns()
	{
		ArrayList<DMedical> medicalArrayList = DBusinessData.GetInstance().getMedicalList().getMedicals();

		//01. 没有药品列表，则重新发送
		if (medicalArrayList.isEmpty())
		{
			m_medicineReminderAddMsgHandler.requestMedicalListAction();
			return;
		}

		//02. add btns
		m_buttons.clear();

		int      size          = medicalArrayList.size();
		int      iMaxColumn    = getMaxColunms();
		int      iMaxRow       = (size + iMaxColumn - 1) / iMaxColumn;
		int      indexMedicine = 0;
		DMedical medical       = null;
		String   tag           = null;
		for (int indexRow = 0; indexRow < iMaxRow; ++indexRow)
		{
			for (int indexColumn = 0; indexColumn < iMaxColumn; ++indexColumn)
			{
				indexMedicine = indexRow * iMaxColumn + indexColumn;
				if (indexMedicine >= size)
				{
					continue;
				}

				View view = m_layoutInflater.inflate(R.layout.fragment_select_list_item, m_gridLayout, false);
				tag = String.valueOf(indexMedicine);
				Button btn = (Button)view.findViewById(R.id.item_id);
				btn.setTag(tag);
				medical = medicalArrayList.get(indexMedicine);
				if (medical == null)
					return;

				String name = medical.getName();
				btn.setText(name);

				DisplayMetrics metric = new DisplayMetrics();
				getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
				int windowWidth = metric.widthPixels; // 屏幕宽度（像素）
				//				int height = metric.heightPixels; // 屏幕高度（像素）

				btn.setWidth(windowWidth / 2);
				m_buttons.add(btn);

				//设置它的行和列
				GridLayout.Spec rowSpec = GridLayout.spec(indexRow);
				GridLayout.Spec columnSpec = GridLayout.spec(indexColumn);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				m_gridLayout.addView(view, params);
			}
		}

	}

	private void initBtnListeners()
	{
		for (Button btn : m_buttons)
		{
			btn.setOnClickListener(m_itemClickEvent);
		}
		return;
	}

	private void cancelAction()
	{
		FragmentManager     fgManager           = getActivity().getFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectMedicineFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		return;

	}
}