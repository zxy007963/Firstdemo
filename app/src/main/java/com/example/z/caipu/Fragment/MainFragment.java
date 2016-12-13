package com.example.z.caipu.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.z.caipu.Bean.FoodData;
import com.example.z.caipu.Bean.Jsonbean;
import com.example.z.caipu.R;
import com.example.z.caipu.activity.FoodActivity;
import com.example.z.caipu.adapter.GuideViewPagerAdapter;
import com.example.z.caipu.adapter.RecyclerLowAdapter;
import com.example.z.caipu.adapter.RecyclerTitleAdapter;
import com.example.z.caipu.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class MainFragment extends Fragment {
    protected ViewPager main_title_vp;
    protected RecyclerView main_mid_recycler,main_low_Recycler;
    private ImageView[] img =new ImageView[4];
    private int[] imgId ={R.mipmap.img_one,R.mipmap.img_two,R.mipmap.img_three,R.mipmap.img_four};
    private ImageView[] point =new ImageView[4];
    private int[] pointIds = {R.id.main_iv1, R.id.main_iv2, R.id.main_iv3, R.id.main_iv4};
    GuideViewPagerAdapter adapter;
    public static ArrayList<FoodData> title_list,low_list;
    RecyclerTitleAdapter list_adapter;
    RecyclerLowAdapter low_list_adapter;
//    SwipeRefreshLayout main_sr;
    View v;
    ProgressDialog progressDialog;
    String title_url;
    String low_url;
    int cid;
    int oldposition= 0;
    int currentposition;
    Gson gson =new Gson();
    ScheduledExecutorService mSchedule;//线程池，用来定时轮播
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String title_str =msg.getData().getString("title_str");
                    Jsonbean jsonbean =gson.fromJson(title_str,Jsonbean.class);
                    title_list =jsonbean.getResult().getData();
                    LinearLayoutManager manager =new LinearLayoutManager(getActivity());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    main_mid_recycler.setLayoutManager(manager);
                    list_adapter.setList(title_list);
                    progressDialog.dismiss();
                    main_mid_recycler.setAdapter(list_adapter);
                    break;
                case 1:
                    String low_str =msg.getData().getString("low_str");
                    Jsonbean jsonbean1 =gson.fromJson(low_str,Jsonbean.class);
                    low_list =jsonbean1.getResult().getData();
                    LinearLayoutManager manager1 =new LinearLayoutManager(getActivity());
                    main_low_Recycler.setLayoutManager(manager1);
                    low_list_adapter.setList(low_list);
                    main_low_Recycler.setAdapter(low_list_adapter);
                    break;
                case 2:
                    main_title_vp.setCurrentItem(currentposition);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=LayoutInflater.from(getActivity()).inflate(R.layout.mainfragment_layout,container,false);
        initView();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPoint(0);
        initImg();
        title_list =new ArrayList<>();
        low_list =new ArrayList<>();
        progressDialog =new ProgressDialog(getActivity());
        progressDialog.show();
        low_list_adapter =new RecyclerLowAdapter(getActivity());
        list_adapter =new RecyclerTitleAdapter(getActivity());
        cid =getCid();
        title_url="http://apis.juhe.cn/cook/index?key=40efe01320c8d2188524d3e7a7914141&cid="+cid+"&rn=10&pn=0";
        low_url ="http://apis.juhe.cn/cook/index?key=40efe01320c8d2188524d3e7a7914141&cid="+cid+"&rn=20&pn=11";

    }

    @Override
    public void onStart() {
        super.onStart();
        //开启一个单个后台线程
        mSchedule= Executors.newSingleThreadScheduledExecutor();
        mSchedule.scheduleWithFixedDelay(new ViewPagerTask(),3,3, TimeUnit.SECONDS);//1.任务事件，2.时间（）延迟多少时间后执行操作，3.时间（）按照这个时间周期性重复执行任务，4.Time.SECONDS设置单位（小时，分钟，秒）
    }

    private class ViewPagerTask implements Runnable{
        @Override
        public void run() {
            currentposition =(currentposition+1)%imgId.length;
            handler.sendEmptyMessage(2);
        }
    }


    private void initView() {
        main_title_vp = (ViewPager) v.findViewById(R.id.main_title_vp);
        main_mid_recycler = (RecyclerView)v.findViewById(R.id.main_mid_recycler);
        main_low_Recycler = (RecyclerView) v.findViewById(R.id.main_low_recycler);
//        main_sr = (SwipeRefreshLayout) v.findViewById(R.id.main_sr);


        for (int i =0;i<pointIds.length;i++){
            point[i] = (ImageView) v.findViewById(pointIds[i]);
        }
        main_title_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initPoint(position);
                oldposition =position;
                currentposition =position;
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
            ImageView iv =new ImageView(getActivity());
            iv.setImageResource(imgId[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            img[i] = iv;
        }
        adapter =new GuideViewPagerAdapter(img);
        main_title_vp.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String title_str=HttpUtil.getJson_str(title_url);
                    Message message =new Message();
                    Bundle bundle =new Bundle();
                    bundle.putString("title_str",title_str);
                    Log.e("MainFragment", title_str);
                    message.setData(bundle);
                    message.what =0;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String low_str=HttpUtil.getJson_str(low_url);
                    Message message =new Message();
                    Bundle bundle =new Bundle();
                    bundle.putString("low_str",low_str);
                    Log.e("MainFragment", low_str);
                    message.setData(bundle);
                    message.what =1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        final Intent intent =new Intent(getActivity(), FoodActivity.class);
        list_adapter.setMyOnclick(new RecyclerTitleAdapter.MyOnclick() {
            @Override
            public void click(int pos) {
                intent.putExtra("title_pos",pos);
                startActivity(intent);

            }
        });
        low_list_adapter.setMyOnclick(new RecyclerLowAdapter.MyOnclick() {
            @Override
            public void click(int pos) {
                intent.putExtra("low_pos",pos);
                startActivity(intent);
            }
        });
    }

    public int getCid(){
        SharedPreferences sp =getActivity().getSharedPreferences("cid", Context.MODE_PRIVATE);
        return sp.getInt("id",1);
    }
}
