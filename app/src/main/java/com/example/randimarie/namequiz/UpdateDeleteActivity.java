package com.example.randimarie.namequiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateDeleteActivity extends AppCompatActivity {

    TextView textView;

    Person person;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        db = FirebaseFirestore.getInstance();
        person = (Person) getIntent().getSerializableExtra("person");
        textView = findViewById(R.id.textView2);
    }




    public void upgradePerson(View view){
    }

    public void deletePerson(View view){
        db.collection("persons").document(person.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdateDeleteActivity.this, "Person deleted", Toast.LENGTH_LONG).show();
                            finish();

                    }
                }

        });

    }

}
