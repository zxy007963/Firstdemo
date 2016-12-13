package com.example.z.caipu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.caipu.Bean.FoodData;
import com.example.z.caipu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class RecyclerLowAdapter extends RecyclerView.Adapter<RecyclerLowAdapter.MyViewHolder> {
    ArrayList<FoodData> list;
    Context context;
    WindowManager wm;

    public RecyclerLowAdapter(Context context) {
        this.context = context;
        list =new ArrayList<>();
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public ArrayList<FoodData> getList() {
        return list;
    }

    public void setList(ArrayList<FoodData> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.low_recycler_layout,parent,false);
        MyViewHolder viewHolder =new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.with(context).load(list.get(position).getAlbums().get(0)).resize(wm.getDefaultDisplay().getWidth(),300).into(holder.low_iv);
        holder.low_tv.setText(list.get(position).getTitle());
        holder.low_low_tv.setText(list.get(position).getImtro());
        if (myOnclick!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myOnclick.click(position);
                }
            });
        }

    }

    MyOnclick myOnclick;

    public MyOnclick getMyOnclick() {
        return myOnclick;
    }

    public void setMyOnclick(MyOnclick myOnclick) {
        this.myOnclick = myOnclick;
    }

    public interface MyOnclick{
        void click(int pos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView low_iv;
        TextView low_tv,low_low_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            low_iv = (ImageView) itemView.findViewById(R.id.low_iv);
            low_tv = (TextView) itemView.findViewById(R.id.low_tv);
            low_low_tv = (TextView) itemView.findViewById(R.id.low_low_tv);
        }
    }
}

