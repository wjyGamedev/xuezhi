/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/10/10		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.config;

import com.xuezhi_client.config.EnumConfig;

public class AssayDetectionConfig
{
	public final static double CHART_LINE_WIDTH            = 1.5F;
	public final static double CHART_CIRCLE_SIZE           = 4.0F;
	public final static String ASSAY_DETECTION_TYPE        = "assay_detection_type";
	public final static int    CHART_X_AXIS_DEFAULT_LENGTH = 5;
	public final static String    CHART_X_AXIS_DEFAULT_NAME = "DataSet";


	//default
	public final static double    CHART_MIN_DEFAULT = 0;
	public final static double    CHART_LOWER_DEFAULT = 20;
	public final static double    CHART_UPPER_DEFAULT = 80;
	public final static double    CHART_MAX_DEFAULT = 100;

	//unit
	public final static String UINT_DEFAULT = "default";
	public final static String UINT_TG = "mmol/L";
	public final static String UINT_TCHO = "mmol/L";
	public final static String UINT_LOLC = "mmol/L";
	public final static String UINT_HDLC = "mmol/L";

	public final static String UINT_ATL = "U/L";
	public final static String UINT_AST = "U/L";
	public final static String UINT_CK = "U/L";
	public final static String UINT_GLU = "mmol/L";
	public final static String UINT_HBA1C = "%";
	public final static String UINT_SCR = "mmol/L";

	//血脂(mmol/L)
	//甘油三酯
	public final static double    CHART_TG_MIN = 0;
	public final static double    CHART_TG_LOWER_LIMIT_VALUE = 0.45;
	public final static double    CHART_TG_UPPER_LIMIT_VALUE = 1.81;
	public final static double    CHART_TG_MAX = 10;

	//总胆固醇(mmol/L)
	public final static double    CHART_TCHO_MIN = 0;
	public final static double    CHART_TCHO_LOWER_LIMIT_VALUE = 2.9;
	public final static double    CHART_TCHO_UPPER_LIMIT_VALUE = 6.0;
	public final static double    CHART_TCHO_MAX = 20;

	//低密度脂蛋白胆固醇(mmol/L)
	public final static double    CHART_LOLC_MIN = 0;
	public final static double    CHART_LOLC_LOWER_LIMIT_VALUE = 2.1;
	public final static double    CHART_LOLC_UPPER_LIMIT_VALUE = 3.1;
	public final static double    CHART_LOLC_MAX = 15;

	//高密度脂蛋白胆固醇(mmol/L)
	public final static double    CHART_HDLC_MIN = 0;
	public final static double    CHART_HDLC_LOWER_LIMIT_VALUE = 1.16;
	public final static double    CHART_HDLC_UPPER_LIMIT_VALUE = 1.42;
	public final static double    CHART_HDLC_MAX = 10;

	//生化
	//谷丙转氨酶(U/L)
	public final static double    CHART_ATL_MIN = 10;
	public final static double    CHART_ATL_LOWER_LIMIT_VALUE = 0;
	public final static double    CHART_ATL_UPPER_LIMIT_VALUE = 40;
	public final static double    CHART_ATL_MAX = 200;

	//谷草转氨酶(U/L)
	public final static double    CHART_AST_MIN = -10;
	public final static double    CHART_AST_LOWER_LIMIT_VALUE = 0;
	public final static double    CHART_AST_UPPER_LIMIT_VALUE = 40;
	public final static double    CHART_AST_MAX = 200;

	//肌酸激酶(U/L)
	public final static double    CHART_CK_MIN = -10;
	public final static double    CHART_CK_LOWER_LIMIT_VALUE = 8;
	public final static double    CHART_CK_UPPER_LIMIT_VALUE = 60;
	public final static double    CHART_CK_MAX = 300;

	//空腹血糖(mmol/L)
	public final static double    CHART_GLU_MIN = 0;
	public final static double    CHART_GLU_LOWER_LIMIT_VALUE = 3.89;
	public final static double    CHART_GLU_UPPER_LIMIT_VALUE = 6.1;
	public final static double    CHART_GLU_MAX = 20;

	//糖化血红蛋白(%)
	public final static double    CHART_HBA1C_MIN = 0;
	public final static double    CHART_HBA1C_LOWER_LIMIT_VALUE = 4;
	public final static double    CHART_HBA1C_UPPER_LIMIT_VALUE = 5.5;
	public final static double    CHART_HBA1C_MAX = 100;

	//肌酐(umol/L)
	public final static double    CHART_SCR_MIN = 0;
	public final static double    CHART_SCR_LOWER_LIMIT_VALUE = 53;
	public final static double    CHART_SCR_UPPER_LIMIT_VALUE = 106;
	public final static double    CHART_SCR_MAX = 300;

