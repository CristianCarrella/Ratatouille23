/*
SBAGLIATO
package com.example.ratatouille_android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.View;

import com.example.ratatouille_android.controllers.CreaProdottoController;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.CreaProdottoActivity;
import com.example.ratatouille_android.views.MainActivity;

@RunWith(MockitoJUnitRunner.class)
public class CreaProdottoActivityTest {
    @Mock
    private CreaProdottoController creaProdottoController;

    @Mock
    private View caricamento;

    private CreaProdottoActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = new CreaProdottoActivity();
        creaProdottoController = new CreaProdottoController(mainActivity, new User());
        caricamento = new View(mainActivity);
    }

    @Test
    public void autoCompilazione_isPossibleGenerateFalse_returnsFalse() {
        boolean result = mainActivity.autoCompilazione("prodotto", 10, false);
        assertFalse(result);
    }

    @Test
    public void autoCompilazione_resultIndexLessThanMaxResultIndex_returnsTrue() {
        when(creaProdottoController.getResultIndex()).thenReturn(5);
        boolean result = mainActivity.autoCompilazione("prodotto", 10, true);
        assertTrue(result);
        verify(caricamento).setVisibility(View.VISIBLE);
        verify(creaProdottoController).getInfoProdotto("prodotto");
    }

    @Test
    public void autoCompilazione_resultIndexGreaterThanMaxResultIndex_returnsFalse() {
        when(creaProdottoController.getResultIndex()).thenReturn(11);
        boolean result = mainActivity.autoCompilazione("prodotto", 10, true);
        assertFalse(result);
        verify(mainActivity).setErrorLabelAutoCompilationOnMaxError();
    }

    @Test
    public void autoCompilazione_emptyProductName_returnsFalse() {
        boolean result = mainActivity.autoCompilazione("", 10, true);
        assertFalse(result);
        verify(mainActivity).setErrorLableAutoCompilationOnErrorEmpty();
    }
}
*/