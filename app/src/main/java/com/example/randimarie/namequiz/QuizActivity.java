package com.example.randimarie.namequiz;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    FirebaseFirestore db;
    Person shownPerson;

    EditText submitText;

    List<Person> personList;
    List<Person> quizList;

    int score = 0;
    int counter;


    ImageView photoView;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        submitText = findViewById(R.id.editText2);
        db = FirebaseFirestore.getInstance();
        photoView = findViewById(R.id.imageView2);
        scoreText = findViewById(R.id.textView);
        scoreText.setText(null);


        personList = new ArrayList<>();

        Task<QuerySnapshot> res = db.collection("persons").get();

                res.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){
                                Person p = d.toObject(Person.class);
                                p.setId(d.getId());
                                personList.add(p);

                            }
                            addToListAndShuffle();
                        }
                    }
                });

    }


    @Override
    protected void onResume(){
        super.onResume();



        score = 0;
        counter = 0;


            if (!personList.isEmpty()) {
                addToListAndShuffle();
            }
        }

    private void addToListAndShuffle(){
        quizList = new ArrayList<Person>(personList);
        Collections.shuffle(quizList);
        shownPerson = quizList.get(counter);
        setNewImage(Uri.parse(shownPerson.getImage()));
    }


    public void submitPerson(View view){

        String personName = submitText.getText().toString();
        String ferdigText = "Hello, quiz is done";

        if(personName.equalsIgnoreCase(shownPerson.getName())){
            score += 1;
            scoreText.setText(""+score);
        }

        if(counter < personList.size()-1){
            counter++;
            shownPerson = quizList.get(counter);
            setNewImage(Uri.parse(shownPerson.getImage()));
        } else {
            setNewImage(null);
            scoreText.setText(score + "/"+ personList.size());

            submitText.setText(ferdigText);
            submitText.setEnabled(false);

        }
    }

    private void setNewImage(Uri uri){
        photoView.setImageURI(uri);
        photoView.getLayoutParams().height = 500;
        photoView.getLayoutParams().width = 500;
    }
}
