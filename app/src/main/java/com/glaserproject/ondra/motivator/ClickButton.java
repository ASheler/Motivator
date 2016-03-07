package com.glaserproject.ondra.motivator;

import android.animation.Animator;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * Created by ondra on 2/20/2016.
 */
public final class ClickButton extends MainActivity {
    public ClickButton(){
    }

    public static void pictureButtonSettings(View view){

        View cardView = view.findViewById(R.id.cardPicture);
        LinearLayout layoutOld = (LinearLayout) view.findViewById(R.id.cardPictureLayoutDefault);
        LinearLayout layoutNew = (LinearLayout) view.findViewById(R.id.cardPictureLayoutSettings);
        RelativeLayout settingsButton = (RelativeLayout) view.findViewById(R.id.pictureSettingsClickable);
        Button arrow = (Button) view.findViewById(R.id.pictureMore);


        int finalRadius = (int)Math.hypot(cardView.getWidth()/2, cardView.getHeight()/2);

        Animator animator = ViewAnimationUtils.createCircularReveal(
                cardView,
                (int)cardView.getWidth()/2,
                (int)cardView.getHeight()/2,
                0,
                finalRadius);
        cardView.setBackgroundColor(view.getResources().getColor(R.color.colorCardSelected));
        layoutOld.setVisibility(View.GONE);
        layoutNew.setVisibility(View.VISIBLE);
        settingsButton.setVisibility(View.GONE);
        arrow.setBackground(view.getResources().getDrawable(R.drawable.ic_arrow_up));
        animator.start();
    }

    public static void pictureButtonDefault(View view){
        View cardView = view.findViewById(R.id.cardPicture);
        LinearLayout layoutOld = (LinearLayout) view.findViewById(R.id.cardPictureLayoutDefault);
        LinearLayout layoutNew = (LinearLayout) view.findViewById(R.id.cardPictureLayoutSettings);
        RelativeLayout settingsButton = (RelativeLayout) view.findViewById(R.id.pictureSettingsClickable);
        Button arrow = (Button) view.findViewById(R.id.pictureMore);

        int finalRadius = (int)Math.hypot(cardView.getWidth()/2, cardView.getHeight()/2);

        Animator animator = ViewAnimationUtils.createCircularReveal(
                cardView,
                (int)cardView.getWidth()/2,
                (int)cardView.getHeight()/2,
                0,
                finalRadius);
        cardView.setBackgroundColor(view.getResources().getColor(R.color.colorBackgroundDefault));
        layoutOld.setVisibility(View.VISIBLE);
        layoutNew.setVisibility(View.GONE);
        settingsButton.setVisibility(View.VISIBLE);
        arrow.setBackground(view.getResources().getDrawable(R.drawable.ic_info));
        animator.start();
    }


    public static void alarmButtonSettings(View view){
        View cardView = view.findViewById(R.id.cardAlarm);
        FrameLayout layoutOld = (FrameLayout) view.findViewById(R.id.cardAlarmLayoutDefault);
        LinearLayout layoutNew = (LinearLayout) view.findViewById(R.id.cardAlarmLayoutSettings);
        RelativeLayout settingsButton = (RelativeLayout) view.findViewById(R.id.alarmSettingsClickable);
        Button arrow = (Button) view.findViewById(R.id.alarmMore);

        int finalRadius = (int)Math.hypot(cardView.getWidth()/2, cardView.getHeight()/2);

        Animator animator = ViewAnimationUtils.createCircularReveal(
                cardView,
                (int)cardView.getWidth()/2,
                (int)cardView.getHeight()/2,
                0,
                finalRadius);
        cardView.setBackgroundColor(view.getResources().getColor(R.color.colorCardSelected));
        layoutOld.setVisibility(View.GONE);
        layoutNew.setVisibility(View.VISIBLE);
        settingsButton.setVisibility(View.GONE);
        arrow.setBackground(view.getResources().getDrawable(R.drawable.ic_arrow_up));
        animator.start();
    }

    public static void alarmButtonDefault(View view){
        View cardView = view.findViewById(R.id.cardAlarm);
        FrameLayout layoutOld = (FrameLayout) view.findViewById(R.id.cardAlarmLayoutDefault);
        LinearLayout layoutNew = (LinearLayout) view.findViewById(R.id.cardAlarmLayoutSettings);
        Button arrow = (Button) view.findViewById(R.id.alarmMore);
        RelativeLayout settingsButton = (RelativeLayout) view.findViewById(R.id.alarmSettingsClickable);
        int finalRadius = (int)Math.hypot(cardView.getWidth()/2, cardView.getHeight()/2);

        Animator animator = ViewAnimationUtils.createCircularReveal(
                cardView,
                (int)cardView.getWidth()/2,
                (int)cardView.getHeight()/2,
                0,
                finalRadius);
        cardView.setBackgroundColor(view.getResources().getColor(R.color.colorBackgroundDefault));
        layoutOld.setVisibility(View.VISIBLE);
        layoutNew.setVisibility(View.GONE);
        settingsButton.setVisibility(View.VISIBLE);
        arrow.setBackground(view.getResources().getDrawable(R.drawable.ic_info));
        animator.start();
    }

    public static void randomButtonSettings(View view){
        View cardView = view.findViewById(R.id.cardRandom);
        FrameLayout layoutOld = (FrameLayout) view.findViewById(R.id.cardRandomLayoutDefault);
        LinearLayout layoutNew = (LinearLayout) view.findViewById(R.id.cardRandomLayoutSettings);
        RelativeLayout settingsButton = (RelativeLayout) view.findViewById(R.id.randomSettingsClickable);
        Button arrow = (Button) view.findViewById(R.id.randomMore);

        int finalRadius = (int)Math.hypot(cardView.getWidth()/2, cardView.getHeight()/2);

        Animator animator = ViewAnimationUtils.createCircularReveal(
                cardView,
                (int)cardView.getWidth()/2,
                (int)cardView.getHeight()/2,
                0,
                finalRadius);
        cardView.setBackgroundColor(view.getResources().getColor(R.color.colorCardSelected));
        layoutOld.setVisibility(View.GONE);
        layoutNew.setVisibility(View.VISIBLE);
        settingsButton.setVisibility(View.GONE);
        arrow.setBackground(view.getResources().getDrawable(R.drawable.ic_arrow_up));
        animator.start();
    }

    public static void randomButtonDefault(View view){
        View cardView = view.findViewById(R.id.cardRandom);
        FrameLayout layoutOld = (FrameLayout) view.findViewById(R.id.cardRandomLayoutDefault);
        LinearLayout layoutNew = (LinearLayout) view.findViewById(R.id.cardRandomLayoutSettings);
        RelativeLayout settingsButton = (RelativeLayout) view.findViewById(R.id.randomSettingsClickable);
        Button arrow = (Button) view.findViewById(R.id.randomMore);

        int finalRadius = (int)Math.hypot(cardView.getWidth()/2, cardView.getHeight()/2);

        Animator animator = ViewAnimationUtils.createCircularReveal(
                cardView,
                (int)cardView.getWidth()/2,
                (int)cardView.getHeight()/2,
                0,
                finalRadius);
        cardView.setBackgroundColor(view.getResources().getColor(R.color.colorBackgroundDefault));
        layoutOld.setVisibility(View.VISIBLE);
        layoutNew.setVisibility(View.GONE);
        settingsButton.setVisibility(View.VISIBLE);
        arrow.setBackground(view.getResources().getDrawable(R.drawable.ic_info));
        animator.start();

    }




}
