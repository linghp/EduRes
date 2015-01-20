package com.cqvip.edures.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

public class CleanableEditText extends EditText {
	private Drawable mRightDrawable;
	private Drawable InvisibleDrawable;
	private boolean isHasFocus;

	public CleanableEditText(Context context) {
		super(context);
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		// getCompoundDrawables:
		// Returns drawables for the left, top, right, and bottom borders.
		Drawable[] drawables = this.getCompoundDrawables();

		// 取得right位置的Drawable
		// 即我们在布局文件中设置的android:drawableRight
		mRightDrawable = drawables[2];
		InvisibleDrawable = new InvisibleDrawable(this);
		InvisibleDrawable.setBounds(mRightDrawable.getBounds());
		// displayDrawable();
		// 设置焦点变化的监听
		this.setOnFocusChangeListener(new FocusChangeListenerImpl());
		// 设置EditText文字变化的监听
		this.addTextChangedListener(new TextWatcherImpl());
		// 初始化时让右边clean图标不可见
		setClearDrawableVisible(false);
	}

	// private void displayDrawable()
	// {
	// if ((isFocused()) && (!TextUtils.isEmpty(getText().toString())))
	// {
	// setCompoundDrawables(getCompoundDrawables()[0],
	// getCompoundDrawables()[1], mRightDrawable, getCompoundDrawables()[3]);
	// //this.c = true;
	// return;
	// }
	// setCompoundDrawables(getCompoundDrawables()[0],
	// getCompoundDrawables()[1], InvisibleDrawable, getCompoundDrawables()[3]);
	// // this.c = false;
	// }

	/**
	 * 当手指抬起的位置在clean的图标的区域 我们将此视为进行清除操作 getWidth():得到控件的宽度
	 * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
	 * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
	 * getPaddingRight():clean的图标右边缘至控件右边缘的距离 于是: getWidth() -
	 * getTotalPaddingRight()表示: 控件左边到clean的图标左边缘的区域 getWidth() -
	 * getPaddingRight()表示: 控件左边到clean的图标右边缘的区域 所以这两者之间的区域刚好是clean的图标的区域
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:

			boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
					&& (event.getX() < (getWidth() - getPaddingRight()));
			if (isClean) {
				setText("");
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private class FocusChangeListenerImpl implements OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			isHasFocus = hasFocus;
			if (isHasFocus) {
				boolean isVisible = !TextUtils.isEmpty(getText().toString().trim());
				setClearDrawableVisible(isVisible);
			} else {
				setClearDrawableVisible(false);
			}
		}

	}

	// 当输入结束后判断是否显示右边clean的图标
	private class TextWatcherImpl implements TextWatcher {
		@Override
		public void afterTextChanged(Editable s) {
			boolean isVisible = !TextUtils.isEmpty(getText().toString().trim());
			setClearDrawableVisible(isVisible);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	// 隐藏或者显示右边clean的图标
	protected void setClearDrawableVisible(boolean isVisible) {
		if (isVisible) {
			setCompoundDrawables(getCompoundDrawables()[0],
					getCompoundDrawables()[1], mRightDrawable,
					getCompoundDrawables()[3]);
		} else {
			setCompoundDrawables(getCompoundDrawables()[0],
					getCompoundDrawables()[1], InvisibleDrawable,
					getCompoundDrawables()[3]);
		}
	}

	// 显示一个动画,以提示用户输入
	public void setShakeAnimation() {
		//防止下次点击动画不起效果
		this.clearAnimation();
		this.setAnimation(shakeAnimation(5));
	}

	// CycleTimes动画重复的次数
	public Animation shakeAnimation(int CycleTimes) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
		translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

	final class InvisibleDrawable extends Drawable {
		InvisibleDrawable(CleanableEditText paramEditTextWithClearButton) {
		}

		public final void draw(Canvas paramCanvas) {
		}

		public final int getOpacity() {
			return 0;
		}

		public final void setAlpha(int paramInt) {
		}

		public final void setColorFilter(ColorFilter paramColorFilter) {
		}
	}
}