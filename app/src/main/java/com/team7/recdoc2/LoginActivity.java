package com.team7.recdoc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView txt_ToSignUp;
    private EditText edtLoginUsername, edtLoginPassword;
    private FirebaseAuth firebaseAuth;
    SharedPreferences localuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        localuser = getSharedPreferences("UserLocal", Context.MODE_PRIVATE);

        edtLoginUsername = findViewById(R.id.edt_email);
        edtLoginPassword = findViewById(R.id.edt_password);
        txt_ToSignUp = findViewById(R.id.txt_ToSignUp);
        btnLogin = findViewById(R.id.btn_login);

        edtLoginUsername.setText("hilmyblaze@gmail.com");
        edtLoginPassword.setText("Darkside1");

        txt_ToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterActivity();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String email = edtLoginUsername.getText().toString();
                String password = edtLoginPassword.getText().toString();

                //validate
                if (email.equals("")) edtLoginUsername.setError("Email is empty!");
                else if (password.equals("")) edtLoginPassword.setError("Password is empty!");
                else {
                   firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               //insert data to local
                               SharedPreferences.Editor editor = localuser.edit();
                               editor.putString("username", firebaseAuth.getCurrentUser().getUid());
                               editor.apply();
                               //ended

                               Toast.makeText(view.getContext(), "berhasil login", Toast.LENGTH_LONG).show();
                               toMainActivity();
                           } else Toast.makeText(view.getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                       }
                   });
                }
            }
        });
    }

    void toRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}