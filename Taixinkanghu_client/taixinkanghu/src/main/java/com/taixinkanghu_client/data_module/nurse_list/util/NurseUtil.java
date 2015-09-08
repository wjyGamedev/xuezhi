/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.data_module.nurse_list.util;

import com.module.data.DGlobal;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;

public class NurseUtil
{
	/**
	 * 教育程度
	 */
	private static final int s_iBeginLevel                    = 0;
	private static final int s_iJuniorHighSchoolLevelAndBelow = s_iBeginLevel;    //一般
	private static final int s_iSeniorMiddleSchoole           = 1;    //良好
	private static final int s_iEndLevel                      = s_iSeniorMiddleSchoole;

	private static final String JUNIOR_HIGH_SCHOOL_LEVEL_AND_BELOW = DGlobal.GetInstance().getAppContext().getString(R.string.junior_high_school_level_and_below);
	private static final String SENIOR_MIDDLE_SCHOOLE = DGlobal.GetInstance().getAppContext().getString(R.string.senior_middle_schoole);

	public static String GetEducationLevelByInteger(Integer iLevel)
	{
		if (iLevel < s_iJuniorHighSchoolLevelAndBelow || iLevel > s_iEndLevel)
		{
			//TODO:error
			return JUNIOR_HIGH_SCHOOL_LEVEL_AND_BELOW;
		}

		if (iLevel == s_iJuniorHighSchoolLevelAndBelow)
		{
			return JUNIOR_HIGH_SCHOOL_LEVEL_AND_BELOW;
		}
		else if (iLevel == s_iSeniorMiddleSchoole)
		{
			return SENIOR_MIDDLE_SCHOOLE;
		}
		else
		{
			//TODO:error
			return JUNIOR_HIGH_SCHOOL_LEVEL_AND_BELOW;
		}
	}

	public static String GetEducationLevelByString(String strLevel)
	{
		if (strLevel == null)
		{
			return "";
		}
		return strLevel;
	}

	/**
	 * 语言能力
	 */
	private static final int s_iGeneral = 0;    //一般
	private static final int s_iGood    = 1;    //良好
	private static final int s_iSkilled = 2;    //熟练
	private static final int s_iCN      = 1;    //汉语
	private static final int s_iEN      = 2;    //英语

	private static final String LANGUAGE_LEVEL_GENERAL = DGlobal.GetInstance().getAppContext().getString(R.string.language_level_general);
	private static final String LANGUAGE_LEVEL_GOOD = DGlobal.GetInstance().getAppContext().getString(R.string.language_level_good);
	private static final String LANGUAGE_LEVEL_SKILLED = DGlobal.GetInstance().getAppContext().getString(R.string.language_level_skilled);

	private static final String LANGUAGE_TYPE_CN = DGlobal.GetInstance().getAppContext().getString(R.string.language_type_cn);
	private static final String LANGUAGE_TYPE_EN = DGlobal.GetInstance().getAppContext().getString(R.string.language_type_en);


	public static String GetLanguageLevelByInteger(Integer iLevel)
	{
		Integer iBits = iLevel % 10;

		//能力
		String strBits = null;
		if (iBits == s_iGeneral)
		{
			strBits = LANGUAGE_LEVEL_GENERAL;
		}
		else if (iBits == s_iGood)
		{
			strBits = LANGUAGE_LEVEL_GOOD;
		}
		else if (iBits == s_iSkilled)
		{
			strBits = LANGUAGE_LEVEL_SKILLED;
		}
		else
		{
			//TODO:ERROR
			strBits = LANGUAGE_LEVEL_GENERAL;
		}

		//语种
		Integer iLanguageType = iLevel - iBits;
		String  strType       = null;
		if (iLanguageType == s_iCN)
		{
			strType = LANGUAGE_TYPE_CN;
		}
		else if (iLanguageType == s_iEN)
		{
			strType = LANGUAGE_TYPE_EN;
		}
		else
		{
			//TODO:ERROR
			strType = LANGUAGE_TYPE_CN;
		}

		return (strBits + strType);
	}

	public static String GetLanguageLevelByString(String strLevel)
	{
		if (strLevel == null)
		{
			return "";
		}
		return strLevel;
	}

	/**
	 * 服务状态
	 */
	private final static int s_iFree     = 0;
	private final static int s_iServices = 1;

