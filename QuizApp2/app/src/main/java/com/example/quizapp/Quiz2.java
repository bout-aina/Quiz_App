package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.quizapp.Quiz1.qCounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Quiz2 extends AppCompatActivity {
    Button button;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        button=(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedRbText = selectedRadioButton.getText().toString();
                    if(selectedRbText.equals("Non") )
                    {
                        qCounter+=1;

                        Intent intent = new Intent(Quiz2.this, Quiz3.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Intent intent = new Intent(Quiz2.this, Quiz3.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText (Quiz2.this, "Nothing selected from the radio group",
                            Toast. LENGTH_SHORT). show();

                }


            }
        });
    }
}