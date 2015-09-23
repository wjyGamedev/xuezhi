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
 * 2015/7/19		WangJY		1.0.0		create
 */

package com.xuezhi_client.data_module.nurse_list.data;


import com.xuzhi_client.xuzhi_app_client.R;
import com.xuezhi_client.config.DataConfig;

import org.json.JSONObject;

import java.util.ArrayList;

public class DFaceImages
{
	public static final int DEFAULT_IMAGE_RES_ID = R.drawable.default_img;

	private DFaceImages()
	{
		initTestData();
	}

	private void initTestData()
	{
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);
		m_ImageArrayList.add(R.drawable.default_img);

	}

	public static DFaceImages getInstance()
	{
		return s_dFaceImages;
	}

	public boolean serialization(JSONObject response)
	{
		return true;
	}

	public ArrayList<Integer> getImageIDList()
	{
		return m_ImageArrayList;
	}

	public Integer getImgResIDbyIndex(int index)
	{
		if (index >= m_ImageArrayList.size())
		{
			return DataConfig.DEFAULT_VALUE;
		}

		if (index < 0)
		{
			return DataConfig.DEFAULT_VALUE;
		}

		return m_ImageArrayList.get(index);

	}

	public Integer getImgResIDbyID(int id)
	{
		int index = (id - 1);
		return getImgResIDbyIndex(index);
	}


	private static DFaceImages        s_dFaceImages    = new DFaceImages();
	private        ArrayList<Integer> m_ImageArrayList = new ArrayList<Integer>();
}
