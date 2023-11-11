package com.example.schedulifeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreen extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        Intent serviceIntent = new Intent(this, ServiceActivity.class);
        startService(serviceIntent);
    }

    public void SignUp(View view) {
    }
}