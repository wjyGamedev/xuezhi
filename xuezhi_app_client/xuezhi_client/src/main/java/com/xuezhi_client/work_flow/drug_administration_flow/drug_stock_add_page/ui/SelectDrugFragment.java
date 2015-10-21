/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.appointment_nursing.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DataConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedical;
import com.xuezhi_client.work_flow.drug_administration_flow.drug_stock_add_page.msg_handler.DrugStockAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectDrugFragment extends Fragment implements View.OnTouchListener
{
	//widget
	@Bind (R.id.header_bg_ll)      LinearLayout m_headerBgLL        = null;
	@Bind (R.id.content_tips)      TextView     m_contentTipsTV     = null;
	@Bind (R.id.more_content_tips) TextView     m_moreContentTipsTV = null;
	@Bind (R.id.widget_region_gl)  GridLayout   m_gridLayout        = null;
	@Bind (R.id.bottom_bg_ll)      LinearLayout m_bottomBgLL        = null;

	//logical
	private LayoutInflater m_layoutInflater = null;
	private View           m_view           = null;

	private ArrayList<Button>      m_buttons                = new ArrayList<>();
	private DrugStockAddMsgHandler m_drugStockAddMsgHandler = null;
	private DrugItemClickEvent     m_drugItemClickEvent     = new DrugItemClickEvent();

	private final int SELECT_DRUG_FRAGMENT_MAX_COLUMN = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_layoutInflater = inflater;
		m_view = m_layoutInflater.inflate(R.layout.fragment_select_drug, container, false);
		ButterKnife.bind(this, m_view);

		init();
		initListener();
		return m_view;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		loadDrugList();
	}

	//防止点击穿透
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		return true;
	}

	/**
	 * widget event
	 */
	@OnClick (R.id.header_bg_ll)
	public void clickAgeHeaderRegion()
	{
		cancelAction();
		return;
	}

	@OnClick (R.id.bottom_bg_ll)
	public void clickAgeBottomRegion()
	{
		cancelAction();
		return;
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
			ArrayList<DMedical> drugList = DBusinessData.GetInstance().getMedicalList().getMedicals();
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

			DMedical drug = drugList.get(indexDrug);
			int      id   = drug.getID();

			m_drugStockAddMsgHandler.setMedicalID(id);
			int medicalUnitID = drug.getMUID();
			m_drugStockAddMsgHandler.setMedicalUnit(medicalUnitID);

			//03. 关闭当前药品选择
			cancelAction();
			return;

		} //end_onClick(View v)
	} //end_class DrugItemClickEvent

	/**
	 * inner func
	 */
	private void init()
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

		return;
	}

	private void initListener()
	{
		//防止点击穿透
		m_view.setOnTouchListener(this);
	}

	private void updateDrugItemListener()
	{
		for (Button btn : m_buttons)
		{
			btn.setOnClickListener(m_drugItemClickEvent);
		}
		return;

	}

	public void loadDrugList()
	{
		ArrayList<DMedical> drugs = DBusinessData.GetInstance().getMedicalList().getMedicals();

		//01. 没有药品列表，则重新发送
		if (drugs.isEmpty())
		{
			m_drugStockAddMsgHandler.requestDrugListAction();
			return;
		}

		//02. 药品列表，则在本地动态显示。
		m_buttons.clear();

		int      size          = drugs.size();
		int      iMaxColumn    = SELECT_DRUG_FRAGMENT_MAX_COLUMN;
		int      iMaxRow       = (size + iMaxColumn - 1) / iMaxColumn;
		int      indexHospital = 0;
		DMedical medical       = null;
		String   tag           = null;
		for (int indexRow = 0; indexRow < iMaxRow; ++indexRow)
		{
			for (int indexColumn = 0; indexColumn < iMaxColumn; ++indexColumn)
			{
				indexHospital = indexRow * iMaxColumn + indexColumn;
				if (indexHospital >= size)
				{
					continue;
				}

				View view = m_layoutInflater.inflate(R.layout.fragment_select_drug_item, m_gridLayout, false);
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
				m_buttons.add(btn);

				//设置它的行和列
				GridLayout.Spec rowSpec = GridLayout.spec(indexRow);
				GridLayout.Spec columnSpec = GridLayout.spec(indexColumn);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				m_gridLayout.addView(view, params);
			}
		}

		//03. 更新药品item的点击event
		updateDrugItemListener();

		return;

	}

	private void cancelAction()
	{
		FragmentManager     fgManager           = getActivity().getFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectDrugFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		return;
	}

}
