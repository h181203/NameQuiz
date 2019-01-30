package com.example.randimarie.namequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button quizButton; 
    private Button newButton; 
    private Button viewButton; 
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        viewButton = findViewById(R.id.button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewAllActivity();
            }
        });
        
        quizButton = findViewById(R.id.button2);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuizActivity();
            }
        });
        
        newButton = findViewById(R.id.button3);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewEntryActivity();
            }
        });
    }

    private void openNewEntryActivity() {
        Intent intent = new Intent(this, NewEntryActivity.class);
        startActivity(intent);
    }

    private void openQuizActivity() {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    private void openViewAllActivity() {
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }
}