	public static String getName(EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
			return CHART_X_AXIS_DEFAULT_NAME;

		switch (assayDetectionType)
		{
		case TG:
		case TCHO:
		case LOLC:
		case HDLC:
		case ATL:
		case AST:
		case CK:
		case GLU:
		case HBA1C:
		case SCR:
			return assayDetectionType.getName();
		default:
			return CHART_X_AXIS_DEFAULT_NAME;
		}
	}

	public static String getUnit(EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
			return UINT_DEFAULT;

		switch (assayDetectionType)
		{
		case TG:
			return UINT_TG;
		case TCHO:
			return UINT_TCHO;
		case LOLC:
			return UINT_LOLC;
		case HDLC:
			return UINT_HDLC;
		case ATL:
			return UINT_ATL;
		case AST:
			return UINT_AST;
		case CK:
			return UINT_CK;
		case GLU:
			return UINT_GLU;
		case HBA1C:
			return UINT_HBA1C;
		case SCR:
			return UINT_SCR;
		default:
			return UINT_DEFAULT;
		}
	}

	public static double getMaxValue(EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
			return CHART_MAX_DEFAULT;

		switch (assayDetectionType)
		{
		case TG:
			return CHART_TG_MAX;
		case TCHO:
			return CHART_TCHO_MAX;
		case LOLC:
			return CHART_LOLC_MAX;
		case HDLC:
			return CHART_HDLC_MAX;
		case ATL:
			return CHART_ATL_MAX;
		case AST:
			return CHART_AST_MAX;
		case CK:
			return CHART_CK_MAX;
		case GLU:
			return CHART_GLU_MAX;
		case HBA1C:
			return CHART_HBA1C_MAX;
		case SCR:
			return CHART_SCR_MAX;
		default:
			return CHART_MAX_DEFAULT;
		}
	}

	public static double getMinValue(EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
			return CHART_MIN_DEFAULT;

		switch (assayDetectionType)
		{
		case TG:
			return CHART_TG_MIN;
		case TCHO:
			return CHART_TCHO_MIN;
		case LOLC:
			return CHART_LOLC_MIN;
		case HDLC:
			return CHART_HDLC_MIN;
		case ATL:
			return CHART_ATL_MIN;
		case AST:
			return CHART_AST_MIN;
		case CK:
			return CHART_CK_MIN;
		case GLU:
			return CHART_GLU_MIN;
		case HBA1C:
			return CHART_HBA1C_MIN;
		case SCR:
			return CHART_SCR_MIN;
		default:
			return CHART_MAX_DEFAULT;
		}
	}

	public static double getLowerValue(EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
			return CHART_LOWER_DEFAULT;

		switch (assayDetectionType)
		{
		case TG:
			return CHART_TG_LOWER_LIMIT_VALUE;
		case TCHO:
			return CHART_TCHO_LOWER_LIMIT_VALUE;
		case LOLC:
			return CHART_LOLC_LOWER_LIMIT_VALUE;
		case HDLC:
			return CHART_HDLC_LOWER_LIMIT_VALUE;
		case ATL:
			return CHART_ATL_LOWER_LIMIT_VALUE;
		case AST:
			return CHART_AST_LOWER_LIMIT_VALUE;
		case CK:
			return CHART_CK_LOWER_LIMIT_VALUE;
		case GLU:
			return CHART_GLU_LOWER_LIMIT_VALUE;
		case HBA1C:
			return CHART_HBA1C_LOWER_LIMIT_VALUE;
		case SCR:
			return CHART_SCR_LOWER_LIMIT_VALUE;
		default:
			return CHART_LOWER_DEFAULT;
		}
	}

	public static double getUpperValue(EnumConfig.AssayDetectionType assayDetectionType)
	{
		if (assayDetectionType == null)
			return CHART_UPPER_DEFAULT;

		switch (assayDetectionType)
		{
		case TG:
			return CHART_TG_UPPER_LIMIT_VALUE;
		case TCHO:
			return CHART_TCHO_UPPER_LIMIT_VALUE;
		case LOLC:
			return CHART_LOLC_UPPER_LIMIT_VALUE;
		case HDLC:
			return CHART_HDLC_UPPER_LIMIT_VALUE;
		case ATL:
			return CHART_ATL_UPPER_LIMIT_VALUE;
		case AST:
			return CHART_AST_UPPER_LIMIT_VALUE;
		case CK:
			return CHART_CK_UPPER_LIMIT_VALUE;
		case GLU:
			return CHART_GLU_UPPER_LIMIT_VALUE;
		case HBA1C:
			return CHART_HBA1C_UPPER_LIMIT_VALUE;
		case SCR:
			return CHART_SCR_UPPER_LIMIT_VALUE;
		default:
			return CHART_UPPER_DEFAULT;
		}
	}

}
