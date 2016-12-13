package com.example.z.caipu.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class GuideViewPagerAdapter extends PagerAdapter {
    private ImageView img[] =new ImageView[4];

    public GuideViewPagerAdapter(ImageView[] img) {
        this.img = img;
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(img[position]);
        return img[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(img[position]);
    }
}

