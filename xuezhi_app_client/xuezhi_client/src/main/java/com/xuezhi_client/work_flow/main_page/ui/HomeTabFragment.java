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

package com.xuezhi_client.work_flow.main_page.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.module.widget.dialog.TipsDialog;
import com.xuezhi_client.work_flow.main_page.data.DMainPageImages;
import com.xuezhi_client.work_flow.main_page.msg_handler.MainMsgHandler;
import com.xuzhi_client.xuzhi_app_client.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class HomeTabFragment extends Fragment
{
	//widget
	@Bind (R.id.today_reminder_title_tv) TextView     m_todayReminderTitleTV = null;
	@Bind (R.id.imgs_display_region_ll)  LinearLayout m_imgsDisplayRegionLL  = null;
	@Bind (R.id.imgs_display_vf)         ViewFlipper  m_imgsDisplayVF        = null;

	@Bind (R.id.reminder_header_ll)        LinearLayout m_reminderHeadersLL      = null;
	@Bind (R.id.reminder_bottom_tips_ll)   LinearLayout m_reminderBottomTipsLL   = null;
	@Bind (R.id.reminder_tips_tv)          TextView     m_reminderTipsTV         = null;
	@Bind (R.id.right_func_region_ll)      LinearLayout m_rightFuncRegionLl      = null;
	@Bind (R.id.take_medicine_reminder_iv) ImageView    m_takeMedicineReminderIV = null;

	@Bind (R.id.medicine_reminder_region_ll) LinearLayout m_medicineReminderRegionLL = null;
	@Bind (R.id.medicine_reminder_iv)        ImageView    m_medicineReminderIV    = null;


	@Bind (R.id.medicine_reminder_time_tv) TextView m_medicineReminderTimeTV = null;
	@Bind (R.id.medicine_name_tv)          TextView m_medicineNameTV         = null;
	@Bind (R.id.rose_tv)                   TextView m_roseTV                 = null;
	@Bind (R.id.medicine_unit_tv)          TextView m_medicineUnitTV         = null;
	@Bind (R.id.take_medicine_cbox)        CheckBox m_takeMedicineCBox       = null;
	@Bind (R.id.medicine_reminder_tv)      TextView m_medicineReminderTV     = null;

	@Bind (R.id.func_region_ll) LinearLayout m_funcRegionLL = null;
	@Bind (R.id.func_region_sv) ScrollView   m_funcRegionSV = null;

	//logical
	private MainMsgHandler  m_mainMsgHandler  = null;
	private View            m_view            = null;
	private GestureDetector m_gestureDetector = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_home, container, false);
		ButterKnife.bind(this, m_view);

		init();
		initContent();
		return m_view;
	}

	@Override
	public void onStart()
	{
		updateContent();
		super.onStart();
	}


	/**
	 * widget event
	 */
	@OnTouch (R.id.imgs_display_vf)
	public boolean hanldeImgsDisplayTouchEvent(View v, MotionEvent event)
	{
		m_gestureDetector.onTouchEvent(event);
		return true;
	}

	@OnClick (R.id.assay_detection_region_ll)
	public void clickAssayDetectionRegion()
	{
		m_mainMsgHandler.go2AssayDetectionAction();
		return;
	}

	@OnClick (R.id.medication_reminder_region_ll)
	public void clickMedicationReminderRegion()
	{
		m_mainMsgHandler.go2MedicationReminderAction();
		return;
	}

	@OnClick (R.id.drug_admin_region_ll)
	public void clickDrugAdminRegion()
	{
		m_mainMsgHandler.go2DrugAdminAction();
		return;
	}

	@OnClick (R.id.calendar_region_ll)
	public void clickCalendarRegion()
	{
		m_mainMsgHandler.go2CalendarAction();
		return;
	}

	@OnCheckedChanged (R.id.take_medicine_cbox)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (isChecked)
		{
			m_takeMedicineCBox.setText(R.string.take_medicine_finished);
		}
		else
		{
			m_takeMedicineCBox.setText(R.string.take_medicine_wait);
		}
	}

	@OnClick (R.id.reminder_bottom_tips_ll)
	public void clickReminderBottonRegion()
	{
		//今日无提醒
		if (m_rightFuncRegionLl.getVisibility() != View.VISIBLE)
		{
			return;
		}
		//今日有提醒，已完成
		m_mainMsgHandler.go2MedicationReminderAction();
		return;
	}

	@OnClick (R.id.right_func_region_ll)
	public void clickTakeMedicineRightFuncRegion()
	{
		//今日有提醒，已完成
		m_mainMsgHandler.go2MedicationReminderAction();
		return;
	}

	@OnClick (R.id.medicine_reminder_region_ll)
	public void clickMedicineReminderRegion()
	{
		//01. 多余一个，则打开fragment
		if (m_medicineReminderIV.getVisibility() == View.VISIBLE)
		{
			//TODO:添加fragment。
		}
		return;
	}



	/**
	 * override func
	 */
	class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onDown(MotionEvent e)
		{
			m_imgsDisplayVF.stopFlipping();
			m_imgsDisplayVF.setAutoStart(false);
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			if (e2.getX() - e1.getX() > MainActivityConfig.DELTA_MOTION_EVENT)
			{             // 从左向右滑动（左进右出）
				Animation rInAnim = AnimationUtils.loadAnimation(HomeTabFragment.this.getActivity(), R.anim.push_right_in
																);    // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）
				Animation rOutAnim = AnimationUtils.loadAnimation(HomeTabFragment.this.getActivity(), R.anim.push_right_out
																 ); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）

				m_imgsDisplayVF.setInAnimation(rInAnim);
				m_imgsDisplayVF.setOutAnimation(rOutAnim);
				m_imgsDisplayVF.showPrevious();
			}
			else if (e2.getX() - e1.getX() < -MainActivityConfig.DELTA_MOTION_EVENT)
			{         // 从右向左滑动（右进左出）
				Animation lInAnim = AnimationUtils.loadAnimation(HomeTabFragment.this.getActivity(), R.anim.push_left_in
																);        // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
				Animation lOutAnim = AnimationUtils.loadAnimation(HomeTabFragment.this.getActivity(), R.anim.push_left_out
																 );    // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）

				m_imgsDisplayVF.setInAnimation(lInAnim);
				m_imgsDisplayVF.setOutAnimation(lOutAnim);
				m_imgsDisplayVF.showNext();
			}
			m_imgsDisplayVF.startFlipping();
			m_imgsDisplayVF.setAutoStart(true);
			return true;
		}

	}


	/**
	 * inner func
	 */
	public void init()
	{
		Activity activity = getActivity();
		if (activity instanceof MainActivity)
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
		m_gestureDetector = new GestureDetector(m_imgsDisplayVF.getContext(), new MyGestureDetector());
	}

	private void initContent()
	{
		initImgDisplayRegion();
	}

	private void initImgDisplayRegion()
	{
		//展示图片：公司/优惠信息
		ArrayList<Integer> imageIDList = DMainPageImages.getInstance().getImageIDList();
		ImageView          imageView   = null;
		for (int index = 0; index < imageIDList.size(); index++)
		{
			imageView = new ImageView(this.getActivity());
			imageView.setImageResource(imageIDList.get(index));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//			imageView.setOnClickListener(m_handleImageClickEvent);
			imageView.setTag(index);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
																				   LinearLayout.LayoutParams.MATCH_PARENT
			);

			m_imgsDisplayVF.addView(imageView, layoutParams);
		}

		m_imgsDisplayVF.setAutoStart(true);
		m_imgsDisplayVF.setFlipInterval(MainActivityConfig.SWITCH_TIME_MILLISECS);
		if (m_imgsDisplayVF.isAutoStart() && !m_imgsDisplayVF.isFlipping())
		{
			m_imgsDisplayVF.startFlipping();
		}

	}

	private void updateContent()
	{
		//调节图片展示区的大小
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		int     iWidth  = display.getWidth();

		ArrayList<Integer> imageIDList = DMainPageImages.getInstance().getImageIDList();
		if (imageIDList.isEmpty())
			return;

		Drawable drawable   = getResources().getDrawable(imageIDList.get(0));
		int      iImgWidth  = drawable.getIntrinsicWidth();
		int      iImgHeight = drawable.getIntrinsicHeight();

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)m_imgsDisplayRegionLL.getLayoutParams();
		params.width = iWidth;
		params.height = iWidth * iImgHeight / iImgWidth;
		m_imgsDisplayRegionLL.requestLayout();

		m_mainMsgHandler.updateHomeFragmentContent();
	}

	/**
	 * widget:get
	 */
	public TextView getTodayReminderTitleTV()
	{
		return m_todayReminderTitleTV;
	}

	public LinearLayout getImgsDisplayRegionLL()
	{
		return m_imgsDisplayRegionLL;
	}

	public LinearLayout getReminderHeadersLL()
	{
		return m_reminderHeadersLL;
	}

	public LinearLayout getReminderBottomTipsLL()
	{
		return m_reminderBottomTipsLL;
	}

	public TextView getReminderTipsTV()
	{
		return m_reminderTipsTV;
	}

	public LinearLayout getRightFuncRegionLl()
	{
		return m_rightFuncRegionLl;
	}

	public ImageView getTakeMedicineReminderIV()
	{
		return m_takeMedicineReminderIV;
	}

	public TextView getMedicineReminderTimeTV()
	{
		return m_medicineReminderTimeTV;
	}

	public TextView getMedicineNameTV()
	{
		return m_medicineNameTV;
	}

	public TextView getRoseTV()
	{
		return m_roseTV;
	}

	public TextView getMedicineUnitTV()
	{
		return m_medicineUnitTV;
	}

	public CheckBox getTakeMedicineCBox()
	{
		return m_takeMedicineCBox;
	}

	public TextView getMedicineReminderTV()
	{
		return m_medicineReminderTV;
	}

	public LinearLayout getFuncRegionLL()
	{
		return m_funcRegionLL;
	}

	public MainMsgHandler getMainMsgHandler()
	{
		return m_mainMsgHandler;
	}

	public ViewFlipper getImgsDisplayVF()
	{
		return m_imgsDisplayVF;
	}

	public LinearLayout getMedicineReminderRegionLL()
	{
		return m_medicineReminderRegionLL;
	}

	public ImageView getMedicineReminderIV()
	{
		return m_medicineReminderIV;
	}
}
