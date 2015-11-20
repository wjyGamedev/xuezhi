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
import butterknife.OnClick;

public class ADHListAdapter extends IBaseAdapter
{
	private DAssayDetectionList        m_assayDetectionList = null;
	private LayoutInflater             m_layoutInflater     = null;
	private ArrayList<DAssayDetection> m_assayDetections    = new ArrayList<>();

	@Override
	public int getCount()
	{
		m_assayDetectionList = DBusinessData.GetInstance().getAssayDetectionList();

		m_assayDetections = m_assayDetectionList.getAssayDetections();
		if (m_assayDetections == null || m_assayDetections.isEmpty())
			return 0;

		return m_assayDetections.size();
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

		return position;
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

		if (!m_assayDetections.isEmpty())
		{
			int index = (int)getItemId(position);
			viewHolder.initContent(m_assayDetections.get(index));
		}
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
	@Bind (R.id.assay_detection_record_date_tv) TextView m_recordDate = null;            //记录时间

	//logical
	private View             m_view   = null;
	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	private final String INFO_ASSAY_DETECTION_STATUS_NORMAL = DGlobal.GetInstance().getAppContext().getString(R.string
																													  .assay_detection_status_normal);
	private DAssayDetection mAssayDetection = null;

	@OnClick(R.id.click_region_ll)
	public void clickRegionLL(View v)
	{
		AssayDetectionHistoryInfoActivity acitvity = (AssayDetectionHistoryInfoActivity)m_view.getContext();
		acitvity.getAssayDetectionHistoryInfoMsgHandler().go2AssayDetectionItemInfoPage(mAssayDetection.getID());
	}


	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}


	public void initContent(DAssayDetection assayDetection)
	{
		mAssayDetection = assayDetection;
		if (mAssayDetection == null)
		{
			return;
		}

		Calendar recordCalendar    = mAssayDetection.getRecordCalendar();
		String   strRecoreCalendar = m_allSDF.format(recordCalendar.getTime());
		m_recordDate.setText(strRecoreCalendar);

	}

}