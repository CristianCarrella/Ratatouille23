package com.example.ratatouille_android;

import static org.junit.Assert.assertEquals;


import android.support.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.CreaProdottoActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(AndroidJUnit4.class)
public class CreaProdottoActivityTest {
    @Rule
    public ActivityTestRule<CreaProdottoActivity> creaProdottoActivity = new ActivityTestRule<>(CreaProdottoActivity.class);

    @Test
    public void autoCompilazione() {
        //creaProdottoActivity.getActivity().autoCompilazione("pizza", 100, true);
    }
}