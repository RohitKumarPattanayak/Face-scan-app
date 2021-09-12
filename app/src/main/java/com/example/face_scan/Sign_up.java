package com.example.face_scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_up extends AppCompatActivity {
    EditText Full_name,Email,Password,Name;
    FirebaseAuth A;
    ProgressBar Process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Full_name = findViewById(R.id.user_full_name);
        Email = findViewById(R.id.user_email);
        Name = findViewById(R.id.user_name);
        Password = findViewById(R.id.user_pass);
        Process = findViewById(R.id.progressBar);
        A = FirebaseAuth.getInstance();

        if(A.getCurrentUser() != null){
            Toast.makeText(this, "You already have an account.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Sign_up.this,login.class));
            finish();
        }
    }

    public void user_register(View view) {
        String Uname = Name.getText().toString();
        String fullname = Full_name.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        if(fullname.isEmpty()){
                Full_name.setError("*Field required");
        }else if(email.isEmpty()){
                Email.setError("*Field required");
        }else if(password.length()<=6){
                Password.setError("*Password must be greater than 6 charecters");
        }else if(Uname.isEmpty()){
                Name.setError("*Field required");
        }else if(password.isEmpty()){
                Password.setError("*Field required");
        }else {
            Process.setVisibility(View.VISIBLE);
            //********* Data has been entered ****************//
            //REGISTERATION

            A.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Sign_up.this, "Sucessfully Registered", Toast.LENGTH_SHORT).show();
                        Process.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(Sign_up.this, "Error !!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Process.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    public void Login_link(View view) {
        startActivity(new Intent(Sign_up.this,login.class));
    }
}