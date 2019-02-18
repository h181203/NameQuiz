package com.example.randimarie.namequiz;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String name;

    private EditText input;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        name = preferences.getString("name", null);

        String newName = "Submit your name";
        if(name == null) {

            noNameDialog(newName);
        }
    }

    public void manageDialog(View view){


        String editName = "Edit your name";
        noNameDialog(editName);
    }


    private void noNameDialog(String title) {
        input = new EditText(this);
        input.setText(name);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputName;
                        if(input.getText() == null){
                            inputName = "";
                        }else {
                            inputName = input.getText().toString();
                        }
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name", inputName);
                        editor.apply();

                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    public void openNewEntryActivity(View view) {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        Intent intent = new Intent(this, NewEntryActivity.class);
        startActivity(intent);
    }

    public void openQuizActivity(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void openViewAllActivity(View view) {
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }
}