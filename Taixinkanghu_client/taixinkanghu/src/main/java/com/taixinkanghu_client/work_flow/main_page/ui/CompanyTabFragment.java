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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;


public class CompanyTabFragment extends Fragment
{

    private ImageButton btn_back;
    private TextView page_title;
    private LinearLayout company_info;
    private LinearLayout service_introduction;
    private LinearLayout worker_level;
    private LinearLayout goto_faq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.activity_about_us, container, false);
//
//        btn_back = (ImageButton) view.findViewById(R.id.btn_back);
//        page_title = (TextView) view.findViewById(R.id.page_title);
//        company_info = (LinearLayout) view.findViewById(R.id.company_info);
//        service_introduction = (LinearLayout) view.findViewById(R.id.service_introduction);
//        worker_level = (LinearLayout) view.findViewById(R.id.worker_level);
//        goto_faq = (LinearLayout) view.findViewById(R.id.goto_faq);
//
//        page_title.setText(R.string.about_us_title);
//        btn_back.setVisibility(View.GONE);
//        service_introduction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ServiceIntroductionActivity.class));
//            }
//        });
//
//        company_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), CompanyInfoActivity.class));
//            }
//        });
//
//        worker_level.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), WorkerLevelActivity.class));
//            }
//        });
//
//        goto_faq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), FaqActivity.class));
//            }
//        });


        return view;
    }
}
