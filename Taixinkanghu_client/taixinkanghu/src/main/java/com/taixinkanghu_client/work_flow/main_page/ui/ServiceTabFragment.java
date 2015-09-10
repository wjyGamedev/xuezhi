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

package com.taixinkanghu_client.work_flow.main_page.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;
import com.taixinkanghu_client.work_flow.service_flow.question_feed_page.ui.QuestionFeedBackActivity;


public class ServiceTabFragment extends Fragment
{
    private LinearLayout call;
    private LinearLayout question_feedback;

    private String m_phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.activity_customer_service, container, false);

        call = (LinearLayout) view.findViewById(R.id.call);
        question_feedback = (LinearLayout) view.findViewById(R.id.question_feedback);

        m_phoneNumber = getResources().getString(R.string.service_phone_number);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setTitle("").setMessage(m_phoneNumber)
                        .setNegativeButton(getResources().getString(R.string.call), new DialogInterface.OnClickListener()
                                           {
                                               public void onClick(DialogInterface dialog, int which)
                                               {
                                                   //拨打电话
                                                   Toast.makeText(getActivity(),
                                                                  getResources().getString(R.string.call_phone),
                                                                  Toast.LENGTH_SHORT
                                                                 ).show();
                                                   Intent intent = new Intent("android.intent.action.CALL",
                                                                              Uri.parse("tel:" + m_phoneNumber)
                                                   );
                                                   getActivity().startActivity(intent);
                                               }
                                           }
                                          )
                        .setPositiveButton(getResources().getString(R.string.cancel), null)
                        .show();
            }
        });

        question_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QuestionFeedBackActivity.class));
            }
        });


        return view;
    }
}
