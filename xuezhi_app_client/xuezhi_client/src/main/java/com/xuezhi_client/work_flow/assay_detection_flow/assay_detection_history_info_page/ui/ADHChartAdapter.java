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

public class ADHChartAdapter extends FragmentStatePagerAdapter
{
	private FragmentManager m_chindFragmentManager = null;

	@Override
	public Fragment getItem(int position)
	{
//		TgChartFragment tgChartFragment = new TgChartFragment();
//		FragmentTransaction transaction = m_adhChartFragment.getChildFragmentManager().beginTransaction();
//		transaction.replace(R.id.chart_vp, tgChartFragment);
//		transaction.commit();
		return TgChartFragment.newInstance();
//		return ComplexityFragment.newInstance();
	}

	@Override
	public int getCount()
	{
		return 1;
	}

	public ADHChartAdapter(FragmentManager fm)
	{
		super(fm);

	}

}
