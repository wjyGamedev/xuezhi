/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.select_nurse.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/29		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.select_nurse_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.module.data.DGlobal;
import com.module.frame.IBaseAdapter;
import com.module.widget.circleimageview.CircleImageView;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.data_module.nurse_list.data.DFaceImages;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasics;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseBasicsList;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseContainer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectNurseAdapter extends IBaseAdapter
{
	private DNurseBasicsList        m_nurseBasicsList = null;
	private LayoutInflater          m_layoutInflater  = null;
	private ArrayList<DNurseBasics> m_nurseBasics     = new ArrayList<DNurseBasics>();

	@Override
	public int getCount()
	{
		ArrayList<DNurseBasics> nurseBasicses = m_nurseBasicsList.getNurseBasicses();
		if (nurseBasicses == null || nurseBasicses.isEmpty())
			return 0;

		return nurseBasicses.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (position >= m_nurseBasics.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_nurseBasics.size()[position:=" + position + "][m_nurseBasics.size():=" +
													m_nurseBasics.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}

		DNurseBasics tmpNurseBasics = m_nurseBasics.get(position);
		return tmpNurseBasics.getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;

		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_worker_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		m_nurseBasics = m_nurseBasicsList.getNurseBasicses();
		viewHolder.initContent(m_nurseBasics, position);
		return convertView;
	}

	public SelectNurseAdapter(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		m_nurseBasicsList = DNurseContainer.GetInstance().getNurseBasicsList();
		m_layoutInflater = LayoutInflater.from(m_context);
	}

}

final class ViewHolder
{

	//widget
	@Bind (R.id.worker_btn) LinearLayout    m_listViewLayout        = null;    //listview布局
	@Bind (R.id.pic)        CircleImageView m_faceImage             = null;        //头像
	@Bind (R.id.name)       TextView        m_tvName                = null;            //名字
	@Bind (R.id.star)       RatingBar       m_starLevel             = null;        //星级
	@Bind (R.id.age)        TextView        m_tvAge                 = null;            //年龄
	@Bind (R.id.province)   TextView        m_tvHomeTown            = null;        //籍贯
	@Bind (R.id.workYear)   TextView        m_tvNursingExp          = null;    //护理经验
	@Bind (R.id.level_name) TextView        m_tvNursingLevel        = null;    //护理级别
	@Bind (R.id.price)      TextView        m_tvServiceChargePerDay = null; //价格
	@Bind (R.id.inService)  TextView        m_tvServiceStatus       = null; //服务状态

	//logical
	private View m_view = null;

	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}

	public void initContent(ArrayList<DNurseBasics> m_nurseBasics, int position)
	{
		if (m_nurseBasics == null || m_nurseBasics.isEmpty())
		{
			TipsDialog.GetInstance().setMsg("m_nurseBasics == null");
			TipsDialog.GetInstance().show();
			return;
		}

		if (position >= m_nurseBasics.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_nurseBasics.size()[position:=" + position + "][m_nurseBasics.size():=" +
													m_nurseBasics.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return;
		}

		DNurseBasics tmpNurseBasics = m_nurseBasics.get(position);

		int headerImgResID = DFaceImages.getInstance().getImgResIDbyID(tmpNurseBasics.getID());
		if (headerImgResID == DataConfig.DEFAULT_VALUE)
		{
			headerImgResID = DFaceImages.DEFAULT_IMAGE_RES_ID;
		}
		m_faceImage.setImageResource(headerImgResID);
		m_tvName.setText(tmpNurseBasics.getName());
		m_starLevel.setRating(tmpNurseBasics.getStarLevel());
		String age = String.valueOf(tmpNurseBasics.getAge()) + DGlobal.GetInstance().getAppContext().getString(R.string.content_age);
		m_tvAge.setText(age);
		m_tvHomeTown.setText(tmpNurseBasics.getHomeTown());
		m_tvNursingExp.setText(tmpNurseBasics.getNursingExp());
		m_tvNursingLevel.setText(tmpNurseBasics.getNursingLevel());
		m_tvServiceChargePerDay.setText(String.valueOf(tmpNurseBasics.getServiceChargePerAllCanntCare()));
		m_tvServiceStatus.setText(tmpNurseBasics.getServiceStatus());

	}

}
