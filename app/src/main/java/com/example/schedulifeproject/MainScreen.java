package com.example.schedulifeproject;

import static com.example.schedulifeproject.FBref.refStorage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainScreen extends AppCompatActivity
{
    private EditText ED1;
    public static final String CHANNEL_ID = "main_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        ED1 = findViewById(R.id.editText);
        Intent serviceIntent = new Intent(this, ServiceActivity.class);
        startService(serviceIntent);
    }

    public void mapIntent(View view) {
        Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
        startActivity(intent);
        finish();
    }
    public void NotificationIntent(View view) {
        Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
        startActivity(intent);
        finish();
    }
    public void SaveInStorage(View view) {
        String text = ED1.getText().toString();

        try {

            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = "text_file_" + timestamp + ".txt";

            OutputStreamWriter oSW= new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE));
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write(text);
            bW.close();
            oSW.close();


            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String filePath = "users/" + userId + "/" + fileName;

            StorageReference fileRef = refStorage.child(filePath);

            fileRef.putFile(Uri.fromFile(getFileStreamPath(fileName)))
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            ED1.setText("");
                            Toast.makeText(MainScreen.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainScreen.this, "Nahh",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}