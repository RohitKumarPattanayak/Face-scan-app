package com.example.face_scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    ProgressBar L_process;
    EditText LUsenname,LPassword;
    FirebaseAuth A;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        L_process = findViewById(R.id.login_progressBar);
        LUsenname = findViewById(R.id.user_username);
        LPassword = findViewById(R.id.User_Lpass);
        A = FirebaseAuth.getInstance();

        if(A.getCurrentUser() != null){
            Toast.makeText(this, "Login succesfull", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(login.this,MainActivity.class));
            finish();
        }
    }

    public void user_login(View view) {
        L_process.setVisibility(View.VISIBLE);
        String login_email = LUsenname.getText().toString();
        String login_password = LPassword.getText().toString();
        if (login_email.isEmpty()) {
            LUsenname.setError("*Field required");
            L_process.setVisibility(View.INVISIBLE);
        } else if (login_password.isEmpty()) {
            LPassword.setError("*Field required");
            L_process.setVisibility(View.INVISIBLE);
        } else {
            A.signInWithEmailAndPassword(login_email, login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(login.this, "Login Sucessfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, MainActivity.class));
                        L_process.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(login.this, "Error !!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void sign_in_link(View view) {
        startActivity(new Intent(login.this,Sign_up.class));
    }

    public void forget_pass(View view) {
        final EditText reset = new EditText(view.getContext());
        final AlertDialog.Builder pass_reset = new AlertDialog.Builder(view.getContext());
        pass_reset.setTitle("Reset password");
        pass_reset.setMessage("Enter your email to get the reset link :");
        pass_reset.setView(reset);

        pass_reset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                L_process.setVisibility(View.VISIBLE);
                String mail = reset.getText().toString();
                A.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(login.this, "Reset link sent to the mail", Toast.LENGTH_SHORT).show();
                        L_process.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Error !!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        L_process.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        pass_reset.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        pass_reset.show();
//        Toast.makeText(this, "hiiiii", Toast.LENGTH_SHORT).show();
    }
}
