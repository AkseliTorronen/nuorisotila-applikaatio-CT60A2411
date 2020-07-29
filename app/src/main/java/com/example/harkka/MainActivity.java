package com.example.harkka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Sign up activity.
public class MainActivity extends AppCompatActivity {
    EditText emailInput, passInput, username;
    Button buttonSignUp;
    TextView text;
    FirebaseAuth firebaseAuth;
    ToggleButton organizerOrNot;
    String organizer = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.email);
        passInput = findViewById(R.id.pass);
        buttonSignUp = findViewById(R.id.signUp);
        text = findViewById(R.id.textView);
        username = findViewById(R.id.userName);

        //Checking the organizer button
        organizerOrNot = findViewById(R.id.toggleButton);
        organizerOrNot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    organizer = "true";
                } else {
                    organizer = "false";
                }
            }
        });

        //Setting up sign up button.
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                final String user = username.getText().toString();
                String pass = passInput.getText().toString();
                if(email.isEmpty()){
                    emailInput.setError("Enter email");
                    emailInput.requestFocus();
                } else if(user.isEmpty()){
                    username.setError("Enter username");
                    username.requestFocus();
                } else if(pass.isEmpty()){
                    passInput.setError("Enter password");
                    passInput.requestFocus();
                } else if(email.isEmpty() && pass.isEmpty() && user.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter email, username and password!", Toast.LENGTH_SHORT).show();
                } else if(!(email.isEmpty() && pass.isEmpty() && user.isEmpty())){ //Checking that user has text in all the fields.
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Sign up failed! Try again later!", Toast.LENGTH_SHORT).show();
                            }else{ //Saving the data to firebase realtime database.
                                String userID = firebaseAuth.getCurrentUser().getUid();
                                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                                Users newUser = new Users(organizer, user, userID);
                                currentUser.setValue(newUser);

                                loadActivity("MAIN");
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        text.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                loadActivity("LOGIN");
            }
        });
    }
    public void loadActivity(String s){
        if(s.equals("MAIN")){
            Intent intent = new Intent(MainActivity.this, ActivityMainMenu.class);
            startActivity(intent);
        } else if(s.equals("LOGIN")){
            Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
            startActivityForResult(intent, 1);
        } else if(s.equals("VIEW")){
            Intent intent = new Intent(MainActivity.this, ActivityViewEvent.class);
            startActivityForResult(intent, 1);
        }
    }
    @Override
    public void onBackPressed(){

    }
}
