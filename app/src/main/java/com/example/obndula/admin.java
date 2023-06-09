package com.example.obndula;

import static android.content.ContentValues.TAG;

import static com.google.firebase.database.ServerValue.TIMESTAMP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class admin extends AppCompatActivity {
    // Firebase Variables
    FirebaseDatabase realTimeDb;
    DatabaseReference reference;

    FirebaseUser    Users;
    FirebaseAuth auth;


    private ImageView addprodimage;
    EditText productname, productdescription, productprice, productid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        Users = auth.getCurrentUser();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);
        // declare variables
        EditText productname = findViewById(R.id.product_name);
        EditText productdescription = findViewById(R.id.description);
        EditText productprice = findViewById(R.id.price);
        ImageView addprodimage = findViewById(R.id.image);
        EditText productid = findViewById(R.id.itemId);

        Button add = findViewById(R.id.addtodatabase);
        TextView gotohomepage = findViewById(R.id.orhomepage);

        // initialize firebase variables
        realTimeDb = FirebaseDatabase.getInstance();
        reference =  realTimeDb.getReference("test items");

        // function to push data to firestore




        // add click event to button
        gotohomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), homepage.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, description, price, id;

                name = String.valueOf(productprice.getText());
                description = String.valueOf(productdescription.getText());
                price = String.valueOf(productprice);
                id = String.valueOf(productid);

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(admin.this,"Enter name",Toast.LENGTH_SHORT ).show();

                }


                else if(TextUtils.isEmpty(description)){
                    Toast.makeText(admin.this,"Type description",Toast.LENGTH_SHORT ).show();

                }



                else if(TextUtils.isEmpty(price)){
                    Toast.makeText(admin.this,"Enter price",Toast.LENGTH_SHORT ).show();

                }
                else if(TextUtils.isEmpty(id)){
                    Toast.makeText(admin.this,"Enter product id",Toast.LENGTH_SHORT ).show();

                }


                else {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                    HashMap<String,String> data=new HashMap<>();
                    data.put("name",name);
                    data.put("description",description);
                    data.put("price",price);
                    data.put("ProductId", id);

                    db.collection("test items")
                           /* .document(productid.getText().toString()).set(data)*/
                            .document(productid.getText().toString()).set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
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
            }
                // get the data to strings
              /* String itemName = name.getText().toString();
                String itemDescription = description.getText().toString();
                String itemPrice = price.getText().toString();




//              // push data to firebase
                // below line is for checking whether the
                // edittext fields are empty or not.
                // else call the method to add
                // data to our database.
               if (TextUtils.isEmpty(itemName) && TextUtils.isEmpty(itemDescription) && TextUtils.isEmpty(itemPrice)  ) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(admin.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                }

                else {


                    ///firestore
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseFirestore db0 = FirebaseFirestore.getInstance();
                    //check if id exist
                    DocumentReference docRef = db0.collection("out-"+Users.getEmail()).document(itemID+Users.getEmail());

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Toast.makeText(admin.this, "You have an item with that id,", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(admin.this, "Please select an id you haven't used before", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    ///firestore
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    FirebaseFirestore db1 = FirebaseFirestore.getInstance();

                                    HashMap<String,String> data=new HashMap<>();
                                    data.put("Item Name",itemName);
                                    data.put("Item Description",itemDescription);

                                    data.put("Item Price","Ksh"+itemPrice);

                                    data.put("email",Users.getEmail());




                                    db.collection("test item")
                                            .document(itemID+Users.getEmail()).set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    Toast.makeText(admin.this, "Item Posted",
                                                            Toast.LENGTH_SHORT).show();

                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                    db1.collection("out-"+Users.getEmail())
                                            .document(itemID+Users.getEmail()).set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    Toast.makeText(admin.this, "Item Posted",
                                                            Toast.LENGTH_SHORT).show();

                                                    finish();

                                                    Intent intent = new Intent(getApplicationContext(), homepage.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                    Toast.makeText(admin.this, "Error adding data",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }

                            }

                        };



                    });

                }};*/
        });}}