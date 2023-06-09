package com.example.obndula;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class signUp extends AppCompatActivity {

    EditText UsernameText, UserEmail,UserPassword ;
    MaterialButton createaccount;
    FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up);

        UsernameText = findViewById(R.id.username);
        UserEmail = findViewById(R.id.email);
        UserPassword = findViewById(R.id.password);
        mAuth =FirebaseAuth.getInstance();

        TextView loginNow = findViewById(R.id.orLogin);
        createaccount = findViewById(R.id.signupbutton);

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username, Useremail, Userpassword;
                Username = String.valueOf(UsernameText.getText());
                Useremail = String.valueOf( UserEmail.getText());
                Userpassword = String.valueOf(UserPassword.getText());

                if(TextUtils.isEmpty(Username)){
                    Toast.makeText(signUp.this,"Enter name",Toast.LENGTH_SHORT ).show();

                }
                else if(TextUtils.isEmpty(Useremail)){
                    Toast.makeText(signUp.this,"Enter email",Toast.LENGTH_SHORT ).show();

                }


                else if(TextUtils.isEmpty(Userpassword)){
                    Toast.makeText(signUp.this,"Enter password",Toast.LENGTH_SHORT ).show();

                }
                else if(Userpassword.length() < 8){
                    Toast.makeText(signUp.this,"password must be more than 8 characters!",Toast.LENGTH_SHORT ).show();

                }
                else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    HashMap<String,String> data=new HashMap<>();
                    data.put("Username",Username);
                    data.put("UserEmail",Useremail);
                    data.put("UserPassword",Userpassword);

                    db.collection("Users")
                            .document(UserEmail.getText().toString()).set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }

                mAuth.createUserWithEmailAndPassword(Useremail, Userpassword)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(signUp.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(signUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

}