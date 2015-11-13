package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.msg_handler;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.data.DAssayDetectionList;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAssayDetectionGetListEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.ADHChartFragment;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.ADHListFragment;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.AssayDetectionHistoryInfoActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_item_info_page.ui.AssayDetectionHistoryItemInfoActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.AssayDetectionActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;
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
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;

		FragmentTransaction transaction = null;
		//01。 如果有list，则删除
		Fragment lineFragment = activity.getSupportFragmentManager().findFragmentByTag(ADHListFragment.class.getName());
		if (lineFragment != null)
		{
			transaction = activity.getSupportFragmentManager().beginTransaction();
			transaction.remove(lineFragment);
			transaction.commit();
		}

		//02. 打开chart
		Fragment chartFragment = activity.getSupportFragmentManager().findFragmentByTag(ADHChartFragment.class.getName());
		ADHChartFragment adhChartFragment = null;
		if (chartFragment != null)
		{
			adhChartFragment = (ADHChartFragment)chartFragment;
		}
		if (adhChartFragment == null)
		{
			adhChartFragment = new ADHChartFragment();
			adhChartFragment.setHeight(activity.getHistoryRegionFLHeight());
		}

		//TODO:这里不能把ADHChartFragment挂接在R.id.history_region_fl子节点下，不然会显示不出来。
		transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(android.R.id.content, adhChartFragment, ADHChartFragment.class.getName());
		transaction.commitAllowingStateLoss();
		return;
	}

	//02. 列表显示
	public void go2ListFragment()
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;

		FragmentTransaction transaction = null;

		//01. 如果有chart先删除
		Fragment chartFragment = activity.getSupportFragmentManager().findFragmentByTag(ADHChartFragment.class.getName());
		if (chartFragment != null)
		{
			transaction = activity.getSupportFragmentManager().beginTransaction();
			transaction.remove(chartFragment);
			transaction.commit();
		}

		//02. 打开line
		Fragment listFragment = activity.getSupportFragmentManager().findFragmentByTag(ADHListFragment.class.getName());
		ADHListFragment adhListFragment = null;
		if (listFragment != null)
		{
			adhListFragment = (ADHListFragment)listFragment;
		}
		if (adhListFragment == null)
		{
			adhListFragment = new ADHListFragment();
		}

		transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.history_region_fl, adhListFragment, ADHListFragment.class.getName());
		transaction.commit();

		return;

	}

	//03. 化验列表的返回
	public void onEventMainThread(AnswerAssayDetectionGetListEvent event)
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;
		FragmentManager fgManager           = activity.getSupportFragmentManager();
		Fragment fragment            = fgManager.findFragmentByTag(ADHListFragment.class.getName());
		if (fragment == null)
		{
			return;
		}

		ADHListFragment ADHListFragment = (ADHListFragment) fragment;
		if (ADHListFragment == null)
		{
			activity.popErrorDialog("ADHListFragment == null");
			return;
		}

		//同步更新
		ADHListFragment.updateContent();
		return;
	}

	//04. 回到主页面
	public void go2MainPage()
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}

	//05. 获取化验检查列表
	public DAssayDetectionList getAssayDetectionList()
	{
		return DBusinessData.GetInstance().getAssayDetectionList();
	}

	public void go2AssayDetectionItemInfoPage(int id)
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;

		Intent intent = new Intent(activity, AssayDetectionHistoryItemInfoActivity.class);
		intent.putExtra(AssayDetectionConfig.SELECTED_ITEM_ID, id);
		activity.startActivity(intent);

		return;
	}

	public void backAction()
	{
		AssayDetectionHistoryInfoActivity activity = (AssayDetectionHistoryInfoActivity)m_context;
		activity.startActivity(new Intent( activity, AssayDetectionActivity.class));
		activity.finish();
	}

}
