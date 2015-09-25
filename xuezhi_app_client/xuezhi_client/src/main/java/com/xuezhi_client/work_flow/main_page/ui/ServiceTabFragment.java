/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.main_page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/13		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.main_page.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xuezhi_client.work_flow.main_page.msg_handler.MainMsgHandler;

public class ServiceTabFragment extends Fragment
{
    //widget
    private LinearLayout call;
    private LinearLayout question_feedback;

    //logical
    //logical
    private MainMsgHandler m_mainMsgHandler = null;

    private String m_phoneNumberDisplay;
    private String m_phoneNumberCall;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return null;
    }

    /**
     * inner func
     */
    private void init()
    {

    }
}
