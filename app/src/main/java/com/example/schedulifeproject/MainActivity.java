package com.example.schedulifeproject;

import static com.example.schedulifeproject.FBref.refUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    private EditText ED1;
    private EditText ED2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ED1 = findViewById(R.id.editText);
        ED2 = findViewById(R.id.editText2);


    }

    public void SignUp(View view) {
         Users User=new Users(ED1.getText().toString(),ED2.getText().toString(),"gmail.com");
         refUsers.child(ED1.getText().toString()).setValue(User);

    }
}