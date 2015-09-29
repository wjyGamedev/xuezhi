package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAssayDetectionListEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.AssayDetectionHistoryInfoActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.ADHListFragment;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuzhi_client.xuzhi_app_client.R;

/**
 * Created by Administrator on 2015/9/23.
 */
public class AssayDetectionHistoryInfoMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public AssayDetectionHistoryInfoMsgHandler(AssayDetectionHistoryInfoActivity activity)
	{
		super(activity);
	}

	//01. 图标显示
	public void go2ChartFragment()
	{
		return;
	}

	//02. 列表显示
	public void go2ListFragment()
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;

		ADHListFragment adhListFragment = new ADHListFragment();

		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		transaction.replace(R.id.history_region_fl, adhListFragment);
		transaction.commit();

		return;

	}

	//03. 化验列表的返回
	public void onEventMainThread(AnswerAssayDetectionListEvent event)
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;
		FragmentManager fgManager           = activity.getFragmentManager();
		Fragment        fragment            = fgManager.findFragmentByTag(ADHListFragment.class.getName());
		if (fragment == null)
		{
			return;
		}

		ADHListFragment adhListFragment = (ADHListFragment) fragment;
		if (adhListFragment == null)
		{
			activity.popErrorDialog("adhListFragment == null");
			return;
		}

		//同步更新
		adhListFragment.updateContent();
		return;
	}

	//04. 回到主页面
	public void go2MainPage()
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}
}
