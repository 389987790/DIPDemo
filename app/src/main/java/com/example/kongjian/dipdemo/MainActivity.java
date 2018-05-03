package com.example.kongjian.dipdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kongjian.dipdemo.annotation.ContentView;
import com.example.kongjian.dipdemo.annotation.OnClick;
import com.example.kongjian.dipdemo.annotation.ViewInject;
import com.example.kongjian.dipdemo.util.InjectUtils;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_1)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
    }
    @OnClick({R.id.tv_1})
    public void mHandle(View btn) {
        textView.setText("mHandle");
        Toast.makeText(this, "mHandle", Toast.LENGTH_SHORT).show();
    }
}
