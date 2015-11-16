/**
 * Copyright (c) 213Team
 *
 * @className : com.third.part.xinge_tengxun.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/5		WangJY		1.0.0		create
 */

package com.third.part.xinge_tengxun;

import android.content.Context;
import android.util.Log;

import com.module.data.DGlobal;
import com.module.util.timer.TimerTaskWrapper;
import com.module.widget.dialog.TipsDialog;
import com.tencent.android.tpush.XGLocalMessage;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.xuezhi_client.config.DateConfig;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicine;
import com.xuezhi_client.work_flow.main_page.data.DMedicineReminder;
import com.xuezhi_client.work_flow.main_page.data.DTakeMedicineReminder;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuzhi_client.xuzhi_app_client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class XinGe
{
	private static XinGe s_xinGe = new XinGe();

	private Context m_context                    = null;
	private String  INFO_TAKE_MEDICINE_TITLE     = DGlobal.GetInstance().getAppContext().getString(R.string
																										   .xinge_noticifation_take_medicine_title);
	private String  INFO_TAKE_BUY_MEDICINE_TITLE = DGlobal.GetInstance().getAppContext().getString(R.string
																										   .xinge_noticifation_buy_medicine_title);

	private String INFO_NEW_LINE   = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_new_line);
	private String INFO_TIME       = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_time);
	private String INFO_NAME       = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_medicine_name);
	private String INFO_DOSE       = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_dose);
	private String INFO_PRECAUTION = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_precaution);
	private String INFO_WILL_BE    = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_will_be);
	private String INFO_BE         = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_be);
	private String INFO_EXHAUST    = DGlobal.GetInstance().getAppContext().getString(R.string.xinge_noticifation_exhaust);


	private final int INFO_MEDICINE_HOUR    = 8;
	private final int INFO_MEDICINE_MINITUE = 0;

	private SimpleDateFormat m_hmSDF        = new SimpleDateFormat(DateConfig.PATTERN_DATE_HOUR_MINUTE);
	private SimpleDateFormat m_ymdSDFNoLine = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_NO_LINE);
	private SimpleDateFormat m_ymdSDF       = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY);

	private ArrayList<DTakeMedicineReminder> m_takeMedicineReminders = new ArrayList<>();
	private ArrayList<DMedicineReminder>     m_medicineReminders     = new ArrayList<>();


	private long                    DELAY_TIME_MILLISENCENDS  = 5000;
	private boolean                 m_firstTakeMedicineFlag   = true;
	private TimerTaskWrapper        m_takeMedicineTask        = new TimerTaskWrapper();
	private TakeMedicineTaskHandler m_takeMedicineTaskHandler = new TakeMedicineTaskHandler();

	private boolean             m_firstMedicineFlag   = true;
	private TimerTaskWrapper    m_medicineTask        = new TimerTaskWrapper();
	private MedicineTaskHandler m_medicineTaskHandler = new MedicineTaskHandler();


	private XinGe() {}

	public static XinGe GetInstance()
	{
		return s_xinGe;
	}

	class TakeMedicineTaskHandler implements TimerTaskWrapper.TimerTaskListener
	{
		@Override
		public void execAction()
		{
			MainActivity activity = (MainActivity)m_context;
			if (activity == null)
				return;

			ArrayList<DTakeMedicineReminder> takeMedicineReminders = activity.getMainMsgHandler().getWaitForRemainder()
																			 .getTakeMedicineReminders();
			addLocalTakeMedicineNotification(takeMedicineReminders);
			return;
		}
	}

	class MedicineTaskHandler implements TimerTaskWrapper.TimerTaskListener
	{
		@Override
		public void execAction()
		{
			MainActivity activity = (MainActivity)m_context;
			if (activity == null)
				return;

			ArrayList<DMedicineReminder> medicineReminders = activity.getMainMsgHandler().getWaitForRemainder().getMedicineReminders();
			addLocalMedicineNotification(medicineReminders);
			return;
		}
	}

	public void init()
	{
		XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback()
											 {

												 @Override
												 public void handleNotify(XGNotifaction xGNotifaction)
												 {
													 Log.i("test", "处理信鸽通知：" + xGNotifaction);
													 // 获取标签、内容、自定义内容
													 String title         = xGNotifaction.getTitle();
													 String content       = xGNotifaction.getContent();
													 String customContent = xGNotifaction.getCustomContent();
													 // 其它的处理
													 // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
													 xGNotifaction.doNotify();
												 }
											 }
											);
	}

	public void initMainContent(Context context)
	{
		XGPushManager.registerPush(DGlobal.GetInstance().getAppContext());

		if (context == null)
		{
			TipsDialog.GetInstance().setMsg("m_context == null");
			TipsDialog.GetInstance().show();
			return;
		}
		m_context = context;

		m_takeMedicineTask.setTimerTaskListener(m_takeMedicineTaskHandler);
		m_medicineTask.setTimerTaskListener(m_medicineTaskHandler);
	}

	private DTakeMedicineReminder findTakeMedicineByPID(int pid)
	{
		if (m_takeMedicineReminders.isEmpty())
			return null;

		for (DTakeMedicineReminder srcTakeMedicineReminder : m_takeMedicineReminders)
		{
			if (pid == srcTakeMedicineReminder.getPID())
			{
				return srcTakeMedicineReminder;
			}
		}

		return null;
	}

	public void readyAddLocalTakeMedicineNotification(ArrayList<DTakeMedicineReminder> takeMedicineReminders)
	{
		if (m_firstTakeMedicineFlag)
		{
			m_firstTakeMedicineFlag = false;
			m_takeMedicineTask.schedule(DELAY_TIME_MILLISENCENDS);
		}
		else
		{
			addLocalTakeMedicineNotification(takeMedicineReminders);
		}
	}

	public void addLocalTakeMedicineNotification(ArrayList<DTakeMedicineReminder> takeMedicineReminders)
	{
		if (m_context == null)
		{
			return;
		}

		XGPushManager.clearLocalNotifications(m_context);
		Calendar today = Calendar.getInstance();
		for (DTakeMedicineReminder takeMedicineReminder : takeMedicineReminders)
		{
			XGLocalMessage local_msg = new XGLocalMessage();
			// 设置本地消息类型，1:通知，2:消息
			local_msg.setType(1);
			// 设置消息标题
			String title = INFO_TAKE_MEDICINE_TITLE;

			int MID = takeMedicineReminder.getMID();
			DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(MID);
			if (medicine != null)
			{
				title = title + medicine.getName();
			}
			local_msg.setTitle(title);

			// 设置消息内容
			Calendar notificationToday = Calendar.getInstance();
			Calendar reminderTime = takeMedicineReminder.getReminderTime();
			notificationToday.set(Calendar.HOUR_OF_DAY, reminderTime.get(Calendar.HOUR_OF_DAY));
			notificationToday.set(Calendar.MINUTE, reminderTime.get(Calendar.MINUTE));

			//如果该通知已经通知了，即当前时间大于通知时间。
			if (today.get(Calendar.HOUR_OF_DAY) > notificationToday.get(Calendar.HOUR_OF_DAY))
			{
				continue;
			}

			if (today.get(Calendar.HOUR_OF_DAY) == notificationToday.get(Calendar.HOUR_OF_DAY) && today.get(Calendar.MINUTE) >
					notificationToday.get(
					Calendar.MINUTE
																																					))
			{
				continue;
			}

			String time = INFO_TIME;

			time = time + m_hmSDF.format(notificationToday.getTime()) + INFO_NEW_LINE;

			String name = INFO_NAME;
			name = name + medicine.getName() + INFO_NEW_LINE;

			String dose = INFO_DOSE;
			if (medicine != null)
			{
				dose = dose + String.valueOf(medicine.getDose()) + INFO_NEW_LINE;
			}

			String precaution = INFO_PRECAUTION;
			if (medicine != null)
			{
				precaution = precaution + medicine.getPrecautions() + INFO_NEW_LINE;
			}

			String content = time + name + dose + precaution;
			local_msg.setContent(content);
			// 设置消息日期，格式为：20140502
			String calendarDispaly = m_ymdSDFNoLine.format(notificationToday.getTime());
			local_msg.setDate(calendarDispaly);
			// 设置消息触发的小时(24小时制)，例如：22代表晚上10点
			String hour = String.valueOf(notificationToday.get(Calendar.HOUR_OF_DAY));
			local_msg.setHour(hour);
			// 获取消息触发的分钟，例如：05代表05分
			String minute = String.valueOf(notificationToday.get(Calendar.MINUTE));
			local_msg.setMin(minute);
			// 设置消息样式，默认为0或不设置
			// local_msg.setBuilderId(6);
			// 设置拉起应用页面
			// local_msg.setActivity("com.qq.xgdemo.SettingActivity");
			// 设置动作类型：1打开activity或app本身，2打开浏览器，3打开Intent ，4通过包名打开应用
			// local_msg.setAction_type(1);
			// 设置URL
			// local_msg.setUrl("http://www.baidu.com");
			// 设置Intent
			// local_msg.setIntent("intent:10086#Intent;scheme=tel;action=android.intent.action.DIAL;S.key=value;end");
			// 自定义本地通知样式
			// local_msg.setIcon_type(0);
			// local_msg.setIcon_res("right");
			// 是否覆盖原先build_id的保存设置。1覆盖，0不覆盖
			// local_msg.setStyle_id(1);
			// 设置音频资源
			// local_msg.setRing_raw("mm");
			// 设置key,value
			// HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("key", "v1");
			// map.put("key2", "v2");
			// local_msg.setCustomContent(map);
			// 设置下载应用URL
			// local_msg.setPackageDownloadUrl("http://softfile.3g.qq.com:8080/msoft/179/1105/10753/MobileQQ1.0(Android)_Build0198.apk");
			// 设置要打开的应用包名
			// local_msg.setPackageName("com.example.com.qq.feedback");
			XGPushManager.addLocalNotification(m_context, local_msg);
		}
	}

	private DMedicineReminder findMedicineByMBID(int mbid)
	{
		if (m_medicineReminders.isEmpty())
			return null;

		for (DMedicineReminder medicineReminder : m_medicineReminders)
		{
			if (mbid == medicineReminder.getMBID())
			{
				return medicineReminder;
			}
		}

		return null;
	}

	public void readyAddLocalMedicineNotification(ArrayList<DMedicineReminder> medicineReminders)
	{
		if (m_firstMedicineFlag)
		{
			m_firstMedicineFlag = false;
			m_medicineTask.schedule(DELAY_TIME_MILLISENCENDS);
		}
		else
		{
			addLocalMedicineNotification(medicineReminders);
		}
	}

	public void addLocalMedicineNotification(ArrayList<DMedicineReminder> medicineReminders)
	{
		if (m_context == null)
		{
			return;
		}

		boolean bOverTime = false;
		XGPushManager.clearLocalNotifications(m_context);
		for (DMedicineReminder medicineReminder : medicineReminders)
		{
			XGLocalMessage local_msg = new XGLocalMessage();
			// 设置本地消息类型，1:通知，2:消息
			local_msg.setType(1);
			// 设置消息标题
			String title = INFO_TAKE_BUY_MEDICINE_TITLE;

			int MID = medicineReminder.getMID();
			DMedicine medicine = DBusinessData.GetInstance().getMedicineList().getMedicineByID(MID);
			if (medicine != null)
			{
				title = title + medicine.getName();
			}
			local_msg.setTitle(title);

			// 设置消息内容
			Calendar today = Calendar.getInstance();
			int currYear = today.get(Calendar.YEAR);
			int currMonth = today.get(Calendar.MONTH);
			int currDay = today.get(Calendar.DAY_OF_MONTH);
			Calendar warnCalendar = medicineReminder.getWaringTime();
			int warnYear = warnCalendar.get(Calendar.YEAR);
			int warnMonth = warnCalendar.get(Calendar.MONTH);
			int warnDay = warnCalendar.get(Calendar.DAY_OF_MONTH);

			if (currYear > warnYear)
			{
				bOverTime = true;
			}
			else if (currYear == warnYear && currMonth > warnMonth)
			{
				bOverTime = true;
			}
			else if (currYear == warnYear &&
					currMonth == warnMonth &&
					currDay > warnDay)
			{
				bOverTime = true;
			}
			else
			{
				bOverTime = false;
			}

			String name = "";
			if (medicine != null)
			{
				name = name + medicine.getName();
			}

			String part1 = "";
			if (bOverTime)
			{
				part1 = INFO_WILL_BE;
			}
			else
			{
				part1 = INFO_BE;
			}

			String warnDisplay = m_ymdSDF.format(warnCalendar.getTime());

			String content = name + part1 + warnDisplay + INFO_EXHAUST;
			local_msg.setContent(content);
			// 设置消息日期，格式为：20140502
			String calendarDispaly = m_ymdSDFNoLine.format(today.getTime());
			local_msg.setDate(calendarDispaly);
			// 设置消息触发的小时(24小时制)，例如：22代表晚上10点
			String hour = String.valueOf(INFO_MEDICINE_HOUR);
			local_msg.setHour(hour);
			// 获取消息触发的分钟，例如：05代表05分
			String minute = String.valueOf(INFO_MEDICINE_MINITUE);
			local_msg.setMin(minute);


			//如果该通知已经通知了，即当前时间大于通知时间。
			if (today.get(Calendar.HOUR_OF_DAY) > INFO_MEDICINE_HOUR)
			{
				continue;
			}

			if (today.get(Calendar.HOUR_OF_DAY) == INFO_MEDICINE_HOUR && today.get(Calendar.MINUTE) > INFO_MEDICINE_MINITUE)
			{
				continue;
			}

			// 设置消息样式，默认为0或不设置
			// local_msg.setBuilderId(6);
			// 设置拉起应用页面
			// local_msg.setActivity("com.qq.xgdemo.SettingActivity");
			// 设置动作类型：1打开activity或app本身，2打开浏览器，3打开Intent ，4通过包名打开应用
			// local_msg.setAction_type(1);
			// 设置URL
			// local_msg.setUrl("http://www.baidu.com");
			// 设置Intent
			// local_msg.setIntent("intent:10086#Intent;scheme=tel;action=android.intent.action.DIAL;S.key=value;end");
			// 自定义本地通知样式
			// local_msg.setIcon_type(0);
			// local_msg.setIcon_res("right");
			// 是否覆盖原先build_id的保存设置。1覆盖，0不覆盖
			// local_msg.setStyle_id(1);
			// 设置音频资源
			// local_msg.setRing_raw("mm");
			// 设置key,value
			// HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("key", "v1");
			// map.put("key2", "v2");
			// local_msg.setCustomContent(map);
			// 设置下载应用URL
			// local_msg.setPackageDownloadUrl("http://softfile.3g.qq.com:8080/msoft/179/1105/10753/MobileQQ1.0(Android)_Build0198.apk");
			// 设置要打开的应用包名
			// local_msg.setPackageName("com.example.com.qq.feedback");
			XGPushManager.addLocalNotification(m_context, local_msg);
		}
	}
}
