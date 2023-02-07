package com.example.ratatouille_android;

import static org.junit.Assert.*;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.ratatouille_android.views.CreaProdottoActivity;

@RunWith(AndroidJUnit4.class)
public class CreaProdottoActivityTest {

    CreaProdottoActivity creaProdottoActivity;

    @Before
    public void init(){
        creaProdottoActivity = new CreaProdottoActivity();
    }

    @Test
    public void autoCompilazione() {
        assertEquals(true, creaProdottoActivity.autoCompilazione("pizza", 11, true));
    }
}