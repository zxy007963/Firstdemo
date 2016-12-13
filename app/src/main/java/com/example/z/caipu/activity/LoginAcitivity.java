package com.example.z.caipu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.z.caipu.R;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class LoginAcitivity extends AppCompatActivity {
    EditText et_id,et_ps;
    Button btn;
    String login_id =null;
    String login_ps =null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        et_id= (EditText) findViewById(R.id.et_id);
        et_ps= (EditText) findViewById(R.id.et_ps);
        btn= (Button) findViewById(R.id.btn);

        TextWatcher watcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                login_id=et_id.getText().toString();
                login_ps=et_ps.getText().toString();
            }
        };
        et_id.addTextChangedListener(watcher);
        et_ps.addTextChangedListener(watcher);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUseInfo();
                finish();

            }
        });

    }
    void saveUseInfo(){
        SharedPreferences sp =getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("ID",login_id);
        editor.putString("PS",login_ps);
        editor.commit();
    }
}