	private static final String NURSE_STATUS_FREE = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_status_free);
	private static final String NURSE_STATUS_SERVICES = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_status_services);


	public static String GetStatusByInteger(Integer iStatus)
	{
		if (iStatus < s_iFree || iStatus > s_iServices)
		{
			//TODO:Log
			return NURSE_STATUS_FREE;
		}

		if (iStatus == s_iFree)
		{
			return NURSE_STATUS_FREE;
		}
		else if (iStatus == s_iServices)
		{
			return NURSE_STATUS_SERVICES;
		}
		else
		{
			//TODO:ERROR
			return NURSE_STATUS_FREE;
		}
	}

	public static String GetStatusByString(String strStatus)
	{
		if (strStatus == null)
		{
			return "";
		}
		return strStatus;
	}

	/**
	 *	服务星级
	 */
	private static final int s_iMinStar = 3;
	private static final int s_iMaxStar = 5;

	public static int GetStarLevelByInteger(int iStarLevel)
	{
		if (iStarLevel < s_iMinStar ||
				iStarLevel > s_iMaxStar)
		{
			//TODO:Log error
			return s_iMinStar;
		}

		return iStarLevel;
	}

	/**
	 * 服务经验
	 */
	private static final String NO_NURSING_EXP = DGlobal.GetInstance().getAppContext().getString(R.string.no_nursing_exp);
	private static final String YEAR_NURSING_EXP = DGlobal.GetInstance().getAppContext().getString(R.string.year_nursing_exp);

	public static String GetServiceExpByInteger(Integer iExp)
	{
		if (iExp < 0 )
		{
			//TODO:Log error
			return NO_NURSING_EXP;
		}
		else if (iExp == 0)
		{
			return NO_NURSING_EXP;
		}
		else
		{
			return (iExp.toString() + YEAR_NURSING_EXP);
		}

	}

	/**
	 * 服务等级
	 */
	public static final int NURSING_LEVEL_JUNIOR = 0;
	public static final int NURSING_LEVEL_INTERMEDIATES = 1;
	public static final int NURSING_LEVEL_SENIOR = 2;
	public static final int NURSING_LEVEL_SUPER = 3;

	private static String s_strSenior = null;
	private static String s_strSuper = null;

	private static final String NURSE_JUNIOR = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_junior);
	private static final String INTERMEDIATES_JUNIOR = DGlobal.GetInstance().getAppContext().getString(R.string.intermediates_junior);
	private static final String SENIOR_JUNIOR = DGlobal.GetInstance().getAppContext().getString(R.string.senior_junior);
	private static final String SUPER_JUNIOR = DGlobal.GetInstance().getAppContext().getString(R.string.super_junior);


	private static String GetStrJunior()
	{
		return NURSE_JUNIOR;
	}

	private static String GetStrInterMediates()
	{
		return INTERMEDIATES_JUNIOR;
	}

	private static String GetStrSenior()
	{
		return SENIOR_JUNIOR;
	}

	private static String GetStrSuper()
	{
		return SUPER_JUNIOR;
	}

	public static String GetNursingLevelByInteger(int iLevel)
	{
		switch(iLevel)
		{
			case NURSING_LEVEL_JUNIOR:
			{
				return GetStrJunior();
			}
			case NURSING_LEVEL_INTERMEDIATES:
			{
				return GetStrInterMediates();
			}
			case NURSING_LEVEL_SENIOR:
			{
				return GetStrSenior();
			}
			case NURSING_LEVEL_SUPER:
			{
				return GetStrSuper();
			}
			default:
			{
				return GetStrJunior();
			}
		}
	}

	/**
	 * 自我介绍
	 */

	private static final String NURSE_INTRO = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_intro);
	private static final String NURSE_DEPARTMENTS = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_departments);
	private static final String NURSE_CERTIFICATE = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_certificate);
	private static final String NURSE_SERVICE_CONTENT = DGlobal.GetInstance().getAppContext().getString(R.string.nurse_service_content);


	public static String GetIntroByString(String strIntro)
	{
		return  (strIntro + NURSE_INTRO);
	}

	/**
	 * 擅长科室
	 */
	public static String GetDepartmentsByString(String strDepartments)
	{
		return  (strDepartments + NURSE_DEPARTMENTS);
	}

	/**
	 * 持有证书
	 */
	public static String GetCertificateByString(String strCertificate)
	{
		return  (strCertificate + NURSE_CERTIFICATE);
	}

	/**
	 * 服务内容
	 */
	public static String GetServiceContentByString(String strServiceContent)
	{
		return  (strServiceContent + NURSE_SERVICE_CONTENT);
	}

}
