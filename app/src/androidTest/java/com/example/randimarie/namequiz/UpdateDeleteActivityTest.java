package com.example.randimarie.namequiz;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static android.support.v4.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class UpdateDeleteActivityTest {

    MainActivity mainActivity = null;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<ViewAllActivity> viewAllActivityActivityTestRule = new ActivityTestRule<ViewAllActivity>(ViewAllActivity.class);

    @Rule
    public ActivityTestRule<UpdateDeleteActivity> updateDeleteActivityTestRule = new ActivityTestRule<UpdateDeleteActivity>(UpdateDeleteActivity.class);


    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();
    }

    @Test
    public void deleteTest() throws InterruptedException {
        onView(withId(R.id.button)).perform(click());

        Thread.sleep(500);

        ViewAllActivity viewAllActivity = viewAllActivityActivityTestRule.getActivity();
        RecyclerView recyclerView = (RecyclerView) viewAllActivity.findViewById(R.id.recyclerView);
        int personCount = recyclerView.getChildCount();

        if(personCount > 0) {

            PersonAdapter pa = (PersonAdapter) recyclerView.getAdapter();
            Person person = pa.getPerson(recyclerView.getChildCount()-1);
            Intent intent = new Intent(viewAllActivity, UpdateDeleteActivity.class);
            intent.putExtra("person", person);
            startActivity(viewAllActivity, intent, null);

            UpdateDeleteActivity updateDeleteActivity = updateDeleteActivityTestRule.getActivity();

            onView(withId(R.id.button6)).perform(click());

            int survivors = countPeople();

            assertTrue(personCount-1 == survivors);

        }
    }

    private int countPeople() throws InterruptedException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<Person> personList = new ArrayList<>();
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
        Thread.sleep(100);
        return personList.size();
    }

}