package com.example.randimarie.namequiz;


import android.widget.TextView;



import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizActivityTest {




    private QuizActivity quizActivity = null;
    private TextView scoreText = null;
    private String score;
    private String name = null;


    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Rule
    public ActivityTestRule<QuizActivity> quizActivityTestRule = new ActivityTestRule<QuizActivity>(QuizActivity.class);


    @Before
    public void setUp() throws Exception {
        MainActivity mainActivity = mainActivityTestRule.getActivity();
    }


    @Test
    public void testScoreRight(){
        onView(withId(R.id.button2)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QuizActivity quizActivity = quizActivityTestRule.getActivity();
        scoreText = (TextView) quizActivity.findViewById(R.id.textView);
        score = (scoreText.getText().toString());
        Person person = quizActivity.shownPerson;
        name = person.getName();

        onView(withId(R.id.editText2)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.button5)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(""+1)));

    }

    @Test
    public void testScoreWrong(){
        onView(withId(R.id.button2)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QuizActivity quizActivity = quizActivityTestRule.getActivity();
        scoreText = (TextView) quizActivity.findViewById(R.id.textView);
        score = (scoreText.getText().toString());
        name = "sdfghjkhygtfd";

        onView(withId(R.id.editText2)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.button5)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("")));
    }

    @After
    public void tearDown() throws Exception {
    }

}