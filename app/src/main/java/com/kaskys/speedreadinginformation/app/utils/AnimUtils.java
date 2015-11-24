package com.kaskys.speedreadinginformation.app.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.ui.activity.HomeActivity;

public class AnimUtils {

	public static void homeStartAnim(final Context context, View main, View left, View right){
		Animation leftAnim = AnimationUtils.loadAnimation(context, R.anim.splash_left_out);
		Animation rightAnim = AnimationUtils.loadAnimation(context, R.anim.splash_right_out);
		Animation mainAnim = AnimationUtils.loadAnimation(context, R.anim.activity_home_in);
		left.startAnimation(leftAnim);
		right.startAnimation(rightAnim);
		main.startAnimation(mainAnim);

		mainAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				((HomeActivity)context).removeStartView();
			}
		});
	}
}
