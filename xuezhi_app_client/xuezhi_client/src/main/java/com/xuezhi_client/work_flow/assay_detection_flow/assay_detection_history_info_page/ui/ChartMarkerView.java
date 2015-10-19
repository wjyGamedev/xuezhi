/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/13		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.xuzhi_client.xuzhi_app_client.R;

public class ChartMarkerView extends MarkerView
{
	private TextView tvContent;

	public ChartMarkerView(Context context, int layoutResource)
	{
		super(context, layoutResource);

		tvContent = (TextView)findViewById(R.id.tvContent);
	}

	// callbacks everytime the MarkerView is redrawn, can be used to update the
	// content (user-interface)
	@Override
	public void refreshContent(Entry e, Highlight highlight)
	{

		if (e instanceof CandleEntry)
		{

			CandleEntry ce = (CandleEntry)e;

			tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
		}
		else
		{
			Object tmpDate = e.getData();
			if (tmpDate instanceof String)
			{
				String displayDate = (String)tmpDate;
				displayDate = "("+displayDate+")";
				tvContent.setText(String.valueOf(e.getVal()) + displayDate);
			}
			else
			{
				tvContent.setText(String.valueOf(e.getVal()));
			}
		}
	}

	@Override
	public int getXOffset()
	{
		// this will center the marker-view horizontally
		return -(getWidth() / 2);
	}

	@Override
	public int getYOffset()
	{
		// this will cause the marker-view to be above the selected value
		return -getHeight();
	}
}
