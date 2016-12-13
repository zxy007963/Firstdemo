package com.example.z.caipu.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.z.caipu.Fragment.AddFoodFragment;
import com.example.z.caipu.Fragment.FenleiFragment;
import com.example.z.caipu.Fragment.MainFragment;
import com.example.z.caipu.Fragment.MyHomeFragment;
import com.example.z.caipu.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button shousou_btn;
    FrameLayout main_framelayout;
    FragmentManager fm =getSupportFragmentManager();
    FragmentTransaction transaction =fm.beginTransaction();
    MainFragment mainFragment;
    FenleiFragment fenleiFragment;
    AddFoodFragment addFoodFragment;
    MyHomeFragment myHomeFragment;
    ImageView main_iv,fenlei_iv,shoucang_iv,myhome_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        main_framelayout = (FrameLayout) findViewById(R.id.main_framelayout);
        main_iv = (ImageView) findViewById(R.id.main_iv);
        fenlei_iv = (ImageView) findViewById(R.id.fenlei_iv);
        shoucang_iv = (ImageView) findViewById(R.id.shoucang_iv);
        myhome_iv = (ImageView) findViewById(R.id.myhome_iv);
        shousou_btn = (Button) findViewById(R.id.shousuo_btn);
        if (mainFragment==null){
            mainFragment =new MainFragment();
        }
        if (fenleiFragment==null){
            fenleiFragment =new FenleiFragment();
        }
        if (addFoodFragment==null){
            addFoodFragment =new AddFoodFragment();
        }
        if (myHomeFragment==null){
            myHomeFragment =new MyHomeFragment();
        }
        transaction.add(R.id.main_framelayout,mainFragment);
        transaction.add(R.id.main_framelayout,fenleiFragment);
        transaction.add(R.id.main_framelayout,addFoodFragment);
        transaction.add(R.id.main_framelayout,myHomeFragment);
        transaction.show(mainFragment);
        transaction.hide(fenleiFragment);
        transaction.hide(addFoodFragment);
        transaction.hide(myHomeFragment);
        transaction.commit();
        main_iv.setOnClickListener(this);
        fenlei_iv.setOnClickListener(this);
        shoucang_iv.setOnClickListener(this);
        myhome_iv.setOnClickListener(this);
        shousou_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction transaction =fm.beginTransaction();
        switch (v.getId()){
            case R.id.main_iv:
                shousou_btn.setVisibility(View.VISIBLE);
                transaction.show(mainFragment);
                transaction.hide(fenleiFragment);
                transaction.hide(addFoodFragment);
                transaction.hide(myHomeFragment);
                break;
            case R.id.fenlei_iv:
                shousou_btn.setVisibility(View.GONE);
                transaction.show(fenleiFragment);
                transaction.hide(mainFragment);
                transaction.hide(addFoodFragment);
                transaction.hide(myHomeFragment);
                break;
            case R.id.shoucang_iv:
                shousou_btn.setVisibility(View.GONE);
                transaction.show(addFoodFragment);
                transaction.hide(mainFragment);
                transaction.hide(fenleiFragment);
                transaction.hide(myHomeFragment);
                break;
            case R.id.myhome_iv:
                shousou_btn.setVisibility(View.GONE);
                transaction.show(myHomeFragment);
                transaction.hide(mainFragment);
                transaction.hide(fenleiFragment);
                transaction.hide(addFoodFragment);
                break;
            case R.id.shousuo_btn:
                shousou_btn.setVisibility(View.GONE);
                transaction.show(fenleiFragment);
                transaction.hide(myHomeFragment);
                transaction.hide(mainFragment);
                transaction.hide(addFoodFragment);
                break;
        }
        transaction.commit();
    }
}
