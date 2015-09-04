/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taixinkanghu_client.work_flow.main_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;


public class ImageAdapter extends BaseAdapter
{

	private LayoutInflater mInflater;
	private              int       m_iPostion  = 0;
	private static final int[]     ids         = {R.drawable.img_company, R.drawable.img_promotions};
	private              ImageView m_imageView = null;

	public ImageAdapter(Context context)
	{
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return ids.length;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public int getCurPosition() { return m_iPostion;}

	public int getNextPosition()
	{
		return (++m_iPostion) % ids.length;
	}

	public int getValidPosition(int iPosition)
	{
		return iPosition % ids.length;
	}

	public void setNextView()
	{
		m_imageView.setImageResource(ids[getNextPosition()]);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		m_iPostion = getValidPosition(position);

		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		if (m_imageView == null)
		{
			m_imageView = (ImageView)convertView.findViewById(R.id.imgView);
			m_imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		}
		m_imageView.setImageResource(ids[m_iPostion]);

		return convertView;
	}

}
