package com.taixinkanghu_client.work_flow.service_flow.question_feed_page.ui;

import android.app.Activity;
import android.os.Bundle;

import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;


/**
 * Created by Administrator on 2015/7/16.
 */
public class QuestionFeedBackActivity extends Activity
{
	private HeaderCommon m_headerCommon = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_feedback);

		init();
		//        btn_back = (ImageButton) findViewById(R.id.btn_back);
		//        btn_open_fragment = (LinearLayout) findViewById(R.id.btn_open_fragment);
		//
		//        page_title.setText(R.string.question_feed_back_title);
		//
		//        btn_back.setOnClickListener(new View.OnClickListener() {
		//            @Override
		//            public void onClick(View v) {
		//                finish();
		//            }
		//        });
		//
		//        btn_open_fragment.setOnClickListener(new View.OnClickListener() {
		//            @Override
		//            public void onClick(View v) {
		//                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.title, new ContactInformationFragment
		// ()).commit();
		//            }
		//        });

	}

	private void init()
	{
		m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
		m_headerCommon.setTitle(R.string.question_feedback_title);
	}
}
