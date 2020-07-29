package com.example.harkka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivitySettings extends AppCompatActivity {
    Button done;
    EditText usernameTextField;
    String newUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        usernameTextField = findViewById(R.id.editTextTextPersonName);
        done = findViewById(R.id.button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Users");
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Getting the user's info from firebase realtime database.
        reference.orderByChild(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String username = snapshot.child(userID + "/username").getValue().toString();
                    usernameTextField.setText(username); //Username from database set to the text field.

                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newUsername = usernameTextField.getText().toString();
                            String nString = "Users/" + userID + "/username";
                            DatabaseReference databaseReference = database.getReference(nString);
                            databaseReference.setValue(newUsername);

                            Intent intent = new Intent(ActivitySettings.this, ActivityMainMenu.class);
                            startActivity(intent);
                        }
                    });
                //}
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}