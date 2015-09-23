/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu_client.work_flow.appiontment_nursing_flow.comment_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/9/23		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.comment_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.module.frame.IBaseAdapter;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;

import butterknife.Bind;

public class CommentListAdapter extends IBaseAdapter
{
	private LayoutInflater m_layoutInflater = null;

	@Override
	public int getCount()
	{
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return null;
	}

	public CommentListAdapter(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		m_layoutInflater = LayoutInflater.from(m_context);
	}


}

final class ViewHolder
{
	//widget
	@Bind (R.id.comment_name_tv) TextView    m_commentName = null;    //listview布局
	@Bind (R.id.comment_date_tv)        TextView m_commentDate      = null;        //头像
	@Bind (R.id.comment_content_tv)       TextView        m_tvName         = null;            //名字

}
