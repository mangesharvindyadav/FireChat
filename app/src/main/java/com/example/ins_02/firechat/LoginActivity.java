package com.example.ins_02.firechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText mUsername,mPassword;
    private Button button;
    private FirebaseAuth mAuth;
    private Context context;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context=this;
        mAuth = FirebaseAuth.getInstance();

        mUsername=findViewById(R.id.username);
        mPassword=findViewById(R.id.password);

        button=findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mUsername.getText().toString().trim();
                String pass=mPassword.getText().toString().trim();

                if (name.equals("")&&pass.equals(""))
                {
                    Toast.makeText(context, "Please Enter all the Fields", Toast.LENGTH_SHORT).show();
                }
                if (name.equals(""))
                {
                    mUsername.setError("Please enter your Email");
                }
                if (pass.equals(""))
                {
                    mPassword.setError("Password can't be Empty");
                }
                if (!name.equals("")&&!pass.equals(""))
                {
                    if (VALID_EMAIL_ADDRESS_REGEX.matcher(name).find())
                    {
                        Signin(name,pass);
                    }
                }

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    private void Signin(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(context,MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                           Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
