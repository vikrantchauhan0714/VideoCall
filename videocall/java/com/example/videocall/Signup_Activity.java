package com.example.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup_Activity extends AppCompatActivity {
    private EditText emailbox, passwordbox, namebox;
    private Button signUp, alreadyAccount;
    FirebaseAuth auth;
    FirebaseFirestore database;
    String name, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);


        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        namebox = findViewById(R.id.createNameText);
        emailbox = findViewById(R.id.loginEmail);
        passwordbox = findViewById(R.id.loginpassword);
        signUp = findViewById(R.id.signupbtn);
        alreadyAccount = findViewById(R.id.alrdyaccntbtn);

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup_Activity.this, MainActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = namebox.getText().toString();
                email = emailbox.getText().toString();
                password = passwordbox.getText().toString();

                if (name.isEmpty()) {
                    namebox.setError("Empty");
                    namebox.requestFocus();
                } else if (email.isEmpty()) {
                    emailbox.setError("Empty");
                    emailbox.requestFocus();
                } else if (password.isEmpty()) {
                    passwordbox.setError("Empty");
                    passwordbox.requestFocus();
                } else {
                    uploadData();
                }

            }
        });

    }

    private void uploadData() {
        final Data data = new Data();
        data.setName(name);
        data.setEmail(email);
        data.setPassword(password);


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    database.collection("Users")
                            .document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(Signup_Activity.this, MainActivity.class));

                        }
                    });
                    Toast.makeText(Signup_Activity.this, "Account is created", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(Signup_Activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


}
