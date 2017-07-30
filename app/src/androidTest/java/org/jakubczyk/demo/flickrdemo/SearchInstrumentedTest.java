package org.jakubczyk.demo.flickrdemo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.AutoCompleteTextView;

import org.jakubczyk.demo.flickrdemo.screens.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SearchInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void change_text_on_search() {
        // given: open search box
        onView(withId(R.id.search)).perform(click());

        // when: use hack from SO to type text in serach box
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText("hey there!"));

        // then: Check that the text was changed.
        onView(withId(R.id.the_text))
                .check(matches(withText("hey there!")));
    }


}
