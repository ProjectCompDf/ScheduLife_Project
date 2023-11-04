package com.example.schedulifeproject;

import static com.example.schedulifeproject.FBref.refUsers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText ED1;
    private EditText ED2;
    private EditText ED3;
    private FirebaseAuth mAuth;
    private Users user;
    private Button signUpBT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ED1 = findViewById(R.id.editText);
        ED2 = findViewById(R.id.editText2);
        mAuth = FirebaseAuth.getInstance();
        user = new Users();
        signUpBT = findViewById(R.id.button4);
        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sign_in.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("NotConstructor")
    public void Login(View view)

    {
        if(TextUtils.isEmpty(ED1.getText()))
        {
            Toast.makeText(Login.this, "Enter your Username", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(ED2.getText()))
        {
            Toast.makeText(Login.this, "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        String thisUserName = ED1.getText().toString();

        Query query = refUsers.orderByChild("username").equalTo(thisUserName);

        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    // This loop will run once for the child with the specified name
                    user = userSnapshot.getValue(Users.class); // Assuming User is your data model

                }
                mAuth.signInWithEmailAndPassword(user.getEmail().toString(), ED2.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Login.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                    try
                                    {
                                        throw task.getException(); // Throw an exception to handle different cases
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        Toast.makeText(Login.this, "Email is not registered, create an account with that Email.",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(Login.this, "Incorrect password",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseNetworkException e) {
                                        Toast.makeText(Login.this, "Network connection problem",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(Login.this, "Something went wrong. Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Handle the error
            }
        });



    }


    public void SignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), Sign_in.class);
        startActivity(intent);
        finish();
    }

    public void Guest(View view) {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void MapIntent(View view) {
        Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
        startActivity(intent);
        finish();
    }
}