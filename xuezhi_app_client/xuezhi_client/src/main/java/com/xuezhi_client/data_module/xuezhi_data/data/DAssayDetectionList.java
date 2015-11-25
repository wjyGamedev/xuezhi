/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.data_module.xuezhi_data.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.xuezhi_data.data;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.module.util.logcal.LogicalUtil;
import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.net.config.AssayDetectionConfig;
import com.xuezhi_client.net.config.ProtocalConfig;
import com.xuzhi_client.xuzhi_app_client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class DAssayDetectionList
{
	private       int                                                        m_Status                    = ProtocalConfig.HTTP_OK;
	private       ArrayList<DAssayDetection>                                 m_assayDetections           = new ArrayList<>();
	private final String                                                     NET_ERROR_JSON_SERILIZATION = DGlobal.GetInstance()
																												  .getAppContext()
																												  .getString(
			R.string.net_error_json_serilization
																																						  );
	//辅助容器	<枚举索引，<有效数据的序列>>
	private       HashMap<EnumConfig.AssayDetectionType, ArrayList<Integer>> mAssayValidDetectionHashMap = new HashMap<>();

	public DAssayDetectionList()
	{
		init();
	}

	private void init()
	{
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.TG, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.TCHO, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.LOLC, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.HDLC, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.ATL, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.AST, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.CK, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.GLU, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.HBA1C, new ArrayList<Integer>());
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.SCR, new ArrayList<Integer>());
	}

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		//01. 清空原来容器
		if (m_assayDetections != null && m_assayDetections.size() != 0)
		{
			m_assayDetections.clear();
		}

		//02. http is ok
		m_Status = response.getInt(ProtocalConfig.HTTP_STATUS);

		if (!LogicalUtil.IsHttpSuccess(m_Status))
		{
			String errorMsg = response.getString(ProtocalConfig.HTTP_ERROR_MSG);
			throw new JsonSerializationException(errorMsg);
		}

		//03. 序列化json
		JSONArray jsonArray = response.getJSONArray(AssayDetectionConfig.LIST);
		if (jsonArray == null)
		{
			throw new JsonSerializationException(NET_ERROR_JSON_SERILIZATION + ":" + AssayDetectionConfig.LIST);
		}

		JSONObject      jsonObject     = null;
		DAssayDetection assayDetection = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject = (JSONObject)jsonArray.get(index);
			assayDetection = new DAssayDetection();
			assayDetection.serialization(jsonObject);

			m_assayDetections.add(assayDetection);
		}

		postHandler();
		return true;

	}

	private void postHandler()
	{
		int                dataSize                 = m_assayDetections.size();
		ArrayList<Integer> tgValidIndexArraylist    = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.TG);
		ArrayList<Integer> tchoValidIndexArraylist  = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.TCHO);
		ArrayList<Integer> lolcValidIndexArraylist  = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.LOLC);
		ArrayList<Integer> hdlcValidIndexArraylist  = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.HDLC);
		ArrayList<Integer> atlValidIndexArraylist   = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.ATL);
		ArrayList<Integer> astValidIndexArraylist   = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.AST);
		ArrayList<Integer> ckValidIndexArraylist    = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.CK);
		ArrayList<Integer> gluValidIndexArraylist   = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.GLU);
		ArrayList<Integer> hba1cValidIndexArraylist = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.HBA1C);
		ArrayList<Integer> scrValidIndexArraylist   = mAssayValidDetectionHashMap.get(EnumConfig.AssayDetectionType.SCR);
		for (int i = 0; i < dataSize; i++)
		{
			if (m_assayDetections.get(i).getTgValue() != 0)
			{
				tgValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getTchoValue() != 0)
			{
				tchoValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getLolcValue() != 0)
			{
				lolcValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getHdlcValue() != 0)
			{
				hdlcValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getAtlValue() != 0)
			{
				atlValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getAstValue() != 0)
			{
				astValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getCkValue() != 0)
			{
				ckValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getGluValue() != 0)
			{
				gluValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getHba1cValue() != 0)
			{
				hba1cValidIndexArraylist.add(i);
			}
			if (m_assayDetections.get(i).getScrValue() != 0)
			{
				scrValidIndexArraylist.add(i);
			}
		}
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.TG, tgValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.TCHO, tchoValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.LOLC, lolcValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.HDLC, hdlcValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.ATL, atlValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.AST, astValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.CK, ckValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.GLU, gluValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.HBA1C, hba1cValidIndexArraylist);
		mAssayValidDetectionHashMap.put(EnumConfig.AssayDetectionType.SCR, scrValidIndexArraylist);
	}

	public ArrayList<DAssayDetection> getAssayDetections()
	{
		return m_assayDetections;
	}

	public DAssayDetection getAssayDetectionByID(int id)
	{
		for (DAssayDetection assayDetection : m_assayDetections)
		{
			if (assayDetection.getID() == id)
				return assayDetection;
		}
		return null;
	}

	public HashMap<EnumConfig.AssayDetectionType, ArrayList<Integer>> getAssayValidDetectionHashMap()
	{
		return mAssayValidDetectionHashMap;
	}
}
