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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team7.recdoc2.model.model.Stats;
import com.team7.recdoc2.model.model.User;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edt_emailSignUp, edt_usernameSignUp, edt_passwordSignUp;
    private Button btn_SignUp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final SharedPreferences localuser = getSharedPreferences("UserLocal", Context.MODE_PRIVATE);

        edt_emailSignUp = findViewById(R.id.edt_emailSignUp);
        edt_usernameSignUp = findViewById(R.id.edt_usernameSignUp);
        edt_passwordSignUp = findViewById(R.id.edt_passwordSignUp);
        btn_SignUp = findViewById(R.id.btn_SignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String username = edt_usernameSignUp.getText().toString();
                final String email = edt_emailSignUp.getText().toString();
                final String password = edt_passwordSignUp.getText().toString();

                if (username.equals("")) edt_usernameSignUp.setError("Username is empty!");
                else if (email.equals("")) edt_emailSignUp.setError("Password is empty!");
                else if (password.equals("")) edt_passwordSignUp.setError("Password is empty!");
                else {
                    if (true) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "Account has been Created!", Toast.LENGTH_LONG).show();

                                    //insert data to local
                                    SharedPreferences.Editor editor = localuser.edit();
                                    editor.putString("username", firebaseAuth.getCurrentUser().getUid());
                                    editor.apply();
                                    //ended

                                    addToDatabase(username, email);

                                    toMenuActivity();
                                } else Toast.makeText(view.getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (firebaseAuth.getCurrentUser().isAnonymous()) {
                        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "Account has been Created and Linked!", Toast.LENGTH_LONG).show();

                                    //insert data to local
                                    SharedPreferences.Editor editor = localuser.edit();
                                    editor.putString("username", firebaseAuth.getCurrentUser().getUid());
                                    editor.apply();
                                    //ended

                                    toMenuActivity();
                                } else Toast.makeText(view.getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    void toMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void addToDatabase(String username, String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://recdoc2-19c2f-default-rtdb.firebaseio.com/");
        DatabaseReference ref = database.getReference();
        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = ref.child("users/" + userId);

        Map<String, User> users = new HashMap<>();
        users.put("profile", new User(username, email));
        userRef.setValue(users);

        Map<String, Stats> stats = new HashMap<>();
        userRef.child("stats").setValue( new Stats(0,0,0,"",""));

    }
}