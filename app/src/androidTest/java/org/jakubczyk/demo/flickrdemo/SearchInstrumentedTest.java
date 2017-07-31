package org.jakubczyk.demo.flickrdemo;

import android.os.SystemClock;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;

import org.hamcrest.Matcher;
import org.jakubczyk.demo.flickrdemo.screens.search.SearchActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SearchInstrumentedTest {

    @Rule
    public ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<>(
            SearchActivity.class);

    @Test
    public void change_text_on_search() {
        // given: open search box
        onView(withId(R.id.search)).perform(click());

        // when: use hack from SO to type text in serach box
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText("kitten"));
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(closeSoftKeyboard());

        SystemClock.sleep(1500);

        // NOTE 21, 41, 61, 81 - the one is for the spinner item because it's part of the recycler view

        // then: Check that the text was changed.
        onView(withId(R.id.search_result_list))
                .check(new RecyclerViewItemCountAssertion(21));

        onView(ViewMatchers.withId(R.id.search_result_list)).perform(ViewActions.swipeUp());

        // wait for next page
        SystemClock.sleep(1500);
        onView(withId(R.id.search_result_list))
                .check(new RecyclerViewItemCountAssertion(41));

        onView(ViewMatchers.withId(R.id.search_result_list)).perform(ViewActions.swipeUp());

        // wait for next page
        SystemClock.sleep(1500);
        onView(withId(R.id.search_result_list))
                .check(new RecyclerViewItemCountAssertion(61));
    }


    static class RecyclerViewItemCountAssertion implements ViewAssertion {

        private final Matcher<Integer> matcher;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.matcher = is(expectedCount);
        }

        public RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
            this.matcher = matcher;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), matcher);
        }

    }

}
