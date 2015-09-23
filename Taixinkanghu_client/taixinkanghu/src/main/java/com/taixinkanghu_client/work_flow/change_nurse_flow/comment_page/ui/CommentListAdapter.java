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

package com.taixinkanghu_client.work_flow.change_nurse_flow.comment_page.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.module.data.DGlobal;
import com.module.frame.IBaseAdapter;
import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.config.DateConfig;
import com.taixinkanghu_client.data_module.nurse_list.data.DComment;
import com.taixinkanghu_client.data_module.nurse_list.data.DCommentList;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseList;
import com.taixinkanghu_client.data_module.nurse_list.data.DNurseSenior;
import com.taixinkanghu_client.work_flow.change_nurse_flow.comment_page.msg_handler.CommentMsgHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentListAdapter extends IBaseAdapter
{
	private LayoutInflater m_layoutInflater = null;
	private DCommentList m_commentList = null;

	@Override
	public int getCount()
	{
		if (m_commentList == null)
			return 0;

		return m_commentList.getCommentNum();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{


		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = m_layoutInflater.inflate(R.layout.item_comment, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		viewHolder.initContent(m_commentList.getCommentArrayList(), position);
		return convertView;

	}

	public CommentListAdapter(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		m_layoutInflater = LayoutInflater.from(m_context);

		CommentActivity activity = (CommentActivity)m_context;
		if (activity == null)
		{
			TipsDialog.GetInstance().setMsg("CommentActivity == null");
			TipsDialog.GetInstance().show();
			return;
		}

		CommentMsgHandler commentMsgHandler = activity.getCommentMsgHandler();
		if (commentMsgHandler == null)
		{
			TipsDialog.GetInstance().setMsg("commentMsgHandler == null");
			TipsDialog.GetInstance().show();
			return;
		}

		int          nurseID     = commentMsgHandler.getSelectedNurseID();
		DNurseSenior nurseSenior = DNurseList.GetInstance().getNurseSeniorByID(nurseID);
		if (nurseSenior == null)
		{
			TipsDialog.GetInstance().setMsg("nurseSenior == null");
			TipsDialog.GetInstance().show();
			return;
		}

		m_commentList = nurseSenior.getCommentList();

	}


}

final class ViewHolder
{
	//widget
	@Bind (R.id.comment_name_tv)    TextView m_commentNameTV    = null;    //listview布局
	@Bind (R.id.comment_date_tv)    TextView m_commentDateTV    = null;        //头像
	@Bind (R.id.comment_content_tv) TextView m_commentContentTV = null;            //名字

	//logical
	private View             m_view   = null;
	private SimpleDateFormat m_allSDF = new SimpleDateFormat(DateConfig.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);


	public ViewHolder(View view)
	{
		m_view = view;
		ButterKnife.bind(this, m_view);
	}

	public void initContent(ArrayList<DComment> commentArrayList, int position)
	{
		if (commentArrayList == null || commentArrayList.isEmpty())
		{
			TipsDialog.GetInstance().setMsg("commentArrayList == null");
			TipsDialog.GetInstance().show();
			return;
		}

		if (position >= commentArrayList.size())
		{
			TipsDialog.GetInstance().setMsg("position >= commentArrayList.size()[position:=" + position + "][commentArrayList.size():=" +
													commentArrayList.size() + "]"
										   );
			TipsDialog.GetInstance().show();
			return;
		}

		DComment comment     = commentArrayList.get(position);
		String   defaultName = DGlobal.GetInstance().getAppContext().getString(R.string.comment_default_name);
		m_commentNameTV.setText(defaultName);

		Calendar dateCalendar = comment.getCommentDate();
		String   dateDisplay  = m_allSDF.format(dateCalendar.getTime());
		m_commentDateTV.setText(dateDisplay);

		m_commentContentTV.setText(comment.getCommentContent());

	}

}
