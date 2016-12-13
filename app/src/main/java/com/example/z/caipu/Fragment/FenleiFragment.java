package com.example.z.caipu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.z.caipu.Bean.FenleiData;
import com.example.z.caipu.Bean.FoodData;
import com.example.z.caipu.Bean.Jsonbean;
import com.example.z.caipu.Bean.List;
import com.example.z.caipu.R;
import com.example.z.caipu.activity.FoodActivity;
import com.example.z.caipu.activity.HomeActivity;
import com.example.z.caipu.adapter.FenleiAdapter;
import com.example.z.caipu.adapter.RecyclerLowAdapter;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class FenleiFragment extends Fragment {
    EditText fenlei_et;
    RecyclerView fenlei_recycler,fenlei_shousuo_recycler;
    RequestQueue requestQueue;
    View v;
    FenleiAdapter adapter;
    RecyclerView.LayoutManager manager;
    ArrayList<List> list;
    public static ArrayList<FoodData> ss_list;
    Gson gson;
    RecyclerLowAdapter ss_list_adapter;
    String url ="http://apis.juhe.cn/cook/category?key=40efe01320c8d2188524d3e7a7914141";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =LayoutInflater.from(getActivity()).inflate(R.layout.fenlei_layout,container,false);
        initView();
        return v;
    }

    private void initView() {
        fenlei_et = (EditText) v.findViewById(R.id.fenlei_et);
        fenlei_recycler = (RecyclerView) v.findViewById(R.id.fenlei_recycler);
        fenlei_shousuo_recycler= (RecyclerView) v.findViewById(R.id.fenlei_shousuo_recycler);
        requestQueue = Volley.newRequestQueue(getActivity());
        gson =new Gson();
        ss_list =new ArrayList<>();
        adapter =new FenleiAdapter(getActivity());
        list =new ArrayList<>();
        ss_list_adapter =new RecyclerLowAdapter(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
                StringRequest request =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        FenleiData data =gson.fromJson(s,FenleiData.class);
                        manager =new GridLayoutManager(getActivity(),5);
                        fenlei_recycler.setLayoutManager(manager);
                        Log.e("FenleiFragment", "data.getResult():" + data.getResult());
                        list=data.getResult().get(0).getList();
                        adapter.addlist(list);
                        fenlei_recycler.setAdapter(adapter);
                        Toast.makeText(getActivity(),"设置成功",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(),"发生异常",Toast.LENGTH_LONG).show();
                    }
                });
                    requestQueue.add(request);
                adapter.setMycallback(new FenleiAdapter.MyCallback() {
                    @Override
                    public void callback(int pos) {
                        save_cid(list.get(pos).getId());
                        Intent intent =new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                    }
                });

    }

    private void save_cid(int pos){
        SharedPreferences sp =getActivity().getSharedPreferences("cid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putInt("id",pos);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        fenlei_et.addTextChangedListener(watcher);
        ss_list_adapter.setMyOnclick(new RecyclerLowAdapter.MyOnclick() {
            @Override
            public void click(int pos) {
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                intent.putExtra("ss_pos",pos);
                startActivity(intent);
            }
        });

    }

    TextWatcher watcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                String str = URLEncoder.encode(fenlei_et.getText().toString(),"UTF-8");
                String ss_url="http://apis.juhe.cn/cook/query?key=40efe01320c8d2188524d3e7a7914141&menu="+str+"&rn=10&pn=1";
                StringRequest request =new StringRequest(Request.Method.GET, ss_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Jsonbean jsonbean =gson.fromJson(s,Jsonbean.class);
                        if (jsonbean.getResultcode()==200){
                        ss_list =jsonbean.getResult().getData();
                        LinearLayoutManager manager =new LinearLayoutManager(getActivity());
                        fenlei_shousuo_recycler.setLayoutManager(manager);
                        ss_list_adapter.setList(ss_list);
                        fenlei_shousuo_recycler.setAdapter(ss_list_adapter);}
                        else{
                            Toast.makeText(getActivity(),"无结果",Toast.LENGTH_SHORT).show();
                            ss_list_adapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(request);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };
}
