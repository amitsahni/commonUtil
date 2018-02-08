package com.util.categories;

import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public final class AnimationUtil {


    protected AnimationUtil() {
    }

    public static View blink(View view, int duration, int offset) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        anim.setStartOffset(offset);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
        return view;
    }

    public static void animate(View view, @AnimRes int anim) {
        Animation animation =
                AnimationUtils.loadAnimation(view.getContext(), anim);
        view.startAnimation(animation);
    }
}
