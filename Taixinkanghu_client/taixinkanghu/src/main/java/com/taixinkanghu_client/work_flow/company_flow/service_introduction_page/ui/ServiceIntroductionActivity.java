package com.taixinkanghu_client.work_flow.company_flow.service_introduction_page.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.taixinkanghu.hiworld.taixinkanghu_client.R;

/**
 * Created by Administrator on 2015/7/16.
 */
public class ServiceIntroductionActivity extends Activity{

    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_introduction);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
