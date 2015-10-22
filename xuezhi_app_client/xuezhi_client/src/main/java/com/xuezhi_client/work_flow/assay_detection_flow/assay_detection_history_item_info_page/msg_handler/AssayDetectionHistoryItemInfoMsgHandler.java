/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_item_info_page.msg_handler.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/21		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_item_info_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.AssayDetectionHistoryInfoActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_item_info_page.ui.AssayDetectionHistoryItemInfoActivity;

public class AssayDetectionHistoryItemInfoMsgHandler extends BaseUIMsgHandler
{
	public AssayDetectionHistoryItemInfoMsgHandler(AssayDetectionHistoryItemInfoActivity context)
	{
		super(context);
	}

	public void backAction()
	{
		AssayDetectionHistoryItemInfoActivity activity = (AssayDetectionHistoryItemInfoActivity)m_context;
		activity.startActivity(new Intent( activity, AssayDetectionHistoryInfoActivity.class));
		activity.finish();
	}
}
