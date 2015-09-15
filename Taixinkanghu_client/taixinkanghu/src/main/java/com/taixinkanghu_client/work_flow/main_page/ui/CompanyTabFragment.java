/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.ui.main_page.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p/>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/7/13		WangJY		1.0.0		create
 */

package com.taixinkanghu_client.work_flow.main_page.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.module.widget.dialog.TipsDialog;
import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.company_flow.company_show_page.ui.CompanyShowActivity;
import com.taixinkanghu_client.work_flow.company_flow.faq_page.ui.FaqActivity;
import com.taixinkanghu_client.work_flow.company_flow.nurse_level_introduction_page.ui.NurseLevelActivity;
import com.taixinkanghu_client.work_flow.company_flow.service_introduction_page.ui.ServiceIntroductionActivity;
import com.taixinkanghu_client.work_flow.main_page.msg_handler.MainMsgHandler;


public class CompanyTabFragment extends Fragment
{
    //widget
    private LinearLayout company_info;
    private LinearLayout service_introduction;
    private LinearLayout worker_level;
    private LinearLayout goto_faq;

    //logical
    private MainMsgHandler m_mainMsgHandler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.activity_about_us, container, false);

        company_info = (LinearLayout)view.findViewById(R.id.company_info);
        service_introduction = (LinearLayout)view.findViewById(R.id.service_introduction);
        worker_level = (LinearLayout)view.findViewById(R.id.worker_level);
        goto_faq = (LinearLayout)view.findViewById(R.id.goto_faq);

        service_introduction.setOnClickListener(new View.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(View v)
                                                    {
                                                        startActivity(new Intent(getActivity(), ServiceIntroductionActivity.class));
                                                    }
                                                }
                                               );

        company_info.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                startActivity(new Intent(getActivity(), CompanyShowActivity.class));
                                            }
                                        }
                                       );

        worker_level.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NurseLevelActivity.class));
            }
        });

        goto_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FaqActivity.class));
            }
        });

        init();
        return view;
    }

    /**
     * inner func
     */
    private void init()
    {
        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity == null)
        {
            TipsDialog.GetInstance().setMsg("mainActivity == null", getActivity());
            TipsDialog.GetInstance().show();
            return;
        }
        m_mainMsgHandler = mainActivity.getMainMsgHandler();
    }

}
