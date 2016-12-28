package com.example.vipul.mychatclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        final EditText emailText = (EditText)findViewById(R.id.email_editText);
        final EditText passwordText = (EditText)findViewById(R.id.password_editText);
        TextView forgotPassText = (TextView)findViewById(R.id.text_forgot_password);
        Button loginButton = (Button)findViewById(R.id.login_button);
        Button createAccountButton = (Button)findViewById(R.id.create_new_account_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(SignInActivity.this, "Please enter email address!", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password))
                    Toast.makeText(SignInActivity.this,"Please enter password!",Toast.LENGTH_SHORT).show();
                if(password.length()<6)
                    Toast.makeText(SignInActivity.this,"Password too short, enter atleast 6 characters",Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                            Toast.makeText(SignInActivity.this,"Authentication failed!",Toast.LENGTH_SHORT).show();
                        else {
                            startActivity(new Intent(SignInActivity.this, MyChatsActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,NewAccountActivity.class));
                finish();
            }
        });

        forgotPassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,ResetPasswordActivity.class);
                intent.putExtra(ResetPasswordActivity.EMAIL_TO_RESET_PASSWORD,emailText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
