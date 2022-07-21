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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// Step 1: declaration
// Step 2: Recuperation des id
// Step 3: Association de listners
// Step 4: traitement

//il s'agit d'un code java qui represente une activité
// nommee MainActivity permet d'afficher le layout activity


//R => class regenere par le systeme contenant tous les elements objets ,
// id d'une app en format int sous forme de lien

// Une activite doit avoir au minimum 1 methode nomme on create

// Set Content View => méthode associer un layout a un controleur

// listner Définition d'interface
// pour un rappel à invoquer lorsqu'une vue est cliquée.
public class MainActivity extends AppCompatActivity {
    // Step 1: declaration
    ImageView top_curve;
    EditText etLogin, etPassword;
    Button bLogin;
    TextView tvRegister;
    TextView email_text, password_text, login_title;
    ImageView logo;
    LinearLayout new_user_layout;
    CardView login_card;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top_curve = findViewById(R.id.top_curve);
        email_text = findViewById(R.id.email_text);
        password_text = findViewById(R.id.password_text);
        logo = findViewById(R.id.logo);
        login_title = findViewById(R.id.login_text);
        new_user_layout = findViewById(R.id.new_user_text);
        login_card = findViewById(R.id.login_card);
        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        top_curve.setAnimation(top_curve_anim);




        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);
        email_text.setAnimation(field_name_anim);
        password_text.setAnimation(field_name_anim);

        login_title.setAnimation(field_name_anim);

        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
        login_card.setAnimation(center_reveal_anim);

        Animation new_user_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);
        new_user_layout.setAnimation(new_user_anim);


        etLogin=(EditText) findViewById(R.id.etLogin);
        etPassword=(EditText) findViewById(R.id.etPassword);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        // Step 2: Recuperation des id


        tvRegister = (TextView) findViewById(R.id.tvRegister);
        // Step 3: Association de listners

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Step 4: traitement
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });


    }

    private void userLogin() {
        String emailuser=etLogin.getText().toString().trim();
        String  passuser=etPassword.getText().toString().trim();
        if(emailuser.isEmpty()){
            etLogin.setError("entre le emailuser");
            etLogin.requestFocus();
            return;
        }

        if(passuser.isEmpty()){
            etPassword.setError("entre le password");
            etPassword.requestFocus();
            return;
        }

        if(passuser.length()<6){
            etPassword.setError("entre le password 6 caractere minimum");
            etPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailuser,passuser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to quiz
                    startActivity(new Intent(MainActivity.this,Quizes.class));
                }else{
                    Toast.makeText(MainActivity.this,"falaid to Login Username or password Incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
/*
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("toto")){
                    startActivity(new Intent(MainActivity.this,quiz1.class));
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,register.class));
            }
        });
        */


    }



}