package com.example.tdd.login;

/**
 * Created by Pavan.VijayaNar on 22-Mar-16.
 */
public interface Login {
    interface View {

        String getUserName();

        void showUserNameError(int strResId);

        String getPassword();

        void showPasswordError(int strResId);

        void loginFailed();

        void loginSuccess();
    }

    interface ServiceCallback {
        void onLoginSuccess();
        void onLoginFailed();
    }

    interface BackendService {
        int LOGIN_STARTED=0,LOGIN_COMPLETED=1;
        void doLogin(String userName, String password, ServiceCallback callback);
        int getStatus();
    }
}
