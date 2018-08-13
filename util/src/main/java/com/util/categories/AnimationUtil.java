package com.util.categories;

import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.util.R;


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

    public static void pulse(View view) {
        animate(view, R.anim.pulse_out_repeat, false);
    }

    public static void fadeIn(View view) {
        animate(view, android.R.anim.fade_in, false);
    }

    public static void fadeOut(View view) {
        animate(view, android.R.anim.fade_out, false);
    }

    public static void animateFromResource(final View view, int animationId) {
        animate(view, animationId, false);
    }

    public static void animateInFromResource(final View view, int animationId) {
        animate(view, animationId, false);
    }

    public static void animateOutFromResource(final View view, int animationId) {
        animate(view, animationId, true);
    }

    private static void animate(final View view, int animationId, final boolean out) {
        if (view == null) return;

        final Animation animation = AnimationUtils.loadAnimation(view.getContext(), animationId);
        final float alpha = view.getAlpha();

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (out) {
                    view.setVisibility(View.GONE);
                    view.setAlpha(alpha);
                }
            }
        });

        if (!out) view.setVisibility(View.VISIBLE);

        view.startAnimation(animation);
    }
}
