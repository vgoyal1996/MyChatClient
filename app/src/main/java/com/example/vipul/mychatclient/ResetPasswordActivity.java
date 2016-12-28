package com.example.vipul.mychatclient;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    public static final String EMAIL_TO_RESET_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        final String receivedEmail = (String)getIntent().getExtras().get(EMAIL_TO_RESET_PASSWORD);
        final EditText emailText = (EditText)findViewById(R.id.email_for_reset_editText);
        Button resetButton = (Button)findViewById(R.id.reset_password_button);
        TextView back = (TextView)findViewById(R.id.back_to_signIn_text);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = emailText.getText().toString();
                if(!enteredEmail.equals(receivedEmail))
                    Toast.makeText(ResetPasswordActivity.this,"Email not found",Toast.LENGTH_SHORT).show();
                else{
                    auth.sendPasswordResetEmail(enteredEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Toast.makeText(ResetPasswordActivity.this,"Instructions sent to your email to reset password",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(ResetPasswordActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
