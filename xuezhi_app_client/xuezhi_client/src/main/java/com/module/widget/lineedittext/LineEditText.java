package com.module.widget.lineedittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/9/30.
 */

public class LineEditText extends EditText
{

	private Paint m_paint;

	/**
	 * @param context
	 * @param attrs
	 */
	public LineEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		m_paint = new Paint();

		m_paint.setStyle(Paint.Style.STROKE);
		m_paint.setColor(Color.BLACK);
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		//      画底线
		canvas.drawLine(0, this.getHeight() - 2, this.getWidth() - 1, this.getHeight() - 2, m_paint);
	}
}