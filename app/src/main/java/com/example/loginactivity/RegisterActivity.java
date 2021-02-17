package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText mUsername,mEmail,mPhone,mPassword;
    TextView LoginBtn;
    Button RegistrationBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsername=findViewById(R.id.etUsername);
        mEmail=findViewById(R.id.etEmail);
        mPassword=findViewById(R.id.etPassWord);
        mPhone=findViewById(R.id.etPhoneNumber);
        RegistrationBtn=findViewById(R.id.btRegister);
        LoginBtn=findViewById(R.id.Exister);

        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        RegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)) {

                    mPassword.setError("Password is Required");
                    return;
                }
                if (password.length()<6) {
                    mPassword.setError("PassWord must be Greater than 6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //Registering the user
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "Creation Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        }


                    });
                }


        });
    }
}