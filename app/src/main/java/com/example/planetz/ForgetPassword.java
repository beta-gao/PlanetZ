package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPassword extends AppCompatActivity {
    FirebaseAuth auth;
    Button ForgetButton;
    TextView BackToLog;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forget), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ForgetButton = findViewById(R.id.ResetButton);
        emailText = findViewById(R.id.email);
        BackToLog = findViewById(R.id.toReg);
        auth = FirebaseAuth.getInstance();

        ForgetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = String.valueOf(emailText.getText());
                if (TextUtils.isEmpty(email)) {
                    emailText.setError("Email field can't be empty");
                    //Toast.makeText();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                }
                else {
                    SendLinktoReset();
                }
            }
        });
        BackToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            //link to the login page
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);//replace with register class
                startActivity(i);
                finish();
            }
        });
    }
    void SendLinktoReset() {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused){
                Toast.makeText(ForgetPassword.this, "A link has been sent to your Email!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgetPassword.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
