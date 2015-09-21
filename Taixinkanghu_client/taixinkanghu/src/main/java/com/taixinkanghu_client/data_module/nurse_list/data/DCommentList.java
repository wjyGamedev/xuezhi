/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.data;

import com.module.data.DGlobal;
import com.module.exception.RuntimeExceptions.net.JsonSerializationException;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.net.config.NurseSeniorListConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class DCommentList
{
	private ArrayList<DComment> m_commentArrayList = new ArrayList<>();
	private double                  m_commentRate   = 0.0f;

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		//01. clear up
		if (m_commentArrayList != null && m_commentArrayList.isEmpty() == false)
		{
			m_commentArrayList.clear();
		}

		//02. comment list
		JSONArray jsonArray = response.getJSONArray(NurseSeniorListConfig.COMMENT_LIST);
		if (jsonArray == null)
		{
			String errMsg = DGlobal.GetInstance().getAppContext().getString(R.string.net_error_json_serilization);
			throw new JsonSerializationException(errMsg + ":" + NurseSeniorListConfig.COMMENT_LIST);
		}

		JSONObject   jsonObject   = null;
		DComment dComment = null;
		for (int index = 0; index < jsonArray.length(); index++)
		{
			jsonObject = (JSONObject)jsonArray.get(index);
			dComment = new DComment();
			dComment.serialization(jsonObject);

			m_commentArrayList.add(dComment);
		}

		//03. calculate m_commentRate
		// very_satisfied + satisfied / all
		int goodNum = 0;
		for (DComment commentEle : m_commentArrayList)
		{
			if (commentEle.getCommentLevel() == EnumConfig.CommentLevel.COMMENT_LEVEL_VERY_STATISFIED	||
					commentEle.getCommentLevel() == EnumConfig.CommentLevel.COMMENT_LEVEL_STATISFIED)
			{
				goodNum++;
			}
		}

		if (m_commentArrayList.isEmpty())
		{
			m_commentRate = 0;
		}
		else
		{
			m_commentRate = ((double)goodNum)/m_commentArrayList.size();
		}
		return true;
	}

	public void copyfrom(DCommentList targetCommentList) throws JSONException
	{
		m_commentArrayList.clear();

		DComment tmpComment = null;
		for (DComment commentELe : targetCommentList.getCommentArrayList())
		{
			tmpComment = new DComment();
			tmpComment.copyfrom(commentELe);
			m_commentArrayList.add(tmpComment);
		}

		m_commentRate = targetCommentList.getCommentRate();

	}


	public ArrayList<DComment> getCommentArrayList()
	{
		return m_commentArrayList;
	}

	public int getCommentNum()
	{
		return m_commentArrayList.size();
	}

	public double getCommentRate()
	{
		return m_commentRate;
	}
}
