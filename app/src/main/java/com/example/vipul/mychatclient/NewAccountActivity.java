package com.example.vipul.mychatclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NewAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        mAuth = FirebaseAuth.getInstance();
        final EditText nameText = (EditText)findViewById(R.id.name_edit_text);
        final EditText passwordText = (EditText)findViewById(R.id.password_editText);
        final EditText confirmPasswordEditText = (EditText)findViewById(R.id.confirm_password_edit_text);
        final EditText emailText = (EditText)findViewById(R.id.email_edit_text);
        final EditText phoneText = (EditText)findViewById(R.id.phone_edit_text);
        Button createAccount = (Button)findViewById(R.id.create_account_button);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confPassword = confirmPasswordEditText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(NewAccountActivity.this, "Please enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(NewAccountActivity.this, "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(NewAccountActivity.this, "Please enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    Toast.makeText(NewAccountActivity.this, "Please enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confPassword)){
                    Toast.makeText(NewAccountActivity.this,"password not matching",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(NewAccountActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(NewAccountActivity.this,"Sign up failed",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(NewAccountActivity.this,SignInActivity.class));
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}
