/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.company_show.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/27		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.show_flow.shopping_show.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.show_flow.shopping_show.msg_handler.ShoppingShowMsgHandler;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingShowActivity extends Activity
{
	//widget
	private HeaderCommon m_headerCommon = null;

	@Bind (R.id.img_info)      ImageView m_saleInfoImg;
	@Bind (R.id.info_title)    TextView  m_contentTitleTv;
	@Bind (R.id.info_text_red) TextView  m_contentRedTitleTv;
	@Bind (R.id.info_content)  TextView  m_contentTv;

	private BottomCommon m_bottomCommon = null;

	//logical
	private ShoppingShowMsgHandler m_shoppingShowMsgHandler = new ShoppingShowMsgHandler(this);
	private ClickBottomBtn        m_clickBottomBtn        = new ClickBottomBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_show);
		ButterKnife.bind(this);

		init();

	}


	/**
	 * overrider func
	 */
	class ClickBottomBtn implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			m_shoppingShowMsgHandler.go2MainPage();
		}
	}

	/**
	 * inner func
	 */
	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.shopping_show_title);

		m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
		m_bottomCommon.getCommonBottomBtn().setText(R.string.company_show_bottom);
		m_bottomCommon.getCommonBottomBtn().setOnClickListener(m_clickBottomBtn);

		m_saleInfoImg.setBackgroundResource(R.drawable.img_promotions);

		m_contentTitleTv.setText(getResources().getString(R.string.shopping_black_text));
		m_contentRedTitleTv.setText(getResources().getString(R.string.shopping_show_red_text));
		m_contentTv.setText(getResources().getString(R.string.shopping_show_content_text));

	}

}
