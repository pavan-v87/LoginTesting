package com.example.tdd.login;

import com.example.tdd.R;

/**
 * Created by Pavan.VijayaNar on 22-Mar-16.
 */
public class LoginPresenter implements Login.ServiceCallback {

    private final Login.View viewImpl;
    private final Login.BackendService serviceImpl;

    public LoginPresenter(Login.View view, Login.BackendService service) {
        viewImpl = view;
        serviceImpl = service;
    }

    public void doLogin() {

        String userName = viewImpl.getUserName();
        String password = viewImpl.getPassword();

        if (userName.isEmpty()) {
            viewImpl.showUserNameError(R.string.user_name_empty);
        }
        else if (password.isEmpty()) {
            viewImpl.showPasswordError(R.string.password_empty);
        }
        else {
            serviceImpl.doLogin(userName, password, this);
        }
    }

    @Override
    public void onLoginSuccess() {
        viewImpl.loginSuccess();
    }

    @Override
    public void onLoginFailed() {
        viewImpl.loginFailed();
    }
}
