/**
 * Copyright (c) 213Team
 *
 * @className : com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/11/18		WangJY		1.0.0		create
 */

package com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuezhi_client.work_flow.medicine_reminder_flow.medicine_reminder_add_page.msg_handler.MedicineReminderAddMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

public class ReminderTime
{
	private LinearLayout mReminderTimeContanerLl = null;
	private int          mIndex                  = 0;
	private Context      mContext                = null;
	private String[] mNumDisplay;
	private LinearLayout                  mParent                        = null;
	private MedicineReminderAddMsgHandler mMedicineReminderAddMsgHandler = null;
	private ClickSelectedTimeRegion       mClickSelectedTimeRegion       = new ClickSelectedTimeRegion();

	private TextView                  mTimeTv       = null;

	public ReminderTime(Context context, LinearLayout reminderTimeContanerLl, int index, MedicineReminderAddMsgHandler
			medicineReminderAddMsgHandler)
	{
		mContext = context;
		mReminderTimeContanerLl = reminderTimeContanerLl;
		mIndex = index;
		mNumDisplay = mContext.getResources().getStringArray(R.array.num_display_array);
		mMedicineReminderAddMsgHandler = medicineReminderAddMsgHandler;
		init();

	}

	private void init()
	{
		//01. parent
		mParent = new LinearLayout(mContext);
		mParent.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
																		 LinearLayout.LayoutParams.WRAP_CONTENT
		);
		mParent.setLayoutParams(params);
		float topPX   = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, mContext.getResources().getDisplayMetrics());
		float rightPX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, mContext.getResources().getDisplayMetrics());
		params.setMargins((int)topPX, 0, (int)rightPX, 0);
		mParent.setOnClickListener(mClickSelectedTimeRegion);

		mReminderTimeContanerLl.addView(mParent);


		//02. element
		//TextView
		TextView textView = new TextView(mContext);
		float heightTextView = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, mContext.getResources().getDisplayMetrics()
														);
		ViewGroup.LayoutParams paramsTextView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)heightTextView);
		textView.setLayoutParams(paramsTextView);
		textView.setBackgroundColor(mParent.getResources().getColor(R.color.main_color));
		mParent.addView(textView, 0);

		//func region
		LinearLayout funcRegion = new LinearLayout(mContext);
		funcRegion.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams paramsFuncRegion = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
																				   LinearLayout.LayoutParams.WRAP_CONTENT
		);
		funcRegion.setLayoutParams(paramsFuncRegion);
		mParent.addView(funcRegion, 1);

		//func element
		//textview
		TextView                  reminderTv      = new TextView(mContext);
		LinearLayout.LayoutParams paramsFuncEleTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
																				  ViewGroup.LayoutParams.WRAP_CONTENT,
																				  1.0f
		);
		paramsFuncEleTv.gravity = Gravity.CENTER;
		reminderTv.setLayoutParams(paramsFuncEleTv);
		float marginTop    = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
		float marginBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
		paramsFuncEleTv.setMargins(0, (int)marginTop, 0, (int)marginBottom);
		if (mIndex >= mNumDisplay.length)
		{

			reminderTv.setText(R.string.medication_reminder_alert_time_1_title_text);
		}
		else
		{
			reminderTv.setText(String.format(mContext.getString(R.string.medication_reminder_alert_time_x_title_text),
											 mNumDisplay[mIndex]
											)
							  );
		}
		reminderTv.setTextSize(20);
		funcRegion.addView(reminderTv, 0);
		//textview
		mTimeTv       = new TextView(mContext);
		LinearLayout.LayoutParams paramsTimeTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
																			   ViewGroup.LayoutParams.WRAP_CONTENT
		);
		paramsTimeTv.gravity = Gravity.CENTER;
		mTimeTv.setLayoutParams(paramsTimeTv);
		float marginTopTime    = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
		float marginBottomTime = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
		float marginRightTime  = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
		paramsTimeTv.setMargins(0, (int)marginTopTime, (int)marginRightTime, (int)marginBottomTime);
		mTimeTv.setHint(R.string.click_select_hint_text);
		mTimeTv.setTextSize(20);
		funcRegion.addView(mTimeTv, 1);
		//imageView
		ImageView                 downArrowIv       = new ImageView(mContext);
		int                       widthDownArrow    = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
																					 20,
																					 mContext.getResources().getDisplayMetrics()
																					);
		int                       heightDownArrow   = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
																					 20,
																					 mContext.getResources().getDisplayMetrics()
																					);
		LinearLayout.LayoutParams paramsDownArrowIv = new LinearLayout.LayoutParams(widthDownArrow, heightDownArrow);
		paramsDownArrowIv.gravity = Gravity.CENTER;
		downArrowIv.setLayoutParams(paramsDownArrowIv);
		downArrowIv.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.img_down));
		funcRegion.addView(downArrowIv, 2);

	}

	public void setVisibility(int visibility)
	{
		mParent.setVisibility(visibility);
	}

	class ClickSelectedTimeRegion implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			mMedicineReminderAddMsgHandler.go2SelectMedicineTimeFragment(mIndex);
		}
	}

	public TextView getTimeTv()
	{
		return mTimeTv;
	}

	public String getIndexDisplay()
	{
		return mNumDisplay[mIndex];
	}
}
