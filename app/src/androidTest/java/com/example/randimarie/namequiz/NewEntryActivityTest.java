package com.example.randimarie.namequiz;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewEntryActivityTest {

    FirebaseFirestore db = null;


    @Rule
    public ActivityTestRule<NewEntryActivity> newEntryActivityActivityTestRule = new ActivityTestRule<NewEntryActivity>(NewEntryActivity.class);


    @Before
    public void setUp() throws Exception {
        db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> res = db.collection("persons").get();
    }


    @Test
    public void addNewEntryTest() throws InterruptedException {

        int personCount = fillList().size();
        NewEntryActivity newEntryActivity = newEntryActivityActivityTestRule.getActivity();
        Random rand = new Random();
        String name = "testman"+rand.nextInt()*1000;

        onView(withId(R.id.editText)).perform(typeText(name), closeSoftKeyboard());
        
        ImageView image = (ImageView) newEntryActivity.findViewById(R.id.imageView);
        Uri.Builder uriBuilder = new Uri.Builder().path("content://media/external/images/media/512");
        Uri uri =  uriBuilder.build();
        image.setImageURI(uri);

        onView(withId(R.id.button4)).perform(click());
        Thread.sleep(500);

        ArrayList<Person> personList = fillList();

        assertEquals(personCount , personList.size() - 1);

    }


    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }

    private ArrayList<Person> fillList() throws InterruptedException {
        final ArrayList<Person> personList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
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
                }
            }
        });
        while(!res.isComplete())
        Thread.sleep(100);
        return personList;
    }

}