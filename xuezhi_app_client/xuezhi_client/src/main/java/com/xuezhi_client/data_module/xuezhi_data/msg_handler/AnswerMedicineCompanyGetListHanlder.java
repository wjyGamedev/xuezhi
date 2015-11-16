/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.Event.net.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/9		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.msg_handler;


import com.module.net.IResponseListener;
import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.data_module.xuezhi_data.data.DBusinessData;
import com.xuezhi_client.data_module.xuezhi_data.data.DMedicineCompanyList;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class AnswerMedicineCompanyGetListHanlder extends IResponseListener
{
	@Override
	public void onResponse(JSONObject response)
	{
		try
		{
			DMedicineCompanyList medicineCompanyList = DBusinessData.GetInstance().getMedicineCompanyList();
			medicineCompanyList.serialization(response);
		}
		catch (JSONException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}
		catch (ParseException e)
		{
			TipsDialog.GetInstance().setMsg(e.toString());
			TipsDialog.GetInstance().show();
			return;
		}

		//解析成功，发送event
		DBusinessMsgHandler.GetInstance().answerMedicineBoxGetListAction();
		return;
	}
}
