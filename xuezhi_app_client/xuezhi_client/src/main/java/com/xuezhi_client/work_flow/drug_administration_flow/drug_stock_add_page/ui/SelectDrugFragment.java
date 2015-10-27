/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.appointment_nursing.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.module.widget.dialog.TipsDialog;
import com.module.widget.fragment.select_list_fragment.SelectListFragment;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.msg_handler.DrugStockAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

public class SelectDrugFragment extends SelectListFragment implements View.OnTouchListener
{
	//logical
	private DrugStockAddMsgHandler m_drugStockAddMsgHandler = null;
	private DrugItemClickEvent     m_drugItemClickEvent     = new DrugItemClickEvent();

	@Override
	public void init()
	{
		//01. m_appiontmentNursingMsgHandler
		DrugStockAddActivity activity = (DrugStockAddActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("ApoitNursingActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_drugStockAddMsgHandler = activity.getDrugStockAddMsgHandler();
		if (m_drugStockAddMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("appiontmentNursingMsgHandler == null");
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 设置提示信息
		m_contentTipsTV.setText(getActivity().getResources().getString(R.string.drug_stock_add_content_tips_text));
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

	private void initBtns()
	{
		ArrayList<DMedicine> drugs = DBusinessData.GetInstance().getMedicineList().getMedicals();

		//01. 没有药品列表，则重新发送
		if (drugs.isEmpty())
		{
			m_drugStockAddMsgHandler.requestDrugListAction();
			return;
		}

		//02. 药品列表，则在本地动态显示。
		m_buttons.clear();

		int       size          = drugs.size();
		int       iMaxColumn    = getMaxColunms();
		int       iMaxRow       = (size + iMaxColumn - 1) / iMaxColumn;
		int       indexHospital = 0;
		DMedicine medical       = null;
		String    tag           = null;
		for (int indexRow = 0; indexRow < iMaxRow; ++indexRow)
		{
			for (int indexColumn = 0; indexColumn < iMaxColumn; ++indexColumn)
			{
				indexHospital = indexRow * iMaxColumn + indexColumn;
				if (indexHospital >= size)
				{
					continue;
				}

				View view = m_layoutInflater.inflate(R.layout.fragment_select_list_item, m_gridLayout, false);
				tag = String.valueOf(indexHospital);
				Button btn = (Button)view.findViewById(R.id.item_id);
				btn.setTag(tag);
				medical = drugs.get(indexHospital);
				if (medical == null)
					return;

				String name = medical.getName();
				btn.setText(name);

				DisplayMetrics metric = new DisplayMetrics();
				getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
				int windowWidth = metric.widthPixels; // 屏幕宽度（像素）
				//				int height = metric.heightPixels; // 屏幕高度（像素）

				btn.setWidth(windowWidth / 2);
				btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				m_buttons.add(btn);

				//设置它的行和列
				GridLayout.Spec rowSpec = GridLayout.spec(indexRow);
				GridLayout.Spec columnSpec = GridLayout.spec(indexColumn);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				m_gridLayout.addView(view, params);
			}
		}

		return;
	}

	private void initBtnListeners()
	{
		for (Button btn : m_buttons)
		{
			btn.setOnClickListener(m_drugItemClickEvent);
		}
		return;
	}

	@Override
	public void impClickHeaderRegion()
	{
		cancelAction();
	}

	@Override
	public void impClickBottomRegion()
	{
		cancelAction();
	}


	/**
	 * override func
	 */
	class DrugItemClickEvent implements View.OnClickListener
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

			//02. update drug ID
			int indexDrug = DataConfig.DEFAULT_VALUE;
			try
			{
				indexDrug = Integer.valueOf(tag);
			}
			catch (NumberFormatException e)
			{
				TipsDialog.GetInstance().setMsg(e.toString(), getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			//drugid
			ArrayList<DMedicine> drugList = DBusinessData.GetInstance().getMedicineList().getMedicals();
			if (drugList == null)
			{
				TipsDialog.GetInstance().setMsg("drugList == null", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}
			if (indexDrug >= drugList.size())
			{
				TipsDialog.GetInstance().setMsg("indexDrug >= drugList.size()[indexDrug:=" + indexDrug +
														"][drugList.size:=+" + drugList.size() + "+]", getActivity()
											   );
				TipsDialog.GetInstance().show();
				return;
			}

			DMedicine drug = drugList.get(indexDrug);
			int       id   = drug.getID();

			m_drugStockAddMsgHandler.setMedicalID(id);
			int medicalUnitID = drug.getMUID();
			m_drugStockAddMsgHandler.setMedicalUnit(medicalUnitID);

			DrugStockAddActivity drugStockAddActivity = (DrugStockAddActivity)getActivity();
			if (drugStockAddActivity.inspection_data())
			{
				drugStockAddActivity.getDrugRunOutTV().setText(drugStockAddActivity.calculateRunOutData());
			}

			//03. 关闭当前药品选择
			cancelAction();
			return;

		}
	}

	private void cancelAction()
	{
		FragmentManager     fgManager           = getActivity().getSupportFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectDrugFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		return;
	}

}
