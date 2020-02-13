package com.ninjeng.softwaricacollegestudentcommunicationportal.Activities;

import androidx.test.rule.ActivityTestRule;

import com.ninjeng.softwaricacollegestudentcommunicationportal.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class loginTesting {
    @Rule
    public ActivityTestRule<LoginActivity> testRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity loginActivity = null;

    @Before
    public void setUp() throws Exception {
        loginActivity = testRule.getActivity();
    }

    @Test
    public void loginTest(){
        onView(withId(R.id.email))
                .perform(typeText("ninzenlama21@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.password))
                .perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.btnLogin))
                .perform(click());

//        onView()
//                .check(matches(withText(mText)));
    }
    @After
    public void tearDown() throws Exception {
    }

}

