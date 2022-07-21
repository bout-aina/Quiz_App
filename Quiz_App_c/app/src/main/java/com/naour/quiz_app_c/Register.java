package com.naour.quiz_app_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    // Step 1: declaration
    EditText etPrenom, etLogin, etPassword, etConfirmPassword;
    Button bLogin;
    TextView name_text, email_text, password_text, login_title;
    ImageView top_curve;
    FirebaseAuth mAuth;
    ImageView logo;

    CardView register_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Step 2: Recuperation des id
        top_curve = findViewById(R.id.top_curve);
        name_text = findViewById(R.id.name_text);
        email_text = findViewById(R.id.email_text);
        password_text = findViewById(R.id.password_text);
        logo = findViewById(R.id.logo);
        login_title = findViewById(R.id.registration_title);
        register_card = findViewById(R.id.register_card);
        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        top_curve.startAnimation(top_curve_anim);



        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);
        name_text.startAnimation(field_name_anim);
        email_text.startAnimation(field_name_anim);
        password_text.startAnimation(field_name_anim);

        login_title.startAnimation(field_name_anim);

        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
        register_card.startAnimation(center_reveal_anim);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }
        // Step 3: Association de listners
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser() {
        etPrenom = (EditText) findViewById(R.id.etPrenom);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        String nom = etPrenom.getText().toString().trim();
        String email = etLogin.getText().toString().trim();
        String pass1 = etPassword.getText().toString().trim();
        String pass2 = etConfirmPassword.getText().toString().trim();
        if (nom.isEmpty()) {
            etPrenom.setError("entre le nom");
            etPrenom.requestFocus();
            return;
        }
       else  if (email.isEmpty()) {
            etLogin.setError("entre email");
            etLogin.requestFocus();
            return;
        }
        else if (pass1.isEmpty()) {
            etPassword.setError("entre pass1");
            etPassword.requestFocus();
            return;
        }
        else if (pass2.isEmpty()) {
            etConfirmPassword.setError("entre pass2");
            etConfirmPassword.requestFocus();
            return;
        }
       else  if (pass1.length() < 6) {
            etPassword.setError("vous devez entrer un password plus de 6 caractere");
            etPassword.requestFocus();
            return;
        }


        else if (!pass1.equals(pass2)) {
            etConfirmPassword.setError("password incorect");
            etConfirmPassword.requestFocus();
            return;
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, pass1)
                    .addOnCompleteListener(this ,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(nom, email);
                                //getUid => get user Id
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(Register.this, Quizes.class));
                                    }
                                });
                            } else {
                                Toast.makeText(Register.this, "falaid to register ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }
}
