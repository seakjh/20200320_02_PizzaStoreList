package com.example.a20200320_02_pizzastorelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a20200320_02_pizzastorelist.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

    }
}
