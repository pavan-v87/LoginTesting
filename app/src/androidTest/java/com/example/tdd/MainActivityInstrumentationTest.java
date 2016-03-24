package com.example.tdd;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.tdd.login.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentationTest {

    private static final String TAG = "MainActivityInstTest";
    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateErrorShownWhenThereIsNoUserName() {
        onView(withId(R.id.login)).perform(click());

        onView(withId(R.id.userName)).check(matches(withError(
                InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.user_name_empty))));

    }

    @Test
    public void validateErrorShownWhenThereIsNoPassword() {
        onView(withId(R.id.userName)).perform(replaceText("pavan.v"));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.password)).check(matches(withError(
                InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.password_empty))));
    }

    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }
                EditText editText = (EditText) view;
                CharSequence error = editText.getError();
                boolean matches = false;
                if (!TextUtils.isEmpty(error)) {
                    matches = error.toString().equals(expected);
                }
                return matches;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(expected);
            }
        };
    }

    @Test
    public void validateLoginSuccess() {

        onView(withId(R.id.userName)).perform(replaceText("pavan.v"));
        onView(withId(R.id.password)).perform(replaceText("1234"));
        onView(withId(R.id.login)).perform(click());

        IdlingResource idlingResource = new IdlingResource() {
            private IdlingResource.ResourceCallback _callback;
            @Override
            public String getName() {
                return "Login Idle Resourece";
            }

            @Override
            public boolean isIdleNow() {
                MainActivity activity = activityTestRule.getActivity();
                //activity.getMainLooper().getQueue().isIdle()
                if (null != activity && activity.isLoginCompleted()){
                    if (_callback != null) {
                        _callback.onTransitionToIdle();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void registerIdleTransitionCallback(ResourceCallback callback) {
                _callback = callback;
            }
        };
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(R.id.textView3)).check(matches(withText(R.string.login_success)));

        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void validateLoginFailed() {

        onView(withId(R.id.userName)).perform(replaceText("pav"));
        onView(withId(R.id.password)).perform(replaceText("1234"));
        onView(withId(R.id.login)).perform(click());

        IdlingResource idlingResource = new IdlingResource() {
            private IdlingResource.ResourceCallback _callback;
            @Override
            public String getName() {
                return "Login Idle Resource";
            }

            @Override
            public boolean isIdleNow() {
                MainActivity activity = activityTestRule.getActivity();
                Log.d(TAG, "isIdleNow()++ Activity : " + activity);
                //activity.getMainLooper().getQueue().isIdle()
                if (null != activity && activity.isLoginCompleted()){
                    Log.d(TAG, "isIdleNow() Login COMPLETED");
                    if (_callback != null) {
                        _callback.onTransitionToIdle();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void registerIdleTransitionCallback(ResourceCallback callback) {
                _callback = callback;
            }
        };
        IdlingPolicies.setIdlingResourceTimeout(2000, TimeUnit.MILLISECONDS);
        Espresso.registerIdlingResources(idlingResource);

        Log.d(TAG, "CHECK if Login failed");
        onView(withId(R.id.textView3)).check(matches(withText(R.string.login_failed)));
        Log.d(TAG, "CHECK Login failed DONE");

        Espresso.unregisterIdlingResources(idlingResource);
    }
}