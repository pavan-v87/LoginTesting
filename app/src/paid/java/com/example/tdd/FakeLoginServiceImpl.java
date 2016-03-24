package com.example.tdd;

import com.example.tdd.login.Login;

/**
 * Created by Pavan.VijayaNar on 24-Mar-16.
 */
class FakeLoginServiceImpl implements Login.BackendService {


    private int status;

    @Override
    public void doLogin(String userName, String password, final Login.ServiceCallback callback) {
        status = LOGIN_STARTED;
        if ("pavan.v".contentEquals(userName) && "1234".contentEquals(password)) {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    callback.onLoginSuccess();
                    status = LOGIN_COMPLETED;
                }
            });
            thread.start();
        }
        else {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    callback.onLoginFailed();
                    status = LOGIN_COMPLETED;
                }
            });
            thread.start();
        }
    }

    @Override
    public int getStatus() {
        return status;
    }
}
