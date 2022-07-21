package com.example.quizapp;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Quiz1 extends AppCompatActivity {
    Button button;
    public  static int qCounter ;
     RadioGroup radioGroup;
     TextView tvScore,tvQuestionNo;
    RadioButton selectedRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);



        button=(Button) findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1) {
                            selectedRadioButton = findViewById(selectedRadioButtonId);
                            String selectedRbText = selectedRadioButton.getText().toString();
                            qCounter=0;
                            if(selectedRbText.equals("à droite") )
                            {

                                qCounter+=1;
                                Intent intent = new Intent(Quiz1.this, Quiz2.class);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Intent intent = new Intent(Quiz1.this, Quiz2.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Toast.makeText (Quiz1.this, "Nothing selected from the radio group",
                                    Toast. LENGTH_SHORT). show();

                        }

                    }
                });
                       // startActivity(new Intent(Quiz1.this,Quiz2.class));

    }
}