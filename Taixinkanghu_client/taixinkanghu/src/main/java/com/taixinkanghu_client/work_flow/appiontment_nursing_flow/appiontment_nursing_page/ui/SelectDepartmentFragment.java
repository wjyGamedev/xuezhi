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

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui;

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
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.UIConfig;
import com.taixinkanghu_client.data_module.department_list.data.DDepartment;
import com.taixinkanghu_client.data_module.department_list.data.DDepartmentList;
import com.taixinkanghu_client.data_module.department_list.msg_handler.AnswerDepartmentListEvent;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.msg_handler.AppiontmentNursingMsgHandler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import event.EventBus;

public class SelectDepartmentFragment extends Fragment implements View.OnTouchListener
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

	private ArrayList<Button>            m_buttons                      = new ArrayList<>();
	private AppiontmentNursingMsgHandler m_appiontmentNursingMsgHandler = null;
	private DepartmentItemClickEvent     m_departmentItemClickEvent     = new DepartmentItemClickEvent();

	//TODO:以后放在基类中
	EventBus m_eventBus = EventBus.getDefault();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_layoutInflater = inflater;
		m_view = m_layoutInflater.inflate(R.layout.fragment_select_list, container, false);
		m_eventBus.register(this);

		init();
		initListener();
		loadDepartmentList();

		return m_view;
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
	class DepartmentItemClickEvent implements View.OnClickListener
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
			int indexDepartment = DataConfig.DEFAULT_VALUE;
			try
			{
				indexDepartment = Integer.valueOf(tag);
			}
			catch (NumberFormatException e)
			{
				TipsDialog.GetInstance().setMsg(e.toString(), getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			//departmentid
			ArrayList<DDepartment> departmentList = DDepartmentList.GetInstance().getDepartments();
			if (departmentList == null)
			{
				TipsDialog.GetInstance().setMsg("departmentList == null", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}
			if (indexDepartment >= departmentList.size())
			{
				TipsDialog.GetInstance().setMsg("indexDepartment >= departmentList.size()[indexDepartment:="+indexDepartment+"][departmentList.size:=+"+departmentList.size()+"+]", getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			DDepartment department = departmentList.get(indexDepartment);
			int id = department.getID();

			m_appiontmentNursingMsgHandler.setDepartmentID(id);

			//03. 关闭当前科室选择
			cancelAction();
			return;

		} //end_onClick(View v)
	} //end_class DepartmentItemClickEvent

	/**
	 * inner func
	 */
	private void init()
	{
		//01. m_appiontmentNursingMsgHandler
		ApoitNursingActivity activity = (ApoitNursingActivity)getActivity();
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("ApoitNursingActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_appiontmentNursingMsgHandler = activity.getAppiontmentNursingMsgHandler();
		if (m_appiontmentNursingMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("appiontmentNursingMsgHandler == null");
			TipsDialog.GetInstance().show();
			return;
		}

		//02. 设置提示信息
		m_contentTipsTV.setText(getActivity().getResources().getString(R.string.department_content_tips));
		m_moreContentTipsTV.setVisibility(View.INVISIBLE);

		return;
	}

	private void initListener()
	{
		//防止点击穿透
		m_view.setOnTouchListener(this);
	}

	private void updateDepartmentItemListener()
	{
		for (Button btn : m_buttons)
		{
			btn.setOnClickListener(m_departmentItemClickEvent);
		}
		return;

	}
	private void loadDepartmentList()
	{
		ArrayList<DDepartment> departments = DDepartmentList.GetInstance().getDepartments();

		//01. 没有科室列表，则重新发送
		if (departments.isEmpty())
		{
			m_appiontmentNursingMsgHandler.requestDepartmentListAction();
			return;
		}

		//02. 科室列表，则在本地动态显示。
		m_buttons.clear();

		int         size          = departments.size();
		int         iMaxColumn    = UIConfig.SELECT_DEPARTMENT_FRAGMENT_MAX_COLUMN;
		int         iMaxRow       = (size + iMaxColumn - 1) / iMaxColumn;
		int         indexHospital = 0;
		DDepartment department    = null;
		String      tag           = null;
		for (int indexRow = 0; indexRow < iMaxRow; ++indexRow)
		{
			for (int indexColumn = 0; indexColumn < iMaxColumn; ++indexColumn)
			{
				indexHospital = indexRow * iMaxColumn + indexColumn;
				if (indexHospital >= size)
				{
					return;
				}

				View view = m_layoutInflater.inflate(R.layout.fragment_select_list_item, m_gridLayout, false);
				tag = String.valueOf(indexHospital);
				Button btn = (Button)view.findViewById(R.id.item_id);
				btn.setTag(tag);
				department = departments.get(indexHospital);
				if (department == null)
					return;

				String name = department.getName();
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

		//03. 更新科室item的点击event
		updateDepartmentItemListener();

		return;

	}

	private void cancelAction()
	{
		FragmentManager             fgManager           = getActivity().getFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectDepartmentFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		return;

	}

	/**
	 * EventBus handler
	 */
	public void onEventMainThread(AnswerDepartmentListEvent event)
	{
		loadDepartmentList();
		return;
	}
}
