package com.cy.androidpdf.view.customview.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.cy.androidpdf.R;

public class ScanProgressView extends View {
	private float progress = 270;
	private boolean isAnimStart = false;
	private int color = 0xFF0D96FF;

	private Paint p;
	private SweepGradient sg;
	private Paint p1;

	private boolean reset = false;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progress += 2;
			progress %= 360;
			invalidate();
			if (!reset) {
				handler.sendEmptyMessageDelayed(0, 15);
			} else if (reset && progress != 270) {
				handler.sendEmptyMessageDelayed(0, 15);
			} else {
				reset = false;
			}
		};
	};

	public ScanProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setStyle(Style.FILL);

		p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
		p1.setStyle(Style.STROKE);
		p1.setStrokeWidth(3);



		TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ScanProgressView);

		color=arr.getColor(R.styleable.ScanProgressView_color,0);
		p.setColor(color);
	}

	public void startAnim() {
		if (!isAnimStart) {
			isAnimStart = true;
			handler.removeMessages(0);
			handler.sendEmptyMessage(0);
		}
	}

	public void stopAnim() {
		if (isAnimStart) {
			isAnimStart = false;
			handler.removeMessages(0);
		}
	}

	public void stopAnimAndReset() {
		if (isAnimStart) {
			isAnimStart = false;
			reset = true;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float w = getWidth();
		float h = getHeight();
		float halfW = w / 2;
		float halfH = h / 2;

		float radius = halfW > halfH ? halfH : halfW;

		for (int i = 3; i >= 0; i--) {
			p1.setStrokeWidth(i < 3 ? 2 : 3);
			canvas.drawCircle(halfW, halfH, radius * (i + 1) / 4 - 2, p1);
		}

		float t = (float) (radius / (Math.sqrt(2)));
		float dx = halfW - radius;
		float dy = halfH - radius;

		canvas.drawLine(dx, dy + radius, dx + radius * 2, dy + radius, p1);
		canvas.drawLine(dx + radius, dy, dx + radius, dy + dx + radius * 2, p1);
		canvas.drawLine(dx + radius - t, dy + radius - t, dx + radius + t, dy
				+ radius + t, p1);
		canvas.drawLine(dx + radius + t, dy + radius - t, dx + radius - t, dy
				+ radius + t, p1);

		canvas.save();
		canvas.rotate(progress, halfW, halfH);
		canvas.drawCircle(halfW, halfH, radius, p);
		canvas.restore();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setGradient(w, h);
	}

	private void setGradient(int w, int h) {
		p1.setColor(color);
		if (w > 0 && h > 0) {
			sg = new SweepGradient(w / 2.0F, h / 2.0F, new int[] { 0x00FFFFFF,
					0x00FFFFFF, color }, null);
			p.setShader(sg);
			invalidate();
		}
	}

	public void setProgress(float progress) {
		this.progress = progress * 359 - 90;
		postInvalidate();
	}

	public void setColor(int color) {
		this.color = color;

		setGradient(getWidth(), getHeight());
	}

}
