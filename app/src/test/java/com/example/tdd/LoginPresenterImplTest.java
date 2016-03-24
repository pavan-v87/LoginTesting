package com.example.tdd;

import com.example.tdd.login.Login;
import com.example.tdd.login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Pavan.VijayaNar on 22-Mar-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {

    LoginPresenter loginPresenter;

    @Mock
    private Login.View mockView;

    @Mock
    private Login.BackendService mockService;

    @Captor
    private ArgumentCaptor<Login.ServiceCallback> callbackArgumentCaptor;

    @Before
    public void initialize() {
        loginPresenter = new LoginPresenter(mockView, mockService);
    }

    @Test
    public void shouldShowErrorWhenUserNameIsEmpty() {
        when(mockView.getUserName()).thenReturn("");

        loginPresenter.doLogin();
        verify(mockView).showUserNameError(R.string.user_name_empty);
    }

    @Test
    public void shouldShowErrorWhenPasswordIsEmpty() {
        when(mockView.getUserName()).thenReturn("John");
        when(mockView.getPassword()).thenReturn("");

        loginPresenter.doLogin();
        verify(mockView).showPasswordError(R.string.user_name_empty);
    }


    @Test
    public void shouldShowLoginFailed() {
        when(mockView.getUserName()).thenReturn("John");
        when(mockView.getPassword()).thenReturn("Doe");

        loginPresenter.doLogin();

        // Let's call the callback. ArgumentCaptor.capture() works like a matcher.
        verify(mockService, times(1)).doLogin(eq("John"), eq("Doe"), callbackArgumentCaptor.capture());

        // Trigger the reply on callbackCaptor.getValue()
        callbackArgumentCaptor.getValue().onLoginFailed();

        // Verify loginFailed is invoked on View
        verify(mockView).loginFailed();
    }


    @Test
    public void shouldShowLoginSuccess() {
        when(mockView.getUserName()).thenReturn("John");
        when(mockView.getPassword()).thenReturn("Doe");

        loginPresenter.doLogin();

        // Let's call the callback. ArgumentCaptor.capture() works like a matcher.
        verify(mockService, times(1)).doLogin(eq("John"), eq("Doe"), callbackArgumentCaptor.capture());

        // Trigger the reply on callbackCaptor.getValue()
        callbackArgumentCaptor.getValue().onLoginSuccess();

        // Verify loginFailed is invoked on View
        verify(mockView).loginSuccess();
    }

}