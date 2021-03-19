package com.example.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailBox,passwordBox;
    private Button login,create;
    FirebaseAuth auth;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();

        emailBox=findViewById(R.id.loginEmail);
        passwordBox=findViewById(R.id.loginpassword);
        login=findViewById(R.id.loginbtn);
        create=findViewById(R.id.logincreatebtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();

                if (email.isEmpty()) {
                    emailBox.setError("Empty");
                    emailBox.requestFocus();
                } else if (password.isEmpty()) {
                    passwordBox.setError("Empty");
                    passwordBox.requestFocus();
                }else{
                    login();
                }




            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Signup_Activity.class);
                startActivity(intent
                );
            }
        });
    }

    private void login() {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {


                    startActivity(new Intent(MainActivity.this,Dashboard_Activity.class));
                }
                else
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}