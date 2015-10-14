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

public class AssayDetectionConfig
{
	public final static double CHART_LINE_WIDTH            = 1.5F;
	public final static double CHART_CIRCLE_SIZE           = 4.0F;
	public final static String ASSAY_DETECTION_TYPE        = "assay_detection_type";
	public final static int    CHART_X_AXIS_DEFAULT_LENGTH = 5;
	public final static String    CHART_X_AXIS_DEFAULT_NAME = "DataSet";

	//血脂(mmol/L)
	//甘油三酯
	public final static double    CHART_TG_UPPER_LIMIT_VALUE = 0.45;
	public final static double    CHART_TG_LOWER_LIMIT_VALUE = 1.81;

	//总胆固醇
	public final static double    CHART_TCHO_UPPER_LIMIT_VALUE = 2.9;
	public final static double    CHART_TCHO_LOWER_LIMIT_VALUE = 6.0;

	//低密度脂蛋白胆固醇
	public final static double    CHART_LOLC_UPPER_LIMIT_VALUE = 2.1;
	public final static double    CHART_LOLC_LOWER_LIMIT_VALUE = 3.1;

	//高密度脂蛋白胆固醇
	public final static double    CHART_HDLC_UPPER_LIMIT_VALUE = 1.16;
	public final static double    CHART_HDLC_LOWER_LIMIT_VALUE = 1.42;

	//生化
	//谷丙转氨酶
	public final static double    CHART_ATL_UPPER_LIMIT_VALUE = 0;//单位：U/L
	public final static double    CHART_ATL_LOWER_LIMIT_VALUE = 40;

	//谷草转氨酶
	public final static double    CHART_AST_UPPER_LIMIT_VALUE = 0;
	public final static double    CHART_AST_LOWER_LIMIT_VALUE = 40;//单位：U/L

	//肌酸激酶
	public final static double    CHART_CK_UPPER_LIMIT_VALUE = 8;
	public final static double    CHART_CK_LOWER_LIMIT_VALUE = 60;//单位：U/L

	//空腹血糖
	public final static double    CHART_GLU_UPPER_LIMIT_VALUE = 3.89;
	public final static double    CHART_GLU_LOWER_LIMIT_VALUE = 6.1; //单位：mmol/L

	//糖化血红蛋白
	public final static double    CHART_HBA1C_UPPER_LIMIT_VALUE = 4;	//单位(%)
	public final static double    CHART_HBA1C_LOWER_LIMIT_VALUE = 5.5;

	//肌酐
	public final static double    CHART_SCR_UPPER_LIMIT_VALUE = 53;
	public final static double    CHART_SCR_LOWER_LIMIT_VALUE = 106;	//单位(umol/L)


}
