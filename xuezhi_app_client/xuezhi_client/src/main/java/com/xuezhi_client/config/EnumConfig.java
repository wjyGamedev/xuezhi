/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.config.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/28		WangJY		1.0.0		create
 */

package com.xuezhi_client.config;

import com.module.data.DGlobal;
import com.xuzhi_client.xuzhi_app_client.R;

public class EnumConfig
{
	//01. 药品单位：片/毫克
	public enum MedicalUnit
	{
		SHEET(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.medical_unit_sheet), 1),
		MILLIGRAM(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.medical_unit_milligram), 2);

		private String m_name = null;
		private int    m_id   = 1;

		private MedicalUnit(String name, int id)
		{
			m_name = name;
			m_id = id;
		}

		public String getName()
		{
			return m_name;
		}

		public int getId()
		{
			return m_id;
		}

		@Override
		public String toString()
		{
			return (m_name + ":" + m_id);
		}

		public static MedicalUnit valueOf(int id)
		{
			switch (id)
			{
			case 1:
				return SHEET;
			case 2:
				return MILLIGRAM;
			default:
				return null;
			}
		}
	}

	//02. 血脂类别
	public enum AssayDetectionType
	{
		//0:所有类型
//		LIPID_BIOCHEMICAL_ALL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_biochemical_all), 0),

		//血脂类型
		LIPID_ALL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_all), 0),
		TG(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_tg), 1),
		TCHO(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_tcho), 2),
		LOLC(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_lolc), 3),
		HDLC(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.lipid_type_hdlc), 4),

		//生化类型
//		BIOCHEMICAL_ALL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_all), 6),
		ATL(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_atl), 5),
		AST(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_ast), 6),
		CK(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_ck), 7),
		GLU(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_glu), 8),
		HBA1C(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_hba1c), 9),
		SCR(DGlobal.GetInstance().getAppContext().getResources().getString(R.string.biochemical_type_scr), 10);

		private String m_name = null;
		private int    m_id   = 1;

		private AssayDetectionType(String name, int id)
		{
			m_name = name;
			m_id = id;
		}

		public String getName()
		{
			return m_name;
		}

		public int getId()
		{
			return m_id;
		}

		@Override
		public String toString()
		{
			return (m_name + ":" + m_id);
		}

		public static AssayDetectionType valueOf(int id)
		{
			switch (id)
			{
			case 0:
				return LIPID_ALL;
			case 1:
				return TG;
			case 2:
				return TCHO;
			case 3:
				return LOLC;
			case 4:
				return HDLC;
			//血脂
			case 5:
				return ATL;
			case 6:
				return AST;
			case 7:
				return CK;
			case 8:
				return GLU;
			case 9:
				return HBA1C;
			case 10:
				return SCR;

			default:
				return LIPID_ALL;
			}
		}
	}




}
