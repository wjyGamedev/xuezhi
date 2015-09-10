package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.ui;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
import com.taixinkanghu_client.data_module.hospital_list.data.DHospital;
import com.taixinkanghu_client.data_module.hospital_list.data.DHospitalList;
import com.taixinkanghu_client.data_module.hospital_list.msg_handler.AnswerHospitalListEvent;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.appiontment_nursing_page.msg_handler.AppiontmentNursingMsgHandler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectHospitalFragment extends Fragment implements View.OnTouchListener
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
	private HospitalItemClickEvent       m_hospitalItemClickEvent       = new HospitalItemClickEvent();

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
		loadHospitalList();

		return m_view;
	}

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
	class HospitalItemClickEvent implements View.OnClickListener
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

			Integer indexBtn = 0;
			int indexHospital = 0;
			try
			{
				indexBtn = Integer.valueOf(tag);
				indexHospital = indexBtn - 1;
			}
			catch (NumberFormatException e)
			{
				TipsDialog.GetInstance().setMsg(e.toString(), getActivity());
				TipsDialog.GetInstance().show();
				return;
			}

			//02. 选择全部医院
			int hospitalID = DataConfig.DEFAULT_VALUE;
			if (indexBtn == 0)
			{
				hospitalID = 0;
			}
			//03. 选择单个医院
			else
			{
				//hospitalid
				ArrayList<DHospital> hospitalLists = DHospitalList.GetInstance().getHospitals();
				if (hospitalLists == null)
				{
					TipsDialog.GetInstance().setMsg("hospitalLists == null", getActivity());
					TipsDialog.GetInstance().show();
					return;
				}
				if (indexHospital >= hospitalLists.size())
				{
					TipsDialog.GetInstance().setMsg("indexHospital >= hospitalLists.size()[indexHospital:="+indexHospital+"][hospitalLists.size:=+"+hospitalLists.size()+"+]", getActivity());
					TipsDialog.GetInstance().show();
					return;
				}

				DHospital dHospital = hospitalLists.get(indexHospital);
				hospitalID = dHospital.getID();
			}

			//04. update hospital id
			m_appiontmentNursingMsgHandler.setHospitalID(hospitalID);

			//05. 关闭当前医院选择
			cancelAction();
			return;

		} //end_onClick(View v)
	} //end_class HospitalItemClickEvent

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
		m_contentTipsTV.setText(getActivity().getResources().getString(R.string.hospital_content_tips));
		m_moreContentTipsTV.setText(getActivity().getResources().getString(R.string.hospital_more_content_tips));

		return;

	}

	private void initListener()
	{
		//防止点击穿透
		m_view.setOnTouchListener(this);

	}

	private void updateHospitalItemListener()
	{
		for (Button btn : m_buttons)
		{
			btn.setOnClickListener(m_hospitalItemClickEvent);
		}
	}


	private void loadHospitalList()
	{
		//填充医院列表
		ArrayList<DHospital> hospitals = DHospitalList.GetInstance().getHospitals();

		//01. 没有医院列表，则重新发送
		if (hospitals.isEmpty())
		{
			m_appiontmentNursingMsgHandler.requestHospitalListAction();
			return;
		}

		//02. 有医院列表，则在本地动态显示。
		m_buttons.clear();

		int       size          = hospitals.size();
		int       iMaxColumn    = UIConfig.SELECT_HOSPITAL_FRAGMENT_MAX_COLUMN;
		int       iMaxRow       = (size + iMaxColumn - 1) / iMaxColumn;
		int       indexHospital = 0;
		int       indexBtn      = 0;
		DHospital dHospital     = null;
		String    tag           = null;

		for (int indexRow = 0; indexRow <= iMaxRow; ++indexRow)
		{
			for (int indexColumn = 0; indexColumn < iMaxColumn; ++indexColumn)
			{
				indexBtn = indexRow * iMaxColumn + indexColumn;
				//因为第一个为全部，所以-1
				indexHospital = indexBtn - 1;
				if (indexHospital >= size)
				{
					return;
				}

				View view = m_layoutInflater.inflate(R.layout.fragment_select_list_item, m_gridLayout, false);
				tag = String.valueOf(indexBtn);
				Button btn = (Button)view.findViewById(R.id.item_id);
				btn.setTag(tag);
				if (indexRow == 0 && indexColumn == 0)
				{
					btn.setText(getActivity().getResources().getString(R.string.content_all));
				}
				else
				{
					dHospital = hospitals.get(indexHospital);
					if (dHospital == null)
						return;

					String name = dHospital.getName();
					btn.setText(name);
				}
				m_buttons.add(btn);

				//设置它的行和列
				GridLayout.Spec rowSpec = GridLayout.spec(indexRow);
				GridLayout.Spec columnSpec = GridLayout.spec(indexColumn);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				m_gridLayout.addView(view, params);
			}
		}

		//03. update hospital item listener
		updateHospitalItemListener();

		return;

	}

	private void cancelAction()
	{
		FragmentManager     fgManager           = getActivity().getFragmentManager();
		Fragment            fragment            = fgManager.findFragmentByTag(SelectHospitalFragment.class.getName());
		FragmentTransaction fragmentTransaction = fgManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();

		return;

	}

	/**
	 * EventBus handler
	 */
	public void onEventMainThread(AnswerHospitalListEvent event)
	{
		//获取医院列表成功，则重新加载数据
		loadHospitalList();
		return;
	}

}