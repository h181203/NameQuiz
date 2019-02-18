package com.example.randimarie.namequiz;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;



import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DeleteAndAddTest {
    private List<Person> personList;
    private FirebaseFirestore db;
    private Person person;
    private ViewAllActivity viewAllActivity = null;
    private NewEntryActivity newEntryActivity = null;



    @Before
    public void setUp() throws Exception {
        db.collection("persons").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){
                                Person p = d.toObject(Person.class);
                                p.setId(d.getId());
                                personList.add(p);
                            }
                        }
                    }
                });
        viewAllActivity = new ViewAllActivity();
        newEntryActivity = new NewEntryActivity();
        person = new Person();
    }


    @Test
    public void addPersonTest() {
        assertFalse(personList.contains(person));
        newEntryActivity.addPerson(person);
        personList = null;
        db.collection("persons").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){
                                Person p = d.toObject(Person.class);
                                p.setId(d.getId());
                                personList.add(p);
                            }
                        }
                    }
                });
        assertTrue(personList.contains(person));
    }


    @Test
    public void deletePersonTest() {
        personList.add(person);
        viewAllActivity.deletePerson(person);
        personList = null;
        db.collection("persons").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){
                                Person p = d.toObject(Person.class);
                                p.setId(d.getId());
                                personList.add(p);
                            }
                        }
                    }
                });
        assertFalse(personList.contains(person));
    }


}