package com.example.schedulifeproject;

import static com.example.schedulifeproject.FBref.refUsers;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreen extends AppCompatActivity
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
        Toast.makeText(this, "You are now signed in", Toast.LENGTH_SHORT).show();
    }
}