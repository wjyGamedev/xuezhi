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
 * 2015/7/12		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.main_page.data;

import android.util.Log;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class DMainPageImages
{
	private DMainPageImages()
	{
		initTestData();
	}

	private void initTestData()
	{
		m_ImageArrayList.add(R.drawable.img_company);
		m_ImageArrayList.add(R.drawable.img_promotions);
	}

	public static DMainPageImages getInstance()
	{
		return  s_dMainPageImages;
	}

	public boolean serialization(JSONObject response)
	{
		m_ImageArrayList.add(R.drawable.img_company);
		m_ImageArrayList.add(R.drawable.img_promotions);
		return true;
	}

	public ArrayList<Integer> getImageIDList()
	{
		return m_ImageArrayList;
	}

	public Integer getImageIDbyIndex(int index)
	{
		if (index >= m_ImageArrayList.size())
		{
			Log.e("error", "index >= m_ImageArrayList.size()");
			return 0;
		}

		return  m_ImageArrayList.get(index);

	}

	private static DMainPageImages    s_dMainPageImages = new DMainPageImages();
	private        ArrayList<Integer> m_ImageArrayList  = new ArrayList<Integer>();
}
