package com.example.tdd;

import com.example.tdd.login.Login;

import okhttp3.OkHttpClient;

/**
 * Created by Pavan.VijayaNar on 24-Mar-16.
 */
public final class LoginServiceFactory {
    public static Login.BackendService get() {
        return new LoginServiceImpl(new OkHttpClient());
    }
}
