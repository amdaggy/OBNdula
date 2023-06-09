package com.example.obndula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity {
    EditText Email, Password;
    MaterialButton buttonlog;
    FirebaseAuth mAuth;



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//
//            Intent intent = new Intent(getApplicationContext(), homepage.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        mAuth =FirebaseAuth.getInstance();


        buttonlog = findViewById(R.id.login);
         TextView signup_page = findViewById(R.id.orsignup);


         signup_page.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), signUp.class);
                 startActivity(intent);
                 finish();

             }
         });



//        signNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        buttonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,  password;
                email = String.valueOf(Email.getText());
                password = String.valueOf( Password.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(login.this,"Enter Email",Toast.LENGTH_SHORT ).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(login.this,"Enter password",Toast.LENGTH_SHORT ).show();
                    return;
                }
                if(password.length() < 8){
                    Toast.makeText(login.this,"password must be more than 8 characters!",Toast.LENGTH_SHORT ).show();

                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(login.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), admin.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


    }


}