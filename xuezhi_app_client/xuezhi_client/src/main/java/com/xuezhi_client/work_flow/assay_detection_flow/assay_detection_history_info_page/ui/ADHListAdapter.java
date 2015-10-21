/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.module.data.DGlobal;
import com.module.frame.IBaseAdapter;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetection;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetectionList;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ADHListAdapter extends IBaseAdapter
{
	private DAssayDetectionList        m_assayDetectionList = null;
	private LayoutInflater             m_layoutInflater     = null;
	private ArrayList<DAssayDetection> m_assayDetections    = new ArrayList<>();

	@Override
	public int getCount()
	{
		m_assayDetectionList = DBusinessData.GetInstance().getAssayDetectionList();

		ArrayList<DAssayDetection> assayDetections = m_assayDetectionList.getAssayDetections();
		if (assayDetections == null || assayDetections.isEmpty())
			return 0;

		return assayDetections.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (position >= m_assayDetections.size())
		{
			TipsDialog.GetInstance().setMsg("position >= m_assayDetections.size()[position:=" + position + "][m_assayDetections.size():=" +
													m_assayDetections.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return 0;
		}

		DAssayDetection tmpAssayDetection = m_assayDetections.get(position);
		return tmpAssayDetection.getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;

		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_assay_detection_history_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		m_assayDetections = m_assayDetectionList.getAssayDetections();
		viewHolder.initContent(m_assayDetections, position);
		return convertView;
	}

	public ADHListAdapter(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		m_assayDetectionList = DBusinessData.GetInstance().getAssayDetectionList();
		m_layoutInflater = LayoutInflater.from(m_context);
	}

}

final class ViewHolder
{
	//widget
	@Bind (R.id.assay_detection_record_date_tv) TextView m_recoreDate = null;            //记录时间
	@Bind (R.id.status_tv)                      TextView m_status     = null;            //状态

	//logical
	private View             m_view   = null;
	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	private final String INFO_ASSAY_DETECTION_STATUS_NORMAL = DGlobal.GetInstance().getAppContext().getString(R.string
																													  .assay_detection_status_normal);

	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}


	public void initContent(ArrayList<DAssayDetection> assayDetections, int position)
	{
		if (assayDetections == null || assayDetections.isEmpty())
		{
			TipsDialog.GetInstance().setMsg("assayDetections == null");
			TipsDialog.GetInstance().show();
			return;
		}

		if (position >= assayDetections.size())
		{
			TipsDialog.GetInstance().setMsg("position >= assayDetections.size()[position:=" + position + "][assayDetections.size():=" +
													assayDetections.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return;
		}

		DAssayDetection assayDetection = assayDetections.get(position);

		Calendar recordCalendar    = assayDetection.getRecordCalendar();
		String   strRecoreCalendar = m_allSDF.format(recordCalendar.getTime());
		m_recoreDate.setText(strRecoreCalendar);

		m_status.setText(INFO_ASSAY_DETECTION_STATUS_NORMAL);

	}

}