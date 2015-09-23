package com.taixinkanghu_client.work_flow.appiontment_nursing_flow.comment_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.appiontment_nursing_flow.comment_page.msg_handler.CommentMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/7/13.
 */
public class CommentActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;    //title：用户协议

	@Bind (R.id.very_good_rbtn)
	RadioButton m_vertGoodRBtn = null;

	@Bind(R.id.good_rbtn)
	RadioButton m_goodRBtn = null;

	@Bind(R.id.normal_rbtn)
	RadioButton m_normalRBtn = null;

	@Bind(R.id.comment_item_lv)
	ListView m_listView = null;



	private BottomCommon m_bottomCommon = null;    //底部按钮：返回主页面


	//logical
	private CommentMsgHandler m_commentMsgHandler = new CommentMsgHandler(this);
	private ClickBottomBtn    m_clickBottomBtn    = new ClickBottomBtn();

	private CommentListAdapter m_commentListAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		ButterKnife.bind(this);

		init();
	}

	@Override
		 protected void onStart()
{
	updateContent();
	super.onStart();
}

	/**
	 * override func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_commentMsgHandler.go2MainPage();
			return;
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		//widget
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.service_commnet);

		m_vertGoodRBtn.setChecked(true);

		m_commentListAdapter = new CommentListAdapter(this);
		m_listView.setAdapter(m_commentListAdapter);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.content_main_page);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		return;
	}

	private void updateContent()
	{
		if (m_commentListAdapter != null)
		{
			m_commentListAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * data:get
	 */
	public CommentMsgHandler getCommentMsgHandler()
	{
		return m_commentMsgHandler;
	}
}
