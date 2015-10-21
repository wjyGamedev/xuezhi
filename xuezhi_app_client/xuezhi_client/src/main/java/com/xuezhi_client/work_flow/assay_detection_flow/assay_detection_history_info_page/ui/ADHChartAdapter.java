/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/29		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.assay_detection_flow.assay_detection_history_info_page.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xuezhi_client.config.EnumConfig;
import com.xuezhi_client.work_flow.assay_detection_flow.config.AssayDetectionConfig;

public class ADHChartAdapter extends FragmentStatePagerAdapter
{
	private FragmentManager m_chindFragmentManager = null;

	@Override
	public Fragment getItem(int position)
	{
//		return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.TG);

		//这里的position映射到我们的9+3个列表。
		//这里减的数量等于m_chartVP.setOffscreenPageLimit(1)中的数量
		switch(position)
		{
		case 0:
			return LipidAllChartFragment.newInstance();
		case 1:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.TG);
		case 2:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.TCHO);
		case 3:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.LOLC);
		case 4:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.HDLC);
		//生化
		case 5:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.ATL);
		//生化相关
		//6：生化all，因为类型不同，无法显示。
		case 6:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.AST);
		case 7:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.CK);
		case 8:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.GLU);
		case 9:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.HBA1C);
		case 10:
			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.SCR);
//		case 11:
//			return LipidAllChartFragment.newInstance();
//		case 12:
//			return SingleChartFragment.newInstance(EnumConfig.AssayDetectionType.TG);
		default:
			return LipidAllChartFragment.newInstance();
		}
	}

	@Override
	public int getCount()
	{
		return AssayDetectionConfig.CHART_PAGE_NUM;
	}

	public ADHChartAdapter(FragmentManager fm)
	{
		super(fm);
	}

}
