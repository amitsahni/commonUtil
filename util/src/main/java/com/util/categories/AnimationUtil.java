package com.util.categories;

import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 * The type Animation util.
 */
public class AnimationUtil {


    /**
     * Instantiates a new Animation util.
     */
    protected AnimationUtil() {
    }

    /**
     * Make a View Blink for a desired duration
     *
     * @param view     mView that will be animated
     * @param duration for how long in ms will it blink
     * @param offset   start offset of the animation
     * @return returns the same mView with animation properties
     */
    public static View blink(View view, int duration, int offset) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        anim.setStartOffset(offset);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
        return view;
    }

    /**
     * Animate.
     *
     * @param view the mView
     * @param anim the anim
     */
    public static void animate(View view, @AnimRes int anim) {
        Animation animation =
                AnimationUtils.loadAnimation(view.getContext(), anim);
        view.startAnimation(animation);
    }
}
