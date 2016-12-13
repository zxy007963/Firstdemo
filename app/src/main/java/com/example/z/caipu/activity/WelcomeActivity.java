package com.example.z.caipu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.caipu.R;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class WelcomeActivity extends AppCompatActivity {
    protected ImageView wel_iv;
    protected TextView wel_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        initView();
        initFlash();
        initTitle();
        final Intent intent =new Intent(WelcomeActivity.this,HomeActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        }).start();
    }

    private void initFlash() {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation translateAnimation =new TranslateAnimation(Animation.RELATIVE_TO_SELF,0
                ,Animation.RELATIVE_TO_SELF,0
                ,Animation.RELATIVE_TO_SELF,0
                ,Animation.RELATIVE_TO_SELF,-4);
        translateAnimation.setDuration(2000);
        set.addAnimation(translateAnimation);
        set.setFillAfter(true);
        wel_iv.startAnimation(set);
    }

    private void initTitle() {
        AnimationSet set =new AnimationSet(true);
        AlphaAnimation alphaAnimation =new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        set.addAnimation(alphaAnimation);
        wel_tv.setAnimation(set);
    }

    private void initView() {
        wel_iv = (ImageView) findViewById(R.id.wel_iv);
        wel_tv = (TextView) findViewById(R.id.wel_tv);
    }

}
