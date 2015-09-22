package com.taixinkanghu_client.work_flow.repeat_order_flow.user_protocal_page.ui;

import android.os.Bundle;

import com.module.frame.BaseActivity;
import com.module.widget.bottom.BottomCommon;
import com.module.widget.header.HeaderCommon;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/7/16.
 */
public class UserProtocalActivity extends BaseActivity
{
    //widget
    private HeaderCommon m_headerCommon = null;    //title：用户协议
    private BottomCommon m_bottomCommon = null;    //底部按钮：确定

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_protocal);
        ButterKnife.bind(this);

        init();
    }

    /**
     * inner func
     */
    private void init()
    {
        m_headerCommon = (HeaderCommon)getFragmentManager().findFragmentById(R.id.common_header_fragment);
        m_headerCommon.setTitle(R.string.user_protocal_title);
        m_bottomCommon = (BottomCommon)getFragmentManager().findFragmentById(R.id.common_bottom_fragment);
        m_bottomCommon.getCommonBottomBtn().setText(R.string.confirm_btn_text);
    }


}
