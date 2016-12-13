package com.example.z.caipu.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.z.caipu.Bean.FoodDataDb;
import com.example.z.caipu.R;
import com.example.z.caipu.activity.FoodActivity;
import com.example.z.caipu.adapter.AddRecyclerAdapter;
import com.example.z.caipu.adapter.RecyclerLowAdapter;
import com.example.z.caipu.db.DBUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class AddFoodFragment extends Fragment {
    RecyclerView add_recycler;
    AddRecyclerAdapter adapter;
    View v;
    DBUtil util;
    ArrayList<FoodDataDb> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=LayoutInflater.from(getActivity()).inflate(R.layout.addfragment_layout,container,false);
        initView();
        return v;
    }

    private void initView() {
        add_recycler = (RecyclerView) v.findViewById(R.id.add_recycler);
        adapter =new AddRecyclerAdapter(getActivity());
        util =new DBUtil(getActivity());


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getdbadapter();
    }

    private void getdbadapter() {
        LinearLayoutManager manager =new LinearLayoutManager(getActivity());
        add_recycler.setLayoutManager(manager);
        adapter.addlist(util.getList());
        add_recycler.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        list =util.getList();
        getdbadapter();
        adapter.setMyOnclick(new AddRecyclerAdapter.MyOnclick() {
            @Override
            public void click(final int pos) {
                AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity());
                dialog.setTitle("提示").setMessage("是否删除").setIcon(R.mipmap.ic_launcher).setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        util.deltitle(list.get(pos).getId());
                        Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                        list =util.getList();
                        adapter.addlist(list);
                        LinearLayoutManager manager =new LinearLayoutManager(getActivity());
                        add_recycler.setLayoutManager(manager);
                        add_recycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).setPositiveButton("否,我要进入页面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Intent intent =new Intent(getActivity(), FoodActivity.class);
                        intent.putExtra("id",list.get(pos).getId());
                        intent.putExtra("add_pos",pos);
                        startActivity(intent);
                    }
                }).create().show();
            }
        });
    }
}
