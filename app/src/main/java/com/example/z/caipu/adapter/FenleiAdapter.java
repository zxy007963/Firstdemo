package com.example.z.caipu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.z.caipu.Bean.List;
import com.example.z.caipu.R;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/8 0008.
 */
public class FenleiAdapter extends RecyclerView.Adapter<FenleiAdapter.MyViewHolder> {
    ArrayList<List> list;
    Context context;


    public FenleiAdapter(Context context) {
        this.context = context;
        list =new ArrayList<>();
    }

    public void addlist(ArrayList<List> arrayList){
        if (list!=null){
            list.clear();
            list.addAll(arrayList);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fenlei_adapter_layout,parent,false);
        MyViewHolder viewHolder =new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.fenlei_adapter_tv.setText(list.get(position).getName());
        if (mycallback!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mycallback.callback(position);
                }
            });
        }


    }
    MyCallback mycallback;

    public MyCallback getMycallback() {
        return mycallback;
    }

    public void setMycallback(MyCallback mycallback) {
        this.mycallback = mycallback;
    }

    public interface MyCallback{
        void callback(int pos);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fenlei_adapter_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            fenlei_adapter_tv= (TextView) itemView.findViewById(R.id.fenlei_adapter_tv);
        }
    }
}
