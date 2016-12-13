package com.example.z.caipu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.z.caipu.Bean.FoodData;
import com.example.z.caipu.Bean.Jsonbean;
import com.example.z.caipu.Fragment.FenleiFragment;
import com.example.z.caipu.Fragment.MainFragment;
import com.example.z.caipu.R;
import com.example.z.caipu.adapter.FoodAdapter;
import com.example.z.caipu.db.DBUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class FoodActivity extends AppCompatActivity {
    ArrayList<FoodData> title_list,low_list,ss_list,add_list;
    TextView food_title_tv,food_main_tv,food_other_tv;
    ImageView food_main_iv,food_back,food_shoucang_iv,food_fenxiang_iv;
    FoodAdapter adapter;
    RecyclerView food_main_recycler;
    DBUtil util;
    Gson gson;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_layout);
        title_list =new ArrayList<>();
        low_list =new ArrayList<>();
        ss_list =new ArrayList<>();
        add_list =new ArrayList<>();
        gson =new Gson();
        requestQueue = Volley.newRequestQueue(getBaseContext());
        ShareSDK.initSDK(getBaseContext());
        util =new DBUtil(getBaseContext());
        adapter =new FoodAdapter(getBaseContext());
        final LinearLayoutManager manager =new LinearLayoutManager(getBaseContext());
        initView();
        Intent intent =getIntent();
        final int pos =intent.getIntExtra("title_pos",-1);
        final int pos_low =intent.getIntExtra("low_pos",-1);
        final int ss_pos =intent.getIntExtra("ss_pos",-1);
        final int add_pos =intent.getIntExtra("add_pos",-1);
        Log.e("FoodActivity", "add_pos:" + add_pos);
        if (pos!=-1) {
            title_list = MainFragment.title_list;
            food_title_tv.setText(title_list.get(pos).getTitle().toString());
            food_main_tv.setText(title_list.get(pos).getImtro());
            food_other_tv.setText(title_list.get(pos).getBurden());
            Picasso.with(getBaseContext()).load(title_list.get(pos).getAlbums().get(0)).into(food_main_iv);
            food_main_recycler.setLayoutManager(manager);
            adapter.setList(title_list.get(pos).getSteps());
            food_main_recycler.setAdapter(adapter);
            food_fenxiang_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showShare(title_list.get(pos).getTitle().toString(),title_list.get(pos).getImtro(),title_list.get(pos).getAlbums().get(0));
                }
            });
        }else if(pos_low!=-1){
            low_list =MainFragment.low_list;
            Log.e("FoodActivity", "low_list:" + low_list.size());
            food_title_tv.setText(low_list.get(pos_low).getTitle().toString());
            food_main_tv.setText(low_list.get(pos_low).getImtro());
            food_other_tv.setText(low_list.get(pos_low).getBurden());
            Picasso.with(getBaseContext()).load(low_list.get(pos_low).getAlbums().get(0)).into(food_main_iv);
            food_main_recycler.setLayoutManager(manager);
            adapter.setList(low_list.get(pos_low).getSteps());
            food_main_recycler.setAdapter(adapter);
            food_fenxiang_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showShare(low_list.get(pos_low).getTitle().toString(),low_list.get(pos_low).getImtro(),low_list.get(pos_low).getAlbums().get(0));
                }
            });
        }
        else if(ss_pos!=-1){
            ss_list = FenleiFragment.ss_list;
            Log.e("FoodActivity", "low_list:" + ss_list.size());
            food_title_tv.setText(ss_list.get(ss_pos).getTitle().toString());
            food_main_tv.setText(ss_list.get(ss_pos).getImtro());
            food_other_tv.setText(ss_list.get(ss_pos).getBurden());
            Picasso.with(getBaseContext()).load(ss_list.get(ss_pos).getAlbums().get(0)).into(food_main_iv);
            food_main_recycler.setLayoutManager(manager);
            adapter.setList(ss_list.get(ss_pos).getSteps());
            food_main_recycler.setAdapter(adapter);
            food_fenxiang_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showShare(ss_list.get(ss_pos).getTitle().toString(),ss_list.get(ss_pos).getImtro(),ss_list.get(ss_pos).getAlbums().get(0));
                }
            });

        }
        else if(add_pos!=-1){
            int id =intent.getIntExtra("id",-1);
            String url ="http://apis.juhe.cn/cook/queryid?key=40efe01320c8d2188524d3e7a7914141&id="+id+"";
            StringRequest request =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Jsonbean jsonbean =gson.fromJson(s,Jsonbean.class);
                    add_list =jsonbean.getResult().getData();
                    food_title_tv.setText(add_list.get(0).getTitle().toString());
                    food_main_tv.setText(add_list.get(0).getImtro());
                    food_other_tv.setText(add_list.get(0).getBurden());
                    Picasso.with(getBaseContext()).load(add_list.get(0).getAlbums().get(0)).into(food_main_iv);
                    food_main_recycler.setLayoutManager(manager);
                    adapter.setList(add_list.get(0).getSteps());
                    food_main_recycler.setAdapter(adapter);
                    food_fenxiang_iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showShare(add_list.get(0).getTitle().toString(),add_list.get(0).getImtro(),add_list.get(0).getAlbums().get(0));
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getBaseContext(),"发生异常",Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(request);
        }
        food_shoucang_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos!=-1){
                    if (util.seekConut(title_list.get(pos).getImtro())==0) {
                        util.addUrl(title_list.get(pos).getId(), title_list.get(pos).getTitle(), "", title_list.get(pos).getImtro(), "", "", title_list.get(pos).getAlbums().get(0));
                        Snackbar.make(view,"收藏成功",Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(view,"该菜谱已收藏",Snackbar.LENGTH_SHORT).show();
                    }
                }else if(pos_low!=-1){
                    if (util.seekConut(low_list.get(pos_low).getImtro())==0){
                    util.addUrl(low_list.get(pos_low).getId(),low_list.get(pos_low).getTitle(),"",low_list.get(pos_low).getImtro(),"","",low_list.get(pos_low).getAlbums().get(0));
                        Snackbar.make(view,"收藏成功",Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(view,"该菜谱已收藏",Snackbar.LENGTH_SHORT).show();
                    }
                }else if (ss_pos!=-1){
                    if (util.seekConut(ss_list.get(ss_pos).getImtro())==0){
                        util.addUrl(ss_list.get(ss_pos).getId(),ss_list.get(ss_pos).getTitle(),"",ss_list.get(ss_pos).getImtro(),"","",ss_list.get(ss_pos).getAlbums().get(0));
                        Snackbar.make(view,"收藏成功",Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(view, "该菜谱已收藏", Snackbar.LENGTH_SHORT).show();
                    }
                }else if (add_pos!=-1) {
                    if (util.seekConut(add_list.get(add_pos).getImtro()) == 0) {
                        util.addUrl(add_list.get(add_pos).getId(), add_list.get(add_pos).getTitle(), "", add_list.get(add_pos).getImtro(), "", "", add_list.get(add_pos).getAlbums().get(0));
                        Snackbar.make(view, "收藏成功", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(view, "该菜谱已收藏", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initView() {
        food_title_tv= (TextView) findViewById(R.id.food_title_tv);
        food_main_tv= (TextView) findViewById(R.id.food_main_tv);
        food_other_tv= (TextView) findViewById(R.id.food_other_tv);
        food_main_iv = (ImageView) findViewById(R.id.food_main_iv);
        food_back = (ImageView) findViewById(R.id.food_back);
        food_fenxiang_iv = (ImageView) findViewById(R.id.food_fenxiang_iv);
        food_shoucang_iv = (ImageView) findViewById(R.id.food_shoucang_iv);
        food_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        food_main_recycler = (RecyclerView) findViewById(R.id.food_main_recycler);
    }

    private void showShare(String title,String imtro,String url) {
        OnekeyShare oks = new OnekeyShare();
        Platform platform = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        platform.SSOSetting(false);
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title);
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText(imtro);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(url);
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
