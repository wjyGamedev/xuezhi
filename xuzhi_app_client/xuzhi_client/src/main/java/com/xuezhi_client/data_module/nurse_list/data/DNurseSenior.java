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
 * 2015/7/18		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_list.data;


import com.module.data.DGlobal;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;
import com.taixinkanghu_client.net.config.NurseOrderConfig;
import com.taixinkanghu_client.net.config.NurseSeniorListConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

public class DNurseSenior implements Serializable
{
	/**
	 * 数据区
	 */
	private int m_ID = DataConfig.DEFAULT_VALUE;                         //ID

	private String m_jobNum         = null;       //工号
	private String m_languageLevel  = null;   //语言水平
	private String m_educationLevel = null;   //文化程度
	private String m_nation         = null;    //民族
	private String m_intro          = null;   //自我介绍
	private String m_departments    = null;  //擅长科室
	private String m_certificate    = null;  //持有证书
	private String m_serviceContent = null;  //服务内容

	private DScheduleList m_scheduleList = new DScheduleList();
	private DCommentList  m_commentList  = new DCommentList();

	public boolean serialization(JSONObject response) throws JSONException, ParseException
	{

//		JSONObject jsonNurseInfo = response.getJSONObject(NurseOrderConfig.NURSE_INFO);

		m_ID = response.getInt(NurseSeniorListConfig.ID);
		m_jobNum = response.getString(NurseSeniorListConfig.JOB_NUM);
		m_languageLevel = response.getString(NurseSeniorListConfig.LANGUAGE_LEVEL);
		m_educationLevel = response.getString(NurseSeniorListConfig.EDUCATION);
		m_nation = response.getString(NurseSeniorListConfig.NATION);
		m_intro = response.getString(NurseSeniorListConfig.INTRO);
		m_intro = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_intro) + m_intro;
		m_departments = response.getString(NurseSeniorListConfig.DEPARTMENTS);
		m_certificate = response.getString(NurseSeniorListConfig.CERTIFICATE);
		m_serviceContent = response.getString(NurseSeniorListConfig.SERVICE_CONTENT);
		m_serviceContent = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_service_content) + m_serviceContent;

		m_scheduleList.serialization(response);

		m_commentList.serialization(response);

		//同步到全局容器中
		DNurseList.GetInstance().syncNurseSenior(m_ID, this);

		return true;
	}

	public boolean serializationFromOrder(JSONObject response) throws JSONException, ParseException
	{
		JSONObject jsonNurseInfo = response.getJSONObject(NurseOrderConfig.NURSE_INFO);

		m_ID = jsonNurseInfo.getInt(NurseSeniorListConfig.ID);
		m_jobNum = jsonNurseInfo.getString(NurseSeniorListConfig.JOB_NUM);
		m_languageLevel = jsonNurseInfo.getString(NurseSeniorListConfig.LANGUAGE_LEVEL);
		m_educationLevel = jsonNurseInfo.getString(NurseSeniorListConfig.EDUCATION);
		m_nation = jsonNurseInfo.getString(NurseSeniorListConfig.NATION);
		m_intro = jsonNurseInfo.getString(NurseSeniorListConfig.INTRO);
		m_intro = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_intro) + m_intro;
		m_departments = jsonNurseInfo.getString(NurseSeniorListConfig.DEPARTMENTS);
		m_certificate = jsonNurseInfo.getString(NurseSeniorListConfig.CERTIFICATE);
		m_serviceContent = jsonNurseInfo.getString(NurseSeniorListConfig.SERVICE_CONTENT);
		m_serviceContent = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_service_content) + m_serviceContent;

		m_scheduleList.serialization(jsonNurseInfo);

		m_commentList.serialization(response);

		//同步到全局容器中
		DNurseList.GetInstance().syncNurseSenior(m_ID, this);

		return true;
	}

	public void copyfrom(DNurseSenior nurseSenior) throws JSONException
	{
		if (nurseSenior == null)
		{
			throw new JSONException("nurseSenior == null");
		}

		m_ID = nurseSenior.getID();

		m_jobNum = nurseSenior.getJobNum();
		m_languageLevel = nurseSenior.getLanguageLevel();
		m_educationLevel = nurseSenior.getEducationLevel();
		m_nation = nurseSenior.getNation();
		m_intro = nurseSenior.getIntro();
		m_departments = nurseSenior.getDepartments();
		m_certificate = nurseSenior.getCertificate();
		m_serviceContent = nurseSenior.getServiceContent();

		m_scheduleList.copyfrom(nurseSenior.getScheduleList());
		m_commentList.copyfrom(nurseSenior.getCommentList());

		return;

	}



	public int getID()
	{
		return m_ID;
	}

	public String getJobNum()
	{
		return m_jobNum;
	}

	public String getLanguageLevel()
	{
		return m_languageLevel;
	}

	public String getEducationLevel()
	{
		return m_educationLevel;
	}

	public String getNation()
	{
		return m_nation;
	}

	public String getIntro()
	{
		return m_intro;
	}

	public String getDepartments()
	{
		return m_departments;
	}

	public String getCertificate()
	{
		return m_certificate;
	}

	public String getServiceContent()
	{
		return m_serviceContent;
	}

	public DScheduleList getScheduleList()
	{
		return m_scheduleList;
	}

	public DCommentList getCommentList()
	{
		return m_commentList;
	}

}
