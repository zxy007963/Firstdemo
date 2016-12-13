package com.example.z.caipu.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.z.caipu.R;
import com.example.z.caipu.activity.LoginAcitivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class MyHomeFragment extends Fragment implements View.OnClickListener,PlatformActionListener {
    Button login_btn,zhuce_btn;
    ImageView qq_login_iv,sina_login_iv;
    String demo_id,demo_ps;
    EditText id_et,ps_et;

    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= LayoutInflater.from(getActivity()).inflate(R.layout.myhome_layout,container,false);
        initView();
        return v;
    }

    private void initView() {
        login_btn = (Button) v.findViewById(R.id.login_btn);
        zhuce_btn = (Button) v.findViewById(R.id.zhuce_btn);
        qq_login_iv= (ImageView) v.findViewById(R.id.qq_login_iv);
        sina_login_iv= (ImageView) v.findViewById(R.id.sina_login_iv);
        id_et = (EditText) v.findViewById(R.id.id_et);
        ps_et = (EditText) v.findViewById(R.id.ps_et);
        ShareSDK.initSDK(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login_btn.setOnClickListener(this);
        zhuce_btn.setOnClickListener(this);
        qq_login_iv.setOnClickListener(this);
        sina_login_iv.setOnClickListener(this);
        id_et.addTextChangedListener(watcher);
        ps_et.addTextChangedListener(watcher);
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent(getActivity(), LoginAcitivity.class);
        switch (view.getId()){
            case R.id.login_btn:
                getUserInfo();
                if (id_et.getText().toString().equals(demo_id) && ps_et.getText().toString().equals(demo_ps)) {
                    Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.zhuce_btn:
                startActivity(intent);
                break;
            case R.id.qq_login_iv:
                qq_login();
                break;
            case R.id.sina_login_iv:
                startActivity(intent);
                break;


        }
    }

    TextWatcher watcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {//EditText改变前的监听

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {//改变中的监听

        }

        @Override
        public void afterTextChanged(Editable s) {//改变后的监听
            String str_id = id_et.getText().toString();
            String str_ps =ps_et.getText().toString();
            if(str_id.length()>0&&str_ps.length()>0) {
                if (str_id.length() > 5) {
                    id_et.setError("ID过长");
                    login_btn.setEnabled(false);
                }else if(str_ps.length()>5){
                    ps_et.setError("ps过长");
                    login_btn.setEnabled(false);
                }else{
                    login_btn.setEnabled(true);
                }
            }else{
                login_btn.setEnabled(false);
            }
        }
    };


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    private void qq_login(){
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.SSOSetting(false);
        qq.setPlatformActionListener(this);
        qq.showUser(null);
//        qq.authorize();
    }

    void getUserInfo(){
        SharedPreferences ps = getActivity().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        demo_id =ps.getString("ID","1234");
        demo_ps =ps.getString("PS","1234");

    }
}
