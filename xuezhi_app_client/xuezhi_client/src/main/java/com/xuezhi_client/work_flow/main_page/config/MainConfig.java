/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.main_page.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/27		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.config;

import com.module.data.DGlobal;
import com.xuzhi_client.xuzhi_app_client.R;

public class MainConfig
{
	public enum TodayReminderState
	{
		NONE(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.today_remind_title_no_tips), 1),
		EXIST(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.today_remind_title_text), 2),
		FINISHED(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.today_remind_title_finished_tips), 3);

		private String m_name = null;
		private int    m_id   = 1;

		private TodayReminderState(String name, int id)
		{
			m_name = name;
			m_id = id;
		}

		public String getName()
		{
			return m_name;
		}

		public int getId()
		{
			return m_id;
		}

		@Override
		public String toString()
		{
			return (m_name + ":" + m_id);
		}

		public static TodayReminderState valueOf(int id)
		{
			switch (id)
			{
			case 1:
				return NONE;
			case 2:
				return EXIST;
			case 3:
				return FINISHED;
			default:
				return null;
			}
		}
	}

	public final static String ERROR_NOT_ENOUGH_MEDICINE_NUM = DGlobal.GetInstance().getAppContext().getString(R.string.error_not_enough_medicine_num);
	public final static String INFO_NOT_ADD = DGlobal.GetInstance().getAppContext().getString(R.string.info_not_add);
	public final static String INFO_GO_2_MEDICINE_BOX = DGlobal.GetInstance().getAppContext().getString(R.string.info_go_2_medicine_box);

}
