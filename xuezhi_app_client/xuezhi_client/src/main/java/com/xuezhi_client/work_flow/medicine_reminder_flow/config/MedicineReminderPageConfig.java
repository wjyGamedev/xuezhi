/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medication_reminder_flow.medication_reminder_page.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/20		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medicine_reminder_flow.config;

import com.module.data.DGlobal;
import com.xuzhi_client.xuzhi_app_client.R;

public class MedicineReminderPageConfig
{
	public static final String SELECTED_MEDICINE_REMINDER_ID               = "selected_medicine_reminder_id";
	public static final int    WAIT_TIME_FOR_DELETE_MEDICINE_REMINDER_ITEM = 5000;
	public static final String OPEN                                        = DGlobal.GetInstance().getAppContext().getString(R.string
																																	 .medicine_reminder_add_open_content);
	public static final String CLOSE                                       = DGlobal.GetInstance().getAppContext().getString(R.string
																																	 .medicine_reminder_add_close_content);
	public static final String ERROR_INPUT_REMINDER_TIME                   = DGlobal.GetInstance().getAppContext().getString(R.string
																																	 .medicine_reminder_add_error_input_reminder_time);
	public static final String ERROR_INPUT_MEDICINE_NAME                   = DGlobal.GetInstance().getAppContext().getString(R.string
																																	 .medicine_reminder_add_error_input_medicine_name_time);
	public static final String ERROR_INPUT_ROSE                            = DGlobal.GetInstance().getAppContext().getString(R.string
																																	 .medicine_reminder_add_error_input_rose_time);

	public static final String ERROR_INPUT_ROSE_ZERO                            = DGlobal.GetInstance().getAppContext().getString(R.string
																																	 .medicine_reminder_set_error_rose_equal_0);

	public static final String INFO_SET_SUCCESS                            = DGlobal.GetInstance().getAppContext().getString(R.string
																																		  .medicine_reminder_set_success);

	public static final String ERROR_NO_CHANGE = DGlobal.GetInstance().getAppContext().getString(R.string
																										 .medicine_reminder_set_error_no_change);

	public static final String INFO_GO_BACK = DGlobal.GetInstance().getAppContext().getString(R.string
																										 .medicine_reminder_set_go_back);


	public static final String INFO_CONTINUE = DGlobal.GetInstance().getAppContext().getString(R.string
																										 .medicine_reminder_set_continue);
}

