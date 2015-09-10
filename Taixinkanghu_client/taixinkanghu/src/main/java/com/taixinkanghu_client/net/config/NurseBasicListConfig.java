/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.event.net.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/17		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.net.config;

public class NurseBasicListConfig
{
	//common
	public final static String ID          = "cid";
	public final static String NAME        = CommonConifg.NAME;
	public final static String AGE         = "age";
	public final static String HOSPITAL_ID = "hospital_id";

	//send
	public final static String PHONE_NUM        = "phone_num";
	public final static String SEX_ID           = "sex_id";
	public final static String WEIGHT           = "weight";
	public final static String DEPARTMENT_NAME  = "department_name";
	public final static String PATIENT_STATE_ID = "patient_state_id";
	public final static String STRICT           = "strict";
	public final static String SCHEDULE_ALL     = "schedule_all";
	public final static String SCHEDULE_DAY     = "schedule_day";
	public final static String SCHEDULE_NIGHT   = "schedule_night";

	//recv
	public final static String LIST           = "nurse_basics_list";
	public final static String STAR_LEVEL     = "star_level";
	public final static String GENDER         = "gender";
	public final static String HOMETOWN       = "hometown";
	public final static String NURING_EXP     = "nursing_exp";
	public final static String NURING_LEVEL   = "nursing_level";
	public final static String SERVICE_STATUS = "nurse_service_status";

	public final static String SERVICE_CHARGE_PER_ALL_CARE       = "service_charge_per_all_care";           //24小时，可自理
	public final static String SERVICE_CHARGE_PER_ALL_HALF_CARE  = "service_charge_per_all_half_care";     //24小时，半自理
	public final static String SERVICE_CHARGE_PER_ALL_CANNT_CARE = "service_charge_per_all_cannt_care";    //24小时，不可自理

	public final static String SERVICE_CHARGE_PER_DAY_CARE       = "service_charge_per_day_care";           //12白，可自理
	public final static String SERVICE_CHARGE_PER_DAY_HALF_CARE  = "service_charge_per_day_half_care";     //12白，半自理
	public final static String SERVICE_CHARGE_PER_DAY_CANNT_CARE = "service_charge_per_day_cannt_care";    //12白，不可自理

	public final static String SERVICE_CHARGE_PER_NIGHT_CARE       = "service_charge_per_night_care";       //12黑，可自理
	public final static String SERVICE_CHARGE_PER_NIGHTL_HALF_CARE = "service_charge_per_night_care";      //12黑，半自理
	public final static String SERVICE_CHARGE_PER_NIGHT_CANNT_CARE = "service_charge_per_night_care";      //12黑，不可自理

	//logical
	public final static String SCHEDULE_SPLIT = ",";
	public final static String OLD_ID         = "old_id";
	public final static String NEW_ID         = "new_id";
	public final static String SELECTED_NURSE_ID         = "selected_nurse_id";
}
