package com.module.widget.fragment.waitting_wheel_mask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.module.frame.BaseFragment;
import com.xuzhi_client.xuzhi_app_client.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/2.
 */
public class WaittingWheelMask extends BaseFragment implements View.OnTouchListener
{
	@Bind (R.id.waitting_wheel_img) ImageView mWaittingWheelImg = null;
	@Bind (R.id.waitting_wheel_sz_img) ImageView mWaittingWheelSzImg = null;
	@Bind (R.id.waitting_wheel_fz_img) ImageView mWaittingWheelFzImg = null;

	private static WaittingWheelMask s_waittingWheelMask = new WaittingWheelMask();

	public WaittingWheelMask GetInstance()
	{
		return s_waittingWheelMask;
	}

	public WaittingWheelMask()
	{

	}

	@Override
	public View onCreateViewAction(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_waitting_wheel_mask, container, false);
	}

	@Override
	public void onAfterCreateAction()
	{
		initRotate();
	}

	private void initRotate()
	{
		LinearInterpolator lin = new LinearInterpolator();
		RotateAnimation rotateAnimation_sz = new RotateAnimation(0, 359,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		rotateAnimation_sz.setDuration(7200);
		rotateAnimation_sz.setRepeatCount(Animation.INFINITE);
		rotateAnimation_sz.setRepeatMode(Animation.INFINITE);
		rotateAnimation_sz.setInterpolator(lin);
		mWaittingWheelSzImg.startAnimation(rotateAnimation_sz);
		RotateAnimation rotateAnimation_fz = new RotateAnimation(0, 359,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		rotateAnimation_fz.setDuration(600);
		rotateAnimation_fz.setRepeatCount(Animation.INFINITE);
		rotateAnimation_fz.setRepeatMode(Animation.INFINITE);
		rotateAnimation_fz.setInterpolator(lin);
		mWaittingWheelFzImg.startAnimation(rotateAnimation_fz);
	}

	@Override
	public void onDestoryViewAction()
	{
	}

	@Override
	public BaseFragment getOwner()
	{
		return this;
	}

	//防止点击穿透
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		return true;
	}


}
