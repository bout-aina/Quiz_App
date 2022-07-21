package com.naour.quiz_app_c;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Score extends AppCompatActivity {
    Button bLogout, bTry,map_gm;
    com.mikhaellopez.circularprogressbar.CircularProgressBar progressBar;
    TextView tvScore,wrongscore,truescore,name_text;
    int qCounter,fail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        // Initialize Firebase Auth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = auth.getCurrentUser();
        if (currentuser == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //Initiatize Firebase Auth

        tvScore =(TextView) findViewById(R.id.tvScore);
        name_text =(TextView) findViewById(R.id.name_text);
        wrongscore =(TextView) findViewById(R.id.wrongscore);
        truescore =(TextView) findViewById(R.id.truescore);
        progressBar=(com.mikhaellopez.circularprogressbar.CircularProgressBar) findViewById(R.id.progressBar);
        bLogout=(Button) findViewById(R.id.bLogout);
        map_gm=(Button) findViewById(R.id.map_gm);
        bTry=(Button) findViewById(R.id.bTry);
        // getter instancier une activité
        Intent intent=getIntent();
        qCounter=intent.getIntExtra("qCounter",0) ;
        fail=intent.getIntExtra("wrongscore",0) ;

        wrongscore.setText(fail+"");
        truescore.setText(qCounter+"");
        tvScore.setText(100*qCounter/5+" %");
        progressBar.setProgress(100*qCounter/5);



        
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("users").child(currentuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null)
                {
                    name_text.setText(user.nom);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Toast.makeText(getApplicationContext(),score+"",Toast.LENGTH_SHORT).show();
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logoutuser();
            }
        });
        map_gm.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View v) {
                Intent intent=new Intent(Score.this,maps.class);
                intent.putExtra("qCounter",qCounter);
                startActivity(intent);
                finish();

            }
        });
        bTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Score.this,Quizes.class));
            }
        });

    }
    private void Logoutuser () {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}