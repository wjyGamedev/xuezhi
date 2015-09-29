package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.msg_handler;

import android.content.Intent;

import com.module.frame.BaseUIMsgHandler;
import com.xuezhi_client.data_module.register_account.data.DAccount;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAddAssayDetectionInfoEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.AnswerAssayDetectionListEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.DBusinessMsgHandler;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAddAssayDetectionInfoEvent;
import com.xuezhi_client.data_module.xuezhi_data.msg_handler.RequestAssayDetectionListEvent;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.AssayDetectionHistoryInfoActivity;
import com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_page.ui.AssayDetectionActivity;
import com.xuezhi_client.work_flow.main_page.ui.MainActivity;

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

		RequestAddAssayDetectionInfoEvent event = new RequestAddAssayDetectionInfoEvent();
		event.setUID(DAccount.GetInstance().getId());

		String tmpTgValue   = activity.getTgET().getText().toString();
		String tmpTchoValue = activity.getTchoET().getText().toString();
		String tmpLolcValue = activity.getLolcET().getText().toString();
		String tmpHdlcValue = activity.getHdlcET().getText().toString();

		String tmpAtlValue = activity.getAtlET().getText().toString();
		String tmpAstValue = activity.getAstET().getText().toString();
		String tmpCkValue = activity.getCkET().getText().toString();
		String tmpGlucValue = activity.getGluET().getText().toString();
		String tmpHba1cValue = activity.getHba1cET().getText().toString();

		try
		{
			double tgValue = Double.valueOf(tmpTgValue);
			event.setTgValue(tgValue);
			double tchoValue = Double.valueOf(tmpTchoValue);
			event.setTchoValue(tchoValue);
			double lolcValue = Double.valueOf(tmpLolcValue);
			event.setLolcValue(lolcValue);
			double hdlcValue = Double.valueOf(tmpHdlcValue);
			event.setHdlcValue(hdlcValue);

			double atlValue = Double.valueOf(tmpAtlValue);
			event.setAtlValue(atlValue);
			double astValue = Double.valueOf(tmpAstValue);
			event.setAstValue(astValue);
			double ckValue = Double.valueOf(tmpCkValue);
			event.setCkValue(ckValue);
			double glucValue = Double.valueOf(tmpGlucValue);
			event.setGluValue(glucValue);
			double hba1cValue = Double.valueOf(tmpHba1cValue);
			event.setHba1cValue(hba1cValue);

		}
		catch (NumberFormatException e)
		{
			activity.popErrorDialog(e.toString());
			return;
		}

		DBusinessMsgHandler.GetInstance().requestAddAssayDetectionInfoAction(event);

		return;
	}

	//03. 对于添加化验检查消息成功的处理
	public void onEventMainThread(AnswerAddAssayDetectionInfoEvent event)
	{
		//请求化验检查列表，来更新本地数据容器
		RequestAssayDetectionListEvent requestAssayDetectionListEvent = new RequestAssayDetectionListEvent();
		DBusinessMsgHandler.GetInstance().requestAssayDetectionListAction(requestAssayDetectionListEvent);
		return;
	}

	//04. 化验列表的返回
	public void onEventMainThread(AnswerAssayDetectionListEvent event)
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


}
