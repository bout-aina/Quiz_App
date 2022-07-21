package com.naour.quiz_app_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Quizes extends AppCompatActivity {
    private StorageReference mStorageReference;
    RadioGroup rg;
    RadioButton rb1,rb2,rb;
    Button bNext;
    int total=0;
    TextView num,questionsTxt,nn;
    ImageView image_view;

    int qCounter=0;
    int wrongscore= 0;
    DatabaseReference reference;
    String RepCorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);

        rg=(RadioGroup) findViewById(R.id.rg);
        rb1=(RadioButton) findViewById(R.id.rb1);
        rb2=(RadioButton) findViewById(R.id.rb2);
        bNext=(Button) findViewById(R.id.bNext);
        num=(TextView) findViewById(R.id.num);
        nn=(TextView) findViewById(R.id.nn);
        questionsTxt=(TextView) findViewById(R.id.questionsTxt);
        image_view=(ImageView)findViewById(R.id.image_view);

        updateQuestion();
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(),"Merci de choisir une réponse S.V.P !",Toast.LENGTH_SHORT).show();
                }
                else{
                    rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                    if(rb.getText().toString().equals(RepCorrect)){
                        qCounter+=1;
                        //Toast.makeText(getApplicationContext(),score+"",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        wrongscore+=1;
                    }
                    updateQuestion();
                }
            }
        });
    }

    private void updateQuestion() {
        total++;
        if (total > 5)
        {
            // open the result Activity
            Intent intent=new Intent(this,Score.class);

            intent.putExtra("qCounter",qCounter);
            intent.putExtra("wrongscore",wrongscore);
            startActivity(intent);
            //overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            overridePendingTransition(R.anim.exit,R.anim.entry);
            finish();

        }else{
            rg.clearCheck();

            reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //A DataSnapshot instance contains data from a Firebase Database location
                    Question question = dataSnapshot.getValue(Question.class);
                    mStorageReference = FirebaseStorage.getInstance().getReference().child("pictures/"+question.getImage()+".jpg");
                    try {
                        //creation d'un fichier local
                         File localFile = File.createTempFile("image:"+question.getImage(), ".jpg");
                        mStorageReference.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(
                                                localFile.getAbsolutePath());
                                        image_view.setImageBitmap(bitmap);
                                       // ((ImageView) findViewById(R.id.image_view)).setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               // Toast.makeText(Quizes.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    num.setText(total+"");
                    questionsTxt.setText(question.getQuestion()+"");
                    rb1.setText(question.getOption1()+"");
                    rb2.setText(question.getOption2()+"");
                    RepCorrect=question.getAnswer().trim();

                            //Toast.makeText(getApplicationContext(),score+"",Toast.LENGTH_SHORT).show();
                        }






                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}