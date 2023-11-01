package com.example.schedulifeproject;

import static com.example.schedulifeproject.FBref.refUsers;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_in extends AppCompatActivity {

    private EditText ED1;
    private EditText ED2;
    private EditText ED3;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ED1 = findViewById(R.id.editText);
        ED2 = findViewById(R.id.editText2);
        ED3 = findViewById(R.id.editText3);
        mAuth = FirebaseAuth.getInstance();

    }

    //    Toast.makeText(this, "You are now signed in", Toast.LENGTH_SHORT).show();
    public void SignUp(View view)
    {
        Users User=new Users(ED1.getText().toString(),ED2.getText().toString(),ED3.getText().toString());
        Users User2=new Users("Avihay.com","123.456", "avihayHaimBartal@gmail.com");
        refUsers.child(User.getUsername()).setValue(User);


        mAuth.createUserWithEmailAndPassword(ED3.getText().toString(), ED2.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Sign_in.this, "You are now signed in", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Sign_in.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}