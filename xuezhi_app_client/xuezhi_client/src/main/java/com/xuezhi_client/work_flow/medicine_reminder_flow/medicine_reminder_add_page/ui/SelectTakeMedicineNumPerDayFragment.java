/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/18		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui;

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
import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

public class SelectTakeMedicineNumPerDayFragment extends SelectListFragment
{
	//logical
	private MedicineReminderAddMsgHandler m_medicineReminderAddMsgHandler = null;
	private ItemClickEvent                m_itemClickEvent                = new ItemClickEvent();

	private int[] mTakeMedicineNumPerDay;

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
		m_contentTipsTV.setText(activity.getString(R.string.medicine_reminder_add_select_medicine_num_per_day));
		m_moreContentTipsTV.setVisibility(View.INVISIBLE);

		mTakeMedicineNumPerDay = getActivity().getResources().getIntArray(R.array.take_medicine_num_per_day_array);
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
			int indexTakeMedicineNum = DataConfig.DEFAULT_VALUE;
			try
			{
				indexTakeMedicineNum = Integer.valueOf(tag);
			}
			catch (NumberFormatException e)
			{
				TipsDialog.GetInstance().setMsg(e.toString(), getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			if (indexTakeMedicineNum >= mTakeMedicineNumPerDay.length)
			{
				TipsDialog.GetInstance().setMsg("indexTakeMedicineNum >= mTakeMedicineNumPerDay.length。[indexTakeMedicineNum:="+indexTakeMedicineNum+"][mTakeMedicineNumPerDay.length:="+mTakeMedicineNumPerDay.length+"]");
				TipsDialog.GetInstance().show();
				return;
			}

			m_medicineReminderAddMsgHandler.setTakeMedicineTime(mTakeMedicineNumPerDay[indexTakeMedicineNum]);

			cancelAction();
			return;
		}
	}

	private void initBtns()
	{
		int      size = mTakeMedicineNumPerDay.length;
		if (size == 0)
			return;

		//02. add btns
		m_buttons.clear();

		int      iMaxColumn    = getMaxColunms();
		int      iMaxRow       = (size + iMaxColumn - 1) / iMaxColumn;
		int      indexTakeMedicineNum = 0;
		String   tag           = null;

		for (int indexRow = 0; indexRow < iMaxRow; ++indexRow)
		{
			for (int indexColumn = 0; indexColumn < iMaxColumn; ++indexColumn)
			{
				indexTakeMedicineNum = indexRow * iMaxColumn + indexColumn;
				if (indexTakeMedicineNum >= size)
				{
					continue;
				}

				View view = m_layoutInflater.inflate(R.layout.fragment_select_list_item, m_gridLayout, false);
				tag = String.valueOf(indexTakeMedicineNum);
				Button btn = (Button)view.findViewById(R.id.item_id);
				btn.setTag(tag);
				btn.setText(String.valueOf(mTakeMedicineNumPerDay[indexTakeMedicineNum]));

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
		FragmentManager fgManager = getActivity().getSupportFragmentManager();
		Fragment        fragment  = fgManager.findFragmentByTag(SelectTakeMedicineNumPerDayFragment.class.getName());
		if (fragment == null)
			return;

		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		return;
	}

}
