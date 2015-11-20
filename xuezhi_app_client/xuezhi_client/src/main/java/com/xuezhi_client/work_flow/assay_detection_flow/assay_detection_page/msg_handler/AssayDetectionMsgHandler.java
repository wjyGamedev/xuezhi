package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAssayDetectionAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAssayDetectionGetListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAssayDetectionAddEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAssayDetectionGetListEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.AssayDetectionHistoryInfoActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.AssayDetectionActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.ShenghuaFragment;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.XuezhiFragment;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;
import com.xuzhi_client.xuzhi_app_client.R;

/**
 * Created by Administrator on 2015/9/23.
 */
public class AssayDetectionMsgHandler extends BaseUIMsgHandler
{
	@Override
	protected void init()
	{
		super.init();
		m_eventBus.register(this);
	}

	public AssayDetectionMsgHandler(AssayDetectionActivity activity)
	{
		super(activity);
	}

	//01. 跳转到化验检查历史信息页面。
	public void go2AssayDetectionHistoryPage()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		activity.startActivity(new Intent(activity, AssayDetectionHistoryInfoActivity.class));
		return;
	}

	//02. 发送化验检查历史信息
	public void requestAddAssayDetectionInfoAction()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;

		RequestAssayDetectionAddEvent event = new RequestAssayDetectionAddEvent();
		event.setUID(DAccount.GetInstance().getId());

		String tmpTgValue   = activity.getTgValue();
		String tmpTchoValue = activity.getTchoValue();
		String tmpLolcValue = activity.getLolcValue();
		String tmpHdlcValue = activity.getHdlcValue();

		String tmpAtlValue = activity.getAtlValue();
		String tmpAstValue = activity.getAstValue();
		String tmpCkValue = activity.getCkValue();
		String tmpGlucValue = activity.getGlucValue();
		String tmpHba1cValue = activity.getHba1cValue();
		String tmpScrValue = activity.getScrValue();
		try
		{
			double tgValue = 0;
			if (!TextUtils.isEmpty(tmpTgValue))
			{
				tgValue = Double.valueOf(tmpTgValue);
			}
			event.setTgValue(tgValue);

			double tchoValue = 0;
			if (!TextUtils.isEmpty(tmpTchoValue))
			{
				tchoValue = Double.valueOf(tmpTchoValue);
			}
			event.setTchoValue(tchoValue);

			double lolcValue = 0;
			if (!TextUtils.isEmpty(tmpLolcValue))
			{
				lolcValue = Double.valueOf(tmpLolcValue);
			}
			event.setLolcValue(lolcValue);

			double hdlcValue = 0;
			if (!TextUtils.isEmpty(tmpHdlcValue))
			{
				hdlcValue = Double.valueOf(tmpHdlcValue);
			}
			event.setHdlcValue(hdlcValue);

			double atlValue = 0;
			if (!TextUtils.isEmpty(tmpAtlValue))
			{
				atlValue = Double.valueOf(tmpAtlValue);
			}
			event.setAtlValue(atlValue);

			double astValue = 0;
			if (!TextUtils.isEmpty(tmpAstValue))
			{
				astValue = Double.valueOf(tmpAstValue);
			}
			event.setAstValue(astValue);

			double ckValue = 0;
			if (!TextUtils.isEmpty(tmpCkValue))
			{
				ckValue = Double.valueOf(tmpCkValue);
			}
			event.setCkValue(ckValue);

			double glucValue = 0;
			if (!TextUtils.isEmpty(tmpGlucValue))
			{
				glucValue = Double.valueOf(tmpGlucValue);
			}
			event.setGluValue(glucValue);

			double hba1cValue = 0;
			if (!TextUtils.isEmpty(tmpHba1cValue))
			{
				hba1cValue = Double.valueOf(tmpHba1cValue);
			}
			event.setHba1cValue(hba1cValue);

			double srcValue = 0;
			if (!TextUtils.isEmpty(tmpScrValue))
			{
				srcValue = Double.valueOf(tmpScrValue);
			}
			event.setScrValue(srcValue);

		}
		catch (NumberFormatException e)
		{
			activity.popErrorDialog(e.toString());
			return;
		}

		DBusinessMsgHandler.GetInstance().requestAssayDetectionAddAction(event);

		return;
	}

	//03. 对于添加化验检查消息成功的处理
	public void onEventMainThread(AnswerAssayDetectionAddEvent event)
	{
		//01. 请求化验检查列表，来更新本地数据容器
		RequestAssayDetectionGetListEvent requestAssayDetectionGetListEvent = new RequestAssayDetectionGetListEvent();
		requestAssayDetectionGetListEvent.setUID(DAccount.GetInstance().getId());
		DBusinessMsgHandler.GetInstance().requestAssayDetectionGetListAction(requestAssayDetectionGetListEvent);

		//02. tips 成功
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		activity.popSaveSuccessDialog();

		return;
	}

	//04. 化验列表的返回
	public void onEventMainThread(AnswerAssayDetectionGetListEvent event)
	{
		//关闭等待dialog
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		activity.dismissWaitDialog();
		return;
	}

	//05. 跳转到主页面
	public void go2MainPage()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		activity.startActivity(new Intent(activity, MainActivity.class));
		return;
	}


	public void go2XuezhiFragment()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(XuezhiFragment.class.getName());
		XuezhiFragment xuezhiFragment = null;
		if (fragment != null)
		{
			xuezhiFragment = (XuezhiFragment)fragment;
		}
		if (xuezhiFragment == null)
		{
			xuezhiFragment = new XuezhiFragment();

			String tmpValue = activity.getTgValue();
			if (!TextUtils.isEmpty(tmpValue))
				xuezhiFragment.setTgValue(tmpValue);

			tmpValue = activity.getTchoValue();
			if (!TextUtils.isEmpty(tmpValue))
				xuezhiFragment.setTchoValue(tmpValue);

			tmpValue = activity.getLolcValue();
			if (!TextUtils.isEmpty(tmpValue))
				xuezhiFragment.setLolcValue(tmpValue);

			tmpValue = activity.getHdlcValue();
			if (!TextUtils.isEmpty(tmpValue))
				xuezhiFragment.setHdlcValue(tmpValue);

		}

		FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_display_region_ll, xuezhiFragment, XuezhiFragment.class.getName());
		fragmentTransaction.commit();

	}

	public void go2ShenghuaFragment()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(ShenghuaFragment.class.getName());
		ShenghuaFragment shenghuaFragment = null;
		if (fragment != null)
		{
			shenghuaFragment = (ShenghuaFragment)fragment;
		}
		if (shenghuaFragment == null)
		{
			shenghuaFragment = new ShenghuaFragment();

			String tmpValue = activity.getAtlValue();
			if (!TextUtils.isEmpty(tmpValue))
				shenghuaFragment.setAtlValue(tmpValue);

			tmpValue = activity.getAstValue();
			if (!TextUtils.isEmpty(tmpValue))
				shenghuaFragment.setAstValue(tmpValue);

			tmpValue = activity.getCkValue();
			if (!TextUtils.isEmpty(tmpValue))
				shenghuaFragment.setCkValue(tmpValue);

			tmpValue = activity.getGlucValue();
			if (!TextUtils.isEmpty(tmpValue))
				shenghuaFragment.setGlucValue(tmpValue);

			tmpValue = activity.getHba1cValue();
			if (!TextUtils.isEmpty(tmpValue))
				shenghuaFragment.setHba1cValue(tmpValue);

			tmpValue = activity.getScrValue();
			if (!TextUtils.isEmpty(tmpValue))
				shenghuaFragment.setScrValue(tmpValue);

		}

		FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_display_region_ll, shenghuaFragment, ShenghuaFragment.class.getName());
		fragmentTransaction.commit();

	}

	public void loadXuezhiData()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(XuezhiFragment.class.getName());
		if (fragment == null)
			return;

		XuezhiFragment xuezhiFragment = (XuezhiFragment)fragment;
		if (xuezhiFragment == null)
			return;

		String tmpValue = xuezhiFragment.getTgValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setTgValue(tmpValue);

		tmpValue = xuezhiFragment.getTchoValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setTchoValue(tmpValue);

		tmpValue = xuezhiFragment.getLolcValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setLolcValue(tmpValue);

		tmpValue = xuezhiFragment.getHdlcValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setHdlcValue(tmpValue);

		return;
	}

	public void loadShenghuaData()
	{
		AssayDetectionActivity activity = (AssayDetectionActivity)m_context;
		Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(ShenghuaFragment.class.getName());
		if (fragment == null)
			return;

		ShenghuaFragment shenghuaFragment = (ShenghuaFragment)fragment;
		if (shenghuaFragment == null)
			return;

		String tmpValue = shenghuaFragment.getAtlValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setAtlValue(tmpValue);

		tmpValue = shenghuaFragment.getAstValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setAstValue(tmpValue);

		tmpValue = shenghuaFragment.getCkValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setCkValue(tmpValue);

		tmpValue = shenghuaFragment.getGlucValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setGlucValue(tmpValue);

		tmpValue = shenghuaFragment.getHba1cValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setHba1cValue(tmpValue);

		tmpValue = shenghuaFragment.getScrValue();
		if (!TextUtils.isEmpty(tmpValue))
			activity.setScrValue(tmpValue);

	}
}
