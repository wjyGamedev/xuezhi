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

package com.taixinkanghu_client.data_module.nurse_list.data;


import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DataConfig;

import org.json.JSONObject;

import java.util.ArrayList;

public class DFaceImages
{
	public static final int DEFAULT_IMAGE_RES_ID = R.drawable.face_1001;

	private DFaceImages()
	{
		initTestData();
	}

	private void initTestData()
	{
		m_ImageArrayList.add(R.drawable.face_1001);
		m_ImageArrayList.add(R.drawable.face_1002);
		m_ImageArrayList.add(R.drawable.face_1003);
		m_ImageArrayList.add(R.drawable.face_1004);
		m_ImageArrayList.add(R.drawable.face_1005);
		m_ImageArrayList.add(R.drawable.face_1006);
		m_ImageArrayList.add(R.drawable.face_1007);
		m_ImageArrayList.add(R.drawable.face_1008);
		m_ImageArrayList.add(R.drawable.face_1009);
		m_ImageArrayList.add(R.drawable.face_1010);
		m_ImageArrayList.add(R.drawable.face_1011);
		m_ImageArrayList.add(R.drawable.face_1012);
		m_ImageArrayList.add(R.drawable.face_1013);
		m_ImageArrayList.add(R.drawable.face_1014);
		m_ImageArrayList.add(R.drawable.face_1015);
		m_ImageArrayList.add(R.drawable.face_1016);
		m_ImageArrayList.add(R.drawable.face_1017);
		m_ImageArrayList.add(R.drawable.face_1018);
		m_ImageArrayList.add(R.drawable.face_1019);
		m_ImageArrayList.add(R.drawable.face_1020);
		m_ImageArrayList.add(R.drawable.face_1021);
		m_ImageArrayList.add(R.drawable.face_1022);
		m_ImageArrayList.add(R.drawable.face_1023);
		m_ImageArrayList.add(R.drawable.face_1024);
		m_ImageArrayList.add(R.drawable.face_1025);
		m_ImageArrayList.add(R.drawable.face_1026);
		m_ImageArrayList.add(R.drawable.face_1027);
		m_ImageArrayList.add(R.drawable.face_1028);
		m_ImageArrayList.add(R.drawable.face_1029);
		m_ImageArrayList.add(R.drawable.face_1030);
		m_ImageArrayList.add(R.drawable.face_1031);
		m_ImageArrayList.add(R.drawable.face_1032);
		m_ImageArrayList.add(R.drawable.face_1033);
		m_ImageArrayList.add(R.drawable.face_1034);
		m_ImageArrayList.add(R.drawable.face_1035);
		m_ImageArrayList.add(R.drawable.face_1036);
		m_ImageArrayList.add(R.drawable.face_1037);
		m_ImageArrayList.add(R.drawable.face_1038);
		m_ImageArrayList.add(R.drawable.face_1039);
		m_ImageArrayList.add(R.drawable.face_1040);
		m_ImageArrayList.add(R.drawable.face_1041);
		m_ImageArrayList.add(R.drawable.face_1042);
		m_ImageArrayList.add(R.drawable.face_1043);
		m_ImageArrayList.add(R.drawable.face_1044);
		m_ImageArrayList.add(R.drawable.face_1045);
		m_ImageArrayList.add(R.drawable.face_1046);
		m_ImageArrayList.add(R.drawable.face_1047);
		m_ImageArrayList.add(R.drawable.face_1048);
		m_ImageArrayList.add(R.drawable.face_1049);
		m_ImageArrayList.add(R.drawable.face_1050);
		m_ImageArrayList.add(R.drawable.face_1051);

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
