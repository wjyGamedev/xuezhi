/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.data.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_list.data;

import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.config.EnumConfig;
import com.taixinkanghu_client.net.config.NurseSeniorListConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DComment implements Serializable
{
	/**
	 * 数据区
	 */
	private int                     m_userID         = DataConfig.DEFAULT_VALUE;
	private String                  m_orderID        = null;
	private int                     m_nurseID        = DataConfig.DEFAULT_VALUE;
	private EnumConfig.CommentLevel m_commentLevel   = EnumConfig.CommentLevel.COMMENT_LEVEL_VERY_STATISFIED;
	private Calendar                m_commentDate    = Calendar.getInstance();
	private String                  m_commentContent = null;
	private int                     m_commentID      = DataConfig.DEFAULT_VALUE;

	private SimpleDateFormat m_simpleDateFormat = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{
		m_userID = response.getInt(NurseSeniorListConfig.UID);
		m_orderID = response.getString(NurseSeniorListConfig.OID);
		m_nurseID = response.getInt(NurseSeniorListConfig.NURSE_ID);

		int commentLevel = response.getInt(NurseSeniorListConfig.COMMENT_LEVEL);
		m_commentLevel = EnumConfig.CommentLevel.valueOf(commentLevel);
		if (m_commentLevel == null)
		{
			m_commentLevel = EnumConfig.CommentLevel.COMMENT_LEVEL_VERY_STATISFIED;
		}

		String commentDate = response.getString(NurseSeniorListConfig.COMMENT_DATE);
		Date   date        = m_simpleDateFormat.parse(commentDate);
		m_commentDate.setTime(date);

		m_commentContent = response.getString(NurseSeniorListConfig.COMMENT_CONTENT);
		m_commentID = response.getInt(NurseSeniorListConfig.COMMENT_ID);

		return true;
	}

	public void copyfrom(DComment dComment) throws JSONException
	{
		if (dComment == null)
		{
			throw new JSONException("nurseBasics == null");
		}

		m_userID = dComment.getUserID();
		m_orderID = dComment.getOrderID();
		m_nurseID = dComment.getNurseID();
		m_commentLevel = dComment.getCommentLevel();
		m_commentDate = dComment.getCommentDate();
		m_commentContent = dComment.getCommentContent();
		m_commentID = dComment.getCommentID();

		return;

	}

	public int getUserID()
	{
		return m_userID;
	}

	public String getOrderID()
	{
		return m_orderID;
	}

	public int getNurseID()
	{
		return m_nurseID;
	}

	public EnumConfig.CommentLevel getCommentLevel()
	{
		return m_commentLevel;
	}

	public Calendar getCommentDate()
	{
		return m_commentDate;
	}

	public String getCommentContent()
	{
		return m_commentContent;
	}

	public int getCommentID()
	{
		return m_commentID;
	}

}
