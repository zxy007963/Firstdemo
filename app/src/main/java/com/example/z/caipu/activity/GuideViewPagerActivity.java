package com.example.z.caipu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.caipu.R;
import com.example.z.caipu.adapter.GuideViewPagerAdapter;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class GuideViewPagerActivity extends AppCompatActivity {
    private ImageView[] img =new ImageView[4];
    private int[] imgId ={R.mipmap.img_one,R.mipmap.img_two,R.mipmap.img_three,R.mipmap.img_four};
    private ImageView[] point =new ImageView[4];
    private int[] pointIds = {R.id.guide_iv1, R.id.guide_iv2, R.id.guide_iv3, R.id.guide_iv4};
    protected ViewPager guide_vp;
    protected TextView guide_tv;
    GuideViewPagerAdapter adapter;
    protected int time;
    protected Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            guide_tv.setText("剩余"+time+"秒跳转");
            time--;
            if(time>=0){
                handler.sendEmptyMessageDelayed(0,1000);
            }else{
                Intent intent =new Intent(GuideViewPagerActivity.this,WelcomeActivity.class);
                startActivity(intent);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guide);
        initView();
        initImg();
        initPoint(0);
        guide_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initPoint(position);
                if (position ==3){
                    time =3;
                    guide_tv.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessage(0);
                }else {
                    guide_tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPoint(int pos) {
        for (int i =0;i<point.length;i++){
            if (i==pos){
                point[i].setAlpha(1f);
            }else {
                point[i].setAlpha(0.3f);
            }
        }
    }

    private void initImg() {
        for (int i =0;i<img.length;i++){
            ImageView iv =new ImageView(getBaseContext());
            iv.setImageResource(imgId[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            img[i] = iv;
        }
        adapter =new GuideViewPagerAdapter(img);
        guide_vp.setAdapter(adapter);

    }

    private void initView() {
        guide_vp = (ViewPager) findViewById(R.id.guide_vp);
        guide_tv = (TextView) findViewById(R.id.guide_tv);
        for (int i =0;i<pointIds.length;i++){
            point[i] = (ImageView) findViewById(pointIds[i]);
        }
    }
}

