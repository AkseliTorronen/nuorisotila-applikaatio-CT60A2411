package com.example.harkka;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity {
    EditText emailInput, passInput;
    Button buttonSignIn;
    TextView text;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.email);
        passInput = findViewById(R.id.pass);
        buttonSignIn = findViewById(R.id.signIn);
        text = findViewById(R.id.textView);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseUser != null) {
                    Toast.makeText(ActivityLogin.this, "You're logged in", Toast.LENGTH_SHORT).show();
                    loadActivity("MAIN");
                }
            }
        };

        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String pass = passInput.getText().toString();
                if (email.isEmpty()) {
                    emailInput.setError("Enter email");
                    emailInput.requestFocus();
                } else if (pass.isEmpty()) {
                    passInput.setError("Enter password");
                    passInput.requestFocus();
                } else if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(ActivityLogin.this, "Enter email and password!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pass.isEmpty())) { //Sending the login information to firebase
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(ActivityLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(ActivityLogin.this, "Login failed! Try again later!", Toast.LENGTH_SHORT).show();
                            } else {
                                loadActivity("MAIN");
                            }
                        }
                    });
                } else {
                    Toast.makeText(ActivityLogin.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(signUp);
            }
        });
    }
        @Override
        protected void onStart() {
            super.onStart();
            firebaseAuth.addAuthStateListener(authStateListener);
    }
    public void loadActivity(String s){
        if(s.equals("MAIN")){
            Intent intent = new Intent(ActivityLogin.this, ActivityMainMenu.class);
            startActivityForResult(intent, 1);
        }
    }
    @Override
    public void onBackPressed(){

    }
}
